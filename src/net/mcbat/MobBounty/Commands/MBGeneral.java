package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBGeneral {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBGeneral(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbg")) || (_plugin.permissions == null && sender.isOp())) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("locale")) {
					_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "locale", args[1].toLowerCase());

					String message = _plugin.getLocaleManager().getString("MBGChange");
					if (message != null) {
						message = message.replace("%S", "locale").replace("%V", args[1].toLowerCase());
						sender.sendMessage(message);						
					}
				}
				else if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("1")) {
					if (args[0].equalsIgnoreCase("envmulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useEnvironmentMultiplier", "true");

						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useEnvironmentMultiplier").replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("timemulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useTimeMultiplier", "true");

						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useTimeMultiplier").replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("grpmulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useGroupMultiplier", "true");

						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useGroupMultiplier").replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("worldmulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useWorldMultiplier", "true");
						
						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useWorldMultiplier").replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("deprreturn")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useDepreciativeReturn", "true");

						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useDepreciativeReturn").replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else
						this.commandUsage(sender, label);
				}
				else if (args[1].equalsIgnoreCase("false") || args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("0")) {
					if (args[0].equalsIgnoreCase("envmulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useEnvironmentMultiplier", "false");
						
						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useEnvironmentMultiplier").replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("timemulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useTimeMultiplier", "false");
						
						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useTimeMultiplier").replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("grpmulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useGroupMultiplier", "false");
						
						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useGroupMultiplier").replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("worldmulti")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useWorldMultiplier", "false");

						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useWorldMultiplier").replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("deprreturn")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "useDepreciativeReturn", "false");

						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "useDepreciativeReturn").replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else
						this.commandUsage(sender, label);
				}
				else if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
					if (args[0].equalsIgnoreCase("deprreturnrate")) {
						_plugin.getConfigManager().setProperty(MobBountyConfFile.GENERAL, "depreciativeReturnRate", args[1]);
						
						String message = _plugin.getLocaleManager().getString("MBGChange");
						if (message != null) {
							message = message.replace("%S", "depreciativeReturnRate").replace("%V", args[1]);
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
		String message = _plugin.getLocaleManager().getString("MBGUsage");
		if (message != null) {
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
		
		message = _plugin.getLocaleManager().getString("MBGProperty");
		if (message != null)
			sender.sendMessage(message);
	}
}
