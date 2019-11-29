package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    ListView listView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        ViewById();
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference("students");

        getStudents();
    }

    private void getStudents(){

        progressDialog.setMessage("Load Students...");
        progressDialog.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Student> students = new ArrayList<Student>();
                progressDialog.dismiss();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Student student = postSnapshot.getValue(Student.class);
                    students.add(student);
                }
                StudentListAdapter adapter = new StudentListAdapter(getApplicationContext(),students);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"data fetch failed",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void ViewById(){
        listView = findViewById(R.id.listView);
    }
}
