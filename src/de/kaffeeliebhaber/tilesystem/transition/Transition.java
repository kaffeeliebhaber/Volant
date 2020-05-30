package de.kaffeeliebhaber.tilesystem.transition;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.debug.Debug;

/**
 * 
 * @author User
 * Eine Transition dient zur Überblendung von einer in eine andere Szene. Dabei spielt es keine Rolle, welche Transition hierfür benutzt wird.
 */
public class Transition {

	private final List<ITransitionListener> transitionListener;
	private boolean active;
	private TransitionState currentState;
	
	private final int fadeIn;
	private final int duration;
	private final int fadeOut;
	
	private int ticks;
	private int alpha;
	
	private final Rectangle area;

	private Color transitionColor;
	
	// constructor
	public Transition(Rectangle area, int fadeIn, int duration, int fadeOut) {
		
		// transition area
		this.area = area;
		
		// time
		this.fadeIn = fadeIn;
		this.duration = duration;
		this.fadeOut = fadeOut;
		
		// create transition listener collection
		transitionListener = new ArrayList<ITransitionListener>();
		
		// stopp transition - inital state
		stopp();
	}
	
	public void setColor(final Color transitionColor) {
		this.transitionColor = transitionColor;
	}
	
	public void start() {
		active = true;
		setTransitionState(TransitionState.ACTIVE);
	}
	
	public void stopp() {
		active = false;
		ticks = 0;
		setTransitionState(TransitionState.INACTIVE);
	}
	
	public void end() {
		active = false;
		ticks = 0;
		setTransitionState(TransitionState.END);
	}
	
	public boolean isActive() {
		return active;
	}
	
	private void setTransitionState(TransitionState state) {
		if (currentState != state) {
			
			if (Debug.TRANSITION_SETTRANSITIONSTATE_SHOW_TRANSITION_STATE) {
				System.out.println("Transition status change to - " + state);
			}
			
			currentState = state;
			this.notifiyTransitionStateListener();
		}
	}
	
	public void update(float timeSinceLastFrame) {
		if (isActive()) {
			
			ticks++;
			
			if (ticks <= fadeIn) {
				// ----- FADE IN
				setTransitionState(TransitionState.FADE_IN);

				alpha = (int) (255 * (1.0 * ticks / fadeIn));

			} else if (ticks >= fadeIn && (ticks < (fadeIn + duration))) {
				
				// ------- MAX 
				setTransitionState(TransitionState.MAX);
				
			} else {
				//System.out.print("FADE->OUT, ");
				// ----- FADE OUT
				setTransitionState(TransitionState.FADE_OUT);
				alpha = (int) (255 - 255 * (1.0 * ticks - fadeIn - duration) / fadeOut);
				
				if (alpha < 0) {
					alpha = 0;
				}
			}

			
			if (isActive() && alpha <= 0) {
				
				// ----- INACTIVE
				end();
			}
		}
	}
	
	public TransitionState getTransitionState() {
		return currentState;
	}
	
	public void render(java.awt.Graphics g) {
		if (isActive()) {
			g.setColor(new Color(transitionColor.getRed(), transitionColor.getGreen(), transitionColor.getBlue(), alpha));
			g.fillRect(area.x, area.y, area.width, area.height);
		}
	}
	
	public void addTransitionListener(final ITransitionListener l) {
		transitionListener.add(l);
	}
	
	public void removeTransitionListener(final ITransitionListener l) {
		transitionListener.remove(l);
	}
	
	private final void notifiyTransitionStateListener() {
		transitionListener.forEach(l -> l.transitionStateChanged(new TransitionEvent(currentState)));
	}
}
