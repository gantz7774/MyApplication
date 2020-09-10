package gantzcompany.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InformacionActivity extends AppCompatActivity {

    private EditText mEditTextMensaje;
    private Button mBtnCrearDatos;
    private DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        mEditTextMensaje = (EditText) findViewById(R.id.editTextMensaje);
        mBtnCrearDatos = (Button) findViewById(R.id.btn_creardatos);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mBtnCrearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = mEditTextMensaje.getText().toString();
                mDataBase.child("texto").setValue(mensaje);
            }
        });
    }
}