package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.adapters.PedidoAdapter;

import gantzcompany.myapplication.models.Pedidos;



import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilRepartidor extends AppCompatActivity {

    private PedidoAdapter nAdapter;
    private RecyclerView nRecyclerView;
    private ArrayList<Pedidos> nPedidosList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private TextView mtextviewNombre;
    private TextView mtextviewCantidad;
    private TextView mtextviewPrecio;
    private TextView mtextviewDescripcion;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_repartidor);
        nRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewPedidos);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mtextviewNombre = (TextView) findViewById(R.id.textviewNombre);
        mtextviewCantidad = (TextView) findViewById(R.id.textviewCantidad);
        mtextviewPrecio = (TextView) findViewById(R.id.textviewPrecio);
        mtextviewDescripcion = (TextView) findViewById(R.id.textviewDescripcion);

        nRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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


                    mtextviewNombre.setText(nombre);
                    mtextviewCantidad.setText(cantidad);
                    mtextviewPrecio.setText(precio);
                    mtextviewDescripcion.setText(descripcion);

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
                        String nombre = ds.child("nombre").getValue().toString();
                        String cantidad = ds.child("cantidad").getValue().toString();
                        String precio = ds.child("precio").getValue().toString();
                        String descripcion = ds.child("descripcion").getValue().toString();
                        nPedidosList.add(new Pedidos(nombre,cantidad,precio,descripcion));

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
}