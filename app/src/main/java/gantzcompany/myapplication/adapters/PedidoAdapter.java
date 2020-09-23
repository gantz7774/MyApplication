package gantzcompany.myapplication.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gantzcompany.myapplication.MapsActivity;
import gantzcompany.myapplication.PerfilCliente;
import gantzcompany.myapplication.R;
import gantzcompany.myapplication.models.Pedidos;

import static androidx.core.content.ContextCompat.startActivity;


public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {
    private int resource;
    private ArrayList<Pedidos> pedidosList;

    public PedidoAdapter(ArrayList<Pedidos> pedidosList, int resource){
        this.pedidosList = pedidosList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PedidoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.ViewHolder holder, int index) {

        final Pedidos pedidos = pedidosList.get(index);

        holder.textviewNombre.setText(pedidos.getNombre());
        holder.textviewCantidad.setText(pedidos.getCantidad());
        holder.textviewPrecio.setText(pedidos.getPrecio());
        holder.textviewDescripcion.setText(pedidos.getDescripcion());
        holder.textviewDireccion.setText(pedidos.getDireccion());
        holder.btnverubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ayuda = new Intent(view.getContext(), MapsActivity.class);
                ayuda.putExtra("longitud", Double.parseDouble(pedidos.getLongitud()));
                ayuda.putExtra("latitud",Double.parseDouble(pedidos.getLatitud()));
                startActivity(view.getContext(), ayuda, null);
            }
        });

    }
    @Override
    public int getItemCount() {
        return pedidosList.size();
    }

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        private TextView textviewNombre;
        private TextView textviewCantidad;
        private TextView textviewPrecio;
        private TextView textviewDescripcion;
        private TextView textviewDireccion;
        private Button btnverubicacion;
        private Button btnasignarrepartidor;
        public View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.textviewNombre = (TextView) view.findViewById(R.id.textviewNombre);
            this.textviewCantidad = (TextView) view.findViewById(R.id.textviewCantidad);
            this.textviewPrecio = (TextView) view.findViewById(R.id.textviewPrecio);
            this.textviewDescripcion = (TextView) view.findViewById(R.id.textviewDescripcion);
            this.textviewDireccion = (TextView) view.findViewById(R.id.textviewDireccion);
            this.btnverubicacion = (Button) view.findViewById(R.id.btnVerUbicacion);
            this.btnasignarrepartidor = (Button) view.findViewById(R.id.btnAsignarRepartidor);


        }
    }
}
