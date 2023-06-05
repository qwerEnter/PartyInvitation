package com.example.partyinvitation.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.partyinvitation.Feedback.FeedbackFragment;
import com.example.partyinvitation.Login.Login;
import com.example.partyinvitation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ReservationMenu extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ReservationFragment reservationFragment = new ReservationFragment();
    FeedbackFragment feedbackFragment = new FeedbackFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_menu);

        // Bottom navigation product
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        // Default launch
        getSupportFragmentManager().beginTransaction().replace(R.id.container, reservationFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.reservation) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, reservationFragment).commit();
                    return true;
                }
                if (item.getItemId() == R.id.feedback) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, feedbackFragment).commit();
                    return true;
                }
                if (item.getItemId() == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(ReservationMenu.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }
}
