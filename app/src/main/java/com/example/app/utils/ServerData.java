package com.example.app.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerData {

    private static ArrayList<EventDetails> eventsInfo = new ArrayList<>();

    private static HashMap<String, String> userDetails = new HashMap<>();

    public ServerData(){

    }

    public static ArrayList<EventDetails> getEventsInfo() {
        return eventsInfo;
    }

    public static void addEventsInfo(EventDetails i){
        eventsInfo.add(i);
    }

    public static String getUserDetails(String key) {
        return userDetails.get(key);
    }

    public static void addToUserDetails(String key, String val){
        userDetails.put(key, val);
    }
}
