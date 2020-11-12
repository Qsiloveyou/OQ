package com.example.QQclient;


import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AndroidClientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_client);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                HttpPost httpRequest = new HttpPost("http://172.16.99.207:8080/AndroidServer/AndroidServerServlet");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("clientData", "您好server端！"));
                try {
                    httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//设置请求參数项
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = httpClient.execute(httpRequest);//运行请求返回响应
                    if(httpResponse.getStatusLine().getStatusCode() == 200){//推断是否请求成功
                        Toast.makeText(AndroidClientActivity.this, EntityUtils.toString(httpResponse.getEntity()), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(AndroidClientActivity.this, "没有获取到Androidserver端的响应！", Toast.LENGTH_LONG).show();
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

