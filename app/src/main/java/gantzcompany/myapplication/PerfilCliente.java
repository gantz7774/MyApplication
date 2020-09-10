package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PerfilCliente extends AppCompatActivity {

    private Button mbtnGenerarPedido;
    private TextView mtextviewNombre;

    private String cantidad = "";
    private String nombre = "";
    private String precio = "";
    private String descripcion ="";
    private String direccion ="";
    private EditText etcantidad;
    private EditText etprecio;
    private EditText etdireccion;
    private Spinner spdescripcion;

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mbtnGenerarPedido = (Button) findViewById(R.id.btn_generar_pedido);
        etcantidad = (EditText) findViewById(R.id.editTextCantidad);
        etprecio = (EditText) findViewById(R.id.editTextPrecio);
        etdireccion = (EditText) findViewById(R.id.editTextDireccion);
        spdescripcion = (Spinner) findViewById(R.id.spinnerDescripcion);

        mtextviewNombre = (TextView) findViewById(R.id.tvNombre);

        obtenerDatoUsuario();

        mbtnGenerarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad = etcantidad.getText().toString();
                precio = etprecio.getText().toString();
                direccion = etdireccion.getText().toString();
                nombre = mtextviewNombre.getText().toString();
                descripcion = spdescripcion.getSelectedItem().toString();

                if(!cantidad.isEmpty() && !direccion.isEmpty() ) {

                    registrarPedido();

                }else{
                    Toast.makeText(PerfilCliente.this,"Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void registrarPedido() {

        String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    FirebaseDatabase fb = FirebaseDatabase.getInstance();
                    map.put( "nombre", nombre);
                    map.put( "cantidad", cantidad+" unidades");
                    map.put( "precio", precio+" soles");
                    map.put( "descripcion", descripcion);
                    map.put( "direccion", direccion);
                    map.put( "entregado", false);
                    map.put( "enviando", false);
                    map.put( "id", id);
                    map.put( "id_repartidor", "");


                    mDataBase.child("Pedidos").child(fb.getReference("quiz").push().getKey()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if(task2.isSuccessful()) {
                                Toast.makeText(PerfilCliente.this, "Pedido Generado", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PerfilCliente.this, MapsActivity.class));
                                finish();

                            }else{
                                Toast.makeText(PerfilCliente.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem){

        int id = menuitem.getItemId();
        switch (id){
            case R.id.btnInformacion:
                startActivity(new Intent(PerfilCliente.this, InformacionActivity.class));
                finish();
                break;

            case R.id.btnCerrarSesion:
                    mAuth.signOut();
                    startActivity(new Intent(PerfilCliente.this, MainActivity.class));
                    finish();
                break;

        }

        return super.onOptionsItemSelected(menuitem);
    }
}