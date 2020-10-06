package gantzcompany.myapplication;

import android.util.Log;
import android.widget.Adapter;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.adapters.PedidoAdapter;
import gantzcompany.myapplication.models.Pedidos;
import gantzcompany.myapplication.models.TipoPedido;

public class ConsultasFirebase {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public ConsultasFirebase(){
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuthUser(){
        return this.mAuth;
    }


    public void getPedidosForRepartidor(DatabaseReference mDataBase, final RecyclerView recyclerView){
        final ArrayList<Pedidos> nPedidosList = new ArrayList<Pedidos>();
        mDataBase.child("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){

                        if(Boolean.parseBoolean(ds.child("enviando").getValue().toString())  != true){


                            String idPedido = ds.getKey();
                            String latitud = ds.child("latitud").getValue().toString();
                            String longitud = ds.child("longitud").getValue().toString();


                            String nombre = ds.child("nombre").getValue().toString();
                            String cantidad = ds.child("cantidad").getValue().toString();
                            String precio = ds.child("precio").getValue().toString();
                            String descripcion = ds.child("descripcion").getValue().toString();
                            String direccion = ds.child("direccion").getValue().toString();
                            TipoPedido tipoPedido = TipoPedido.REPARTIDOR;
                            nPedidosList.add(new Pedidos(idPedido, nombre,cantidad,precio,descripcion,direccion,latitud,longitud,tipoPedido));
                        }
                    }

                    recyclerView.setAdapter(new PedidoAdapter(nPedidosList, R.layout.pedidos_por_asignar_view));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Task asignarRepartidor(String id_repartidor, String id_pedido){
        return this.mDatabase.child("Pedidos").child(id_pedido).child("id_repartidor").setValue(id_repartidor);
    }

}
