package net.mcbat.MobBounty;

import net.mcbat.MobBounty.Utils.MobBountyConfFile;

public class MobBountyLocale {
	private final MobBounty _plugin;
	
	public MobBountyLocale(MobBounty plugin) {
		_plugin = plugin;
	}
		
	public String getString(String key) {
		String locale = _plugin.getConfigManager().getProperty(MobBountyConfFile.GENERAL, "locale");
		
		if (locale != null)	locale = locale.toLowerCase();
		else				locale = "en";
		
		String value = _plugin.getConfigManager().getProperty(MobBountyConfFile.LOCALE, locale+"."+key);
		
		if (value == null)
			value = _plugin.getConfigManager().getProperty(MobBountyConfFile.LOCALE, "en."+key);

		if (value != null)
			value = value.replace('&', '¤').replace("¤¤", "&");
		
		return value;
	}
}
