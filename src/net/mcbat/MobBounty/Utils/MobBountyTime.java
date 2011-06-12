package net.mcbat.MobBounty.Utils;

public enum MobBountyTime {
	DAY("Day", 0, 10000),
	SUNSET("Sunset", 10000, 1500),
	NIGHT("Night", 11500, 7000),
	SUNRISE("Sunrise", 18500, 1500);

	private final String _name;
	private final int _starts;
	private final int _length;

	private MobBountyTime(final String name, final int starts, final int length) {
		_name = name;
		_starts = starts;
		_length = length;
	}
	
	public String getName() {
		return _name;
	}
	
	public int getStartingTime() {
		return _starts;
	}

	public int getLength() {
		return _length;
	}
	
	public static MobBountyTime getTimeFromString(String time) {
		if (time.equalsIgnoreCase("day"))			return MobBountyTime.DAY;
		else if (time.equalsIgnoreCase("sunset"))	return MobBountyTime.SUNSET;
		else if (time.equalsIgnoreCase("night"))	return MobBountyTime.NIGHT;
		else if (time.equalsIgnoreCase("sunrise"))	return MobBountyTime.SUNRISE;
		
		return null;
	}
	
	public static MobBountyTime getTimeOfDay(long time) {
		if (time >= 0 && time < 10000)
			return MobBountyTime.DAY;
		else if (time >= 10000 && time < 11500)
			return MobBountyTime.SUNSET;
		else if (time >= 11500 && time < 18500)
			return MobBountyTime.NIGHT;
		
		return MobBountyTime.SUNRISE;
	}
}
