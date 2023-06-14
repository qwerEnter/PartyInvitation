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

    TextView txt1, txt2,txt3;
    Button btnUpdate, btnDelete, btnConfirm;

    ReservationModel reservationModel;

    DatabaseReference myRef;

    Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_reservation2, container, false);

        txt1 = view.findViewById(R.id.txtInvitation);
        txt2 = view.findViewById(R.id.txtName);
        txt3 = view.findViewById(R.id.txtGuest);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnConfirm = view.findViewById(R.id.btnConfirm);


        // Retrieve invitation details from Firebase
        DatabaseReference invitationRef = FirebaseDatabase.getInstance().getReference("invitations").child("-NXh5FdK4z6mxIx0oqzr");

        invitationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the invitation data from the dataSnapshot
                String invitationTitle = dataSnapshot.child("invitationTitle").getValue(String.class);
                String hostName = dataSnapshot.child("hostName").getValue(String.class);
                String guestName = dataSnapshot.child("guestName").getValue(String.class);

                // Set the text for the TextViews
                txt1.setText(invitationTitle);
                txt2.setText(hostName);
                txt3.setText(guestName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur
            }
        });
        return view;
    }
}