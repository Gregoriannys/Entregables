package Entregables.Entregable2;

public class DetallePedido {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetallePedido(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio(); //captura precio unitario al agregar el producto al pedido.

    }

    public double getTotal(){
        return cantidad * precioUnitario;
    }

    public Producto getProducto(){
        return producto;
    }
    
    public int getCantidad(){
        return cantidad;
    }
}