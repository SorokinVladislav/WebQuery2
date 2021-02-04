package com.spring.spring;

import com.spring.spring.models.condot.reportMapClass;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.spring.spring.ConfigFile.TraceWayLogin;
import static com.spring.spring.ConfigFile.TraceWayPass;

public class jsonCreator {


    static ResultSet resultSet3 = null;


    public static String getTWToken() throws IOException {
        //Token
        String token = "";
        URL url = new URL("http://93.190.230.34/api/auth");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        String input = "{\"login\": \"" + TraceWayLogin + "\",\"password\":\"" + TraceWayPass + "\"}";
        System.out.println(input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            JSONObject obj = new JSONObject(output);
            token = obj.getJSONObject("token").getString("id");
        }
        conn.disconnect();
        br.close();
        return token;
    }


    public static Statement getDBConnection() {
        Connection connection;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://192.168.63.250", "su", "Gr0m21$h0w");
            //  connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-CGJS7NF\\TEST", "sa", "12345");
            //  connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\sql", "sa", "12345");
            statement = connection.createStatement();
            System.out.println("Соединение установлено");
        } catch (SQLException throwables) {
            System.out.println("Соединение не установлено");
        }
        return statement;

    }

    public static Integer getTWCount(String gtin, String batch) throws IOException {

        URL url2 = new URL("http://93.190.230.34/api/fns?expand=nomenclatures%2Cinvoice%2Cgtin%2Cgt" +
                "ins%2CnewObject%2Cnewobject%2Cprev_operation_uid%2CrelatedDocuments&gtin=" + gtin + "&operation_u" +
                "id=1&page=1&per-page=100&series=" + batch + "&sort=-cdt&access-token=" + getTWToken());
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setDoOutput(true);
        conn2.setRequestMethod("GET");

        BufferedReader br2;
        try {
            br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
        } catch (Exception e) {
            br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br2.readLine()) != null) {
            returnString += output2;
        }
        System.out.println(returnString);
        conn2.disconnect();
        br2.close();

        int sum = 0;
        JSONArray array = new JSONObject(returnString).getJSONArray("fns");
        for (int i = 0; i < array.length(); i++) {
            sum += array.getJSONObject(i).getInt("indcnt");
        }


        return sum;
    }


    public static String mdlp(String sscc) throws IOException {

        //Token
        String token = "";
        URL url = new URL("http://93.190.230.34/api/auth");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        String input = "{\"login\": \"" + TraceWayLogin + "\",\"password\":\"" + TraceWayPass + "\"}";
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            JSONObject obj = new JSONObject(output);
            token = obj.getJSONObject("token").getString("id");
        }
        conn.disconnect();
        br.close();


        URL url2 = new URL("http://93.190.230.34/api/code/" + sscc + "/ism?combo=true&access-token=" + token);
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setDoOutput(true);
        conn2.setRequestMethod("GET");

        BufferedReader br2;
        try {
            br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
        } catch (Exception e) {
            br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br2.readLine()) != null) {
            returnString += output2;
        }
        conn2.disconnect();
        br2.close();

        return returnString;
    }


    public static String tw(String sscc) throws IOException {

        URL url2 = new URL("http://93.190.230.34/api/code/" + sscc + "/codes?expand=object%2CpaletraCode%2Cparent&access-token=" + getTWToken());
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setDoOutput(true);
        conn2.setRequestMethod("GET");

        BufferedReader br2;
        try {
            br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
        } catch (Exception e) {
            br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
        }
        String output2 = "";
        String returnString = "";
        while ((output2 = br2.readLine()) != null) {
            returnString += output2;
        }
        conn2.disconnect();
        br2.close();

        return returnString;
    }

    public static String sendToTW(String jsonToTW) throws IOException {
        String token = "";
        URL url = new URL("http://93.190.230.34/api/auth");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        String input = "{\"login\": \"" + TraceWayLogin + "\",\"password\":\"" + TraceWayPass + "\"}";
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            JSONObject obj = new JSONObject(output);
            token = obj.getJSONObject("token").getString("id");
        }
        conn.disconnect();
        br.close();


        URL url2 = new URL("http://93.190.230.34/api/v1/erp/run?access-token=" + token);
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setDoOutput(true);
        conn2.setRequestMethod("POST");
        conn2.setRequestProperty("Content-type", "application/json");
        OutputStream os2 = conn2.getOutputStream();
        os2.write(jsonToTW.getBytes());
        os2.flush();

        BufferedReader br2;
        try {
            br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
        } catch (Exception e) {
            br2 = new BufferedReader(new InputStreamReader((conn2.getErrorStream())));
        }

        String output2 = "";
        String returnString = "";
        while ((output2 = br2.readLine()) != null) {
            returnString += output2;
        }
        conn2.disconnect();
        br2.close();

        return returnString;
    }


    public static String json(String sscc) throws SQLException, FileNotFoundException {

        String doc_date = "";
        String expiration_date = "";
        String series = "";
        String gtin = "";
        String production_date = "";


        ArrayList<String> secc3 = new ArrayList<>();

        Statement statement = getDBConnection();

        List<reportMapClass> listAllGoods = new ArrayList<>();

        String selectSql = "SELECT  [JobID],[Pkg_Level] ,[UIDCode],[NextLevel_UIDCode],[Prod_Pkg_ID] ,[IsPrinted] ,[IsInspected] ,[IsRejected] ,[IsSampled],IsAggregated\n" +
                "                          FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
                "                         where NextLevel_UIDCode='" + sscc + "'";
        resultSet3 = statement.executeQuery(selectSql);
        String jobid = "";
        String pkg_level = "";
        while (resultSet3.next()) {
            secc3.add(resultSet3.getString("UIDCode"));
            jobid = resultSet3.getString("JobID");
            pkg_level = resultSet3.getString("Pkg_Level");
        }

        selectSql = "SELECT  [JobID]\n" +
                "      ,[Pkg_Level]\n" +
                "      ,[UIDCode]\n" +
                "      ,[NextLevel_UIDCode]\n" +
                "      ,[Prod_Pkg_ID]\n" +
                "      ,[IsPrinted]\n" +
                "      ,[IsInspected]\n" +
                "      ,[IsRejected]\n" +
                "      ,[IsSampled]\n" +
                "      ,[IsAggregated]" +
                "  FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
                "  where JobID=" + jobid + " and IsAggregated=1 order by NextLevel_UIDCode\n";

        resultSet3 = statement.executeQuery(selectSql);
        while (resultSet3.next()) {
            listAllGoods.add(new reportMapClass(resultSet3.getString("JobID"), resultSet3.getString("Pkg_Level"),
                    resultSet3.getString("NextLevel_UIDCode"), resultSet3.getString("UIDCode"), resultSet3.getString("IsPrinted"),
                    resultSet3.getString("IsInspected"), resultSet3.getString("IsRejected"), resultSet3.getString("IsSampled"),
                    resultSet3.getString("IsAggregated")));
        }

        String fileReader = "";
        try {
            //   FileReader reader = new FileReader("\\\\192.168.63.250\\rTrackerFTP\\ThirdPartyUpload\\"+jobid+"_915_Request.txt");
            FileReader reader = new FileReader("/run/user/1000/gvfs/smb-share:server=192.168.63.250,share=rtrackerftp/ThirdPartyUpload/" + jobid + "_915_Request.txt");


            BufferedReader bufReader = new BufferedReader(reader);

            String line = bufReader.readLine();
            while (line != null) {
                fileReader = fileReader + line;
                line = bufReader.readLine();
            }

            JSONObject oldRequest = new JSONObject(fileReader);

            doc_date = oldRequest.get("doc_date").toString();
            expiration_date = oldRequest.getJSONObject("data").getJSONObject("product").get("expiration_date").toString();
            series = oldRequest.getJSONObject("data").getJSONObject("product").get("series").toString();
            gtin = oldRequest.getJSONObject("data").getJSONObject("product").get("gtin").toString();
            production_date = oldRequest.getJSONObject("data").getJSONObject("product").get("production_date").toString();

        } catch (Exception e) {
        }

        JSONObject main = new JSONObject();
        main.put("doc_id", "" + ((int) (Math.random() * 99999999)) + "");
        main.put("type_id", 61);
        main.put("doc_num", "" + ((int) (Math.random() * 99999999)) + "");
        main.put("doc_date", doc_date);
        main.put("subject_id", "00000000272114");
        JSONObject data = new JSONObject();
        main.put("data", data);


        data.put("invoice_number", "");
        data.put("invoice_date", "");
        JSONObject product = new JSONObject();
        product.put("name", "");
        product.put("gtin", gtin);
        product.put("series", series);
        product.put("production_date", production_date);
        product.put("expiration_date", expiration_date);
        data.put("product", product);


        JSONObject mainJO = new JSONObject();

        mainJO.put("code", sscc);
        mainJO.put("type", "sscc");
        JSONArray secon3 = new JSONArray();


        if (pkg_level.equals("SEC")) {
            for (reportMapClass list : listAllGoods) {
                if (list.getNextLevel_UIDCode().equals(sscc)) {
                    JSONObject jo2 = new JSONObject();
                    jo2.put("type", "sgtin");
                    jo2.put("code", gtin + list.getUIDCode());
                    secon3.put(jo2);
                }
            }

        } else {
            for (String codeSec3 : secc3) {
                JSONObject jo = new JSONObject();
                jo.put("type", "sscc");
                jo.put("code", codeSec3);

                JSONArray secon = new JSONArray();

                for (reportMapClass list : listAllGoods) {
                    if (list.getNextLevel_UIDCode().equals(codeSec3)) {
                        JSONObject jo2 = new JSONObject();
                        jo2.put("type", "sgtin");
                        jo2.put("code", gtin + list.getUIDCode());
                        secon.put(jo2);
                    }
                }
                JSONObject jo3 = new JSONObject();
                jo3.put("dataItems", secon);
                jo.put("content", jo3);
                secon3.put(jo);
            }
        }


        JSONObject jo4 = new JSONObject();
        jo4.put("dataItems", secon3);
        mainJO.put("content", jo4);
        JSONArray MainDataItems = new JSONArray();
        MainDataItems.put(mainJO);
        JSONObject mainDataItems2 = new JSONObject();
        mainDataItems2.put("dataItems", MainDataItems);
        data.put("aggregatedData", mainDataItems2);
        main.put("data", data);

        return (main.toString());

    }


}







