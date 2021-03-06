/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.game.event;

import com.nullpointerworks.game.LoopListener;

/**
 * The ASAP(As Soon As Possible) loop is a minimalistic implementation of a game loop. The key advantage of this loop is it's simplicity with very little overhead time. It's great for general purpose applications or games that don't depend on timing accuracy. 
 * <br><br>
 * It has fixed time stepping and updates may occur more frequent to compensate for lost time. When an update occurs it will also call {@code onRender(double)} afterwards. This low granularity of update time can sometimes make it appear faster or slower on different machines. To increase time granularity, either increase the frame rate, or use a different type of game loop. This implementation does not provide any means of interpolating between rendering. This makes it not well suites for simulations. 
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 */
public class AsapLoop implements Runnable
{
	private final long NANO 		= 1_000_000_000;// 10^9
	private final double inv_NANO 	= 1.0 / NANO;	// 10^-9
	
	private Thread thread;
	private boolean running 	= false;
	private double UPDATE_CAP 	= 1d / 60d;
	private int SLEEP_TIME 		= (int)(UPDATE_CAP*1000d);
	private LoopListener e;
	
	/**
	 * Creates a new {@code AsapLoop} object that drives the provided {@code LoopListener} event methods.
	 * @param looplistener - the loop event listener to listen
	 * @param fps - the desired frames per second
	 * @since 1.0.0
	 */
	public AsapLoop(LoopListener looplistener, int fps)
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
		UPDATE_CAP	= 1d / (double)fps;
		SLEEP_TIME	= (int)(UPDATE_CAP*1000d) - 1;
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
		running = true;
		boolean render = false;
		double d_Time = 0; // delta time
		double s_Time = 0; // spare unprocessed time
		double c_Time = 0; // current time
		double p_Time = System.nanoTime() * inv_NANO; // previous time
		
		e.onInit();
		
		while(running)
		{
			render = false;
			c_Time = System.nanoTime() * inv_NANO;
			d_Time = c_Time - p_Time;
			p_Time = c_Time;
			
			s_Time += d_Time;
			
			while(s_Time >= UPDATE_CAP)
			{
				s_Time -= UPDATE_CAP;
				render = true;
				e.onUpdate(UPDATE_CAP);
			}
			
			if (render)
			{
				e.onRender(1.0);
			}
			else
			{
				try 
				{
					Thread.sleep(SLEEP_TIME);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		e.onDispose();
	}
}
