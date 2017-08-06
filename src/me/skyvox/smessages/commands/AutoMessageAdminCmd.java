package me.skyvox.smessages.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.skyvox.smessages.Sky;
import me.skyvox.smessages.files.ConfigFile;
import me.skyvox.smessages.files.MessagesFile;

public class AutoMessageAdminCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			help(sender);
			return true;
		} else if (args[0].equalsIgnoreCase("help")) {
			help(sender);
			return true;
		} else if (args[0].equalsIgnoreCase("max") || args[0].equalsIgnoreCase("players") || args[0].equalsIgnoreCase("maxplayers")) {
			if (sender.hasPermission("skymessages.setmax") || sender.hasPermission("skymessages.admin")) {
				if (args.length >= 1) {
					try {
						int max = Integer.parseInt(args[1]);
						ConfigFile.get().set("Motd.max-players", max);
						ConfigFile.save();
						sender.sendMessage(ChatColor.GREEN + "You changed the max players to " + ChatColor.GRAY + max + ChatColor.GREEN + "!");
					} catch (NumberFormatException e) {
						sender.sendMessage(ChatColor.RED + "Whoops, args '" + args[1] + "' need to be Integer.");
					}
				} else {
					help(sender);
				}
				return true;
			} else {
				sender.sendMessage(Sky.getInstance().insufficientPermission);
			}
			return true;
		} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			if (sender.hasPermission("skymessages.reload") || sender.hasPermission("skymessages.admin")) {
				MessagesFile.reload();
				ConfigFile.reload();
				sender.sendMessage(ConfigFile.get().contains("Reload-Message") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Reload-Message")) : ChatColor.YELLOW + "SkyMessages " + ChatColor.RED + "has been reloaded.");
			} else {
				sender.sendMessage(Sky.getInstance().insufficientPermission);
			}
		}
		return true;
	}
	
	private void help(CommandSender sender) {
		for (String str : ConfigFile.get().getStringList("AutoMessageAdmin.help")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", sender.getName())));
		}
	}
}