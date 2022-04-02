package snooka;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * Class Area - Classe usada na cria��o de uma �rea retangular desenh�vel.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00
 * @docRoot
 *  
 * Date: 22.11.2005
 * 
 */
public class Area {
    /** Area retangular desenh�vel. */
	private Rectangle2D.Double area;
    /** Cor da �rea retangular. */
	private Color color;       
    
    /**
     * Construtor da classe Area.
     * @param x Coordenada 'x' do canto superior esquerdo do ret�ngulo.
     * @param y Coordenada 'y' do canto superior esquerdo do ret�ngulo. 
     * @param width Largura do ret�ngulo.
     * @param height Altura do ret�ngulo.
     * @param color Cor com que o ret�ngulo ser� pintado.
     */
    public Area(double x, double y, double width, double height, Color color) {
        this.area = new Rectangle2D.Double(x, y, width, height);
        this.color = color;
    }
    
    /**
     * Desenha o ret�ngulo em uma tela.
     * @param window Tela onde o jogo acontece.
     */
    public void draw(Interface window) {
        window.drawContent(this.area, this.color);
    }
    
    /**
     * Transforma a �rea atual em um objeto da classe Rectangle2D.
     * @return Retorna a �rea transformada.
     */
    public Rectangle2D toRectangle2D() {
    	return this.area;
    }
}
