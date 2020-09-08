package gantzcompany.myapplication;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    private Button mIniciar_sesion;
    private EditText mEtCorreo;
    private EditText mEtContrasena;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;

    private String correo = "";
    private String contrasena = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mIniciar_sesion = (Button) findViewById(R.id.btn_iniciar_sesion);
        mEtCorreo = (EditText) findViewById(R.id.et_correologin);
        mEtContrasena = (EditText) findViewById(R.id.et_contrasenalogin);

        mIniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = mEtCorreo.getText().toString();
                contrasena = mEtContrasena.getText().toString();

                if (!correo.isEmpty() && !contrasena.isEmpty()){

                    loginUser();
                }else{

                    Toast.makeText(Login.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(){


        mAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    mDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){
                                String tipo = dataSnapshot.child("tipo").getValue().toString();

                                if(tipo == "1"){
                                    startActivity(new Intent(Login.this, ProfileActivity.class));
                                    finish();
                                }if(tipo == "2"){
                                    startActivity(new Intent(Login.this, PerfilRepartidor.class));
                                    finish();
                                }if(tipo == "3"){
                                    startActivity(new Intent(Login.this, PerfilAdmin.class));
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(Login.this, "No se pudo iniciar sesion, compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
