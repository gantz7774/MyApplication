package gantzcompany.myapplication.models;

public class Usuarios {
    private String Nombre;
    public Usuarios(){}

    public Usuarios(String Nombre){
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
