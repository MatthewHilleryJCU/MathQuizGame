package au.com.hillnet.mathquizgame;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


class QArrayAdapter extends ArrayAdapter<Question> {

    QArrayAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Question q = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_question, parent, false);
        }

        TextView txtQuestion = (TextView) convertView.findViewById(R.id.txtQuestion);
        assert q != null;
        txtQuestion.setText(q.getLine());
        TextView txtInput = (TextView) convertView.findViewById(R.id.txtInput);
        txtInput.setText(q.getInput());

        LinearLayout itemLayout = (LinearLayout) convertView.findViewById(R.id.itemLayout);
        if (q.getStatus() == QStatus.Right) {
            itemLayout.setBackgroundColor(Color.parseColor("#B7F5A6")); //light green
            //txtQuestion
        } else if (q.getStatus() == QStatus.Wrong) {
            itemLayout.setBackgroundColor(Color.parseColor("#F5A6AE")); //light red
            txtInput.append(String.format(" | A: %s", q.getAns()));
        } else {
            itemLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        return convertView;
    }

}
