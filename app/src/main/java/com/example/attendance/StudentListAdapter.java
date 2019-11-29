package com.example.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentListAdapter extends BaseAdapter {

    Context context;
    List<Student> students;

    public StudentListAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
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
        View v = layoutInflater.inflate(R.layout.student_list_adapter_layout, viewGroup,false);

        TextView id = v.findViewById(R.id.std_id);
        TextView name = v.findViewById(R.id.std_name);
        ImageView imageView = v.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("students");
                mDatabase.child(students.get(i).getId()).removeValue();

            }
        });


        id.setText(students.get(i).getId());
        name.setText(students.get(i).getName());

        return v;
    }
}
