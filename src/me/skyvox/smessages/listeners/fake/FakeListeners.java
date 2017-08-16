package me.skyvox.smessages.listeners.fake;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.skyvox.smessages.commands.FakeCmd;

public class FakeListeners implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause().equals(DamageCause.FALL)) {
				if (FakeCmd.slapfall.contains(player.getUniqueId())) {
					event.setCancelled(true);
					FakeCmd.slapfall.remove(player.getUniqueId());
				}
			}
		}
	}
}