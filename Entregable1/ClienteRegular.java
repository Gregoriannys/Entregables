public class ClienteRegular extends Cliente {

    public ClienteRegular(int id, String nombre){
        super(id, nombre);
    }


    //No aplica descuento
    @Override
    public double calcularDescuento(double subtotal){
        return 0;
    }


    
}
