package com.spring.spring;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class MDLPQuery {

    public static File getRequest(String docID) throws IOException, InterruptedException {
        String token = MDLPToken.MDLPToken;

        URL url = new URL("https://mdlp.crpt.ru/documents/download/" + docID);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Cookie", "lk-auth-token=" + token);

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        } catch (Exception e) {
            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            MDLPToken.getToken();
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br.readLine()) != null) {
            returnString += output2;
        }

        conn.disconnect();
        br.close();

        File file = new File(docID+".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(returnString);
            writer.flush();
        } catch (IOException ex) {

        }

        return file;
    }


    public static File getResponse(String docID) throws IOException, InterruptedException {
        String token = MDLPToken.MDLPToken;

        URL url = new URL("https://mdlp.crpt.ru/documents/" + docID + "/ticket/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Cookie", "lk-auth-token=" + token);

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        } catch (Exception e) {
            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            MDLPToken.getToken();
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br.readLine()) != null) {
            returnString += output2;
        }

        conn.disconnect();
        br.close();

        File file = new File(docID+"_ticket.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(returnString);
            writer.flush();
        } catch (IOException ex) {

        }

        return file;

    }


    public static JSONArray suzRequest() throws IOException, InterruptedException {

        URL url = new URL("https://suzgrid.crpt.ru/webapi/v1/orders?limit=20&skip=0");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Cookie", "OMSSESSIONID=PHUuwt58N2NYDo4cVgKhrDF1kp0f_xctG-UwTHRx; omsId=09b6fe8d-7c7d-4bc0-99bc-a98402dfd2f5");

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        } catch (Exception e) {
            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            MDLPToken.getToken();
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br.readLine()) != null) {
            returnString += output2;
        }
        JSONObject object = new JSONObject(returnString);


        JSONArray array = object.getJSONArray("orderSummaryInfos");
        for (int i = 0; i < array.length(); i++) {

            Long lon = Long.parseLong(array.getJSONObject(i).get("createdTimestamp").toString());
            Date date = new Date(lon);
            array.getJSONObject(i).put("createdTimestamp", date);
        }


        conn.disconnect();
        br.close();

        return array;
    }

    public static String getDocIDfromSUZid(String suzId) throws IOException, InterruptedException {
        String token = MDLPToken.MDLPToken;
        URL url2 = new URL("https://mdlp.crpt.ru/api/v1/documents/outcome");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setDoOutput(true);
        conn2.setRequestMethod("POST");
        conn2.setRequestProperty("Content-type", " application/json;charset=UTF-8");
        conn2.setRequestProperty("Authorization", " token " + token);
//        JSONObject tokenQuery = new JSONObject();
//        tokenQuery.put("code", token);
//        tokenQuery.put("signature", signature);
        String input2 = "{\"start_from\":0,\"count\":20,\"filter\":{\"skzkm_report_id\":\"" + suzId + "\"}}";

        OutputStream os2 = conn2.getOutputStream();
        //   os2.write(tokenQuery.toString().getBytes());
        os2.write(input2.getBytes());
        os2.flush();

        BufferedReader br2;
        try {
            br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
        } catch (Exception e) {
            br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
            MDLPToken.getToken();
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br2.readLine()) != null) {
            returnString += output2;
        }
        conn2.disconnect();
        br2.close();
        return new JSONObject(returnString).getJSONArray("documents").getJSONObject(0).get("document_id").toString();
    }



    public static String getMDLPCount(String gtin, String batch) throws IOException, InterruptedException {
        String token = MDLPToken.MDLPToken;
        URL url2 = new URL("https://mdlp.crpt.ru/lk/v1/reestr/sgtin/filter");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setDoOutput(true);
        conn2.setRequestMethod("POST");
        conn2.setRequestProperty("Content-type", " application/json;charset=UTF-8");
        conn2.setRequestProperty("Authorization", " token " + token);
//        JSONObject tokenQuery = new JSONObject();
//        tokenQuery.put("code", token);
//        tokenQuery.put("signature", signature);
        String input2 = "{\"start_from\":0,\"count\":20,\"sort\":\"NO_SORT\",\"filter\":{\"gtin\":\""+gtin+"\",\"batch\":\""+batch+"\",\"status\":[\"marked\"]}}";

        OutputStream os2 = conn2.getOutputStream();
        //   os2.write(tokenQuery.toString().getBytes());
        os2.write(input2.getBytes());
        os2.flush();

        BufferedReader br2;
        try {
            br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
        } catch (Exception e) {
            br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
            MDLPToken.getToken();
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br2.readLine()) != null) {
            returnString += output2;
        }
    
        conn2.disconnect();
        br2.close();
        return new JSONObject(returnString).get("total").toString();
    }






}
