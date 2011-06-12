package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBEnvMulti {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBEnvMulti(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbem")) || (_plugin.permissions == null && sender.isOp())) {
			if (args.length == 2) {
				if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
					Double amount = Double.parseDouble(args[1]);
					
					if (args[0].equalsIgnoreCase("nether")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.MULTIPLIERS, "Environment.Nether", amount.toString());

						String message = _plugin.getLocaleManager().getString("MBEMChange");
						if (message != null) {
							message = message.replace("%E", "nether").replace("%V", amount.toString());
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("normal")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.MULTIPLIERS, "Environment.Normal", amount.toString());

						String message = _plugin.getLocaleManager().getString("MBEMChange");
						if (message != null) {
							message = message.replace("%E", "normal").replace("%V", amount.toString());
							sender.sendMessage(message);
						}
					}
					else
						this.commandUsage(sender, label);
				}
				else
					this.commandUsage(sender, label);
			}
			else
				this.commandUsage(sender, label);
		}
		else {
			String message = _plugin.getLocaleManager().getString("NoAccess");
			if (message != null) sender.sendMessage(message);
		}
		
		return true;
	}
	
	private void commandUsage(CommandSender sender, String command) {
		String message = _plugin.getLocaleManager().getString("MBEMUsage");
		if (message != null) {
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
		
		message = _plugin.getLocaleManager().getString("MBEMEnvs");
		if (message != null)
			sender.sendMessage(message);
	}
}
