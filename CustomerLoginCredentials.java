package com.aurionpro.foodOrderingApp.model;

public class CustomerLoginCredentials implements CharSequence {
	String name;
	int password;
	
	public CustomerLoginCredentials(String name, int password) {
		
		this.name = name;
		this.password = password;
	}

	@Override
	public String toString() {
	    return name + "," + password + System.lineSeparator();
	}


	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char charAt(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
