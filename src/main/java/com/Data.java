package com;

import com.Classes.Client;
import com.Classes.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

    public static List<Client> clients = new ArrayList<>();
    public static Map<Integer, List<Transaction>> transactions = new HashMap<>();
    public static Map<Integer, Client> iClients = new HashMap<>();

    public static Map<Integer, Integer> nips = new HashMap<>();





    public static int currentNIP;
}
