package com.example.shiva.smartagriculture.features;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by shiva on 28-10-2018.
 */


public class HttpRequestFromFlask extends AsyncTask<String, String, String> {

    private static final String TAG = "Cms";
    private String resp;
    ProgressDialog progressDialog;
    Context mcontext;

    String attachmentName = "bitmap";
    String attachmentFileName = "bitmap.bmp";
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary =  "*****";

    Bitmap bitmap;


    HttpRequestFromFlask(Context mcontext,Bitmap bitmap){
        this.mcontext = mcontext;
        this.bitmap = bitmap;
    }

    @Override
    protected String doInBackground(String... params) {
        //publishProgress("Sleeping..."); // Calls onProgressUpdate()
//        try {
//            int time = Integer.parseInt(params[0])*1000;
//
//            Thread.sleep(time);
//            resp = "Slept for " + params[0] + " seconds";
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp = e.getMessage();
//        }

        HttpURLConnection httpUrlConnection = null;
        URL url = null;
        try {
            Log.d("cms","start1");
            url = new URL("http://127.0.0.1:5000/");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + this.boundary);

            Log.d("cms","start2");

            DataOutputStream request = new DataOutputStream(
                    httpUrlConnection.getOutputStream());

            InputStream responseStream = new
                    BufferedInputStream(httpUrlConnection.getInputStream());

            Log.d("cms","start5");

            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            String response = stringBuilder.toString();

            Log.d("cms",response);



            request.writeBytes(this.twoHyphens + this.boundary + this.crlf);

            Log.d("cms","start3");

            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    this.attachmentName + "\";filename=\"" +
                    this.attachmentFileName + "\"" + this.crlf);
            request.writeBytes(this.crlf);

            Log.d("cms","start4");

            byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            for (int i = 0; i < bitmap.getWidth(); ++i) {
                for (int j = 0; j < bitmap.getHeight(); ++j) {
                    //we're interested only in the MSB of the first byte,
                    //since the other 3 bytes are identical for B&W images
                    pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
                }
            }

            request.write(pixels);

            request.writeBytes(this.crlf);
            request.writeBytes(this.twoHyphens + this.boundary +
                    this.twoHyphens + this.crlf);

            request.flush();
            request.close();


            responseStreamReader.close();



            responseStream.close();

            httpUrlConnection.disconnect();

            Log.d("cms",response);
            Toast.makeText(mcontext,"Image Uploaded and response received",Toast.LENGTH_LONG).show();
            return response;



        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }


    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        //progressDialog.dismiss();
        //finalResult.setText(result);
    }


    @Override
    protected void onPreExecute() {
        Toast.makeText(mcontext,"Started Sending the file",Toast.LENGTH_LONG).show();
//        progressDialog = ProgressDialog.show(mcontext,
//                "ProgressDialog",
//                "Wait for seconds");
    }


    @Override
    protected void onProgressUpdate(String... text) {
        //.setText(text[0]);

    }
}
