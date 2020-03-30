package com.example.app;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class BackgroundWork extends AsyncTask<String,Void,String> {


    Context context;
    //use this for mail. Create other contructors for other tasks
    public BackgroundWork(Context context){
        this.context = context;
        Toast.makeText(this.context,"bg",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        //write code here
        String method = params[0];
        if(method.equals("register")){
            String username = params[1];
            String nume = params[2];
            String prenume = params[3];
            String pass = params[4];
            String login = params[5];
            String date = params[6];
            try{
                URL url = new URL("http://gladiaholdings.com/PHP/register.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username,"UTF-8") + "&" +
                              URLEncoder.encode("nume","UTF-8") + "=" + URLEncoder.encode(nume,"UTF-8") + "&" +
                              URLEncoder.encode("prenume","UTF-8") + "=" + URLEncoder.encode(prenume,"UTF-8") + "&" +
                              URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass,"UTF-8") + "&" +
                              URLEncoder.encode("date","UTF-8") + "=" + URLEncoder.encode(date,"UTF-8") + "&" +
                              URLEncoder.encode("sex","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
                String regex = "[0-9]+";
                if(login.matches(regex))
                    data = data + URLEncoder.encode("nrtel", "UTF-8") + "=" + URLEncoder.encode(login,"UTF-8") + "&" +
                                  URLEncoder.encode("mail","UTF-8") + "=" + URLEncoder.encode("-null-","UTF-8");
                else
                    data = data + URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(login,"UTF-8") + "&" +
                            URLEncoder.encode("nrtel","UTF-8") + "=" + URLEncoder.encode("-null-","UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                return "Registration successful";
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
    }
}
