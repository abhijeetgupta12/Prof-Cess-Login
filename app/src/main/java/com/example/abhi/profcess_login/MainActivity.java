package com.example.abhi.profcess_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.style.FadingCircle;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email,pass;
    Button login;
    ProgressBar progressBar;
    String eml,pas;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Login");

        progressBar = findViewById(R.id.progress);
        email = findViewById(R.id.loginMail);
        pass = findViewById(R.id.loginpass);
        login = findViewById(R.id.loginBtn);

        FadingCircle wave = new FadingCircle();
        progressBar.setIndeterminateDrawable(wave);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              eml=email.getText().toString().trim();
              pas=pass.getText().toString().trim();

                if(eml.equals(""))
                {
                    email.setError("Can't be empty");
                    email.requestFocus();
                    return;
                }

                if(pas.equals(""))
                {
                    pass.setError("Can't be empty");
                    pass.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                check obj =new check();
                Thread t1 = new Thread(obj);
                t1.start();







            }
        });


    }

    public class check implements Runnable {

        @Override
        public void run() {

            RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
            String url = "https://homimarket.com/wp-content/Android/proff_login.php?";
            StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressBar.setVisibility(View.INVISIBLE);

                    // Log.d("12345",response);
                    if(response.equals("true"))
                    {
                        Intent i = new Intent(MainActivity.this,LoggedIn.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"failed", Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_LONG).show();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String,String> hm = new HashMap<>();
                    hm.put("email",eml);
                    hm.put("pass",pas);
                    return hm;
                }
            };

            rq.add(sr);



        }
    }



}
