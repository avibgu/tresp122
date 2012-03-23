package dwteam;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */


public class DroidPosition implements java.io.Serializable
{
	private double x = 0.0;
	private double y = 0.0;

	public DroidPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
