/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.game;

/** 
 * The Fixed loop is an extendible game loop class that provides between-update frame interpolation. It's best suites for heavy game logic and simulations. 
 * <br><br>
 * It provides fixed time stepping and it may update more frequent to compensate for lost time in the previous cycle. Though this can make it appear to run faster or slower at times(like in the ASAP game loop), this is solved by utilizing between-update frame interpolation, which enabled for precise stepping between each update. The disadvantage of this type of loop is that is can be CPU intensive on some machines. Applications using a fixed game loop will probably not run well on machines that experience frequent interruptions.
 * 
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 */
public abstract class Fixed implements Runnable, Loop
{
	private Thread thread;
	private double game_hertz;
	private int game_fps;
	private long ideal_render_time;
	private long ideal_update_time;
	private double inv_ideal_update_time;
	private double inv_game_hertz;
	private boolean running = true;
	
	@Override
	public void setTargetFPS(int fps) 
	{
		game_fps = fps;
		ideal_render_time = NANO / game_fps;
	}
	
	@Override
	public void setTargetHz(double hertz) 
	{
		game_hertz 				= hertz;
		inv_game_hertz 			= 1d / game_hertz;
		ideal_update_time 		= (long)((double)NANO * inv_game_hertz);
		inv_ideal_update_time 	= 1d / ideal_update_time;
	}
	
	@Override
	public void start()
	{
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop()
	{
		running = false;
	}

	@Override
	public void run() 
	{
		long nanotime_curr;
		long yield_step;
		long update_nanotime_prev;
		long render_nanotime_prev;
		double interpolation;
		
		onInit();
		update_nanotime_prev = System.nanoTime() - ideal_update_time;
		
		while (running)
		{
			/*
			 * get time stamp
			 * update logic while/if we are allowed.
			 * this will catch up on missed time, if it happens. 
			 * for example, some process on your OS halted the game for a moment.
			 */
			nanotime_curr = System.nanoTime();
			
			while( nanotime_curr - update_nanotime_prev > ideal_update_time)
			{
				onUpdate(inv_game_hertz);
				update_nanotime_prev += ideal_update_time;
			}
			
			/*
			 * calculate in-between frame interpolation. capped at 1(100%).
			 */
			interpolation = (nanotime_curr - update_nanotime_prev) * inv_ideal_update_time;
			interpolation = (interpolation>1d)? 1d: interpolation;
			
			/*
			 * render with interpolation
			 */
			onRender(interpolation);
			render_nanotime_prev = nanotime_curr; // System.nanoTime();
			
			/*
			 * if both updating and rendering have been performed recently, yield the thread.
			 */
			do
			{
				//Thread.yield(); // needs testing
				yield_step = System.nanoTime() - render_nanotime_prev;
			}
			while (yield_step < ideal_render_time && yield_step < ideal_update_time);
		}
		
		onDispose();
	}
}
