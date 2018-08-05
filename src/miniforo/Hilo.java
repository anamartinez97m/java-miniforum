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
public class Hilo implements Serializable{
    private String titulo;
    private String autor;
    private ArrayList<Mensaje> mensajes;
    
    public Hilo(String titulo, Mensaje mensaje, String autor){
        this.titulo = titulo;
        this.autor = autor;
        this.mensajes = new ArrayList<>();
        this.mensajes.add(mensaje);
    }
    
    public String getAutor(){
        return this.autor;
    }
    
    public String getTitulo(){
        return titulo;
    }
    
    public ArrayList<Mensaje> getMensajes(){
        return mensajes;
    }
    
    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public void setTitutlo(String titulo){
        this.titulo = titulo;
    }
    
    public void setMensajes(ArrayList<Mensaje> mensajes){
        this.mensajes = mensajes;
    }
    
    @Override
    public String toString(){
        String resultado = "El titulo del hilo es: " + 
                this.titulo + ", los mensajes son: ";
        
        for(Mensaje m: mensajes){
            resultado += "\n" + m;
        }
        
        return resultado;
    }
    
    public boolean existeHilo(String titulo){
        return (this.titulo.equals(titulo));
    }
    
    public void crearMensaje(Mensaje m){
        this.mensajes.add(m);
    }
    
    public Mensaje obtenerMensaje(String autor, String titulo, String cuerpo){
        Mensaje mensaje = null;
        boolean encontrado = false;
        Iterator<Mensaje> it = this.mensajes.iterator();
        while((!encontrado) && (it.hasNext())){
            mensaje = it.next();
            encontrado = mensaje.existeMensaje(autor, titulo, cuerpo);
        }
        if(!encontrado){
            mensaje = null;
        }
        return mensaje;
    }
    
    public void eliminarMensaje(String autor, String titulo, String cuerpo) throws EMensajeNoExiste{
        Mensaje mensaje = obtenerMensaje(autor, titulo, cuerpo);
        if(mensaje != null){
            this.mensajes.remove(mensaje);
        } else {
            throw new EMensajeNoExiste(autor, titulo, cuerpo);
        }
    }
    
    public int getNumeroMensajesUsuarioHilo(String nick) {
        int contador = 0;
        for (Mensaje mens: mensajes){
            if (mens.getAutor().equalsIgnoreCase(nick)) {
                contador = contador + 1;
            }
        }
        return contador;
    }
}
