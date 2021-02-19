import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * INTELIGENCIA ARTIFICIAL DO PLAYER (PARA DEIXAR JOGAR SOZINHO
	 * 
	 *
	 * 
	 */
	public static boolean IAPLAYER = true;
	
	public static final double IAPlayer = 0.6;
	public static final double IAInimigo = 0.6;
	
	
	public final static int LARGURA = 350, ALTURA = 250, ESCALA = 3;
	public static int  pontuacaoPlayer = 0, pontuacaoInimigo= 0;
	JFrame janela;
	Thread thread;
	private boolean isRunning;
	private BufferedImage imagem;  
	public Background background;
	public static Player player;
	private BufferedImage imageback; 
	public static Entidade entidade;
	public static Inimigo inimigo;
	private int fps;
	private boolean pausado = false;
	public boolean Menu = true;
	/**
	 * 
	 * CONTROLE DO MENU
	 */
	private int numeroy = 255;
	
	/**
	 * 
	 */
	private void Janela() {
		this.setPreferredSize(new Dimension(LARGURA * ESCALA, ALTURA * ESCALA));
		janela = new JFrame("PONG");
		janela.add(this);
		janela.setResizable(false);
		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setVisible(true);
		janela.toFront();
		janela.requestFocus();
		
	}
	
	public Game() {
		
		this.addKeyListener(this);
		entidade = new Entidade(90,150);
		inimigo = new Inimigo();
		player = new Player(90, 330);
	
	}
	
	public static void main(String args[]) {
		
		
		Game game = new Game();
		game.Janela();
		game.start();
		game.imagem = new BufferedImage(ALTURA, LARGURA, BufferedImage.TYPE_INT_RGB);
		game.background = new Background();
		
		game.background.BackgroundImage("/background.jpg");
		game.imageback = game.background.GetBackground(0,0,1280,720);
		
		
	}



	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop() {
		isRunning = false;
		try {
			System.exit(0);
			thread.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		background.tick();
		if(Menu != true) {
		player.tick();
		entidade.tick();
		inimigo.tick();
		}
		}

	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = imagem.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, LARGURA, ALTURA);
		g.drawImage(this.imageback, 0,0,null);
		background.render(g);
		

		g.setColor(new Color(0,0,255,50));
		g.fillRect(0, 0, LARGURA*ESCALA, ALTURA*ESCALA);
		
		

		if(Menu == true)
			Menu(g);
		else {
			player.render(g);
			entidade.render(g);
			inimigo.render(g);	

		}
		
		
		g.setFont(new Font("Times New Roman", Font.BOLD,  15));
		g.setColor(Color.white);
		/**/
		g.drawString("Player eixo X: " + player.x,0, 15);
		g.drawString("Player eixo Y: " + player.y,0, 30);
		g.drawString("Eixo X: " + (int)entidade.x, 0, 45);
		g.drawString("Eixo Y: " + (int)entidade.y, 0, 60);
		
		g.drawString("Quadros Por Segundo: " + fps, 0, 325);
		g.drawString("Pontuacao do Inimigo " + Game.pontuacaoInimigo, 0, 200);
		g.drawString("Pontuacao do Jogador " + Game.pontuacaoPlayer, 0, 100);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(imagem, 0,0,LARGURA*ESCALA, ALTURA*ESCALA, null);
		
		bs.show();
	}
	
	public void Menu(Graphics g) {		
		g.setFont(new Font("Times New Roman", Font.BOLD,  25));
		g.setColor(new Color(90, 182, 219));
		g.drawString("Bem-vindo",35, 80);
		g.setFont(new Font("Times New Roman", Font.BOLD,  50));
		g.setColor(Color.white);
		g.drawString("Pong",90, 130);
		
		g.setFont(new Font("Times New Roman", Font.BOLD,  25));
		g.setColor(new Color(90, 182, 219));
		g.drawString("Bem-vindo",35, 80);
		g.setFont(new Font("Times New Roman", Font.BOLD,  50));
		g.setColor(Color.white);
		g.drawString("Pong",90, 130);
		
		
		g.setFont(new Font("Times New Roman", Font.BOLD,  15));
		g.setColor(Color.white);
		g.drawString("Começar o Jogo",60, 250);
		
		g.setColor(Color.white);
		g.fillRect(60, numeroy, 110, 2);
		
		g.setFont(new Font("Times New Roman", Font.BOLD,  15));
		g.setColor(Color.white);
		g.drawString("Opções",87, 270);
		
		g.setFont(new Font("Times New Roman", Font.BOLD,  15));
		g.setColor(Color.white);
		g.drawString("Fechar",90, 290);
		
	}
	
	public void run() {

		long LastTime = System.nanoTime();
		double AmountOfTicks = 60.0;
		double ns = 1000000000 / AmountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {

			long now = System.nanoTime();
			delta += (now - LastTime) / ns;
			LastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
				}
			
			
			if(System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS: "  + frames);
				fps = frames;
				frames = 0;
				timer += 1000;
			}
			}
		
		stop();
		
		}
	

	@Override
	public void keyTyped(KeyEvent e) {

		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.esquerda = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.direita = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(Menu == true) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
		
			switch (numeroy) {
			case 255:
				numeroy = 295;
				break;
			case 275:
				numeroy = 255;
				break;
			case 295:
				numeroy = 275;
				break;
			}
				
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			switch (numeroy) {
			case 255:
				numeroy = 275;
				break;
			case 275:
				numeroy = 295;
				break;
			case 295:
				numeroy = 255;
				break;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			switch (numeroy) {
			case 255:
				Menu = false;
				break;
			case 275:
				System.out.println("Configuração");
				break;
			case 295:
				stop();
				break;
			}
			
		}
		}
		else {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.esquerda = false;
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					player.direita = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if(pausado == true) {
					pausado = false;
				} else{
					
					pausado = true;
				}
				
			}
		}	
	}
	
	
	
}
