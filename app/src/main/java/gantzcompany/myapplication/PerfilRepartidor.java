package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.adapters.PedidoAdapter;

import gantzcompany.myapplication.models.Pedidos;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilRepartidor extends AppCompatActivity {

    private TextView mtextviewNombreUser;
    private PedidoAdapter nAdapter;
    private RecyclerView nRecyclerView;
    private ArrayList<Pedidos> nPedidosList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private TextView mtextviewNombre;
    private TextView mtextviewCantidad;
    private TextView mtextviewPrecio;
    private TextView mtextviewDescripcion;
    private TextView mtextviewDireccion;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_repartidor);
        nRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewPedidos);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mtextviewNombre = (TextView) findViewById(R.id.textviewNombre);
        mtextviewNombreUser = (TextView) findViewById(R.id.tvNombre);
        mtextviewCantidad = (TextView) findViewById(R.id.textviewCantidad);
        mtextviewPrecio = (TextView) findViewById(R.id.textviewPrecio);
        mtextviewDescripcion = (TextView) findViewById(R.id.textviewDescripcion);
        mtextviewDireccion = (TextView) findViewById(R.id.textviewDireccion);

        nRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        obtenerDatoUsuario();
        getPedidosFromFirebase();
        obtenerDatoPedido();
    }

    private void obtenerDatoPedido(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Pedidos").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String cantidad = dataSnapshot.child("cantidad").getValue().toString();
                    String precio = dataSnapshot.child("precio").getValue().toString();
                    String descripcion = dataSnapshot.child("descripcion").getValue().toString();
                    String direccion = dataSnapshot.child("direccion").getValue().toString();


                    mtextviewNombre.setText(nombre);
                    mtextviewCantidad.setText(cantidad);
                    mtextviewPrecio.setText(precio);
                    mtextviewDescripcion.setText(descripcion);
                    mtextviewDireccion.setText(direccion);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void obtenerDatoUsuario(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();


                    mtextviewNombreUser.setText(nombre);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPedidosFromFirebase(){
        mDatabase.child("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){

                        if(Boolean.parseBoolean(ds.child("enviando").getValue().toString())  != true){
                            String nombre = ds.child("nombre").getValue().toString();
                            String cantidad = ds.child("cantidad").getValue().toString();
                            String precio = ds.child("precio").getValue().toString();
                            String descripcion = ds.child("descripcion").getValue().toString();
                            String direccion = ds.child("direccion").getValue().toString();
                            nPedidosList.add(new Pedidos(nombre,cantidad,precio,descripcion,direccion));
                        }
                    }

                    nAdapter = new PedidoAdapter(nPedidosList, R.layout.pedidos_view);
                    nRecyclerView.setAdapter(nAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_pedidos_pendientes, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem){

        int id = menuitem.getItemId();
        switch (id){

            case R.id.btnInformacion:
                startActivity(new Intent(this, InformacionActivity.class));
                finish();
                break;
            case R.id.btnCerrarSesion:
                mAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(menuitem);
    }
}