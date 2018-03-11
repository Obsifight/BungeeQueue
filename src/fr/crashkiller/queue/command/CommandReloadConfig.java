package fr.crashkiller.queue.command;

import fr.crashkiller.queue.Main;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandReloadConfig extends Command{

	private Main main;

	public CommandReloadConfig(Main main) {
		super("reloadqueue");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] arg) {
		if(!sender.hasPermission("queue.reloadqueue")){
			sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.RED + "You dont have the permission to reload the configuration."));
			return;
		}
		
		try {
			main.getConfig().reload();
			sender.sendMessage(new TextComponent(main.getPrefix() +ChatColor.GREEN + "The reload has been done."));
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			sender.sendMessage(new TextComponent(main.getPrefix() + ChatColor.RED + "The reload has failed, check your bungee log."));
		}
		
	}

}
