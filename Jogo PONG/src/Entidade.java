import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Entidade {

	
	public double x,y;
	public double direcaoX, direcaoY, velocidade = 6;

	
	
	
	public Entidade(double x, double y) {
		this.x = x;
		this.y = y;	
		
		int angulo = new Random().nextInt((110 - 45) + 45);
		direcaoX = Math.cos(Math.toRadians(angulo));
		direcaoY = Math.sin(Math.toRadians(angulo));
		
	}
	
	
	public void tick() {
		if(x+(direcaoX * velocidade) >= 230) {
			direcaoX *=-1;
			
		}
		else if(x+(direcaoX*velocidade) <= 0) {
			direcaoX*=-1;
			
		}
		
		Rectangle FisicaJogador = new Rectangle(Game.player.x, Game.player.y, Game.player.largura, Game.player.altura);
		Rectangle FisicaBola = new Rectangle((int)(x+(direcaoX * velocidade)), (int)(y+(direcaoY * velocidade)), 20,20);
		Rectangle FisicaInimigo = new Rectangle((int)Game.inimigo.x, (int)Game.inimigo.y, Game.inimigo.largura, Game.inimigo.altura);
		if(y >= 380) {
			Game.pontuacaoInimigo++;
			new Game();
			
		}else if(y < 0) {
			Game.pontuacaoPlayer++;
			new Game();
		}
		if(FisicaBola.intersects(FisicaJogador))
			direcaoY *=-1;
		if(FisicaBola.intersects(FisicaInimigo))
			direcaoY *=-1;
		
		x += direcaoX * velocidade;
		y += direcaoY * velocidade;
		
		
	}
	
	public void render(Graphics g) {		
		
		g.setColor(Color.white);
		g.fillOval((int)x, (int)y, 20, 20);
		
		
	}
	
	
}
