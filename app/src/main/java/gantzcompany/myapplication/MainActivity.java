package gantzcompany.myapplication;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button btn_ir_login, btn_ir_registrarse;
    private FirebaseAuth nAuth;
    private DatabaseReference mDataBase;

    private FusedLocationProviderClient mFusedLocationClient;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        nAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

       mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        subirLatLongFirebase();

        btn_ir_login = findViewById(R.id.btn_ir_login);
        btn_ir_registrarse = findViewById(R.id.btn_ir_registrarse);

        btn_ir_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
                //finish();
            }
        });

        btn_ir_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Login.class));
                //finish();
            }
        });
    }

   private void subirLatLongFirebase() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.e("Latitud: ", location.getLatitude()+" Longitud: "+location.getLongitude());
                        }
                    }
                });

    }

    @Override
    protected void onStart() {

        super.onStart();
        if(nAuth.getCurrentUser() != null){
            mDataBase = FirebaseDatabase.getInstance().getReference();
            String id = nAuth.getCurrentUser().getUid();
            mDataBase.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Tipo = dataSnapshot.child("tipo").getValue().toString();
                    switch (Tipo)
                    {
                        case "1":
                            startActivity(new Intent(MainActivity.this, PerfilCliente.class));

                            break;
                        case "2":
                            startActivity(new Intent(MainActivity.this, PerfilRepartidor.class));

                            break;
                        case"3":
                            startActivity(new Intent(MainActivity.this, PerfilAdmin.class));

                            break;
                    }


                    Toast.makeText(MainActivity.this, Tipo, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //startActivity(new Intent(MainActivity.this, PerfilCliente.class));
            //finish();
        }

    }
}
