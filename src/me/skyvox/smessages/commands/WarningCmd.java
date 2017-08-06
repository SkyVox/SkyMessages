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

public class WarningCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("skymessages.warning") || sender.hasPermission("skymessages.admin")) {
			if (args.length == 0) {
				help(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("title")) {
				if (args.length >= 2) {
					StringBuilder strB = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						strB.append(args[i] + " ");
					}
					String msg = ConfigFile.get().contains("Warning.title") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Warning.title").replace("%message%", "" + strB.toString().trim())) : ChatColor.RED + "MyServer> " + ChatColor.AQUA + strB.toString().trim();
					int fadeIn = 20;
					int stay = 5;
					int fadeOut = 20;
					try {
						fadeIn = Integer.parseInt(ConfigFile.get().getString("Warning.time").split(",")[0]);
						stay = Integer.parseInt(ConfigFile.get().getString("Warning.time").split(",")[1]);
						fadeOut = Integer.parseInt(ConfigFile.get().getString("Warning.time").split(",")[2]);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					for (Player players : Bukkit.getOnlinePlayers()) {
						TitleAPI.clearTitle(players);
						TitleAPI.sendSubtitle(players, fadeIn, stay, fadeOut, msg);
					}
					return true;
				} else {
					help(sender);
				}
				return true;
			} else if (args.length >= 2) {
				StringBuilder strB = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					strB.append(args[i] + " ");
				}
				String msg = ConfigFile.get().contains("Warning.chat") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Warning.chat").replace("%message%", "" + strB.toString().trim())) : ChatColor.RED + "MyServer> " + ChatColor.AQUA + strB.toString().trim();
				for (Player players : Bukkit.getOnlinePlayers()) {
					players.sendMessage(msg);
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
		for (String str : ConfigFile.get().getStringList("Warning.help")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", sender.getName())));
		}
	}
}