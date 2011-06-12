package net.mcbat.MobBounty.Commands;

import java.util.Iterator;
import java.util.List;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBGroupMulti {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBGroupMulti(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbgm")) || (_plugin.permissions == null && sender.isOp())) {
			if (args.length == 3) {
				World world = _plugin.getServer().getWorld(args[0]);

				if (world != null) {					
					if (args[2].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
						Double amount = Double.parseDouble(args[2]);
						_plugin.getConfigManager().setProperty(MobBountyConfFile.MULTIPLIERS, "Groups."+world.getName()+"."+args[1], amount.toString());
						
						String message = _plugin.getLocaleManager().getString("MBGMChange");
						if (message != null) {
							message = message.replace("%G", args[1]).replace("%W", world.getName()).replace("%A", amount.toString());
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
		String message = _plugin.getLocaleManager().getString("MBGMUsage");
		if (message != null) {
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
		
		message = _plugin.getLocaleManager().getString("MBGMWorlds");
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
