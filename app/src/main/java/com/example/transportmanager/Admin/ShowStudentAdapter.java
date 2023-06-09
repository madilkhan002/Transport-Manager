package com.example.transportmanager.Admin;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportmanager.R;
import com.example.transportmanager.Student.Student;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowStudentAdapter extends RecyclerView.Adapter<ShowStudentAdapter.ViewHolder> {

    ArrayList<Student> allStudents;
    Context context;
    private OnItemClickListener onItemClickListener;

    public ShowStudentAdapter(ArrayList<Student> studentArrayList, Context context) {
        this.allStudents = studentArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_students_custom_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowStudentAdapter.ViewHolder holder, int position) {
        holder.Username.setText(allStudents.get(position).getUsername());
        holder.Password.setText(allStudents.get(position).getPassword());
        holder.Route.setText(allStudents.get(position).getRoute());
        if(allStudents.get(position).isEligilble() == true)
        {
            holder.Eligibility.setText("YES");
        }
        else
        {
            holder.Eligibility.setText("NO");
        }
    }

    @Override
    public int getItemCount() {
        return allStudents.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //interface
    public interface OnItemClickListener {
        void onItemClick(Student clickedStudent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Username,Password,Route,Eligibility;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Username=itemView.findViewById(R.id.textViewUsernameStudent);
            Password=itemView.findViewById(R.id.textViewPasswordStudent);
            Route=itemView.findViewById(R.id.textViewRouteStudent);
            Eligibility=itemView.findViewById(R.id.textViewEligibilityStudent);


            // get the specific row click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        Student clickedStudent = allStudents.get(position);
                        onItemClickListener.onItemClick(clickedStudent);
                    }
                }
            });

        }
    }
}
