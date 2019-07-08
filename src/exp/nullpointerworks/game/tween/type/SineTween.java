package exp.nullpointerworks.game.tween.type;

import exp.nullpointerworks.game.tween.abstracts.TweenElement;

public class SineTween extends TweenElement
{
	protected final float RADIAN = (float)(180.0 / Math.PI);
	
	private float targetValue = 0f;
	private float deltaValue = 0f;
	private float startValue = 0f;
	
	private float angle = 0f;
	private float angleStep = 0f;
	
	public SineTween(float value, float time)
	{
		targetValue = value;
		angleStep = 90f / time;
	}

	@Override
	public void setValue(float value)
	{
		super.setValue(value);
		startValue = value;
		deltaValue = targetValue - value;
	}
	
	@Override
	public boolean isComplete()
	{
		return (angle >= 90f);
	}
	
	@Override
	public void reset()
	{
		angle = 0f;
	}
	
	// ===================================
	
	@Override
	public void update(float time) 
	{
		angle += (angleStep*time);
		float val = startValue + (float)Math.sin(angle * RADIAN)*deltaValue;
		val = clamp(startValue, val, targetValue);
		super.setValue(val);
	}

}
