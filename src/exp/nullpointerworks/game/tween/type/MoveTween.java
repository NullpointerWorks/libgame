package exp.nullpointerworks.game.tween.type;

import exp.nullpointerworks.game.tween.abstracts.TweenElement;

public class MoveTween extends TweenElement 
{
	private float targetValue = 0f;
	private float startValue = 0f;
	private float totalTime = 1f;
	
	private float currentTime = 0f;
	private float perc = 0f;
	
	
	public MoveTween(float value, float time)
	{
		targetValue = value;
		totalTime = 1f / time;
	}
	
	public void setValue(float value) 
	{
		super.setValue(value);
		startValue = value;
	}
	
	@Override
	public void update(float time) 
	{
		currentTime += time;
		perc = currentTime * totalTime; 
		perc = clamp(0f, perc, 1f);
		super.setValue( lerp(startValue, targetValue, perc) );
	}
	
	@Override
	public boolean isComplete() 
	{
		return perc >= 1.0f;
	}
	
	@Override
	public void reset() 
	{
		currentTime = 0f;
		perc = 0f;
	}

}
