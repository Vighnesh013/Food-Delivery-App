package com.aurionpro.foodOrderingApp.model;

import java.util.Random;

public class DeliveryPartnerManager {
    private static final String[] partners = {"Zomato Partner", "Swiggy Partner"};

    public static String getRandomPartner() {
        Random random = new Random();
        return partners[random.nextInt(partners.length)];
    }
    
    public static String[] getAllPartners() {
        return partners;
    }

}

