package xyz.eneroth.memo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView2);

        final ArrayList<String> list = new ArrayList<String>();

        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(itemsAdapter);

        ArrayList<MemoRow> arrayOfUsers = new ArrayList<MemoRow>();
        final MemoAdapter adapter = new MemoAdapter(this, arrayOfUsers);
        ListView listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setAdapter(adapter);

        //getAllMemos
        button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText namn = (EditText) findViewById(R.id.editText);
                try {
                    JSONArray jSONArray = getAllMemos(namn.getText().toString()).getJSONArray("memos");
                    adapter.clear();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject JSONObject = jSONArray.getJSONObject(i);
                        MemoRow memoRow = new MemoRow(" " + (String) JSONObject.get("userId") + " ", (String) JSONObject.get("memo") + " ", (String) JSONObject.get("id"));
                        adapter.add(memoRow);
                    }
                    itemsAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //addMemo
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    EditText namn = (EditText) findViewById(R.id.editText);
                    EditText memo = (EditText) findViewById(R.id.editText2);
                    try {
                        addMemo(namn.getText().toString(), memo.getText().toString());
                        namn.setText("");
                        button1.performClick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void removeMemo(View v) {
        LinearLayout vwParentRow = (LinearLayout) v.getParent();
        TextView id = (TextView) vwParentRow.getChildAt(2);
        Button btnChild = (Button) vwParentRow.getChildAt(3);
        try {
            deleteMemo(id.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        button1.performClick();
        vwParentRow.refreshDrawableState();
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

    public void deleteMemo(String id) throws Exception {
        String url = getString(R.string.url_delete) + id;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'DELETE' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }

    public void addMemo(String userId, String memo) throws Exception {
        String url = getString(R.string.url_add) + userId + "&memo=" + memo;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }
}
