package Entregables.Entregable3;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SistemaPedidos sistema = new SistemaPedidos();

       
        sistema.cargarProductos();
        sistema.cargarClientes();
        sistema.cargarPedidos();

      
        ProcesadorPedidos procesador = new ProcesadorPedidos(sistema);
        GeneradorReportes generador = new GeneradorReportes(sistema);

        procesador.start();
        generador.start();

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

          
            System.out.println("9. Guardar sistema manualmente");
            System.out.println("10. Cargar sistema desde archivos");
            System.out.println("11. Generar reporte del sistema");
            System.out.println("12. Ver estado de procesamiento");

            System.out.println("0. Salir");

            System.out.print("Seleccione una opción: ");

            try {

                opcion = sc.nextInt();

                switch (opcion) {

                    case 1: sistema.opcionRegistrarProducto(sc); break;
                    case 2: sistema.opcionRegistrarCliente(sc); break;
                    case 3: sistema.opcionCrearPedido(sc); break;
                    case 4: sistema.opcionAgregarProductoPedido(sc); break;
                    case 5: sistema.opcionVerDetallePedido(sc); break;
                    case 6: sistema.opcionListarProductos(); break;
                    case 7: sistema.opcionListarPedidos(); break;
                    case 8: sistema.opcionCambiarEstadoPedido(sc); break;

                   
                    case 9:
                        sistema.guardarTodo();
                        break;

                    case 10:
                        sistema.cargarProductos();
                        sistema.cargarClientes();
                        sistema.cargarPedidos();
                        break;

                    case 11:
                        sistema.generarReporte();
                        break;

                    case 12:
                        sistema.verEstadoProcesamiento();
                        break;

                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                }

            } catch (Exception e) {
                System.out.println("Entrada inválida.");
                sc.nextLine();
                opcion = -1;
            }

        } while (opcion != 0);

        
        sistema.guardarTodo();
        procesador.detener();
        sc.close();
    }
}
