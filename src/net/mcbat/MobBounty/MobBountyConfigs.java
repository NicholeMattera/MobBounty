package net.mcbat.MobBounty;

import java.io.File;
import java.util.HashMap;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.util.config.Configuration;

public class MobBountyConfigs {
	private final MobBounty _plugin;
	
	private final HashMap<MobBountyConfFile, Configuration> _configurations;
	
	public MobBountyConfigs(MobBounty plugin) {
		_plugin = plugin;
		
		_configurations = new HashMap<MobBountyConfFile, Configuration>();
		
		this.loadConfig();
	}
	
	public void loadConfig() {
		for (MobBountyConfFile file : MobBountyConfFile.values()) {			
			File confFile = new File(file.getPath());
			
			if (confFile.exists()) {
				if (_configurations.containsKey(file))
					_configurations.remove(file);
				
				Configuration conf = new Configuration(confFile);
				_configurations.put(file, conf);
				
				conf.load();
			}
			else {
				File parentFile = confFile.getParentFile();
				
				if (!parentFile.exists())
					parentFile.mkdirs();
				
				this.createConfig(file, confFile);
			}
		}
		
		_plugin.getMinecraftLogger().info("[MobBounty] Config loaded.");
	}
	
	public void saveConfig() {
		for (MobBountyConfFile file : MobBountyConfFile.values()) {
			if (_configurations.containsKey(file))
				_configurations.get(file).save();
		}
		
		_plugin.getMinecraftLogger().info("[MobBounty] Config saved.");
	}

	public String getProperty(MobBountyConfFile file, String path) {
		Configuration conf = _configurations.get(file);
		
		if (conf != null) {
			String prop = conf.getString(path, "NULL");
			
			if (!prop.equalsIgnoreCase("NULL"))
				return prop;
			else
				conf.removeProperty(path);
		}
		
		return null;		
	}

	public boolean setProperty(MobBountyConfFile file, String path, String value) {
		Configuration conf = _configurations.get(file);
		
		if (conf != null) {
			conf.setProperty(path, value);
			conf.save();
			return true;
		}
		
		return false;
	}
	
	public boolean removeProperty(MobBountyConfFile file, String path) {
		Configuration conf = _configurations.get(file);
		
		if (conf != null) {
			conf.removeProperty(path);
			return true;
		}
		
		return false;
	}
	
