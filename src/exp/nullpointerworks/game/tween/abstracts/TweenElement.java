package exp.nullpointerworks.game.tween.abstracts;

public abstract class TweenElement 
{
	private float value = 0f;
	
	public float getValue() 
	{
		return value;
	}
	
	public void setValue(float value) 
	{
		this.value = value;
	}
	
	protected float lerp(float start, float end, float lerp) 
	{
		return start - (start+end)*lerp;
	}

	protected float clamp(float l, float x, float h) 
	{
		if (x < l) return l;
		if (x > h) return h;
		return x;
	}
	
	// ===============================
	
	public abstract void update(float time);
	public abstract boolean isComplete();
	public abstract void reset();
}
