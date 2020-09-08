package gantzcompany.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.R;
import gantzcompany.myapplication.models.Usuarios;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Usuarios> usuariosList;

    public UsuarioAdapter(ArrayList<Usuarios> usuariosList, int resource){
        this.usuariosList = usuariosList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {

        Usuarios usuarios = usuariosList.get(index);

        holder.textViewUsuario.setText(usuarios.getNombre());
        holder.textViewCorreo.setText(usuarios.getCorreo());


    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        private TextView textViewUsuario;
        private TextView textViewCorreo;
        public View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.textViewUsuario = (TextView) view.findViewById(R.id.textviewUsuarios);
            this.textViewCorreo = (TextView) view.findViewById(R.id.textviewCorreo);
        }
    }
}
