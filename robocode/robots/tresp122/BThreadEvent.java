package tresp122;

public class BThreadEvent {

	protected BThreadEventType	mType;
	protected int				mPriority;
	protected double			mValue;
	
	public BThreadEvent(BThreadEventType pType, int pPriority, double pValue) {
		
		setType(pType);
		setPriority(pPriority);
		setValue(pValue);
	}

	public BThreadEventType getType() {
		return mType;
	}

	public void setType(BThreadEventType mType) {
		this.mType = mType;
	}

	public int getPriority() {
		return mPriority;
	}

	public void setPriority(int mPriority) {
		this.mPriority = mPriority;
	}

	public double getValue() {
		return mValue;
	}

	public void setValue(double mValue) {
		this.mValue = mValue;
	}
}
