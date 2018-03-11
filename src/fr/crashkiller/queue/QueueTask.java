package fr.crashkiller.queue;

import java.util.Collection;
import java.util.Map.Entry;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class QueueTask implements Runnable {

	private Main main;

	
	public QueueTask(Main main) {
        this.main = main;
    }

	public void run() {
		for(Entry<String, Server> entry : main.getServerList().entrySet()) {
		    String name = entry.getKey();
		    Server server = entry.getValue();
		    
		    if(main.serverIsFull(main.getProxy().getServerInfo(name), true) && main.kickForVip()){
				Collection<ProxiedPlayer> players = main.getProxy().getServerInfo(name).getPlayers();

				for(ProxiedPlayer player : players){
					if(!main.isVipPlayer(player.getName(), name)){
						player.disconnect(new TextComponent(main.getPrefix() + main.getTranslation().getKick()));
						return;
					}
				}
			}
		    
		    if(!main.serverIsFull(main.getProxy().getServerInfo(name), false) && server.getWaitlist().size() > 0){
		    	String player = server.getWaitlist().get(0);
		    	
		    	server.getWaitlist().remove(0);
		    	main.getDirectConnectList().add(player);
		    	
		    	main.connectPlayer(name, player);
		    	
		    	main.notifyPlayerQueue(name, server.getWaitlist());
		    }
		}
		
	}
}