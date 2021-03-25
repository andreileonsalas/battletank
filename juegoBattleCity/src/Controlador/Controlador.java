/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import Objetos.*;
import java.util.Random;
import javax.swing.JOptionPane;



/**
 *
 * @author josemurillo
 */
public class Controlador extends Frame implements ActionListener {
    public static final int frameAncho = 800; //ancho de la ventana
    public static final int frameLargo = 600; //alto de la ventana
    public static boolean printable = true; //revisa si se debe o no imprimir en pantalla
    //se uso mas que todo para saber cuando detener o pausar el programa
    
    //elementos del menu y pantalla
    MenuBar jmb = null;
    Menu menu1 = null, menu2 = null, menu3 = null, menu4 = null,menu5=null;
    MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
    jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null,jmi10=null;  
    Image imagenPantalla = null;
    
    public FactoryPoderes FactoryPoderes = new FactoryPoderes();//creador de poderes
    public boolean jugador2 = false;//saber si el jugador 2 esta activo
    public Aguila aguila = new Aguila(373, 557, this);//creacion de la aguila

    public boolean gano = false;//ver si se gano, validacion mas adelante
    boolean pausado = false;//maneja el estado de pausado
    //creacion de elementos
    public List<Rio> rio = new ArrayList<Rio>();
    public List<Jugador> jugadores = new ArrayList<Jugador>();
    public List<Enemigo> enemigos = new ArrayList<Enemigo>();        
    public List<Bala> balas = new ArrayList<Bala>();
    public List<Arbol> arboles = new ArrayList<Arbol>();
    public List<Ladrillo> homeLadrillos = new ArrayList<Ladrillo>();
    public List<Metal> homeMetalLadrillos = new ArrayList<Metal>();
    public List<Ladrillo> otros = new ArrayList<Ladrillo>();
    public List<Metal> metalWall = new ArrayList<Metal>();
    public List<Poderes> poderes = new ArrayList<Poderes>();
    Jugador tank = new Jugador(300,557,true,"",this,1);   //jugador 1
    Jugador j2 = new Jugador(450,557,true,"",this,2);   //jugador2

    
    
            
    @Override//loop de actualizacion de imagen
    public void update(Graphics g){
        imagenPantalla = this.createImage(frameAncho, frameLargo);
        
        Graphics gps = imagenPantalla.getGraphics();
        Color c = gps.getColor();
        gps.setColor(Color.black);
        gps.fillRect(0, 0, frameAncho, frameLargo);
        gps.setColor(c);
        Dibujar(gps);
        g.drawImage(imagenPantalla, 0, 0, null);
    }
    
    
    //aca se dibujan todos los elementos y tambien se revisa si gano o no
    public void Dibujar(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.green);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        
        //valida si gano
        
