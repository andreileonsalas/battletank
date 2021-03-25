/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.awt.Image;
import java.awt.Toolkit;
import Controlador.Controlador;
import java.awt.Graphics;

/**
 *
 * @author josemurillo
 */
public class Arbol extends Objeto implements Inmovible{
    private static Image[] arbolImagenes = null;
    Controlador c;
    
    public Arbol(int x, int y, Controlador c){
        super();
        this.x = x;
        this.y = y;
        this.c = c;
        this.ancho = 30;
        this.largo = 30;
        this.tk = Toolkit.getDefaultToolkit();
        arbolImagenes = new Image[]  {tk.getImage(Arbol.class.getResource("Imagenes/tree.gif"))};
    }
    
    @Override
    public void colocar(Graphics g){
        g.drawImage(arbolImagenes[0], this.x, this.y, null);
    }

    

}
