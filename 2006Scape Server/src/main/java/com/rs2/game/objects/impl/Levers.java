package com.rs2.game.objects.impl;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.players.Player;
import com.rs2.world.clip.ObjectDefinition;

/**
 * Levers
 * @author Andrew (Mr Extremez)
 */

public class Levers {
	
	/**
	 * ObjectX, ObjectY, PlayerX, PlayerY
	 */
	private final static int[][] LEVERS = {
		{3090, 3956, 2539, 4712}, {2539, 4712, 3090, 3956}, {2271, 4680, 3067, 10253}, 
		{3067, 10253, 2271, 4680}, {3153, 3923, 2561, 3311}, {2561, 3311, 3153, 3923}
	};

	public static void pullLever(final Player player, int objectType) {
		String objectName = ObjectDefinition.getObjectDef(objectType).name;
		for (final int[] element : LEVERS) {
			if (player.objectX == element[0] && player.objectY == element[1] && objectName.equalsIgnoreCase("Lever")) {
				if (System.currentTimeMillis() - player.leverDelay > 3750) {
					player.leverDelay = System.currentTimeMillis();
					player.stopPlayerPacket = true;
					player.getPacketSender().sendMessage("You pull the lever...");
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (player.wildLevel > 20) {
								player.getPlayerAssistant().startTeleport2(element[2], element[3], 0);
								container.stop();
							} else {
								player.getPlayerAssistant().startTeleport(element[2], element[3], 0, "modern");
								container.stop();
							}
						}
						@Override
						public void stop() {
							player.stopPlayerPacket = false;
						}
					}, 1);
				}
			}
		}
	}

}
