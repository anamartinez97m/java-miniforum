/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Ana
 */
public class Categoria implements Serializable{
    private String nombre;
    private ArrayList<Hilo> hilos;
    
    public Categoria(String nombre){
        this.nombre = nombre;
        this.hilos = new ArrayList<>();
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public ArrayList<Hilo> getHilos(){
        return this.hilos;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setHilos(ArrayList<Hilo> hilos){
        this.hilos = hilos;
    }
    
    @Override
    public String toString(){
        String resultado = "El nombre de la categoria es: " + 
                this.nombre + ", los hilos son: ";
        
        for(Hilo h: hilos){
            resultado += "\n" + h;
        }
        
        return resultado;
    }
    
    public boolean existeCategoria(String nombre){
        return (this.nombre.equals(nombre));
    }
    
    public void crearHilo(Hilo h){
        this.hilos.add(h);
    }
    
    public Hilo obtenerHilo(String titulo){
        Hilo hilo = null;
        boolean encontrado = false;
        Iterator<Hilo> it = this.hilos.iterator();
        while((!encontrado) && (it.hasNext())){
            hilo = it.next();
            encontrado = hilo.existeHilo(titulo);
        }
        if(!encontrado){
            hilo = null;
        }
        return hilo;
    }

    public void eliminarHilo(String titulo) throws EHiloNoExiste{
        Hilo hilo = obtenerHilo(titulo);
        if(hilo != null){
            this.hilos.remove(hilo);
        } else {
            throw new EHiloNoExiste(titulo);
        }
    }
    
    public int getNumMensajesTotal(){
        int resultado = 0;
        
        for(Hilo hilo: this.hilos){
            resultado += hilo.getMensajes().size();
        }
        return resultado;
    }
    
    public int getNumPalabrasTotal(){
        int resultado = 0;
        ArrayList<Mensaje> mensajes = null;
        
        for(Hilo hilo: this.hilos){
            mensajes = hilo.getMensajes();
            for(Mensaje msg: mensajes){
                resultado += msg.getNumPalabras();
            }
        }
        
        return resultado;
    }
    
    public int getNumeroMensajesUsuarioCategoria(String nick) {
        int contador = 0;
        for (Hilo h: hilos){
            contador = contador + h.getNumeroMensajesUsuarioHilo(nick);
        }
        return contador;
    }
}
