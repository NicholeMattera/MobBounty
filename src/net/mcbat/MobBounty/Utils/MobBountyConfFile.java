package net.mcbat.MobBounty.Utils;

public enum MobBountyConfFile {
	GENERAL("plugins/MobBounty/General.yml"),
	LOCALE("plugins/MobBounty/Locale.yml"),
	MULTIPLIERS("plugins/MobBounty/Multiplier.yml"),
	REWARDS("plugins/MobBounty/Reward.yml");
	
	private final String _path;
	
    private MobBountyConfFile(final String path) {
        _path = path;
    }
    
    public String getPath() {
    	return _path;
    }
}
