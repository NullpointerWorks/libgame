package com.nullpointerworks.game.event;

import com.nullpointerworks.game.LoopListener;

public class VariableLoop implements Runnable 
{
	private final long NANO 		= 1_000_000_000;	// 10^9
	private final long MICRO 		= 1_000_000;	// 10^6
	private final double inv_NANO 	= 1.0 / NANO;	// 10^-9
	private final double inv_MICRO 	= 1.0 / MICRO;	// 10^-6
	
	private Thread thread;
	
	private boolean running 	= true;
	private int target_update 	= 30; 
	private long ideal_time 	= NANO / target_update;
	private long nanotime_prev;
	private LoopListener e;

	// =================================================
	
	public VariableLoop(LoopListener el, int fps)
	{
		e = el;
		setTargetFPS(fps);
	}
	
	// =================================================
	
	public void setTargetFPS(int fps) 
	{
		target_update = fps;
		ideal_time = NANO / target_update;
	}
	
	public void start()
	{
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Terminates the program.
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
