/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objetos;

import java.awt.*;

/**
 *
 * @author josemurillo
 */
public abstract class Objeto {
    
    protected int x;
    protected int y;
    protected int ancho;
    protected int largo;
    protected int vida;
    protected boolean vivo;
    protected static Toolkit tk = Toolkit.getDefaultToolkit();
    
    public Objeto(){
        vivo = true;
        
    }
    
    public Rectangle getRect(){
        return new Rectangle(this.x, this.y, this.ancho, this.largo);
    }
    
}



