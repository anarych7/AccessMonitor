package com.company.net.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pinger {
    private String command;

    public String ping(String ip) {
        this.command = "ping " + ip;
        return execute(command);
    }

    public String ping(String ip, int count) {
        this.command = "ping " + ip + " -n " + count;
        return execute(command);
    }

    public String getURLStatus(String url) {
        try {
            HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();
            System.out.println(http.getResponseMessage());
            return http.getResponseMessage();
        } catch (Exception e1) {
            e1.printStackTrace();
            return "Unknown";
        }
    }

    private String execute(String command) {
        String result = null;
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(command);
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                result += inputLine;
            }
            in.close();
        } catch (IOException e) {
            result = "IOException";
            System.out.println(e);
        } finally {
            return result;
        }
    }
}