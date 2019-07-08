/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

public interface Loop extends LoopListener
{
	public final long	MILLI 	= 1000;			// 10^3
	public final long	MICRO 	= 1000_000;		// 10^6
	public final long	NANO 	= 1000_000_000;	// 10^9
	
	public final double inv_MILLI 	= 1d/1000d;			// 10^-3
	public final double inv_MICRO 	= 1d/1000000d;		// 10^-6
	public final double inv_NANO 	= 1d/1000000000d;	// 10^-9
	
	public void start();
	public void stop();
	public void setTargetFPS(int fps);
	public void setTargetHz(double hertz);
}
