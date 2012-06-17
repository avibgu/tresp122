package tresp122team.action;

public class BThreadAction {

	protected BThreadActionType	mType;
	protected int				mPriority;
	protected double			mValue;
	protected String			mMessage;

	public BThreadAction(BThreadActionType pType, int pPriority, double pValue) {
		this(pType, pPriority, pValue, "");
	}

	public BThreadAction(BThreadActionType pType, int pPriority,
			String pMessage) {
		this(pType, pPriority, 0.0, pMessage);
	}

	public BThreadAction(BThreadActionType pType, int pPriority, double pValue,
			String pMessage) {

		setType(pType);
		setPriority(pPriority);
		setValue(pValue);
		setMessage(pMessage);
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

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String pMessage) {
		mMessage = pMessage;
	}
}
