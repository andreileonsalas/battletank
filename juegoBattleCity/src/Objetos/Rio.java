/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Controlador.Controlador;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author josemurillo
 */
public class Rio extends Objeto implements Inmovible{
    private static Image[] rioImagenes = null;
    Controlador c;
    
    public Rio(int x, int y, Controlador pC){
        super();
        this.ancho = 55;
        this.largo = 154;
        this.x = x;
        this.y = y;
        this.c = pC;
        rioImagenes = new Image[]{tk.getImage(Rio.class.getResource("Imagenes/river.jpg"))
        };
      
    }


    @Override
    public void colocar(Graphics g){
        g.drawImage(rioImagenes[0], this.x, this.y, null);
    }
    
    
    
    public int getAncho(){
        return this.ancho;
    }
    
    public int getLargo(){
        return this.largo;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void setY(int pY){
        this.y = pY;
    }
    
    @Override
    public Rectangle getRect(){
        return new Rectangle(this.x, this.y, this.ancho, this.largo);
    }
    
}
