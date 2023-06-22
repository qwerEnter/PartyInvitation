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

    DatabaseReference myRef;

    ReservationModel reservationModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation2, container, false);

        txt1 = view.findViewById(R.id.txtInvitation);
        txt2 = view.findViewById(R.id.txtName);
        txt3 = view.findViewById(R.id.txtGuest);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String reservationId = bundle.getString("reservationId");
            String inviteName = bundle.getString("inviteName");
            String hostName = bundle.getString("hostName");
            String guestName = bundle.getString("guestName");

            // Use the retrieved values to display in the TextViews
            txt1.setText(inviteName);
            txt2.setText(hostName);
            txt3.setText(guestName);

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
                        txt1.setText(reservationModel.getInvite_name());
                        txt2.setText(reservationModel.getHost_name());
                        txt3.setText(reservationModel.getGuest_name());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to retrieve reservation data", Toast.LENGTH_SHORT).show();
                }

            });
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateandTime dateandTime = new DateandTime();
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String reservationId = bundle.getString("reservationId");

                    Bundle dateAndTimeBundle = new Bundle();
                    dateAndTimeBundle.putString("reservationId", reservationId);

                    dateandTime.setArguments(dateAndTimeBundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, dateandTime)
                            .commit();


               /* Bundle bundle = new Bundle();
                bundle.putString("reservationId", reservationModel.getReservationId());
                dateandTime.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, dateandTime).commit();
*/
                }
            }
        });

        return view;
}
}