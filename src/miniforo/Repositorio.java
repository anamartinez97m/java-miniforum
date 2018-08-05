/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 *
 * @author Ana
 */
public class Repositorio implements Serializable{
    public static void serializarForo(Foro foro, String nombreFichero) throws IOException {
        FileOutputStream fos = new FileOutputStream(nombreFichero);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(foro);
        oos.flush();
        
        fos.close();
        oos.close();
    }
    
    public static Foro deserializarForo(String nombreFichero) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nombreFichero);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        Foro foro = (Foro)ois.readObject();
        
        fis.close();
        ois.close();
        
        return foro;
    }
    
    public static void guardarFicheroForero(Foro foro, Forero forero, String nombreFichero) throws IOException {
        FileWriter fw = new FileWriter(nombreFichero);
        PrintWriter pw = new PrintWriter(fw);
        
        for(Categoria c: foro.getCategorias()) {
            for(Hilo h: c.getHilos()) {
                for(Mensaje m: h.getMensajes()) {
                    if(m.getAutor().equalsIgnoreCase(forero.getNick())) {
                        pw.println("Autor: " + m.getAutor() + ", Titulo: " + m.getTitulo() + ", Cuerpo: " +
                                m.getCuerpo());
                    }
                }
            }
        }
        
        pw.close();
    }
}
