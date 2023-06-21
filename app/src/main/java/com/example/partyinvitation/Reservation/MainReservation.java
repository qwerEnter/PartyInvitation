package com.example.partyinvitation.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.partyinvitation.R;

public class MainReservation extends Fragment {

    Button btnStart, btnDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_reservation, container, false);

        btnStart = view.findViewById(R.id.btnReservation);
        btnDisplay = view.findViewById(R.id.btnDisplay);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationFragment reservationFragment = new ReservationFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, reservationFragment).commit();
            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ReservationList.class);
                startActivity(intent);
            }
        });

        return view;
    }
}