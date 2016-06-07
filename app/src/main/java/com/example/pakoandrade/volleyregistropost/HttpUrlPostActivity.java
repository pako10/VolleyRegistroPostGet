package com.example.pakoandrade.volleyregistropost;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class HttpUrlPostActivity extends AppCompatActivity {
    TextView content;
    EditText fname, email, login, pass;
    String Name, Email, Login, Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_post);
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

        content = (TextView) findViewById(R.id.content);
        fname = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        login = (EditText)findViewById(R.id.loginname);
        pass = (EditText)findViewById(R.id.password);


        Button saveme=(Button)findViewById(R.id.save);


        saveme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    GetText();
                }catch (Exception ex){
                    content.setText("Url exception");
                }
            }
        });
    }

    // Create GetText Metod
    public  void  GetText()  throws UnsupportedEncodingException {
        // Get user defined values
        Name = fname.getText().toString();
        Email   = email.getText().toString();
        Login   = login.getText().toString();
        Pass   = pass.getText().toString();


        // Create data variable for sent values to server

        String data = URLEncoder.encode("nombre", "UTF-8")
                + "=" + URLEncoder.encode(Name, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(Email, "UTF-8");

        data += "&" + URLEncoder.encode("apellido", "UTF-8")
                + "=" + URLEncoder.encode(Login, "UTF-8");

        data += "&" + URLEncoder.encode("password", "UTF-8")
                + "=" + URLEncoder.encode(Pass, "UTF-8");

        data += "&" + URLEncoder.encode("lat", "UTF-8")
                + "=" + URLEncoder.encode(Pass, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("http://androidexample.com/media/webservice/httppost.php");

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
        content.setText( text  );

    }

}
