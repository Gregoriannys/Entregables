import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SistemaPedidos sistema = new SistemaPedidos();
        int opcion;

        do {
            System.out.println("\n SISTEMA DE GESTION DE PEDIDOS ");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear pedido");
            System.out.println("4. Agregar producto a pedido");
            System.out.println("5. Ver detalle de pedido");
            System.out.println("6. Listar productos");
            System.out.println("7. Listar pedidos");
            System.out.println("8. Cambiar estado de pedido");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    sistema.opcionRegistrarProducto(sc);
                    break;

                case 2:
                    sistema.opcionRegistrarCliente(sc);
                    break;

                case 3:
                    sistema.opcionCrearPedido(sc);
                    break;

                case 4:
                    sistema.opcionAgregarProductoPedido(sc);
                    break;

                case 5:
                    sistema.opcionVerDetallePedido(sc);
                    break;

                case 6:
                    sistema.opcionListarProductos();
                    break;

                case 7:
                    sistema.opcionListarPedidos();
                    break;

                case 8:
                    sistema.opcionCambiarEstadoPedido(sc);
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción invalida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}

