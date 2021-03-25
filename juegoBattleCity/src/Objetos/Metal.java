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
import java.awt.Toolkit;

/**
 *
 * @author josemurillo
 */
public class Metal extends Muro{
    public Metal(int x, int y, Controlador c){
        super();
        this.x = x;
        this.y = y;
        this.ancho = 36;
        this.largo = 37;
        this.tk = Toolkit.getDefaultToolkit();
       
        this.imagenesMuro = new Image[] {
            tk.getImage(Metal.class.getResource("Imagenes/metalWall.gif"))};
    }
    
    public void colocar(Graphics g){
        g.drawImage(imagenesMuro[0], this.x, this.y, null);
    }
    
    public Rectangle getRect(){
        return new Rectangle (this.x, this.y, this.ancho, this.largo);
    }
    
}
