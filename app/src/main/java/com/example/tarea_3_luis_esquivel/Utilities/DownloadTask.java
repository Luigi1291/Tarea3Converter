package com.example.tarea_3_luis_esquivel.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.Toast;

import com.example.tarea_3_luis_esquivel.Models.CurrencyItem;
import com.example.tarea_3_luis_esquivel.R;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private static String TAG="DownloadTask Class";
    private View view;
    private DownloadTaskListener mListener;

    //Download Progress Bar
    private ProgressDialog mProgress;

    public DownloadTask(View pView) {
        view = pView;
    }
    //Interfaz de tarea Async
    public interface DownloadTaskListener {
        void onPostExecuteConcluded(String result);
    }

    @Override
    protected String doInBackground(String... pUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = null;

        try{
            HttpUrl.Builder urlBuilder = HttpUrl.parse(pUrl[0]).newBuilder();
            urlBuilder.addQueryParameter("access_key", view.getContext().getResources().getString(R.string.api_Access_Key));
            urlBuilder.addQueryParameter("currencies", "USD,CRC,CAD,MXN,EUR,JPY");
            urlBuilder.addQueryParameter("format", "1");

            String url = urlBuilder.build().toString();

            request = new Request.Builder()
                    .url(url)
                    .build();

        } catch (Exception e){
            Log.i(TAG,"error in the URL");
            return "Download failed";
        }

        if(hasInternetAccess()) {
            try {
                Response response = null;
                response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    return response.body().string();
                }
            } catch (Exception e) {
                Toast.makeText(view.getContext().getApplicationContext(), "Error durante la descarga", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(view.getContext().getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgress = new ProgressDialog(view.getContext());
        //Evita cancelar el loading
        mProgress.setCancelable(false);

        mProgress.setMessage("Downloading currencies");
        mProgress.show();
    }

    final public void setListener(DownloadTaskListener listener) {
        mListener = listener;
    }

    @Override
    final protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (mListener != null)
            mListener.onPostExecuteConcluded(result);

        mProgress.dismiss();
    }

    public boolean hasInternetAccess() {
        ConnectivityManager cm = (ConnectivityManager) view.getContext().getSystemService(view.getContext().getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}