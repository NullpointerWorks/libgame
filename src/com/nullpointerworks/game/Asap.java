/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

/**
 * The ASAP loop is a minimalistic implementation of a game loop. 
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 */
public abstract class Asap implements Runnable, Loop
{
	private Thread thread;
	
	private boolean running 	= false;
	private double UPDATE_CAP 	= 1d / 60d;
	private int SLEEP_TIME 		= (int)(UPDATE_CAP*1000d);
	
	// =================================================
	
	@Override
	public void setTargetFPS(int fps) 
	{
		UPDATE_CAP	= 1d / (double)fps;
		SLEEP_TIME	= (int)(UPDATE_CAP*1000d) - 1;
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
	
	@Override
	public void stop()
	{
		running = false;
	}
	
	// =================================================
	
	@Override
	public void run()
	{
		running = true;
		boolean render = false;
		double d_Time = 0; // delta time
		double s_Time = 0; // spare unprocessed time
		double c_Time = 0; // current time
		double p_Time = System.nanoTime() * inv_NANO; // previous time
		
		onInit();
		
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
				onUpdate(UPDATE_CAP);
			}
			
			if (render)
			{
				onRender(1.0);
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
		
		onDispose();
	}
}
