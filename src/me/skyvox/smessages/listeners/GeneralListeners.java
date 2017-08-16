package me.skyvox.smessages.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import me.skyvox.smessages.Sky;
import me.skyvox.smessages.api.TitleAPI;
import me.skyvox.smessages.files.ConfigFile;

public class GeneralListeners implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerList(ServerListPingEvent event) {
		if ((ConfigFile.get().contains("Motd.enabled")) && (ConfigFile.get().getString("Motd.enabled").equalsIgnoreCase("true"))) {
			String lineOne = ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Motd.line-1").replace("%serverName%", Bukkit.getServerName()));
			String lineTwo = ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Motd.line-2").replace("%serverName%", Bukkit.getServerName()));
			event.setMotd(lineOne + "\n" + lineTwo);
		}
		if ((ConfigFile.get().contains("Motd.one-more-slot")) && (ConfigFile.get().getString("Motd.one-more-slot").equalsIgnoreCase("true"))) {
			event.setMaxPlayers(event.getNumPlayers() + 1);
		} else if ((ConfigFile.get().contains("Motd.max-enabled")) && (ConfigFile.get().getString("Motd.max-enabled").equalsIgnoreCase("true"))) {
			event.setMaxPlayers(ConfigFile.get().getInt("Motd.max-players"));
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if ((ConfigFile.get().contains("JoinAndQuit.join-enabled")) && (ConfigFile.get().getString("JoinAndQuit.join-enabled").equalsIgnoreCase("true"))) {
			Player player = event.getPlayer();
			if ((ConfigFile.get().contains("JoinAndQuit.title-when-join")) && (ConfigFile.get().getString("JoinAndQuit.title-when-join").equalsIgnoreCase("true"))) {
				String title = ConfigFile.get().contains("JoinAndQuit.title-message") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("JoinAndQuit.title-message").replace("%playerName%", player.getName()).replace("%playerDisplayname%", player.getDisplayName())) : ChatColor.DARK_AQUA + "Welcome back " + player.getName();
				String subtitle = null;
				if (title.contains("<sub/>")) {
					String[] titleSplit = title.split("<sub/>");
					title = ChatColor.translateAlternateColorCodes('&', titleSplit[0].replace("%playerName%", player.getName()).replace("%playerDisplayname%", player.getDisplayName()));
					subtitle = ChatColor.translateAlternateColorCodes('&', titleSplit[1].replace("%playerName%", player.getName()).replace("%playerDisplayname%", player.getDisplayName()));
				}
				int fadeIn = 20;
				int stay = 5;
				int fadeOut = 20;
				try {
					fadeIn = Integer.parseInt(ConfigFile.get().getString("JoinAndQuit.time").split(",")[0]);
					stay = Integer.parseInt(ConfigFile.get().getString("JoinAndQuit.time").split(",")[1]);
					fadeOut = Integer.parseInt(ConfigFile.get().getString("JoinAndQuit.time").split(",")[2]);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				TitleAPI.sendFullTitle(player, fadeIn, stay, fadeOut, title, (subtitle == null ? "" : subtitle));
			}
			for (String str : ConfigFile.get().getStringList("JoinAndQuit.join-player-message")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", player.getName()).replace("%playerDisplayname%", player.getDisplayName())));
			}
			if ((ConfigFile.get().contains("JoinAndQuit.need-permission")) && (ConfigFile.get().getString("JoinAndQuit.need-permission").equalsIgnoreCase("true"))) {
				if (!(event.getPlayer().hasPermission("skymessages.join") || event.getPlayer().hasPermission("skymessages.admin"))) {
					return;
				}
			}
			String msg = ConfigFile.get().contains("JoinAndQuit.join-message") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("JoinAndQuit.join-message").replace("%playerName%", player.getName()).replace("%displayName%", player.getDisplayName())) : ChatColor.YELLOW + "[" + ChatColor.GREEN + "+" + ChatColor.YELLOW + "] " + ChatColor.AQUA + player.getName();
			event.setJoinMessage(msg);
			
		}
		if ((ConfigFile.get().contains("AutoMessageSettings.enable-when-player-join")) && (ConfigFile.get().getString("AutoMessageSettings.enable-when-player-join").equalsIgnoreCase("true"))) {
			if (!Sky.automessage.isAutoMessage(event.getPlayer().getUniqueId())) {
				Sky.automessage.start(event.getPlayer().getUniqueId());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if ((ConfigFile.get().contains("JoinAndQuit.quit-enabled")) && (ConfigFile.get().getString("JoinAndQuit.quit-enabled").equalsIgnoreCase("true"))) {
			if ((ConfigFile.get().contains("JoinAndQuit.need-permission")) && (ConfigFile.get().getString("JoinAndQuit.need-permission").equalsIgnoreCase("true"))) {
				if (!(event.getPlayer().hasPermission("skymessages.quit") || event.getPlayer().hasPermission("skymessages.admin"))) {
					return;
				}
			}
			Player player = event.getPlayer();
			String msg = ConfigFile.get().contains("JoinAndQuit.quit-message") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("JoinAndQuit.quit-message").replace("%playerName%", player.getName()).replace("%displayName%", player.getDisplayName())) : ChatColor.YELLOW + "[" + ChatColor.RED + "-" + ChatColor.YELLOW + "] " + ChatColor.AQUA + player.getName();
			event.setQuitMessage(msg);
		}
		if ((ConfigFile.get().contains("AutoMessageSettings.disable-when-quit")) && (ConfigFile.get().getString("AutoMessageSettings.disable-when-quit").equalsIgnoreCase("true"))) {
			if (Sky.automessage.isAutoMessage(event.getPlayer().getUniqueId())) {
				Sky.automessage.stop(event.getPlayer().getUniqueId());
			}
		}
	}
}