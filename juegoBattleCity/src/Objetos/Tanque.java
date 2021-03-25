/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 *
 * @author josemurillo
 */
public abstract class Tanque extends Objeto implements Movible{
    protected boolean aliado;
    public int velocidad;
    protected Image ultimoestado =null;
    protected String ultimadireccion ="";
    protected int numJugador;
    protected int oldX,oldY;
    protected Image[] imagenes = {
                        tk.getImage(Tanque.class.getResource("Imagenes/tankD.gif")),
			tk.getImage(Tanque.class.getResource("Imagenes/tankU.gif")),
			tk.getImage(Tanque.class.getResource("Imagenes/tankL.gif")),
			tk.getImage(Tanque.class.getResource("Imagenes/tankR.gif")), 
                        tk.getImage(Tanque.class.getResource("Imagenes/j1aba.png")), 
			tk.getImage(Tanque.class.getResource("Imagenes/j1arr.png")), 
			tk.getImage(Tanque.class.getResource("Imagenes/j1izq.png")),
			tk.getImage(Tanque.class.getResource("Imagenes/j1der.png")),
			tk.getImage(Tanque.class.getResource("Imagenes/HtankD2.gif")),
			tk.getImage(Tanque.class.getResource("Imagenes/HtankU2.gif")),
			tk.getImage(Tanque.class.getResource("Imagenes/HtankL2.gif")),
			tk.getImage(Tanque.class.getResource("Imagenes/HtankR2.gif")),
			};
    
    public Tanque(){
        super();
    }
    
    @Override
    public void mover(){
    }
    
    public void cambiarViejaDir(){
    }
    
    public void decidirDireccion(){
    }
    
    public boolean esBueno(){
        return false;
    }
    
    public boolean isVivo(){
        return true;
    }
    
    public int getVida(){
        return 0;
    }
    
    public boolean chocaPared(Ladrillo w){
        if (this.vivo && this.getRect().intersects(w.getRect())){
            this.cambiarViejaDir();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean chocaPared(Metal w){
        if (this.vivo && this.getRect().intersects(w.getRect())){
            this.cambiarViejaDir();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean chocaRio(Rio r){
        if (this.vivo && this.getRect().intersects(r.getRect())){
            this.cambiarViejaDir();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean chocaAguila(Aguila a){
        if (this.vivo && this.getRect().intersects(a.getRect())){
            this.cambiarViejaDir();
            return true;
        }
        return false;
    }
    
    public boolean chocaTanque(java.util.List<Enemigo> enemigos){
        for (int i = 0; i < enemigos.size(); i++){
            Enemigo e = enemigos.get(i);
                if (this.vivo && e.isVivo()){
                   if (this.getRect().intersects(e.getRect())){
                       this.cambiarViejaDir();
                       e.cambiarViejaDir();
                       return true;
                   }
                }
        }
        return false;
    }
    
    
    
    public void setVida(int pVida){
    }
    
    public void setVivo(boolean vivo){
    }   
    
}
