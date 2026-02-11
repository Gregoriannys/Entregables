import java.util.Scanner;

public class SistemaPedidos {

    // Arrays (tamaño fijo)
    private Producto[] productos = new Producto[10];
    private Cliente[] clientes = new Cliente[10];
    private Pedido[] pedidos = new Pedido[10];

    private int contProd = 0;
    private int contCli = 0;
    private int contPed = 0;

    //1
    public void opcionRegistrarProducto(Scanner sc) {
        System.out.print("ID producto: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (buscarProductoPorId(id) != null) {
            System.out.println("ID de producto duplicado.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Precio: ");
        double precio = sc.nextDouble();

        System.out.print("Stock: ");
        int stock = sc.nextInt();

        productos[contProd++] = new Producto(id, nombre, precio, stock);
        System.out.println("Producto registrado.");
    }

    //2
    public void opcionRegistrarCliente(Scanner sc) {
        System.out.print("ID cliente: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (buscarClientePorId(id) != null) {
            System.out.println("ID de cliente duplicado.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Tipo (1=Regular, 2=VIP): ");
        int tipo = sc.nextInt();

        if (tipo == 1) {
            clientes[contCli++] = new ClienteRegular(id, nombre);
        } else if (tipo == 2) {
            System.out.print("Porcentaje descuento (ej: 0.1): ");
            double desc = sc.nextDouble();
            clientes[contCli++] = new ClienteVIP(id, nombre, desc);
        }

        System.out.println("Cliente registrado.");
    }

    //3
    public void opcionCrearPedido(Scanner sc) {
        System.out.print("ID pedido: ");
        int id = sc.nextInt();

        if (buscarPedidoPorId(id) != null) {
            System.out.println("ID de pedido duplicado.");
            return;
        }

        System.out.print("ID cliente: ");
        int idCliente = sc.nextInt();

        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no existe.");
            return;
        }

        pedidos[contPed++] = new Pedido(id, cliente, 10);
        System.out.println("Pedido creado en estado BORRADOR.");
    }

    // 4
    public void opcionAgregarProductoPedido(Scanner sc) {
        System.out.print("ID pedido: ");
        int idPedido = sc.nextInt();

        Pedido pedido = buscarPedidoPorId(idPedido);
        if (pedido == null) {
            System.out.println("Pedido no existe.");
            return;
        }

        System.out.print("ID producto: ");
        int idProducto = sc.nextInt();

        Producto producto = buscarProductoPorId(idProducto);
        if (producto == null) {
            System.out.println("Producto no existe.");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = sc.nextInt();

        if (cantidad > producto.getStock()) {
            System.out.println("Stock insuficiente.");
            return;
        }

        pedido.agregarProducto(producto, cantidad);
        System.out.println("Producto agregado al pedido.");
    }

    // 5
    public void opcionVerDetallePedido(Scanner sc) {
        System.out.print("ID pedido: ");
        int id = sc.nextInt();

        Pedido pedido = buscarPedidoPorId(id);
        if (pedido != null) {
            pedido.mostrarDetalle();
        } else {
            System.out.println("Pedido no encontrado.");
        }
    }

    // 6
    public void opcionListarProductos() {
        for (int i = 0; i < contProd; i++) {
            System.out.println(
                productos[i].getId() + " | " +
                productos[i].getNombre() + " | Precio: " +
                productos[i].getPrecio() + " | Stock: " +
                productos[i].getStock()
            );
        }
    }

    // 7
    public void opcionListarPedidos() {
        for (int i = 0; i < contPed; i++) {
            System.out.println(
                "Pedido #" + pedidos[i].getId() +
                " | Estado: " + pedidos[i].getEstado()
            );
        }
    }

    // 8
    public void opcionCambiarEstadoPedido(Scanner sc) {
        System.out.print("ID pedido: ");
        int id = sc.nextInt();

        Pedido pedido = buscarPedidoPorId(id);
        if (pedido == null) {
            System.out.println("Pedido no existe.");
            return;
        }

        System.out.print("1=Confirmar, 2=Cancelar: ");
        int op = sc.nextInt();

        if (op == 1) {
            pedido.confirmar();
        } else if (op == 2) {
            pedido.cancelar();
        }
    }

    // Metodos busqueda

    private Producto buscarProductoPorId(int id) {
        for (int i = 0; i < contProd; i++) {
            if (productos[i].getId() == id) return productos[i];
        }
        return null;
    }

    private Cliente buscarClientePorId(int id) {
        for (int i = 0; i < contCli; i++) {
            if (clientes[i].getId() == id) return clientes[i];
        }
        return null;
    }

    private Pedido buscarPedidoPorId(int id) {
        for (int i = 0; i < contPed; i++) {
            if (pedidos[i].getId() == id) return pedidos[i];
        }
        return null;
    }
}
