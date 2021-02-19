import java.awt.Color;
import java.awt.Graphics;

public class Player {

	public boolean direita, esquerda;
	public int x,y;
	
	public final int largura = 60, altura = 20;
	public Player(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	
	public void tick() {
		if(Game.IAPLAYER == true) {
			x = (int) ((int)Game.entidade.x * Game.IAPlayer);
		}
		else {
			if(direita == true) {		
				x++;			
			
		}
		else if(esquerda == true) {
			x--;
		}
		
		if(x >= 190) {
			x = 190;
		}
		if(x <= 0) {
			x = 0;
		}
		}
		/*
	
		*/
		 
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x,y, largura, altura);
		
		
	}
	
	
	
}
