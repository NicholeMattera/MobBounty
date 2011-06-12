package net.mcbat.MobBounty.Listeners;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.MobBountyListeners;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MobBountyPlayerListener extends PlayerListener {
	private final MobBounty _plugin;
	private final MobBountyListeners _manager;
	
	public MobBountyPlayerListener(MobBounty plugin, MobBountyListeners manager) {
		_plugin = plugin;
		_manager = manager;
	}
	
	public void registerEvents() {
		_plugin.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_QUIT, this, Priority.Monitor, _plugin);
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		_manager.getEntityListener().onPlayerQuit(event.getPlayer().getName());
	}
}
