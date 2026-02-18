package Entregables.Entregable2;

public class ClienteVIP  extends Cliente{

    private double porcentaje;

    public ClienteVIP(int id, String nombre, double porcentaje){
        super(id, nombre);
        this.porcentaje = porcentaje;
    }

    //Descuento porcentual al subtotal
    @Override
    public double calcularDescuento(double subtotal){
       return subtotal * porcentaje;
    }


    
}