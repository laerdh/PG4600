package com.westerdals.dako.pokemon.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.westerdals.dako.pokemon.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataHandler extends AsyncTask<Void, Void, ResponseWrapper> {
    private ResultsListener listener;
    private String targetURL;
    private Context context;
    private String progressMessage;
    private ProgressDialog progressDialog;


    public DataHandler(Context context) {
        this.context = context;
    }


    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }


    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }


    public void setOnResultsListener(ResultsListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(progressMessage);
        progressDialog.show();
    }


    @Override
    protected ResponseWrapper doInBackground(Void... params) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(targetURL).openConnection();
            connection.setRequestProperty("X-Token", context.getString(R.string.API_KEY));

            // Handle response
            ResponseWrapper response = new ResponseWrapper();
            response.response = connection.getInputStream();
            response.responseCode = connection.getResponseCode();

            return response;
        } catch (IOException e) {
            e.printStackTrace();

            ResponseWrapper errorResponse = new ResponseWrapper();
            errorResponse.responseCode = 420;
            return errorResponse;
        }
    }


    @Override
    protected void onPostExecute(final ResponseWrapper response) {
        progressDialog.cancel();

        // Return the result
        listener.onResultsSucceeded(response);
    }
}
