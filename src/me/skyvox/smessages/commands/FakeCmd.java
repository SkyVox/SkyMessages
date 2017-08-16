package me.skyvox.smessages.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.skyvox.smessages.Sky;
import me.skyvox.smessages.files.ConfigFile;
import me.skyvox.smessages.files.FakeFile;

public class FakeCmd implements CommandExecutor {
	public static ArrayList<UUID> slapfall = new ArrayList<UUID>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("skymessages.fake") || sender.hasPermission("skymessages.admin")) {
			if (args.length == 0) {
				help(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("op")) {
				if (sender.hasPermission("skymessages.fake.op") || sender.hasPermission("skymessages.admin")) {
					if (args.length >= 2) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to " + target.getName());
							target.sendMessage(FakeFile.get().contains("Fake.Messages.fake-op") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.fake-op").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.YELLOW + "You are now OP!");
						} else {
							sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
						}
						return true;
					} else {
						help(sender);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("deop")) {
				if (sender.hasPermission("skymessages.fake.deop") || sender.hasPermission("skymessages.admin")) {
					if (args.length >= 2) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to " + target.getName());
							target.sendMessage(FakeFile.get().contains("Fake.Messages.fake-deop") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.fake-deop").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.YELLOW + "You are no longer OP!");
						} else {
							sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
						}
						return true;
					} else {
						help(sender);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("enter")) {
				if (sender.hasPermission("skymessages.fake.join") || sender.hasPermission("skymessages.admin")) {
					if (args.length >= 2) {
						String target = args[1];
						sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", target).replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to " + target);
						Bukkit.broadcastMessage(FakeFile.get().contains("Fake.Messages.fake-join") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.fake-join").replace("%targetName%", target).replace("%playerName%", sender.getName())) : ChatColor.YELLOW + target + " joined!");
						return true;
					} else {
						help(sender);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("quit") || args[0].equalsIgnoreCase("leave")) {
				if (sender.hasPermission("skymessages.fake.quit") || sender.hasPermission("skymessages.admin")) {
					if (args.length >= 2) {
						String target = args[1];
						sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", target).replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to " + target);
						Bukkit.broadcastMessage(FakeFile.get().contains("Fake.Messages.fake-quit") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.fake-quit").replace("%targetName%", target).replace("%playerName%", sender.getName())) : ChatColor.YELLOW + target + " left!");
						return true;
					} else {
						help(sender);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("ban")) {
				if (sender.hasPermission("skymessages.fake.ban") || sender.hasPermission("skymessages.admin")) {
					if (args.length >= 2) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to " + target.getName());
							for (String str : FakeFile.get().getStringList("Fake.Messages.fake-ban")) {
								str = ChatColor.translateAlternateColorCodes('&', str.replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName()));
								target.sendMessage(str);
							}
						} else {
							sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
						}
						return true;
					} else {
						help(sender);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("gamemode") || args[0].equalsIgnoreCase("gm")) {
				if (sender.hasPermission("skymessages.fake.gamemode") || sender.hasPermission("skymessages.fake.gm") || sender.hasPermission("skymessages.admin")) {
					if (args.length >= 2) {
						Player target = Bukkit.getPlayer(args[1]);
						String gamemode = "CREATIVE";
						if (args.length >= 3) {
							gamemode = args[2];
						}
						if (target != null) {
							sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to " + target.getName());
							target.sendMessage(FakeFile.get().contains("Fake.Messages.fake-gm") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.fake-gm").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName()).replace("%gamemode%", gamemode)) : ChatColor.YELLOW + "Your game mode has been updated.");
						} else {
							sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
						}
						return true;
					} else {
						help(sender);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("slap")) {
				if (sender.hasPermission("skymessages.fake.slap") || sender.hasPermission("skymessages.admin")) {
					if (args.length >= 2) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to " + target.getName());
							target.sendMessage(FakeFile.get().contains("Fake.Messages.slap") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.slap").replace("%targetName%", target.getName()).replace("%targetDisplayname%", target.getDisplayName()).replace("%playerName%", sender.getName())) : ChatColor.YELLOW + "Wait.. I can fly?");
							target.setVelocity(target.getLocation().getDirection().multiply(5));
							target.setVelocity(new Vector(target.getVelocity().getX(), 10.0, target.getVelocity().getZ()));
							World world = target.getWorld();
							world.strikeLightningEffect(target.getLocation());
							if (!slapfall.contains(target.getUniqueId())) {
								slapfall.add(target.getUniqueId());
							}
						} else {
							sender.sendMessage(ConfigFile.get().contains("Offline-Player") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Offline-Player").replace("%playerName%", sender.getName())) : ChatColor.RED + "This player is not online");
						}
						return true;
					} else {
						help(sender);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
				if (sender.hasPermission("skymessages.fake.reload") || sender.hasPermission("skymessages.fake.rl") || sender.hasPermission("skymessages.admin")) {
					sender.sendMessage(FakeFile.get().contains("Fake.Messages.success-fake") ? ChatColor.translateAlternateColorCodes('&', FakeFile.get().getString("Fake.Messages.success-fake").replace("%targetName%", "everyone").replace("%playerName%", sender.getName())) : ChatColor.RED + "You send a fake action to everyone");
					for (String str : FakeFile.get().getStringList("Fake.Messages.fake-reload")) {
						str = ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", sender.getName()));
						Bukkit.broadcastMessage(str);
					}
				} else {
					sender.sendMessage(Sky.getInstance().insufficientPermission);
				}
				return true;
			}
		} else {
			sender.sendMessage(Sky.getInstance().insufficientPermission);
		}
		return true;
	}
	
	private void help(CommandSender sender) {
		for (String str : FakeFile.get().getStringList("Fake.help")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("%playerName%", sender.getName())));
		}
	}
}