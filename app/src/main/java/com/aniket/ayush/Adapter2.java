package com.aniket.ayush;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.io.Serializable;
import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter.ViewHolder>{

    LayoutInflater inflater;
    List<Appointment> appointmentList;
    RequestQueue queue;
    private String JSON_URL = "https://sihmvp1.herokuapp.com/inquiry/";

    public Adapter2(Context ctx, List<Appointment> appointmentList, RequestQueue queue){
        this.inflater = LayoutInflater.from(ctx);
        this.appointmentList = appointmentList;
        this.queue = queue;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cards_layout,parent,false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        // bind the data
        Appointment appointment = appointmentList.get(position);
        holder.hospitalName.setText(appointmentList.get(position).getPatient_name());
        TextView hospitalName;
       // Button accept;

        hospitalName = holder.itemView.findViewById(R.id.label_name_card);
       // accept = holder.itemView.findViewById(R.id.btn_accept1);

//        holder.accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                System.out.println("print hoja bhai "+hospital.getId());
//
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AppointmentData.class);
                i.putExtra("key", (Serializable) appointment).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView hospitalName;
        //Button accept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hospitalName = itemView.findViewById(R.id.label_name_card);
            //accept = itemView.findViewById(R.id.btn_accept1);
            // handle onClick


//            accept.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    approve();
//                }
//            });
        }


        @Override
        public void onClick(View view) {
            int position  =   getAdapterPosition();

        }
    }


}
