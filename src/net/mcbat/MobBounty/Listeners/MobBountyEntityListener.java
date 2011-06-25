package net.mcbat.MobBounty.Listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.MobBountyConfFile;
import net.mcbat.MobBounty.Utils.MobBountyCreature;
import net.mcbat.MobBounty.Utils.MobBountyPlayerKillData;
import net.mcbat.MobBounty.Utils.MobBountyTime;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.PluginManager;

public class MobBountyEntityListener extends EntityListener {
	private final MobBounty _plugin;
	
	private Map<String, MobBountyPlayerKillData> _playerData;
	private Map<LivingEntity, Player> _deathNote;
	
	public MobBountyEntityListener(MobBounty plugin) {
		_plugin = plugin;
		
		_playerData = new HashMap<String, MobBountyPlayerKillData>();
		_deathNote = new HashMap<LivingEntity, Player>();
	}
	
	public void registerEvents() {
		PluginManager pm = _plugin.getServer().getPluginManager();

		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Priority.Monitor, _plugin);
		pm.registerEvent(Event.Type.ENTITY_DEATH, this, Priority.Monitor, _plugin);
	}
	
	public void onPlayerQuit(String name) {
		_playerData.remove(name);
	}
	
	public void onEntityDamage(EntityDamageEvent event) {
		if (_plugin.method == null || event.isCancelled())
			return;
		
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;

		LivingEntity entity = (LivingEntity) event.getEntity();
		MobBountyCreature creature = MobBountyCreature.valueOf(entity, "");
				
		if (creature == null)
			return;
		
		if (entity.getHealth() - event.getDamage() <= 0) {
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent subevent = (EntityDamageByEntityEvent)event;
				
				if (subevent.getDamager() instanceof Player) {
					Player damager = (Player) subevent.getDamager();

					if ((_plugin.permissions != null && _plugin.permissions.has(damager, "mobbounty.collect")) || (_plugin.permissions == null)) {
						if (_deathNote.get(entity) == null)
							_deathNote.put(entity, damager);
					}
				}
			}
			else if (event instanceof EntityDamageByProjectileEvent) {
				EntityDamageByProjectileEvent subevent = (EntityDamageByProjectileEvent)event;

				if (subevent.getDamager() instanceof Player) {
					Player damager = (Player) subevent.getDamager();
					
					if (_deathNote.get(entity) == null)
						_deathNote.put(entity, damager);
				}
			}
		}
	}
	
	public void onEntityDeath(EntityDeathEvent event) {
		if (_plugin.method == null)
			return;
		
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;
		
		LivingEntity mob = (LivingEntity) event.getEntity();
		Player player = _deathNote.get(mob);
		
		if (player != null) {
			World world = player.getWorld();
			MobBountyCreature creature = MobBountyCreature.valueOf(mob, player.getName());
				
			if (creature != null) {	
				double reward = 0;
				double multiplier = 1.0;
				String booleanTest = null;

				booleanTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "useMobSpawnerProtection");

				if (booleanTest != null && (booleanTest.equalsIgnoreCase("true") || booleanTest.equalsIgnoreCase("yes") || booleanTest.equalsIgnoreCase("1"))) {
					if (this.mobSpawnerNear(player.getLocation())) {
						_deathNote.remove(mob);
						return;
					}
				}
				
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
				
				String rewardTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.REWARDS, "Worlds."+world.getName()+"."+creature.getName());
				
				if (rewardTest == null)
					rewardTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.REWARDS, "Default."+creature.getName());

				if (rewardTest != null) {
					if (rewardTest.contains(":")) {
						String[] rewardRange = rewardTest.split(":");

						Random rand = new Random();
						int range;
						int loc;
						
						if (Double.valueOf(rewardRange[0]) > Double.valueOf(rewardRange[1])) {
							range = (int) ((Double.valueOf(rewardRange[0]) * 100) - (Double.valueOf(rewardRange[1]) * 100));
							loc =  (int) (Double.valueOf(rewardRange[1]) * 100);
						}
						else {
							range = (int) ((Double.valueOf(rewardRange[1]) * 100) - (Double.valueOf(rewardRange[0]) * 100));
							loc =  (int) (Double.valueOf(rewardRange[0]) * 100);
						}
						
						reward = (((double) (loc + rand.nextInt(range+1))) / 100) * multiplier;
					}
					else {
						reward = Double.valueOf(rewardTest) * multiplier;

						booleanTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "useDepreciativeReturn");
						if (booleanTest != null && (booleanTest.equalsIgnoreCase("true") || booleanTest.equalsIgnoreCase("yes") || booleanTest.equalsIgnoreCase("1"))) {
							MobBountyPlayerKillData playerData = _playerData.get(player.getName());
						
							if (playerData == null) {
								playerData = new MobBountyPlayerKillData();
							}
							else if (playerData.lastKill == creature) {
								String returnRate = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "depreciativeReturnRate");
								playerData.lastRewardPercentage -= Double.valueOf(returnRate);
								reward *= playerData.lastRewardPercentage;
							}
							else {
								playerData.lastRewardPercentage = 1;
							}

							playerData.lastKill = creature;
							_playerData.put(player.getName(), playerData);
						}
					}
				}
				
				if (_plugin.method != null && _plugin.method.hasAccount(player.getName())) {
					if (reward > 0.0) {
						_plugin.method.getAccount(player.getName()).add(reward);
						
						String message = _plugin.getLocaleManager().getString("Awarded");
						if (message != null) {
							message = message.replace("%A", _plugin.method.format(reward)).replace("%M", creature.getName());
							player.sendMessage(message);
						}
					}
					else if (reward < 0.0) {
						_plugin.method.getAccount(player.getName()).subtract(Math.abs(reward));
						
						String message = _plugin.getLocaleManager().getString("Fined");
						if (message != null) {
							message = message.replace("%A", _plugin.method.format(reward)).replace("%M", creature.getName());
							player.sendMessage(message);
						}
					}
				}
				
				_deathNote.remove(mob);
			}
		}
	}
	
	private boolean mobSpawnerNear(Location loc) {
		int radius;
	    World world;
	    int x1, x2, y1, y2, z1, z2;
	    
	    String radiusTest = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "mobSpawnerProtectionRadius");
	    
	    if (radiusTest != null && radiusTest.matches("[0-9]+")) {
	    	radius = Integer.valueOf(radiusTest);
		    world = loc.getWorld();
		    x1 = (int) (loc.getX());
		    y1 = (int) (loc.getY());
		    z1 = (int) (loc.getZ());

		    for (x2 = 0 - radius; x2 <= radius; x2++) {
		    	for (y2 = 0 - radius; y2 <= radius; y2++) {
		    		for (z2 = 0 - radius; z2 <= radius; z2++) {
		    			Block block = world.getBlockAt(x1 + x2, y1 + y2, z1 + z2);
		    			
		    			if (block.getType() == Material.MOB_SPAWNER)
		    				return true;
		    		}
		    	}
		    }
	    }
	    
	    return false;
	}
}
