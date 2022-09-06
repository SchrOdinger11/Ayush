package com.aniket.ayush;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Random;

public class Adapter3 extends RecyclerView.Adapter<Adapter3.Viewholder> {

    final Context context;
    final ArrayList<Hospital> courseModelArrayList;

    // Constructor
    public Adapter3(Context context, ArrayList<Hospital> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public Adapter3.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hospital_cards, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter3.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Hospital model = courseModelArrayList.get(position);
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
//        holder.courseNameTV.setText(model.getCourse_name());
//        holder.courseRatingTV.setText("" + model.getCourse_rating());
//        holder.courseIV.setImageResource(model.getCourse_image());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1 = ((AppCompatActivity)context).getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
                BottomSheetDialog dialog = new BottomSheetDialog(context);

                TextView jname = view1.findViewById(R.id.j_name);
                TextView jaddress = view1.findViewById(R.id.addr);
                TextView jopenT = view1.findViewById(R.id.openButton);
                TextView jcloseT = view1.findViewById(R.id.closeButton);
                RatingBar ratingBar = view1.findViewById(R.id.ratingBar_all);
                Button reqCall = view1.findViewById(R.id.formButton);
                Button call = view1.findViewById(R.id.callButton);
                Button web = view1.findViewById(R.id.visitButton);


                ratingBar.setRating(new Random().nextInt(5)+1);
                jaddress.setText(model.getAddress());
                jopenT.setText(model.getO_time());
                jcloseT.setText(model.getC_time());
                jname.setText(model.getName());


                reqCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context.getApplicationContext(), UserForm.class);
                        i.putExtra("hospitalId", model.getDocumentId());
                        context.startActivity(i);
                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions((Activity) context,new String[] {Manifest.permission.CALL_PHONE},101);
                        }
                        else {
                            String dial =  "tel:" + model.getPh_numbers();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(dial));
                            context.startActivity(callIntent);
                        }
                    }
                });

                web.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stackoverflow.com")));
                    }
                });

                dialog.setContentView(view1);
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class Viewholder extends RecyclerView.ViewHolder {
//        private final ImageView courseIV;
//        private final TextView courseNameTV;
//        private final TextView courseRatingTV;
        TextView name;
        TextView address;
        TextView jname;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
//            courseIV = itemView.findViewById(R.id.idIVCourseImage);
//            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
//            courseRatingTV = itemView.findViewById(R.id.idTVCourseRating);
            name = itemView.findViewById(R.id.idTVCourseName);
            address = itemView.findViewById(R.id.idTVCourseRating);
            jname = itemView.findViewById(R.id.j_name);
        }
    }

}