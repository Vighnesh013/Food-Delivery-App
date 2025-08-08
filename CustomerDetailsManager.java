package com.aurionpro.foodOrderingApp.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDetailsManager {

	public void createFile() throws IOException {
		File file = new File("CustomerDetails.txt");
		if (file.createNewFile()) {
			System.out.println("File created successfully\n");
		} else {
			System.out.println("File already exists");
		}

	}
	
	

	public void addCustomer(String name, int password) throws IOException {
		FileWriter write = new FileWriter("CustomerDetails.txt", true);

		write.append(new CustomerLoginCredentials(name, password));
		System.out.println("Customer Login Credentials Added Successfully!");
		write.close();
	}

	public boolean authenticateCustomer(String name, int password)
			throws NumberFormatException, IOException, FileNotFoundException {
		FileReader read = new FileReader("CustomerDetails.txt");
		BufferedReader read1 = new BufferedReader(read);
		String data;
		boolean check = false;
		boolean check1 = false;
		while ((data = read1.readLine()) != null) {
			String[] customer = data.split(",");

			if (customer[0].equalsIgnoreCase(name)) {

				check = true;

			}
			if (Integer.parseInt(customer[1]) == password) {
				check1 = true;
			}
			if (check && check1) {
				break;
			}

		}
		read.close();
		read1.close();
		if (!check) {
			System.out.println("UserName Does not Exists!");
			return false;

		}
		if (check && !check1) {
			System.out.println("Incorrect Password!");
			return false;
		}

		return true;
	}
	
	public List<String> getAllCustomerNames() throws IOException {
	    List<String> names = new ArrayList<>();
	    BufferedReader reader = new BufferedReader(new FileReader("CustomerDetails.txt"));
	    String line;
	    while ((line = reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        if (parts.length >= 1) {
	            names.add(parts[0]);
	        }
	    }
	    reader.close();
	    return names;
	}

}
