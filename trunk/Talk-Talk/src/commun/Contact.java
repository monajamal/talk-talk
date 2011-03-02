package commun;

public abstract class Contact {
	
	public static final int FRIEND = 1;
	public static final int GROUPE = 1;
	
	public abstract String getName();
	public abstract String getImg();
	public abstract int getType();
}