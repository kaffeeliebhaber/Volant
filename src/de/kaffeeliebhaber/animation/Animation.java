package de.kaffeeliebhaber.animation;

import java.util.List;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Animation {

	private String animationName;
	private List<IAnimationListener> animationListeners;
	private BufferedImage[] frames; 
	private int currentFrame; 
	private int frameDelay;
	private int currentFrameDelay;
	private int length; 
	private boolean stopped;
	private int loops;
	private int currentLoop;
	private boolean endsOfLastFrame;
	
	public Animation(final String animationName, final BufferedImage[] frames, int frameDelay) {
		
		animationListeners = new LinkedList<IAnimationListener>();
		
		this.frames = frames;
		this.length = frames.length;
		this.animationName = animationName;
		this.frameDelay = frameDelay;
		this.currentFrame = 0;
		this.stopped = true;
	}
	
	// Startet die Animation.
	public void start() {
        if (stopped && frames.length != 0) {
        	stopped = false;
        	this.fireAnimationStarted();
        }
	}
	
	// Stoppt die Animation
	public void stopp() {
		if (frames.length != 0) {
        	stopped = true;
        }
	}
	
	// Setzt die Animation zurück ohne sie dabei zu stoppen.
	public void restart() {
		if (frames.length != 0) {
        	stopped = false;
            currentFrame = 0;
            currentLoop = 0;
        }
	}
	
	// Definiert die Anzahl der Widerholungen der Animation.
	// Ein Wert < 0 bedeutet, dass die Widerholung unendlich oft widerholt wird.
	public void loops(int loops) {
		this.loops = loops;
	}
	
	// Gibt an, ob das Ende der Widerholungen erreicht wurde. 
	private boolean isLoopsEndReached() {
		return currentLoop == loops;
	}

	// Setzt die Animation gestoppet zurück.
	public void reset() {
		this.stopped = true;
        this.currentFrameDelay = 0;
        this.currentFrame = 0;
        this.currentLoop = 0;
	}
	
	public BufferedImage getSprite() {
		return frames[currentFrame];
	}
	
	// Definiert, dass die Animation im letzten Frame (Bild) endet, sollte die Anzahl der Widerholungen erreicht sein.
	public void setAnimationEndsOnLastFrame(boolean endsOfLastFrame) {
		this.endsOfLastFrame = endsOfLastFrame;
	}
	
	// Gibt der aktuellen Wert des Status zurück, ob die Animation im letzten Frame beendet wird.
	public boolean isAnimationEndsOnLastFrameActive() {
		return this.endsOfLastFrame;
	}
	
	// Aktualisiert die Animation.
	public void update(float timeSinceLastFrame) {
		
		if (!stopped && this.isLoopConditionValid()) {
			
			currentFrameDelay++;
			
			if (currentFrameDelay > frameDelay) {
				currentFrameDelay = 0;
				currentFrame++;
				
				if (currentFrame >= length) {

					currentLoop++;
					
					if (this.isLoopsEndReached())
					{
						if (this.isAnimationEndsOnLastFrameActive()) {
					        this.reset();
					        currentFrame = length - 1;
						} else {
							this.reset();
						}
						
						this.fireAnimationComplete();
						
					} else {
				        this.currentFrameDelay = 0;
				        this.currentFrame = 0;
				        
				        
					}
				}
			}
		}
	}
	
	// Prüft, ob die Bedingungen für den Widerholung erfüllt sind.
	private boolean isLoopConditionValid() {
		boolean isValid = true;
		
		if (loops > 0 && currentLoop >= loops) {
			isValid = false;
		}
		
		return isValid;
	}
	
	// Liefert den Namen der Animation.
	public String getName() {
		return this.animationName;
	}
	
	// Informiert alle Listener, dass die Animation gestartet wurde.
	private void fireAnimationStarted() {
		for (final IAnimationListener animationListener : animationListeners) {
			animationListener.animationStarted(new AnimationEvent(this));
		}
	}
	
	// Informiert alle Listener, dass die Animation beendet wurde.
	private void fireAnimationComplete() {
		for (final IAnimationListener animationListener : animationListeners) {
			animationListener.animationCompleted(new AnimationEvent(this));
		}
	}
	
	// Registers a <IAnimationListener>.
	public void addAnimationListener(final IAnimationListener animationListener) {
		animationListeners.add(animationListener);
	}
	
	// Remove a <IAnimationListener>.
	public void removeAnimationListener(final IAnimationListener animationListener) {
		animationListeners.remove(animationListener);
	}
}