	private void createConfig(MobBountyConfFile config, File file) {
		switch (config) {
			case GENERAL:
				Configuration generalConf = new Configuration(file);
				generalConf.setProperty("locale", "en");
				generalConf.setProperty("useEnvironmentMultiplier", true);
				generalConf.setProperty("useGroupMultiplier", true);
				generalConf.setProperty("useTimeMultiplier", true);
				generalConf.setProperty("useWorldMultiplier", true);
				generalConf.setProperty("useMobSpawnerProtection", false);
				generalConf.setProperty("mobSpawnerProtectionRadius", new Integer(5));
				generalConf.setProperty("useDepreciativeReturn", false);
				generalConf.setProperty("depreciativeReturnRate", new Double(0.1));
				generalConf.save();
				
				_configurations.put(config, generalConf);
				break;

			case LOCALE:
				Configuration localeConf = new Configuration(file);
				localeConf.setProperty("en.Awarded", "&2You have been awarded &F%A &2for killing a &F%M&2.");
				localeConf.setProperty("en.Fined", "&4You have been fined &F%A &4for killing a &F%M&4.");
				localeConf.setProperty("en.NoAccess", "&CYou do not have access to that command.");
				localeConf.setProperty("en.MBReward", "&2%M : &F%A");
				localeConf.setProperty("en.MBRewardRange", "&2%M : &F%1 - %2");
				localeConf.setProperty("en.MBFine", "&4%M : &F%A");
				localeConf.setProperty("en.MBFineRange", "&4%M : &F%A - %2");
				localeConf.setProperty("en.MBGChange", "&2General setting &F%S &2has been changed to &F%V&2.");
				localeConf.setProperty("en.MBGUsage", "&CUsage: /%C [property] <amount>");
				localeConf.setProperty("en.MBGProperty", "&7Property: locale, envmulti, timemulti, grpmulti, worldmulti, deprreturn, deprreturnrate");
				localeConf.setProperty("en.MBRChange", "&2Default reward/fine for mob &F%M &2has been changed to &F%A&2.");
				localeConf.setProperty("en.MBRUsage", "&CUsage: /%C [mob] <amount>");
				localeConf.setProperty("en.MBRMobs", "&7Mob: Chicken, Cow, Creeper, ElectrifiedCreeper, Ghast, Giant, Monster, Pig, PigZombie, SelfTamedWolf, Sheep, Skeleton, Slime, Spider, Squid, TamedWolf, Wolf, Zombie");
				localeConf.setProperty("en.MBWRChange", "&2Reward for mob &F%M &2in world &F%W &2has been changed to &F%A&2.");
				localeConf.setProperty("en.MBWRReset", "&2Reward for mob &F%M &2in world &F%W &2has been reset to default.");
				localeConf.setProperty("en.MBWRUsage", "&CUsage: /%C [world] [mob] <amount>");
				localeConf.setProperty("en.MBWRWorlds", "&7World: %W");
				localeConf.setProperty("en.MBWRMobs", "&7Mob: Chicken, Cow, Creeper, ElectrifiedCreeper, Ghast, Giant, Monster, Pig, PigZombie, SelfTamedWolf, Sheep, Skeleton, Slime, Spider, Squid, TamedWolf, Wolf, Zombie");
				localeConf.setProperty("en.MBEMChange", "&2Multiplier for the &F%E &2environment has been changed to &F%A&2.");
				localeConf.setProperty("en.MBEMUsage", "&CUsage: /%C [environment] <amount>");
				localeConf.setProperty("en.MBEMEnvs", "&7Environment: Normal, Nether");
				localeConf.setProperty("en.MBTMChange", "&2Multiplier during the &F%T &2has been changed to &F%A&2.");
				localeConf.setProperty("en.MBTMUsage", "&CUsage: /%C [time] <amount>");
				localeConf.setProperty("en.MBTMTimes", "&7Time: Day, Sunset, Night, Sunrise");
				localeConf.setProperty("en.MBGMChange", "&2Multiplier for the group &F%G &2of &F%W &2has been changed to &F%A&2.");
				localeConf.setProperty("en.MBGMUsage", "&CUsage: /%C [world] <group> <amount>");
				localeConf.setProperty("en.MBGMWorlds", "&7World: %W");
				localeConf.setProperty("en.MBWMChange", "&2Multiplier for &F%W &2has been changed to &F%A&2.");
				localeConf.setProperty("en.MBWMUsage", "&CUsage: /%C [world] <amount>");
				localeConf.setProperty("en.MBWMWorlds", "&7World: %W");
				localeConf.setProperty("en.MBSSaved", "&2MobBounty config has been saved.");
				localeConf.setProperty("en.MBLLoaded", "&2MobBounty config has been loaded.");
				localeConf.save();
				
				_configurations.put(config, localeConf);
				break;

			case MULTIPLIERS:
				Configuration multiplierConfig = new Configuration(file);
				multiplierConfig.setProperty("Environment.Normal", 1.0);
				multiplierConfig.setProperty("Environment.Nether", 2.0);
				multiplierConfig.setProperty("Time.Day", 0.5);
				multiplierConfig.setProperty("Time.Sunset", 1.0);
				multiplierConfig.setProperty("Time.Night", 2.0);
				multiplierConfig.setProperty("Time.Sunrise", 1.0);
				multiplierConfig.save();
				
				_configurations.put(config, multiplierConfig);
				break;

			case REWARDS:
				Configuration rewardConfig = new Configuration(file);
				rewardConfig.setProperty("Default.Chicken", new Double(0.0));
				rewardConfig.setProperty("Default.Cow", new Double(0.0));
				rewardConfig.setProperty("Default.Creeper", new Double(57.0));
				rewardConfig.setProperty("Default.ElectrifiedCreeper", new Double(69.0));
				rewardConfig.setProperty("Default.Ghast", new Double(69.0));
				rewardConfig.setProperty("Default.Giant", new Double(85.0));
				rewardConfig.setProperty("Default.Monster", new Double(85.0));
				rewardConfig.setProperty("Default.Pig", new Double(0.0));
				rewardConfig.setProperty("Default.PigZombie", new Double(28.5));
				rewardConfig.setProperty("Default.SelfTamedWolf", new Double(0.0));
				rewardConfig.setProperty("Default.Sheep", new Double(0.0));
				rewardConfig.setProperty("Default.Skeleton", new Double(33.0));
				rewardConfig.setProperty("Default.Slime", new Double(0.0));
				rewardConfig.setProperty("Default.Spider", new Double(28.5));
				rewardConfig.setProperty("Default.Squid", new Double(0.0));
				rewardConfig.setProperty("Default.TamedWolf", new Double(21.0));
				rewardConfig.setProperty("Default.Wolf", new Double(28.5));
				rewardConfig.setProperty("Default.Zombie", new Double(21.0));
				rewardConfig.save();
				
				break;
		}
	}
}
