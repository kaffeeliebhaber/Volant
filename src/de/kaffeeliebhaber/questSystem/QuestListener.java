package de.kaffeeliebhaber.questSystem;

public interface QuestListener {

	void complete(final int questID, final Reward reward);

	void accept(final int questID);
}
