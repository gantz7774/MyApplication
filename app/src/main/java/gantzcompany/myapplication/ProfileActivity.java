package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Button mbtnCerrarSesion;
    private Button mbtnGenerarPedido;
    private TextView mtextviewNombre;

    private String cantidad = "";
    private String nombre = "";
    private String precio = "";
    private String descripcion ="";
    private EditText etcantidad;
    private EditText etprecio;
    private Spinner spdescripcion;

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mbtnCerrarSesion = (Button) findViewById(R.id.btn_cerrar_sesion);
        mbtnGenerarPedido = (Button) findViewById(R.id.btn_generar_pedido);
        etcantidad = (EditText) findViewById(R.id.editTextCantidad);
        etprecio = (EditText) findViewById(R.id.editTextPrecio);
        spdescripcion = (Spinner) findViewById(R.id.spinnerDescripcion);

        mtextviewNombre = (TextView) findViewById(R.id.tvNombre);

        mbtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });
        obtenerDatoUsuario();

        mbtnGenerarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad = etcantidad.getText().toString();
                precio = etprecio.getText().toString();
                nombre = mtextviewNombre.getText().toString();
                /*descripcion = spdescripcion.getOnItemSelectedListener().toString();
                */
                if(!cantidad.isEmpty()) {

                    registrarPedido();

                }else{
                    Toast.makeText(ProfileActivity.this,"Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }

                /*startActivity(new Intent(ProfileActivity.this, MapsActivity.class));*/

            }
        });

    }

    private void registrarPedido() {

                    Map<String, Object> map = new HashMap<>();
                    map.put( "nombre", nombre);
                    map.put( "cantidad", cantidad+" unidades");
                    map.put( "precio", precio+" soles");
                    map.put( "descripcion", "llenado");


                    String id = mAuth.getCurrentUser().getUid();

                    mDataBase.child("Pedidos").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if(task2.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "Pedido Generado", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProfileActivity.this, MapsActivity.class));
                                finish();

                            }else{
                                Toast.makeText(ProfileActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
            }



    private void obtenerDatoUsuario(){
        String id = mAuth.getCurrentUser().getUid();
        mDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();


                    mtextviewNombre.setText(nombre);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}