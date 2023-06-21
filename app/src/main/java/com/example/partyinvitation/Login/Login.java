package com.example.partyinvitation.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.partyinvitation.R;
import com.example.partyinvitation.Reservation.ReservationMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Login extends AppCompatActivity implements SensorEventListener {

    EditText email,password;
    TextView textView;
    Button btnregister,btnlogin;
    FirebaseAuth mAuth;

    //declare variable sensor part (NUR IZZLIN BINTI AZMAN)
    SensorManager sensorManager;
    Sensor sensor;
    Context context;

    boolean success;

    // stay in home page if still online
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), ReservationMenu.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //login with email and password
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnregister = findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);
        textView = findViewById(R.id.textViewreset);

        //
        sensorManager=(SensorManager)getSystemService(Service.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //button register
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        //button register
        btnregister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        //button login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }
        });



    }

    //method part sensor light
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void  onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, sensor,sensorManager.SENSOR_DELAY_NORMAL);
    }

    //implement Sensor Light (NUR IZZLIN BINTI AZMAN CB20012)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_LIGHT);
        {
            if (event.values[0]<15)
            {
                permission();
                setBrightness(240);
            }
            else if (event.values[0]>80)
            {
                permission();
                setBrightness(50);
            }
        }

    }

    private void permission(){
        boolean value;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            value = Settings.System.canWrite(getApplicationContext());
            if (value){
                success=true;
            }
            else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:"+getApplicationContext().getOpPackageName()));
                startActivityForResult(intent, 100);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean value = Settings.System.canWrite(getApplicationContext());
                if (value) {
                    success = true;
                } else {
                    Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void setBrightness(int brightness)
    {
        if (brightness<0)
        {
            brightness=0;
        }
        else if (brightness>255)
        {
            brightness=255;
        }
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS,brightness);
    }



    //login with email n password
    private void loginUser() {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6)
        {
            Toast.makeText(this, "Password Must Be More Than 6", Toast.LENGTH_SHORT).show();
            return;
        }

        //login user db
        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(Login.this, "Welcome", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ReservationMenu.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Login.this, "This Account Does Not Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}