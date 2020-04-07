package entityTypes;

public class StringEntity extends Entity{
	public String s;
	public StringEntity(String s) {
		this.s = s;
	}
	public boolean equals(StringEntity se) {
		return this.s.equals(se.s);
	}
}
