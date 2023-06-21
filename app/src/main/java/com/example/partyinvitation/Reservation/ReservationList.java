package com.example.partyinvitation.Reservation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partyinvitation.Model.ReservationModel;
import com.example.partyinvitation.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ReservationList extends AppCompatActivity {

    RecyclerView recyclerView;

    ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ReservationModel> options =
                new FirebaseRecyclerOptions.Builder<ReservationModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reservation"), ReservationModel.class)
                        .build();

        reservationAdapter = new ReservationAdapter(options);
        recyclerView.setAdapter(reservationAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reservationAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reservationAdapter.stopListening();
    }
}