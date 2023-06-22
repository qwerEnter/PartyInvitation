package com.example.partyinvitation.Reservation;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.Locale;

public class DateandTime_View extends Fragment {

    Button btnConfirm2;
    TextView txt_Date, txt_Time, txt_Agenda;

    DatabaseReference myRef;
    ReservationModel reservationModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dateand_time__view, container, false);

        txt_Date = view.findViewById(R.id.txtDate);
        txt_Time = view.findViewById(R.id.txtTime);
        txt_Agenda = view.findViewById(R.id.txtAgenda);
        btnConfirm2 = view.findViewById(R.id.btnConfirm2);



        Bundle bundle = getArguments();
        if (bundle != null) {
            String reservationId = bundle.getString("reservationId");
            String invite_date = bundle.getString("inviteDate");
            String invite_time = bundle.getString("inviteTime");
            String party_agenda = bundle.getString("PartyAgenda");

            // Use the retrieved values to display in the TextViews
            txt_Date.setText(invite_date);
            txt_Time.setText(invite_time);
            txt_Agenda.setText(party_agenda);

        /*Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("reservationId")) {
            String reservationId = bundle.getString("reservationId");*/

            // Get the reservation data from Firebase using the retrieved reservationId
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            myRef = databaseRef.child("Reservation").child(reservationId);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        reservationModel = dataSnapshot.getValue(ReservationModel.class);

                        // Set the values of TextViews using reservation data
                        txt_Date.setText(reservationModel.getInvite_date());
                        txt_Time.setText(reservationModel.getInvite_time());
                        txt_Agenda.setText(reservationModel.getParty_agenda());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to retrieve reservation data", Toast.LENGTH_SHORT).show();
                }
            });
        }


        btnConfirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddConFragment1 addCon1 = new AddConFragment1();
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String reservationId = bundle.getString("reservationId");

                    Bundle dateAndTimeBundle = new Bundle();
                    dateAndTimeBundle.putString("reservationId", reservationId);
                    addCon1.setArguments(dateAndTimeBundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, addCon1)
                            .commit();
            }
        }
        });

        return view;
    }
}
