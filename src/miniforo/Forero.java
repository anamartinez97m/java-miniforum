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
public class Forero implements Serializable{
    private String nick;
    private String password;
    private boolean sancionado;
    private int vecesSancionado;
    private int nMensajesTotal;
    
    public Forero(String nick, String password){
        this.nick = nick;
        this.password = password;
        this.nMensajesTotal = 0;
    }
    
    public String getNick(){
        return this.nick;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public boolean getSancionado(){
        return this.sancionado;
    }
    
    public int getVecesSancionado(){
        return this.vecesSancionado;
    }
    
    public int getNMensajesTotal() {
        return this.nMensajesTotal;
    }
    
    public void setNick(String nick){
        this.nick = nick;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setSancionado(boolean sancionado){
        this.sancionado = sancionado;
    }
    
    public void setVecesSancionado(int vecesSancionado){
        this.vecesSancionado = vecesSancionado;
    }
    
    public void incrementarNMensajesTotal() {
        this.nMensajesTotal ++;
    }
    
    @Override
    public String toString(){
        String resultado = "El nick es: " + this.nick + 
                ", la contraseña es: " + this.password +
                ", ¿está sancionado? " + this.sancionado;
        return resultado;
    }    
}
