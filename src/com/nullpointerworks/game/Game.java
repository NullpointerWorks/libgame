/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.game;

import com.nullpointerworks.game.system.GameElement;
import com.nullpointerworks.game.system.GameSystem;

/**
 * Provides a singleton {@code GameSystem} to be accessed all over 
 * your project. The {@code GameSystem} can also be initialized as 
 * a class. This class gives the same functionality as one would 
 * with a locally instantiated object.
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 * @see GameSystem
 */
public class Game 
{
	private static GameSystem system = null;
	
	/**
	 * Returns the singleton instance.
	 * @return the singleton instance 
	 * @since 1.0.0
	 */
	public static GameSystem getInstance()
	{
		if (system==null) system = new GameSystem();
		return system;
	}
	
	/**
	 * Add a game element to the game system.
	 * @param element - the {@code GameElement} object to be added
	 * @param ID - the identifier for this element
	 * @return the array index of the appended element
	 * @since 1.0.0
	 */
	public void addElement(GameElement element, int ID)
	{
		system.addElement(element, ID);
	}
	
	/**
	 * Invokes the {@code onInit()} on all enabled elements.
	 * @since 1.0.0
	 */
	public static void initAll()
	{
		system.initAll();
	}
	
	/**
	 * Invokes the {@code onDispose()} on all enabled elements.
	 * This method clears all elements stored in the manager.
	 * @since 1.0.0
	 */
	public void disposeAll()
	{
		system.disposeAll();
	}
	
	/**
	 * Invokes the {@code onInit()} on the specified index.
	 * @param ID - the identifier of the game element
	 * @since 1.0.0
	 */
	public void initElement(int ID)
	{
		system.initElement(ID);
	}
	
	/**
	 * Enable a list of elements by passing their indices to be 
	 * enabled in the game system. All other elements not specified 
	 * will be disabled. This method invokes the {@code onEnable()} 
	 * and {@code onDisable()} on elements when they switch from one 
	 * state to another. All elements that do not change state will
	 * not be invoked on those methods. <br>
	 * <br>
	 * Changes in the enabling of elements will be applied before 
	 * the next rendering frame takes place in the system. When 
	 * they do, the {@code onTransition()} method is invoked on all 
	 * elements. 
	 * @param IDs - a list of identifiers to be enabled in the 
	 * game system
	 * @since 1.0.0
	 */
	public static void enableElements(int... IDs)
	{
		system.enableElements(IDs);
	}
	
	/**
	 * Invokes the {@code onUpdate(double)} on all enabled elements
	 * @param time - the time elapsed between updates in seconds
	 * @since 1.0.0
	 */
	public static void update(double time)
	{
		system.update(time);
	}

	/**
	 * Invokes the {@code onRender(double)} on all enabled elements
	 * @param interpolation - the render progression between updates 
	 * as a factor between 0 and 1
	 * @since 1.0.0
	 */
	public static void render(double interpolation)
	{
		system.render(interpolation);
	}
}
