/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class LoopAdapter implements WindowListener, LoopListener
{
	public abstract void onInit();
	public abstract void onUpdate(double dt);
	public abstract void onRender(double l);
	public abstract void onDispose();
	
	public void windowClosing(WindowEvent evt) 
	{
		onDispose();
		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{}

	@Override
	public void windowClosed(WindowEvent e)
	{}

	@Override
	public void windowIconified(WindowEvent e)
	{}

	@Override
	public void windowDeiconified(WindowEvent e)
	{}

	@Override
	public void windowActivated(WindowEvent e)
	{}

	@Override
	public void windowDeactivated(WindowEvent e)
	{}
}
