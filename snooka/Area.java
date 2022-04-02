package snooka;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * Class Area - Classe usada na criação de uma área retangular desenhável.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00
 * @docRoot
 *  
 * Date: 22.11.2005
 * 
 */
public class Area {
    /** Area retangular desenhável. */
	private Rectangle2D.Double area;
    /** Cor da área retangular. */
	private Color color;       
    
    /**
     * Construtor da classe Area.
     * @param x Coordenada 'x' do canto superior esquerdo do retângulo.
     * @param y Coordenada 'y' do canto superior esquerdo do retângulo. 
     * @param width Largura do retângulo.
     * @param height Altura do retângulo.
     * @param color Cor com que o retângulo será pintado.
     */
    public Area(double x, double y, double width, double height, Color color) {
        this.area = new Rectangle2D.Double(x, y, width, height);
        this.color = color;
    }
    
    /**
     * Desenha o retângulo em uma tela.
     * @param window Tela onde o jogo acontece.
     */
    public void draw(Interface window) {
        window.drawContent(this.area, this.color);
    }
    
    /**
     * Transforma a área atual em um objeto da classe Rectangle2D.
     * @return Retorna a área transformada.
     */
    public Rectangle2D toRectangle2D() {
    	return this.area;
    }
}
