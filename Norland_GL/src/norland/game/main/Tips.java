package norland.game.main;

import norland.game.main.levels.Level02;

public class Tips {

	
	//----------------------------------------------------------------------------------
	// General
	
	// Level 1
	public static String objectivesTitle = "Welcome to Norland!";
	public static String objectives = "The compass surrouding your ship will point you towards your current objective.";

	// Level 1
	public static String movementTitle = "Traversing The Seas";
	public static String movement = "Tap anywhere in the ocean and your ship will move towards that location," +
			" but watch out for rocks, icebergs, and other hazardous obstacles.";
	
	// Level 2
	public static String healthTitle = "Surviving Enemy Attacks";
	public static String health = "Viking longships are strong, but not invincible. Your ship's health is located at the top of the screen." +
			" If your ship becomes heavily damaged your crew will perform repairs.";
	
	// Level 3
	public static String attacksTitle = "Attacking Your Enemies";
	public static String attacks = "Your ship has a powerful front-mounted ballista. Tap in front of your ship" +
			" to fire an arrow.\n Tap your ship to fire your cannons.\nIcebergs can be broken apart by both of your weapons. " +
			"First try killing "+ Level02.objectiveCompleteCondition1 + " icebergs with arrows.";	
	// Level 3
	public static String attacks2Title = "Attacking Your Enemies";
	public static String attacks2 = "Fabulous. Now try blasting "+ Level02.objectiveCompleteCondition2 + " icebergs with your cannons.";	
	
	// Level 3
	public static String attacks3Title = "Attacking Your Enemies";
	public static String attacks3 = "These waters have been cleared. We can now continue our journey home.";	
	
	//TODO Remove from Level 8
	public static String betaTitle = "Congratulations";
	public static String beta = "Thank you for playing Norland Beta. Please send us feedback using the form in the main menu. " +
			"We are pretty awesome and will probably listen to your suggestions!\nLevel 9 is a special challenge mission. Enjoy :D";	
	
	//----------------------------------------------------------------------------------
	// Upgrades
	public static String upgradesTitle = "Upgrades";
	public static String upgrades = "Earn more points by completing new levels in the campaign." 
			+ " You can read in-depth descriptions for each upgrade by tapping its title. Upgrade choices are not" +
			" permanent and can be modified on return to this menu.";
	// Arrows
	public static String arrowDamageTitle= "Arrow Damage";
	public static String arrowDamage="Increases the damage of each arrow.";
	
	public static String arrowFRTitle ="Arrow Fire Rate";
	public static String arrowFR = "Reduces the cooldown of your arrows.";
	
	public static String arrowUberTitle = "Uber Arrows";
	public static String arrowUber = "All your arrows become fire arrows, this increases their damage and makes them look awesome." +
			" This upgrade requires all previous arrow upgrades to be complete.";
	// Cannons
	public static String cannonDamageTitle = "Cannon Damage";
	public static String cannonDamage = "Increases the damage of each cannonball.";
	
	public static String cannonFRTitle = "Cannon Fire Rate";
	public static String cannonFR = "Reduces the cooldown of your cannons.";
	
	public static String cannonRangeTitle = "Cannon Range";
	public static String cannonRange = "Increases the distance each cannonball will travel.";
	
	public static String cannonSpreadTitle = "Cannon Spread";
	public static String cannonSpread = "Increases the number of cannons fired per volley.";
	
	public static String cannonUberTitle = "Uber Cannons";
	public static String cannonUber = "Fire two volleys at once. This upgrade requires all previous cannon upgrades to be complete.";	
	
	// Utility
	public static String shipSpeedTitle = "Ship Speed";
	public static String shipSpeed = "Increases the ship's movement speed.";
	
	public static String shipTRTitle = "Ship Turning Radius";
	public static String shipTR = "Allows the ship to turn around quicker.";
	
	public static String utilityUberTitle = "Uber Utility";
	public static String utilityUber = "Periodically drops mines behind the ship. This upgrade requires all previous utility upgrades to be complete.";
	
	// Defense
	public static String shipHResistTitle = "Hull Resist";
	public static String shipHResist = "Decreases the damage taken from collisions.";
	
	public static String shipFResistTitle = "Fire Resist";
	public static String shipFResist = "Decreases the damage taken from fire attacks.";
	
	public static String shipHDTitle = "Naval Ram";
	public static String shipHD = "Increases the damage dealt when the ship collides with enemies.";
	
	public static String defenseUberTitle = "Uber Defense";
	public static String defenseUber = "Allows the ship to survive collisions with rocks. This upgrade requires all previous defense upgrades to be complete.";	
	
	// Health
	public static String shipHealthTitle = "Maximum Heatlh";
	public static String shipHealth = "Increases the total health of the ship.";
	
	public static String shipHRegenTitle = "Health Regeneration";
	public static String shipHRegen = "Increases the recharge rate of the ship's health.";
		
	public static String healthUberTitle = "Uber Health";
	public static String healthUber = "Increases the maximum health and recharge rate of the ship. Also allows the ship to regenerate to 100% health." +
			" This upgrade requires all previous health upgrades to be complete.";
	
	
	//----------------------------------------------------------------------------------
	//Enemies
	public static String vodTitle = "The Vodianoi";
	public static String vod = "Evil men who live in the wrecks of old ships and prey on passerbys.\n" +
			"- Armaments: hand cannons\n" +
			"- Threat: low\n" +
			"- Strategies: Avoid attacking head-on, instead broadside them with cannons while dodging their attacks";
	
	public static String fosseTitle = "The Fosse Grim";
	public static String fosse = "Fosse Grim care nothing for troubles of the world, and instead spend their time" +
			" rocking out on their magical violins.\n" +
			"- Armaments: lovely and deadly music\n" +
			"- Threat: neutral\n" +
			"- Strategies: Green notes heal, but red notes hurt";
	
	public static String grindylowTitle = "The Grindylow";
	public static String grindylow = "Strange monsters which lurk just beneath the surface of the water, Grindylows will drag ships down into the sea.\n" +
			"- Armaments: hands\n" +
			"- Threat: medium\n" +
			"- Strategies: Arrows are not effective against grindylows. Only the blast of cannons will cause them to retreat back to the depths";
	
	public static String loreleiTitle = "The Lorelei";
	public static String lorelei = "Beautiful women with long, golden hair. Their voice has lured many sailors to their grave.\n" +
			"- Armaments: heal and strengthen nearby enemies, instant death if touched\n" +
			"- Threat: low\n" +
			"- Strategies: The Lorelei are dormant if there are no nearby enemies. Take them out while you have the chance";	
	
	public static String spriteTitle = "Water Sprites";
	public static String sprite = "Blue fairies, one of few creatures not yet corrupted by evil.\n" +
			"- Armaments: a powerful aura which increases the health regeneration and resist of nearby friendlies. A shield will " +
			"appear near your health bar if you are affected by the sprite's aura\n" +
			"- Threat: n/a\n" +
			"- Strategies: Water sprites hide themselves from foe, but can be killed by stray enemy fire";	
}
