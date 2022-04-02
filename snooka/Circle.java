package snooka;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Class Circle - Classe usada na criação e manipulação de uma área circular desenhável.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00 
 * @docRoot
 *  
 * Date: 22.11.2005
 * 
 */
public class Circle {    
	/** Area circular desenhável. */
	protected Ellipse2D.Double circle;	
	/** Cor da área circular desenhável. */
	protected Color color;
    
    /**
     * Construtor da classe Circle.
     * @param x Coordenada 'x' do canto superior esquerdo do círculo.
     * @param y Coordenada 'y' do canto superior esquerdo do círculo.
     * @param radius Raio do círculo.
     * @param color Cor com que o círculo irá ser pintado.
     */
    protected Circle(double x, double y, double radius, Color color) {
        this.circle = new Ellipse2D.Double(x, y, 2 * radius, 2 * radius);
        this.color = color;
    }
    
    /**
     * Desenha o círculo em uma tela.
     * @param frame Tela onde o jogo acontece.
     */
    protected void draw(Interface frame) {
        frame.drawContent(this.circle, this.color);
    }

    /**
     * Apaga um círculo de uma tela.
     * @param window Tela onde o jogo acontece.
     * @param color Cor com que o círculo irá ser apagado.
     */
    protected void erase(Interface window, Color color) {
		window.drawContent(this.circle, color);
	}
    
    /**
     * Transforma o círculo atual em um objeto da classe Rectangle2D.
     * @return Retorna o círculo transformado.
     */
    protected Rectangle2D toRectangle2D() {
    	return this.circle.getBounds2D();
    }
}
