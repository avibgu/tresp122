package tresp122.action;

public enum BThreadActionType {

	// Robocode API Actions
	FIRE,
	AHEAD,
	BACK,
	TURN_LEFT,
	TURN_RIGHT,
	TURN_GUN_LEFT,
	TURN_GUN_RIGHT,
	TURN_RADAR_LEFT,
	TURN_RADAR_RIGHT,
	STOP,
	
	// Our Level 1 Actions
	INCREASE_FIGHT_PRIORITY,
	DECREASE_FIGHT_PRIORITY,
	INCREASE_FIRE_POWER,
	DECREASE_FIRE_POWER,
	INCREASE_AVOID_BULLETS_PRIORITY,
	DECREASE_AVOID_BULLETS_PRIORITY,
	INCREASE_AVOID_COLLISIONS_PRIORITY,
	DECREASE_AVOID_COLLISIONS_PRIORITY,
//	INCREASE_MOVE_PRIORITY,
//	DECREASE_MOVE_PRIORITY,
//	INCREASE_KEEP_ENERGY_PRIORITY,
//	DECREASE_KEEP_ENERGY_PRIORITY,
//	INCREASE_TRACK_PRIORITY,
//	DECREASE_TRACK_PRIORITY,
	
	// Our Level 0 Actions
	INCREASE_KILL_PRIORITY,
	DECREASE_KILL_PRIORITY,
	INCREASE_STAY_ALIVE_PRIORITY,
	DECREASE_STAY_ALIVE_PRIORITY,
}
