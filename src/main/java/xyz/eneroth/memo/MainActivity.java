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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button1;
    final RestServices restServices = new RestServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        final ArrayList<String> list = new ArrayList<String>();

        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(itemsAdapter);

        ArrayList<MemoRow> arrayOfUsers = new ArrayList<MemoRow>();
        final MemoAdapter adapter = new MemoAdapter(this, arrayOfUsers);
        ListView listView2 = (ListView) findViewById(R.id.listView);
        listView2.setAdapter(adapter);

        //getAllMemos
        button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText namn = (EditText) findViewById(R.id.editText);
                try {
                    JSONArray jSONArray = restServices.getAllMemos(namn.getText().toString(), getString(R.string.url_getall)).getJSONArray("memos");
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
                        restServices.addMemo(namn.getText().toString(), memo.getText().toString(), getString(R.string.url_add));
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

        button1.setFocusable(true);
        button1.setFocusableInTouchMode(true);
        button1.requestFocus();
    }

    //removeMemo
    public void removeMemo(View v) {
        LinearLayout parentRow = (LinearLayout) v.getParent();
        TextView id = (TextView) parentRow.getChildAt(2);
        try {
            restServices.deleteMemo(id.getText().toString(), getString(R.string.url_delete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        button1.performClick();
        parentRow.refreshDrawableState();
    }
}
