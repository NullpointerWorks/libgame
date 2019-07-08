/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

/** synchronization fixed time stepping<br><br>
 * 
 * + Consistent render time across different machines.<br>
 * + Has interpolation<br>
 * + Good for math heavy games.<br>
 * = No delta time. Each render has equal time spacing.<br>
 * - Not good on machines that experience frequent interruptions.<br>
 * - CPU intensive on some OS.<br>
 * 
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
	
	// =================================================
	
	/*
	 * Set the target renders per second.
	 */
	public void setTargetFPS(int fps) 
	{
		game_fps = fps;
		ideal_render_time = NANO / game_fps;
	}
	
	/*
	 * Set the target updates per second.
	 */
	public void setTargetHz(double hertz) 
	{
		game_hertz 				= hertz;
		inv_game_hertz 			= 1d / game_hertz;
		ideal_update_time 		= (long)((double)NANO * inv_game_hertz);
		inv_ideal_update_time 	= 1d / ideal_update_time;
	}
	
	// =================================================
	
	@Override
	public void start()
	{
		thread = new Thread(this);
		thread.run();
	}
	
	/*
	 * Terminates the program.
	 */
	public void stop()
	{
		running = false;
	}
	
	// =================================================
	
	/*
	 * Start the program.
	 */
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
