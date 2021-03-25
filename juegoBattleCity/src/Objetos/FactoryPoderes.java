/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author admin
 */
public class FactoryPoderes{
    

    public Poderes crearpoder(int poder,int x, int y) {
        switch (poder) {
            case 1 :
                return new VelocidadMov(x,y);
            case 2 :
                return new Tiroveloz(x,y);
            case 3 :
                return new HPup(x,y);
            default:
                return null;
        }
    }
    
 
    
}
