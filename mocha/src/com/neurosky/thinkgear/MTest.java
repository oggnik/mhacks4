package com.neurosky.thinkgear;

public class MTest {
	public static void main(String[] args)
	{
		try {
	    	System.load("C:\\thinkgear.dll");
	    } catch (UnsatisfiedLinkError e) {
	      System.err.println("Native code library failed to load.\n" + e);
	      System.exit(1);
	    }
		
	}
}
