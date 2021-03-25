/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;
import Controlador.Controlador;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;

/**
 *
 * @author josemurillo
 */
public class Jugador extends Tanque{
    private Controlador c;
    private String direccion = "Arr";
    private int podervelocidad=12;
    private boolean bL = false, bU = false, bR = false, bD = false ;
    
    
    
    
    public Jugador(int x, int y, boolean bueno, String direccion, Controlador pC, int player){
        super();
        this.x = x;
        this.oldX = x;
        this.oldY = y;
        this.y = y;
        this.aliado = bueno;
        this.numJugador = player;
        this.vida = 100;
        this.largo = 35;
        this.ancho = 35;
        this.velocidad = 6;
        this.tk = Toolkit.getDefaultToolkit();
        this.c = pC;
        this.direccion = direccion;
        this.ultimadireccion="Arr";//define cual fue la ultima direccion, se usa para ver a donde disparar
        //se ingresa ultimo estado para que segun el jugador, muestre el dibujo correspondiente
        if (numJugador == 1){
                 ultimoestado=imagenes[5];    
                 }else
                 ultimoestado=imagenes[9];
        
    }
    
    @Override
    public void colocar(Graphics g){
         if (!vivo){
             c.jugadores.remove(this);
             return;
          }
         switch (direccion){
             case "Aba":
                 if (numJugador == 1){
                 ultimoestado=imagenes[4];    
                 }else
                 ultimoestado=imagenes[8];
                 
                 ultimadireccion=direccion;
                 if (numJugador == 1){
                     g.drawImage(imagenes[4], x, y, null);
                 }else if (c.jugador2 && numJugador == 2){
                     g.drawImage(imagenes[8], x, y, null);
                 }
                 break;
            
             case "Arr":
                 if (numJugador == 1){
                 ultimoestado=imagenes[5];    
                 }else
                 ultimoestado=imagenes[9];
                 ultimadireccion=direccion;
                 if (numJugador == 1){
                     g.drawImage(imagenes[5], x, y, null);
                 }else if (c.jugador2 && numJugador == 2){
                     g.drawImage(imagenes[9], x, y, null);
                 }
                 break;
            
             case "Izq":
                 if (numJugador == 1){
                 ultimoestado=imagenes[6];    
                 }else
                 ultimoestado=imagenes[10];
                 ultimadireccion=direccion;
                 if (numJugador == 1){
                     g.drawImage(imagenes[6], x, y, null);
                 }else if (c.jugador2 && numJugador == 2){
                     g.drawImage(imagenes[10], x, y, null);
                 }
                 break;
                 
             case "Der":
                 if (numJugador == 1){
                 ultimoestado=imagenes[7];    
                 }else
                 ultimoestado=imagenes[11];
                 ultimadireccion=direccion;
                 if (numJugador == 1){
                     g.drawImage(imagenes[7], x, y, null);
                 }else if (c.jugador2 && numJugador == 2){
                     g.drawImage(imagenes[11], x , y, null);
                 }
                 
            case "":
                 g.drawImage(ultimoestado, x, y, null);
                 break;
         }
         
         mover();
     } 
    
      
    @Override
    public void mover(){
        this.oldX = x;
        this.oldY = y;
        switch (direccion) {  
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
        
        if (x < 0){
            x = 0;
        }
        
        if (y < 40){
            y = 40;
        }
        
        if (x + this.ancho > c.frameAncho){
            x = c.frameAncho - this.ancho;
        }
        
        if (y + this.largo > c.frameLargo){
            y = c.frameLargo - this.largo;
        }
    }
    
    @Override
    public void cambiarViejaDir(){
        x = this.oldX;
        y = this.oldY;
    }
    
    public void keyPressed(KeyEvent e){
        int tecla = e.getKeyCode();
        if (numJugador == 1){
            switch(tecla){
                case KeyEvent.VK_R://resetea el juego
                    c.dispose();
                    Controlador abc = new Controlador();
                    break;
                    
                case KeyEvent.VK_D:
                    bR = true;
                    break;
                
                case KeyEvent.VK_A:
                    bL = true;
                    break;
                case KeyEvent.VK_W:
                    bU = true;
                    break;
                case KeyEvent.VK_S:
                    bD = true;
                    break;
            }
        }
        
        if (numJugador == 2){
            switch (tecla){
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
            }
        }
        
        decidirDireccion();
        
    }
    
    @Override
    public void decidirDireccion(){
        if (!bL && !bU && bR && !bD){
            direccion = "Der";
        }else if (bL && !bU && !bR && !bD){
           direccion = "Izq";
 
        }else if (!bL && bU && !bR && !bD){
            direccion = "Arr";
        }else if (!bL && !bU && !bR && bD){
            direccion = "Aba";
        }else if (!bL && !bU && !bR && !bD){
            direccion = "";
        } 
    }
    
    public void keyReleased(KeyEvent e){
        int tecla = e.getKeyCode();
        if (numJugador == 1){
            switch (tecla){
                case KeyEvent.VK_F:
                    disparar(podervelocidad);
                    break;
                
                case KeyEvent.VK_D:
                    bR = false;
                    break;
                
                case KeyEvent.VK_A:
                    bL = false;
                    break;
                
                case KeyEvent.VK_W:
                    bU = false;
                    break;
                 
                case KeyEvent.VK_S:
                    bD = false;
                    break;
            }
        }
        
        if (numJugador == 2){
            switch (tecla){
                case KeyEvent.VK_ENTER:
                    disparar(podervelocidad);
                    break;
				
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
			
		case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
			
		case KeyEvent.VK_UP:
                    bU = false;
                    break;
			
		case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
            }
        }
        decidirDireccion();
    }
    
    public Bala disparar(){
        if (!vivo){
            return null;
        }
        int balaX = this.x + this.ancho / 2 - Bala.ancho / 2; 
	int balaY = this.y + this.largo / 2 - Bala.largo / 2;
        Bala b = new Bala(balaX, balaY + 2, true, this.ultimadireccion, this.c); 
	c.balas.add(b);                                                
	return b; 
    }
    
    public Bala disparar(int velocidad){
        if (!vivo){
            return null;
        }
        int balaX = this.x + this.ancho / 2 - Bala.ancho / 2; 
	int balaY = this.y + this.largo / 2 - Bala.largo / 2;
        Bala b = new Bala(balaX, balaY + 2, true, this.ultimadireccion, this.c,velocidad); 
	c.balas.add(b);                                                
	return b; 
    }
    
    @Override
    public boolean isVivo(){
        return this.vivo;
    }
    
    public void setVida(boolean nVida){
        this.vivo = nVida;
    }
    
    @Override
    public boolean esBueno(){
        return true;
    }
    
    public boolean chocaPoder(Poderes r){
        if (this.vivo && this.getRect().intersects(r.getRect())){
            r.tanqueupgrade(this);
            this.c.poderes.remove(r);            
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public void setVida(int pVida){
        this.vida = pVida;
    }
      

    @Override
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
    
    
           

    @Override
    public int getVida() {
        return this.vida;
    }

    public void setPodervelocidad(int podervelocidad) {
        this.podervelocidad = podervelocidad;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }


    
    
    
}
