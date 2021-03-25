/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Controlador.Controlador;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.awt.*;

/**
 *
 * @author josemurillo
 */
public class Bala extends Objeto implements Movible {
    public int velocidad = 12;
    //se define el ancho y largo de TODAS las balas
    public static int ancho = 10;
    public static int largo = 10;
    private boolean buena;//se utiliza para saber si es una bala de un aliado o de un enemigo
    private Controlador c;
    String direccion;
    private static Image[] balaImagenes = new Image[] {
				tk.getImage(Bala.class.getClassLoader().getResource("Imagenes/bulletL.gif")),
				tk.getImage(Bala.class.getClassLoader().getResource("Imagenes/bulletU.gif")),
				tk.getImage(Bala.class.getClassLoader().getResource("Imagenes/bulletR.gif")),
				tk.getImage(Bala.class.getClassLoader().getResource("Imagenes/bulletD.gif"))
                                };
    private static Map<String, Image> imgs;
        
    
    
    
    
    
    public Bala(int x, int y, boolean buena, String dir, Controlador c){
        this.x = x;
        this.y = y;
        this.direccion = dir;
        this.c = c;
        imgs = new HashMap<String, Image>();
        imgs.put("Izq", balaImagenes[0]); 
        imgs.put("Arr", balaImagenes[1]);
        imgs.put("Der", balaImagenes[2]);
        imgs.put("Aba", balaImagenes[3]);
        this.ancho=10;
        this.largo=10;
        this.buena=buena;
        
    }
    
        public Bala(int x, int y, boolean buena, String dir, Controlador c,int velocidad){
        this.x = x;
        this.y = y;
        this.direccion = dir;
        this.c = c;
        imgs = new HashMap<String, Image>();
        imgs.put("Izq", balaImagenes[0]); 
        imgs.put("Arr", balaImagenes[1]);
        imgs.put("Der", balaImagenes[2]);
        imgs.put("Aba", balaImagenes[3]);
        this.ancho=10;
        this.largo=10;
        this.buena=buena;
        this.velocidad=velocidad;
    }
    
    @Override
    public void mover(){
        switch (direccion){
            case "Izq":
                x -= velocidad;
                break;
            case "Arr":
                y -= velocidad;
                break;
            case "Der":
                x += velocidad;
                break;
            case "Aba":
                y += velocidad;
                break;
            case "":
                break;
        }
        if (x < 0 || y < 0 || x > Controlador.frameAncho
                || y > Controlador.frameLargo){
            this.vivo = false;
        }   
    }
    
    @Override
    public void colocar(Graphics g){
        if (!this.vivo) {
            c.balas.remove(this);
            return;
        }
        switch (direccion){
            case "Izq":
                g.drawImage(imgs.get("Izq"),x, y, null);
                break;
                
            case "Arr":
                g.drawImage(imgs.get("Arr"), x, y, null);
                break;
                
            case "Der":
                g.drawImage(imgs.get("Der"), x, y, null);
                break;
                
            case "Aba":
                g.drawImage(imgs.get("Aba"), x, y, null);
                break;
                
        }
        mover();
    }
    
    
    public boolean isVivo(){
        return vivo;
    }
    
    @Override
    public Rectangle getRect(){
        return new Rectangle(this.x, this.y, this.ancho, this.largo);
    }
    
    public boolean hitTanques(List<Enemigo> enemigos){
        
        //recorrer si la bala de un jugador le pego a algun enemigo
        for (int i = 0; i < enemigos.size(); i++){
               if (hitTanqueEnemigo(enemigos.get(i)))
               {
                return true;//para que desaparezca la bala, pero hittanquealiado se encarga de matar al enemigo
               }
        }
        return false;
    }
    
    public boolean hitTanqueEnemigo(Enemigo t){
        // si la bala esta vivo, hitbox vs enemigo , tanque esta vivo
        if (this.vivo && this.getRect().intersects(t.getRect()) && t.isVivo()){
            if (this.buena != t.esBueno()){//retorna malo si es aliando del enemigo
                t.setVida(t.getVida() - 50);
                if (t.getVida() <= 0){
                    t.setVida(0);
                    t.setVivo(false);
                }
                this.vivo = false;
                
            }
            
            return true;
        }
        return false;
    }
    
    public boolean hitTanqueAliado(Jugador t){//revisa si la bala de la computdora le pego a uno
        if (this.vivo && this.getRect().intersects(t.getRect()) && t.isVivo()){
            if (this.buena != t.esBueno()){//retorna bueno si es aliado
                t.setVida(t.getVida() - 50);
                if (t.getVida() <= 0){
                    t.setVida(0);
                    t.setVivo(false);
                }   
                this.vivo = false;
            }
            
            return true;
        }
        return false;
    }
        
    public boolean chocaLadrillo(Ladrillo w){
        if (this.vivo && this.getRect().intersects(w.getRect())){
            this.vivo = false;
            this.c.otros.remove(w);
            this.c.homeLadrillos.remove(w);
            return true;
        }
        return false;
    }
    
    public boolean chocaBala( Bala w){
        if (this.vivo && this.getRect().intersects(w.getRect())){
            this.vivo = false;
            this.c.balas.remove(w);
            return true;
        }
        return false;
    }

    public boolean chocaMetal (Metal w){
        if (this.vivo && this.getRect().intersects(w.getRect())){
            this.vivo = false;
            return true;
        }
        return false;
    }
    
    public boolean chocaAguila(){
        if (this.vivo && this.getRect().intersects(c.aguila.getRect())){
            this.vivo = false;
            this.c.aguila.setVivo(false);
            return true;
        }
        return false;
    }
}
