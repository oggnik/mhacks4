package main;

public class Main {
	static {
		try {
	    	System.load("C:\\thinkgear.dll");
	    } catch (UnsatisfiedLinkError e) {
	      System.err.println("Native code library failed to load.\n" + e);
	      System.exit(1);
	    }
	}
	public static void main(String[] args) {
		// Insert global logic here
	}
}
