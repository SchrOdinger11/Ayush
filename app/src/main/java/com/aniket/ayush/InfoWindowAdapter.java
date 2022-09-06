package com.aniket.ayush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private View view;

    private FragmentActivity myContext;

    public InfoWindowAdapter(FragmentActivity aContext) {
        this.myContext = aContext;

        LayoutInflater inflater = (LayoutInflater) myContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.custom_marker,
                null);
    }

    @Override
    public View getInfoContents(Marker marker) {

        if (marker != null
                && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {

        final String title = marker.getTitle();
        final TextView titleUi = ((TextView) view.findViewById(R.id.title));
//        Button badge = (Button) view.findViewById(R.id.badge);
//
//        badge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(myContext.getApplicationContext(),"Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
        if (title != null) {
            titleUi.setText(title);
        } else {
            titleUi.setText("");
            titleUi.setVisibility(View.GONE);
        }

        final String snippet = marker.getSnippet();
        final TextView snippetUi = ((TextView) view
                .findViewById(R.id.snippet));

        if (snippet != null) {

            String[] SnippetArray = snippet.split(" ");


            snippetUi.setText(SnippetArray[0]);
        } else {
            snippetUi.setText("");
        }

        return view;
    }
}