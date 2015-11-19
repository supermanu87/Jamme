package com.jamme.gdisanto.jamme;

/**
 * Created by supermanu on 13/11/2015.
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jamme.gdisanto.jamme.JSON.JSONParser;

public class Login2 extends Activity {
    EditText un,pw;
    TextView error;
    Button ok, signup, anonim;

    // Progress Dialog
    private ProgressDialog pDialog;

    private static final String LOGIN_URL           = "http://192.168.1.3:8080/login.php";

    private static final String TAG_SUCCESS         = "success";
    private static final String TAG_MESSAGE         = "message";
    private static final String INVALID_CREDENTIAL  = "Username o password non validi!";
    private String username, password;


    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        un=(EditText)findViewById(R.id.et_un);
        pw=(EditText)findViewById(R.id.et_pw);
        ok=(Button)findViewById(R.id.button_login);
        signup=(Button)findViewById(R.id.button_signup);
        anonim=(Button)findViewById(R.id.button_anonimous);

        error=(TextView)findViewById(R.id.tv_error);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                username = un.getText().toString();
                password = pw.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Log.d("FIGA", "Username o password nulli");
                    Toast.makeText(Login2.this, INVALID_CREDENTIAL, Toast.LENGTH_LONG).show();

                } else {
                    new AttemptLogin().execute();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login2.this, Signup.class);

                startActivity(i);

            }
        });
    }


    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login2.this);
            pDialog.setMessage("Aspiett nu mument....!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
                        params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    // save user data
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(Login2.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();
                    Intent i = new Intent(Login2.this, Collector.class);
                    i.putExtra("username", un.getText());

                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Login2.this, file_url, Toast.LENGTH_LONG).show();
            }

        }
    }




}

