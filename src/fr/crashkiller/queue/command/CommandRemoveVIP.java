package fr.crashkiller.queue.command;

import fr.crashkiller.queue.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandRemoveVIP extends Command{

	private Main main;

	public CommandRemoveVIP(Main main) {
		super("removevip");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] arg) {
		if(!sender.hasPermission("queue.removevip")){
			sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.RED + "You dont have the permission to remove vip."));
			return;
		}
		
		if(main.getConfig().getPermissionsMode()){
			sender.sendMessage(new TextComponent(main.getPrefix() + ChatColor.RED + "Command can be use only if you use data file."));
			return;
		}
		
		if(arg.length >= 1){
			if(arg.length >= 2){
				if(main.isVipPlayer(arg[0], arg[1])){
					main.removeVipPlayer(arg[0], arg[1]);
					sender.sendMessage(new TextComponent(main.getPrefix() + ChatColor.GREEN + "The Player have been removed from the " + arg[1] + " vip list."));
				}else{
					sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.RED + "This player isnt vip on this server."));
				}
			}else{
				if(main.isVipPlayer(arg[0])){
					main.removeVipPlayer(arg[0], "PROXY");
					sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.GREEN + "The Player have been removed from all server vip list."));
				}else{
					sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.RED + "This player isnt vip on any server."));
				}
			}
		}else{
			sender.sendMessage(new TextComponent(main.getPrefix() + ChatColor.RED + main.getTranslation().getBadArg()));
		}
	}

}
