package me.skyvox.smessages.automessage;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.skyvox.smessages.Sky;
import me.skyvox.smessages.api.TitleAPI;
import me.skyvox.smessages.files.ConfigFile;
import me.skyvox.smessages.files.MessagesFile;

public class AutoMessageManager {
	private HashMap<UUID, BukkitRunnable> perPlayerRunnable;
	private HashMap<UUID, Integer> messageToSend;
	
	public AutoMessageManager() {
		perPlayerRunnable = new HashMap<>();
		messageToSend = new HashMap<>();
	}
	
	public void start() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (!perPlayerRunnable.containsKey(players.getUniqueId())) {
				perPlayerRunnable.put(players.getUniqueId(), new BukkitRunnable() {
					
					@Override
					public void run() {
						sendMessage(players.getUniqueId());
					}
				});
				int seconds = Integer.valueOf(ConfigFile.get().contains("AutoMessageSettings.messages-delay") ? ConfigFile.get().getInt("AutoMessageSettings.messages-delay") : 300);
				perPlayerRunnable.get(players.getUniqueId()).runTaskTimer(Sky.getInstance(), 20 * seconds, 20 * seconds);
			}
		}
	}
	
	public void start(UUID uuid) {
		if (!perPlayerRunnable.containsKey(uuid)) {
			perPlayerRunnable.put(uuid, new BukkitRunnable() {
				
				@Override
				public void run() {
					sendMessage(uuid);
				}
			});
			int seconds = Integer.valueOf(ConfigFile.get().contains("AutoMessageSettings.messages-delay") ? ConfigFile.get().getInt("AutoMessageSettings.messages-delay") : 300);
			perPlayerRunnable.get(uuid).runTaskTimer(Sky.getInstance(), 20 * seconds, 20 * seconds);
		}
	}
	
	public void stop() {
		try {
			for (Entry<UUID, BukkitRunnable> map : perPlayerRunnable.entrySet()) {
				perPlayerRunnable.get(map.getKey()).cancel();
			}
			perPlayerRunnable.clear();
			messageToSend.clear();
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "exception has occurred while trying to disable AutoMessage! Exception: " + e);
		}
	}
	
	public void stop(UUID uuid) {
		try {
			if (perPlayerRunnable.containsKey(uuid)) {
				perPlayerRunnable.get(uuid).cancel();
				perPlayerRunnable.remove(uuid);
				messageToSend.remove(uuid);
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "exception has occurred while trying to disable AutoMessage for " + ChatColor.YELLOW + uuid.toString() + ChatColor.RED + "! Exception: " + e);
		}
	}
	
	private void sendMessage(UUID uuid) {
		if (perPlayerRunnable.containsKey(uuid)) {
			if (MessagesFile.get().contains("Messages")) {
				Set<String> messages = MessagesFile.get().getConfigurationSection("Messages").getKeys(false);
				if ((ConfigFile.get().contains("AutoMessageSettings.send-type")) && (ConfigFile.get().getString("AutoMessageSettings.send-type").equalsIgnoreCase("random"))) {
					Random random = new Random();
					Player target = Bukkit.getPlayer(uuid);
					if (target != null) {
						int i = random.nextInt(messages.size());
						if (MessagesFile.get().contains("Messages." + i + ".message")) {
							for (String str : MessagesFile.get().getStringList("Messages." + i + ".message")) {
								target.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", target.getName()).replace("%playerDisplayName%", target.getDisplayName())));
							}
						}
						if ((MessagesFile.get().contains("Messages." + i + ".title")) && (MessagesFile.get().getString("Messages." + i + ".title").equalsIgnoreCase("true"))) {
							String title = "";
							String subtitle = "";
							int fadeIn = 20;
							int stay = 5;
							int fadeOut = 20;
							try {
								fadeIn = Integer.parseInt(MessagesFile.get().getString("Time").split(",")[0]);
								stay = Integer.parseInt(MessagesFile.get().getString("Time").split(",")[1]);
								fadeOut = Integer.parseInt(MessagesFile.get().getString("Time").split(",")[2]);
							} catch (NullPointerException e) {
								e.printStackTrace();
							}
							if ((MessagesFile.get().contains("Messages." + i + ".title-message")) && !(MessagesFile.get().getString("Messages." + i + ".title-message").equalsIgnoreCase("null"))) {
								title = ChatColor.translateAlternateColorCodes('&', MessagesFile.get().getString("Messages." + i + ".title-message").replace("%playerName%", target.getName()).replace("%playerDisplayName%", target.getDisplayName()));
							}
							if ((MessagesFile.get().contains("Messages." + i + ".subtitle-message")) && !(MessagesFile.get().getString("Messages." + i + ".subtitle-message").equalsIgnoreCase("null"))) {
								subtitle = ChatColor.translateAlternateColorCodes('&', MessagesFile.get().getString("Messages." + i + ".subtitle-message").replace("%playerName%", target.getName()).replace("%playerDisplayName%", target.getDisplayName()));
							}
							TitleAPI.clearTitle(target);
							TitleAPI.sendFullTitle(target, fadeIn, stay, fadeOut, title, subtitle);
						}
					}
				} else if ((ConfigFile.get().contains("AutoMessageSettings.send-type")) && (ConfigFile.get().getString("AutoMessageSettings.send-type").equalsIgnoreCase("order"))) {
					if (!messageToSend.containsKey(uuid)) {
						messageToSend.put(uuid, 0);
					}
					Player target = Bukkit.getPlayer(uuid);
					if (target != null) {
						int i = messageToSend.get(uuid);
						if (MessagesFile.get().contains("Messages." + i + ".message")) {
							for (String str : MessagesFile.get().getStringList("Messages." + i + ".message")) {
								target.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", target.getName()).replace("%playerDisplayName%", target.getDisplayName())));
							}
						}
						if ((MessagesFile.get().contains("Messages." + i + ".title")) && (MessagesFile.get().getString("Messages." + i + ".title").equalsIgnoreCase("true"))) {
							String title = "";
							String subtitle = "";
							int fadeIn = 20;
							int stay = 5;
							int fadeOut = 20;
							try {
								fadeIn = Integer.parseInt(MessagesFile.get().getString("Time").split(",")[0]);
								stay = Integer.parseInt(MessagesFile.get().getString("Time").split(",")[1]);
								fadeOut = Integer.parseInt(MessagesFile.get().getString("Time").split(",")[2]);
							} catch (NullPointerException e) {
								e.printStackTrace();
							}
							if ((MessagesFile.get().contains("Messages." + i + ".title-message")) && !(MessagesFile.get().getString("Messages." + i + ".title-message").equalsIgnoreCase("null"))) {
								title = ChatColor.translateAlternateColorCodes('&', MessagesFile.get().getString("Messages." + i + ".title-message").replace("%playerName%", target.getName()).replace("%playerDisplayName%", target.getDisplayName()));
							}
							if ((MessagesFile.get().contains("Messages." + i + ".subtitle-message")) && !(MessagesFile.get().getString("Messages." + i + ".subtitle-message").equalsIgnoreCase("null"))) {
								subtitle = ChatColor.translateAlternateColorCodes('&', MessagesFile.get().getString("Messages." + i + ".subtitle-message").replace("%playerName%", target.getName()).replace("%playerDisplayName%", target.getDisplayName()));
							}
							TitleAPI.clearTitle(target);
							TitleAPI.sendFullTitle(target, fadeIn, stay, fadeOut, title, subtitle);
						}
					}
					messageToSend.put(uuid, (messageToSend.get(uuid) + 1));
					if (messageToSend.get(uuid) >= messages.size()) {
						messageToSend.put(uuid, 0);
					}
				}
			}
		}
	}
	
	public boolean isAutoMessage(UUID uuid) {
		if (perPlayerRunnable.containsKey(uuid)) {
			return true;
		}
		return false;
	}
}