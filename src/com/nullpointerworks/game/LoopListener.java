/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.game;

/**
 * Event interface for game loops and systems.
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 */
public interface LoopListener 
{
	/**
	 * Invoked when a game loop or system has been initialized.
	 * @since 1.0.0
	 */
	public void onInit();
	
	/**
	 * Invoked when a game loop or system is forced to update.
	 * @param time - the time elapsed between updates in seconds
	 * @since 1.0.0
	 */
	public void onUpdate(double time);
	
	/**
	 * Invoked when a game loop or system is forced to render.
	 * @param interpolation - the render progression between updates as a factor between 0 and 1
	 * @since 1.0.0
	 */
	public void onRender(double interpolation);
	
	/**
	 * Invoked when a game loop or system is shutting down.
	 * @since 1.0.0
	 */
	public void onDispose();
}
