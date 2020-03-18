package com.example.onlineshop2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class MyAdapter extends ArrayAdapter<String> {

    Context context;
    String rName[];
    String rPrice[];
    int rImages[];

    MyAdapter(Context c, String names[], String prices[], int images[]){
        super(c, R.layout.row,R.id.nameText,names);
        this.context = c;
        this.rName = names;
        this.rPrice = prices;
        this.rImages = images;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        ImageView imageView =  view.findViewById(R.id.imageView);
        TextView name =  view.findViewById(R.id.nameText);
        TextView price =  view.findViewById(R.id.priceText);

        imageView.setImageResource(rImages[position]);
        name.setText(rName[position]);
        price.setText(rPrice[position]);


        return view;
    }
}