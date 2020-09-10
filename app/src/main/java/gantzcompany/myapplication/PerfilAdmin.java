package gantzcompany.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.adapters.UsuarioAdapter;
import gantzcompany.myapplication.models.Usuarios;

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

public class PerfilAdmin extends AppCompatActivity {

    private UsuarioAdapter nAdapter;
    private RecyclerView nRecyclerView;
    private ArrayList<Usuarios> nUsuariosList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private TextView mtextviewNombre;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_admin);

        nRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewUsuarios);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mtextviewNombre = (TextView) findViewById(R.id.tvNombre);

        nRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getUsuariosFromFirebase();
        obtenerDatoUsuario();
    }

    private void obtenerDatoUsuario(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
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

    private void getUsuariosFromFirebase(){
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String nombre = ds.child("nombre").getValue().toString();
                        String correo = ds.child("correo").getValue().toString();
                        nUsuariosList.add(new Usuarios(nombre,correo));

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_admin, menu);

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
            case R.id.btnPedidosPorAsignar:
                startActivity(new Intent(this, PedidosPorAsignar.class));
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