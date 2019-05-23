package com.example.tarea_3_luis_esquivel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarea_3_luis_esquivel.Models.CurrencyItem;
import com.example.tarea_3_luis_esquivel.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<CurrencyItem> {
    int mLayoutId;
    public CurrencyAdapter(Context context, int resource, List<CurrencyItem> objects) {
        super(context, resource, objects);
        mLayoutId = resource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.DOWN);

        CurrencyItem currencyItem = getItem(position);
        String key = currencyItem.getKey();
        Double value = currencyItem.getValue();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayoutId, parent, false);
        }

        TextView txtKey = (TextView) view.findViewById(R.id.lblKey);
        TextView txtValue = (TextView) view.findViewById(R.id.lblValue);
        txtKey.setText(key);
        txtValue.setText(df.format(value));

        ImageView imgFlag = (ImageView) view.findViewById(R.id.imgFlag);

        switch (key){
            case "CRC":
                imgFlag.setImageResource(R.drawable.crcflag);
                break;
            case "CAD":
                imgFlag.setImageResource(R.drawable.canadaflag);
                break;
            case "MXN":
                imgFlag.setImageResource(R.drawable.mexflag);
                break;
            case "EUR":
                imgFlag.setImageResource(R.drawable.eurflag);
                break;
            case "JPY":
                imgFlag.setImageResource(R.drawable.japanflag);
                break;
        }

        return view;
    }
}
