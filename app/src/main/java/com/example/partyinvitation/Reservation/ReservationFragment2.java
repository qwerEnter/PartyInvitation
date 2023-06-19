package com.example.partyinvitation.Reservation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.partyinvitation.Model.ReservationModel;
import com.example.partyinvitation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReservationFragment2 extends Fragment {

    TextView txt1, txt2, txt3;
    Button btnConfirm;

    DatabaseReference reservationRef;

    ReservationModel reservationModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation2, container, false);

        txt1 = view.findViewById(R.id.txtInvitation);
        txt2 = view.findViewById(R.id.txtName);
        txt3 = view.findViewById(R.id.txtGuest);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        // Get the reservation data from Firebase
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        reservationRef = databaseRef.child("Reservation").child("-NXxP_KpNez0Zd1kdVAd");

        reservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ReservationModel reservation = dataSnapshot.getValue(ReservationModel.class);

                    // Set the values of TextViews using reservation data
                    txt1.setText(reservation.getInvite_name());
                    txt2.setText(reservation.getHost_name());
                    txt3.setText(reservation.getGuest_name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to retrieve reservation data", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}