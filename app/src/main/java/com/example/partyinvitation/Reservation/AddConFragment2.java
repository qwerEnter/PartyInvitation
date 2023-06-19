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
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddConFragment2 extends Fragment {

    TextView address, contact;
    MaterialButton confirm;

    DatabaseReference reservationRef;

    ReservationModel reservationModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_con2, container, false);

        address = view.findViewById(R.id.displayAdd);
        contact = view.findViewById(R.id.displayCon);
        confirm = view.findViewById(R.id.btn2);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("reservationId")) {
            String reservationId = bundle.getString("reservationId");

            // Get the reservation data from Firebase using the retrieved reservationId
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            reservationRef = databaseRef.child("Reservation").child(reservationId);

            reservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        reservationModel = dataSnapshot.getValue(ReservationModel.class);

                        // Set the values of TextViews using reservation data
                        address.setText(reservationModel.getAddress());
                        contact.setText(reservationModel.getContact());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to retrieve address and contact information", Toast.LENGTH_SHORT).show();
                }

            });
        }

        return view;
    }
}
