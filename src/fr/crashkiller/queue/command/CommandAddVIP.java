package fr.crashkiller.queue.command;

import fr.crashkiller.queue.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandAddVIP extends Command{

	private Main main;

	public CommandAddVIP(Main main) {
		super("addvip");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] arg) {
		if(!sender.hasPermission("queue.addvip")){
			sender.sendMessage(new TextComponent(main.getPrefix() + ChatColor.RED + "You dont have the permission to add vip."));
			return;
		}
		
		if(main.getConfig().getPermissionsMode()){
			sender.sendMessage(new TextComponent(main.getPrefix() + ChatColor.RED + "Command can be use only if you use data file."));
			return;
		}
		
		
		if(arg.length >= 1) {
			if(arg.length >= 2){
				if(main.isRealServer(arg[1])) {
					main.addVipPlayer(arg[0], arg[1]);
					sender.sendMessage(new TextComponent(main.getPrefix() + ChatColor.GREEN + main.getTranslation().getVIPSuccess()));
				}else{
					sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.RED + main.getTranslation().getWrongServerName()));
				}
			}else{
				main.addVipPlayer(arg[0], "PROXY");
				sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.GREEN + main.getTranslation().getVIPSuccess()));
			}
		}else{
			sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.RED + main.getTranslation().getBadArg()));
		}
	}
}