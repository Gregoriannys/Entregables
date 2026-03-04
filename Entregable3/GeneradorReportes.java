package Entregables.Entregable3;



public class GeneradorReportes extends Thread {

    private SistemaPedidos sistema;

    public GeneradorReportes(SistemaPedidos sistema) {
        this.sistema = sistema;
        setDaemon(true);
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(10000);
                sistema.generarReporte();
            } catch (InterruptedException e) {
                System.out.println("Error en generador");
            }
        }
    }
}