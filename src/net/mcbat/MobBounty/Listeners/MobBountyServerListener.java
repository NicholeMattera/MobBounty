package net.mcbat.MobBounty.Listeners;

import net.mcbat.MobBounty.MobBounty;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.nijikokun.bukkit.Permissions.Permissions;
import net.mcbat.Register.payment.Methods;

public class MobBountyServerListener extends ServerListener {
	private final MobBounty _plugin;
	private Methods _methods = null;
	
	public MobBountyServerListener(MobBounty plugin) {
		_plugin = plugin;
		_methods = new Methods();

		PluginManager pm = _plugin.getServer().getPluginManager();
		Plugin permissions = pm.getPlugin("Permissions");
			
		if (permissions != null && permissions.isEnabled()) {
			_plugin.permissions = ((Permissions) permissions).getHandler();
			_plugin.getMinecraftLogger().info("[MobBounty] Permission plugin was found ("+permissions.getDescription().getName()+" v"+permissions.getDescription().getVersion()+").");
		}
	}
	
	public void registerEvents() {
		PluginManager pm = _plugin.getServer().getPluginManager();

		pm.registerEvent(Event.Type.PLUGIN_ENABLE, this, Priority.Monitor, _plugin);
		pm.registerEvent(Event.Type.PLUGIN_DISABLE, this, Priority.Monitor, _plugin);
	}
	
	public void onPluginEnable(PluginEnableEvent event) {
		if (_plugin.permissions == null && event.getPlugin().getDescription().getName().equals("Permissions")) {
			_plugin.permissions = ((Permissions)event.getPlugin()).getHandler();
			_plugin.getMinecraftLogger().info("[MobBounty] Permission plugin was found ("+event.getPlugin().getDescription().getName()+" v"+event.getPlugin().getDescription().getVersion()+").");
		}
		
		if (_methods != null && !_methods.hasMethod()) {
			if (_methods.setMethod(event.getPlugin())) {
				_plugin.method = _methods.getMethod();
				_plugin.getMinecraftLogger().info("[MobBounty] Payment plugin was found ("+_plugin.method.getName()+" v"+_plugin.method.getVersion()+").");
			}
		}
	}
	
	public void onPluginDisable(PluginDisableEvent event) {
		if (_plugin.permissions != null && event.getPlugin().getDescription().getName().equals("Permissions")) {
			_plugin.permissions = null;
			_plugin.getMinecraftLogger().info("[MobBounty] Permission plugin was disabled.");
		}
		
		if (_methods != null && _methods.hasMethod()) {
			Boolean check = _methods.checkDisabled(event.getPlugin());
			
			if (check) {
				_plugin.method = null;
				_plugin.getMinecraftLogger().info("[MobBounty] Payment plugin was disabled.");
			}
		}
	}
}
