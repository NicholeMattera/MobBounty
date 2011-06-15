package net.mcbat.MobBounty;

import java.util.logging.Logger;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import net.mcbat.Register.payment.Method;

public class MobBounty extends JavaPlugin {
	private final Logger _logger = Logger.getLogger("Minecraft");
	
	private MobBountyConfigs _configManager;
	private MobBountyCommands _commandManager;
	private MobBountyLocale _localeManger;

	public PermissionHandler permissions = null;
	public Method method = null;

	@Override
	public void onEnable() {
		_logger.info("[MobBounty] v"+this.getDescription().getVersion()+" (Oxygen) enabled.");
		_logger.info("[MobBounty] Developed by: [Mattera, Steven (IchigoKyger)].");
		_logger.info("[MobBounty] Special Thanks to: nijikokun for the Register API.");
		
		_configManager = new MobBountyConfigs(this);
		_commandManager = new MobBountyCommands(this);
		_localeManger = new MobBountyLocale(this);
		
		(new MobBountyListeners(this)).registerListeners();
	}

	@Override
	public void onDisable() {
		_logger.info("[MobBounty] v"+this.getDescription().getVersion()+" (Oxygen) disabled.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return _commandManager.onCommand(sender, command, label, args);
	}

	public Logger getMinecraftLogger() {
		return _logger;
	}
	
	public MobBountyConfigs getConfigManager() {
		return _configManager;
	}
	
	public MobBountyLocale getLocaleManager() {
		return _localeManger;
	}
}
