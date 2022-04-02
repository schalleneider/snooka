package snooka;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 * Class Interface - Respons�vel por criar uma janela para desenhar os jogo e a pontua��o.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00
 * @docRoot
 * 
 * Date: 22.11.2005
 * 
 */
@SuppressWarnings("serial")
public class Interface extends JFrame {
	
	/** Label referente ao jogador 1. */
	InterfaceLabel player1;
	/** Label referente ao jogador 2. */
	InterfaceLabel player2;
	/** Label referente � barra de informa��es. */
	InterfaceLabel statusBar;
	
	/** Painel onde fica a barra de informa��es. */
	private JPanel statusPane;
	/** Painel onde fica os labels dos jogadores. */
	private JPanel playersPane;	
	/** Painel onde os elementos do jogo s�o desenhados. */
	private InterfacePanel gamePane;
	
	/** Barra de menu. */
	private JMenuBar menuBar;
	
    /** Item da barra de menu. */
	private JMenu menuGame;
    /** Item da barra de menu. */
    private JMenu menuHelp;

	/** Item do menu "Game". */
	private JMenuItem menuGameNew;
    /** Item do menu "Game". */
    private JMenuItem menuGameSave;
    /** Item do menu "Game". */
    private JMenuItem menuGameExit;
    /** Item do menu "Game". */
    private JMenuItem menuGameLoad;
	
    /** Item do menu "Help". */
    private JMenuItem menuHelpAbout;
	
	/**
	 * Construtor da classe Interface.
	 */
	public Interface() {
		this(400, 200);
		this.gamePane.setVisible(false);
		this.playersPane.setVisible(false);		
	}
	
	/**
	 * Contrutor da classe Interface.
	 * @param width Largura da �rea da mesa.
	 * @param height Altura da �rea da mesa.
	 */
	public Interface(int width, int height) {		
		super("Snooka - Simulador de Sinuca");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setIconImage((new ImageIcon("images/snooka2.gif")).getImage());
		this.setResizable(false);

		this.playersPane = new JPanel();
		this.statusPane = new JPanel();
		this.gamePane = new InterfacePanel(width, height);
		
		this.playersPane.setPreferredSize(new Dimension(width, 35));
		this.statusPane.setPreferredSize(new Dimension(width, 35));
		
		this.player1 = new InterfaceLabel("Player 1", 0, width, 25);
		this.player2 = new InterfaceLabel("Player 2", 0, width, 25);
		this.statusBar = new InterfaceLabel("Clique em Game > New Game para iniciar um novo jogo.", width, 25);

		this.playersPane.add(this.player1, BorderLayout.EAST);
		this.playersPane.add(this.player2, BorderLayout.WEST);
		this.statusPane.add(this.statusBar, BorderLayout.CENTER);
				
		// Adiciona os pain�is.
		this.getContentPane().add(this.playersPane, BorderLayout.NORTH);
		this.getContentPane().add(this.gamePane, BorderLayout.CENTER);
		this.getContentPane().add(this.statusPane, BorderLayout.SOUTH);
		
		// Cria os menus.
		this.createMenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.pack();
		
		// Centraliza a janela na tela.
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = this.getSize();
		this.setLocation(((screen.width - frame.width) / 2), ((screen.height - frame.height) / 2));
		
		this.gamePane.inicialize();
		
		this.setVisible(true);
	}
	
