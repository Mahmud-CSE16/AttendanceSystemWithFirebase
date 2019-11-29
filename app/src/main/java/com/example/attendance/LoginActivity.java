package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername,editTextPassword;
    private Spinner spinner;
    private ProgressDialog progressDialog;

    String spinner_text;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editText_username);
        editTextPassword = findViewById(R.id.editText_password);

        //set spinner
        spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View view){
        spinner_text = spinner.getSelectedItem().toString();

        if(isNetworkAvailable()){
            if(spinner_text.equals("Admin")){
                insideLogin();
            }else if(spinner_text.equals("Faculty")){
                insideLogin();
            }else{
                Toast.makeText(this,"Please Choose Admin of Faculty",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Internet not Connected",Toast.LENGTH_SHORT).show();
        }
    }

    private void insideLogin(){
        final String email = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Login User...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    // Sign in success, update UI with the signed-in user's information
                    if(spinner_text .equals("Admin")){
                        Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                        startActivity(intent);
                    }else if(spinner_text .equals("Faculty")){
                        Intent intent = new Intent(LoginActivity.this,FacultyActivity.class);
                        startActivity(intent);
                    }
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    progressDialog.dismiss();
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }

    public void register(View view){
        if(isNetworkAvailable()){
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Internet not Connected",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
