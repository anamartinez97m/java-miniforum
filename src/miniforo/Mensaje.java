/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ana
 */
public class Mensaje implements Serializable{
    private String autor;
    private String titulo;
    private String cuerpo;
    private int numPalabras;
    
    public Mensaje(String autor, String titulo, String cuerpo){
        this.autor = autor;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.numPalabras = cuerpo.split(" ").length;
    }
    
    public String getAutor(){
        return this.autor;
    }
    
    public String getTitulo(){
        return this.titulo;
    }
    
    public String getCuerpo(){
        return this.cuerpo;
    }
    
    public int getNumPalabras(){
        return this.numPalabras;
    }
    
    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    public void setCuerpo(String cuerpo){
        this.cuerpo = cuerpo;
    }
    
    public void setNumPalabras(int numPalabras){
        this.numPalabras = numPalabras;
    }
    
    @Override
    public String toString(){
        return "El autor del mensaje es: " + this.autor +
                ", el titulo del mensaje es: " + this.titulo + 
                ", el cuerpo del mensaje es: " + this.cuerpo +
                ", el número de palabras del cuerpo es: " +
                this.numPalabras;
    }
    
    public boolean existeMensaje(String autor, String titulo, String cuerpo){
        return ((this.autor.equals(autor)) &&
                (this.titulo.equals(titulo)) &&
                (this.cuerpo.equals(cuerpo)));
    }
}
