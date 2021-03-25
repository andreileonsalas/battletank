/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;
import Controlador.Controlador;
import java.awt.*;

/**
 *
 * @author josemurillo
 */
public class Ladrillo extends Muro {
    private Controlador c;
    public Ladrillo(int x, int y, Controlador c){
        super();
        this.x = x;
        this.y = y;
        this.ancho = 22;
        this.largo = 21;
        this.c = c;
        this.tk = Toolkit.getDefaultToolkit();
        this.imagenesMuro = new Image[]{ 
            tk.getImage(Ladrillo.class.getResource("Imagenes/commonWall.gif"))
        };
    }
    
    @Override
    public void colocar(Graphics g){
        g.drawImage(imagenesMuro[0], this.x, this.y, null);
    }
}
