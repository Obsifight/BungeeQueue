package fr.crashkiller.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import fr.crashkiller.queue.command.CommandAddVIP;
import fr.crashkiller.queue.command.CommandLeave;
import fr.crashkiller.queue.command.CommandReloadConfig;
import fr.crashkiller.queue.command.CommandRemoveVIP;
import fr.crashkiller.queue.config.ConfigPlugin;
import fr.crashkiller.queue.config.ConfigReservedSlot;
import fr.crashkiller.queue.config.Translation;
import fr.crashkiller.queue.listener.BungeeListener;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;



public class Main extends Plugin {

	/**
	 * @author Mac' and Crashkilleur
	 */
	
	private static ConfigReservedSlot 	rsConfig;
	private static ConfigPlugin 		config;
	private static Translation 			translation;
	private String 						msgPrefix;
	
	private ArrayList<String> 			directConnect = new ArrayList<String>();
	private HashMap<String, Server> 	serverList = new HashMap<String, Server>();
	
    @Override
    public void onEnable() {
    	this.loadConfig();
    	//this.msgPrefix = ChatColor.DARK_RED + "[" + ChatColor.GOLD + config.getPrefix() + ChatColor.DARK_RED + "] " + ChatColor.RESET;
    	this.msgPrefix = "";
    	
    	this.getProxy().getScheduler().schedule(this, new QueueTask(this), config.getRefreshTime(), config.getRefreshTime(), TimeUnit.SECONDS);
    	this.getProxy().getScheduler().schedule(this, new SaveTask(this), config.getSaveIntervalTime(), config.getSaveIntervalTime(), TimeUnit.MINUTES);
    	
    	this.getProxy().getPluginManager().registerListener(this, new BungeeListener(this));
    	getProxy().getPluginManager().registerCommand(this, new CommandLeave(this));
    	getProxy().getPluginManager().registerCommand(this, new CommandAddVIP(this));
    	getProxy().getPluginManager().registerCommand(this, new CommandRemoveVIP(this));
    	getProxy().getPluginManager().registerCommand(this, new CommandReloadConfig(this));
    	
    	for(String name : getProxy().getServers().keySet()) {
    		if(!Main.config.getWithoutQueue().contains(name)) {
    			serverList.put(name, new Server(1, false));  
    		}
    	}
    	for(final String name : serverList.keySet()) {
    		getProxy().getServerInfo(name).ping(new Callback<ServerPing>() {
    			@Override
    			public void done(ServerPing arg0, Throwable arg1) {
					if(arg0 != null) {
						int max = arg0.getPlayers() == null ? 0 : arg0.getPlayers().getMax();
						serverList.get(name).setMaxPlayer(max);
						serverList.get(name).setOnline(true);
					}
					else {
						serverList.get(name).setOnline(false);
					}
				}
    		});
    		
    	}
    }
    
    @Override
    public void onDisable(){
    	saveConfig();
    }
    
	public void loadConfig() {
		try {
			rsConfig = new ConfigReservedSlot(this);
			rsConfig.init();
			
			config = new ConfigPlugin(this);
			config.init();
			
			translation = new Translation(this);
			translation.init();
			
		} catch (InvalidConfigurationException ex) {
		    getLogger().severe("Something go wrong with your configuration or translation.");
		    ex.printStackTrace();
		}
		
	}
	
