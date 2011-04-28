package net.mcbat.MobBounty.Listeners;

import java.util.HashMap;
import java.util.Map;

import net.mcbat.MobBounty.Main;
import net.mcbat.MobBounty.Utils.Colors;
import net.mcbat.MobBounty.Utils.CreatureID;
import net.mcbat.MobBounty.Utils.Time;

import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.coelho.iConomy.system.Account;

public class DeathListener extends EntityListener {
	private final Main _plugin;
	private Map<LivingEntity, Player> _deathNote;
		
	public DeathListener(Main plugin) {
		_plugin = plugin;
		_deathNote = new HashMap<LivingEntity, Player>();
	}
	
	public void onEntityDamage(EntityDamageEvent event) {
		if (_plugin.iConomy == null)
			return;
		
		if (event.isCancelled())
			return;
		
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;
		
		LivingEntity entity = (LivingEntity) event.getEntity();
		
		if (CreatureID.fromEntity(entity) == null)
			return;
		
		if (entity.getHealth() - event.getDamage() <= 0) {
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent subevent = (EntityDamageByEntityEvent)event;
				
				if (subevent.getDamager() instanceof Player) {
					Player damager = (Player) subevent.getDamager();
					
					if (_deathNote.get(entity) == null)
						_deathNote.put(entity, damager);
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
		if (_plugin.iConomy == null)
			return;
		
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;
		
		LivingEntity mob = (LivingEntity) event.getEntity();
		Player player = _deathNote.get(mob);

		if (player != null) {
			CreatureID creature = CreatureID.fromEntity(mob);

			if (creature != null) {
				this.mobKilledByPlayer(mob.getWorld(), creature, player);
				_deathNote.remove(mob);
			}
		}
	}
	
	private void mobKilledByPlayer(World world, CreatureID creature, Player player) {
		if (_plugin.iConomy == null)
			return;
		
		if (_plugin.Permissions != null && !(_plugin.Permissions.has(player, "MobBounty.mb")))
			return;

		double multiplier = 1.0;
		if (_plugin.getConfig().getGeneralSetting("useEnvironmentMultiplier"))
			multiplier *= _plugin.getConfig().getEnvironmentMultiplier(world.getEnvironment());
		if (_plugin.getConfig().getGeneralSetting("useTimeMultiplier"))
			multiplier *= _plugin.getConfig().getTimeMultiplier(Time.getTimeOfDay(world.getTime()));			
		if (_plugin.getConfig().getGeneralSetting("useWorldMultiplier"))
			multiplier *= _plugin.getConfig().getWorldMultiplier(world.getName());

		double reward = _plugin.getConfig().getReward(world.getName(), creature)*multiplier;
		
		if (reward != 0.0) {					
			Account account = iConomy.getBank().getAccount(player.getName());
					
			if (reward > 0.0) {
				account.add(reward);
				player.sendMessage(Colors.DarkGreen+"You have been awarded "+Colors.White+reward+Colors.DarkGreen+" "+iConomy.getBank().getCurrency()+"(s) for killing a "+Colors.White+creature.getName());
			}
			else if (reward < 0.0) {
				account.subtract(Math.abs(reward));
				player.sendMessage(Colors.DarkRed+"You have been fined "+Colors.White+Math.abs(reward)+Colors.DarkRed+" "+iConomy.getBank().getCurrency()+"(s) for killing a "+Colors.White+creature.getName());
			}
		}
	}
}
