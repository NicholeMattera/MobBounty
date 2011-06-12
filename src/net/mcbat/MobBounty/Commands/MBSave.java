package net.mcbat.MobBounty.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBSave {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBSave(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbs")) || (_plugin.permissions == null && sender.isOp())) {
			_plugin.getConfigManager().saveConfig();
			
			String message = _plugin.getLocaleManager().getString("MBSSaved");
			if (message != null) sender.sendMessage(message);
		}
		else {
			String message = _plugin.getLocaleManager().getString("NoAccess");
			if (message != null) sender.sendMessage(message);
		}
		
		return true;
	}
}
