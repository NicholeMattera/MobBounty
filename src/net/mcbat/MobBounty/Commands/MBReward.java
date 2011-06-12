package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;
import net.mcbat.MobBounty.Utils.MobBountyCreature;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBReward {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBReward(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbr")) || (_plugin.permissions == null && sender.isOp())) {
			if (args.length == 2) {
				MobBountyCreature mob = MobBountyCreature.fromName(args[0]);

				if (mob != null) {
					if (args[1].matches("[-]?[0-9]+([.][0-9]+)?")) {
						Double amount = Double.parseDouble(args[1]);
						_plugin.getConfigManager().setProperty(MobBountyConfFile.REWARDS, "Default."+mob.getName(), amount.toString());
						
						String message = _plugin.getLocaleManager().getString("MBRChange");
						if (message != null) {
							message = message.replace("%M", mob.getName()).replace("%A", amount.toString());
							sender.sendMessage(message);
						}
					}
					else if (args[1].matches("[-]?[0-9]+([.][0-9]+)?[:][-]?[0-9]+([.][0-9]+)?")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.REWARDS, "Default."+mob.getName(), args[1]);
						
						String message = _plugin.getLocaleManager().getString("MBRChange");
						if (message != null) {
							message = message.replace("%M", mob.getName()).replace("%A", args[1]);
							sender.sendMessage(message);
						}
					}
					else	this.commandUsage(sender, label);
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
		String message = _plugin.getLocaleManager().getString("MBRUsage");
		if (message != null) {
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
		
		message = _plugin.getLocaleManager().getString("MBRMobs");
		if (message != null)
			sender.sendMessage(message);
	}
}
