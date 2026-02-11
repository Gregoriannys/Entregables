public class Pedido {

    

    public static final String BORRADOR = "BORRADOR";
    public static final String CONFIRMADO = "CONFIRMADO";
    public static final String CANCELADO = "CANCELADO";

    private int id;
    private Cliente cliente;
    private String estado;
    private DetallePedido[] detalles;
    private int contador;

    public Pedido(int id, Cliente cliente, int maxDetalles) {
        this.id = id;
        this.cliente = cliente;
        this.estado = BORRADOR;
        this.detalles = new DetallePedido[maxDetalles];
        this.contador = 0;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (estado.equals(BORRADOR) && contador < detalles.length) {
            detalles[contador] = new DetallePedido(producto, cantidad);
            contador++;
        }
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < contador; i++) {
            subtotal += detalles[i].getTotal();
        }
        return subtotal;
    }

    public double calcularDescuento() {
        return cliente.calcularDescuento(calcularSubtotal());
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public void confirmar() {
        if (estado.equals(BORRADOR) && contador > 0) {
            estado = CONFIRMADO;
        }
    }

    public void cancelar() {
        estado = CANCELADO;
    }

    public void mostrarDetalle() {
    System.out.println("Pedido #" + id + " | Estado: " + estado);

    for (int i = 0; i < contador; i++) {
        System.out.println(
            detalles[i].getProducto().getNombre() +
            " x " + detalles[i].getCantidad() +
            " = " + detalles[i].getTotal()
        );
    }

    System.out.println("Subtotal: " + calcularSubtotal());
    System.out.println("Descuento: " + calcularDescuento());
    System.out.println("Total: " + calcularTotal());
}

}

    

