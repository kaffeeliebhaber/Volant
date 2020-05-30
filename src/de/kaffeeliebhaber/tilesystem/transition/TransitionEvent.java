package de.kaffeeliebhaber.tilesystem.transition;

public class TransitionEvent {

	private TransitionState state;
	
	public TransitionEvent(TransitionState state) {
		this.state = state;
	}
	
	public TransitionState getState() {
		return state;
	}
}
