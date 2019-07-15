/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game.system;

import java.util.ArrayList;

/**
 * The GameSystem is a manager class for handling elements of an 
 * application. They can be initialized, enabled, disabled, updated 
 * and rendered. All of these states have an event method that will 
 * be invoked when triggered. This manager will accept {@code GameElement} 
 * objects that interface with the manager's event triggers.<br><br>
 * Particularly in games, it may be useful to enabled elements when
 * they need to appear, or disable some that need to be hidden(menus, 
 * inventories, etc.). 
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 * @see GameElement
 */
public class GameSystem 
{
	private ArrayList<GameElement> elements;
	private ArrayList<int[]> enableOrders;
	
	/**
	 * Creates a new GameSystem manager object with an initial capacity of ten.
	 * @since 1.0.0
	 */
	public GameSystem()
	{
		elements = new ArrayList<GameElement>();
		enableOrders = new ArrayList<int[]>();
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
		element.setID(ID);
		element.setParent(this);
		elements.add(element);
	}
	
	/**
	 * Invokes the {@code onInit()} on all enabled elements.
	 * @since 1.0.0
	 */
	public void initAll()
	{
		for (int i=0, l=elements.size(); i<l; i++)
		{
			GameElement el = elements.get(i);
			el.onInit();
		}
	}
	
	/**
	 * Invokes the {@code onDispose()} on all enabled elements.
	 * This method clears all elements stored in the manager.
	 * @since 1.0.0
	 */
	public void disposeAll()
	{
		for (int i=0, l=elements.size(); i<l; i++)
		{
			GameElement el = elements.get(i);
			el.onDispose();
			elements.set(i, null);
		}
		elements.clear();
	}
	
	/**
	 * Invokes the {@code onInit()} on the specified index.
	 * @param ID - the identifier of the game element
	 * @since 1.0.0
	 */
	public void initElement(int ID)
	{
		for (int i=0, l=elements.size(); i<l; i++)
		{
			GameElement el = elements.get(i);
			if (el.getID() == ID) 
				el.onInit();
		}
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
	public void enableElements(int... IDs)
	{
		enableOrders.add(IDs);
	}
	
	/**
	 * Invokes the {@code onUpdate(double)} on all enabled elements.
	 * @param time - the time elapsed between updates in seconds
	 * @since 1.0.0
	 */
	public void update(double time)
	{
		for (GameElement el : elements)
		{
			if (el.isEnabled())
				el.onUpdate(time);
		}
	}
	
	/**
	 * Invokes the {@code onRender(double)} on all enabled elements.
	 * @param interpolation - the render progression between updates as a factor between 0 and 1
	 * @since 1.0.0
	 */
	public void render(double interpolation)
	{
		while (enableOrders.size() > 0)
		{
			enable();
		}
		
		for (GameElement el : elements)
		{
			if (el.isEnabled())
				el.onRender(interpolation);
		}
	}
	
	private boolean contains(int[] list, int id)
	{
		for (int next : list)
		{
			if (id == next) return true;
		}
		return false;
	}
	
	private void enable()
	{
		int[] enableIDs = enableOrders.get(0);
		enableOrders.remove(0);
		
		for (int i=0, l=elements.size(); i<l; i++)
		{
			GameElement el = elements.get(i);
			if (contains(enableIDs, el.getID()))
			{
				el.setEnable(true);
			}
			else
			{
				el.setEnable(false);
			}
		}
	}
}
