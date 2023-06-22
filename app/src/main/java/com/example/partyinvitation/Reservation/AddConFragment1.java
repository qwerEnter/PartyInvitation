package com.example.partyinvitation.Reservation;

import android.location.Address;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.ktx.Firebase;


public class AddConFragment1 extends Fragment {

TextView step2, tvAddress, tvContact;
EditText address, contact;
MaterialButton btn1;
ReservationModel reservationModel;
DatabaseReference myRef;
String reservationId;
//private int reservationIdCounter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_con1, container, false);

        // Retrieve the reservationId from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            reservationId = bundle.getString("reservationId");
        }

        // Initialize the database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Reservation");

        step2 = view.findViewById(R.id.step2);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvContact = view.findViewById(R.id.tvContact);
        address = view.findViewById(R.id.address);
        contact = view.findViewById(R.id.phone);

        //reservationModel = new ReservationModel();
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("Reservation");

        MaterialButton button = view.findViewById(R.id.btn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reservationRef = myRef.child(reservationId);

                reservationRef.child("address").setValue(address.getText().toString());
                reservationRef.child("contact").setValue(contact.getText().toString());

                AddConFragment2 addcon2 = new AddConFragment2();
                Bundle bundle = new Bundle();
                bundle.putString("reservationId", reservationId);
                addcon2.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, addcon2)
                        .commit();
            }
        });

        //myRef.addValueEventListener(new ValueEventListener() {
            /*@Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reservationIdCounter = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newReservationRef = myRef.push();
                final String[] reservationId = {newReservationRef.getKey()};

                reservationModel.setReservationId(reservationId[0]);
                reservationModel.setAddress(address.getText().toString());
                reservationModel.setContact(contact.getText().toString());

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
                            Toast.makeText(getActivity(), "Failed to save information", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });*/

        return view;
    }


}