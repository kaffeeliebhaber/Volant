package de.kaffeeliebhaber.questSystem.quests;

import de.kaffeeliebhaber.questSystem.Quest;
import de.kaffeeliebhaber.questSystem.Reward;
import de.kaffeeliebhaber.switchsystem.SwitchSystem;

public class SearchFOX extends Quest {

	public SearchFOX(int questID) {
		super(questID, new int[] {1});
	}
	
	public void foxFounded() {
		SwitchSystem.instance.activate(1);
		checkQuestComplete();
	}

	@Override
	public Reward getReward() {
		System.out.println("Quest | Fuchs suchen abgeschlossen.");
		return null;
	}

}
