package exp.nullpointerworks.game.tween;

import java.util.ArrayList;
import java.util.List;

import exp.nullpointerworks.game.tween.abstracts.TweenElement;
import exp.nullpointerworks.game.tween.type.CosineTween;
import exp.nullpointerworks.game.tween.type.MoveTween;
import exp.nullpointerworks.game.tween.type.PauseTween;
import exp.nullpointerworks.game.tween.type.ScaleTween;
import exp.nullpointerworks.game.tween.type.SineTween;
import exp.nullpointerworks.game.tween.type.ValueTween;

public class Tween 
{
	/**
	 * scale the starting value with a factor within a certain amount of time
	 */
	public static TweenElement scale(float factor, float time) 
	{
		return new ScaleTween(factor,time);
	}
	
	/**
	 * move the current value to the given value in a certain amount of time
	 */
	public static TweenElement move(float value, float time) 
	{
		return new MoveTween(value,time);
	}
	
	/**
	 * pause value modification for a given time
	 */
	public static TweenElement pause(float time) 
	{
		return new PauseTween(time);
	}
	
	/**
	 * move to the given value using a sine function. starts strong, ends slow
	 */
	public static TweenElement sine(float value, float time) 
	{
		return new SineTween(value,time);
	}
	
	/**
	 * move to the given value using the cosine function. starts slow, ends strong
	 */
	public static TweenElement cosine(float value, float time) 
	{
		return new CosineTween(value,time);
	}
	
	/**
	 * hard set a value in the tween at this point.
	 */
	public static TweenElement value(float value) 
	{
		return new ValueTween(value);
	}
	
	// ====================================
	
	private List<TweenElement> tweens;
	private TweenElement currentTween = null;
	private int currentElement 	= 0;
	private final float startValue;
	private float currentValue 	= 0f;
	private float previousValue = 0f;
	private boolean repeat 		= false;
	private boolean enable 		= true;
	
	public Tween()
	{
		this(0f);
	}
	
	public Tween(float startValue)
	{
		this.startValue = startValue;
		tweens = new ArrayList<TweenElement>();
		clear();
		reset();
	}
	
	/**
	 * add a tween using Tween static functions.<br>
	 * example: myTween.add( Tween.move(value, time) );
	 */
	public void add(TweenElement element) 
	{
		tweens.add(element);
		currentTween = tweens.get(0);
	}
	
	/**
	 * reset the tween back to starting values
	 */
	public void reset() 
	{
		setValue(startValue);
		currentElement 	= 0;
		currentTween 	= tweens.get(0);
		currentTween.setValue(startValue);
	}
	
	/**
	 * delete all elements and values from this tween.
	 */
	public void clear()
	{
		tweens.clear();
		repeat 			= false;
		enable 			= true;
		currentElement 	= 0;
		setValue(startValue);
		currentTween 	= new ValueTween(startValue);
		tweens.add(currentTween);
	}
	
	public void setValue(float f) 
	{
		previousValue = currentValue = f;
	}
	
	/**
	 * return the currently available value from the tween.
	 */
	public float getValue() 
	{
		return currentValue;
	}
	
	public float getValue(float interpolation) 
	{
		return previousValue - (previousValue + currentValue)*interpolation;
	}
	
	public boolean isRepeat() {return repeat;}
	public boolean isEnable() {return enable;}
	public void setRepeat(boolean repeat) {this.repeat = repeat;}
	public void setEnable(boolean enable) {this.enable = enable;}
	
	/**
	 * update the current tween and calculate new values, switch to the new tween<br>
	 */
	public void update(float time)
	{
		if (!isEnable()) return;
		
		previousValue = currentValue;
		if (currentTween.isComplete())
		{
			currentValue = currentTween.getValue();
			currentTween.reset();
			currentElement++;
			if (currentElement < tweens.size())
			{
				currentTween = tweens.get(currentElement);
				currentTween.setValue(currentValue);
				currentTween.update(time);
			}
			else
			{
				reset();
				if (isRepeat())
				{
					currentTween.update(time);
				}
				else
				{
					setEnable(false);
				}
			}
		}
		else
		{
			currentTween.update(time);
			currentValue = currentTween.getValue();
		}
	}
	
}
