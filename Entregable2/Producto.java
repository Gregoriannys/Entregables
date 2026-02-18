package Entregables.Entregable2;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {

        this.id = id;
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // VALIDACIÓN STRING
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        } else {
            this.nombre = "Producto";
        }
    }

    public void setPrecio(double precio) {
        this.precio = (precio > 0) ? precio : 1;
    }

    public void setStock(int stock) {
        this.stock = (stock >= 0) ? stock : 0;
    }

    public void disminuirStock(int cantidad) {
        stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }
}
