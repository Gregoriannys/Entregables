package Entregables.Entregable3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.util.Date;

public class SistemaPedidos {

    private List<Producto> productos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private int pedidosEnProceso = 0;

    public void opcionRegistrarProducto(Scanner sc) {

        try {
            System.out.print("ID producto: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (buscarProductoPorId(id) != null) {
                System.out.println("ID duplicado.");
                return;
            }

            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Nombre inválido.");
                return;
            }

            System.out.print("Precio: ");
            double precio = sc.nextDouble();

            System.out.print("Stock: ");
            int stock = sc.nextInt();

            productos.add(new Producto(id, nombre, precio, stock));

            System.out.println("Producto registrado.");

        } catch (Exception e) {
            System.out.println("Error al registrar producto.");
            sc.nextLine();
        }
    }

    public void opcionRegistrarCliente(Scanner sc) {

        try {
            System.out.print("ID cliente: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (buscarClientePorId(id) != null) {
                System.out.println("ID duplicado.");
                return;
            }

            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Nombre inválido.");
                return;
            }

            System.out.print("Tipo (1=Regular, 2=VIP): ");
            int tipo = sc.nextInt();

            if (tipo == 1) {
                clientes.add(new ClienteRegular(id, nombre));
            } else if (tipo == 2) {
                System.out.print("Porcentaje descuento (ej: 0.1): ");
                double desc = sc.nextDouble();
                clientes.add(new ClienteVIP(id, nombre, desc));
            } else {
                System.out.println("Tipo inválido.");
                return;
            }

            System.out.println("Cliente registrado.");

        } catch (Exception e) {
            System.out.println("Error al registrar cliente.");
            sc.nextLine();
        }
    }

    public void opcionCrearPedido(Scanner sc) {

        try {
            System.out.print("ID pedido: ");
            int id = sc.nextInt();

            if (buscarPedidoPorId(id) != null) {
                System.out.println("ID duplicado.");
                return;
            }

            System.out.print("ID cliente: ");
            int idCliente = sc.nextInt();

            Cliente cliente = buscarClientePorId(idCliente);

            if (cliente == null) {
                System.out.println("Cliente no existe.");
                return;
            }

            pedidos.add(new Pedido(id, cliente));

            System.out.println("Pedido creado en estado BORRADOR.");

        } catch (Exception e) {
            System.out.println("Error al crear pedido.");
            sc.nextLine();
        }
    }

    public void opcionAgregarProductoPedido(Scanner sc) {

        try {
            System.out.print("ID pedido: ");
            int idPedido = sc.nextInt();

            Pedido pedido = buscarPedidoPorId(idPedido);

            if (pedido == null)
                throw new PedidoInvalidoException("Pedido no existe.");

            System.out.print("ID producto: ");
            int idProducto = sc.nextInt();

            Producto producto = buscarProductoPorId(idProducto);

            if (producto == null)
                throw new ProductoNoEncontradoException("Producto no existe.");

            System.out.print("Cantidad: ");
            int cantidad = sc.nextInt();

            pedido.agregarProducto(producto, cantidad);

            System.out.println("Producto agregado.");

        } catch (StockInsuficienteException | ProductoNoEncontradoException | PedidoInvalidoException e) {

            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Error inesperado.");
            sc.nextLine();
        } finally {
            System.out.println("Operación finalizada.");
        }
    }

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

    public void opcionListarProductos() {

        for (Producto p : productos) {
            System.out.println(
                    p.getId() + " | " +
                            p.getNombre() + " | Precio: " +
                            p.getPrecio() + " | Stock: " +
                            p.getStock());
        }
    }

    public void opcionListarPedidos() {

        for (Pedido p : pedidos) {
            System.out.println(
                    "Pedido #" + p.getId() +
                            " | Estado: " + p.getEstado());
        }
    }

    public void opcionCambiarEstadoPedido(Scanner sc) {

        try {
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
            } else {
                System.out.println("Opción inválida.");
            }

        } catch (PedidoInvalidoException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al cambiar estado.");
            sc.nextLine();
        }
    }

    private Producto buscarProductoPorId(int id) {

        for (Producto p : productos) {
            if (p.getId() == id)
                return p;
        }

        return null;
    }

    private Cliente buscarClientePorId(int id) {

        for (Cliente c : clientes) {
            if (c.getId() == id)
                return c;
        }

        return null;
    }

    private Pedido buscarPedidoPorId(int id) {

        for (Pedido p : pedidos) {
            if (p.getId() == id)
                return p;
        }

        return null;
    }

    public synchronized void procesarPedidosConfirmados() {

        for (Pedido p : pedidos) {

            if (p.getEstado().equals(Pedido.CONFIRMADO)) {

                try {
                    pedidosEnProceso++;

                    Thread.sleep(2000);

                    p.procesar();

                    guardarPedidos(); // actualizar archivo

                    pedidosEnProceso--;

                } catch (InterruptedException e) {
                    System.out.println("Error procesando pedido");
                }
            }
        }
    }

    public synchronized void verEstadoProcesamiento() {
        System.out.println("Pedidos en procesamiento: " + pedidosEnProceso);
    }

    public synchronized void guardarProductos() {

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("productos.dat"))) {

            for (Producto p : productos) {
                out.writeInt(p.getId());
                out.writeUTF(p.getNombre());
                out.writeDouble(p.getPrecio());
                out.writeInt(p.getStock());
            }

        } catch (IOException e) {
            System.out.println("Error guardando productos");
        }
    }

    public synchronized void cargarProductos() {

        productos.clear();

        try (DataInputStream in = new DataInputStream(new FileInputStream("productos.dat"))) {

            while (true) {
                productos.add(
                        new Producto(
                                in.readInt(),
                                in.readUTF(),
                                in.readDouble(),
                                in.readInt()));
            }

        } catch (EOFException e) {
            // fin archivo
        } catch (IOException e) {
            System.out.println("No se pudieron cargar productos");
        }
    }

    public synchronized void guardarClientes() {

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("clientes.dat"))) {

            for (Cliente c : clientes) {

                out.writeInt(c.getId());
                out.writeUTF(c.getNombre());

                if (c instanceof ClienteVIP) {
                    out.writeBoolean(true);
                    out.writeDouble(((ClienteVIP) c).getPorcentaje());
                } else {
                    out.writeBoolean(false);
                }
            }

        } catch (IOException e) {
            System.out.println("Error guardando clientes");
        }
    }

    public synchronized void cargarClientes() {

        clientes.clear();

        try (DataInputStream in = new DataInputStream(new FileInputStream("clientes.dat"))) {

            while (true) {

                int id = in.readInt();
                String nombre = in.readUTF();
                boolean esVIP = in.readBoolean();

                if (esVIP) {
                    double porcentaje = in.readDouble();
                    clientes.add(new ClienteVIP(id, nombre, porcentaje));
                } else {
                    clientes.add(new ClienteRegular(id, nombre));
                }
            }

        } catch (EOFException e) {
        } catch (IOException e) {
            System.out.println("No se pudieron cargar clientes");
        }
    }

    public synchronized void guardarPedidos() {

        try (PrintWriter pw = new PrintWriter("pedidos.txt")) {

            for (Pedido p : pedidos) {
                pw.println(
                        p.getId() + "|" +
                                p.getEstado() + "|" +
                                p.getFechaCreacion().getTime());
            }

        } catch (Exception e) {
            System.out.println("Error guardando pedidos");
        }
    }

    public synchronized void cargarPedidos() {

        pedidos.clear();

        try (BufferedReader br = new BufferedReader(new FileReader("pedidos.txt"))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split("\\|");

                int id = Integer.parseInt(partes[0]);
                String estado = partes[1];

               
            }

        } catch (IOException e) {
            System.out.println("No se pudieron cargar pedidos");
        }
    }

    public synchronized void guardarTodo() {
        guardarProductos();
        guardarClientes();
        guardarPedidos();
    }

    public synchronized void generarReporte() {

        try (PrintWriter pw = new PrintWriter("reporte_sistema.txt")) {

            pw.println("REPORTE DEL SISTEMA");
            pw.println("Fecha: " + new Date());

            pw.println("Total productos: " + productos.size());
            pw.println("Total clientes: " + clientes.size());

            int borrador = 0;
            int confirmado = 0;
            int procesado = 0;

            for (Pedido p : pedidos) {

                if (p.getEstado().equals(Pedido.BORRADOR))
                    borrador++;
                if (p.getEstado().equals(Pedido.CONFIRMADO))
                    confirmado++;
                if (p.getEstado().equals(Pedido.PROCESADO))
                    procesado++;
            }

            pw.println("BORRADOR: " + borrador);
            pw.println("CONFIRMADO: " + confirmado);
            pw.println("PROCESADO: " + procesado);

        } catch (Exception e) {
            System.out.println("Error generando reporte");
        }
    }

}
