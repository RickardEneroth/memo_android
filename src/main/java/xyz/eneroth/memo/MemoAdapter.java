package xyz.eneroth.memo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MemoAdapter extends ArrayAdapter<MemoRow> {
    public MemoAdapter(Context context, ArrayList<MemoRow> memoRow) {
        super(context, 0, memoRow);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemoRow memoRow = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.memo_layout, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvMemo = (TextView) convertView.findViewById(R.id.tvMemo);
        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        tvName.setText(memoRow.name);
        tvMemo.setText(memoRow.memo);
        tvId.setText(memoRow.id);

        return convertView;
    }
}