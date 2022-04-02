package snooka;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Class Input - Classe criada para solicitar entradas de dados ao usuário.
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
	 * Solicita ao usuário a inserção de uma string válida.
	 * @param title Título da caixa de diálogo.
	 * @param prompt Texo da caixa de diálogo.
	 * @return Uma String válida.
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
