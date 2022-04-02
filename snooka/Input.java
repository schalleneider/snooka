package snooka;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Class Input - Classe criada para solicitar entradas de dados ao usu�rio.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00
 * @docRoot
 * 
 * Date: 22.11.2005
 * 
 */
public class Input {
	
	/**
	 * Construtor da classe Input.
	 */
	private Input() {}
	
	/**
	 * Solicita ao usu�rio a inser��o de uma string v�lida.
	 * @param title T�tulo da caixa de di�logo.
	 * @param prompt Texo da caixa de di�logo.
	 * @return Uma String v�lida.
	 */
	public static String getString(String title, String prompt) {
        Object[] buttons = {"OK"};
        boolean validInput = false;
        String result = "";

        while(!validInput) {            
        	JOptionPane box = new JOptionPane(prompt,
        			JOptionPane.QUESTION_MESSAGE,
        			JOptionPane.OK_OPTION,
        			null,
        			buttons,
        			"OK");
            
            box.setWantsInput(true);
            
            JDialog dialog = box.createDialog(null, title);

            dialog.pack();
            dialog.setVisible(true);
            
            result = (String)box.getInputValue();
            
            if(!result.trim().equals("") && !result.equals(JOptionPane.UNINITIALIZED_VALUE)) validInput = true;            
        }
        return result;
    }
}
