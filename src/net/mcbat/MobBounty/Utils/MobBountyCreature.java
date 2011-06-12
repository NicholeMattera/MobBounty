package net.mcbat.MobBounty.Utils;

import net.minecraft.server.EntityWolf;

import org.bukkit.craftbukkit.entity.CraftWolf;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public enum MobBountyCreature {
	CHICKEN("Chicken"),
	COW("Cow"),
	CREEPER("Creeper"),
	ELECTRIFIED_CREEPER("ElectrifiedCreeper"),
	GHAST("Ghast"),
	GIANT("Giant"),
	MONSTER("Monster"),
	PIG("Pig"),
	PIG_ZOMBIE("PigZombie"),
	SELF_TAMED_WOLF("SelfTamedWolf"),
	SHEEP("Sheep"),
	SKELETON("Skeleton"),
	SLIME("Slime"),
	SPIDER("Spider"),
	SQUID("Squid"),
	TAMED_WOLF("TamedWolf"),
	WOLF("Wolf"),
	ZOMBIE("Zombie");
	
	private final String _name;

	private MobBountyCreature(final String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public static MobBountyCreature fromName(String name) {
		for (MobBountyCreature id : MobBountyCreature.values()) {
			if (id.name().equalsIgnoreCase(name))
				return id;
		}
		
		return null;
	}
	
	public static MobBountyCreature valueOf(Entity entity, String playerName) {
		if (entity instanceof Chicken)					return MobBountyCreature.CHICKEN;
		else if (entity instanceof Cow)					return MobBountyCreature.COW;
		else if (entity instanceof Creeper) {
			Creeper creeper = (Creeper) entity;
			
			if (creeper.isPowered())					return MobBountyCreature.ELECTRIFIED_CREEPER;
			else										return MobBountyCreature.CREEPER;
		}
		else if (entity instanceof Ghast)				return MobBountyCreature.GHAST;
		else if (entity instanceof Giant)				return MobBountyCreature.GIANT;
		else if (entity instanceof Pig)					return MobBountyCreature.PIG;
		else if (entity instanceof PigZombie)			return MobBountyCreature.PIG_ZOMBIE;
		else if (entity instanceof Sheep)				return MobBountyCreature.SHEEP;
		else if (entity instanceof Skeleton)			return MobBountyCreature.SKELETON;
		else if (entity instanceof Slime)				return MobBountyCreature.SLIME;
		else if (entity instanceof Spider)				return MobBountyCreature.SPIDER;
		else if (entity instanceof Squid)				return MobBountyCreature.SQUID;
		else if (entity instanceof Wolf) {
			CraftWolf wolf = (CraftWolf) entity;
			EntityWolf wolfEntity = wolf.getHandle();
			
			if (wolfEntity.x().equalsIgnoreCase(""))	return MobBountyCreature.WOLF;
			else if (wolfEntity.x().equalsIgnoreCase(playerName))
														return MobBountyCreature.SELF_TAMED_WOLF;
			else										return MobBountyCreature.TAMED_WOLF;
		}
		else if (entity instanceof Zombie)				return MobBountyCreature.ZOMBIE;
		else if (entity instanceof Monster)				return MobBountyCreature.MONSTER;
		
		return null;
	}	
}
