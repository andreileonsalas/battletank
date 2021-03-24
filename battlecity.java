Bullets.javaimport java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bullets {
	public static  int speedX = 12;
	public static  int speedY = 12;

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	Direction diretion;

	private boolean good;
	private boolean live = true;

	private TankClient tc;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bulletImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>(); 
	static {
		bulletImages = new Image[] {
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletL.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletU.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletR.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletD.gif")),

		};

		imgs.put("L", bulletImages[0]); 

		imgs.put("U", bulletImages[1]);

		imgs.put("R", bulletImages[2]);

		imgs.put("D", bulletImages[3]);

	}

	public Bullets(int x, int y, Direction dir) { 
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}

	public Bullets(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}

	private void move() {

		switch (diretion) {
		case L:
			x -= speedX;
			break;

		case U:
			y -= speedY;
			break;

		case R:
			x += speedX; 
			break;

		case D:
			y += speedY;
			break;

		case STOP:
			break;
		}

		if (x < 0 || y < 0 || x > TankClient.Fram_width
				|| y > TankClient.Fram_length) {
			live = false;
		}
	}

	public void draw(Graphics g) {
		if (!live) {
			tc.bullets.remove(this);
			return;
		}

		switch (diretion) { 
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;

		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;

		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;

		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;

		}

		move(); 
	}

	public boolean isLive() { 
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean hitTank(Tank t) { 

		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {

			BombTank e = new BombTank(t.getX(), t.getY(), tc);
			tc.bombTanks.add(e);
			if (t.isGood()) {
				t.setLife(t.getLife() - 50); 
				if (t.getLife() <= 0)
					t.setLive(false); 
			} else {
				t.setLive(false); 
			}

			this.live = false;

			return true; 
		}
		return false;
	}

	public boolean hitWall(CommonWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.otherWall.remove(w); 
			this.tc.homeWall.remove(w);
			return true;
		}
		return false;
	}
	public boolean hitBullet(Bullets w){
		if (this.live && this.getRect().intersects(w.getRect())){
			this.live=false;
			this.tc.bullets.remove(w);
			return true;
		}
		return false;
	}
	public boolean hitWall(MetalWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}

	public boolean hitHome() { 
		if (this.live && this.getRect().intersects(tc.home.getRect())) {
			this.live = false;
			this.tc.home.setLive(false); 
			return true;
		}
		return false;
	}

}
CommonWall.javaimport java.awt.*;

public class CommonWall {
	public static final int width = 22; 
	public static final int length = 21;
	int x, y;

	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { 
		tk.getImage(CommonWall.class.getResource("Images/commonWall.gif")), };
	}

	public CommonWall(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc; 
	}

	public void draw(Graphics g) {
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() {  
		return new Rectangle(x, y, width, length);
	}
}
Direction.java
public enum Direction {  
	L, U,  R,  D,  STOP
}
GetBlood.javaimport java.awt.*;
import java.util.Random;

public class GetBlood {
	
	public static final int width = 34;
	public static final int length = 30;

	private int x, y;
	TankClient tc;
	private static Random r = new Random();

	int step = 0; 
	private boolean live = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bloodImags = null;
	static {
		bloodImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/hp.png")), };
	}

	private int[][] poition = { { 700, 196 }, { 500, 58 }, { 80, 300 },
			{600,321}, { 345, 456 }, { 123, 321 }, { 258, 413 } };

	public void draw(Graphics g) {
		if (r.nextInt(100) > 98) {
			this.live = true;
			move();
		}
		if (!live)
			return;
		g.drawImage(bloodImags[0], x, y, null);

	}

