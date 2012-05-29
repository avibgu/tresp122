package tresp122team.event;

public enum BThreadEventType {

	// These Events are sent from Level-2 BThreads to 'Kill' and 'StayAlive'
	WE_ARE_UNDER_ATTACK,
	WE_MADE_DAMAGE_TO_ENEMY,
	
	ENEMY_IS_WEAK,
	
	WE_ARE_WEAK,
	WE_ARE_STRONG,

	// These Events are sent from Level-1 BThreads to 'success'
	WE_ARE_GOING_TO_WIN,
	WE_ARE_GOING_TO_DIE,
}
