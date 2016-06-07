package com.example.pakoandrade.volleyregistropost;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends BaseVolley {
    Spinner spCiudades;
    EditText etValue;
    EditText etName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    EditText etPost;
    Button btRegistrate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spCiudades = (Spinner) findViewById(R.id.spinner2);
        etValue = (EditText) findViewById(R.id.editText5);
        etName = (EditText) findViewById(R.id.etnombre);
        etLastName = (EditText) findViewById(R.id.etapellidos);
        etEmail = (EditText) findViewById(R.id.etcorreo);
        etPassword = (EditText) findViewById(R.id.etcontrasenia);
        etPost = (EditText) findViewById(R.id.etPost);
        btRegistrate = (Button) findViewById(R.id.button2);
        makeRequest();

        btRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // registerUser();
                RegisterUser();
            }
        });

        spCiudades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getValue(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void makeRequest(){
        String url = "http://wsplannerregistro.cloudapp.net/wsRegistrro.svc/ListPais";
        //String url = " http://wsplanner.cloudapp.net/wsPL.svc/GetPrueba";
        //String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=39.476245,-0.349448&sensor=true";
        final ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try{
                    JSONArray jsonArray = jsonObject.optJSONArray("d");
                    String data = "";


                    List<String> list = new ArrayList<String>(data.length());
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObjeto = jsonArray.getJSONObject(i);

                        String id = jsonObjeto.optString("Text");
                        final String value = jsonObjeto.optString("Value");
                        spCiudades.setAdapter(adapter);
                        adapter.add(id);

                        data +=   value + "\n" ;
                    }
                    //label2.setText(data);
                }catch (JSONException e) {e.printStackTrace();}

                //label.setText(jsonObject.toString());
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed(volleyError.toString());
            }
        });
        addToQueue(request);
    }

    private void getValue(final int value){
        String url = "http://wsplannerregistro.cloudapp.net/wsRegistrro.svc/ListPais";

        final JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try{
                    JSONArray jsonArray = jsonObject.optJSONArray("d");
                    String data = "";


                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObjeto = jsonArray.getJSONObject(i);

                        if(value == i){
                            String ciudad = jsonObjeto.optString("Value");
                            etValue.setText(ciudad);
                        }
                        final String value = jsonObjeto.optString("Value");



                        data +=   value + "\n" ;
                    }
                }catch (JSONException e) {e.printStackTrace();}

                //label.setText(jsonObject.toString());
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed(volleyError.toString());
            }
        });
        addToQueue(request);
    }

  /*  private void registerUser() {
        final String ciudadesUrl = "http://wsplanner.cloudapp.net/wsPL.svc/InsertPID";
        final String keyCityValue = "pID";
        final String keyCity = "ciudad";
        final String keyCountry = "pais";
        final String keyUserName = "nombre";
        final String keyUserLastName = "apellido";
        final String keyPassword = "password";
        final String keyEmail = "email";
        final String keyLatitud = "lat";
        final String keyLongitud = "long";
        final String username = etName.getText().toString().trim();
        final String userLastName = etLastName.getText().toString();
        final String password = etPassword.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String cityValue = etValue.getText().toString().trim();



        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,ciudadesUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = jsonObject.optJSONArray("d");
                            String data = "";


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjeto = jsonArray.getJSONObject(i);
                                String post = jsonObjeto.optString("Text");


                                data += post;
                            }
                            etPost.setText(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //label.setText(jsonObject.toString());
                        onConnectionFinished();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistroActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        etPost.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put(keyUserName, username);
                params.put(keyUserLastName, userLastName);
                params.put(keyEmail, email);
                params.put(keyPassword, password);
                //params.put(keyCityValue, cityValue);
                params.put(keyCountry, cityValue);
                params.put(keyCity, null);
                params.put(keyLatitud, null);
                params.put(keyLongitud, null);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/

    public void RegisterUser(){
       // final String cityValueR = label2.getText().toString().trim();
        final String cityValue = "2964adba-8a58-4c89-9d43-ee65e8f322a4";
        final String KEY_USERNAME = "pID";
        final String keyCity = "ciudad";
        final String keyCountry = "pais";
        final String keyUserName = "nombre";
        final String keyUserLastName = "apellido";
        final String keyPassword = "password";
        final String keyEmail = "email";
        final String keyLatitud = "lat";
        final String keyLongitud = "long";
        final String username = etName.getText().toString().trim();
        final String userLastName = etLastName.getText().toString();
        final String password = etPassword.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        //final String cityValue = etValue.getText().toString().trim();



        RequestParams params = new RequestParams();
        //params.put(KEY_USERNAME,value);
        params.put(keyUserName, username);
        params.put(keyUserLastName, userLastName);
        params.put(keyEmail, email);
        params.put(keyPassword, password);
        //params.put(keyCityValue, cityValue);
        params.put(keyCountry, cityValue);
        params.put(keyCity,"2964adba-8a58-4c89-9d43-ee65e8f322a4");
        params.put(keyLatitud, "a");
        params.put(keyLongitud, "a");
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://wsplannerregistro.cloudapp.net/wsRegistrro.svc/InsertPID", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    byte[] bytes = responseBody;
                    String str = new String(bytes);
                   // JSONObject jsObj = new JSONObject(city);
                     Toast.makeText(RegistroActivity.this, "Entro", Toast.LENGTH_SHORT).show();
                    etPost.setText(str);
                    int size = responseBody.length;
                   // parseResponseAmount(jsObj,size);

                }catch (Exception e){
                    Toast.makeText(RegistroActivity.this, "No entra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(RegistroActivity.this, statusCode, Toast.LENGTH_SHORT).show();
                //etPost.setText(responseBody.toString());
                //etPost.setText(headers.toString());
                etPost.setText(error.toString());
            }
        });

    }

}
