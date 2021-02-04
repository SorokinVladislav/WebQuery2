package com.spring.spring;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.spring.spring.ConfigFile.*;

public class MDLPToken implements Runnable {

    public static String MDLPToken = "";
    public static String MDLPBalance = "";

    public static void getToken() throws IOException, InterruptedException {
        String token = "";
        URL url = new URL("https://mdlp.crpt.ru/api/v1/auth");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        String input = "{\"auth_type\": \"SIGNED_CODE\",\"user_id\": \"AA4EE451BF0B937883863B0CA28D53FD4A591325\"}";
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            JSONObject obj = new JSONObject(output);
            token = obj.getString("code");
        }

        conn.disconnect();
        br.close();


        try (FileWriter writer = new FileWriter(new File(getPathtoTokenFile()))) {

            writer.write(token);
            writer.flush();
        } catch (IOException ex) {
        }


        String command = getCommandLineText();


        Process p1 = Runtime.getRuntime().exec(command);

        Thread.sleep(500);

        String signature = "";

        try {
            File file = new File(getFileWithSignature());
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                signature += line.replace("\n", "");
                line = reader.readLine();
            }
            fr.close();
            reader.close();
        } catch (FileNotFoundException e) {
        }

        try {

            URL url2 = new URL("https://mdlp.crpt.ru/api/v1/token");
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setDoOutput(true);
            conn2.setRequestMethod("POST");
            conn2.setRequestProperty("Content-type", " application/json;charset=UTF-8");
            JSONObject tokenQuery = new JSONObject();
            tokenQuery.put("code", token);
            tokenQuery.put("signature", signature);
            // String input2 = "{\"code\": \"" + token + "\", \"signature\": " + "\""+ signature.replace("\n","") + "\"}";

            OutputStream os2 = conn2.getOutputStream();
            os2.write(tokenQuery.toString().getBytes());
            os2.flush();

            BufferedReader br2;
            try {
                br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
            } catch (Exception e) {
                br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
            }
            String output2;
            while ((output2 = br2.readLine()) != null) {
                JSONObject obj2 = new JSONObject(output2);
                MDLPToken = obj2.getString("token");
            }
            conn2.disconnect();
            br2.close();
        } catch (Exception e) {
        }
    }

    public void getBalance() throws IOException {
        URL url2 = new URL("https://mdlp.crpt.ru/api/v1/members/current/billing/info");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setDoOutput(true);
        conn2.setRequestMethod("GET");
        conn2.setRequestProperty("Accept", " application/json, text/plain, */*");
        conn2.setRequestProperty("Authorization", " token " + MDLPToken);


        BufferedReader br2;
        try {
            br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
        } catch (Exception e) {
            br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
        }
        String output2;
        while ((output2 = br2.readLine()) != null) {
            JSONObject obj2 = new JSONObject(output2);
            String bal = obj2.getJSONArray("accounts").getJSONObject(0).get("balance").toString();
            System.out.println(bal);
            MDLPBalance = "Баланc: " + bal.substring(0, bal.length() - 2);
        }
        conn2.disconnect();
        br2.close();

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20000);
                getToken();
                while (MDLPToken.equals("")) {
                    getToken();
                    Thread.sleep(10000);
                }
                System.out.println(MDLPToken);
                getBalance();
                Thread.sleep(1750000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
