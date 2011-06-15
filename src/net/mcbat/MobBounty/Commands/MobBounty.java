package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.Utils.MobBountyCreature;
import net.mcbat.MobBounty.Utils.MobBountyConfFile;
import net.mcbat.MobBounty.Utils.MobBountyTime;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nijiko.permissions.Group;

public class MobBounty {
	private final net.mcbat.MobBounty.MobBounty _plugin;
	
	public MobBounty(net.mcbat.MobBounty.MobBounty plugin) {
		_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.permissions != null && _plugin.permissions.has((Player)sender, "mobbounty.commands.mb")) || (_plugin.permissions == null)) {
			Player player = (Player)sender;
			World world = player.getWorld();
			double multiplier = 1.0;
			String booleanTest = null;
			
			if ((_plugin.permissions != null && _plugin.permissions.has(player, "mobbounty.multipliers.environment")) || (_plugin.permissions == null)) {
				booleanTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "useEnvironmentMultiplier");
				if (booleanTest != null && (booleanTest.equalsIgnoreCase("true") || booleanTest.equalsIgnoreCase("yes") || booleanTest.equalsIgnoreCase("1"))) {
					String wrldEnv = (world.getEnvironment() == Environment.NORMAL)?"Normal":"Nether";
					String multiTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.MULTIPLIERS, "Environment."+wrldEnv);
				
					if (multiTest != null)
						multiplier *= Double.valueOf(multiTest);
				}
			}

			if ((_plugin.permissions != null && _plugin.permissions.has(player, "mobbounty.multipliers.time")) || (_plugin.permissions == null)) {
				booleanTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "useTimeMultiplier");
				if (booleanTest != null && (booleanTest.equalsIgnoreCase("true") || booleanTest.equalsIgnoreCase("yes") || booleanTest.equalsIgnoreCase("1"))) {
					String timeTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.MULTIPLIERS, "Time."+MobBountyTime.getTimeOfDay(world.getTime()).getName());
				
					if (timeTest != null)
						multiplier *= Double.valueOf(timeTest);
				}
			}

			if ((_plugin.permissions != null && _plugin.permissions.has(player, "mobbounty.multipliers.group"))) {
				booleanTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "useGroupMultiplier");
				if (booleanTest != null && (booleanTest.equalsIgnoreCase("true") || booleanTest.equalsIgnoreCase("yes") || booleanTest.equalsIgnoreCase("1"))) {
					String grpTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.MULTIPLIERS, "Groups."+world.getName()+"."+_plugin.permissions.getGroup(world.getName(), player.getName()));
					
					if (grpTest != null)
						multiplier *= Double.valueOf(grpTest);
				}
			}
			
			if ((_plugin.permissions != null && _plugin.permissions.has(player, "mobbounty.multipliers.world")) || (_plugin.permissions == null)) {
				booleanTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "useWorldMultiplier");
				if (booleanTest != null && (booleanTest.equalsIgnoreCase("true") || booleanTest.equalsIgnoreCase("yes") || booleanTest.equalsIgnoreCase("1"))) {
					String wrldTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.MULTIPLIERS, "Worlds."+world.getName());
				
					if (wrldTest != null)
						multiplier *= Double.valueOf(wrldTest);
				}
			}

			for (MobBountyCreature creature : MobBountyCreature.values()) {
				double reward = 0;
		
				String rewardTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.REWARDS, "Worlds."+world.getName()+"."+creature.getName());
				
				if (rewardTest == null)
					rewardTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.REWARDS, "Default."+creature.getName());

				if (rewardTest != null) {
					if (rewardTest.contains(":")) {
						String[] rewardRange = rewardTest.split(":");
						double from = 0;
						double to = 0;
						
						if (Double.valueOf(rewardRange[0]) > Double.valueOf(rewardRange[1])) {
							from = Double.valueOf(rewardRange[1]) * multiplier;
							to = Double.valueOf(rewardRange[0]) * multiplier;
						}
						else {
							from = Double.valueOf(rewardRange[0]) * multiplier;
							to = Double.valueOf(rewardRange[1]) * multiplier;
						}
						
						if (_plugin.method != null) {
							if (from > 0.0) {
								String message = _plugin.getLocaleManager().getString("MBRewardRange");
								if (message != null) {
									message = message.replace("%1", _plugin.method.format(from)).replace("%2", _plugin.method.format(to)).replace("%M", creature.getName());
									player.sendMessage(message);
								}
							}
							else if (from < 0.0) {
								String message = _plugin.getLocaleManager().getString("MBFineRange");
								if (message != null) {
									message = message.replace("%1", _plugin.method.format(from)).replace("%2", _plugin.method.format(to)).replace("%M", creature.getName());
									player.sendMessage(message);
								}
							}
						}
					}
					else {
						reward = Double.valueOf(rewardTest) * multiplier;

						if (_plugin.method != null) {
							if (reward > 0.0) {
								String message = _plugin.getLocaleManager().getString("MBReward");
								if (message != null) {
									message = message.replace("%A", _plugin.method.format(reward)).replace("%M", creature.getName());
									player.sendMessage(message);
								}
							}
							else if (reward < 0.0) {
								String message = _plugin.getLocaleManager().getString("MBFine");
								if (message != null) {
									message = message.replace("%A", _plugin.method.format(reward)).replace("%M", creature.getName());
									player.sendMessage(message);
								}
							}
						}
					}
				}
			}
		}
		else {
			String message = _plugin.getLocaleManager().getString("NoAccess");
			if (message != null) sender.sendMessage(message);
		}
		
		return true;
	}
}
