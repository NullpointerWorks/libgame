package com.nullpointerworks.game.system;

import com.nullpointerworks.game.LoopListener;

public abstract class GameElement implements LoopListener
{
	private GameSystem parent;
	private int elementID = -1;
	private boolean enabled = false;
	
	/*
	 * override for transition effects
	 */
	public void onEnable()
	{
		setEnable(true);
	}
	
	/*
	 * override for transition effects
	 */
	public void onDisable()
	{
		setEnable(false);
	}
	
	/*
	 * Changes in the game system trigger this method.
	 */
	public void onTransition()
	{
		
	}
	
	//===================================
	
	public final void setEnable(boolean enable)
	{
		enabled = enable;
	}
	
	public final boolean isEnabled()
	{
		return enabled;
	}
	
	public final int getID() 
	{
		return elementID;
	}
	
	// ==================================
	
	public final GameSystem getParent()
	{
		return parent;
	}
	
	protected final void setParent(GameSystem p)
	{
		parent = p;
	}
	
	protected final void setID(int id) 
	{
		elementID = id;
	}
}
