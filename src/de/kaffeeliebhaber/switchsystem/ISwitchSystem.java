package de.kaffeeliebhaber.switchsystem;

public interface ISwitchSystem {

	public abstract void activate(final int eventID);
	
	public abstract void deactivate(final int eventID);
	
	public abstract boolean isActivated(final int eventID);
}
