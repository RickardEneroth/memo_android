package xyz.eneroth.memo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RestServices {
    public JSONObject getAllMemos(String userId, String url) throws Exception {
        url = url + userId;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        JSONObject jsonObject = new JSONObject(response.toString());

        return jsonObject;
    }

    public void deleteMemo(String id, String url) throws Exception {
        url = url + id;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'DELETE' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }

    public void addMemo(String userId, String memo, String url) throws Exception {
        url = url + userId + "&memo=" + memo;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }
}
