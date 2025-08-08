package com.aurionpro.foodOrderingApp.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.aurionpro.foodOrderingApp.model.AdminManager;
import com.aurionpro.foodOrderingApp.model.CustomerDetailsManager;
import com.aurionpro.foodOrderingApp.model.DeliveryPartnerManager;
import com.aurionpro.foodOrderingApp.model.MenuItem;
import com.aurionpro.foodOrderingApp.model.MenuManager;
import com.aurionpro.foodOrderingApp.model.OrderItem;

public class FoodOrderingTest {
	public static void main(String[] args) throws IOException {
		createMenuFileIfNotExists();
		Scanner scanner = new Scanner(System.in);
		CustomerDetailsManager customer = new CustomerDetailsManager();
		MenuManager menuManager = new MenuManager();

		while (true) {
			try {
				System.out.println("\nEnter : \n1 : Customer \n2 : Admin \n3 : Exit");
				int choice = scanner.nextInt();
				scanner.nextLine();

				if (choice == 1) {
					while (true) {
						try {
							System.out.println("Enter which operation \n1 : Login \n2 : Create New Account \n3 : Back");
							int check = scanner.nextInt();
							scanner.nextLine();

							if (check == 1) {
								System.out.print("Enter Customer Name: ");
								String name = scanner.nextLine().trim();
								while (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
									System.out.println("Enter valid name");
									name = scanner.nextLine().trim();
								}
								System.out.print("Enter four digit password: ");
								int password;
								while (true) {
									try {
										System.out.print("Enter a 4-digit password: ");
										password = scanner.nextInt();

										if (password >= 1000 && password <= 9999) {
											break;
										} else {
											System.out
													.println("Invalid password! Please enter a 4-digit password only!");
										}

									} catch (InputMismatchException e) {
										System.out.println("Invalid input! Please enter numbers only");
										scanner.nextLine();
									}
								}

								scanner.nextLine();

								if (customer.authenticateCustomer(name, password)) {
									System.out.println("Login Successful!\n");
									placeOrder(scanner, menuManager);
								} else {
									System.out.println("Login Failed!\n");
								}

							} else if (check == 2) {
								System.out.print("Enter your name: ");
								String name = scanner.nextLine().trim();
								while (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
									System.out.println("Enter valid name");
									name = scanner.nextLine().trim();
								}
								String mobile;
								while (true) {
									System.out.print("Enter your mobile number: ");
									mobile = scanner.next();

									if (mobile.matches("\\d{10}")) {
										break;
									} else {
										System.out.println("Invalid mobile number! Please enter a 10-digit number.");
									}
								}
								int otp;
								while (true) {
									System.out.print("Enter 4-digit OTP sent to " + mobile + ": ");
									try {
										otp = scanner.nextInt();
										if (otp >= 1000 && otp <= 9999) {
											break;
										} else {
											System.out.println("Invalid OTP! Please enter a 4-digit number.");
										}
									} catch (InputMismatchException e) {
										System.out.println("Invalid input! Please enter numbers only.");
										scanner.nextLine();
									}
								}
								int password;
								while (true) {
									try {
										System.out.print("Set a 4-digit password: ");
										password = scanner.nextInt();

										if (password >= 1000 && password <= 9999) {
											break;
										} else {
											System.out
													.println("Invalid password! Please enter a 4-digit password only!");
										}

									} catch (InputMismatchException e) {
										System.out.println("Invalid input! Please enter numbers only");
										scanner.nextLine();
									}
								}
								scanner.nextLine();
								customer.addCustomer(name, password);
							} else if (check == 3) {
								break;
							} else {
								System.out.println("Enter digits between (1-3)");
							}
						} catch (InputMismatchException e) {
							System.out.println("Enter only digits!");
							scanner.nextLine();
						}

					}

				} else if (choice == 2) {
					System.out.print("Enter admin username: ");
					String user = scanner.nextLine().trim();
					while (user.isEmpty() || !user.matches("[a-zA-Z ]+")) {
						System.out.println("Enter valid name");
						user = scanner.nextLine().trim();
					}
					System.out.print("Enter admin password: ");
					String pass;
					while (true) {
					    System.out.print("Enter a 4-digit password: ");
					    pass = scanner.next();

					    if (pass.matches("\\d{4}")) {
					        break;
					    } else {
					        System.out.println("Invalid password! Please enter a 4-digit numeric password only.");
					    }
					}


					AdminManager admin = new AdminManager(menuManager, customer);
					if (admin.authenticateAdmin(user, pass)) {
						admin.showAdminMenu(scanner);
					} else {
						System.out.println("Invalid admin credentials.");
					}
				} else if (choice == 3) {
					System.out.println("Thank you for using the app!");
					break;
				} else {
					System.out.println("Enter only digits between(1-3)");
				}
			} catch (InputMismatchException e) {
				System.out.println("Enter only digits!");
				scanner.nextLine();
			}

		}
	}

