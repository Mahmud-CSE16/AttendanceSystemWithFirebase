package com.example.attendance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentsAdapter extends BaseAdapter {

    Context context;
    List<Student> students;
    String course,section,date;

    public StudentsAdapter(Context context, List<Student> students, String course, String section, String date) {
        this.context = context;
        this.students = students;
        this.course = course;
        this.section = section;
        this.date = date;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View v = layoutInflater.inflate(R.layout.student_list_view_adapter_layout, viewGroup,false);

        TextView id = v.findViewById(R.id.std_id);
        TextView name = v.findViewById(R.id.std_name);
        final CheckBox checkBox = v.findViewById(R.id.checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Attendances/"+course+"/"+section+"/"+date);
                if(checkBox.isChecked()){
                    mDatabase.child(students.get(i).getId()).setValue(students.get(i));
                }else{
                    mDatabase.child(students.get(i).getId()).removeValue();
                }
            }
        });


        id.setText(students.get(i).getId());
        name.setText(students.get(i).getName());

        return v;
    }
}
