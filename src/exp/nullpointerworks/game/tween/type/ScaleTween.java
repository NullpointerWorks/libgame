package exp.nullpointerworks.game.tween.type;

import exp.nullpointerworks.game.tween.abstracts.TweenElement;

public class ScaleTween extends TweenElement 
{
	private float currentTime = 0f;
	private float totalTime = 0f;
	private float scaling = 1f;
	private float perc = 0f;
	
	private float startVal = 0f;
	private float endVal = 0f;
	
	public ScaleTween(float factor, float totalTime)
	{
		scaling = factor;
		this.totalTime = 1f / totalTime;
	}
	
	public void setValue(float value) 
	{
		super.setValue(value);
		startVal = value;
		endVal = value * scaling;
	}
	
	@Override
	public void update(float time) 
	{
		currentTime += time;
		perc = currentTime * totalTime; 
		perc = clamp(0f, perc, 1f);
		super.setValue( lerp(startVal, endVal, perc) );
	}

	public boolean isComplete()
	{
		return perc >= 1.0f;
	}
	
	public void reset() 
	{
		currentTime = 0f;
		perc = 0f;
	}
}
