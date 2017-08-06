package me.skyvox.smessages.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skyvox.smessages.Sky;
import me.skyvox.smessages.files.ConfigFile;

public class ClearChatCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("skymessages.clearchat") || sender.hasPermission("skymessages.admin")) {
			String message = ConfigFile.get().contains("Clear-Chat-Message") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Clear-Chat-Message")) : "";
			for (Player players : Bukkit.getOnlinePlayers()) {
				for (int i = 0; i < 175; i++) {
					players.sendMessage(message);
				}
			}
			return true;
		} else {
			sender.sendMessage(Sky.getInstance().insufficientPermission);
		}
		return true;
	}
}