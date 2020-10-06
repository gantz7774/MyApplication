package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformacionActivity extends AppCompatActivity {

    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    private TextView mtextviewNombre;
    private TextView mtextviewCorreo;
    private TextView mtextviewTelefono;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        this.setTitle(R.string.informacion_activity);
        //toolbar.setTitleTextColor(0xFF00FF00);


        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mtextviewNombre = (TextView) findViewById(R.id.textviewNombreInfo);
        mtextviewCorreo = (TextView) findViewById(R.id.textviewCorreoInfo);
        mtextviewTelefono = (TextView) findViewById(R.id.textviewTelefonoInfo);

        obtenerDatoUsuario();
    }

    private void obtenerDatoUsuario(){
        String id = mAuth.getCurrentUser().getUid();
        mDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String correo = dataSnapshot.child("correo").getValue().toString();
                    String telefono = dataSnapshot.child("telefono").getValue().toString();


                    mtextviewNombre.setText(nombre);
                    mtextviewCorreo.setText(correo);
                    mtextviewTelefono.setText(telefono);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}