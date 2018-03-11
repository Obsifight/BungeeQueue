package fr.crashkiller.queue.config;

import java.io.File;
import java.util.ArrayList;

import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import net.md_5.bungee.api.plugin.Plugin;

public class ConfigPlugin extends Config {
	
	public ConfigPlugin(Plugin plugin) {
	       CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
	}

	@Comment("Time to refresh all server (players) in SECONDS")
	private int refreshTime = 2;
	
	@Comment("interval between each backup from the list of reserved slot in MINUTES")
	private int saveIntervalTime = 5;
	
	@SuppressWarnings("serial")
	@Comment("Name of server whithout queue")
	private ArrayList<String> withoutQueue = new ArrayList<String>(){{add("lobby");add("hub");}};

	@Comment("Number of slots reserved in each server")
	private int reservedSlot = 10;
	
	@Comment("Kick player for keep reserved slot in each server")
	private boolean kickForVip = true;
	
	@Comment("Use permission instead of data.yml")
	private boolean permissionsMode = false;
	
	@Comment("Prefix when the plugin send message to player")
	private String prefix = "Queue";
	
	public int getReservedSlot() {
		return reservedSlot;
	}

	public int getRefreshTime() {
		return refreshTime;
	}

	public ArrayList<String> getWithoutQueue() {
		return withoutQueue;
	}

	public boolean getKickForVip() {
		return kickForVip;
	}

	public int getSaveIntervalTime() {
		return saveIntervalTime;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public boolean getPermissionsMode() {
		return permissionsMode;
	}
}
