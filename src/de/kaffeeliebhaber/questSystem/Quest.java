package de.kaffeeliebhaber.questSystem;

import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.switchsystem.SwitchSystem;

public abstract class Quest {

	private List<QuestListener> questListeners;
	
	private final int questID;
	private final int[] switchIDs;
	private boolean accepted;
	private boolean complete;
	
	public Quest(final int questID, final int[] switchIDs) {
		this.questID = questID;
		this.switchIDs = switchIDs;
		
		questListeners = new ArrayList<QuestListener>();
	}
	
	public int getID() {
		return questID;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	protected void checkQuestComplete() {
		if (SwitchSystem.instance.isFinished(switchIDs)) {
			complete();
		}
	}
	
	private void complete() {
		complete = true;
		fireQuestCompleteEvent();
	}
	
	public void accept() {
		accepted = true;
		fireQuestAcceptEvent();
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public void completeID(final int completeID) {
		if (containsID(completeID)) {
			SwitchSystem.instance.activate(completeID);
		}
	}
	
	public abstract Reward getReward();
	
	private boolean containsID(final int id) {
		boolean contains = false;
		
		final int size = switchIDs.length;
		
		for (int i = 0; i < size && !contains; i++) {
			
			if (switchIDs[i] == id) {
				contains = true;
			}
		}
		
		return contains;
	}
	
	private void fireQuestCompleteEvent() {
		questListeners.stream().forEach(q -> q.complete(questID, getReward()));
	}
	
	private void fireQuestAcceptEvent() {
		questListeners.stream().forEach(q -> q.accept(questID));
	}
	
	public void addQuestListener(final QuestListener l) {
		questListeners.add(l);
	}
	
	public void removeQuestListener(final QuestListener l) {
		questListeners.remove(l);
	}
	
}
