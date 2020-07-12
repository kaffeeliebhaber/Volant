package de.kaffeeliebhaber.questSystem;

import java.util.HashMap;
import java.util.Map;

// TODO: M�glichkeit bereitstellen, die Quest sp�ter �ber 'JSON' einladen.
public class QuestManager {

	/*
	 * SINGLETON
	 */
	private static QuestManager instance;
	
	static {
		if (instance == null) {
			instance = new QuestManager();
		}
	}
	
	private Map<Integer, Quest> quests;
	
	private QuestManager() {
		quests = new HashMap<Integer, Quest>();
	}
	
	public static QuestManager getInstance() {
		return instance;
	}
	
	public Quest get(final int questID) {
		Quest quest = null;
		
		if (quests.containsKey(questID)) {
			quest = quests.get(questID);
		}
		
		return quest;
	}
	
	public void put(final int questID, final Quest quest) {
		quests.put(questID, quest);
	}
	
	/*
	 * TODO: Folgende Methoden m�ssen noch implementiert werden
	 * > getOpenQuests()
	 * > getCompleteQuests()
	 */
	
}
