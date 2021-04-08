/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * An AWT {@code WindowListener} and {@code LoopListener} combination. 
 * This adapter may be added to a {@code Window} object for windowing 
 * event queues. 
 * @author Michiel Drost - Nullpointer Works
 * @since 1.0.0
 * @see WindowListener
 * @see LoopListener
 */
public abstract class LoopAdapter implements WindowListener, LoopListener
{
	@Override
	public void windowClosing(WindowEvent evt) 
	{
		onDispose();
		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
