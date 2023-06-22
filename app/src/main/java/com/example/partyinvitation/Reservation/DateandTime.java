
package com.example.partyinvitation.Reservation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.partyinvitation.Model.ReservationModel;
import com.example.partyinvitation.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DateandTime extends Fragment {

    DatabaseReference myRef;
    EditText inviteDate, inviteTime, partyAgenda;
    String reservationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dateand_time, container, false);

        // Retrieve the reservationId from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            reservationId = bundle.getString("reservationId");
        }

        // Initialize the database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Reservation");

        inviteDate = view.findViewById(R.id.editDate);
        inviteTime = view.findViewById(R.id.editTime);
        partyAgenda = view.findViewById(R.id.editAgenda);

        MaterialButton button = view.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reservationRef = myRef.child(reservationId); // Get the reference to the reservation with the reservationId

                // Save the date and time details to the reservation
                reservationRef.child("invite_date").setValue(inviteDate.getText().toString());
                reservationRef.child("invite_time").setValue(inviteTime.getText().toString());
                reservationRef.child("party_agenda").setValue(partyAgenda.getText().toString());

                // Continue with further actions or navigate to other fragments/activities
                DateandTime_View dateandTimeView = new DateandTime_View();
                Bundle bundle = new Bundle();
                bundle.putString("reservationId", reservationId);
                dateandTimeView.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, dateandTimeView)
                        .commit();
            }
        });

        return view;
    }
}

/*public class DateandTime extends Fragment {

    private EditText editDate, editTime, editAgenda;
    private Button btnSave;

    ReservationModel reservationModel;
    DatabaseReference myRef;
    DatabaseReference newReservationRef; // Declare newReservationRef as a class-level variable
    private int reservationIdCounter = 0; // Declare reservationIdCounter as a class-level variable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dateand_time, container, false);

        // Obtain a reference to the Firebase Realtime Database
        reservationModel = new ReservationModel();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Reservation");

        editDate = view.findViewById(R.id.editDate);
        editTime = view.findViewById(R.id.editTime);
        editAgenda = view.findViewById(R.id.editAgenda);
        btnSave = view.findViewById(R.id.btnSave);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Update the reservationIdCounter based on the data in the database
                reservationIdCounter = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newReservationRef == null) {
                    newReservationRef = myRef.push();
                }
                final String reservationId = newReservationRef.getKey(); // Get the generated reservationId

                ReservationModel reservationModel = new ReservationModel();
                reservationModel.setReservationId(reservationId);
                reservationModel.setInvite_date(editDate.getText().toString());
                reservationModel.setInvite_time(editTime.getText().toString());
                reservationModel.setParty_agenda(editAgenda.getText().toString());

                newReservationRef.setValue(reservationModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            DateandTime_View dateandTime_view = new DateandTime_View();

                            Bundle bundle = new Bundle();
                            bundle.putString("reservationId", reservationId);
                            dateandTime_view.setArguments(bundle);

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, dateandTime_view).commit();
                        } else {
                            Toast.makeText(getActivity(), "Failed to save reservation", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });*/

   /*     return view;
    }
}*/

