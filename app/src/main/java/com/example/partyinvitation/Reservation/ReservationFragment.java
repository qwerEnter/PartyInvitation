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

import com.example.partyinvitation.Model.ReservationModel;
import com.example.partyinvitation.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ReservationFragment extends Fragment {

    MaterialButton button;
    EditText invitationTitle, hostName, guestName;
    ReservationModel reservationModel;
    DatabaseReference myRef;
    private int reservationIdCounter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation1, container, false);

        button = view.findViewById(R.id.btnSave);
        invitationTitle = view.findViewById(R.id.txtField1);
        hostName = view.findViewById(R.id.txtField2);
        guestName = view.findViewById(R.id.txtField3);

        reservationModel = new ReservationModel();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Reservation");

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newReservationRef = myRef.push();
                final String[] reservationId = {newReservationRef.getKey()};

                reservationModel.setReservationId(reservationId[0]);
                reservationModel.setInvite_name(invitationTitle.getText().toString());
                reservationModel.setHost_name(hostName.getText().toString());
                reservationModel.setGuest_name(guestName.getText().toString());

                newReservationRef.setValue(reservationModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            reservationId[0] = ref.getKey(); // Get the generated reservationId
                            ReservationFragment2 reservationFragment2 = new ReservationFragment2();

                            Bundle bundle = new Bundle();
                            bundle.putString("reservationId", reservationId[0]);
                            reservationFragment2.setArguments(bundle);

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, reservationFragment2).commit();
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
