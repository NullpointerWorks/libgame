package exp.nullpointerworks.game.tween.type;

import exp.nullpointerworks.game.tween.abstracts.TweenElement;

public class PauseTween extends TweenElement 
{
	private float endTime = 1f;
	private float currentTime = 0f;
	
	public PauseTween(float time)
	{
		endTime = time;
	}
	
	@Override
	public void update(float time) 
	{
		currentTime+=time;
	}

	@Override
	public boolean isComplete() 
	{
		return currentTime >= endTime;
	}

	@Override
	public void reset() 
	{
		currentTime = 0f;
	}
}
