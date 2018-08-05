/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Ana
 */
public class Moderador extends Forero{
    
    private int nVecesHaSancionado;
    
    public Moderador(String nick, String password){
        super(nick, password);
        this.nVecesHaSancionado = 0;
    }
    
    public int getNVecesHaSancionado() {
        return this.nVecesHaSancionado;
    }
    
    public void incrementarNVecesHaSancionado() {
        this.nVecesHaSancionado++;
    }
}
