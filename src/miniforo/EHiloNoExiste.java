/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

/**
 *
 * @author Ana
 */
public class EHiloNoExiste extends Exception{
    public EHiloNoExiste(String titulo){
        super("El Hilo con el titulo: " + titulo + "no existe.");
    }
}
