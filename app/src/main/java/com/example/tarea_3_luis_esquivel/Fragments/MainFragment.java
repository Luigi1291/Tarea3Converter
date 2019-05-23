package com.example.tarea_3_luis_esquivel.Fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tarea_3_luis_esquivel.Adapters.CurrencyAdapter;
import com.example.tarea_3_luis_esquivel.Models.CurrencyItem;
import com.example.tarea_3_luis_esquivel.R;
import com.example.tarea_3_luis_esquivel.Utilities.DownloadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainFragment extends Fragment{

    ArrayList<CurrencyItem> mListCurrency = new ArrayList<CurrencyItem>();
    CurrencyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI (final View view) {
        /*Call Service and fill listView*/
        DownloadCurrencyValues(view);

        ImageView imgFlag = (ImageView) view.findViewById(R.id.imgFlag);
        imgFlag.setImageResource(R.drawable.usdflag);

        EditText txtEdit = (EditText) view.findViewById(R.id.txtUSD);
        txtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    RecalculateValues(view);
                }
            }
        });
    }

    private void RecalculateValues(View view){
        TextView txtUSD =  (TextView) view.findViewById(R.id.txtUSD);

        //Recalcular la cantidad de dólares
        for(CurrencyItem currencyItem : mListCurrency){
            currencyItem.setValue(currencyItem.getBasicValue() * Double.valueOf(txtUSD.getText().toString()));
        }

        //Actualizar listview con los valores nuevos de la lista
        adapter.notifyDataSetChanged();
    }

    private void DownloadCurrencyValues(final View view){
        DownloadTask task = new DownloadTask(view);
        task.setListener(new DownloadTask.DownloadTaskListener() {
            @Override
            public void onPostExecuteConcluded(String result) {
                try{
                    JSONObject response = new JSONObject(result);
                    String success = response.getString("success");
                    JSONObject currencies = response.getJSONObject("quotes");

                    //Llenar lista con valores del resultado JSON
                    FillCurrencyListView(currencies);

                    //Pintar listview con resultado JSON
                    int layoutId = R.layout.list_currency_view;
                     adapter = new CurrencyAdapter(getContext() ,layoutId, mListCurrency);

                    ListView mListView = (ListView) view.findViewById(R.id.listViewCurrency);
                    mListView.setAdapter(adapter);
                }
                catch (JSONException je){
                    je.printStackTrace();
                }
            }
        });
        task.execute(new String[] { getResources().getString(R.string.api_Url)});
    }

    private void FillCurrencyListView(JSONObject currencies){
        try {

            Double colon = currencies.getDouble("USDCRC"); //Devuelve el value del key
            Double dolarCanadiense = currencies.getDouble("USDCAD");
            Double pesoMexicano = currencies.getDouble("USDMXN");
            Double euro = currencies.getDouble("USDEUR");
            Double yen = currencies.getDouble("USDJPY");
            mListCurrency.add(new CurrencyItem("CRC",colon,colon));
            mListCurrency.add(new CurrencyItem("CAD",dolarCanadiense,dolarCanadiense));
            mListCurrency.add(new CurrencyItem("MXN",pesoMexicano,pesoMexicano));
            mListCurrency.add(new CurrencyItem("EUR",euro,euro));
            mListCurrency.add(new CurrencyItem("JPY",yen,yen));
        }
        catch (JSONException je){
            je.printStackTrace();
        }
    }
}
