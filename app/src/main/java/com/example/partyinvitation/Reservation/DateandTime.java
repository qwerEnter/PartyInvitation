
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

    private EditText editDate, editTime, editAgenda;
    private Button btnSave;

    ReservationModel reservationModel;
    DatabaseReference myRef;
    private int reservationIdCounter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dateand_time, container, false);

        // Obtain a reference to the Firebase Realtime Database
        reservationModel = new ReservationModel();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Reservation");

        editDate = view.findViewById(R.id.editDate);
        editTime = view.findViewById(R.id.editTime);
        editAgenda = view.findViewById(R.id.editAgenda);
        btnSave = view.findViewById(R.id.btnSave);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                DatabaseReference newReservationRef = myRef.push();
                final String[] reservationId = {newReservationRef.getKey()};

                reservationModel.setReservationId(reservationId[0]);
                reservationModel.setInvite_date(editDate.getText().toString());
                reservationModel.setInvite_time(editTime.getText().toString());
                reservationModel.setParty_agenda(editAgenda.getText().toString());

                newReservationRef.setValue(reservationModel, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        reservationId[0] = ref.getKey(); // Get the generated reservationId
                        DateandTime_View dateandTime_view = new DateandTime_View();

                        Bundle bundle = new Bundle();
                        bundle.putString("reservationId", reservationId[0]);
                        dateandTime_view.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, dateandTime_view).commit();
                    } else {
                        Toast.makeText(getActivity(), "Failed to save reservation", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    });

        return view;
}
}
