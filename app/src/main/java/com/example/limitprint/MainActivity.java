package com.example.limitprint;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ProgressBar progressBar;

    private LocationManager locationManager;
    private Location lastLocation;
    private float[] tempDistance = new float[1];
    private float sum;
    private LocationListener locationListener;
    private boolean[] notifArray = {false, false, false};
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();


        button = (Button) findViewById(R.id.start);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(35000);
        progressBar.setProgress(0);

        locationListener = locationListenerInit();

        button.setOnClickListener(buttonInit());

        createNotificationChannels();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        startService(new Intent(this, MyService.class));
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    "channel1",
                    "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    private LocationListener locationListenerInit() {
        return location -> {
            if (lastLocation != null) {
                Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(), location.getLatitude(), location.getLongitude(), tempDistance);
                sum += tempDistance[0];
                progressBar.setProgress((int) sum);
            }
            lastLocation = location;
            if (sum > (float) progressBar.getMax() / 2 && !notifArray[0]) {
                notifArray[0] = true;
                Notification notification = new NotificationCompat.Builder(this, "channel1")
                        .setSmallIcon(R.drawable.wind)
                        .setContentTitle("(CO2)unter Alert")
                        .setContentText("You have driven over 50% of a sustainable carbon footprint!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("You have driven over 50% of a sustainable carbon footprint!"))
                        .build();
                notificationManagerCompat.notify(1,notification);
            }
            if (sum > (float) progressBar.getMax() / (float) (4/3) && !notifArray[1]) {
                notifArray[1] = true;
                Notification notification = new NotificationCompat.Builder(this, "channel1")
                        .setSmallIcon(R.drawable.wind)
                        .setContentTitle("(CO2)unter Alert")
                        .setContentText("You have driven over 75% of a sustainable carbon footprint! Try and minimize your driving time and maximize other carbon efficient methods!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("You have driven over 75% of a sustainable carbon footprint! Try and minimize your driving time and maximize other carbon efficient methods!"))
                        .build();
                notificationManagerCompat.notify(2,notification);
            }
            if (sum > (float) progressBar.getMax() && !notifArray[2]) {
                notifArray[2] = true;
                Notification notification = new NotificationCompat.Builder(this, "channel1")
                        .setSmallIcon(R.drawable.wind)
                        .setContentTitle("(CO2)unter Alert")
                        .setContentText("You have driven over 100% of a sustainable carbon footprint! Try and minimize your driving time and maximize other carbon efficient methods!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("You have driven over 100% of a sustainable carbon footprint! Try and minimize your driving time and maximize other carbon efficient methods!"))
                        .build();
                notificationManagerCompat.notify(3,notification);
            }
        };
    }

    private View.OnClickListener buttonInit() {
        return v -> {
            if (button.getText().equals("start")) {
                button.setText("end");
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
            } else {
                button.setText("start");
                locationManager.removeUpdates(locationListener);
            }
        };
    }

}