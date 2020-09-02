package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.adapters.UsuarioAdapter;
import gantzcompany.myapplication.models.Usuarios;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilAdmin extends AppCompatActivity {

    private UsuarioAdapter nAdapter;
    private RecyclerView nRecyclerView;
    private ArrayList<Usuarios> nUsuariosList = new ArrayList<>();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_admin);

        nRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewUsuarios);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getUsuariosFromFirebase();
    }

    private void getUsuariosFromFirebase(){
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String nombre = ds.child("nombre").getValue().toString();
                        nUsuariosList.add(new Usuarios(nombre));
                    }

                    nAdapter = new UsuarioAdapter(nUsuariosList, R.layout.usuarios_view);
                    nRecyclerView.setAdapter(nAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}