    /**
     * Cria a barra de menus do jogo.
     */
	private void createMenuBar() {
		// Barra do menu.
		this.menuBar = new JMenuBar();
		//this.menuBar.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		// Criando itens da barra e adicionando.
		this.menuGame = new JMenu("Game");
		//this.menuGame.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		this.menuHelp = new JMenu("Help");
		//this.menuHelp.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		this.menuBar.add(this.menuGame);
		this.menuBar.add(this.menuHelp);
		
		// Criando itens do menu Game e adicionando.
		this.menuGameNew = new JMenuItem("New Game");
		//this.menuGameNew.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		this.menuGameLoad = new JMenuItem("Load Game");
		//this.menuGameLoad.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		this.menuGameSave = new JMenuItem("Save Game");
		//this.menuGameSave.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		this.menuGameExit = new JMenuItem("Exit");
        this.menuGameExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		//this.menuGameExit.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		this.menuGame.add(this.menuGameNew);
		this.menuGame.addSeparator();
		this.menuGame.add(this.menuGameLoad);
		this.menuGame.add(this.menuGameSave);
		this.menuGame.addSeparator();
		this.menuGame.add(this.menuGameExit);
		
		// Criando itens do menu Help e adicionando.		
		this.menuHelpAbout = new JMenuItem("About Snooka...");
		//this.menuHelpAbout.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		
		this.menuHelp.add(this.menuHelpAbout);
	}
	
	/**
	 * Adiciona um "listener" de eventos no painel onde o jogo acontece.
	 * @param arg0 "Listener" a ser adicionado.
	 */
	public void addEvents(MouseListener arg0) {
		this.gamePane.addMouseListener(arg0);
	}
	
	/**
	 * Adiciona um "listener" de eventos no painel onde o jogo acontece.
	 * @param arg0 "Listener" a ser adicionado.
	 */
	public void addEvents(MouseMotionListener arg0) {
		this.gamePane.addMouseMotionListener(arg0);
	}
	
	/**
	 * Adiciona um "listener" de eventos no painel onde o jogo acontece.
	 * @param arg0 "Listener" a ser adicionado.
	 */
	public void addEvents(ActionListener arg0) {
		this.menuGameNew.addActionListener(arg0);
		this.menuGameLoad.addActionListener(arg0);
		this.menuGameSave.addActionListener(arg0);
		this.menuGameExit.addActionListener(arg0);
		this.menuHelpAbout.addActionListener(arg0);
	}
	
	/**
	 * Troca o texto de informa��o do label de status.
	 * @param text Texto a ser escrito no label.
	 */
	public void setStatusBarInformation(String text) {
		this.statusBar.setText(text);
	}
	
	/**
	 * Habilita/Desabilita os itens da barra de menu.
	 * @param enabled "true" habilita os itens da barra de menu. "false" desabilita.
	 */
	public void setEnabledMenu(boolean enabled) {
		this.menuGame.setEnabled(enabled);
		this.menuHelp.setEnabled(enabled);
	}
	
	/**
	 * Habilita/Desabilita um subitem de um dos menus.
	 * <br><br>
	 * �ndices:<br>
	 * <br>
	 * 00 - Game<br>
	 * 01 - Game > New Game<br>
	 * 02 - Game > Load Game<br>
	 * 03 - Game > Save Game<br>
	 * 04 - Game > Exit<br>
	 * <br>
	 * 10 - Help<br>
	 * 11 - Help > Help<br>
	 * 12 - Help > About Snooka...<br>
	 * 
	 * @param index �ndice do menu.
	 * @param enabled "true" habilita os itens menu. "false" desabilita.
	 */
	public void setEnabledMenuItem(int index, boolean enabled) {
		switch (index) {
			case 00: this.menuGame.setEnabled(enabled); break;
			case 10: this.menuHelp.setEnabled(enabled); break;
			case 01: this.menuGameNew.setEnabled(enabled); break;
			case 02: this.menuGameLoad.setEnabled(enabled); break;
			case 03: this.menuGameSave.setEnabled(enabled); break;
			case 04: this.menuGameExit.setEnabled(enabled); break;
			case 12: this.menuHelpAbout.setEnabled(enabled); break;
		}
	}
	
	/**
	 * Desenha um "Shape" no "ContentPane" da tela. 
	 * @param shape "Shape" a ser desenhado.
	 * @param color Cor com que o "Shape" ser� desenhado.
	 */
	public void drawContent(Shape shape, Color color) {
		this.gamePane.graphics.setColor(color);
		this.gamePane.graphics.fill(shape);
		this.gamePane.repaint();
	}
	
