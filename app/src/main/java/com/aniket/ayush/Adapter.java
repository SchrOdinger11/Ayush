package com.aniket.ayush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {
    LayoutInflater inflater;
    List<Hospital> hospitals;
    RequestQueue queue;
    private String JSON_URL = "https://sihmvp1.herokuapp.com/admin";


    public Adapter(Context ctx, List<Hospital> hospitals, RequestQueue queue){
        this.inflater = LayoutInflater.from(ctx);
        this.hospitals = hospitals;
        this.queue = queue;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cards_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        Hospital hospital = hospitals.get(position);
        holder.hospitalName.setText(hospitals.get(position).getName());
        TextView hospitalName;
        Button accept;

        hospitalName = holder.itemView.findViewById(R.id.label_name1);
        accept = holder.itemView.findViewById(R.id.btn_reject1);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("print hoja bhai "+hospital.getId());
                approve(hospital);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reject(hospital);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), HospitalData.class);
                i.putExtra("key", hospital).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                view.getContext().startActivity(i);
            }
        });
    }

    public void reject(Hospital h){
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.PUT, JSON_URL+"/disapprove/"+h.getId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(accept.getContext(), "Succesfully approved", Toast.LENGTH_SHORT).show();
//                System.out.println("yess nikal gya");
                hospitals.remove(hospitals.indexOf(h));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }


    public void approve(Hospital h){


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.PUT, JSON_URL+"/approve/"+h.getId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(accept.getContext(), "Succesfully approved", Toast.LENGTH_SHORT).show();
//                System.out.println("yess nikal gya");
                hospitals.remove(hospitals.indexOf(h));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }




    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView hospitalName;
        Button accept, reject;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hospitalName = itemView.findViewById(R.id.label_name1);
            accept = itemView.findViewById(R.id.btn_accept1);
            reject = itemView.findViewById(R.id.btn_reject1);

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
