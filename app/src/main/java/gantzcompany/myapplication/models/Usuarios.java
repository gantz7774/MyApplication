package gantzcompany.myapplication.models;

public class Usuarios {
    private String Nombre;
    private String Correo;
    public Usuarios(){}

    public Usuarios(String Nombre, String Correo){
        this.Nombre = Nombre;
        this.Correo = Correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }
}
