package com.example.limitprint;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private ProgressBar progressBar;

    private LocationManager locationManager;
    private Location lastLocation;
    private float[] tempDistance = new float[1];
    private float sum ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        button = (Button) findViewById(R.id.start);
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(20000);
        progressBar.setProgress(0);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button.getText().equals("start")) {
                    button.setText("Clicked!");
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            if (lastLocation != null) {
                                Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(), location.getLatitude(), location.getLongitude(), tempDistance);
                                    sum += tempDistance[0];
                                    progressBar.setProgress((int)sum);
                                textView.setText(Float.toString(sum));
                            }
                            lastLocation = location;
                        }
                    });
                }
            }
        });
    }

    public void endTravel(View view) {
    }

    public void startTravel(View view) {
    }
}