package net.mcbat.MobBounty;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class MobBountyCommands {
	private final net.mcbat.MobBounty.Commands.MobBounty _mobBounty;
	private final net.mcbat.MobBounty.Commands.MBEnvMulti _mbEnvMulti;
	private final net.mcbat.MobBounty.Commands.MBGeneral _mbGeneral;
	private final net.mcbat.MobBounty.Commands.MBGroupMulti _mbGroupMulti;
	private final net.mcbat.MobBounty.Commands.MBLoad _mbLoad;
	private final net.mcbat.MobBounty.Commands.MBReward _mbReward;
	private final net.mcbat.MobBounty.Commands.MBSave _mbSave;
	private final net.mcbat.MobBounty.Commands.MBTimeMulti _mbTimeMulti;
	private final net.mcbat.MobBounty.Commands.MBWorldMulti _mbWorldMulti;
	private final net.mcbat.MobBounty.Commands.MBWorldReward _mbWorldReward;

	public MobBountyCommands(MobBounty plugin) {
		_mobBounty = new net.mcbat.MobBounty.Commands.MobBounty(plugin);
		_mbEnvMulti = new net.mcbat.MobBounty.Commands.MBEnvMulti(plugin);
		_mbGeneral = new net.mcbat.MobBounty.Commands.MBGeneral(plugin);
		_mbGroupMulti = new net.mcbat.MobBounty.Commands.MBGroupMulti(plugin);
		_mbLoad = new net.mcbat.MobBounty.Commands.MBLoad(plugin);
		_mbReward = new net.mcbat.MobBounty.Commands.MBReward(plugin);
		_mbSave = new net.mcbat.MobBounty.Commands.MBSave(plugin);
		_mbTimeMulti = new net.mcbat.MobBounty.Commands.MBTimeMulti(plugin);
		_mbWorldMulti = new net.mcbat.MobBounty.Commands.MBWorldMulti(plugin);
		_mbWorldReward = new net.mcbat.MobBounty.Commands.MBWorldReward(plugin);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("Commands are designed for in-game only.");
			return true;
		}
		
		if (command.getName().equalsIgnoreCase("mobbounty")) {
			return _mobBounty.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbenvmulti")) {
			return _mbEnvMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbgeneral")) {
			return _mbGeneral.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbgroupmulti")) {
			return _mbGroupMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbload")) {
			return _mbLoad.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbreward")) {
			return _mbReward.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbsave")) {
			return _mbSave.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbtimemulti")) {
			return _mbTimeMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbworldmulti")) {
			return _mbWorldMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbworldreward")) {
			return _mbWorldReward.onCommand(sender, command, label, args);
		}
		
		
		return false;
	}
}
