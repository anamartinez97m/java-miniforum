/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Ana
 */
public class Foro implements Serializable{
    private ArrayList<Forero> foreros;
    private ArrayList<Moderador> moderadores;
    private ArrayList<Administrador> administradores;
    private ArrayList<Categoria> categorias;
    private ArrayList<Sancionado> sancionados;
    private String tematica;
    
    public Foro(String tematica){
        this.tematica = tematica;
        this.foreros = new ArrayList<>();
        this.moderadores = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.sancionados = new ArrayList<>();
    }
    
    public ArrayList<Forero> getForeros(){
        return this.foreros;
    }
    
    public ArrayList<Moderador> getModeradores(){
        return this.moderadores;
    }
    
    public ArrayList<Administrador> getAdministradores(){
        return this.administradores;
    }
    
    public ArrayList<Categoria> getCategorias(){
        return this.categorias;
    }
    
    public String getTematica(){
        return this.tematica;
    }
    
    public ArrayList<Sancionado> getSancionados() {
        return this.sancionados;
    }
    
    public void setForeros(ArrayList<Forero> foreros){
        this.foreros = foreros;
    }
    
    public void setCategorias(ArrayList<Categoria> categorias){
        this.categorias = categorias;
    }
    
    public void setTematica(String tematica){
        this.tematica = tematica;
    }
    
    @Override
    public String toString(){
        String resultado = "La temática del foro es: " + 
                this.tematica +
                "Los foreros son: ";
        
        for(Forero f: foreros){
            resultado += "\n" + f;
        }
        
        resultado += "Las categorias son: ";
        
        for(Categoria c: categorias){
            resultado += "\n" + c;
        }
        
        return resultado;
    }
    
    public void anadirForero(Forero f) {
        this.foreros.add(f);
    }
    
    public void crearCategoria(Categoria c){
        this.categorias.add(c);
    }
    
    public Categoria obtenerCategoria(String nombre){
        Categoria categoria = null;
        boolean encontrado = false;
        Iterator<Categoria> it = this.categorias.iterator();
        while((!encontrado) && (it.hasNext())){
            categoria = it.next();
            encontrado = categoria.existeCategoria(nombre);
        }
        if(!encontrado){
            categoria = null;
        }
        return categoria;
    }
    
    public void eliminarCategoria(String nombre){
        Categoria categoria = obtenerCategoria(nombre);
        if(categoria != null){
            this.categorias.remove(categoria);
        }
    }
    
    public void listaCategoriasOrdenNumMensajes(){
        ArrayList<Categoria> categoriasCopia = new ArrayList<>(this.categorias);
        Collections.sort(categoriasCopia, new ComparadorNumeroMensajesPublicados());
    }
    
    public void listaModeradoresOrdenSanciones(boolean ordenada){
        if(ordenada){
            ArrayList<Moderador> moderadoresCopia = new ArrayList<>(this.moderadores);
            Collections.sort(moderadoresCopia, new ComparadorNumeroSanciones());
        }
        //mostrarModeradores();
    }
    
    public void nombrarModerador(Forero f){
        Moderador nuevoM = new Moderador(f.getNick(), f.getPassword());
        this.moderadores.add(nuevoM);
        this.foreros.remove(f);
    }
    
    public void destituirModerador(Moderador m){
        Forero f = new Forero(m.getNick(), m.getPassword());
        this.foreros.add(f);
        this.moderadores.remove(m);
    }
    
    public void sancionar(Forero f, Moderador m){
        String nombreF = f.getNick();
        String nombreM = m.getNick();
        Sancionado s = new Sancionado(nombreF, nombreM);
        f.setVecesSancionado(f.getVecesSancionado() + 1);
        f.setSancionado(true);
        this.sancionados.add(s);
        m.incrementarNVecesHaSancionado();
    }
    
    public void levantarSancion(Forero f){
        f.setSancionado(false);
    }
    
    public int numForerosSancionados(Moderador m){
        String nombreM = m.getNick();
        int nVeces = 0;
        
        for(Sancionado s: sancionados){
            if(nombreM.equals(s.getNombreM())){
                nVeces++;
            }
        }
        return nVeces;
    }
    public void publicarMensaje(Hilo h, Mensaje m){
        h.getMensajes().add(m);
    }
    
    public Categoria categoriaMasPublicadaPorUsuario(String nick) {
        Categoria masFrecuente = null;
        int mensajesCategoriaMasFrecuente = 0;
        for (Categoria c : categorias) {
            if (c.getNumeroMensajesUsuarioCategoria(nick) > mensajesCategoriaMasFrecuente) {
                masFrecuente = c;
                mensajesCategoriaMasFrecuente = c.getNumeroMensajesUsuarioCategoria(nick);
            }
        }
        return masFrecuente;
    }
    
    public ArrayList<Hilo> hilosCreadosUsuario(String nick) {
        ArrayList<Hilo> hilosUsuario = new ArrayList<>();
        
        for(Categoria c: categorias) {
            for(Hilo h: c.getHilos()) {
                if(h.getMensajes().get(0).getAutor().equalsIgnoreCase(nick)) {
                    hilosUsuario.add(h);
                }
            }
        }
        
        return hilosUsuario;
    }
}