	private void move() {
		step++;
		if (step == poition.length) {
			step = 0;
		}
		x = poition[step][0];
		y = poition[step][1];
	}

	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {  
		this.live = live;
	}

}
Home.javaimport java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Home {
	private int x, y;
	private TankClient tc;
	public static final int width = 43, length = 43; 
	private boolean live = true;

	private static Toolkit tk = Toolkit.getDefaultToolkit(); 
	private static Image[] homeImags = null;
	static {
		homeImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/home.jpg")), };
	}

	public Home(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc; 
	}

	public void gameOver(Graphics g) {

		tc.tanks.clear();
		tc.metalWall.clear();
		tc.otherWall.clear();
		tc.bombTanks.clear();
		tc.theRiver.clear();
		tc.trees.clear();
		tc.bullets.clear();
		tc.homeTank.setLive(false);
		Color c = g.getColor(); 
		g.setColor(Color.green);
		Font f = g.getFont();
		g.setFont(new Font(" ", Font.PLAIN, 40));
		g.setFont(f);
		g.setColor(c);

	}

	public void draw(Graphics g) {

		if (live) { 
			g.drawImage(homeImags[0], x, y, null);

			for (int i = 0; i < tc.homeWall.size(); i++) {
				CommonWall w = tc.homeWall.get(i);
				w.draw(g);
			}
		} else {
			gameOver(g); 

		}
	}

	public boolean isLive() { 
		return live;
	}

	public void setLive(boolean live) { 
		this.live = live;
	}

	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}

}
MetalWall.javaimport java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class MetalWall {
	public static final int width = 36; 
	public static final int length = 37;
	private int x, y;
	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/metalWall.gif")), };
	}

	public MetalWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) { 
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}
}
River.javaimport java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class River {
	public static final int riverWidth = 55;
	public static final int riverLength = 154;
	private int x, y;
	TankClient tc ;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImags = null;
	
	static {   
		riverImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/river.jpg")),
		};
	}
	
	
	public River(int x, int y, TankClient tc) {   
		this.x = x;
		this.y = y;
		this.tc = tc;           
	}
	
	public void draw(Graphics g) {
		g.drawImage(riverImags[0],x, y, null);          
	}
	public static int getRiverWidth() {
		return riverWidth;
	}

	public static int getRiverLength() {
		return riverLength;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, riverWidth, riverLength);
	}


}
Tank.javaimport java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
	public static  int speedX = 6, speedY =6; 
	public static int count = 0;
	public static final int width = 35, length = 35;
	private Direction direction = Direction.STOP;
	private Direction Kdirection = Direction.U; 
	TankClient tc;
	private int player=0;
	private boolean good;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true;
	private int life = 200;
	private int rate=1;
	private static Random r = new Random();
	private int step = r.nextInt(10)+5 ; 

	private boolean bL = false, bU = false, bR = false, bD = false;
	

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImags = null; 
	static {
		tankImags = new Image[] {
				tk.getImage(BombTank.class.getResource("Images/tankD.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankU.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankL.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankR.gif")), 
				tk.getImage(BombTank.class.getResource("Images/HtankD.gif")), 
				tk.getImage(BombTank.class.getResource("Images/HtankU.gif")), 
				tk.getImage(BombTank.class.getResource("Images/HtankL.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankR.gif")), 
				tk.getImage(BombTank.class.getResource("Images/HtankD2.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankU2.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankL2.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankR2.gif")),
				};

	}

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc, int player) {
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
		this.player=player;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		//if (good)
			//new DrawBloodbBar().draw(g); 
		switch (Kdirection) {
							
		case D:
			if(player==1){	g.drawImage(tankImags[4], x, y, null);
			}
			else if(tc.Player2&&player==2){
				g.drawImage(tankImags[8], x, y, null);
			}else{
			g.drawImage(tankImags[0], x, y, null);}
			break;

		case U:
			if(player==1){	g.drawImage(tankImags[5], x, y, null);
			}else if(tc.Player2&&player==2){
				g.drawImage(tankImags[9], x, y, null);
			}else{
			g.drawImage(tankImags[1], x, y, null);}
			break;
		case L:if(player==1){	g.drawImage(tankImags[6], x, y, null);
		}else if(tc.Player2&&player==2){
			g.drawImage(tankImags[10], x, y, null);
		}else{
			g.drawImage(tankImags[2], x, y, null);}
			break;

		case R:if(player==1){	g.drawImage(tankImags[7], x, y, null);
		}else if(tc.Player2&&player==2){
			g.drawImage(tankImags[11], x, y, null);
		}else{
			g.drawImage(tankImags[3], x, y, null);}
			break;

		}

		move();   
	}

	void move() {

		this.oldX = x;
		this.oldY = y;

		switch (direction) {  
		case L:
			x -= speedX;
			break;
		case U:
			y -= speedY;
			break;
		case R:
			x += speedX;
			break;
		case D:
			y += speedY;
			break;
		case STOP:
			break;
		}

		if (this.direction != Direction.STOP) {
			this.Kdirection = this.direction;
		}

		if (x < 0)
			x = 0;
		if (y < 40)     
			y = 40;
		if (x + Tank.width > TankClient.Fram_width)  
			x = TankClient.Fram_width - Tank.width;
		if (y + Tank.length > TankClient.Fram_length)
			y = TankClient.Fram_length - Tank.length;

		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  
				int mod=r.nextInt(9);
				if (playertankaround()){
					if(x==tc.homeTank.x){ if(y>tc.homeTank.y) direction=directons[1];
					else if (y<tc.homeTank.y) direction=directons[3];
					}else if(y==tc.homeTank.y){ if(x>tc.homeTank.x) direction=directons[0];
					else if (x<tc.homeTank.x) direction=directons[2];
					}
					else{
						int rn = r.nextInt(directons.length);
						direction = directons[rn]; 
					}
					rate=2;
				}else if (mod==1){
					rate=1;
				}else if(1<mod&&mod<=3){
					rate=1;
				}else{
				int rn = r.nextInt(directons.length);
				direction = directons[rn]; 
				rate=1;}    
			}
			step--;
			if(rate==2){
				if (r.nextInt(40) > 35)
					this.fire();
			}else if (r.nextInt(40) > 38)
				this.fire();
		}
	}
	public boolean playertankaround(){
		int rx=x-15,ry=y-15;
		if((x-15)<0) rx=0;
		if((y-15)<0)ry=0;
		Rectangle a=new Rectangle(rx, ry,60,60);
		if (this.live && a.intersects(tc.homeTank.getRect())) {
		return true;	
		}
		return false;	
	}
	public int getzone(int x,int y){
		int tempx=x;
		int tempy=y;
		if (tempx<85&&tempy<300) return 11;
		else if(tempx>85&&tempx<140&&tempy>0&&tempy<100) return 9;
		else if(tempx>85&&tempx<140&&tempy>254&&tempy<300) return 10;
		else if(tempx>0&&tempx<200&&tempy>300&&tempy<715) return 12;
		else if(tempx>140&&tempx<400&&tempy>0&&tempy<150) return 7;
		else if(tempx>140&&tempx<400&&tempy>210&&tempy<300) return 8;
		else if(tempx>400&&tempx<500&&tempy>0&&tempy<300) return 6;
		else if(tempx>500&&tempy>0&&tempy<180) return 5;
		else if(tempx>500&&tempy>180&&tempy<300) return 4;
		else if(tempx>520&&tempx<600&&tempy>3000&&tempy<715) return 2;
		else if(tempx>600&&tempy>300&&tempy<715) return 3;
	return 1;
	}
	public int getdirect(int a,int b){
		if(b==13){
			
		}
		return 4;
	}
	private void changToOldDir() {  
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) {  
		int key = e.getKeyCode();
		if (player==1){
		switch (key) {
		case KeyEvent.VK_R:  
			tc.tanks.clear(); 
			tc.bullets.clear();
			tc.trees.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.metalWall.clear();
			tc.homeTank.setLive(false);
			if (tc.tanks.size() == 0) {        
				for (int i = 0; i < 20; i++) {
					if (i < 9)                             
						tc.tanks.add(new Tank(150 + 70 * i, 40, false,
								Direction.R, tc,0));
					else if (i < 15)
						tc.tanks.add(new Tank(700, 140 + 50 * (i -6), false,
								Direction.D, tc,0));
					else
						tc.tanks.add(new Tank(10,  50 * (i - 12), false,
								Direction.L, tc,0));
				}
			}
			
			tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc,0);
			if (!tc.home.isLive()) 
				tc.home.setLive(true);
			TankClient abc=new TankClient();
			if (tc.Player2) abc.Player2=true;
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
		}}
		if (player==2){
		switch(key){
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
		decideDirection();
	}

	void decideDirection() {
		if (!bL && !bU && bR && !bD) 
			direction = Direction.R;

		else if (bL && !bU && !bR && !bD) 
			direction = Direction.L;

		else if (!bL && bU && !bR && !bD) 
			direction = Direction.U;

		else if (!bL && !bU && !bR && bD) 
			direction = Direction.D;

		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP; 
	}

	public void keyReleased(KeyEvent e) {  
		int key = e.getKeyCode();
		if (player==1){
		switch (key) {
		
		case KeyEvent.VK_F:
			fire();
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
			

		}}
		if (player==2){
			switch (key) {
			
			case KeyEvent.VK_SLASH:
				fire();
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
				

			}}
		decideDirection(); 
	}

	public Bullets fire() { 
		if (!live)
			return null;
		int x = this.x + Tank.width / 2 - Bullets.width / 2; 
		int y = this.y + Tank.length / 2 - Bullets.length / 2;
		Bullets m = new Bullets(x, y + 2, good, Kdirection, this.tc); 
		tc.bullets.add(m);                                                
		return m;
	}


	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public boolean collideWithWall(CommonWall w) {  
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}

	public boolean collideRiver(River r) {    
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideHome(Home h) {  
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(java.util.List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		}
		return false;
	}


	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private class DrawBloodbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(375, 585, width, 10);
			int w = width * life / 200;
			g.fillRect(375, 585, w, 10);
			g.setColor(c);
		}
	}

	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
			this.life = this.life+100;      
			else
				this.life = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
