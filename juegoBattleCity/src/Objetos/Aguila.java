/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author josemurillo
 */
public class Aguila extends Objeto implements Inmovible{
    private static Image[] aguilaImagen = null;
    private Controlador c;
    
    public Aguila(int x, int y, Controlador c){
        super();
        this.x = x;
        this.y = y;
        this.c = c;
        this.tk = Toolkit.getDefaultToolkit();
        aguilaImagen = new Image[] {
            tk.getImage(Aguila.class.getResource("Imagenes/home.jpg")),
                
                };
        this.largo = 43;
        this.ancho = 43;
        
    }
    
    public void gameOver( Graphics g){//se encarga de terminar el juego o iniciar uno nuevo si muere el aguila
                Object[] options = { "Confirmar", "Salir" };
                int response = JOptionPane.showOptionDialog(null, "Desea volverlo a intentar?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
                if (response == 1) {
				System.out.println("break down");
				System.exit(0);
			} else {
				
                Controlador abc = new Controlador();


                }
                //system.exit
    }
    
    @Override
    public void colocar(Graphics g){
        if (vivo){
            g.drawImage(aguilaImagen[0], x, y, null);
            for (int i = 0; i < c.homeLadrillos.size(); i++){
                Ladrillo w = c.homeLadrillos.get(i);
                w.colocar(g);
            }
        }else{
            
            c.dispose();
            gameOver(g);
        }
        g.drawImage(aguilaImagen[0], x, y,null);
    }
    
    public boolean isVivo(){
        return this.vivo;
    }
    
    public void setVivo(boolean pVivo){
        this.vivo = pVivo;
    }
}
