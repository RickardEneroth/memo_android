package xyz.eneroth.memo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button)findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText_memolist);
                EditText namn = (EditText)findViewById(R.id.editText_namn);
                try {
                    JSONArray jSONArray = getAllMemos(namn.getText().toString()).getJSONArray("memos");
                    String str = "";
                    for (int i=0;i<jSONArray.length();i++) {
                        JSONObject JSONObject = jSONArray.getJSONObject(i);
                        str += (String) JSONObject.get("userId") + ", " + (String) JSONObject.get("memo") + "\n";
                    }
                    editText.setText(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button button3 = (Button)findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText namn = (EditText)findViewById(R.id.editText_namn);
                try {
                    deleteAllMemos(namn.getText().toString());
                    namn.setText("");
                    button1.performClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button button2 = (Button)findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText namn = (EditText)findViewById(R.id.editText_namn);
                EditText memo = (EditText)findViewById(R.id.editText_memo);
                try {
                    addMemo(namn.getText().toString(), memo.getText().toString());
                    namn.setText("");
                    button1.performClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JSONObject getAllMemos(String userId) throws Exception {
        String url = getString(R.string.url_getall) + userId;
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

    public void deleteAllMemos(String userId) throws Exception {
        String url = getString(R.string.url_delall) + userId;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }

    public void addMemo(String userId, String memo) throws Exception {
        String url = getString(R.string.url_add) + userId + "&memo=" + memo;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }
}
