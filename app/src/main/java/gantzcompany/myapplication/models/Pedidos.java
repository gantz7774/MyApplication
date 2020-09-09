package gantzcompany.myapplication.models;

public class Pedidos {
    private String Nombre;
    private String Cantidad;
    private String Precio;
    private String Descripcion;

    public Boolean getEntregado() {
        return Entregado;
    }

    public void setEntregado(Boolean entregado) {
        Entregado = entregado;
    }

    private Boolean Entregado;

    public Pedidos(){}

    public Pedidos(String Nombre, String Cantidad, String Precio, String Descripcion ){
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.Descripcion = Descripcion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


}
