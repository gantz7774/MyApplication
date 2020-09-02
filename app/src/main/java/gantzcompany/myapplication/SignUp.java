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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private Button btn_crear_cuenta;
    private EditText et_Nombre;
    private EditText et_telefono;
    private EditText et_correo;
    private EditText et_contrasena;

    //VARIABLE DE LOS DATOS QUE VAMOS A REGISTRAR
    private String nombre = "";
    private String telefono = "";
    private String correo = "";
    private String contrasena = "";

    private FirebaseAuth nAuth;
    private DatabaseReference nDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nAuth = FirebaseAuth.getInstance();
        nDatabase = FirebaseDatabase.getInstance().getReference();

        et_Nombre = (EditText) findViewById(R.id.et_nombre);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        et_correo = (EditText) findViewById(R.id.et_correo);
        et_contrasena = (EditText) findViewById(R.id.et_contrasena);

        btn_crear_cuenta=findViewById(R.id.btn_crear_cuenta);

        btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = et_Nombre.getText().toString();
                telefono = et_telefono.getText().toString();
                correo = et_correo.getText().toString();
                contrasena = et_contrasena.getText().toString();

                if(!nombre.isEmpty() && !telefono.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty()) {

                    if (contrasena.length() >= 6) {

                        registrarUsuario();

                    }else{
                        Toast.makeText(SignUp.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SignUp.this,"Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void registrarUsuario() {

        nAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put( "nombre", nombre);
                    map.put( "telefono", telefono);
                    map.put( "correo", correo);
                    map.put( "contrasena", contrasena);
                    map.put( "tipo", 1);

                    String id = nAuth.getCurrentUser().getUid();

                    nDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if(task2.isSuccessful()) {
                                startActivity(new Intent(SignUp.this,ProfileActivity.class));
                                finish();

                            }else{
                                Toast.makeText(SignUp.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(SignUp.this, "No se pudo registrar este Usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
