package net.mcbat.MobBounty.Commands;

import java.util.Iterator;
import java.util.List;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;
import net.mcbat.MobBounty.Utils.MobBountyCreature;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBWorldReward {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MBWorldReward(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mbwr")) || (_plugin.permissions == null && sender.isOp())) {
			if (args.length == 3) {
				if (args[2].matches("[-]?[0-9]+([.][0-9]+)?")) {
					World world = _plugin.getServer().getWorld(args[0]);
			
					if (world != null) {
						MobBountyCreature mob = MobBountyCreature.fromName(args[1]);
			
						if (mob != null) {
							Double amount = Double.parseDouble(args[2]);
							
							_plugin.getConfigManager().setProperty(MobBountyConfFile.REWARDS, "Worlds."+world.getName()+"."+mob.getName(), amount.toString());

							String message = _plugin.getLocaleManager().getString("MBWRChange");
							if (message != null) {
								message = message.replace("%M", mob.getName()).replace("%W", world.getName()).replace("%A", amount.toString());
								sender.sendMessage(message);
							}
						}
						else	this.commandUsage(sender, label);
					}
					else	this.commandUsage(sender, label);
				}
				else if (args[2].matches("[-]?[0-9]+([.][0-9]+)?[:][-]?[0-9]+([.][0-9]+)?")) {
					World world = _plugin.getServer().getWorld(args[0]);
					
					if (world != null) {
						MobBountyCreature mob = MobBountyCreature.fromName(args[1]);
			
						if (mob != null) {
							_plugin.getConfigManager().setProperty(MobBountyConfFile.REWARDS, "Worlds."+world.getName()+"."+mob.getName(), args[2]);

							String message = _plugin.getLocaleManager().getString("MBWRChange");
							if (message != null) {
								message = message.replace("%M", mob.getName()).replace("%W", world.getName()).replace("%A", args[2]);
								sender.sendMessage(message);
							}

						}
					}
				}
				else if (args[2].equalsIgnoreCase("default")) {
					World world = _plugin.getServer().getWorld(args[0]);
					
					if (world != null) {
						MobBountyCreature mob = MobBountyCreature.fromName(args[1]);
			
						if (mob != null) {
							_plugin.getConfigManager().removeProperty(MobBountyConfFile.REWARDS, "Worlds."+world.getName()+"."+mob.getName());
							
							String message = _plugin.getLocaleManager().getString("MBWRReset");
							if (message != null) {
								message = message.replace("%M", mob.getName()).replace("%W", world.getName());
								sender.sendMessage(message);
							}
						}
						else	this.commandUsage(sender, label);
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
		String message = _plugin.getLocaleManager().getString("MBWRUsage");
		if (message != null) {
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
		
		message = _plugin.getLocaleManager().getString("MBWRWorlds");
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

		message = _plugin.getLocaleManager().getString("MBWRMobs");
		if (message != null)
			sender.sendMessage(message);
	}
}