	/**
	 * Apaga um "Shape" do "ContentPane" da tela. 
	 * @param shape "Shape" a ser apagado.
	 * @param color Cor com que o "Shape" ser� apagado.
	 */
	public void eraseContent(Shape shape, Color color) {
		this.gamePane.graphics.setBackground(color);
		this.drawContent(shape, color);
	}
	
	/**
	 * Class InterfacePanel - Respons�vel por criar um label espec�fico escrita.
	 * 
	 * @author Willians Schallemberger Schneider
	 * @version 2.00 
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 * 
	 */
	class InterfaceLabel extends JLabel {
		/** Cor do fundo do label quando o jogador que o label representa estiver jogando.
		 * 	Usado tamb�m como cor de fundo do label da barra de inform��es.	 
		 */
		private Color activeColor = new Color(163, 184, 204);
		/** Cor do fundo do label quando o jogador que o label representa estiver esperando. */
		private Color inactiveColor = new Color(192, 192, 192);

		/**
		 * Construtor da classe InterfaceLabel.
		 * @param text Texto a ser escrito no label.
		 * @param width Largura do label.
		 * @param height Altura do label.
		 */
		private InterfaceLabel(String text, int width, int height) {
			this.setText(text);
			this.setPreferredSize(new Dimension(width - 10, height));			
			this.setHorizontalAlignment(JLabel.CENTER);
			this.setVerticalTextPosition(JLabel.CENTER);
			this.setHorizontalTextPosition(JLabel.CENTER);
			this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			this.setOpaque(true);
			this.setBackground(this.activeColor);
			//this.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		}
		
		/**
		 * Construtor da classe InterfaceLabel.
		 * @param playerName Nome do jogador a ser mostrado.
		 * @param playerPoints N�mero de pontos do jogador a ser mostrado.
		 * @param width Largura do label.
		 * @param height Altura do label.
		 */
		private InterfaceLabel(String playerName, int playerPoints, int width, int height) {
			this(playerName + " :: " + playerPoints + " pts.", width / 2, height);
		}
		
		/**
		 * Atualiza o texto do label.
		 * @param playerName Nome do jogador.
		 * @param playerPoints N�mero de pontos do jogador.
		 */
		void update(String playerName, long playerPoints) {
			this.setText(playerName + " :: " + playerPoints + " pts.");
		}	
		
		/**
		 * Muda a cor de fundo do label, para indicar se ele deve estar ativado ou n�o.
		 * @param active Indica qual cor ser� utilizada. "true" utiliza a cor ativada. "false" utiliza a cor padr�o.
		 */
		void changeColor(boolean active) {
			if(active)
				this.setBackground(this.activeColor);
			else
				this.setBackground(this.inactiveColor);
		}
	}
	
	/**
	 * Class InterfacePanel - Respons�vel por criar um painel espec�fico para desenho.
	 * 
	 * @author Willians Schallemberger Schneider
	 * @version 2.00 
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 * 
	 */
	private class InterfacePanel extends JPanel {
		/** Objeto "Graphics2D" respons�vel por desenhar shapes neste painel. */
		private Graphics2D graphics;
		/** Dimens�o do painel. */
		private Dimension size;
		/** Imagem usada para fazer o "repaint" da tela, quando necess�rio. */
		private Image image;
		
		/**
		 * Construtor da classe InterfacePanel
		 * @param width Largura do painel;
		 * @param height Altura do painel.
		 */
		private InterfacePanel(int width, int height) {
			this.size = new Dimension(width, height);
			this.setPreferredSize(size);
		}
		
		/**
		 * Inicializa o painel.
		 */
		private void inicialize() {
			this.image = this.createImage(size.width, size.height);
			this.graphics = (Graphics2D)this.image.getGraphics();
		}
		
		/**
		 * Redesenha o conte�do no painel com base na imagem obtida anteriormente.
		 * @param g Objeto Graphics do painel.
		 */
		public void paint(Graphics g) {
        	if(this.image != null) {
        		g.drawImage(this.image, 0, 0, null);
        	}
        }
	}
}
