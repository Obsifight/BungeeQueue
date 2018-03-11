package fr.crashkiller.queue.command;

import fr.crashkiller.queue.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandLeave extends Command{

	private Main main;

	public CommandLeave(Main main) {
		super("leavequeue");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] arg) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			String playerName = player.getName();
			String serverName = main.getServerQueue(playerName);
			
			if(serverName != null){
				if(main.getWaitlist(serverName).contains(playerName)){
					int place = main.getWaitlist(serverName).indexOf(playerName);
					main.getWaitlist(serverName).remove(place);
					player.sendMessage(new TextComponent(main.getPrefix() + ChatColor.GREEN + main.getTranslation().getLeaveQueue()));
					main.notifyPlayerQueue(serverName, main.getWaitlist(serverName));
				}
			}else
				player.sendMessage(new TextComponent(main.getPrefix() + ChatColor.RED + main.getTranslation().getLeaveQueueFail()));
		}
	}

}
