import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Background {

	private int x, y;
	private int random;
	private Random rand;
	private BufferedImage background;
	
	public void BackgroundImage(String caminho) {
		try {
			background = ImageIO.read(getClass().getResource(caminho));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage GetBackground(int x, int y, int widht, int height) {
		return background.getSubimage(x, y, widht, height);
	}
	public void tick() {
		rand = new Random();
		random = rand.nextInt(10);
		if(random == 1) {
			
			x = rand.nextInt(Game.ALTURA); y = rand.nextInt(Game.LARGURA);	
			
			}
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(new Color(254, 254, 162));
		g.fillOval(x, y, 3, 3);
		g.setColor(Color.white);
		g.fillOval(x, y, 3, 3);
		

	}
	
}
