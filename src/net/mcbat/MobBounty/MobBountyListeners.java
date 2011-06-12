package net.mcbat.MobBounty;

import net.mcbat.MobBounty.Listeners.MobBountyEntityListener;
import net.mcbat.MobBounty.Listeners.MobBountyPlayerListener;
import net.mcbat.MobBounty.Listeners.MobBountyServerListener;

public class MobBountyListeners {
	private final MobBounty _plugin;

	private final MobBountyEntityListener _entityListener;
	private final MobBountyPlayerListener _playerListener;
	private final MobBountyServerListener _serverListener;
	
	public MobBountyListeners(MobBounty plugin) {
		_plugin = plugin;

		_entityListener = new MobBountyEntityListener(_plugin);
		_playerListener = new MobBountyPlayerListener(_plugin, this);
		_serverListener = new MobBountyServerListener(_plugin);
	}
	
	public void registerListeners() {
		_entityListener.registerEvents();
		_playerListener.registerEvents();
		_serverListener.registerEvents();
	}
	
	public MobBountyEntityListener getEntityListener() {
		return _entityListener;
	}
}
