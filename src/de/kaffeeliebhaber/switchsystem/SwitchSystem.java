package de.kaffeeliebhaber.switchsystem;

import java.util.ArrayList;
import java.util.List;

public class SwitchSystem implements ISwitchSystem {

	public static SwitchSystem instance;
	private static final int STATES = 2000;
	
	private List<Boolean> switchList;
	
	static {
		
		if (instance == null) {
			instance = new SwitchSystem();
		}
	}
	
	private SwitchSystem() {
		init();
	}
	
	private void init() {
		switchList = new ArrayList<Boolean>();
		
		for (int i = 0; i < STATES; i++) {
			switchList.add(i, false);
		}
	}
	
	public boolean isActivated(int eventID) {
		boolean eventState = false;
		
		if (inRange(eventID)) {
			eventState = switchList.get(eventID);
		}
		
		return eventState;
	}

	private void setState(final int eventID, final boolean state) {
		if (inRange(eventID)) {
			switchList.set(eventID, state);
		}
	}
	
	public void activate(final int eventID) {
		setState(eventID, true);
	}
	
	public void deactivate(int eventID) {
		setState(eventID, false);
	}
	
	private boolean inRange(final int eventID) {
		return eventID >= 0 && eventID < switchList.size();
	}
}
