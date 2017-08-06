package me.skyvox.smessages.automessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skyvox.smessages.Sky;
import me.skyvox.smessages.files.ConfigFile;

public class AutoMessageCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("skymessages.automessage") || sender.hasPermission("skymessages.admin")) {
			if (args.length == 0) {
				help(sender);
			} else if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable")) {
				if (args.length == 1) {
					Sky.automessage.start();
					sender.sendMessage(ConfigFile.get().contains("AutoMessageSettings.messages.start-for-everyone") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("AutoMessageSettings.messages.start-for-everyone").replace("%playerName%", sender.getName())) : ChatColor.GREEN + "You have enabled Auto-Message!");
				} else if (args.length >= 2) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target != null) {
						if (Sky.automessage.isAutoMessage(target.getUniqueId())) {
							sender.sendMessage(ConfigFile.get().contains("AutoMessageSettings.messages.already-enabled") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("AutoMessageSettings.messages.already-enabled").replace("%playerName%", sender.getName()).replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName())) : ChatColor.RED + "Auto-Message is already on.");
						} else {
							Sky.automessage.start(target.getUniqueId());
							sender.sendMessage(ConfigFile.get().contains("AutoMessageSettings.messages.enable-for-a-player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("AutoMessageSettings.messages.enable-for-a-player").replace("%playerName%", sender.getName()).replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName())) : ChatColor.GREEN + "You have been enabled Auto-Message for " + target.getName() + "!");
						}
						return true;
					} else {
						sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
					}
					return true;
				}
				return true;
			} else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")) {
				if (args.length == 1) {
					Sky.automessage.stop();
					sender.sendMessage(ConfigFile.get().contains("AutoMessageSettings.messages.disable-for-everyone") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("AutoMessageSettings.messages.disable-for-everyone").replace("%playerName%", sender.getName())) : ChatColor.RED + "You have disabled Auto-Message!");
				} else if (args.length >= 2) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target != null) {
						if (Sky.automessage.isAutoMessage(target.getUniqueId())) {
							Sky.automessage.stop(target.getUniqueId());
							sender.sendMessage(ConfigFile.get().contains("AutoMessageSettings.messages.disabled-for-a-player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("AutoMessageSettings.messages.disabled-for-a-player").replace("%playerName%", sender.getName()).replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName())) : ChatColor.RED + "You have been disabled Auto-Message for " + target.getName() + "!");
						} else {
							sender.sendMessage(ConfigFile.get().contains("AutoMessageSettings.messages.already-disabled") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("AutoMessageSettings.messages.already-disabled").replace("%playerName%", sender.getName()).replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName())) : ChatColor.RED + "Auto-Message is already off.");
						}
						return true;
					} else {
						sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
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
		for (String str : ConfigFile.get().getStringList("AutoMessageSettings.help")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", sender.getName())));
		}
	}
}