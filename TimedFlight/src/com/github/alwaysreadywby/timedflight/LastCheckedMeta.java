package com.github.alwaysreadywby.timedflight;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class LastCheckedMeta implements MetadataValue{
	
	private long data;
	
	protected LastCheckedMeta(long time) {
		data=time;
	}

	@Override
	public boolean asBoolean() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte asByte() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double asDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float asFloat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int asInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long asLong() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public short asShort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String asString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plugin getOwningPlugin() {
		// TODO Auto-generated method stub
		return TimedFlight.getPlug();
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return data;
	}
	
}