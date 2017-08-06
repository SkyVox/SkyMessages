package me.skyvox.smessages;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.skyvox.smessages.automessage.AutoMessageCmd;
import me.skyvox.smessages.automessage.AutoMessageManager;
import me.skyvox.smessages.commands.AutoMessageAdminCmd;
import me.skyvox.smessages.commands.ClearChatCmd;
import me.skyvox.smessages.commands.ShoutCmd;
import me.skyvox.smessages.commands.WarnCmd;
import me.skyvox.smessages.commands.WarningCmd;
import me.skyvox.smessages.files.ConfigFile;
import me.skyvox.smessages.files.MessagesFile;
import me.skyvox.smessages.listeners.CommandPreprocess;
import me.skyvox.smessages.listeners.GeneralListeners;
import me.skyvox.smessages.utils.Update;

public class Sky extends JavaPlugin {
	private static Sky sky;
	public static AutoMessageManager automessage;
	
	public String tag = ChatColor.YELLOW + "SkyMessages" + ChatColor.GRAY + "> ";
	public String lines = "---------------";
	public String insufficientPermission;
	
	@Override
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(lines);
		getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "Enabling " + tag.replace("> ", " ") + ChatColor.GRAY + "Version: " + ChatColor.GREEN + getDescription().getVersion() + ChatColor.GRAY + ".");
		
		sky = this;
		ConfigFile.setup();
		MessagesFile.setup();
		automessage = new AutoMessageManager();
		insufficientPermission = ConfigFile.get().contains("Insufficient-Permission") ? ChatColor.translateAlternateColorCodes('&', ConfigFile.get().getString("Insufficient-Permission")) : ChatColor.RED + "You do not have permission.";
		
		getServer().getPluginManager().registerEvents(new GeneralListeners(), this);
		getServer().getPluginManager().registerEvents(new CommandPreprocess(), this);
		
		getCommand("clearchat").setExecutor(new ClearChatCmd());
		getCommand("automessageadmin").setExecutor(new AutoMessageAdminCmd());
		getCommand("automessage").setExecutor(new AutoMessageCmd());
		if ((ConfigFile.get().contains("Shout.shout-command")) && (ConfigFile.get().getString("Shout.shout-command").equalsIgnoreCase("true"))) {
			getCommand("shout").setExecutor(new ShoutCmd());
		}
		if ((ConfigFile.get().contains("Warning.warning-command")) && (ConfigFile.get().getString("Warning.warning-command").equalsIgnoreCase("true"))) {
			getCommand("warning").setExecutor(new WarningCmd());
		}
		if ((ConfigFile.get().contains("Warn.warn-command")) && (ConfigFile.get().getString("Warn.warn-command").equalsIgnoreCase("true"))) {
			getCommand("warn").setExecutor(new WarnCmd());
		}
		
		if ((ConfigFile.get().contains("AutoMessageSettings.enable-when-server-start")) && (ConfigFile.get().getString("AutoMessageSettings.enable-when-server-start").equalsIgnoreCase("true"))) {
			automessage.start();
		}
		
		Update update = new Update(this, 45202);
		if (update.needsUpdate()) {
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "A new update is available, Click on this link to download the latest version: " + ChatColor.YELLOW + "https://www.spigotmc.org/resources/skymessages-all-about-messages.45202/history");
		} else {
			getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "This plugin is up to date! :D");
		}
		
		getServer().getConsoleSender().sendMessage(tag + ChatColor.GREEN + "Has been enabled!");
		getServer().getConsoleSender().sendMessage(lines);
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(lines);
		
		sky = null;
		automessage.stop();
		automessage = null;
		
		getServer().getConsoleSender().sendMessage(tag + ChatColor.GREEN + "Has been disabled!");
		getServer().getConsoleSender().sendMessage(lines);
	}
	
	public static Sky getInstance() {
		return sky;
	}
}