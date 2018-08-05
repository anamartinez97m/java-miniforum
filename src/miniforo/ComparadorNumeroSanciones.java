/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.util.Comparator;

/**
 *
 * @author Ana
 */
public class ComparadorNumeroSanciones implements Comparator<Moderador>{
    @Override
    public int compare(Moderador m1, Moderador m2) {
        //Ordeno de mayor a menor
        return m2.getNVecesHaSancionado() - m1.getNVecesHaSancionado();
    }
}