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

public class AnnounceCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("skymessages.announce") || sender.hasPermission("skymessages.admin")) {
			if (args.length == 0) {
				help(sender);
				return true;
			} else if (args.length >= 1) {
				StringBuilder strB = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					strB.append(args[i] + " ");
				}
				if ((ConfigFile.get().contains("Announce.announce-title")) && (ConfigFile.get().getString("Announce.announce-title").equalsIgnoreCase("true"))) {
					String msg = ConfigFile.get().contains("Announce.title-prefix") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Announce.title-prefix").replace("%message%", "" + strB.toString().trim())) : ChatColor.RED + "Announce" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + strB.toString().trim();
					int fadeIn = 20;
					int stay = 5;
					int fadeOut = 20;
					try {
						fadeIn = Integer.parseInt(ConfigFile.get().getString("Announce.time").split(",")[0]);
						stay = Integer.parseInt(ConfigFile.get().getString("Announce.time").split(",")[1]);
						fadeOut = Integer.parseInt(ConfigFile.get().getString("Announce.time").split(",")[2]);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					for (Player players : Bukkit.getOnlinePlayers()) {
						TitleAPI.clearTitle(players);
						TitleAPI.sendSubtitle(players, fadeIn, stay, fadeOut, msg);
					}
				} else {
					for (Player players : Bukkit.getOnlinePlayers()) {
						for (String str : ConfigFile.get().getStringList("Announce.prefix")) {
							str = ChatColor.translateAlternateColorCodes('&', str.replace("%message%", "" + strB.toString().trim()).replace("%playerName%", sender.getName()).replace("%targetName%", players.getName()).replace("%targetDisplayname%", players.getDisplayName()));
							players.sendMessage(str);
						}
					}
					return true;
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
		for (String str : ConfigFile.get().getStringList("Announce.help")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", sender.getName())));
		}
	}
}