package com.example.pakoandrade.volleyregistropost;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    EditText etPass;
    EditText etUser;
    Button btLogin;
    EditText etPostLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etPass = (EditText) findViewById(R.id.etPass);
        etUser = (EditText) findViewById(R.id.etlogin);
        btLogin = (Button) findViewById(R.id.btLogin);
        etPostLogin = (EditText) findViewById(R.id.etPostLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

    }

    public void LoginUser(){

        final String keyPassword = "mipass";
        final String keyUser = "correo";
        final String username = etUser.getText().toString().trim();
        final String password = etPass.getText().toString().trim();




        RequestParams params = new RequestParams();

        params.put(keyUser, username);
        params.put(keyPassword, password);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://wsplannerlog.cloudapp.net/wsLogin.svc/GetLogin", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    byte[] bytes = responseBody;
                    String str = new String(bytes);
                    // JSONObject jsObj = new JSONObject(city);
                    Toast.makeText(LoginActivity.this, "Entro", Toast.LENGTH_SHORT).show();
                    etPostLogin.setText(str);
                    int size = responseBody.length;
                    // parseResponseAmount(jsObj,size);

                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "No entra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                etPostLogin.setText(error.toString());
            }
        });

    }


}
