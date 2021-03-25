/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;
import Controlador.Controlador;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 *
 * @author josemurillo
 */
public class Enemigo extends Tanque {
    String direccion = "";
    public static int count = 7;
    public static int velocidad = 6;
    public static int bala = 6;
    public static int vida = 50;
    private int rate = 1;
    private Controlador c;
    private static Random r = new Random();
    private int step = r.nextInt(10) + 5;
    private boolean bL = false, bU = false, bR = false, bD = false;
    
    public Enemigo(int x, int y, boolean bueno, String direccion, Controlador pC, int player){
        super();
        this.x = x;
        this.oldX = x;
        this.oldY = y;
        this.y = y;
        this.aliado = bueno;
        this.numJugador = player;
        //this.vida = 50;
        this.largo = 35;
        this.ancho = 35;        
        this.tk = Toolkit.getDefaultToolkit();
        this.c = pC;
        this.direccion = direccion;
        this.ultimoestado=imagenes[0];
    }
    
    public void colocar(Graphics g){
         if (!vivo){
             c.enemigos.remove(this);
             return;
          }
         
         switch (direccion){
             case "Aba":
                 ultimoestado=imagenes[0];
                 ultimadireccion=direccion;
                 g.drawImage(this.imagenes[0], this.x, this.y, null);
                 break;
            
             case "Arr":
                 ultimoestado=imagenes[1];
                 ultimadireccion=direccion;
                 g.drawImage(this.imagenes[1], this.x, this.y, null);
                 break;
            
             case "Izq":
                 ultimoestado=imagenes[2];
                 ultimadireccion=direccion;
                 g.drawImage(this.imagenes[2], this.x, this.y, null);
                 break;
                 
             case "Der":
                 ultimoestado=imagenes[3];
                 ultimadireccion=direccion;
                 g.drawImage(this.imagenes[3], this.x, this.y, null);
                 break;
             case "":
                 g.drawImage(ultimoestado,this.x,this.y,null);
                 break;
         }
         mover();
     } 
    
      
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
        
        String[] direcciones = {"Izq", "Arr", "Der", "Aba", ""};
        if (step == 0){
            step = r.nextInt(12) + 3;
            int mod = r.nextInt(9);
            if (jugadorCerca()){
                    if(this.x == c.jugadores.get(0).x){
                        if(this.y > c.jugadores.get(0).y){
                            direccion=direcciones[1];
                        }else if (this.y < c.jugadores.get(0).y){
                            direccion = direcciones[3];
                        }
                        
                    }else if(this.y == c.jugadores.get(0).y){ 
                        if(this.x > c.jugadores.get(0).x){
                            direccion = direcciones[0];
                        }else if (this.x < c.jugadores.get(0).x){
                            direccion = direcciones[2];
                        }
                    }else{
                        int rn = r.nextInt(direcciones.length);
                        direccion = direcciones[rn]; 
                    }
                    rate = 2;
		}else if (mod == 1){
                    rate=1;
                }else if(1 < mod && mod <=3){
                    rate = 1;
		}else{
                    int rn = r.nextInt(direcciones.length);
                    direccion = direcciones[rn]; 
                    rate=1;
                }    
            }
            step--;
            if(rate == 2){
                if (r.nextInt(40) > 35){
			this.disparar();
                }
            }else if (r.nextInt(40) > 38){
			this.disparar();
            }
    }
    
    boolean jugadorCerca(){
        int rx = this.x - 15;
        int ry = this.y - 15;
        if ((x - 15) < 0){
            rx = 0;
        }
        
        if ((y - 15) < 0){
            ry = 0;
        }
        Rectangle a = new Rectangle(rx, ry, 60, 60);
        if (this.vivo && a.intersects(c.jugadores.get(0).getRect())){
            return true;
        }
        return false;
    }
    
    public void cambiarViejaDir(){
        x = this.oldX;
        y = this.oldY;
    }
    
    public void decidirDireccion(){
        direccion = "";
    }
    
    public void keyReleased(KeyEvent e){        
        decidirDireccion();
    }
    
    public Bala disparar(){
        if (!vivo){
            return null;
        }
        int balaX = this.x + this.ancho / 2 - Bala.ancho / 2; 
	int balaY = this.y + this.largo / 2 - Bala.largo / 2;
        Bala b = new Bala(balaX, balaY + 2, false, ultimadireccion, this.c,Enemigo.bala); 
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
        return false;
    }
    
    @Override
    public boolean chocaPared(Metal w){
        if (this.vivo && this.getRect().intersects(w.getRect())){
            this.cambiarViejaDir();
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public boolean chocaRio(Rio r){
        if (this.vivo && this.getRect().intersects(r.getRect())){
            this.cambiarViejaDir();
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public boolean chocaAguila(Aguila a){
        if (this.vivo && this.getRect().intersects(a.getRect())){
            this.cambiarViejaDir();
            return true;
        }
        return false;
    }
    
    @Override
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
    
        public boolean chocaJugador(java.util.List<Jugador> jugador){
        for (int i = 0; i < jugador.size(); i++){
            Jugador e = jugador.get(i);
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
    
    @Override
    public void setVida(int pVida){
        this.vida = pVida;
    }
    

    @Override
    public int getVida() {
        return this.vida;
    }

    @Override
    public void setVivo(boolean b) {
        this.vivo = b;
    }
}
