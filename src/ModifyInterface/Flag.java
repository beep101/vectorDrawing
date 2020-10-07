package ModifyInterface;

public class Flag {
	private boolean val;
	public Flag() {
		val=false;
	}
	
	public void set(boolean val) {
		this.val=val;
	}
	
	public boolean get() {
		return val;
	}
}
