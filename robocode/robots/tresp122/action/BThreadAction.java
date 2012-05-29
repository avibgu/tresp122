package tresp122.action;

public class BThreadAction {

	protected BThreadActionType	mType;
	protected int				mPriority;
	protected double			mValue;
	
	public BThreadAction(BThreadActionType pType, int pPriority, double pValue) {
		
		setType(pType);
		setPriority(pPriority);
		setValue(pValue);
	}

	public BThreadActionType getType() {
		return mType;
	}

	public void setType(BThreadActionType mType) {
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
