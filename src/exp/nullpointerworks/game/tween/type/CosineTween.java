package exp.nullpointerworks.game.tween.type;

public class CosineTween extends SineTween
{
	private float targetValue = 0f;
	private float deltaValue = 0f;
	private float startValue = 0f;
	
	private float angle = 0f;
	private float angleStep = 0f;
	
	public CosineTween(float value, float time)
	{
		super(value,time);
	}
	
	@Override
	public void update(float time) 
	{
		angle += (angleStep*time);
		float val = startValue + (1f - (float)Math.cos(angle * RADIAN))*deltaValue;
		val = clamp(startValue, val, targetValue);
		super.setValue(val);
	}
	
}
