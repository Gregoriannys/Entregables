package Entregables.Entregable3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public  abstract class Cliente {

    private int id;
    private String nombre;

    public Cliente(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
    
    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    //Metodo
    public abstract double calcularDescuento(double subtotal);


    
}