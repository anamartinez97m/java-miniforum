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
public class EMensajeNoExiste extends Exception{
    public EMensajeNoExiste(String autor, String titulo, String cuerpo){
        super("El Mensaje de titulo: " + titulo + "del autor: "
        + autor + "no existe.");
    }
}
