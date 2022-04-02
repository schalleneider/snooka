package snooka;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Class Circle - Classe usada na cria��o e manipula��o de uma �rea circular desenh�vel.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00 
 * @docRoot
 *  
 * Date: 22.11.2005
 * 
 */
public class Circle {    
	/** Area circular desenh�vel. */
	protected Ellipse2D.Double circle;	
	/** Cor da �rea circular desenh�vel. */
	protected Color color;
    
    /**
     * Construtor da classe Circle.
     * @param x Coordenada 'x' do canto superior esquerdo do c�rculo.
     * @param y Coordenada 'y' do canto superior esquerdo do c�rculo.
     * @param radius Raio do c�rculo.
     * @param color Cor com que o c�rculo ir� ser pintado.
     */
    protected Circle(double x, double y, double radius, Color color) {
        this.circle = new Ellipse2D.Double(x, y, 2 * radius, 2 * radius);
        this.color = color;
    }
    
    /**
     * Desenha o c�rculo em uma tela.
     * @param frame Tela onde o jogo acontece.
     */
    protected void draw(Interface frame) {
        frame.drawContent(this.circle, this.color);
    }

    /**
     * Apaga um c�rculo de uma tela.
     * @param window Tela onde o jogo acontece.
     * @param color Cor com que o c�rculo ir� ser apagado.
     */
    protected void erase(Interface window, Color color) {
		window.drawContent(this.circle, color);
	}
    
    /**
     * Transforma o c�rculo atual em um objeto da classe Rectangle2D.
     * @return Retorna o c�rculo transformado.
     */
    protected Rectangle2D toRectangle2D() {
    	return this.circle.getBounds2D();
    }
}
