/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.game.event;

import com.nullpointerworks.game.LoopListener;

/** 
 * The Variable game loop provide high time stepping granularity. It's great for lightweight games that require accurate time stepping at high frame rates. 
 * <br><br>
 * Provides high time granularity since every single update is followed by a render call. Update and render logic is capped at a specific frame rate to make it consistent on different machines. The time between updates will likely vary depending on the power of the host machine. This variable time step will be passed as an argument when the {@code onUpdate(double)} method is invoked.<br>
 * Despite the advantages this game loop brings, it has no interpolation between frames. This makes it not well suites for heavy game logic and/or physics simulations that require high granularity every update, but do not requires to be rendered immediately after. 
 * 
 * <br><br>
 * The {@code onRender(double)} method does not have to be implemented for this loop to work. The update and render event call only occur once each cycle, so rendering can also be done at the end of the update method. Though not required, it's safe to override it.
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 */
public class VariableLoop implements Runnable 
{
	private final long NANO 		= 1_000_000_000;// 10^9
	private final long MICRO 		= 1_000_000;	// 10^6
	private final double inv_NANO 	= 1.0 / NANO;	// 10^-9
	private final double inv_MICRO 	= 1.0 / MICRO;	// 10^-6
	
	private Thread thread;
	private boolean running 	= true;
	private int target_update 	= 30; 
	private long ideal_time 	= NANO / target_update;
	private long nanotime_prev;
	private LoopListener e;
	
	/**
	 * Creates a new {@code VariableLoop} object that drives the provided {@code LoopListener} event methods.
	 * @param looplistener - the loop event listener to listen
	 * @param fps - the desired frames per second
	 * @since 1.0.0
	 */
	public VariableLoop(LoopListener looplistener, int fps)
	{
		e = looplistener;
		setTargetFPS(fps);
	}

	/**
	 * Set the desired frames per second.
	 * @param fps - the desired frames per second
	 * @since 1.0.0
	 */
	public void setTargetFPS(int fps) 
	{
		target_update = fps;
		ideal_time = NANO / target_update;
	}

	/**
	 * Start the game loop in a new thread.
	 * @since 1.0.0
	 */
	public void start()
	{
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops the main game loop thread.
	 * @since 1.0.0
	 */
	public void stop()
	{
		running = false;
	}
	
	@Override
	public void run()
	{
		long nanotime_curr;
		long nanotime_delta;
		long sleep;
		double timing;
		
		e.onInit();
		
		ideal_time = NANO / target_update;
		nanotime_prev = System.nanoTime();
		
		while (running)
		{
			nanotime_curr = System.nanoTime();
			nanotime_delta = nanotime_curr - nanotime_prev;
			nanotime_prev = nanotime_curr;
			timing = nanotime_delta * inv_NANO;
			e.onUpdate(timing);
			e.onRender(1.0);
			
			/*
			 * get nanotime till next cycle
			 * convert to milliseconds, since thats what sleep() wants
			 */
			sleep = (long)( ((nanotime_curr - System.nanoTime() + ideal_time) * inv_MICRO));
			try
			{
				Thread.sleep( (sleep<0)?0:sleep );
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		e.onDispose();
	}
}
