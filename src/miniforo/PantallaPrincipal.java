/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniforo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 *
 * @author Ana
 */

public class PantallaPrincipal extends javax.swing.JFrame {
    Foro foro = new Foro("series");
    Forero usuario = null;
    Categoria catSeleccionada = null;
    Hilo hiloSeleccionado = null;
    Mensaje mensajeSeleccionado = null;
    Moderador moderadorSeleccionado = null;
    Forero foreroSeleccionado = null;
    
    public PantallaPrincipal() {
        initComponents();
        mostrarPantallaInicial();
        setupForo();
        //cargarUsuarios();
        rellenarCategorias();
    }
    
    public void setupForo() {
        try {
            this.foro = Repositorio.deserializarForo("./miniforo.bin");
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarPantallaInicial() {
        PanelAcceder.setVisible(false);
        PanelLogin.setVisible(true);
    }
    
    /*public void cargarUsuarios(){
        Forero f = new Forero("fran", "1234");
        Forero f2 = new Forero("paco", "holi");
        Forero f3 = new Forero("rafa", "1998");
        Moderador m = new Moderador("ana", "4567");
        Moderador m2 = new Moderador("judith", "1995");
        Moderador m3 = new Moderador("felipe", "1960");
        Administrador a = new Administrador("pluf", "7890");
        
        foro.getForeros().add(f);
        foro.getForeros().add(f2);
        foro.getForeros().add(f3);
        foro.getModeradores().add(m);
        foro.getModeradores().add(m2);
        foro.getModeradores().add(m3);
        foro.getAdministradores().add(a);
        
    }*/
    
    public void rellenarCategorias(){
        /*Hilo h1 = new Hilo("Titulo H1", new Mensaje("Ana", "TituloM", "Holi"), "Ana");
        Hilo h2 = new Hilo("Titulo H2", new Mensaje("Fran", "TituloM", "Holiii"), "Fran");
        h1.crearMensaje(new Mensaje("ana", "Prueba2", "Esto sigue siendo una prueba"));
        Categoria c1 = new Categoria("Categoría 1");
        c1.crearHilo(h1);
        c1.crearHilo(h2);
        
        Hilo h3 = new Hilo("Titulo H3", new Mensaje("Ana", "TituloM3", "Holi3"), "Ana");
        Hilo h4 = new Hilo("Titulo H4", new Mensaje("Fran", "TituloM4", "Holi4"), "Fran");
        Hilo h5 = new Hilo("Titulo H5", new Mensaje("Pluf", "TituloM5", "Holi5"), "Pluf");
        Categoria c2 = new Categoria("Categoría 2");
        c2.crearHilo(h3);
        c2.crearHilo(h4);
        c2.crearHilo(h5);
        
        Hilo h6 = new Hilo("Titulo Hilo 6", new Mensaje("paco", "Titulo del mensaje 1", "Eso es una prueba"), "paco");
        Categoria c3 = new Categoria("Categoría 3");
        c3.crearHilo(h6);
        
        foro.crearCategoria(c1);
        foro.crearCategoria(c2);
        foro.crearCategoria(c3);
        */
        String categorias[] = new String[foro.getCategorias().size()];
        for(int i = 0; i < categorias.length; i++) {
            categorias[i] = foro.getCategorias().get(i).getNombre();
        }
        
        comboCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(categorias));
        
    }
    
    public void rellenarListaCategorias() {
        DefaultListModel<String> modelListaCategorias;
        
        modelListaCategorias = new DefaultListModel<String>();
        for(Categoria c : foro.getCategorias()){
            modelListaCategorias.addElement(c.getNombre());
        }    
        listaCategorias.setModel(modelListaCategorias);     
        listaCategorias.setSelectedIndex(0);
    }
    
    public void rellenarCategoriasOrdenadas(String tipo){
        if(tipo.equals("mensajes")) {
            ComparadorNumeroMensajesPublicados comparador = new ComparadorNumeroMensajesPublicados();
            ArrayList<Categoria> copia = this.foro.getCategorias();
            Collections.sort(copia, comparador);
            
            DefaultListModel<String> modelListaCategorias;
            modelListaCategorias = new DefaultListModel<String>();
            
            for (Categoria c : copia) {
                modelListaCategorias.addElement(c.getNombre() + " (Mensajes: " + c.getNumMensajesTotal() + ")"); 
            }
            listaVerCategorias.setModel(modelListaCategorias); 
            
        } else if (tipo.equals("palabras")){
            ComparadorNumeroPalabrasPublicadas comparador = new ComparadorNumeroPalabrasPublicadas();
            ArrayList<Categoria> copia = this.foro.getCategorias();
            Collections.sort(copia, comparador);
            
            DefaultListModel<String> modelListaCategorias;
            modelListaCategorias = new DefaultListModel<String>();
            
            for (Categoria c : copia) {
                modelListaCategorias.addElement(c.getNombre() + " (Palabras: " + c.getNumPalabrasTotal() + ")");
            }
            listaVerCategorias.setModel(modelListaCategorias); 
        }
    }
    
    public void rellenarListaForerosNoModeradores() {
        DefaultListModel<String> modelListaForerosNoModeradores;
        
        modelListaForerosNoModeradores = new DefaultListModel<String>();
        for(Forero f : foro.getForeros()){
            modelListaForerosNoModeradores.addElement(f.getNick());
        }    
        listaForerosNoModeradores.setModel(modelListaForerosNoModeradores);
    }
    
    public void rellenarListaModeradores() {
        DefaultListModel<String> modelListaModeradores;
        
        modelListaModeradores = new DefaultListModel<String>();
        for(Moderador m : foro.getModeradores()){
            modelListaModeradores.addElement(m.getNick());
        }    
        listaModeradores.setModel(modelListaModeradores);
    }
    
    public void rellenarCategoriasNuevoHilo() {
        String categorias[] = new String[foro.getCategorias().size()];
        for(int i = 0; i < categorias.length; i++) {
            categorias[i] = foro.getCategorias().get(i).getNombre();
        }
        
        comboCategoriasNuevoHilo.setModel(new javax.swing.DefaultComboBoxModel<>(categorias));
    }
    
    public void rellenarComboForeros() {
        comboVerForero.addItem("");
        
        for(Forero f: this.foro.getForeros()) {
            comboVerForero.addItem(f.getNick());
        }
        
        for(Moderador m: this.foro.getModeradores()) {
            comboVerForero.addItem(m.getNick());
        }
        
        for(Administrador a: this.foro.getAdministradores()) {
            comboVerForero.addItem(a.getNick());
        }
    }
    
    public void rellenarHilos(String categoria) {
        Categoria cSeleccionada = null;
        
        for(Categoria c: foro.getCategorias()) {
            if(c.getNombre().equalsIgnoreCase(categoria)) {
                cSeleccionada = c;
            }
        }
        
        if(cSeleccionada != null) {
            DefaultListModel<String> modelHilos;
        
            modelHilos = new DefaultListModel<String>();
            for(Hilo h : cSeleccionada.getHilos()){
                modelHilos.addElement(h.getTitulo());
            }    
            listaHilos.setModel(modelHilos);     
            listaHilos.setSelectedIndex(0);
        }
    }
    
    public void mostrarMenuEspecialM(){
        lblMenuEspecial.setVisible(true);
        comboMenuEspecial.setVisible(true);
        comboMenuEspecial.addItem("");
        comboMenuEspecial.addItem("Sancionar");
        comboMenuEspecial.addItem("Quitar Sancion");
    }
    
    public void mostrarMenuEspecialA(){
        lblMenuEspecial.setVisible(true);
        comboMenuEspecial.setVisible(true);
        comboMenuEspecial.addItem("");
        comboMenuEspecial.addItem("Sancionar");
        comboMenuEspecial.addItem("Quitar Sancion");
        comboMenuEspecial.addItem("Crear Categoria");
        comboMenuEspecial.addItem("Eliminar Categoria");
        comboMenuEspecial.addItem("Nombrar Moderador");
        comboMenuEspecial.addItem("Destituir Moderador");
        comboMenuEspecial.addItem("Ver Moderadores");
        comboMenuEspecial.addItem("Ver Categorias");
        comboMenuEspecial.addItem("Ver Foreros");
        comboMenuEspecial.addItem("Enviar datos forero");

    }
    
    public boolean logear(String userName, String pass){
        boolean logueado = false;
        
        Forero f;
        for(int i = 0; i < foro.getForeros().size() && !logueado; i++) {
            f = foro.getForeros().get(i);
            logueado = (f.getNick().equalsIgnoreCase(userName) && f.getPassword().equalsIgnoreCase(pass));
            if(logueado){
                this.usuario = f;
                btnCerrarSesion.setText("Cerrar Sesión: " + this.usuario.getNick() + "(" + this.usuario.getClass().getSimpleName() + ")");
            }
        }
        
        Moderador m;
        for(int i = 0; i < foro.getModeradores().size() && !logueado; i++) {
            m = foro.getModeradores().get(i);
            logueado = (m.getNick().equalsIgnoreCase(userName) && m.getPassword().equalsIgnoreCase(pass));
            if(logueado){
                this.usuario = m;
                mostrarMenuEspecialM();
                btnCerrarSesion.setText("Cerrar Sesión: " + this.usuario.getNick() + "(" + this.usuario.getClass().getSimpleName() + ")");
                btnEliminarMensaje.setVisible(true);
            }
        }
        
        Administrador a;
        for(int i = 0; i < foro.getAdministradores().size() && !logueado; i++) {
            a = foro.getAdministradores().get(i);
            logueado = (a.getNick().equalsIgnoreCase(userName) && a.getPassword().equalsIgnoreCase(pass));
            if(logueado){
                this.usuario = a;
                mostrarMenuEspecialA();
                btnCerrarSesion.setText("Cerrar Sesión: " + this.usuario.getNick() + "(" + this.usuario.getClass().getSimpleName() + ")");
                btnEliminarMensaje.setVisible(true);
            }
        }
        
        return logueado;
    }
    
    public void mostrarPantallaPrincipal(){
        PanelLogin.setVisible(false);
        PanelAcceder.setVisible(false);
        PanelPantallaPrincipal.setVisible(true);
    }
    
    public void eliminarHiloForero() {
        boolean permitidoBorrar = true;
        
        for(int i = 0; i < this.hiloSeleccionado.getMensajes().size() && permitidoBorrar; i++) {
            permitidoBorrar = this.usuario.getNick().equalsIgnoreCase(this.hiloSeleccionado.getMensajes().get(i).getAutor());
        }
        
        if(permitidoBorrar) {
            this.catSeleccionada.getHilos().remove(this.hiloSeleccionado);
            lblErrorBorrarHilo.setVisible(false);
            rellenarHilos(this.catSeleccionada.getNombre());
        } else {
            lblErrorBorrarHilo.setVisible(true);
        }
    }
    
    public void eliminarHilo() {    
        this.catSeleccionada.getHilos().remove(this.hiloSeleccionado);
        rellenarHilos(this.catSeleccionada.getNombre());
    }
    
    public void mostrarMensajesHilo(){
        DefaultListModel<String> modelMensajes;
        
        modelMensajes = new DefaultListModel<String>();
        for(Mensaje m : this.hiloSeleccionado.getMensajes()){
            String mensaje = "Autor: " + m.getAutor() + " - " + 
                             "Titulo: " + m.getTitulo() + " - " + 
                             "Cuerpo: " + m.getCuerpo();
            modelMensajes.addElement(mensaje);
        }    
        listaMensajes.setModel(modelMensajes);     
        listaMensajes.setSelectedIndex(0);
    }
    
    public void rellenarForeros() {
        DefaultListModel<String> modelForeros;
        
        modelForeros = new DefaultListModel<String>();
        for(Forero f : foro.getForeros()){
            if(!f.getSancionado()) {
                modelForeros.addElement(f.getNick());
            }
        }    
        listaForeros.setModel(modelForeros);     
        //listaForeros.setSelectedIndex(0);
    }
    
    public void rellenarForerosSancionados() {
        DefaultListModel<String> modelForerosSancionados;
        
        modelForerosSancionados = new DefaultListModel<String>();
        for(Forero f : foro.getForeros()){
            if(f.getSancionado()) {
                modelForerosSancionados.addElement(f.getNick());
            }
        }    
        listaForerosSancionados.setModel(modelForerosSancionados); 
    }
    
    public void rellenarDatosVerForero(Forero f) {
        lblNumMensajesForero.setText("" + f.getNMensajesTotal());
        lblVecesSancionadoForero.setText("" + f.getVecesSancionado());
        if(f.getSancionado()) {
            lblActualmenteSancionadoForero.setText("Si");
        } else {
            lblActualmenteSancionadoForero.setText("No");
        }
        
        String nombreCategoria = "Ninguna";
        if(foro.categoriaMasPublicadaPorUsuario(f.getNick()) != null) {
            nombreCategoria = foro.categoriaMasPublicadaPorUsuario(f.getNick()).getNombre();
        }
        lblCatMasPublicaForero.setText("" + nombreCategoria);
        
        DefaultListModel<String> modelHilosForero;
        
        modelHilosForero = new DefaultListModel<String>();
        for(Hilo h : this.foro.hilosCreadosUsuario(f.getNick())){
            modelHilosForero.addElement(h.getTitulo());
        }    
        listaVerHilosForero.setModel(modelHilosForero);
        
    }
    
    public void rellenarComboForerosParaEnviar() {
        comboVerForeroEnviar.addItem("");
        for(Forero f: this.foro.getForeros()) {
            comboVerForeroEnviar.addItem(f.getNick());
        }
        
        for(Moderador m: this.foro.getModeradores()) {
            comboVerForeroEnviar.addItem(m.getNick());
        }
        
        for(Administrador a: this.foro.getAdministradores()) {
            comboVerForeroEnviar.addItem(a.getNick());
        }
        
    }
    
    public void rellenarListaModeradoresSancionadoresOrdenada(boolean ordenada) {
        DefaultListModel<String> modelListaModeradoresSancionadores;
        modelListaModeradoresSancionadores = new DefaultListModel<String>();
        if(!ordenada) {
            for(Moderador m : this.foro.getModeradores()){
                modelListaModeradoresSancionadores.addElement(m.getNick());
            }    
            listaModeradoresSanciones.setModel(modelListaModeradoresSancionadores);
        } else {
            ArrayList<Moderador> copiaModeradores = new ArrayList<>(this.foro.getModeradores());
            Collections.sort(copiaModeradores, new ComparadorNumeroSanciones());
            for(Moderador m : copiaModeradores){
                modelListaModeradoresSancionadores.addElement(m.getNick() + " (Sanciones realizadas: " + m.getNVecesHaSancionado() + ")");
            }    
            listaModeradoresSanciones.setModel(modelListaModeradoresSancionadores);
        }
    }
    
    public void rellenarListaForerosSancionados(String moderador) {
        DefaultListModel<String> modelListaSancionados;
        modelListaSancionados = new DefaultListModel<String>();
        
        for(Sancionado s: this.foro.getSancionados()) {
            if(s.getNombreM().equalsIgnoreCase(moderador)) {
                modelListaSancionados.addElement(s.getNombreF());
            }
        }
        listaSancionadosPorModerador.setModel(modelListaSancionados);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        PanelAcceder = new javax.swing.JPanel();
        PanelAcceder.setVisible(false);
        userName = new javax.swing.JLabel();
        pass = new javax.swing.JLabel();
        userNameTxt = new javax.swing.JTextField();
        passTxt = new javax.swing.JPasswordField();
        btnEntrar = new javax.swing.JButton();
        lblErrorLogin = new javax.swing.JLabel();
        lblErrorLogin.setVisible(false);
        btnVolverLogin = new javax.swing.JButton();
        lblTituloAcceder = new javax.swing.JLabel();
        PanelLogin = new javax.swing.JPanel();
        TituloLogin = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();
        btnNuevoUsuario = new javax.swing.JButton();
        PanelCrearNuevoUsuario = new javax.swing.JPanel();
        PanelCrearNuevoUsuario.setVisible(false);
        userNameNuevoUsuario = new javax.swing.JLabel();
        passNuevoUsuario = new javax.swing.JLabel();
        userNameTxtNuevoUsuario = new javax.swing.JTextField();
        passTxtNuevoUsuario = new javax.swing.JPasswordField();
        btnCrearNuevoUsuario = new javax.swing.JButton();
        lblErrorCrearNuevoUsuario = new javax.swing.JLabel();
        lblErrorCrearNuevoUsuario.setVisible(false);
        btnVolverCrearNuevoUsuario = new javax.swing.JButton();
        lblCrearNuevoUsuario = new javax.swing.JLabel();
        PanelPantallaPrincipal = new javax.swing.JPanel();
        PanelPantallaPrincipal.setVisible(false);
        btnCerrarSesion = new javax.swing.JButton();
        subPanelHilos = new javax.swing.JPanel();
        comboCategorias = new javax.swing.JComboBox<>();
        lblCategoria = new javax.swing.JLabel();
        lblHilos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaHilos = new javax.swing.JList<>();
        lblMenuUsuario = new javax.swing.JLabel();
        btnCrearHilo = new javax.swing.JButton();
        btnEliminarHilo = new javax.swing.JButton();
        lblMenuEspecial = new javax.swing.JLabel();
        lblMenuEspecial.setVisible(false);
        comboMenuEspecial = new javax.swing.JComboBox<>();
        comboMenuEspecial.setVisible(false);
        lblErrorBorrarHilo = new javax.swing.JLabel();
        lblErrorBorrarHilo.setVisible(false);
        subPanelCrearHilo = new javax.swing.JPanel();
        subPanelCrearHilo.setVisible(false);
        lblNombreHilo = new javax.swing.JLabel();
        lblCrearHilo = new javax.swing.JLabel();
        lblTituloMsgInicial = new javax.swing.JLabel();
        lblMensajeInicial = new javax.swing.JLabel();
        txtNombreHilo = new javax.swing.JTextField();
        txtTituloMensajeInicial = new javax.swing.JTextField();
        txtMensajeInicial = new javax.swing.JScrollPane();
        txtCuerpoMensajeInicial = new javax.swing.JTextArea();
        lblCategoriaNuevoHilo = new javax.swing.JLabel();
        comboCategoriasNuevoHilo = new javax.swing.JComboBox<>();
        btnCrearNuevoHilo = new javax.swing.JButton();
        btnAtrasCrearNuevoHilo = new javax.swing.JButton();
        subPanelMensajes = new javax.swing.JPanel();
        subPanelMensajes.setVisible(false);
        btnAtrasPanelMensaje = new javax.swing.JButton();
        lblTitMensajesHilo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaMensajes = new javax.swing.JList<>();
        btnPublicarMensaje = new javax.swing.JButton();
        btnEliminarMensaje = new javax.swing.JButton();
        btnEliminarMensaje.setVisible(false);
        subPanelCrearMensaje = new javax.swing.JPanel();
        subPanelCrearMensaje.setVisible(false);
        btnAtrasPanelCrearMensaje = new javax.swing.JButton();
        lblCrearMensaje = new javax.swing.JLabel();
        lblTituloNuevoMensaje = new javax.swing.JLabel();
        lblCuerpoMensaje = new javax.swing.JLabel();
        txtTituloNuevoMensaje = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtCuerpoNuevoMensaje = new javax.swing.JTextArea();
        btnPublicarNuevoMensaje = new javax.swing.JButton();
        subPanelSancionar = new javax.swing.JPanel();
        subPanelSancionar.setVisible(false);
        btnAtrasPanelSancionar = new javax.swing.JButton();
        lblListaForeros = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaForeros = new javax.swing.JList<>();
        btnSancionar = new javax.swing.JButton();
        subPanelQuitarSancion = new javax.swing.JPanel();
        subPanelQuitarSancion.setVisible(false);
        btnAtrasPanelQuitarSancion = new javax.swing.JButton();
        lblListaForerosSancionados = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listaForerosSancionados = new javax.swing.JList<>();
        btnQuitarSancion = new javax.swing.JButton();
        subPanelCrearCategoria = new javax.swing.JPanel();
        subPanelCrearCategoria.setVisible(false);
        btnAtrasPanelCrearCategoria = new javax.swing.JButton();
        lblCrearCategoria = new javax.swing.JLabel();
        lblTituloNuevaCategoria = new javax.swing.JLabel();
        txtTituloNuevaCategoria = new javax.swing.JTextField();
        btnCrearNuevaCategoria = new javax.swing.JButton();
        subPanelEliminarCategoria = new javax.swing.JPanel();
        subPanelEliminarCategoria.setVisible(false);
        btnAtrasPanelEliminarCategoria = new javax.swing.JButton();
        lblListaCategorias = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        listaCategorias = new javax.swing.JList<>();
        btnEliminarCategoria = new javax.swing.JButton();
        subPanelNombrarModerador = new javax.swing.JPanel();
        subPanelNombrarModerador.setVisible(false);
        btnAtrasPanelNombrarModerador = new javax.swing.JButton();
        lblListaForerosNoModeradores = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        listaForerosNoModeradores = new javax.swing.JList<>();
        btnNombrarModerador = new javax.swing.JButton();
        subPanelDestituirModerador = new javax.swing.JPanel();
        subPanelDestituirModerador.setVisible(false);
        btnAtrasPanelDestituirModerador = new javax.swing.JButton();
        lblListaModeradores = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        listaModeradores = new javax.swing.JList<>();
        btnDestituirModerador = new javax.swing.JButton();
        subPanelVerCategorias = new javax.swing.JPanel();
        subPanelVerCategorias.setVisible(false);
        btnAtrasPanelVerCategorias = new javax.swing.JButton();
        lblTitVerCategorias = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        listaVerCategorias = new javax.swing.JList<>();
        btnOrdenarCatPorMensajes = new javax.swing.JButton();
        btnOrdenarCatPorPalabras = new javax.swing.JButton();
        subPanelVerForeros = new javax.swing.JPanel();
        subPanelVerForeros.setVisible(false);
        btnAtrasPanelVerForeros = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        listaVerHilosForero = new javax.swing.JList<>();
        comboVerForero = new javax.swing.JComboBox<>();
        lblForeroSeleccionado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNumeroMensajesForero = new javax.swing.JLabel();
        lblVecesSancionado = new javax.swing.JLabel();
        lblActualmenteSancionado = new javax.swing.JLabel();
        lblNumMensajesForero = new javax.swing.JLabel();
        lblVecesSancionadoForero = new javax.swing.JLabel();
        lblActualmenteSancionadoForero = new javax.swing.JLabel();
        lblCategoriaMasPublica = new javax.swing.JLabel();
        lblCatMasPublicaForero = new javax.swing.JLabel();
        subPanelEnviarDatosForero = new javax.swing.JPanel();
        subPanelEnviarDatosForero.setVisible(false);
        btnAtrasPanelEnviarDatosForero = new javax.swing.JButton();
        comboVerForeroEnviar = new javax.swing.JComboBox<>();
        lblForeroSeleccionadoParaEnviar = new javax.swing.JLabel();
        lblEnviarDatosForero = new javax.swing.JLabel();
        txtRutaGuardarForero = new javax.swing.JTextField();
        btnGuardarForero = new javax.swing.JButton();
        subPanelModeradoresSanciones = new javax.swing.JPanel();
        subPanelModeradoresSanciones.setVisible(false);
        btnAtrasPanelModeradoresSanciones = new javax.swing.JButton();
        lblTitModeradoresSanciones = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        listaModeradoresSanciones = new javax.swing.JList<>();
        btnOrdenarModeradoresSanciones = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        listaSancionadosPorModerador = new javax.swing.JList<>();
        lblTituloSancionadosPorModerador = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MiniForoApp");
        setPreferredSize(new java.awt.Dimension(1024, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanelAcceder.setPreferredSize(new java.awt.Dimension(1024, 600));

        userName.setText("Nombre de Usuario:");

        pass.setText("Contraseña:");

        btnEntrar.setText("Entrar");
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        lblErrorLogin.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorLogin.setText("Error. Nombre de usuario y/o contraseña incorrectos.");

        btnVolverLogin.setText("Atrás");
        btnVolverLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverLoginActionPerformed(evt);
            }
        });

        lblTituloAcceder.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTituloAcceder.setText("Acceder");

        javax.swing.GroupLayout PanelAccederLayout = new javax.swing.GroupLayout(PanelAcceder);
        PanelAcceder.setLayout(PanelAccederLayout);
        PanelAccederLayout.setHorizontalGroup(
            PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAccederLayout.createSequentialGroup()
                .addContainerGap(328, Short.MAX_VALUE)
                .addGroup(PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelAccederLayout.createSequentialGroup()
                        .addGroup(PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userName)
                            .addComponent(pass))
                        .addGap(71, 71, 71)
                        .addGroup(PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(passTxt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userNameTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblErrorLogin)
                    .addComponent(btnEntrar))
                .addGap(305, 305, 305))
            .addGroup(PanelAccederLayout.createSequentialGroup()
                .addGroup(PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAccederLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnVolverLogin))
                    .addGroup(PanelAccederLayout.createSequentialGroup()
                        .addGap(484, 484, 484)
                        .addComponent(lblTituloAcceder)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelAccederLayout.setVerticalGroup(
            PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAccederLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVolverLogin)
                .addGap(48, 48, 48)
                .addComponent(lblTituloAcceder)
                .addGap(117, 117, 117)
                .addGroup(PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userName)
                    .addComponent(userNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(PanelAccederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pass)
                    .addComponent(passTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblErrorLogin)
                .addGap(38, 38, 38)
                .addComponent(btnEntrar)
                .addContainerGap(207, Short.MAX_VALUE))
        );

        PanelLogin.setPreferredSize(new java.awt.Dimension(1024, 600));

        TituloLogin.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        TituloLogin.setText("MiniForo");

        btnIniciarSesion.setText("Iniciar Sesión");
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        btnNuevoUsuario.setText("Crear nuevo usuario");
        btnNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelLoginLayout = new javax.swing.GroupLayout(PanelLogin);
        PanelLogin.setLayout(PanelLoginLayout);
        PanelLoginLayout.setHorizontalGroup(
            PanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLoginLayout.createSequentialGroup()
                .addContainerGap(439, Short.MAX_VALUE)
                .addGroup(PanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLoginLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnNuevoUsuario))
                    .addGroup(PanelLoginLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnIniciarSesion))
                    .addComponent(TituloLogin))
                .addGap(420, 420, 420))
        );
        PanelLoginLayout.setVerticalGroup(
            PanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLoginLayout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(TituloLogin)
                .addGap(29, 29, 29)
                .addComponent(btnIniciarSesion)
                .addGap(18, 18, 18)
                .addComponent(btnNuevoUsuario)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        PanelCrearNuevoUsuario.setPreferredSize(new java.awt.Dimension(1024, 600));

        userNameNuevoUsuario.setText("Nombre de Usuario:");

        passNuevoUsuario.setText("Contraseña:");

        btnCrearNuevoUsuario.setText("Crear");
        btnCrearNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearNuevoUsuarioActionPerformed(evt);
            }
        });

        lblErrorCrearNuevoUsuario.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorCrearNuevoUsuario.setText("Error. Nombre de usuario ya existe.");

        btnVolverCrearNuevoUsuario.setText("Atrás");
        btnVolverCrearNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverCrearNuevoUsuarioActionPerformed(evt);
            }
        });

        lblCrearNuevoUsuario.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCrearNuevoUsuario.setText("Crear Nuevo Usuario");

        javax.swing.GroupLayout PanelCrearNuevoUsuarioLayout = new javax.swing.GroupLayout(PanelCrearNuevoUsuario);
        PanelCrearNuevoUsuario.setLayout(PanelCrearNuevoUsuarioLayout);
        PanelCrearNuevoUsuarioLayout.setHorizontalGroup(
            PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCrearNuevoUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCrearNuevoUsuarioLayout.createSequentialGroup()
                        .addComponent(btnVolverCrearNuevoUsuario)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCrearNuevoUsuarioLayout.createSequentialGroup()
                        .addGap(0, 318, Short.MAX_VALUE)
                        .addGroup(PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelCrearNuevoUsuarioLayout.createSequentialGroup()
                                .addGroup(PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(userNameNuevoUsuario)
                                    .addComponent(passNuevoUsuario))
                                .addGap(71, 71, 71)
                                .addGroup(PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(passTxtNuevoUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(userNameTxtNuevoUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblErrorCrearNuevoUsuario)
                            .addComponent(btnCrearNuevoUsuario))
                        .addGap(305, 305, 305))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCrearNuevoUsuarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCrearNuevoUsuario)
                .addGap(363, 363, 363))
        );
        PanelCrearNuevoUsuarioLayout.setVerticalGroup(
            PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCrearNuevoUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVolverCrearNuevoUsuario)
                .addGap(53, 53, 53)
                .addComponent(lblCrearNuevoUsuario)
                .addGap(112, 112, 112)
                .addGroup(PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameNuevoUsuario)
                    .addComponent(userNameTxtNuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(PanelCrearNuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passNuevoUsuario)
                    .addComponent(passTxtNuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblErrorCrearNuevoUsuario)
                .addGap(38, 38, 38)
                .addComponent(btnCrearNuevoUsuario)
                .addContainerGap(207, Short.MAX_VALUE))
        );

        PanelPantallaPrincipal.setPreferredSize(new java.awt.Dimension(1024, 600));

        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        comboCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriasActionPerformed(evt);
            }
        });

        lblCategoria.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCategoria.setText("Categoría");

        lblHilos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHilos.setText("Hilos");

        listaHilos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaHilos.setToolTipText("Para ver los mensajes del hilo, pulsa dos veces");
        listaHilos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaHilosMouseClicked(evt);
            }
        });
        listaHilos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaHilosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listaHilos);

        lblMenuUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMenuUsuario.setText("Menú de Usuario");

        btnCrearHilo.setText("Crear Hilo");
        btnCrearHilo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearHiloActionPerformed(evt);
            }
        });

        btnEliminarHilo.setText("Eliminar Hilo");
        btnEliminarHilo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarHiloActionPerformed(evt);
            }
        });

        lblMenuEspecial.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMenuEspecial.setText("Menú Especial");

        comboMenuEspecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMenuEspecialActionPerformed(evt);
            }
        });

        lblErrorBorrarHilo.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorBorrarHilo.setText("Error: No se pudo borrar el hilo");

        javax.swing.GroupLayout subPanelHilosLayout = new javax.swing.GroupLayout(subPanelHilos);
        subPanelHilos.setLayout(subPanelHilosLayout);
        subPanelHilosLayout.setHorizontalGroup(
            subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelHilosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelHilosLayout.createSequentialGroup()
                        .addGroup(subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCategoria)
                            .addComponent(comboCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addGroup(subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMenuUsuario)
                            .addGroup(subPanelHilosLayout.createSequentialGroup()
                                .addComponent(btnCrearHilo)
                                .addGap(18, 18, 18)
                                .addGroup(subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblErrorBorrarHilo)
                                    .addComponent(btnEliminarHilo))))
                        .addGap(164, 164, 164)
                        .addGroup(subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMenuEspecial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboMenuEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHilos)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        subPanelHilosLayout.setVerticalGroup(
            subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelHilosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategoria)
                    .addComponent(lblMenuUsuario)
                    .addComponent(lblMenuEspecial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(subPanelHilosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrearHilo)
                    .addComponent(btnEliminarHilo)
                    .addComponent(comboMenuEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblErrorBorrarHilo)
                .addGap(5, 5, 5)
                .addComponent(lblHilos)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        lblNombreHilo.setText("Nombre Hilo:");

        lblCrearHilo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCrearHilo.setText("Crear Hilo");

        lblTituloMsgInicial.setText("Título mensaje inicial:");

        lblMensajeInicial.setText("Mensaje inicial:");

        txtTituloMensajeInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloMensajeInicialActionPerformed(evt);
            }
        });

        txtCuerpoMensajeInicial.setColumns(20);
        txtCuerpoMensajeInicial.setRows(5);
        txtMensajeInicial.setViewportView(txtCuerpoMensajeInicial);

        lblCategoriaNuevoHilo.setText("Categoría:");

        btnCrearNuevoHilo.setText("Crear");
        btnCrearNuevoHilo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearNuevoHiloActionPerformed(evt);
            }
        });

        btnAtrasCrearNuevoHilo.setText("Atrás");
        btnAtrasCrearNuevoHilo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasCrearNuevoHiloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelCrearHiloLayout = new javax.swing.GroupLayout(subPanelCrearHilo);
        subPanelCrearHilo.setLayout(subPanelCrearHiloLayout);
        subPanelCrearHiloLayout.setHorizontalGroup(
            subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelCrearHiloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAtrasCrearNuevoHilo)
                .addGap(107, 107, 107)
                .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCrearHilo)
                    .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnCrearNuevoHilo)
                        .addGroup(subPanelCrearHiloLayout.createSequentialGroup()
                            .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNombreHilo)
                                .addComponent(lblTituloMsgInicial)
                                .addComponent(lblMensajeInicial)
                                .addComponent(lblCategoriaNuevoHilo))
                            .addGap(56, 56, 56)
                            .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombreHilo)
                                .addComponent(txtTituloMensajeInicial)
                                .addComponent(txtMensajeInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                .addComponent(comboCategoriasNuevoHilo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(488, Short.MAX_VALUE))
        );
        subPanelCrearHiloLayout.setVerticalGroup(
            subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelCrearHiloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCrearHilo)
                    .addComponent(btnAtrasCrearNuevoHilo))
                .addGap(26, 26, 26)
                .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategoriaNuevoHilo)
                    .addComponent(comboCategoriasNuevoHilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreHilo)
                    .addComponent(txtNombreHilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloMsgInicial)
                    .addComponent(txtTituloMensajeInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(subPanelCrearHiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMensajeInicial)
                    .addComponent(txtMensajeInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCrearNuevoHilo)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        subPanelMensajes.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelMensaje.setText("Atrás");
        btnAtrasPanelMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelMensajeActionPerformed(evt);
            }
        });

        lblTitMensajesHilo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitMensajesHilo.setText("Mensajes");

        jScrollPane2.setPreferredSize(new java.awt.Dimension(258, 130));

        listaMensajes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaMensajesValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listaMensajes);

        btnPublicarMensaje.setText("Publicar Mensaje");
        btnPublicarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPublicarMensajeActionPerformed(evt);
            }
        });

        btnEliminarMensaje.setText("Eliminar Mensaje");
        btnEliminarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMensajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelMensajesLayout = new javax.swing.GroupLayout(subPanelMensajes);
        subPanelMensajes.setLayout(subPanelMensajesLayout);
        subPanelMensajesLayout.setHorizontalGroup(
            subPanelMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelMensajesLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelMensajesLayout.createSequentialGroup()
                        .addComponent(lblTitMensajesHilo)
                        .addGap(124, 124, 124)
                        .addComponent(btnPublicarMensaje)
                        .addGap(30, 30, 30)
                        .addComponent(btnEliminarMensaje))
                    .addComponent(btnAtrasPanelMensaje)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        subPanelMensajesLayout.setVerticalGroup(
            subPanelMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelMensajesLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelMensaje)
                .addGap(35, 35, 35)
                .addGroup(subPanelMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelMensajesLayout.createSequentialGroup()
                        .addComponent(lblTitMensajesHilo)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subPanelMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPublicarMensaje)
                        .addComponent(btnEliminarMensaje)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        subPanelCrearMensaje.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelCrearMensaje.setText("Atrás");
        btnAtrasPanelCrearMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelCrearMensajeActionPerformed(evt);
            }
        });

        lblCrearMensaje.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCrearMensaje.setText("Crear Mensaje");

        lblTituloNuevoMensaje.setText("Titulo Mensaje:");

        lblCuerpoMensaje.setText("Cuerpo Mensaje:");

        txtCuerpoNuevoMensaje.setColumns(20);
        txtCuerpoNuevoMensaje.setRows(5);
        jScrollPane3.setViewportView(txtCuerpoNuevoMensaje);

        btnPublicarNuevoMensaje.setText("Publicar");
        btnPublicarNuevoMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPublicarNuevoMensajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelCrearMensajeLayout = new javax.swing.GroupLayout(subPanelCrearMensaje);
        subPanelCrearMensaje.setLayout(subPanelCrearMensajeLayout);
        subPanelCrearMensajeLayout.setHorizontalGroup(
            subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelCrearMensajeLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCrearMensaje)
                    .addComponent(btnAtrasPanelCrearMensaje)
                    .addGroup(subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnPublicarNuevoMensaje)
                        .addGroup(subPanelCrearMensajeLayout.createSequentialGroup()
                            .addGroup(subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTituloNuevoMensaje)
                                .addComponent(lblCuerpoMensaje))
                            .addGap(54, 54, 54)
                            .addGroup(subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTituloNuevoMensaje)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))))
                .addContainerGap(731, Short.MAX_VALUE))
        );
        subPanelCrearMensajeLayout.setVerticalGroup(
            subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelCrearMensajeLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelCrearMensaje)
                .addGap(35, 35, 35)
                .addComponent(lblCrearMensaje)
                .addGap(35, 35, 35)
                .addGroup(subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloNuevoMensaje)
                    .addComponent(txtTituloNuevoMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(subPanelCrearMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCuerpoMensaje)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPublicarNuevoMensaje)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        subPanelSancionar.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelSancionar.setText("Atrás");
        btnAtrasPanelSancionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelSancionarActionPerformed(evt);
            }
        });

        lblListaForeros.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblListaForeros.setText("Foreros");

        listaForeros.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaForerosValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(listaForeros);

        btnSancionar.setText("Sancionar");
        btnSancionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSancionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelSancionarLayout = new javax.swing.GroupLayout(subPanelSancionar);
        subPanelSancionar.setLayout(subPanelSancionarLayout);
        subPanelSancionarLayout.setHorizontalGroup(
            subPanelSancionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelSancionarLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelSancionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelSancionarLayout.createSequentialGroup()
                        .addComponent(lblListaForeros)
                        .addGap(124, 124, 124)
                        .addComponent(btnSancionar))
                    .addComponent(btnAtrasPanelSancionar)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        subPanelSancionarLayout.setVerticalGroup(
            subPanelSancionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelSancionarLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelSancionar)
                .addGap(35, 35, 35)
                .addGroup(subPanelSancionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelSancionarLayout.createSequentialGroup()
                        .addComponent(lblListaForeros)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSancionar))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        subPanelQuitarSancion.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelQuitarSancion.setText("Atrás");
        btnAtrasPanelQuitarSancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelQuitarSancionActionPerformed(evt);
            }
        });

        lblListaForerosSancionados.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblListaForerosSancionados.setText("Foreros Sancionados");

        listaForerosSancionados.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaForerosSancionadosValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(listaForerosSancionados);

        btnQuitarSancion.setText("Quitar Sancion");
        btnQuitarSancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarSancionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelQuitarSancionLayout = new javax.swing.GroupLayout(subPanelQuitarSancion);
        subPanelQuitarSancion.setLayout(subPanelQuitarSancionLayout);
        subPanelQuitarSancionLayout.setHorizontalGroup(
            subPanelQuitarSancionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelQuitarSancionLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelQuitarSancionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelQuitarSancionLayout.createSequentialGroup()
                        .addComponent(lblListaForerosSancionados)
                        .addGap(124, 124, 124)
                        .addComponent(btnQuitarSancion))
                    .addComponent(btnAtrasPanelQuitarSancion)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        subPanelQuitarSancionLayout.setVerticalGroup(
            subPanelQuitarSancionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelQuitarSancionLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelQuitarSancion)
                .addGap(35, 35, 35)
                .addGroup(subPanelQuitarSancionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelQuitarSancionLayout.createSequentialGroup()
                        .addComponent(lblListaForerosSancionados)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnQuitarSancion))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        subPanelCrearCategoria.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelCrearCategoria.setText("Atrás");
        btnAtrasPanelCrearCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelCrearCategoriaActionPerformed(evt);
            }
        });

        lblCrearCategoria.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCrearCategoria.setText("Crear Categoria");

        lblTituloNuevaCategoria.setText("Titulo Categoria:");

        btnCrearNuevaCategoria.setText("Crear");
        btnCrearNuevaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearNuevaCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelCrearCategoriaLayout = new javax.swing.GroupLayout(subPanelCrearCategoria);
        subPanelCrearCategoria.setLayout(subPanelCrearCategoriaLayout);
        subPanelCrearCategoriaLayout.setHorizontalGroup(
            subPanelCrearCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelCrearCategoriaLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelCrearCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCrearCategoria)
                    .addComponent(btnAtrasPanelCrearCategoria)
                    .addGroup(subPanelCrearCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnCrearNuevaCategoria)
                        .addGroup(subPanelCrearCategoriaLayout.createSequentialGroup()
                            .addComponent(lblTituloNuevaCategoria)
                            .addGap(63, 63, 63)
                            .addComponent(txtTituloNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(724, Short.MAX_VALUE))
        );
        subPanelCrearCategoriaLayout.setVerticalGroup(
            subPanelCrearCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelCrearCategoriaLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelCrearCategoria)
                .addGap(35, 35, 35)
                .addComponent(lblCrearCategoria)
                .addGap(35, 35, 35)
                .addGroup(subPanelCrearCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloNuevaCategoria)
                    .addComponent(txtTituloNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCrearNuevaCategoria)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        subPanelEliminarCategoria.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelEliminarCategoria.setText("Atrás");
        btnAtrasPanelEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelEliminarCategoriaActionPerformed(evt);
            }
        });

        lblListaCategorias.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblListaCategorias.setText("Categorias");

        listaCategorias.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaCategoriasValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(listaCategorias);

        btnEliminarCategoria.setText("Eliminar Categoria");
        btnEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelEliminarCategoriaLayout = new javax.swing.GroupLayout(subPanelEliminarCategoria);
        subPanelEliminarCategoria.setLayout(subPanelEliminarCategoriaLayout);
        subPanelEliminarCategoriaLayout.setHorizontalGroup(
            subPanelEliminarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelEliminarCategoriaLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelEliminarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelEliminarCategoriaLayout.createSequentialGroup()
                        .addComponent(lblListaCategorias)
                        .addGap(124, 124, 124)
                        .addComponent(btnEliminarCategoria))
                    .addComponent(btnAtrasPanelEliminarCategoria)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        subPanelEliminarCategoriaLayout.setVerticalGroup(
            subPanelEliminarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelEliminarCategoriaLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelEliminarCategoria)
                .addGap(35, 35, 35)
                .addGroup(subPanelEliminarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelEliminarCategoriaLayout.createSequentialGroup()
                        .addComponent(lblListaCategorias)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEliminarCategoria))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        subPanelNombrarModerador.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelNombrarModerador.setText("Atrás");
        btnAtrasPanelNombrarModerador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelNombrarModeradorActionPerformed(evt);
            }
        });

        lblListaForerosNoModeradores.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblListaForerosNoModeradores.setText("Foreros");

        listaForerosNoModeradores.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaForerosNoModeradoresValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(listaForerosNoModeradores);

        btnNombrarModerador.setText("Nombrar Moderador");
        btnNombrarModerador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNombrarModeradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelNombrarModeradorLayout = new javax.swing.GroupLayout(subPanelNombrarModerador);
        subPanelNombrarModerador.setLayout(subPanelNombrarModeradorLayout);
        subPanelNombrarModeradorLayout.setHorizontalGroup(
            subPanelNombrarModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelNombrarModeradorLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelNombrarModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelNombrarModeradorLayout.createSequentialGroup()
                        .addComponent(lblListaForerosNoModeradores)
                        .addGap(124, 124, 124)
                        .addComponent(btnNombrarModerador))
                    .addComponent(btnAtrasPanelNombrarModerador)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        subPanelNombrarModeradorLayout.setVerticalGroup(
            subPanelNombrarModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelNombrarModeradorLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelNombrarModerador)
                .addGap(35, 35, 35)
                .addGroup(subPanelNombrarModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelNombrarModeradorLayout.createSequentialGroup()
                        .addComponent(lblListaForerosNoModeradores)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNombrarModerador))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        subPanelDestituirModerador.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelDestituirModerador.setText("Atrás");
        btnAtrasPanelDestituirModerador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelDestituirModeradorActionPerformed(evt);
            }
        });

        lblListaModeradores.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblListaModeradores.setText("Moderadores");

        listaModeradores.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaModeradoresValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(listaModeradores);

        btnDestituirModerador.setText("Destituir Moderador");
        btnDestituirModerador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDestituirModeradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelDestituirModeradorLayout = new javax.swing.GroupLayout(subPanelDestituirModerador);
        subPanelDestituirModerador.setLayout(subPanelDestituirModeradorLayout);
        subPanelDestituirModeradorLayout.setHorizontalGroup(
            subPanelDestituirModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelDestituirModeradorLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelDestituirModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelDestituirModeradorLayout.createSequentialGroup()
                        .addComponent(lblListaModeradores)
                        .addGap(124, 124, 124)
                        .addComponent(btnDestituirModerador))
                    .addComponent(btnAtrasPanelDestituirModerador)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        subPanelDestituirModeradorLayout.setVerticalGroup(
            subPanelDestituirModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelDestituirModeradorLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelDestituirModerador)
                .addGap(35, 35, 35)
                .addGroup(subPanelDestituirModeradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelDestituirModeradorLayout.createSequentialGroup()
                        .addComponent(lblListaModeradores)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDestituirModerador))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        subPanelVerCategorias.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelVerCategorias.setText("Atrás");
        btnAtrasPanelVerCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelVerCategoriasActionPerformed(evt);
            }
        });

        lblTitVerCategorias.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitVerCategorias.setText("Categorias");

        listaVerCategorias.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaVerCategoriasValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(listaVerCategorias);

        btnOrdenarCatPorMensajes.setText("Ordenar por numero de mensajes");
        btnOrdenarCatPorMensajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarCatPorMensajesActionPerformed(evt);
            }
        });

        btnOrdenarCatPorPalabras.setText("Ordenar por numero de palabras");
        btnOrdenarCatPorPalabras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarCatPorPalabrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelVerCategoriasLayout = new javax.swing.GroupLayout(subPanelVerCategorias);
        subPanelVerCategorias.setLayout(subPanelVerCategoriasLayout);
        subPanelVerCategoriasLayout.setHorizontalGroup(
            subPanelVerCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelVerCategoriasLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelVerCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelVerCategoriasLayout.createSequentialGroup()
                        .addComponent(lblTitVerCategorias)
                        .addGap(106, 106, 106)
                        .addComponent(btnOrdenarCatPorMensajes)
                        .addGap(48, 48, 48)
                        .addComponent(btnOrdenarCatPorPalabras))
                    .addComponent(btnAtrasPanelVerCategorias)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        subPanelVerCategoriasLayout.setVerticalGroup(
            subPanelVerCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelVerCategoriasLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelVerCategorias)
                .addGap(35, 35, 35)
                .addGroup(subPanelVerCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitVerCategorias)
                    .addComponent(btnOrdenarCatPorMensajes)
                    .addComponent(btnOrdenarCatPorPalabras))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subPanelVerForeros.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelVerForeros.setText("Atrás");
        btnAtrasPanelVerForeros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelVerForerosActionPerformed(evt);
            }
        });

        listaVerHilosForero.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaVerHilosForeroValueChanged(evt);
            }
        });
        jScrollPane10.setViewportView(listaVerHilosForero);

        comboVerForero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboVerForeroItemStateChanged(evt);
            }
        });
        comboVerForero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVerForeroActionPerformed(evt);
            }
        });

        lblForeroSeleccionado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblForeroSeleccionado.setText("Forero");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Hilos creados");

        lblNumeroMensajesForero.setText("Nº de mensajes:");

        lblVecesSancionado.setText("Veces sancionado:");

        lblActualmenteSancionado.setText("Actualmente sancionado:");

        lblNumMensajesForero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNumMensajesForero.setText("0");

        lblVecesSancionadoForero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblVecesSancionadoForero.setText("0");

        lblActualmenteSancionadoForero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblActualmenteSancionadoForero.setText("No");

        lblCategoriaMasPublica.setText("Categoría en la que más publica:");

        lblCatMasPublicaForero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCatMasPublicaForero.setText("Ninguna");

        javax.swing.GroupLayout subPanelVerForerosLayout = new javax.swing.GroupLayout(subPanelVerForeros);
        subPanelVerForeros.setLayout(subPanelVerForerosLayout);
        subPanelVerForerosLayout.setHorizontalGroup(
            subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnAtrasPanelVerForeros)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                        .addGroup(subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblForeroSeleccionado)
                            .addComponent(comboVerForero, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                                .addComponent(lblNumeroMensajesForero)
                                .addGap(60, 60, 60)
                                .addComponent(lblVecesSancionado)
                                .addGap(53, 53, 53)
                                .addComponent(lblActualmenteSancionado))
                            .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(lblNumMensajesForero, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(121, 121, 121)
                                .addComponent(lblVecesSancionadoForero, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(137, 137, 137)
                                .addComponent(lblActualmenteSancionadoForero, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(71, 71, 71)
                        .addGroup(subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblCatMasPublicaForero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblCategoriaMasPublica))))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        subPanelVerForerosLayout.setVerticalGroup(
            subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelVerForeros)
                .addGap(27, 27, 27)
                .addGroup(subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                        .addComponent(lblForeroSeleccionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboVerForero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subPanelVerForerosLayout.createSequentialGroup()
                        .addGroup(subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNumeroMensajesForero)
                            .addComponent(lblVecesSancionado)
                            .addComponent(lblActualmenteSancionado)
                            .addComponent(lblCategoriaMasPublica))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(subPanelVerForerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNumMensajesForero)
                            .addComponent(lblVecesSancionadoForero)
                            .addComponent(lblActualmenteSancionadoForero)
                            .addComponent(lblCatMasPublicaForero))))
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subPanelEnviarDatosForero.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelEnviarDatosForero.setText("Atrás");
        btnAtrasPanelEnviarDatosForero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelEnviarDatosForeroActionPerformed(evt);
            }
        });

        comboVerForeroEnviar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboVerForeroEnviarItemStateChanged(evt);
            }
        });
        comboVerForeroEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVerForeroEnviarActionPerformed(evt);
            }
        });

        lblForeroSeleccionadoParaEnviar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblForeroSeleccionadoParaEnviar.setText("Forero");

        lblEnviarDatosForero.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblEnviarDatosForero.setText("Ruta de guardado:");

        txtRutaGuardarForero.setToolTipText("Ej: \"./nombre.txt\"");

        btnGuardarForero.setText("Guardar");
        btnGuardarForero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarForeroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPanelEnviarDatosForeroLayout = new javax.swing.GroupLayout(subPanelEnviarDatosForero);
        subPanelEnviarDatosForero.setLayout(subPanelEnviarDatosForeroLayout);
        subPanelEnviarDatosForeroLayout.setHorizontalGroup(
            subPanelEnviarDatosForeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelEnviarDatosForeroLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelEnviarDatosForeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarForero)
                    .addComponent(lblEnviarDatosForero)
                    .addComponent(btnAtrasPanelEnviarDatosForero)
                    .addComponent(lblForeroSeleccionadoParaEnviar)
                    .addComponent(comboVerForeroEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRutaGuardarForero, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(870, Short.MAX_VALUE))
        );
        subPanelEnviarDatosForeroLayout.setVerticalGroup(
            subPanelEnviarDatosForeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelEnviarDatosForeroLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelEnviarDatosForero)
                .addGap(27, 27, 27)
                .addComponent(lblForeroSeleccionadoParaEnviar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboVerForeroEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblEnviarDatosForero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRutaGuardarForero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGuardarForero)
                .addContainerGap(245, Short.MAX_VALUE))
        );

        subPanelModeradoresSanciones.setPreferredSize(new java.awt.Dimension(1221, 514));

        btnAtrasPanelModeradoresSanciones.setText("Atrás");
        btnAtrasPanelModeradoresSanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPanelModeradoresSancionesActionPerformed(evt);
            }
        });

        lblTitModeradoresSanciones.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitModeradoresSanciones.setText("Moderadores");

        listaModeradoresSanciones.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaModeradoresSancionesValueChanged(evt);
            }
        });
        jScrollPane11.setViewportView(listaModeradoresSanciones);

        btnOrdenarModeradoresSanciones.setText("Ordenar");
        btnOrdenarModeradoresSanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarModeradoresSancionesActionPerformed(evt);
            }
        });

        jScrollPane12.setPreferredSize(new java.awt.Dimension(258, 130));

        listaSancionadosPorModerador.setPreferredSize(new java.awt.Dimension(258, 80));
        jScrollPane12.setViewportView(listaSancionadosPorModerador);

        lblTituloSancionadosPorModerador.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTituloSancionadosPorModerador.setText("Foreros Sancionados");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText(">");

        javax.swing.GroupLayout subPanelModeradoresSancionesLayout = new javax.swing.GroupLayout(subPanelModeradoresSanciones);
        subPanelModeradoresSanciones.setLayout(subPanelModeradoresSancionesLayout);
        subPanelModeradoresSancionesLayout.setHorizontalGroup(
            subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelModeradoresSancionesLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAtrasPanelModeradoresSanciones)
                    .addGroup(subPanelModeradoresSancionesLayout.createSequentialGroup()
                        .addGroup(subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(subPanelModeradoresSancionesLayout.createSequentialGroup()
                                .addComponent(lblTitModeradoresSanciones)
                                .addGap(124, 124, 124)
                                .addComponent(btnOrdenarModeradoresSanciones))
                            .addComponent(jScrollPane11))
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTituloSancionadosPorModerador))))
                .addContainerGap(420, Short.MAX_VALUE))
        );
        subPanelModeradoresSancionesLayout.setVerticalGroup(
            subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPanelModeradoresSancionesLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnAtrasPanelModeradoresSanciones)
                .addGap(35, 35, 35)
                .addGroup(subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitModeradoresSanciones)
                    .addGroup(subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOrdenarModeradoresSanciones)
                        .addComponent(lblTituloSancionadosPorModerador)))
                .addGroup(subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subPanelModeradoresSancionesLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(subPanelModeradoresSancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subPanelModeradoresSancionesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(187, 187, 187))))
        );

        javax.swing.GroupLayout PanelPantallaPrincipalLayout = new javax.swing.GroupLayout(PanelPantallaPrincipal);
        PanelPantallaPrincipal.setLayout(PanelPantallaPrincipalLayout);
        PanelPantallaPrincipalLayout.setHorizontalGroup(
            PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(subPanelHilos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnCerrarSesion)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subPanelCrearHilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelMensajes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelCrearMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subPanelSancionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelQuitarSancion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subPanelCrearCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelEliminarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelNombrarModerador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelDestituirModerador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subPanelVerCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelVerForeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelEnviarDatosForero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addComponent(subPanelModeradoresSanciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        PanelPantallaPrincipalLayout.setVerticalGroup(
            PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnCerrarSesion)
                .addGap(28, 28, 28)
                .addComponent(subPanelHilos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addGap(65, 65, 65)
                    .addComponent(subPanelCrearHilo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(21, 21, 21)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelMensajes, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addGap(86, 86, 86)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelCrearMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(subPanelSancionar, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelQuitarSancion, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(subPanelCrearCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(66, 66, 66)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelEliminarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelNombrarModerador, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelDestituirModerador, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(subPanelVerCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelVerForeros, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(subPanelEnviarDatosForero, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGap(81, 81, 81)))
            .addGroup(PanelPantallaPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPantallaPrincipalLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(subPanelModeradoresSanciones, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addGap(76, 76, 76)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PanelAcceder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(PanelPantallaPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PanelCrearNuevoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PanelAcceder, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(PanelPantallaPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PanelCrearNuevoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed
        PanelLogin.setVisible(false);
        PanelAcceder.setVisible(true);
    }//GEN-LAST:event_btnIniciarSesionActionPerformed

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        String user = userNameTxt.getText();
        String pass = passTxt.getText();
        boolean exitoLogin = logear(user, pass);
        
        if(exitoLogin){
            PanelLogin.setVisible(false);
            PanelAcceder.setVisible(false);
            PanelPantallaPrincipal.setVisible(false);
            lblErrorLogin.setVisible(false);
            mostrarPantallaPrincipal();
            userNameTxt.setText("");
            passTxt.setText("");
        } else{
            lblErrorLogin.setVisible(true);
        }
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnVolverLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverLoginActionPerformed
        mostrarPantallaInicial();
    }//GEN-LAST:event_btnVolverLoginActionPerformed

    private void comboCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriasActionPerformed
        String categoria = String.valueOf(comboCategorias.getSelectedItem());
        
        for(Categoria c: foro.getCategorias()) {
            if(c.getNombre().equalsIgnoreCase(categoria)) {
                this.catSeleccionada = c;
            }
        }
        
        rellenarHilos(categoria);
    }//GEN-LAST:event_comboCategoriasActionPerformed

    private void btnCrearHiloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearHiloActionPerformed
        subPanelHilos.setVisible(false);
        subPanelCrearHilo.setVisible(true);
        
        rellenarCategoriasNuevoHilo();
    }//GEN-LAST:event_btnCrearHiloActionPerformed

    private void btnAtrasCrearNuevoHiloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasCrearNuevoHiloActionPerformed
        subPanelCrearHilo.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasCrearNuevoHiloActionPerformed

    private void btnCrearNuevoHiloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearNuevoHiloActionPerformed
        String categoria = String.valueOf(comboCategoriasNuevoHilo.getSelectedItem());
        String nombreHilo = txtNombreHilo.getText();
        String titMensaje = txtTituloMensajeInicial.getText();
        String cuerpoMensaje = txtCuerpoMensajeInicial.getText();
        
        Categoria cat = null;
        
        for(Categoria c: foro.getCategorias()) {
            if(categoria.equalsIgnoreCase(c.getNombre())) {
                cat = c;
            }
        }
        
        if(cat != null) {
            Hilo nuevoHilo = new Hilo(nombreHilo, new Mensaje(this.usuario.getNick(), 
                    titMensaje, cuerpoMensaje), this.usuario.getNick());
            cat.crearHilo(nuevoHilo);
        }
        
        txtNombreHilo.setText("");
        txtTituloMensajeInicial.setText("");
        txtCuerpoMensajeInicial.setText("");
        
        rellenarHilos(categoria);
        
        subPanelCrearHilo.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnCrearNuevoHiloActionPerformed

    private void txtTituloMensajeInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloMensajeInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloMensajeInicialActionPerformed

    private void listaHilosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaHilosValueChanged
        String hSeleccionado = listaHilos.getSelectedValue();
        
        for(Hilo h: this.catSeleccionada.getHilos()) {
            if(h.getTitulo().equalsIgnoreCase(hSeleccionado)) {
                this.hiloSeleccionado = h;
            }
        }
    }//GEN-LAST:event_listaHilosValueChanged

    private void btnEliminarHiloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarHiloActionPerformed
        if(this.usuario.getClass().getSimpleName().equalsIgnoreCase("forero")) {
            eliminarHiloForero();
        } else {
            eliminarHilo();
        }
    }//GEN-LAST:event_btnEliminarHiloActionPerformed

    private void listaHilosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaHilosMouseClicked
        if (evt.getClickCount() == 2) {
            // Double-click detected
            int index = listaHilos.locationToIndex(evt.getPoint());
            subPanelMensajes.setVisible(true);
            if(this.usuario.getSancionado()){
                btnPublicarMensaje.setVisible(false);
            }
            subPanelHilos.setVisible(false);
            subPanelCrearHilo.setVisible(false);
            
            lblTitMensajesHilo.setText("Mensajes del hilo: " + this.hiloSeleccionado.getTitulo() 
                    + " (" + this.catSeleccionada.getNombre() + ") ");
            
            mostrarMensajesHilo();
        }
    }//GEN-LAST:event_listaHilosMouseClicked

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        mostrarPantallaInicial();
        PanelPantallaPrincipal.setVisible(false);
        this.usuario = null;
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnAtrasPanelMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelMensajeActionPerformed
        mostrarPantallaPrincipal();
        subPanelMensajes.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelMensajeActionPerformed

    private void btnAtrasPanelCrearMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelCrearMensajeActionPerformed
        subPanelCrearMensaje.setVisible(false);
        subPanelMensajes.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelCrearMensajeActionPerformed

    private void btnPublicarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPublicarMensajeActionPerformed
        subPanelMensajes.setVisible(false);
        subPanelCrearMensaje.setVisible(true);
    }//GEN-LAST:event_btnPublicarMensajeActionPerformed

    private void btnPublicarNuevoMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPublicarNuevoMensajeActionPerformed
        String titMensaje = txtTituloNuevoMensaje.getText();
        String cuerpoMensaje = txtCuerpoNuevoMensaje.getText();
        
        Mensaje mensaje = new Mensaje(this.usuario.getNick(), titMensaje, cuerpoMensaje);
        
        this.hiloSeleccionado.getMensajes().add(mensaje);
        this.usuario.incrementarNMensajesTotal();
        mostrarMensajesHilo();
        
        txtTituloNuevoMensaje.setText("");
        txtCuerpoNuevoMensaje.setText("");
        
        subPanelCrearMensaje.setVisible(false);
        subPanelMensajes.setVisible(true);
    }//GEN-LAST:event_btnPublicarNuevoMensajeActionPerformed

    private void btnEliminarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMensajeActionPerformed
        if(!this.usuario.getClass().getSimpleName().equalsIgnoreCase("forero")) {
            this.hiloSeleccionado.getMensajes().remove(this.mensajeSeleccionado);
            mostrarMensajesHilo();
        }
    }//GEN-LAST:event_btnEliminarMensajeActionPerformed

    private void listaMensajesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaMensajesValueChanged
        
        String mSeleccionado = listaMensajes.getSelectedValue();
            
        if(mSeleccionado != null) {
            String partes[] = mSeleccionado.split("\\-");
            String parte2 = partes[1].replaceAll(" ", "");
            parte2 = parte2.split("\\:")[1];
            mSeleccionado = parte2;
            if(mSeleccionado != null) {
                for(Mensaje m: this.hiloSeleccionado.getMensajes()) {
                    if(m.getTitulo().equalsIgnoreCase(mSeleccionado)) {
                        this.mensajeSeleccionado = m;
                    }
                }
            }
        }
    }//GEN-LAST:event_listaMensajesValueChanged

    private void comboMenuEspecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMenuEspecialActionPerformed
        String elegido = String.valueOf(comboMenuEspecial.getSelectedItem());
        
        switch(elegido.toLowerCase()){
            case "sancionar":
                subPanelHilos.setVisible(false);
                subPanelSancionar.setVisible(true);
                rellenarForeros();
                break;
            case "quitar sancion":
                subPanelHilos.setVisible(false);
                subPanelQuitarSancion.setVisible(true);
                rellenarForerosSancionados();
                break;
            case "crear categoria":
                subPanelHilos.setVisible(false);
                subPanelCrearCategoria.setVisible(true);
                rellenarCategorias();
                break;
            case "eliminar categoria":
                subPanelHilos.setVisible(false);
                subPanelEliminarCategoria.setVisible(true);
                rellenarListaCategorias();
                break;
            case "nombrar moderador":
                subPanelHilos.setVisible(false);
                subPanelNombrarModerador.setVisible(true);
                rellenarListaForerosNoModeradores();
                break;
            case "destituir moderador":
                subPanelHilos.setVisible(false);
                subPanelDestituirModerador.setVisible(true);
                rellenarListaModeradores();
                break;
            case "ver moderadores":
                subPanelHilos.setVisible(false);
                subPanelModeradoresSanciones.setVisible(true);
                rellenarListaModeradoresSancionadoresOrdenada(false);
                break;
            case "ver categorias":
                subPanelHilos.setVisible(false);
                subPanelVerCategorias.setVisible(true);
                rellenarCategoriasOrdenadas("mensajes");
                break;
            case "ver foreros":
                subPanelHilos.setVisible(false);
                subPanelVerForeros.setVisible(true);
                rellenarComboForeros();
                break;
            case "enviar datos forero":
                subPanelHilos.setVisible(false);
                subPanelEnviarDatosForero.setVisible(true);
                rellenarComboForerosParaEnviar();
                break;
        }
    }//GEN-LAST:event_comboMenuEspecialActionPerformed

    private void btnAtrasPanelSancionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelSancionarActionPerformed
        subPanelSancionar.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelSancionarActionPerformed

    private void listaForerosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaForerosValueChanged
        String fSeleccionado = listaForeros.getSelectedValue();
        
        for(Forero f: foro.getForeros()) {
            if(f.getNick().equalsIgnoreCase(fSeleccionado)) {
                this.foreroSeleccionado = f;
            }
        }
    }//GEN-LAST:event_listaForerosValueChanged

    private void btnSancionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSancionarActionPerformed
        foro.sancionar(this.foreroSeleccionado, (Moderador)this.usuario);
        rellenarForeros();
    }//GEN-LAST:event_btnSancionarActionPerformed

    private void btnAtrasPanelQuitarSancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelQuitarSancionActionPerformed
        subPanelQuitarSancion.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelQuitarSancionActionPerformed

    private void listaForerosSancionadosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaForerosSancionadosValueChanged
        String fSeleccionado = listaForerosSancionados.getSelectedValue();
        
        for(Forero f: foro.getForeros()) {
            if(f.getNick().equalsIgnoreCase(fSeleccionado)) {
                this.foreroSeleccionado = f;
            }
        }
    }//GEN-LAST:event_listaForerosSancionadosValueChanged

    private void btnQuitarSancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarSancionActionPerformed
        foro.levantarSancion(this.foreroSeleccionado);
        rellenarForerosSancionados();
    }//GEN-LAST:event_btnQuitarSancionActionPerformed

    private void btnAtrasPanelCrearCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelCrearCategoriaActionPerformed
        subPanelCrearCategoria.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelCrearCategoriaActionPerformed

    private void btnCrearNuevaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearNuevaCategoriaActionPerformed
        String tituloCategoria = txtTituloNuevaCategoria.getText();
        Categoria categoria = new Categoria(tituloCategoria);      
        foro.crearCategoria(categoria);
        subPanelCrearCategoria.setVisible(false);
        subPanelHilos.setVisible(true);
        rellenarCategorias();
    }//GEN-LAST:event_btnCrearNuevaCategoriaActionPerformed

    private void btnAtrasPanelEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelEliminarCategoriaActionPerformed
        subPanelEliminarCategoria.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelEliminarCategoriaActionPerformed

    private void listaCategoriasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaCategoriasValueChanged
        String cSeleccionada = listaCategorias.getSelectedValue();
        
        for(Categoria c: foro.getCategorias()) {
            if(c.getNombre().equalsIgnoreCase(cSeleccionada)) {
                this.catSeleccionada = c;
            }
        }
    }//GEN-LAST:event_listaCategoriasValueChanged

    private void btnEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCategoriaActionPerformed
        foro.eliminarCategoria(this.catSeleccionada.getNombre());
        subPanelEliminarCategoria.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnEliminarCategoriaActionPerformed

    private void btnAtrasPanelNombrarModeradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelNombrarModeradorActionPerformed
        subPanelNombrarModerador.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelNombrarModeradorActionPerformed

    private void listaForerosNoModeradoresValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaForerosNoModeradoresValueChanged
        String fSeleccionado = listaForerosNoModeradores.getSelectedValue();
        
        for(Forero f: foro.getForeros()) {
            if(f.getNick().equalsIgnoreCase(fSeleccionado)) {
                this.foreroSeleccionado = f;
            }
        }
    }//GEN-LAST:event_listaForerosNoModeradoresValueChanged

    private void btnNombrarModeradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNombrarModeradorActionPerformed
        foro.nombrarModerador(this.foreroSeleccionado);
        subPanelNombrarModerador.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnNombrarModeradorActionPerformed

    private void btnAtrasPanelDestituirModeradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelDestituirModeradorActionPerformed
        subPanelDestituirModerador.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelDestituirModeradorActionPerformed

    private void listaModeradoresValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaModeradoresValueChanged
        String mSeleccionado = listaModeradores.getSelectedValue();
        
        for(Moderador m: foro.getModeradores()) {
            if(m.getNick().equalsIgnoreCase(mSeleccionado)) {
                this.moderadorSeleccionado = m;
            }
        }
    }//GEN-LAST:event_listaModeradoresValueChanged

    private void btnDestituirModeradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDestituirModeradorActionPerformed
        foro.destituirModerador(this.moderadorSeleccionado);
        subPanelDestituirModerador.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnDestituirModeradorActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            Repositorio.serializarForo(this.foro, "./miniforo.bin");
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnCrearNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearNuevoUsuarioActionPerformed
        String user = userNameTxtNuevoUsuario.getText();
        String pass = passTxtNuevoUsuario.getText();
        boolean existeUser = false;
        
        for(int i = 0; i < this.foro.getForeros().size() && !existeUser; i++) {
            existeUser = this.foro.getForeros().get(i).getNick().equals(user);
        }
        
        for(int i = 0; i < this.foro.getModeradores().size() && !existeUser; i++) {
            existeUser = this.foro.getModeradores().get(i).getNick().equals(user);
        }
        
        for(int i = 0; i < this.foro.getAdministradores().size() && !existeUser; i++) {
            existeUser = this.foro.getAdministradores().get(i).getNick().equals(user);
        }
        
        if(existeUser) {
            lblErrorCrearNuevoUsuario.setVisible(true);
        } else {
            Forero f = new Forero(user, pass);
            lblErrorCrearNuevoUsuario.setVisible(false);
            this.foro.anadirForero(f);
        }
        
        
        boolean exitoLogin = logear(user, pass);
        
        if(exitoLogin){
            PanelLogin.setVisible(false);
            PanelAcceder.setVisible(false);
            PanelCrearNuevoUsuario.setVisible(false);
            PanelPantallaPrincipal.setVisible(false);
            subPanelHilos.setVisible(true);
            lblErrorCrearNuevoUsuario.setVisible(false);
            mostrarPantallaPrincipal();
            userNameTxtNuevoUsuario.setText("");
            passTxtNuevoUsuario.setText("");
        }
    }//GEN-LAST:event_btnCrearNuevoUsuarioActionPerformed

    private void btnVolverCrearNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverCrearNuevoUsuarioActionPerformed
        PanelCrearNuevoUsuario.setVisible(false);
        PanelLogin.setVisible(true);
    }//GEN-LAST:event_btnVolverCrearNuevoUsuarioActionPerformed

    private void btnNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoUsuarioActionPerformed
        PanelAcceder.setVisible(false);
        PanelLogin.setVisible(false);
        PanelCrearNuevoUsuario.setVisible(true);
    }//GEN-LAST:event_btnNuevoUsuarioActionPerformed

    private void btnAtrasPanelVerCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelVerCategoriasActionPerformed
        subPanelVerCategorias.setVisible(false);{
        subPanelHilos.setVisible(true);
    }
    }//GEN-LAST:event_btnAtrasPanelVerCategoriasActionPerformed

    private void listaVerCategoriasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaVerCategoriasValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_listaVerCategoriasValueChanged

    private void btnOrdenarCatPorMensajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarCatPorMensajesActionPerformed
        rellenarCategoriasOrdenadas("mensajes");
    }//GEN-LAST:event_btnOrdenarCatPorMensajesActionPerformed

    private void btnOrdenarCatPorPalabrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarCatPorPalabrasActionPerformed
        rellenarCategoriasOrdenadas("palabras");
    }//GEN-LAST:event_btnOrdenarCatPorPalabrasActionPerformed

    private void btnAtrasPanelVerForerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelVerForerosActionPerformed
        subPanelVerForeros.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelVerForerosActionPerformed

    private void listaVerHilosForeroValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaVerHilosForeroValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_listaVerHilosForeroValueChanged

    private void comboVerForeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVerForeroActionPerformed
        String elegido = String.valueOf(comboVerForero.getSelectedItem());
        boolean encontrado = false;
        
        for(int i = 0; i < this.foro.getForeros().size() && !encontrado; i++) {
            if(this.foro.getForeros().get(i).getNick().equalsIgnoreCase(elegido)) {
                encontrado = true;
                Forero f = this.foro.getForeros().get(i);
                if(encontrado) {
                    rellenarDatosVerForero(f);
                }
            }
        }
        
        for(int i = 0; i < this.foro.getModeradores().size() && !encontrado; i++) {
            if(this.foro.getModeradores().get(i).getNick().equalsIgnoreCase(elegido)) {
                encontrado = true;
                Moderador f = this.foro.getModeradores().get(i);
                if(encontrado) {
                    rellenarDatosVerForero(f);
                }
            }
        }
        
        for(int i = 0; i < this.foro.getAdministradores().size() && !encontrado; i++) {
            if(this.foro.getAdministradores().get(i).getNick().equalsIgnoreCase(elegido)) {
                encontrado = true;
                Administrador f = this.foro.getAdministradores().get(i);
                if(encontrado) {
                    rellenarDatosVerForero(f);
                }
            }
        }
    }//GEN-LAST:event_comboVerForeroActionPerformed

    private void comboVerForeroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboVerForeroItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboVerForeroItemStateChanged

    private void btnAtrasPanelEnviarDatosForeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelEnviarDatosForeroActionPerformed
        subPanelHilos.setVisible(false);
        subPanelEnviarDatosForero.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelEnviarDatosForeroActionPerformed

    private void comboVerForeroEnviarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboVerForeroEnviarItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboVerForeroEnviarItemStateChanged

    private void comboVerForeroEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVerForeroEnviarActionPerformed
        
    }//GEN-LAST:event_comboVerForeroEnviarActionPerformed

    private void btnGuardarForeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarForeroActionPerformed
        String elegido = String.valueOf(comboVerForeroEnviar.getSelectedItem());
        String ruta = txtRutaGuardarForero.getText();
        boolean encontrado = false;
        
        for(int i = 0; i < this.foro.getForeros().size() && !encontrado; i++) {
            if(this.foro.getForeros().get(i).getNick().equalsIgnoreCase(elegido)) {
                encontrado = true;
                Forero f = this.foro.getForeros().get(i);
                if(encontrado) {
                    try {
                        Repositorio.guardarFicheroForero(this.foro, f, ruta);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        for(int i = 0; i < this.foro.getModeradores().size() && !encontrado; i++) {
            if(this.foro.getModeradores().get(i).getNick().equalsIgnoreCase(elegido)) {
                encontrado = true;
                Moderador f = this.foro.getModeradores().get(i);
                if(encontrado) {
                    try {
                        Repositorio.guardarFicheroForero(this.foro, f, ruta);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        for(int i = 0; i < this.foro.getAdministradores().size() && !encontrado; i++) {
            if(this.foro.getAdministradores().get(i).getNick().equalsIgnoreCase(elegido)) {
                encontrado = true;
                Administrador f = this.foro.getAdministradores().get(i);
                if(encontrado) {
                    try {
                        Repositorio.guardarFicheroForero(this.foro, f, ruta);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        subPanelEnviarDatosForero.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnGuardarForeroActionPerformed

    private void btnAtrasPanelModeradoresSancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPanelModeradoresSancionesActionPerformed
        subPanelModeradoresSanciones.setVisible(false);
        subPanelHilos.setVisible(true);
    }//GEN-LAST:event_btnAtrasPanelModeradoresSancionesActionPerformed

    private void listaModeradoresSancionesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaModeradoresSancionesValueChanged
        String modSeleccionado = listaModeradoresSanciones.getSelectedValue();
        
        if(modSeleccionado != null) {
            //Vemos si viene solo el nombre del moderador en el string, o algo más
            if(modSeleccionado.split(" ").length > 1) {
                rellenarListaForerosSancionados(modSeleccionado.split(" ")[0]);
            } else {
                rellenarListaForerosSancionados(modSeleccionado);
            }
        }
    }//GEN-LAST:event_listaModeradoresSancionesValueChanged

    private void btnOrdenarModeradoresSancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarModeradoresSancionesActionPerformed
        rellenarListaModeradoresSancionadoresOrdenada(true);
    }//GEN-LAST:event_btnOrdenarModeradoresSancionesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAcceder;
    private javax.swing.JPanel PanelCrearNuevoUsuario;
    private javax.swing.JPanel PanelLogin;
    private javax.swing.JPanel PanelPantallaPrincipal;
    private javax.swing.JLabel TituloLogin;
    private javax.swing.JButton btnAtrasCrearNuevoHilo;
    private javax.swing.JButton btnAtrasPanelCrearCategoria;
    private javax.swing.JButton btnAtrasPanelCrearMensaje;
    private javax.swing.JButton btnAtrasPanelDestituirModerador;
    private javax.swing.JButton btnAtrasPanelEliminarCategoria;
    private javax.swing.JButton btnAtrasPanelEnviarDatosForero;
    private javax.swing.JButton btnAtrasPanelMensaje;
    private javax.swing.JButton btnAtrasPanelModeradoresSanciones;
    private javax.swing.JButton btnAtrasPanelNombrarModerador;
    private javax.swing.JButton btnAtrasPanelQuitarSancion;
    private javax.swing.JButton btnAtrasPanelSancionar;
    private javax.swing.JButton btnAtrasPanelVerCategorias;
    private javax.swing.JButton btnAtrasPanelVerForeros;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnCrearHilo;
    private javax.swing.JButton btnCrearNuevaCategoria;
    private javax.swing.JButton btnCrearNuevoHilo;
    private javax.swing.JButton btnCrearNuevoUsuario;
    private javax.swing.JButton btnDestituirModerador;
    private javax.swing.JButton btnEliminarCategoria;
    private javax.swing.JButton btnEliminarHilo;
    private javax.swing.JButton btnEliminarMensaje;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnGuardarForero;
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnNombrarModerador;
    private javax.swing.JButton btnNuevoUsuario;
    private javax.swing.JButton btnOrdenarCatPorMensajes;
    private javax.swing.JButton btnOrdenarCatPorPalabras;
    private javax.swing.JButton btnOrdenarModeradoresSanciones;
    private javax.swing.JButton btnPublicarMensaje;
    private javax.swing.JButton btnPublicarNuevoMensaje;
    private javax.swing.JButton btnQuitarSancion;
    private javax.swing.JButton btnSancionar;
    private javax.swing.JButton btnVolverCrearNuevoUsuario;
    private javax.swing.JButton btnVolverLogin;
    private javax.swing.JComboBox<String> comboCategorias;
    private javax.swing.JComboBox<String> comboCategoriasNuevoHilo;
    private javax.swing.JComboBox<String> comboMenuEspecial;
    private javax.swing.JComboBox<String> comboVerForero;
    private javax.swing.JComboBox<String> comboVerForeroEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblActualmenteSancionado;
    private javax.swing.JLabel lblActualmenteSancionadoForero;
    private javax.swing.JLabel lblCatMasPublicaForero;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCategoriaMasPublica;
    private javax.swing.JLabel lblCategoriaNuevoHilo;
    private javax.swing.JLabel lblCrearCategoria;
    private javax.swing.JLabel lblCrearHilo;
    private javax.swing.JLabel lblCrearMensaje;
    private javax.swing.JLabel lblCrearNuevoUsuario;
    private javax.swing.JLabel lblCuerpoMensaje;
    private javax.swing.JLabel lblEnviarDatosForero;
    private javax.swing.JLabel lblErrorBorrarHilo;
    private javax.swing.JLabel lblErrorCrearNuevoUsuario;
    private javax.swing.JLabel lblErrorLogin;
    private javax.swing.JLabel lblForeroSeleccionado;
    private javax.swing.JLabel lblForeroSeleccionadoParaEnviar;
    private javax.swing.JLabel lblHilos;
    private javax.swing.JLabel lblListaCategorias;
    private javax.swing.JLabel lblListaForeros;
    private javax.swing.JLabel lblListaForerosNoModeradores;
    private javax.swing.JLabel lblListaForerosSancionados;
    private javax.swing.JLabel lblListaModeradores;
    private javax.swing.JLabel lblMensajeInicial;
    private javax.swing.JLabel lblMenuEspecial;
    private javax.swing.JLabel lblMenuUsuario;
    private javax.swing.JLabel lblNombreHilo;
    private javax.swing.JLabel lblNumMensajesForero;
    private javax.swing.JLabel lblNumeroMensajesForero;
    private javax.swing.JLabel lblTitMensajesHilo;
    private javax.swing.JLabel lblTitModeradoresSanciones;
    private javax.swing.JLabel lblTitVerCategorias;
    private javax.swing.JLabel lblTituloAcceder;
    private javax.swing.JLabel lblTituloMsgInicial;
    private javax.swing.JLabel lblTituloNuevaCategoria;
    private javax.swing.JLabel lblTituloNuevoMensaje;
    private javax.swing.JLabel lblTituloSancionadosPorModerador;
    private javax.swing.JLabel lblVecesSancionado;
    private javax.swing.JLabel lblVecesSancionadoForero;
    private javax.swing.JList<String> listaCategorias;
    private javax.swing.JList<String> listaForeros;
    private javax.swing.JList<String> listaForerosNoModeradores;
    private javax.swing.JList<String> listaForerosSancionados;
    private javax.swing.JList<String> listaHilos;
    private javax.swing.JList<String> listaMensajes;
    private javax.swing.JList<String> listaModeradores;
    private javax.swing.JList<String> listaModeradoresSanciones;
    private javax.swing.JList<String> listaSancionadosPorModerador;
    private javax.swing.JList<String> listaVerCategorias;
    private javax.swing.JList<String> listaVerHilosForero;
    private javax.swing.JLabel pass;
    private javax.swing.JLabel passNuevoUsuario;
    private javax.swing.JPasswordField passTxt;
    private javax.swing.JPasswordField passTxtNuevoUsuario;
    private javax.swing.JPanel subPanelCrearCategoria;
    private javax.swing.JPanel subPanelCrearHilo;
    private javax.swing.JPanel subPanelCrearMensaje;
    private javax.swing.JPanel subPanelDestituirModerador;
    private javax.swing.JPanel subPanelEliminarCategoria;
    private javax.swing.JPanel subPanelEnviarDatosForero;
    private javax.swing.JPanel subPanelHilos;
    private javax.swing.JPanel subPanelMensajes;
    private javax.swing.JPanel subPanelModeradoresSanciones;
    private javax.swing.JPanel subPanelNombrarModerador;
    private javax.swing.JPanel subPanelQuitarSancion;
    private javax.swing.JPanel subPanelSancionar;
    private javax.swing.JPanel subPanelVerCategorias;
    private javax.swing.JPanel subPanelVerForeros;
    private javax.swing.JTextArea txtCuerpoMensajeInicial;
    private javax.swing.JTextArea txtCuerpoNuevoMensaje;
    private javax.swing.JScrollPane txtMensajeInicial;
    private javax.swing.JTextField txtNombreHilo;
    private javax.swing.JTextField txtRutaGuardarForero;
    private javax.swing.JTextField txtTituloMensajeInicial;
    private javax.swing.JTextField txtTituloNuevaCategoria;
    private javax.swing.JTextField txtTituloNuevoMensaje;
    private javax.swing.JLabel userName;
    private javax.swing.JLabel userNameNuevoUsuario;
    private javax.swing.JTextField userNameTxt;
    private javax.swing.JTextField userNameTxtNuevoUsuario;
    // End of variables declaration//GEN-END:variables
}
