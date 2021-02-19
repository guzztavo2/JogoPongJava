import java.awt.Color;
import java.awt.Graphics;

public class Inimigo {

	public double x = 90,y = 0; 
	
	public final int largura = 60, altura = 20;
	
	
	public void tick() {
		x = (Game.entidade.x + 6) * Game.IAInimigo;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x,(int)y, largura, altura);
		
		
	}
	
}
