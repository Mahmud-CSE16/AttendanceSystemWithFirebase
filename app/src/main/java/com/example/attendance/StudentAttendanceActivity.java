package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentAttendanceActivity extends AppCompatActivity {

    Spinner spinnerCourses,spinnerSections;

    private ProgressDialog progressDialog;

    TextView textViewDate;
    Button buttonDate;
    ListView listView;

    String course,section,date;

    Calendar c;
    DatePickerDialog dpd;
    int mDay;
    int mMonth;
    int mYear;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        ViewById();

        // Get Current Date
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        textViewDate.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);

        progressDialog = new ProgressDialog(this);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.cources, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapter1);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.sections, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSections.setAdapter(adapter2);

        mDatabase = FirebaseDatabase.getInstance().getReference("students");
    }


    public void show(View v){
        course = spinnerCourses.getSelectedItem().toString();
        section = spinnerSections.getSelectedItem().toString();
        date = textViewDate.getText().toString();
        if(course.equals("Select") || section.equals("Select")){
            Toast.makeText(this, "Select Course and Section",Toast.LENGTH_SHORT).show();
        }else{
            getStudents();
        }
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
                StudentsAdapter studentsAdapter = new StudentsAdapter(getApplicationContext(),students,course,section,date);
                listView.setAdapter(studentsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"data fetch failed",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void submit(View view){
        Toast.makeText(getApplicationContext(),"Submit Successfully",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, FacultyActivity.class));
    }

    public void datepicker(View view){

        dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        textViewDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void ViewById(){
        spinnerCourses = findViewById(R.id.spinner_courses);
        spinnerSections = findViewById(R.id.spinner_sections);
        textViewDate = findViewById(R.id.date_picker_textView);
        buttonDate = findViewById(R.id.date_picker);
        listView = findViewById(R.id.listView);
    }
}
