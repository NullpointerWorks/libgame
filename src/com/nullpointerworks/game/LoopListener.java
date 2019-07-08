/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.game;

/**
 * 
 */
public interface LoopListener 
{
	public void onInit();
	public void onUpdate(double dt);
	public void onRender(double l);
	public void onDispose();
}
