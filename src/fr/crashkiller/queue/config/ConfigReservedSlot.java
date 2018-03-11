package fr.crashkiller.queue.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import net.md_5.bungee.api.plugin.Plugin;

public class ConfigReservedSlot extends Config {
	
   public ConfigReservedSlot(Plugin plugin) {
       CONFIG_FILE = new File(plugin.getDataFolder(), "reservedSlot.yml");
   }
   
   @SuppressWarnings({ "serial" })
   @Comment("List of all vip for all server in proxy")
   private HashMap<String, ArrayList<String>> serverRSlist = new HashMap<String, ArrayList<String>>() {{
	   put("server1",new ArrayList<String>(){{
		   add("player1");
		   add("player2");
	   }});
	   put("server2",new ArrayList<String>(){{
		   add("player1");
		   add("player2");
	   }});
   }};

   public HashMap<String, ArrayList<String>> getServerRSlist() {
	   return serverRSlist;
   }
}