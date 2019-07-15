/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

/** 
 * Variable time stepping<br>
 * <br>
 * + Render and game logic speed capped for consistency on different machines.<br>
 * + High granularity, render updates after changes take place.<br>
 * + Provides delta time to provide equal update speeds on various machines.<br>
 * = No interpolation available.<br>
 * - Not good for games or simulations, like those using physics,
 *   which requires small time steps but don't need to be immediately rendered.<br>
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 */
public abstract class Variable implements Runnable, Loop 
{
	private Thread thread;
	private boolean running = true;
	private int target_update = 30; 
	private long ideal_time = NANO / target_update;
	private long nanotime_prev;

	// =================================================
	
	/**
	 * Set the target frames rendered per second
	 */
	@Override
	public void setTargetFPS(int fps) 
	{
		target_update = fps;
		ideal_time = NANO / target_update;
	}
	
	@Override
	public void setTargetHz(double hertz) 
	{
		
	}
	
	// =================================================
	
	@Override
	public void start()
	{
		thread = new Thread(this);
		thread.run();
	}
	
	/**
	 * Terminates the loop after the last render call.
	 */
	public void stop()
	{
		running = false;
	}

	// =================================================
	
	/**
	 * Start the program.
	 */
	@Override
	public void run()
	{
		onInit();
		
		ideal_time = NANO / target_update;
		nanotime_prev = System.nanoTime();
		long nanotime_curr;
		long nanotime_delta;
		long sleep;
		double timing;
		
		while (running)
		{
			nanotime_curr = System.nanoTime();
			nanotime_delta = nanotime_curr - nanotime_prev;
			nanotime_prev = nanotime_curr;
			timing = nanotime_delta * inv_NANO;
			onUpdate(timing);
			onRender(1.0);
			
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
		
		onDispose();
	}
	
	@Override
	public void onRender(double lerp) { }
}
