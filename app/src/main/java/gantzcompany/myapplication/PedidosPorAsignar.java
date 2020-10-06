package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.adapters.PedidoAdapter;
import gantzcompany.myapplication.models.Pedidos;
import gantzcompany.myapplication.models.TipoPedido;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PedidosPorAsignar extends AppCompatActivity {

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
        setContentView(R.layout.activity_pedidos_por_asignar);

        nRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewPedidosPorAsignar);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mtextviewNombre = (TextView) findViewById(R.id.textviewNombre);
        mtextviewCantidad = (TextView) findViewById(R.id.textviewCantidad);
        mtextviewPrecio = (TextView) findViewById(R.id.textviewPrecio);
        mtextviewDescripcion = (TextView) findViewById(R.id.textviewDescripcion);
        mtextviewDireccion = (TextView) findViewById(R.id.textviewDireccion);

        nRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ConsultasFirebase consultaFirebase = new ConsultasFirebase();
        consultaFirebase.getPedidosForRepartidor(mDatabase, nRecyclerView);

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
}