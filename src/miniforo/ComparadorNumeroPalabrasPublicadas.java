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
public class ComparadorNumeroPalabrasPublicadas implements Comparator<Categoria>{
    @Override
    public int compare(Categoria c1, Categoria c2) {
        //Ordena de mayor a menor
        return c2.getNumPalabrasTotal() - c1.getNumPalabrasTotal();
    }
}
