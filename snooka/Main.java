package snooka;

import javax.swing.UIManager;

/**
 * Class Main - Classe criada para abrigar o método main, que inicializa o programa.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00
 * @docRoot
 * 
 * Date: 22.11.2005
 * 
 */
public class Main {
	
	/** 
	 * Construtor da classe Main. 
	 */
	private Main() {}
		
	/**
	 * Método main. Inicio da execução do programa.
	 * @param args Argumento do mátodo 'main'.
	 */
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {}
        
		new Game();
	}
}
