/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

import com.nullpointerworks.game.system.GameElement;
import com.nullpointerworks.game.system.GameSystem;

public class Game 
{
	private static GameSystem system = null;
	
	public static GameSystem getInstance()
	{
		if (system==null) system = new GameSystem();
		return system;
	}
	
	public static void addElement(GameElement element)
	{
		system.addElement(element);
	}
	
	public static void enableElements(int... enable)
	{
		system.enableElements(enable);
	}
	
	public static void update(double time)
	{
		system.update(time);
	}
	
	public static void render(double interpolation)
	{
		system.render(interpolation);
	}
	
}
