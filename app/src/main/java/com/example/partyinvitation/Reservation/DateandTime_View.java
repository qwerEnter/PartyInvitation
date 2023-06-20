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

    Button btnConfirm;
    TextView txt_Date, txt_Time, txt_Agenda;

    DatabaseReference reservationRef;
    ReservationModel reservationModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dateand_time__view, container, false);

        btnConfirm = view.findViewById(R.id.btnConfirm2);
        txt_Date = view.findViewById(R.id.txtDate);
        txt_Time = view.findViewById(R.id.txtTime);
        txt_Agenda = view.findViewById(R.id.txtAgenda);

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
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

                        String inviteDate = dateFormat.format(reservationModel.getInvite_date());
                        String inviteTime = timeFormat.format(reservationModel.getInvite_time());


                        txt_Date.setText(inviteDate);
                        txt_Time.setText(inviteTime);
                        txt_Agenda.setText(reservationModel.getParty_agenda());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to retrieve reservation data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
