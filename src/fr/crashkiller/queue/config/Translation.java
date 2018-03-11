package fr.crashkiller.queue.config;

import java.io.File;

import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

public class Translation extends Config{
	
	public Translation(Plugin plugin) {
	       CONFIG_FILE = new File(plugin.getDataFolder(), "translation.yml");
	}

	@Comment("Call to notify player when he join a queue")
	private String addQueue = "You have been add to queue at position";
	
	@Comment("Call to notify player's place in current queue")
	private String notifyPlace = "You are now at position ";

	@Comment("Call when player is kicked for keep reserved slot")
	private String kick = "This slot is reserved for vip";
	
	@Comment("Call to notify player when he leave a queue")
	private String leaveQueue = "You have leave the queue ";
	
	@Comment("Call when player try to leave a queue when hes not in queue.")
	private String leaveQueueFail = "You are not in queue ";
	
	@Comment("Call when player try to add vip with no arg")
	private String badarg = "Wrong argument try : addvip <player> [server]  (no server = proxy)";
	
	@Comment("Call when player add a vip player")
	private String addvip = "Player have been correctly add to vip list.";
	
	@Comment("Call when player add a vip player in a server and the server doesnt exist")
	private String wrongServerName = "The server name that you used doesnt exist";

	public String getAddQueue() {
		return ChatColor.translateAlternateColorCodes('&', addQueue);
	}

	public String getNotifyPlace() {
		return ChatColor.translateAlternateColorCodes('&', notifyPlace);
	}

	public String getKick() {
		return ChatColor.translateAlternateColorCodes('&', kick);
	}

	public String getLeaveQueue() {
		return ChatColor.translateAlternateColorCodes('&', leaveQueue);
	}

	public String getLeaveQueueFail() {
		return ChatColor.translateAlternateColorCodes('&', leaveQueueFail);
	}
	
	public String getBadArg() {
		return ChatColor.translateAlternateColorCodes('&', badarg);
	}
	
	public String getVIPSuccess() {
		return ChatColor.translateAlternateColorCodes('&', addvip);
	}
	
	public String getWrongServerName() {
		return ChatColor.translateAlternateColorCodes('&', wrongServerName);
	}
}
