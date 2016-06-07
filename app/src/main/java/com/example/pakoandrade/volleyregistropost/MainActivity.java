package com.example.pakoandrade.volleyregistropost;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.*;

public class MainActivity extends BaseVolley {
    private TextView label;
    private Button connector;
    Spinner spinerP;
    TextView label2;
    Button btPost;
    EditText etPost;
    Spinner spinerC;
    public String valor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        label = (TextView) findViewById(R.id.label);
        connector = (Button) findViewById(R.id.connection_button);
        spinerP = (Spinner) findViewById(R.id.spinner);
        spinerC = (Spinner) findViewById(R.id.spinner3);

        label2 = (EditText) findViewById(R.id.editText);
        btPost = (Button) findViewById(R.id.button);
        etPost = (EditText) findViewById(R.id.etPost);

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nuevo(valor);
                //registerUser();
               // consultCity();
               /*try{
                    GetText();
                }catch (Exception ex){
                    etPost.setText("Url exception");
                }*/
            }
        });

        connector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });

        spinerP.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        // makeRequest();
                        //label.setText("Spinner1: position=" + position + " id=" + id + "\n "+"value="  );
                        getValue(position);

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //("Spinner1: unselected");
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        spinerP.setAdapter(adapter);
                        adapter.add(id);

                        //                       for(int a = 0;a < jsonArray.length();a++){
                        //                        cityArray.add("i");

                        //                     }
                        //"Value" "Selected"
//                        String name = jsonObjeto.optString("name").toString();
//                        float salary = Float.parseFloat(jsonObject.optString("salary").toString());

                        data +=   value + "\n" ;
                        //+" \n Name= "+ name +" \n Salary= "+ salary +" \n ";




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
                            Nuevo(ciudad);
                            label2.setText(ciudad);
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

    private void registerUser()  {
        final String ciudadesUrl = "http://wsplanner.cloudapp.net/wsPL.svc/ListCiudad";
        final String cityValueR = label2.getText().toString().trim();
        final String cityValue = "2964adba-8a58-4c89-9d43-ee65e8f322a4";
        final String KEY_USERNAME = "pID";



        HashMap<String, String> params = new HashMap<String, String>();
        params.put(KEY_USERNAME, cityValueR);
        params.put("contentType", "application/json; charset=utf-8");

        JSONObject parame = new JSONObject();

        try {
            parame.put(KEY_USERNAME,cityValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "Conectando....", Toast.LENGTH_SHORT).show();
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,ciudadesUrl,parame,
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
                            etPost.setText(e.toString());
                        }

                        //label.setText(jsonObject.toString());
                        onConnectionFinished();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        etPost.setText(error.toString());
                    }
                }) {
         /*   @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                JSONObject obj = new JSONObject();
               // obj.put(KEY_USERNAME,cityValue);
                params.put("contentType", "application/json; charset=utf-8");
                params.put(KEY_USERNAME, cityValue);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("contentType","application/json");
                return params;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }*/

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void consultCity(){
        final String url = "http://wsplanner.cloudapp.net/wsPL.svc/ListCiudad";
        final String cityValueR = label2.getText().toString().trim();
        final String cityValue = "2964adba-8a58-4c89-9d43-ee65e8f322a4";
        final String KEY_USERNAME = "pID";

        RequestQueue requestManager = Volley.newRequestQueue(this);


        Response.Listener<String> jsonListerner = new Response.Listener<String>() {
            @Override
            public void onResponse(String list) {
                Log.w("Respuesta",list);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.w("Volley Error", error.getMessage());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                label2.setText(error.toString());
            }
        };

        StringRequest fileRequest = new StringRequest(Request.Method.POST, url, jsonListerner,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, cityValue);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // do not add anything here
                return headers;
            }
        };
        requestManager.add(fileRequest);
    }

    // Create GetText Metod
    public  void  GetText()  throws UnsupportedEncodingException {
        // Get user defined values
        Toast.makeText(MainActivity.this, "Conectando...", Toast.LENGTH_SHORT).show();
        String Name;
        Name = label2.getText().toString().trim();



        // Create data variable for sent values to server

        String data = "{" + URLEncoder.encode("pID", "UTF-8")
                + ":" + URLEncoder.encode(Name, "UTF-8") + "}";

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
           // URL url = new URL("http://androidexample.com/media/webservice/httppost.php");
            URL url = new URL("http://wsplanner.cloudapp.net/wsPL.svc/ListCiudad");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        etPost.setText( text  );

    }

    public void Nuevo(String value){
        final String cityValueR = label2.getText().toString().trim();
        final String cityValue = "2964adba-8a58-4c89-9d43-ee65e8f322a4";
        final String KEY_USERNAME = "pID";



        RequestParams params = new RequestParams();
        params.put(KEY_USERNAME,value);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://wsplannerregistro.cloudapp.net/wsRegistrro.svc/ListCiudad", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    byte[] bytes = responseBody;
                    String city = new String(bytes);
                    JSONObject jsObj = new JSONObject(city);
                   // Toast.makeText(MainActivity.this, "Entro", Toast.LENGTH_SHORT).show();
                    //etPost.setText(str);
                    int size = responseBody.length;
                    parseResponseAmount(jsObj,size);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "No entra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, statusCode, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void parseResponseAmount (JSONObject response, int amount) {
        final ArrayAdapter<CharSequence> adapterC =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item );
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try {
            JSONArray jsonArray = response.optJSONArray("d");
            //JSONArray readerArray = new JSONArray(response);
            //JSONArray jsonArray = readerArray.optJSONArray("d");


            for (int i = 0; i < amount; i++)
            {
                JSONObject userObject = (JSONObject) jsonArray.get(i);
                String id = userObject.optString("Text");
                //String ciudad = userObject.getString("Text");
                spinerC.setAdapter(adapterC);
                adapterC.add(id);
               // etPost.setText(id);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    

}
