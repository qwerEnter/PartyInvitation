package com.example.partyinvitation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.partyinvitation.Model.ReservationModel;
import com.example.partyinvitation.Reservation.ReservationFragment2;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateandTime extends Fragment {
    MaterialButton button;
    EditText invitationDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // test
        View view = inflater.inflate(R.layout.fragment_dateand_time, container, false);


        button =view.findViewById(R.id.btnSave2);
        invitationDate = view.findViewById(R.id.editTextDate);



        return view;
    }
}