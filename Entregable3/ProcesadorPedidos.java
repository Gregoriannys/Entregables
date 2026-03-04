package Entregables.Entregable3;


public class ProcesadorPedidos extends Thread {

    private SistemaPedidos sistema;
    private boolean activo = true;

    public ProcesadorPedidos(SistemaPedidos sistema) {
        this.sistema = sistema;
        setPriority(MAX_PRIORITY); 
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {

        while (activo) {

            try {
                sistema.procesarPedidosConfirmados();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Procesador interrumpido");
            }
        }
    }
}
