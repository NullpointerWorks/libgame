/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

/**
 * A game loop interface for extra methods to implement aside 
 * from those available in {@code LoopListener}. 
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 * @see LoopListener
 */
public interface Loop extends LoopListener
{
	public final long	MILLI 	= 1000;			// 10^3
	public final long	MICRO 	= 1000_000;		// 10^6
	public final long	NANO 	= 1000_000_000;	// 10^9
	
	public final double inv_MILLI 	= 1d/1000d;			// 10^-3
	public final double inv_MICRO 	= 1d/1000000d;		// 10^-6
	public final double inv_NANO 	= 1d/1000000000d;	// 10^-9
	
	/**
	 * Start the game loop in a new thread.
	 * @since 1.0.0
	 */
	public void start();
	
	/**
	 * Stops the main game loop thread.
	 * @since 1.0.0
	 */
	public void stop();
	
	/**
	 * Set the desired frames per second.
	 * @param fps - the desired frames per second
	 * @since 1.0.0
	 */
	public void setTargetFPS(int fps);
	
	/**
	 * Set the desired amount of updates per second.
	 * @param hertz - the desired amount of updates per second
	 * @since 1.0.0
	 */
	public void setTargetHz(double hertz);
}