	public static void createMenuFileIfNotExists() {
		File file = new File("MenuItems.txt");
		if (!file.exists()) {
			try (FileWriter writer = new FileWriter(file)) {
				writer.write("Pizza,250\nBurger,150\nPasta,200\nFries,100\nCold Drink,60\n");
			} catch (IOException e) {
				System.out.println("Could not create menu file.");
			}
		}
	}

	private static void placeOrder(Scanner scanner, MenuManager menuManager) {
	    List<OrderItem> orderItems = new ArrayList<>();

	    while (true) {
	        menuManager.displayMenu();

	        while (true) {
	            System.out.print("\nEnter item number to add to order (0 to End): ");
	            int itemNumber = scanner.nextInt();
	            if (itemNumber == 0)
	                break;

	            if (itemNumber < 1 || itemNumber > menuManager.getMenuItems().size()) {
	                System.out.println("Invalid item number.");
	                continue;
	            }

	            MenuItem selectedItem = menuManager.getMenuItems().get(itemNumber - 1);
	            System.out.print("Enter quantity: ");
	            int quantity = scanner.nextInt();
	            orderItems.add(new OrderItem(selectedItem, quantity));
	        }

	        if (orderItems.isEmpty()) {
	        	while(true) {
	        		System.out.print("\nYou have not added any items. Do you want to add items? (1 for yes/ 0 for no): ");
		            int choice = scanner.nextInt();
		            if (choice == 1) {
		                continue;
		            } else if(choice == 0){
		                System.out.println("Order canceled. Returning to main menu.");
		                return;
		            } else {
		            	System.out.println("Enter only between (0-1)");
		            }
	        	}
	            
	        } else {
	            break;
	        }
	    }

	    double total = 0;
	    for (OrderItem item : orderItems) {
	        total += item.getTotalPrice();
	    }

	    double discount = total > 500 ? 100 : 0;
	    double finalTotal = total - discount;

	    int paymentChoice;
	    String paymentMode = "";

	    while (true) {
	    	try {
	    		 System.out.println("\nSelect payment mode: \n1 : Cash \n2 : UPI");
	 	        paymentChoice = scanner.nextInt();

	 	        if (paymentChoice == 1) {
	 	            paymentMode = "Cash";
	 	            break;
	 	        } else if (paymentChoice == 2) {
	 	            paymentMode = "UPI";
	 	            break;
	 	        } else {
	 	            System.out.println("Invalid choice! Please select 1 for Cash or 2 for UPI.");
	 	        }
	    	}catch(InputMismatchException e) {
	    		System.out.println("Enter only digits!");
	    		scanner.nextLine();
	    	}
	       
	    }

	    String deliveryPartner = DeliveryPartnerManager.getRandomPartner();

	    printInvoice(orderItems, total, discount, finalTotal, paymentMode, deliveryPartner);
	}


	private static void printInvoice(List<OrderItem> items, double total, double discount, double finalTotal,
			String paymentMode, String deliveryPartner) {
		System.out.println("\n======= INVOICE =======");
		System.out.println("Item\t\tQty\tPrice");

		for (OrderItem item : items) {
			System.out.printf("%-10s\t%d\t₹%.2f\n", item.getItem().getName(), item.getQuantity(), item.getTotalPrice());
		}

		System.out.println("----------------------------");
		System.out.printf("Subtotal:\t\t₹%.2f\n", total);
		System.out.printf("Discount:\t\t₹%.2f\n", discount);
		System.out.printf("Total:\t\t\t₹%.2f\n", finalTotal);
		System.out.println("Payment Mode:\t\t" + paymentMode);
		System.out.println("Delivery Partner:\t" + deliveryPartner);
		System.out.println("============================");
	}
}
