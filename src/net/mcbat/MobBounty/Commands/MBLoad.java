package net.mcbat.MobBounty.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBLoad {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBLoad(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbl")) || (_plugin.permissions == null && sender.isOp())) {
			_plugin.getConfigManager().loadConfig();
			
			String message = _plugin.getLocaleManager().getString("MBLLoaded");
			if (message != null) sender.sendMessage(message);
		}
		else {
			String message = _plugin.getLocaleManager().getString("NoAccess");
			if (message != null) sender.sendMessage(message);
		}
		
		return true;
	}
}