	public void saveConfig(){
		try {
			rsConfig.save();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public boolean serverIsFull(ServerInfo server, boolean b) {
		if(b)
			return server.getPlayers().size() > (this.getServerList().get(server.getName()).getMaxPlayer() - this.getReservedSlot(server.getName()));
		else
			return server.getPlayers().size() >= (this.getServerList().get(server.getName()).getMaxPlayer() - this.getReservedSlot(server.getName()));
				
	}

	public void connectPlayer(String serverName, String playerName) {
    	ServerInfo target = this.getProxy().getServerInfo(serverName);
		this.getProxy().getPlayer(playerName).connect(target);
	}

	public void notifyPlayerQueue(String serverName, ArrayList<String> waitlist) {
		int place;
		for(String playerName : waitlist){
			place = waitlist.indexOf(playerName)+1;
			this.getProxy().getPlayer(playerName).sendMessage(new TextComponent(this.getPrefix() + this.getTranslation().getNotifyPlace() + " " +place+"/"+waitlist.size()));
		}
	}

	public String getServerQueue(String playerName) {
		for(Entry<String, Server> entry : this.getServerList().entrySet()){
			String name = entry.getKey();
		    if(this.getWaitlist(name).contains(playerName))
		    	return name;
		}
		return null;
	}

	public int getReservedSlot(String serverName) {
		return config.getReservedSlot();
	}

	public boolean isVipPlayer(String playerName, String serverName) {
		if(this.getConfig().getPermissionsMode()){
			if(this.getProxy().getPlayer(playerName).hasPermission("queue.server.all"))
				return true;
			else if(this.getProxy().getPlayer(playerName).hasPermission("queue.server." + serverName))
				return true;
			else
				return false;
		}else{
			if(Main.rsConfig.getServerRSlist().containsKey(serverName))
				return Main.rsConfig.getServerRSlist().get(serverName).contains(playerName);
			else 
				return false;
		}
	}
	
	public boolean isVipPlayer(String playerName){
		if(this.getConfig().getPermissionsMode())
			return this.getProxy().getPlayer(playerName).hasPermission("queue.server.all");
		else{
			for(Entry<String, ArrayList<String>> server : this.getRSConfig().getServerRSlist().entrySet()) {
				if(server.getValue().contains(playerName))
					return true;
			}
			return false;
		}
	}
	
	public void addVipPlayer(String playerName, String serverName) {
		if(serverName.contains("PROXY")) {
			for(Entry<String, ArrayList<String>> server : this.getRSConfig().getServerRSlist().entrySet()) {
				server.getValue().add(playerName);
			}
		}else{
			this.getRSConfig().getServerRSlist().get(serverName).add(playerName);
		}
	}
	
	public void removeVipPlayer(String playerName, String serverName) {
		if(serverName.contains("PROXY")) {
			for(Entry<String, ArrayList<String>> server : this.getRSConfig().getServerRSlist().entrySet()) {
				if(server.getValue().contains(playerName))
					server.getValue().remove(playerName);
			}
		}else {
			this.getRSConfig().getServerRSlist().get(serverName).remove(playerName);
		}
			
		
	}
	
	public boolean isPlayerInQueue(String playerName) {
		for(Entry<String, Server> server : serverList.entrySet()) {
			if(server.getValue().getWaitlist().contains(playerName)) {
				return true;
			}
    	}
		return false;
	}
	
	public void removePlayerFromAllQueue(String playerName) {
		for(Entry<String, Server> server : serverList.entrySet()) {
			if(server.getValue().getWaitlist().contains(playerName)) {
				server.getValue().getWaitlist().remove(playerName);
			}
    	}
	}
	
	public boolean hasServerQueue(String serverName) {
		return !config.getWithoutQueue().contains(serverName);
	}
	
	public HashMap<String, Server> getServerList() {
		return serverList;
	}
	public void setServerList(HashMap<String, Server> serverList) {
		this.serverList = serverList;
	}
	public ArrayList<String> getWaitlist(String serverName){
		return this.serverList.get(serverName).getWaitlist();
	}
	
	public ArrayList<String> getDirectConnectList() {
		return directConnect;
	}

	public Translation getTranslation() {
		return translation;
	}

	public boolean kickForVip() {
		return config.getKickForVip();
	}
	
	public ConfigReservedSlot getRSConfig() {
		return rsConfig;
	}
	
	public boolean isRealServer(String name) {
		if(Main.rsConfig.getServerRSlist().containsKey(name)) {
				return true;
		}
		return false;
	}

	public ConfigPlugin getConfig() {
		return config;
	}
	
	public String getPrefix() {
		return msgPrefix;
	}
}