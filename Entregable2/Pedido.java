package Entregables.Entregable2;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Pedido {

    public static final String BORRADOR = "BORRADOR";
    public static final String CONFIRMADO = "CONFIRMADO";
    public static final String CANCELADO = "CANCELADO";

    private int id;
    private Cliente cliente;
    private String estado;
    private List<DetallePedido> detalles;
    private Date fechaCreacion;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.estado = BORRADOR;
        this.detalles = new ArrayList<>();
        this.fechaCreacion = new Date();
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void agregarProducto(Producto producto, int cantidad)
            throws StockInsuficienteException {

        if (!estado.equals(BORRADOR))
            return;

        if (cantidad > producto.getStock()) {
            throw new StockInsuficienteException("Stock insuficiente.");
        }

        detalles.add(new DetallePedido(producto, cantidad));
    }

    public double calcularSubtotal() {

        double subtotal = 0;

        for (DetallePedido d : detalles) {
            subtotal += d.getTotal();
        }

        return subtotal;
    }

    public double calcularDescuento() {
        return cliente.calcularDescuento(calcularSubtotal());
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public void confirmar() throws PedidoInvalidoException {

        if (detalles.isEmpty()) {
            throw new PedidoInvalidoException("No se puede confirmar un pedido vacío.");
        }

        if (estado.equals(BORRADOR)) {

            for (DetallePedido d : detalles) {
                d.getProducto().disminuirStock(d.getCantidad());
            }

            estado = CONFIRMADO;
        }
    }

    public void cancelar() {

        if (estado.equals(CONFIRMADO)) {

            for (DetallePedido d : detalles) {
                d.getProducto().aumentarStock(d.getCantidad());
            }
        }

        estado = CANCELADO;
    }

    public void mostrarDetalle() {

        System.out.println("Pedido #" + id);
        System.out.println("Estado: " + estado);
        System.out.println("Fecha: " + fechaCreacion);

        for (DetallePedido d : detalles) {
            System.out.println(
                    d.getProducto().getNombre() +
                            " x " + d.getCantidad() +
                            " = " + d.getTotal());
        }

        System.out.println("Subtotal: " + calcularSubtotal());
        System.out.println("Descuento: " + calcularDescuento());
        System.out.println("Total: " + calcularTotal());
    }
}
