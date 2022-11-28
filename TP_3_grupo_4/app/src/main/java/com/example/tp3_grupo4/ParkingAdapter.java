package com.example.tp3_grupo4;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.Parkings;

public class ParkingAdapter extends ArrayAdapter<Parkings> {
    public ParkingAdapter(@NonNull Context context, ArrayList<Parkings> parkingList) {
        super(context, 0, parkingList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) listitemView = LayoutInflater.from(getContext()).inflate(R.layout.parking_list_layout, parent, false);

        Parkings parking = getItem(position);
        TextView parkingInfo = listitemView.findViewById(R.id.parkingInfo);

        parkingInfo.setText(parking.getPatent() + "\n" + parking.getTime());
        return listitemView;
    }
}
