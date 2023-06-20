package com.example.partyinvitation.Reservation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.partyinvitation.Model.ReservationModel;
import com.example.partyinvitation.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateandTime extends Fragment {
    MaterialButton button;
    EditText invitationDate, invitationTime, partyAgenda;

    ReservationModel reservationModel;
    DatabaseReference myRef;

    private int reservationIdCounter = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dateand_time, container, false);

        button = view.findViewById(R.id.btnSave2);
        invitationDate = view.findViewById(R.id.editTextDate);
        invitationTime = view.findViewById(R.id.editTextTime);
        partyAgenda = view.findViewById(R.id.editTextTextMultiLine);

        ReservationModel reservationModel = new ReservationModel();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Reservation");
        // Remove "DatabaseReference" before myRef

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reservationIdCounter = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newReservationRef = myRef.push();
                final String[] reservationId = {newReservationRef.getKey()};

                String dateString = invitationDate.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date date = sdf.parse(dateString);
                    reservationModel.setInvite_date(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String timeString = invitationTime.getText().toString();
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());
                try {
                    Time time = (Time) sdf2.parse(timeString);
                    reservationModel.setInvite_time(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                reservationModel.setReservationId(reservationId[0]);
                reservationModel.setParty_agenda(partyAgenda.getText().toString());

                newReservationRef.setValue(reservationModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            reservationId[0] = ref.getKey(); // Get the generated reservationId
                            DateandTime_View dateAndTimeViewFragment = new DateandTime_View();

                            Bundle bundle = new Bundle();
                            bundle.putString("reservationId", reservationId[0]);
                            dateAndTimeViewFragment.setArguments(bundle);

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, dateAndTimeViewFragment)
                                    .commit();
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
