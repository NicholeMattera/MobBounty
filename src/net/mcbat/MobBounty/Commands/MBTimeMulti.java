package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;
import net.mcbat.MobBounty.Utils.MobBountyTime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBTimeMulti {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBTimeMulti(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbtm")) || (_plugin.permissions == null && sender.isOp())) {
			if (args.length == 2) {
				if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
					Double amount = Double.parseDouble(args[1]);
					MobBountyTime time = MobBountyTime.getTimeFromString(args[0]);
					
					if (time != null) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.MULTIPLIERS, "Time."+time.getName(), amount.toString());
						
						String message = _plugin.getLocaleManager().getString("MBGMChange");
						if (message != null) {
							message = message.replace("%T", time.getName()).replace("%A", amount.toString());
							sender.sendMessage(message);
						}
					}
					else 	this.commandUsage(sender, label);
				}
				else	this.commandUsage(sender, label);
			}
			else	this.commandUsage(sender, label);
		}
		else {
			String message = _plugin.getLocaleManager().getString("NoAccess");
			if (message != null) sender.sendMessage(message);
		}
		
		return true;
	}
	
	private void commandUsage(CommandSender sender, String command) {
		String message = _plugin.getLocaleManager().getString("MBTMUsage");
		if (message != null) {
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
		
		message = _plugin.getLocaleManager().getString("MBTMTimes");
		if (message != null)
			sender.sendMessage(message);
	}
}
