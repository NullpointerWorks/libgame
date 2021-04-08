/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.game.system;

import com.nullpointerworks.game.LoopListener;

/**
 * The GameElement class can be used in combination with a manager 
 * such as the {@code GameSystem} or game loops. This class implements
 * all methods specified in the {@code LoopListener} interface.
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 * @see LoopListener
 */
public abstract class GameElement implements LoopListener
{
	private GameSystem parent;
	private int elementID = -1;
	private boolean enabled = false;
	
	/**
	 * Invoked when this element is enabled.
	 * @since 1.0.0
	 */
	public void onEnable() {}
	
	/**
	 * Invoked when this element is disabled.
	 * @since 1.0.0
	 */
	public void onDisable() {}
	
	/**
	 * When changes in the GameSystem take place, this method is invoked 
	 * before any elements change their state.
	 * @since 1.0.0
	 */
	public void onTransition() {}
	
	//===================================
	
	/**
	 * Change the state of the game element to the desired state. When 
	 * its state has changed it will invoke the {@code onEnable()} or 
	 * {@code onDisable()} method. Set the state to {@code true} to enable 
	 * or {@code false} to disable.
	 * @param state - set {@code true} to enabled, {@code false} otherwise
	 * @since 1.0.0
	 */
	final void setEnable(boolean state)
	{
		onTransition();
		if (enabled ^ state)
		{
			enabled = state;
			if (enabled)
			{
				onEnable();
			}
			else
			{
				onDisable();
			}
		}
	}
	
	/**
	 * Returns {@code true} if the element is enabled, {@code false} otherwise.
	 * @return {@code true} if the element is enabled, {@code false} otherwise
	 * @since 1.0.0
	 */
	public final boolean isEnabled()
	{
		return enabled;
	}
	
	/**
	 * Returns the element identifier.
	 * @return the element identifier
	 * @since 1.0.0
	 */
	public final int getID() 
	{
		return elementID;
	}
	
	/**
	 * Returns the parent GameSystem.
	 * @return the parent GameSystem
	 * @since 1.0.0
	 */
	public final GameSystem getParent()
	{
		return parent;
	}
	
	/**
	 * Set the parent GameSyetem for this element.
	 * @param parent - the parent game system object
	 * @since 1.0.0
	 */
	protected final void setParent(GameSystem parent)
	{
		this.parent=parent;
	}
	
	/**
	 * Sets the element identifier.
	 * @param id - the identifier for this element
	 * @since 1.0.0
	 */
	protected final void setID(int id) 
	{
		elementID = id;
	}
}
