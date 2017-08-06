package me.skyvox.smessages.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.skyvox.smessages.files.ConfigFile;

public class CommandPreprocess implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPrecess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		for (String str : ConfigFile.get().getStringList("Blockes-Cmds.commands")) {
			if (event.getMessage().equalsIgnoreCase(str)) {
				if (!(player.hasPermission("skymessages.blockedcmds.bypass") || player.hasPermission("skymessages.admin"))) {
					event.setCancelled(true);
					player.sendMessage(ConfigFile.get().contains("Blockes-Cmds.error-message") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Blockes-Cmds.error-message").replace("%playerName%", player.getName()).replace("%playerDisplayname", player.getDisplayName())) : ChatColor.RED + "This command does not exist.");
				}
			}
		}
	}
}