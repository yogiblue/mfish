package com.muwuprojects.mfish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mrmu on 08/04/2017.
 */

public class TextListAdaptor extends ArrayAdapter<String> {

    private Context context;
    private List<String> values;


    public TextListAdaptor(Context context, List<String> objects) {
        super(context, R.layout.list_layout, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

        imageView.setVisibility(View.INVISIBLE);

        textView2.setText("Short witty remark");
        textView.setText(values.get(position));
        return rowView;

        }

    }
