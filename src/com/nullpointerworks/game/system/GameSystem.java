package com.nullpointerworks.game.system;

import java.util.ArrayList;

public class GameSystem 
{
	private ArrayList<GameElement> elements;
	private ArrayList<int[]> enableOrders;
	
	public GameSystem()
	{
		elements = new ArrayList<GameElement>();
		enableOrders = new ArrayList<int[]>();
	}
	
	// ========================================================
	
	/**
	 * Add an element to the game system
	 */
	public void addElement(GameElement element)
	{
		element.setID(elements.size());
		element.setParent(this);
		elements.add(element);
	}
	
	/**
	 * Call the init() on all elements
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
	 * 
	 */
	public void initElement(int id)
	{
		if (id < 0) return;
		if (id >= elements.size()) return;
		GameElement el = elements.get(id);
		el.onInit();
	}
	
	/**
	 * pass a list of ID's that match the elements in the system
	 */
	public void enableElements(int... enable)
	{
		enableOrders.add(enable);
	}

	// ========================================================
	
	/**
	 * Call the update on all enabled elements
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
	 * Call the render on all enabled elements
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
	
	// ========================================================
	
	private boolean contains(int id, int... enable)
	{
		for (int j=0, k=enable.length; j<k; j++)
		{
			int index = enable[j];
			if (id == index) return true;
			
		}
		return false;
	}
	
	private void enable() 
	{
		int[] enable = enableOrders.get(0);
		enableOrders.remove(0);
		
		// set all to false
		for (int i=0, l=elements.size(); i<l; i++)
		{
			GameElement el = elements.get(i);
			el.onTransition();
			
			if (contains(el.getID(), enable))
			{
				if (!el.isEnabled())
				{
					el.onEnable();
				}
			}
			else
			{
				if (el.isEnabled())
				{
					el.onDisable();
				}
			}
		}
	}
}