TankClient.javaimport java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class TankClient extends Frame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 800; 
	public static final int Fram_length = 600;
	public static boolean printable = true;
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null,jm5=null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null,jmi10=null,jmi11=null;
	Image screenImage = null;
	
	Tank homeTank = new Tank(300, 560, true, Direction.STOP, this,1);
	Tank homeTank2 = new Tank(449, 560,true,Direction.STOP,this,2);
	Boolean Player2=false;
	GetBlood blood = new GetBlood();
	Home home = new Home(373, 557, this);
	Boolean win=false,lose=false;
	List<River> theRiver = new ArrayList<River>();
	List<Tank> tanks = new ArrayList<Tank>();
	List<BombTank> bombTanks = new ArrayList<BombTank>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	List<Tree> trees = new ArrayList<Tree>();
	List<CommonWall> homeWall = new ArrayList<CommonWall>();
	List<CommonWall> otherWall = new ArrayList<CommonWall>();
	List<MetalWall> metalWall = new ArrayList<MetalWall>();

	public void update(Graphics g) {

		screenImage = this.createImage(Fram_width, Fram_length);

		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.GRAY);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void framPaint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.green); 

		Font f1 = g.getFont();
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		if(!Player2)g.drawString("Tanks left in the field: ", 200, 70);
		else g.drawString("Tanks left in the field: ", 100, 70);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		if(!Player2)g.drawString("" + tanks.size(), 400, 70);
		else g.drawString("" + tanks.size(), 300, 70);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		if(!Player2)g.drawString("Health: ", 580, 70);
		else g.drawString("Health: ", 380, 70);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		if(!Player2) g.drawString("" + homeTank.getLife(), 650, 70);
		else g.drawString("Player1: " + homeTank.getLife()+"    Player2:"+homeTank2.getLife(), 450, 70);
		g.setFont(f1);
		if (!Player2){
			if (tanks.size() == 0 && home.isLive() && homeTank.isLive()&&lose==false) {
			Font f = g.getFont();
			g.setFont(new Font("Times New Roman", Font.BOLD, 60)); 
			this.otherWall.clear();
			g.drawString("Congratulations! ", 200, 300);
			g.setFont(f);
			win=true;
		}

		if (homeTank.isLive() == false&&win==false) {
			Font f = g.getFont();
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			tanks.clear();
			bullets.clear();
			g.drawString("Sorry. You lose!", 200, 300);
			lose=true;
			g.setFont(f);
		}}else{
			if (tanks.size() == 0 && home.isLive() && (homeTank.isLive()||homeTank2.isLive())&&lose==false) {
				Font f = g.getFont();
				g.setFont(new Font("Times New Roman", Font.BOLD, 60));
				this.otherWall.clear();
				g.drawString("Congratulations! ", 200, 300);
				g.setFont(f);
				win=true;
			}

			if (homeTank.isLive() == false&&homeTank2.isLive()==false&&win==false) {
				Font f = g.getFont();
				g.setFont(new Font("Times New Roman", Font.BOLD, 40));
				tanks.clear();
				bullets.clear();
				g.drawString("Sorry. You lose!", 200, 300);
				System.out.println("2");
				g.setFont(f);
				lose=true;
			}
		}
		g.setColor(c);

		for (int i = 0; i < theRiver.size(); i++) {
			River r = theRiver.get(i);
			r.draw(g);
		}

		for (int i = 0; i < theRiver.size(); i++) {
			River r = theRiver.get(i);
			homeTank.collideRiver(r);
			if(Player2) homeTank2.collideRiver(r);
			r.draw(g);
		}

		home.draw(g); 
		homeTank.draw(g);
		homeTank.eat(blood);
		if (Player2) {homeTank2.draw(g);homeTank2.eat(blood);}
		
		for (int i = 0; i < bullets.size(); i++) { 
			Bullets m = bullets.get(i);
			m.hitTanks(tanks); 
			m.hitTank(homeTank);
			m.hitTank(homeTank2);
			m.hitHome(); 
			for(int j=0;j<bullets.size();j++){
				if (i==j) continue;
				Bullets bts=bullets.get(j);
				m.hitBullet(bts);
			}
			for (int j = 0; j < metalWall.size(); j++) { 
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}

			for (int j = 0; j < otherWall.size(); j++) {
				CommonWall w = otherWall.get(j);
				m.hitWall(w);
			}

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				m.hitWall(cw);
			}
			m.draw(g); 
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i); 

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				t.collideWithWall(cw); 
				cw.draw(g);
			}
			for (int j = 0; j < otherWall.size(); j++) { 
				CommonWall cw = otherWall.get(j);
				t.collideWithWall(cw);
				cw.draw(g);
			}
			for (int j = 0; j < metalWall.size(); j++) {
				MetalWall mw = metalWall.get(j);
				t.collideWithWall(mw);
				mw.draw(g);
			}
			for (int j = 0; j < theRiver.size(); j++) {
				River r = theRiver.get(j);
				t.collideRiver(r);
				r.draw(g);
				// t.draw(g);
			}

			t.collideWithTanks(tanks);
			t.collideHome(home);

			t.draw(g);
		}

		//blood.draw(g);

		for (int i = 0; i < trees.size(); i++) { 
			Tree tr = trees.get(i);
			tr.draw(g);
		}

		for (int i = 0; i < bombTanks.size(); i++) { 
			BombTank bt = bombTanks.get(i);
			bt.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) { 
			CommonWall cw = otherWall.get(i);
			cw.draw(g);
		}

		for (int i = 0; i < metalWall.size(); i++) { 
			MetalWall mw = metalWall.get(i);
			mw.draw(g);
		}

		homeTank.collideWithTanks(tanks);
		homeTank.collideHome(home);
		if (Player2) {homeTank2.collideWithTanks(tanks);
		homeTank2.collideHome(home);}

		for (int i = 0; i < metalWall.size(); i++) {
			MetalWall w = metalWall.get(i);
			homeTank.collideWithWall(w);
			if (Player2)homeTank2.collideWithWall(w);
			w.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) {
			CommonWall cw = otherWall.get(i);
			homeTank.collideWithWall(cw);
			if (Player2)homeTank2.collideWithWall(cw);
			cw.draw(g);
		}

		for (int i = 0; i < homeWall.size(); i++) {
			CommonWall w = homeWall.get(i);
			homeTank.collideWithWall(w);
			if (Player2)homeTank2.collideWithWall(w);
			w.draw(g);
		}

	}

	public TankClient() {
		// printable = false;
		
		jmb = new MenuBar();
		jm1 = new Menu("Game");
		jm2 = new Menu("Pause/Continue");
		jm3 = new Menu("Help");
		jm4 = new Menu("Level");
		jm5 = new Menu("Addition");
		jm1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jm2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jm3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jm4.setFont(new Font("Times New Roman", Font.BOLD, 15));

		jmi1 = new MenuItem("New Game");
		jmi2 = new MenuItem("Exit");
		jmi3 = new MenuItem("Stop");
		jmi4 = new MenuItem("Continue");
		jmi5 = new MenuItem("Help");
		jmi6 = new MenuItem("Level1");
		jmi7 = new MenuItem("Level2");
		jmi8 = new MenuItem("Level3");
		jmi9 = new MenuItem("Level4");
		jmi10=new MenuItem("Add Player 2");
		jmi11= new MenuItem("Join other's game");
		jmi1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi5.setFont(new Font("Times New Roman", Font.BOLD, 15));

		jm1.add(jmi1);
		jm1.add(jmi2);
		jm2.add(jmi3);
		jm2.add(jmi4);
		jm3.add(jmi5);
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);
		jm5.add(jmi10);
		jm5.add(jmi11);

		jmb.add(jm1);
		jmb.add(jm2);

		jmb.add(jm4);
		jmb.add(jm5);
		jmb.add(jm3);
		

		jmi1.addActionListener(this);
		jmi1.setActionCommand("NewGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("level1");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("level2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("level3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("level4");
		jmi10.addActionListener(this);
		jmi10.setActionCommand("Player2");
		jmi11.addActionListener(this);
		jmi11.setActionCommand("Join");

		this.setMenuBar(jmb);
		this.setVisible(true);

		for (int i = 0; i < 10; i++) { 
			if (i < 4)
				homeWall.add(new CommonWall(350, 580 - 21 * i, this));
			else if (i < 7)
				homeWall.add(new CommonWall(372 + 22 * (i - 4), 517, this));
			else
				homeWall.add(new CommonWall(416, 538 + (i - 7) * 21, this));

		}

		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				otherWall.add(new CommonWall(200 + 21 * i, 300, this)); 
				otherWall.add(new CommonWall(500 + 21 * i, 180, this));
				otherWall.add(new CommonWall(200, 400 + 21 * i, this));
				otherWall.add(new CommonWall(500, 400 + 21 * i, this));
			} else if (i < 32) {
				otherWall.add(new CommonWall(200 + 21 * (i - 16), 320, this));
				otherWall.add(new CommonWall(500 + 21 * (i - 16), 220, this));
				otherWall.add(new CommonWall(222, 400 + 21 * (i - 16), this));
				otherWall.add(new CommonWall(522, 400 + 21 * (i - 16), this));
			}
		}

		for (int i = 0; i < 20; i++) { 
			if (i < 10) {
				metalWall.add(new MetalWall(140 + 30 * i, 150, this));
				metalWall.add(new MetalWall(600, 400 + 20 * (i), this));
			} else if (i < 20)
				metalWall.add(new MetalWall(140 + 30 * (i - 10), 180, this));
			
		}

		for (int i = 0; i < 4; i++) { 
			if (i < 4) {
				trees.add(new Tree(0 + 30 * i, 360, this));
				trees.add(new Tree(220 + 30 * i, 360, this));
				trees.add(new Tree(440 + 30 * i, 360, this));
				trees.add(new Tree(660 + 30 * i, 360, this));
			}

		}

		theRiver.add(new River(85, 100, this));

		for (int i = 0; i < 20; i++) {
			if (i < 9) 
				tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this,0));
			else if (i < 15)
				tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D,
						this,0));
			else
				tanks
						.add(new Tank(10, 50 * (i - 12), false, Direction.D,
								this,0));
		}

		this.setSize(Fram_width, Fram_length);
		this.setLocation(280, 50); 
		this
				.setTitle("Battle City    Final Project for CPE 640");

		this.addWindowListener(new WindowAdapter() { 
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start(); 
	}

	public static void main(String[] args) {
		new TankClient(); 
	}

	private class PaintThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) { 
			homeTank.keyReleased(e);
			homeTank2.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			homeTank.keyPressed(e);
			homeTank2.keyPressed(e);
		}

	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("NewGame")) {
			printable = false;
			Object[] options = { "Confirm", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to start a new game?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {

				printable = true;
				this.dispose();
				new TankClient();
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); 
			}

		} else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
			// try {
			// Thread.sleep(10000);
			//
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
		} else if (e.getActionCommand().equals("Continue")) {

			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start();
			}
		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "Confirm", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to exit?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("break down");
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); 

			}

		} else if(e.getActionCommand().equals("Player2")){
			printable = false;
			Object[] options = { "Confirm", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to add player2?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				printable = true;
				this.dispose();
				TankClient Player2add=new TankClient();
				Player2add.Player2=true;
			} else {
				printable = true;
				new Thread(new PaintThread()).start();
			}
		}
		else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "Use WSAD to control Player1's direction, use F to fire and restart with pressing R\nUse diection key to Control Player2, use slash to fire",
					"Help", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); 
		} else if (e.getActionCommand().equals("level1")) {
			Tank.count = 12;
			Tank.speedX = 6;
			Tank.speedY = 6;
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level2")) {
			Tank.count = 12;
			Tank.speedX = 10;
			Tank.speedY = 10;
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			this.dispose();
			new TankClient();

		} else if (e.getActionCommand().equals("level3")) {
			Tank.count = 20;
			Tank.speedX = 14;
			Tank.speedY = 14;
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level4")) {
			Tank.count = 20;
			Tank.speedX = 16;
			Tank.speedY = 16;
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("Join")){
			printable = false;
			String s=JOptionPane.showInputDialog("Please input URL:");
			System.out.println(s);
			printable = true;
			new Thread(new PaintThread()).start();
		}
		
	}
}
Tree.javaimport java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Tree {
	public static final int width = 30;
	public static final int length = 30;
	int x, y;
	TankClient tc ;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] treeImags = null;
	static {
		treeImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/tree.gif")),
		};
	}
	
	
	public Tree(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {        
		g.drawImage(treeImags[0],x, y, null);
	}
	
}
BombTank.javaimport java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class BombTank {
	private int x, y;
	private boolean live = true; 
	private TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] imgs = {
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/1.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/2.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/3.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/4.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/5.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/6.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/7.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/8.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/9.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/10.gif")), };
	int step = 0;

	public BombTank(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) { 

		if (!live) { 
			tc.bombTanks.remove(this);
			return;
		}
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}

		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
