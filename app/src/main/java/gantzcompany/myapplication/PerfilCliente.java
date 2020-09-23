package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String descripcion = "";
    private String direccion = "";
    private EditText etcantidad;
    private TextView etprecio;
    private EditText etdireccion;
    private Spinner spdescripcion;
    private FusedLocationProviderClient mFusedLocationClient;

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
        etprecio = (TextView) findViewById(R.id.editTextPrecio);
        etdireccion = (EditText) findViewById(R.id.editTextDireccion);
        spdescripcion = (Spinner) findViewById(R.id.spinnerDescripcion);

        mtextviewNombre = (TextView) findViewById(R.id.tvNombre);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        obtenerDatoUsuario();

        etcantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                actualizarPrecio();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spdescripcion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                actualizarPrecio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mbtnGenerarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad = etcantidad.getText().toString();
                precio = etprecio.getText().toString();
                direccion = etdireccion.getText().toString();
                nombre = mtextviewNombre.getText().toString();
                descripcion = spdescripcion.getSelectedItem().toString();

                //Toast.makeText(view.getContext(), descripcion, Toast.LENGTH_SHORT).show();

                if (!cantidad.isEmpty() && !direccion.isEmpty()) {

                    registrarPedido();

                } else {
                    Toast.makeText(PerfilCliente.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void registrarPedido() {

        String id = mAuth.getCurrentUser().getUid();
        final Map<String, Object> map = new HashMap<>();
        final FirebaseDatabase fb = FirebaseDatabase.getInstance();
        map.put("nombre", nombre);
        map.put("cantidad", cantidad + " unidades");
        map.put("precio", precio + " soles");
        map.put("descripcion", descripcion);
        map.put("direccion", direccion);
        map.put("entregado", false);
        map.put("enviando", false);

        map.put("id", id);
        map.put("id_repartidor", "");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                            map.put("latitud", location.getLatitude());
                            map.put("longitud", location.getLongitude());
                            mDataBase.child("Pedidos").child(fb.getReference("quiz").push().getKey()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {

                                    if(task2.isSuccessful()) {
                                        Toast.makeText(PerfilCliente.this, "Pedido Generado", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(PerfilCliente.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            Log.e("Latitud: ", location.getLatitude() + " Longitud: " + location.getLongitude());
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

    public void actualizarPrecio(){
        String descripcion = spdescripcion.getSelectedItem().toString();
        if(!etcantidad.getText().toString().isEmpty() && (descripcion.equals("Intercambio") || descripcion.equals("Vaceo"))){
            double valor = (descripcion.equals("Intercambio"))?3:4;
            double total = Double.parseDouble(etcantidad.getText().toString())*valor;
            etprecio.setText(String.valueOf(total));
        }
        else{
            etprecio.setText("");
        }
    }
}