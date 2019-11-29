package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    EditText editTextName,editTextStdId;
    Spinner spinnerDepts,spinnerSections;

    private ProgressDialog progressDialog;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        ViewById();

        progressDialog = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.depts, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepts.setAdapter(adapter1);

        progressDialog = new ProgressDialog(this);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.sections, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSections.setAdapter(adapter2);
    }

    public void addStudent(View view){

        if(spinnerDepts.getSelectedItem().toString().equals("CSE/BBA/SWE") || spinnerSections.getSelectedItem().toString().equals("A/B/C/D/E") ){
            Toast.makeText(this,"Please Choose Depertment and Sections",Toast.LENGTH_SHORT).show();
        }else{
            insideAddStudent();
        }
    }

    private void insideAddStudent(){
        final String name = editTextName.getText().toString().trim();
        final String std_id = editTextStdId.getText().toString().trim();
        final String dept = spinnerDepts.getSelectedItem().toString();
        final String section = spinnerSections.getSelectedItem().toString();

        Student student = new Student(std_id, name, dept, section);
//        String key = mDatabase.child("students").push().getKey();
        mDatabase.child("students").child(std_id).setValue(student)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Student add successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddStudentActivity.this,AdminActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Student Add failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void ViewById(){
        editTextName = findViewById(R.id.editText_name);
        editTextStdId = findViewById(R.id.editText_std_id);
        spinnerDepts = findViewById(R.id.spinner_depts);
        spinnerSections = findViewById(R.id.spinner_sections);
    }
}
