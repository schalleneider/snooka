package snooka;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

/**
 * Class Game - Classe usada no gerenciamento do jogo.
 * 
 * @author  Willians Schallemberger Schneider
 * @version  2.00 
 * @docRoot
 * 
 * Date: 22.11.2005
 */
public class Game implements Runnable{	
	/** Valor da gravidade. */
	public static final double gravity = 9.8;	
	/** Coeficiente de atrito da mesa. */
	public static final double atrrition = -0.5;
	/** Propor��o entre o tamanho da bola e da ca�apa. */
	public static final double difficulty = 1.5;
	
	/** Cor da ca�apa da mesa. */
	public static final Color tableHoleColor = new Color(0, 0, 0);
	/** Cor da �rea da mesa. */
	public static final Color tableAreaColor = new Color(0, 101, 0);
	/** Cor da borda da mesa. */
	public static final Color tableBorderColor = new Color(117, 67, 0);
	
	/** Arquivo de configura��es. */
	private File file = null;
	/** Mesa onde o jogo acontece. */
	private Table table = null;
	/** Janela onde o jogo se desenvolve. */
	private Interface window = null;
	/** Lista contendo todas as bolas do jogo. */
	private List<Ball> balls = new List<Ball>();
	/** Lista contendo todos os jogadores do jogo. */
	private List<Player> players = new List<Player>();
	
	/** Indica se, na tacada atual, o jogador j� enca�apou ao menos uma bola. */
	private boolean killedAnyBall;
	/** Indica se, na tacada atual, o jogador dever� jogar novamente. */
	private boolean playAgain;
		
	/**
	 * Construtor da classe Game.
	 */
	public Game() {
		this.window = new Interface();
		this.addActionListeners();
		// Desabilita o item de menu "Save Game".
		this.window.setEnabledMenuItem(03, false);
	}
	
	/**
	 * Construtor da classe Game.
	 * @param file Arquivo de configura��es.
	 */
	public Game(File file) {
		
		this.file = file;
		this.window = new Interface(
				(int)(this.file.getFileTable().getWidth() + 2 * Game.difficulty * this.file.getFileRadius()), 
				(int)(this.file.getFileTable().getHeight() + 2 * Game.difficulty * this.file.getFileRadius())
				);
		
		this.table = new Table(0, 0,
				this.file.getFileTable().getWidth(), 
				this.file.getFileTable().getHeight(), 
				this.file.getFileRadius());	
		
		this.table.draw(this.window);
		
		for(int i = 0 ; i < file.getFileBalls().size ; i++) {			
			this.balls.insert(
					new Ball(
							this.file.getFileBalls().getElement(i).getX(),
							this.file.getFileBalls().getElement(i).getY(),
							this.file.getFileBalls().getElement(i).getRadius(),
							this.file.getFileBalls().getElement(i).getWeight(),
							this.file.getFileBalls().getElement(i).getColor()
							));
			
			this.balls.getElement(i).draw(this.window);
			
			if(this.balls.getElement(i).color == Color.WHITE) {
				// Link dentro da lista para a bola branca.
				this.balls.user = this.balls.getElement(i);
			}
		}
		
		// Verifica ou cria os jogadores.
		if(this.file.getFilePlayers().getElement(0).getPoints() < 0) {
			for(int i = 0 ; i < 2 ; i++) {
				this.players.insert(new Player(Input.getString("Configura��o dos jogadores", "Digite o nome do jogador " + (i+1) + "."), 0));
			}
		}
		else {
			for(int i = 0 ; i < 2 ; i++) {
				this.players.insert(new Player(
						this.file.getFilePlayers().getElement(i).getName(), 
						this.file.getFilePlayers().getElement(i).getPoints()
						));
			}
		}
		
		this.players.getElement(1).changeStatus();
		this.changeActivePlayer();		
		this.addActionListeners();
	}
	
