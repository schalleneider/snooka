package snooka;

import java.awt.Color;

/**
 * Class Player - Classe usada na para manipula��o dos jogadores do jogo.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00 
 * @docRoot
 * 
 * Date: 22.11.2005
 * 
 */
public class Player {
	
	/**
	 * Enum PlayerAction - Status de a��o para um jogador.
	 * 
	 * @author Willians Schallemberger Schneider
	 * @version 2.00 
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 * 
	 */
	public enum PlayerAction {
		/** O jogador est� esperando. */
		waiting, 
		/** O jogador est� jogando. */
		playing
	}

	/** Nome do jogador. */
	private String name;
	/** Pontos do jogador. */
	private int points;
	/** Status do jogador. */
	private PlayerAction status;
	
	/**
	 * Contrutor da classe Player.
	 * @param name Nome do jogador a ser criado.
	 * @param points Pontua��o do jogador a ser criado.
	 */
	public Player(String name, int points) {
		this.name = name;
		this.points = points;
		this.status = PlayerAction.waiting;
	}
	
	/**
	 * Devolve o nome do jogador.
	 * @return O nome do jogador.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Retorna a pontua��o atual do jogador. 
	 * @return A pontua��o atual.
	 */
	public int getPoints() {
		return this.points;
	}
	
	/**
	 * Retorna o status do jogador. 
	 * @return O status atual.
	 */
	public PlayerAction getStatus() {
		return this.status;
	}
	
	/**
	 * Altera o status do jogador. Se ele estiver esperando, passa a jogar. Se estiver jogando, passa a esperar.
	 */
	public void changeStatus() {
		if(this.status.equals(PlayerAction.waiting))
			this.status = PlayerAction.playing;
		else
			this.status = PlayerAction.waiting;
	}
	
	/**
	 * Aumenta a pontua��o do jogador, de acordo com a cor da bola que foi enca�apada.
	 * @param color Cor da bola enca�apada.
	 * @return Retorna 'true' se a pontua��o aumentou e 'false' caso n�o tenha aumentado.
	 */
	public boolean increasePoints(Color color) {
		if(color.equals(Color.WHITE)) {
			this.points += 0;
			return false;
		}		
		else if(color.equals(Color.RED))    this.points += 1;
		else if(color.equals(Color.BLUE))   this.points += 1;
		else if(color.equals(Color.PINK))   this.points += 1;
		else if(color.equals(Color.BLACK))  this.points += 1;
		else if(color.equals(Color.GREEN))  this.points += 1;
		else if(color.equals(Color.ORANGE)) this.points += 1;
		else if(color.equals(Color.YELLOW)) this.points += 1;		
		else {
			this.points += 0;
			return false;
		}
		return true;
	}
}
