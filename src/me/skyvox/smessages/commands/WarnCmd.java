package me.skyvox.smessages.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skyvox.smessages.Sky;
import me.skyvox.smessages.api.TitleAPI;
import me.skyvox.smessages.files.ConfigFile;

public class WarnCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("skymessages.warn") || sender.hasPermission("skymessages.admin")) {
			if (args.length == 0) {
				help(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("title")) {
				if (args.length >= 2) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target != null) {
						int fadeIn = 20;
						int stay = 5;
						int fadeOut = 20;
						try {
							fadeIn = Integer.parseInt(ConfigFile.get().getString("Warn.time").split(",")[0]);
							stay = Integer.parseInt(ConfigFile.get().getString("Warn.time").split(",")[1]);
							fadeOut = Integer.parseInt(ConfigFile.get().getString("Warn.time").split(",")[2]);
						} catch (NullPointerException e) {
							e.printStackTrace();
						}
						StringBuilder strB = new StringBuilder();
						for (int i = 0; i < args.length; i++) {
							strB.append(args[i] + " ");
						}
						sender.sendMessage(ConfigFile.get().contains("Warn.reply") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Warn.reply").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName())) : ChatColor.RED + "You warned " + ChatColor.AQUA + target.getName());
						TitleAPI.clearTitle(target);
						TitleAPI.sendSubtitle(target, fadeIn, stay, fadeOut, ChatColor.translateAlternateColorCodes('&', strB.toString().trim().replace("%playerName%", sender.getName()).replace("%targetName%", target.getName())));
					} else {
						sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
					}
					return true;
				} else {
					help(sender);
				}
				return true;
			} else if (args.length >= 2) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					StringBuilder strB = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
						strB.append(args[i] + " ");
					}
					sender.sendMessage(ConfigFile.get().contains("Warn.reply") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Warn.reply").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName())) : ChatColor.RED + "You warned " + ChatColor.AQUA + target.getName());
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', strB.toString().trim().replace("%playerName%", sender.getName()).replace("%targetName%", target.getName())));
				} else {
					sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
				}
				return true;
			}
			help(sender);
			return true;
		} else {
			sender.sendMessage(Sky.getInstance().insufficientPermission);
		}
		return true;
	}
	
	private void help(CommandSender sender) {
		for (String str : ConfigFile.get().getStringList("Warn.help")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", sender.getName())));
		}
	}
}