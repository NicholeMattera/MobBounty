package net.mcbat.MobBounty.Commands;

import java.util.Iterator;
import java.util.List;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBWorldMulti {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBWorldMulti(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbwm")) || (_plugin.permissions == null && sender.isOp())) {
			if (args.length == 2) {
				if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
					World world = _plugin.getServer().getWorld(args[0]);
				
					if (world != null) {
						Double amount = Double.parseDouble(args[1]);
						
						_plugin.getConfigManager().setProperty(MobBountyConfFile.MULTIPLIERS, "Worlds."+world.getName(), amount.toString());

						String message = _plugin.getLocaleManager().getString("MBGMChange");
						if (message != null) {
							message = message.replace("%W", world.getName()).replace("%A", amount.toString());
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
		String message = _plugin.getLocaleManager().getString("MBWMUsage");
		if (message != null) {
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
		
		message = _plugin.getLocaleManager().getString("MBWMWorlds");
		if (message != null) {
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();
		
			String worldsStr = "";
		
			while (worldIterator.hasNext()) {
				World world = worldIterator.next();
			
				worldsStr += world.getName();
				worldsStr += " ";
			}
			
			message = message.replace("%W", worldsStr);
			sender.sendMessage(message);
		}
	}
}
