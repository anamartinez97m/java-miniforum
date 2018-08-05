/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.io.Serializable;

/**
 *
 * @author Ana
 */
public class Sancionado implements Serializable{
    private String nombreF;
    private String nombreM;
    
    public Sancionado(String nombreF, String nombreM){
        this.nombreF = nombreF;
        this.nombreM = nombreM;
    }
    
    public String getNombreF(){
        return this.nombreF;
    }
    
    public String getNombreM(){
        return this.nombreM;
    }
}
