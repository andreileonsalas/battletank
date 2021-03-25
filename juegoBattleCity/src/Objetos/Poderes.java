package Objetos;

import Controlador.Controlador;
import java.awt.*;

/**
 *
 * @author josemurillo
 */
public abstract class Poderes extends Objeto implements Inmovible{
    
    
    Controlador c;
    
    public Poderes(){
        vivo = true;
        
    }
    
    @Override
    public Rectangle getRect(){
        return new Rectangle(this.x, this.y, this.ancho, this.largo);
    }
    public void tanqueupgrade (Jugador j){
    
    }
    
}