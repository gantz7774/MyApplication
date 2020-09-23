package gantzcompany.myapplication.models;

public class Pedidos {
    private String Nombre;
    private String Cantidad;
    private String Precio;
    private String Descripcion;
    private String Direccion;
    private String Latitud;
    private String Longitud;

    public Boolean getEntregado() {
        return Entregado;
    }

    public void setEntregado(Boolean entregado) {
        Entregado = entregado;
    }

    private Boolean Entregado;

    public Pedidos(){}

    public Pedidos(String Nombre, String Cantidad, String Precio, String Descripcion, String Direccion, String Latitud, String Longitud ){
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.Descripcion = Descripcion;
        this.Direccion = Direccion;
        this.Latitud = Latitud;
        this.Longitud = Longitud;
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

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }
}
