package com.aniket.ayush;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UserMainActivity extends AppCompatActivity {

    Button logout;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Button sendData;


    Button filterDistance;
    String item1 ;
    String item2 ;
    String item3;
    String item4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        sendData=(Button) findViewById(R.id.button5);

        spinner1 = (Spinner) findViewById(R.id.option_1);
        spinner2 = (Spinner) findViewById(R.id.option_2);
        spinner3 = (Spinner) findViewById(R.id.option_3);
        spinner4 = (Spinner) findViewById(R.id.distance);



        filterDistance=(Button)(findViewById(R.id.filterdistance));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Symptoms, R.layout.spinner_item2);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Symptoms2, R.layout.spinner_item2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Symptoms3, R.layout.spinner_item2);
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.Distance, R.layout.spinner_item2);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);



        filterDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item4 = spinner4.getSelectedItem().toString();
                Intent i=new Intent(getApplicationContext(),Map_Display_User_Location_Hospital.class);
                i.putExtra("flag","true");
                i.putExtra("Spinner4", item4);
                startActivity(i);
            }
        });



    }


    public void sendData(View view){


        item1 = spinner1.getSelectedItem().toString();

        item2 = spinner2.getSelectedItem().toString();

        item3 = spinner3.getSelectedItem().toString();

        item4 = spinner4.getSelectedItem().toString();

        Intent i=new Intent(getApplicationContext(),Map_Display_User_Location_Hospital.class);
        i.putExtra("Spinner1",item1);
        i.putExtra("Spinner2",item2);
        i.putExtra("Spinner3",item3);
        i.putExtra("Spinner4", item4);
        i.putExtra("flag","false");
//        Bundle extras = new Bundle();
//        extras.putString("Spinner1",item1);
//        extras.putString("Spinner2",item2);
//        extras.putString("Spinner3",item3);
        //  i.putExtras(extras);

        startActivity(i);

    }


    public void startChatBot(View view) {


        Intent i=new Intent(getApplicationContext(),chatBot.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

}