	/**
	 * Inicia o jogo.
	 */
	public void run() {		
		
		while(this.isPlaying()) {			
			// Retorna a bola branca para a mesa, caso ela tenha sido enca�apada.
			if(this.balls.user.isKilled)
				this.balls.user.back(this.window, file.getFileTable().getWidth(), file.getFileTable().getHeight());
			
			this.window.player1.update(
					this.players.getElement(0).getName(),
					this.players.getElement(0).getPoints()
					);
			this.window.player2.update(
					this.players.getElement(1).getName(),
					this.players.getElement(1).getPoints()
					);
						
			killedAnyBall = false;			
			playAgain = false;		
			
			this.window.setStatusBarInformation(this.activePlayer().getName() + ", clique em alguma �rea da mesa. A bola ir� mover-se nessa dire��o.");
			
			// Aguarda a execu��o de algum evento.
			while(this.balls.user.isMoving == false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			
			this.window.setEnabledMenu(false);
			
			// Para cada bola, incrementa o seu tempo, verifica se houve colis�o e modifica a pontua��o se necess�rio.
			while(this.isMoving()) {
				try	{
					Thread.sleep((long)this.file.getFileDelay());
				}
				catch(InterruptedException e) {					
				}
				
				for(int i = 0 ; i < this.balls.size ; i++) {
					this.balls.getElement(i).increaseTime();
					this.balls.getElement(i).move();			
					this.balls.getElement(i).refresh(this.window);
					this.collision(this.balls.getElement(i));
					
					this.window.player1.update(
							this.players.getElement(0).getName(),
							this.players.getElement(0).getPoints()
							);
					this.window.player2.update(
							this.players.getElement(1).getName(),
							this.players.getElement(1).getPoints()
							);
				}
			}
			
			this.window.setEnabledMenu(true);
			
			// Troca de jogador, caso o atual n�o tenha enca�apado nenhuma bola ou matado a bola branca.
			if(!killedAnyBall || !playAgain) this.changeActivePlayer();
		}
		// Verifica quem foi o ganhador do jogo.
		String winner = (this.players.getElement(0).getPoints() < this.players.getElement(1).getPoints()) ? (this.players.getElement(1).getName()) : (this.players.getElement(0).getPoints() > this.players.getElement(1).getPoints()) ? (this.players.getElement(0).getName()) : (null);
		if(winner != null) {
			this.window.setStatusBarInformation("Fim de jogo. Parab�ns, " + winner + ". Voc� venceu a partida.");
		}
		else {
			this.window.setStatusBarInformation("Fim de jogo. Houve empate na partida realizada.");
		}
	}
	
	/**
	 * Verifica se pelo menos uma bola em jogo est� em movimento.
	 * @return 'true' se pelo menos uma estiver em movimento e 'false' em caso contr�rio.
	 */
	private boolean isMoving() {
		for(long i = 0 ; i < this.balls.size ; i++)
			if(this.balls.getElement(i).isMoving == true) return true;
		return false;
	}
	
	/**
	 * Verifica se h� alguma bola, exceto a branca; em jogo. 
	 * @return Retorna 'true' se pelo menos uma bola al�m da branca estiver em jogo e 'false' em caso contr�rio.
	 */
	private boolean isPlaying() {
		for(long i = 0 ; i < this.balls.size ; i++)
			if(this.balls.getElement(i).isKilled == false && this.balls.getElement(i).color != Color.WHITE) 
				return true;
		return false;
	}
	
	/**
	 * Verifica se a bola informada, colide com algum objeto da mesa.
	 * @param arg0 Bola a ser verificada.
	 */
	private void collision(Ball arg0) {		
		// Apenas bolas em movimento colidem.
		if(arg0.isMoving) {
			for(int i = 0 ; i < ((this.balls.size < 6) ? 6 : this.balls.size) ; i++) {
				// Colis�o com a ca�apa.
				if(arg0.intersects(table.getHole(i))) {
					arg0.kill(this.window);
					// Aumenta a pontua��o do jogador e verifica se ele tem que jogar novamente, 
					// no caso da bola branca ter ca�do dentro da ca�apa.
					killedAnyBall = true;
					playAgain = playAgain ? true : this.activePlayer().increasePoints(arg0.color);
				}
				// Colis�o com a borda da mesa.
				else if(arg0.intersects(table.getBorder(i))) {
					arg0.collides(i);
				}
				// Colis�o com outra bola.
				else if(arg0.intersects(this.balls.getElement(i))) {
					arg0.collides(this.balls.getElement(i));
				}
			}
		}
	}
	
	/**
	 * Troca de jogador e muda a cor da borda dos labels de visualiza��o de pontua��o.
	 */
	private void changeActivePlayer() {		
		this.players.getElement(0).changeStatus();
		this.players.getElement(1).changeStatus();
		this.window.player1.changeColor(this.players.getElement(0).getStatus().equals(Player.PlayerAction.playing) ? true : false);
		this.window.player2.changeColor(this.players.getElement(0).getStatus().equals(Player.PlayerAction.playing) ? false : true);
	}
	
	/**
	 * Verifica e retorna qual jogador est� jogando. 
	 * @return O jogador que est� jogando.
	 */
	private Player activePlayer() {
		return this.players.getElement(0).getStatus().equals(Player.PlayerAction.playing) ? this.players.getElement(0) : this.players.getElement(1);
	}
	
	/**
	 * Atualiza a vari�vel que cont�m uma c�pia do arquivo de configura��es para fazer criar um novo arquivo de 'save'.
	 */
	private void updateFile() {
		// Apenas os nomes dos jogadores, sua pontua��o, e as posi��es das bolas s�o alterados durante o jogo.
		for(int i = 0 ; i < 2 ; i++) {
			this.file.getFilePlayers().getElement(i).setName(this.players.getElement(i).getName());
			this.file.getFilePlayers().getElement(i).setPoints(this.players.getElement(i).getPoints());
		}
		
		for(int i = 0 ; i < this.balls.size ; i++) {
			this.file.getFileBalls().getElement(i).setX(this.balls.getElement(i).getPoint().getX());
			this.file.getFileBalls().getElement(i).setY(this.balls.getElement(i).getPoint().getY());
		}
	}
	
	/**
	 * Executa uma tacada na bola branca, de acordo com a posi��o do mouse na tela.
	 * @param m Posi��o do mouse no momento da a��o.
	 */
	private void action(Point2D m) {
		double speedy, angle;
		
		double x = Math.abs(m.getX() - this.balls.user.getPoint().getX());
		double y = Math.abs(m.getY() - this.balls.user.getPoint().getY());
		
		angle = Math.toDegrees(Math.atan(y/x));
		speedy = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
		
		// C�lculo do �ngulo do movimento da tacada.
		if(m.getX() >= this.balls.user.getPoint().getX()) {
			if(m.getY() >= this.balls.user.getPoint().getY()) { // 4� Quadrante
				angle = 360 - angle;
			}
			else { // 1� Quadrante
				angle += 0;
			}
		}
		else {
			if(m.getY() >= this.balls.user.getPoint().getY()) { // 3� Quadrante
				angle += 180;
			}
			else { // 2� Quadrante
				angle = 180 - angle;
			}
		}
		
		this.balls.user.newMoviment((speedy > 77 ? 88 : speedy), 360 - angle);
	}
	
	/**
	 * Adiciona os listeners de a��es para o mouse e menu da tela de jogo.
	 */
	private void addActionListeners() {
		
		// Adiciona listeners de a��es para o mouse.
		this.window.addEvents(new MouseListener(){			
			
			// Quando o bot�o do mouse for solto.
			public void mouseReleased(MouseEvent arg0) {
				// Caso nenhuma bola esteja em movimento, executa a a��o.
				if(!isMoving()) {
					action(arg0.getPoint());
				}
			}
			
			// Quando o bot�o do mouse for clicado.
			public void mouseClicked(MouseEvent arg0) {
			}
			
			// Quando o bot�o do mouse for pressionado.
			public void mousePressed(MouseEvent arg0) {
			}
			
			// Quando o ponteiro do mouse entrar na janela.
			public void mouseEntered(MouseEvent arg0) {
			}
			
			// Quando o ponteiro do mouse sair da janela.
			public void mouseExited(MouseEvent arg0) {
			}			
        });
		
		// Adiciona listeners de movimento para o mouse.
		this.window.addEvents(new MouseMotionListener() {
			
			// Quando o mouse for movido com o bot�o pressionado.
			public void mouseDragged(MouseEvent arg0) {
			}
			
			// Quando o mouse for movido.
			public void mouseMoved(MouseEvent arg0) {
			}
		});
		
		// Adiciona listeners de a��o no menu.
		this.window.addEvents(new ActionListener() {
			
			// Quando algum item do menu for clicado.
			public void actionPerformed(ActionEvent arg0) {
				
				File newfile;
				JFileChooser fileChooser = new JFileChooser();				
				String menuItemText = ((JMenuItem)arg0.getSource()).getText();
								
				if(menuItemText.equals("New Game")) {
					fileChooser.showOpenDialog(window);
					if(fileChooser.getSelectedFile() != null) {	
						newfile = new File();
						if(newfile.readNew(fileChooser.getSelectedFile().getAbsolutePath())) {
							window.dispose();
							Thread newGame = new Thread(new Game(newfile));
							newGame.start();
						}
					}
				}
				
				else if(menuItemText.equals("Load Game")) {
					fileChooser.showOpenDialog(window);
					if(fileChooser.getSelectedFile() != null) {
						newfile = new File();
						if(newfile.readLoad(fileChooser.getSelectedFile().getAbsolutePath())) {						
							window.dispose();
							Thread newGame = new Thread(new Game(newfile));
							newGame.start();
						}
					}
				}
				
				else if(menuItemText.equals("Save Game")) {
					fileChooser.showSaveDialog(window);
					updateFile();
					file.writeSave(fileChooser.getSelectedFile().getAbsolutePath());	
				}
				
				else if(menuItemText.equals("Exit")) {
					System.exit(0);
				}
				
				else if(menuItemText.equals("About Snooka...")) {
					window.setStatusBarInformation("Snooka - Simulador de Sinuca - v2.00");
				}
			}			
		});
	}
}