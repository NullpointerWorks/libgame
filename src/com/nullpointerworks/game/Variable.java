/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

/** 
 * The Variable game loop is an extendible class that provide high time stepping granularity. It's great for lightweight games that require accurate time stepping at high frame rates. 
 * <br><br>
 * Provides high time granularity since every single update is followed by a render call. Update and render logic is capped at a specific frame rate to make it consistent on different machines. The time between updates will likely vary depending on the power of the host machine. This variable time step will be passed as an argument when the {@code onUpdate(double)} method is invoked.<br>
 * Despite the advantages this game loop brings, it has no interpolation between frames. This makes it not well suites for heavy game logic and/or physics simulations that require high granularity every update, but do not requires to be rendered immediately after. 
 * 
 * <br><br>
 * This game loop has {@code setTargetHz(double)} disabled. To set the desired frame rate use {@code setTargetFPS(int)}. Also, the {@code onRender(double)} method has been implemented to be blank. The update and render event call only occur once each cycle, so rendering can also be done at the end of the update method. Though not required, it's safe to override it.
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
	
	@Override
	public void setTargetFPS(int fps) 
	{
		target_update = fps;
		ideal_time = NANO / target_update;
	}

	/**
	 * This method has been disabled for this {@code Loop} implementation. Use {@code setTargetFPS(int)} instead.
	 */
	@Override
	public void setTargetHz(double hertz) 
	{
		
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