        if (enemigos.isEmpty()) {
            balas.clear();
            arboles.clear();
            homeLadrillos.clear();
            metalWall.clear();
            rio.clear();
            otros.clear();
            poderes.clear();
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0, frameAncho, frameLargo);
            g.setColor(Color.green);
            g.drawString("Felicidades!! ", 350, 300);
            gano=true;
            metalWall.add(new Metal(337,500, this));
            metalWall.add(new Metal(337,536, this));
            metalWall.add(new Metal(337,572, this));
            metalWall.add(new Metal(411,500, this));
            metalWall.add(new Metal(411,536, this));
            metalWall.add(new Metal(411,572, this));
            metalWall.add(new Metal(374,500, this));                   
        }
        //valida si perdio por falta de jugadores
        if ((jugadores.isEmpty()==true)) {
            //muestra ventana para saber si quieres volver a jugar
                printable = false;
                Object[] options = { "Confirmar", "Salir" };
                int response = JOptionPane.showOptionDialog(null, "Has perdido, desdeas volverlo a intentar?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
                if (response == 1) {//cierra el programa
				System.out.println("Perdiste");
				System.exit(0);
			} else {//inicia el juego de nuevo
				this.dispose();
                                Controlador abc = new Controlador();
                                printable = true;
                

                }    
        } else if (jugadores.size()==1 && jugador2==false) {//revisa si murio jugador 1 y el jugador 2 no jugaba
             printable = false;
                Object[] options = { "Confirmar", "Salir" };
                int response = JOptionPane.showOptionDialog(null, "Has perdido, desdeas volverlo a intentar?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
                if (response == 1) {
				System.out.println("Perdiste");
				System.exit(0);
			} else {
				this.dispose();
                Controlador abc = new Controlador();
                printable = true;
             }   
                //si aun quedan jugadores, los dibuja
        }else if (jugadores.size()==1)
            {
                jugadores.get(0).colocar(g);   
            }
        else if (jugadores.size()==2)
        {
            jugadores.get(0).colocar(g);
            if (jugador2)
            jugadores.get(1).colocar(g);
        }
        //impresion de mapa
        //impresion informativa
        g.drawString("Vida J1: ", 10, 70);
        g.drawString(""+tank.getVida(),100, 70);
        if (jugador2) {
            g.drawString("Vida J2: ", 600, 70);
            g.drawString(""+j2.getVida(),700, 70);
        }
        //impresion de elementos no moviles del mapa
        aguila.colocar(g);
        homeLadrillos.stream().forEach((homeLadrillo) -> {
            homeLadrillo.colocar(g);
        });
        metalWall.stream().forEach((metalWall1) -> {
            metalWall1.colocar(g);
        });
        otros.stream().forEach((otro) -> {
            otro.colocar(g);
        });
        rio.stream().forEach((rio1) -> {
            rio1.colocar(g);
        });
        poderes.stream().forEach((podere) -> {
            podere.colocar(g);
        });
        //valida los elementos moviles del mapa
        //los metodos chocaX se usa para validar el hitbox 
        //validacion jugadores
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador aux = jugadores.get(i);
            aux.chocaTanque(enemigos);
            aux.chocaAguila(aguila);
            for (int j = 0; j < otros.size(); j++) {
                aux.chocaPared(otros.get(j));
            }
            for (int k = 0; k < rio.size(); k++) {
                aux.chocaRio(rio.get(k));
            }
            for (int l = 0; l < metalWall.size(); l++) {
                aux.chocaPared(metalWall.get(l));
            }
            for (int m = 0; m < homeLadrillos.size(); m++) {
                aux.chocaPared(homeLadrillos.get(m));
            }
            for (int n = 0; n < poderes.size(); n++) {
                aux.chocaPoder(poderes.get(n));
            } 
        }
        
        //validacion hitbox enemigos
        for (int i = 0; i < enemigos.size(); i++) {
            Enemigo aux = enemigos.get(i);           
            aux.chocaAguila(aguila);
            enemigos.get(i).colocar(g);
            for (int j = 0; j < otros.size(); j++) {
                aux.chocaPared(otros.get(j));
            }
            for (int k = 0; k < rio.size(); k++) {
                aux.chocaRio(rio.get(k));
            }
            for (int l = 0; l < metalWall.size(); l++) {
                aux.chocaPared(metalWall.get(l));
            }
            for (int l = 0; l < homeLadrillos.size(); l++) {
                aux.chocaPared(homeLadrillos.get(l));
            }
            
        }
        //Validacion hitbox balas
        for (int i = 0; i < balas.size(); i++) { 
			Bala b = balas.get(i);
                        b.hitTanques(enemigos);//recorrer todos enemigos a ver si le pega
                        b.chocaAguila();
                        for (int j = 0; j < jugadores.size(); j++) { 
				b.hitTanqueAliado(jugadores.get(j));
			}
			b.colocar(g); 
                        for (int j = 0; j < homeLadrillos.size(); j++) { 
				Ladrillo mw = homeLadrillos.get(j);
				b.chocaLadrillo(mw);
			}
                        for (int j = 0; j < otros.size(); j++) { 
				Ladrillo mw = otros.get(j);
				b.chocaLadrillo(mw);
			}
                        for (int j = 0; j < metalWall.size(); j++) { 
				Metal mw = metalWall.get(j);
				b.chocaMetal(mw);
			}
                        for (int j = 0; j < balas.size(); j++) {
                            if (i==j) continue;
                                Bala b2 = balas.get(j);
                                b2.chocaBala(b);
                        }                                
                }        
        //Arboles se coloca aqui y no arriba, ya que se supone que debe camuflar TODOS los elementos de arriba
        for (int i = 0; i < arboles.size(); i++){
            arboles.get(i).colocar(g);
        }
        //fin validacion hitbox
    }
        

    public Controlador(){
        //aca se procede a agregar todos los elementos a las listas
        //para que el update se encarge de dibujarlo
        //primero los elementos del menu
                jmb = new MenuBar();
		menu1 = new Menu("Juego");
		menu2 = new Menu("Pausa/Continue");
		menu3 = new Menu("Ayuda");
		menu4 = new Menu("Nivel");
		menu5 = new Menu("Agregar");
		menu1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		menu2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		menu3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		menu4.setFont(new Font("Times New Roman", Font.BOLD, 15));
                menu5.setFont(new Font("Times New Roman", Font.BOLD, 15));

		jmi1 = new MenuItem("Nuevo Juego");
		jmi2 = new MenuItem("Salir");
		jmi3 = new MenuItem("Pausa");
		jmi4 = new MenuItem("Continuar");
		jmi5 = new MenuItem("Ayuda");
		jmi6 = new MenuItem("Nivel 1");
		jmi7 = new MenuItem("Nivel 2");
		jmi8 = new MenuItem("Nivel 3");
		jmi9 = new MenuItem("Nivel 4");
		jmi10=new MenuItem("Agregar jugador 2");
		jmi1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi5.setFont(new Font("Times New Roman", Font.BOLD, 15));
                jmi10.setFont(new Font("Times New Roman", Font.BOLD, 15));

		menu1.add(jmi1);
		menu1.add(jmi2);
		menu2.add(jmi3);
		menu2.add(jmi4);
		menu3.add(jmi5);
		menu4.add(jmi6);
		menu4.add(jmi7);
		menu4.add(jmi8);
		menu4.add(jmi9);
		menu5.add(jmi10);

		jmb.add(menu1);
		jmb.add(menu2);
		jmb.add(menu4);
		jmb.add(menu5);
		jmb.add(menu3);
		

		jmi1.addActionListener(this);
		jmi1.setActionCommand("JuegoNuevo");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Salir");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Detener");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continuar");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("Ayuda");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("Nivel 1");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("Nivel 2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("Nivel 3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("Nivel 4");
		jmi10.addActionListener(this);
		jmi10.setActionCommand("Jugador 2");
                

		this.setMenuBar(jmb);
		this.setVisible(true);
                
                 
                //Aca es donde debemos meter cuantos muros hay, cuantos bosques, etc, para que luego el repaint se encarge de dibujarlo 
                //se agregan 2 jugadores manualmente, ya que maximo abra 2
                jugadores.add(tank);
                jugadores.add(j2);
                //Ingreso de enemigos para que se dibujen, el valor.count dice cuantos tanques se imprimiran
                //los for se encargan de dibujarlos
		for (int i = 0; i < Enemigo.count; i++) {
			if (i < 9) 
                            
				enemigos.add(new Enemigo(150 + 70 * i, 40, false, "Aba", this,0));
			else if (i < 15)
				enemigos.add(new Enemigo(700, 140 + 50 * (i - 6), false, "Aba",
						this,0));
			else
				enemigos.add(new Enemigo(10, 50 * (i - 12), false, "Aba",this,0));
		}
                //creacion de ladrillos alrededor del aguila
                for (int i = 0; i < 10; i++){
                    if (i < 4){
                        homeLadrillos.add(new Ladrillo(350, 580 - 21 * i, this));
                    }
                    			else if (i < 7)
				homeLadrillos.add(new Ladrillo(372 + 22 * (i - 4), 517, this));
			else
				homeLadrillos.add(new Ladrillo(416, 538 + (i - 7) * 21, this));
                }
                //creacion de rio
                rio.add(new Rio(85, 100, this));
                
                
                //creacion de metal
                for (int i = 0; i < 20; i++) { 
			if (i < 10) {
				metalWall.add(new Metal(140 + 30 * i, 150, this));
				metalWall.add(new Metal(600, 400 + 20 * (i), this));
			} else if (i < 20)
				metalWall.add(new Metal(140 + 30 * (i - 10), 180, this));
			
		}
                
                //creacion de ladrillos
                for (int i = 0; i < 32; i++) {
			if (i < 16) {
				otros.add(new Ladrillo(200 + 21 * i, 300, this)); 
				otros.add(new Ladrillo(500 + 21 * i, 180, this));
				otros.add(new Ladrillo(200, 400 + 21 * i, this));
				otros.add(new Ladrillo(500, 400 + 21 * i, this));
			} else if (i < 32) {
				otros.add(new Ladrillo(200 + 21 * (i - 16), 320, this));
				otros.add(new Ladrillo(500 + 21 * (i - 16), 220, this));
				otros.add(new Ladrillo(222, 400 + 21 * (i - 16), this));
				otros.add(new Ladrillo(522, 400 + 21 * (i - 16), this));
			}
		}
                //creacion de arboles
                for (int i = 0; i < 4; i++) { 
			if (i < 4) {
				arboles.add(new Arbol(0 + 30 * i, 360, this));
				arboles.add(new Arbol(220 + 30 * i, 360, this));
				arboles.add(new Arbol(440 + 30 * i, 360, this));
				arboles.add(new Arbol(660 + 30 * i, 360, this));
			}

		}               
                // fin edicion de mapa
                
                //se configura el frame
                this.setSize(frameAncho, frameLargo);
		this.setLocation(280, 50); 
		this.setTitle("Battle City    Terranigma Edition!!");
		this.addWindowListener(new WindowAdapter() { 
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());//lee las teclas
		new Thread(new PaintThread()).start(); //hace que el panel se dibuje
                new Thread(new PoderesThread()).start();//hace que los poderes parezcan cada x tiempo


                
                
    
    }
    
    public void actionPerformed(ActionEvent e){
        //menu de acciones
    if (e.getActionCommand().equals("JuegoNuevo")) {
        this.dispose();//cierrra el juego
        Controlador abc = new Controlador();//crea uno nuevo
        
    }
    if (e.getActionCommand().equals("Nivel 1")) {//configura el nivel 1
        Enemigo.bala=12;
        Enemigo.velocidad=6;
        Enemigo.vida=50;
        Enemigo.count=7;
        this.dispose();
        Controlador nivel1 = new Controlador();

    }
    if (e.getActionCommand().equals("Nivel 2")) {//configura el nivel 2
        Enemigo.bala=15;
        Enemigo.velocidad=8;
        Enemigo.vida=100;
        Enemigo.count=12;
        this.dispose();
        Controlador nivel2 = new Controlador();
    }
    if (e.getActionCommand().equals("Nivel 3")) {//configura el nivel 3
        Enemigo.bala=15;
        Enemigo.velocidad=10;
        Enemigo.vida=150;
        Enemigo.count=16;
        this.dispose();
        Controlador nivel3 = new Controlador();
    }
    if (e.getActionCommand().equals("Nivel 4")) {//configura el nivel 4
        Enemigo.bala=18;
        Enemigo.velocidad=12;
        Enemigo.vida=200;
        Enemigo.count=20;
        this.dispose();
        Controlador nive4 = new Controlador();
    }
    if (e.getActionCommand().endsWith("Detener")) {//pausa el juego 
        if (pausado == false){
            printable = false;
        }
        pausado = true;
    }
    if (e.getActionCommand().endsWith("Continuar")) {//reanuda el juego
        if (pausado == true){
            printable = true;
            new Thread(new PaintThread()).start();
        }
        pausado = false;
    }
    if (e.getActionCommand().endsWith("Jugador 2")) {//habilita el jugador 2
			jugador2=true;
    }
    
        if (e.getActionCommand().endsWith("Ayuda")) {//muestra un mensaje de ayuda
			printable = false;
                        JOptionPane.showMessageDialog(this, "Utilice a/s/d/w para moverse y f para disparar\nEl jugador 2 utliza las flechas y enter para disparar","Ayuda",1);
                        
    }
    if (e.getActionCommand().endsWith("Salir")) {//sale del juego
			System.exit(0);
    }
    
    }
    
        
        //Es el que hace que se mueva y se impriman los elementos si no esta pausado
    	private class PaintThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);//define que tan rapido se mueven los tanques
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
                }
        //se encarga que cada x segundos, se autogeneren poderes
        private class PoderesThread extends Thread {
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
                                if(gano==false){
				try 
                                {
					Thread.sleep(5000);
                                        Random r = new Random();
                                        int altor = r.nextInt(750 - 1 + 1) + 20;
                                        int anchor = r.nextInt(750 - 1 + 1) + 20;
                                        int cualpoder= r.nextInt(3)+1;
                                        poderes.add(FactoryPoderes.crearpoder(cualpoder, altor, anchor));
				} 
                                catch (InterruptedException e) 
                                {
					e.printStackTrace();
				}
                                }
			}
		}
                }
    
    
            	private class KeyMonitor extends KeyAdapter {
		public void keyReleased(KeyEvent e) { //monitorea cuando un jugador apreta una tecla
                        tank.keyReleased(e);
                        j2.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
                        tank.keyPressed(e);//monitorea cuando un jugador suelta una tecla
                        j2.keyPressed(e);
		}
                }

    
    
}
