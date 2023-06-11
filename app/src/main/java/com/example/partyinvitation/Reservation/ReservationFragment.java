package com.example.partyinvitation.Reservation;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

    EditText invitationTitle,hostName,guestName;

    ReservationModel reservationModel;

    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // test
        View view = inflater.inflate(R.layout.fragment_reservation1, container, false);


        button = view.findViewById(R.id.btnSave);
        invitationTitle = view.findViewById(R.id.txtField1);
        hostName = view.findViewById(R.id.txtField2);
        guestName = view.findViewById(R.id.txtField3);

        reservationModel = new ReservationModel();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Reservation");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //insert function - add reservation data in the Reservation Table
                reservationModel.setInvite_name(invitationTitle.getText().toString());
                reservationModel.setHost_name(hostName.getText().toString());
                reservationModel.setGuest_name(guestName.getText().toString());
                myRef.push().setValue(reservationModel);

                //Once the database is insert will direct tu ReservationFragment2 page
                ReservationFragment2 reservationFragment2 = new ReservationFragment2();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, reservationFragment2).commit();

            }
        });

        return view;
    }
}