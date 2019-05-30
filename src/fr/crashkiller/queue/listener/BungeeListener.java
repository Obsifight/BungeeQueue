package fr.crashkiller.queue.listener;

import fr.crashkiller.queue.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeListener implements Listener{
	Main main;
	
	public BungeeListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPreLogin(ServerConnectEvent event) {
		String serverName = event.getTarget().getName();
		String playerName = event.getPlayer().getName();
		
		if(main.getServerList().containsKey(serverName) && !main.getServerList().get(serverName).isOnline()) {
			event.getPlayer().sendMessage(new TextComponent(main.getPrefix() + ChatColor.RED + "Le serveur " + serverName + " est actuellement offline, vous ne pouvez pas le rejoindre."));
		}
		
		if(main.isVipPlayer(playerName, serverName) || main.getDirectConnectList().contains(playerName) || event.getTarget().getPlayers().contains(event.getPlayer())) return;
		
		if(main.isVipPlayer(playerName, serverName)){
			
			return;
		}
		
		if(main.hasServerQueue(serverName)){
			if((main.serverIsFull(event.getTarget(), false) || main.getWaitlist(serverName).size() > 0) && !main.getWaitlist(serverName).contains(playerName)){
				if(main.isPlayerInQueue(playerName)) {
					main.removePlayerFromAllQueue(playerName);
				}
				event.setCancelled(true);
				main.getWaitlist(serverName).add(playerName);
				int place = main.getWaitlist(serverName).indexOf(playerName) + 1;
				main.getProxy().getPlayer(playerName).sendMessage(new TextComponent(main.getPrefix() + main.getTranslation().getAddQueue()+ " "+place+"/"+main.getWaitlist(serverName).size()));
			}else if(main.getWaitlist(serverName).contains(playerName))
				event.setCancelled(true);
			} else if ((!main.serverIsFull(event.getTarget(), false) && (main.getWaitlist(serverName).size() == 0)) && !main.getWaitlist(serverName).contains(playerName)) {
				event.setCancelled(false);
			} else {
				main.getProxy().getPlayer(playerName).sendMessage(new TextComponent(main.getPrefix() + "Une erreur est survenue."));
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPostLogin(ServerConnectedEvent event) {
		String serverName = event.getServer().getInfo().getName();
		String playerName = event.getPlayer().getName();
		if(main.hasServerQueue(serverName)){
			if(main.getDirectConnectList().contains(playerName)){
				main.getDirectConnectList().remove(playerName);
				
			}
		}
	}
	
	@EventHandler
	public void onDisconnectLogin(ServerDisconnectEvent event) {
		String playerName = event.getPlayer().getName();
		String serverName = main.getServerQueue(playerName);
		if(serverName != null){
			if(main.getWaitlist(serverName).contains(playerName)){
				int place = main.getWaitlist(serverName).indexOf(playerName);
				main.getWaitlist(serverName).remove(place);
				main.notifyPlayerQueue(serverName, main.getWaitlist(serverName));
			}
		}
	}
}
