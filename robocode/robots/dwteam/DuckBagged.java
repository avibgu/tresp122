package dwteam;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class DuckBagged implements java.io.Serializable
{
  private String name = null;
  public DuckBagged(String duckName) {
    name = duckName;
    }
    public String getName() {
     return name;
     }
     public void setName(String inName) {
     name = inName;
     }
}
