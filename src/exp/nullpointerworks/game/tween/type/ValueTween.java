package exp.nullpointerworks.game.tween.type;

import exp.nullpointerworks.game.tween.abstracts.TweenElement;

public class ValueTween extends TweenElement 
{
	private float value;
	
	public ValueTween(float value)
	{
		this.value=value;
	}
	
	@Override
	public void update(float time) 
	{
		super.setValue(value);
	}

	@Override
	public boolean isComplete() 
	{
		return true;
	}

	@Override
	public void reset() 
	{
		
	}
}
