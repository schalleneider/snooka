package snooka;

import java.awt.Color;

/**
 * Class Table - Classe usada na criação e interação dos objetos da mesa de jogo.
 * 
 * @author  Willians Schallemberger Schneider
 * @version  2.00 
 * @docRoot  
 * 
 * Date: 22.11.2005
 */
public class Table {
	/** Lista contendo a área desenhável da mesa. */
	private List<Area> area = new List<Area>();
	/** Lista contendo todas as bordas da mesa. */
	private List<Area> border = new List<Area>();
	/** Lista contendo todas as caçapas da mesa. */
	private List<Circle> hole = new List<Circle>();
	
	/**
	 * Contrutor da classe Table.
	 * @param x Coordenada 'x' do canto superior esquerdo da mesa.
	 * @param y Coordenada 'y' do canto superior esquerdo da mesa.
	 * @param width Largura da mesa.
	 * @param height Altura da mesa.
	 * @param radius Raio de uma bola, usada no jogo. O tamanho da mesa é calculado em função deste valor.
	 */
	public Table(double x, double y, double width, double height, double radius) {
		Color color;
        
        radius *= Game.difficulty;
        
        color = Game.tableAreaColor;
        
        // Cria a área jogável da mesa.
        this.area.insert(new Area(x + radius, y + radius, width, height, color));        
        
        color = Game.tableBorderColor;
        
        // Cria as bordas da mesa.
        this.border.insert(new Area(x + radius, y, width, radius, color));
        this.border.insert(new Area(x, y + radius, radius, height, color));
        this.border.insert(new Area(x + radius, y + radius + height, width, radius, color));
        this.border.insert(new Area(x + width + radius, y + radius, radius, height, color));
        
        color = Game.tableHoleColor;
		
        // Cria as caçapas da mesa.
        this.hole.insert(new Circle(x, y, radius, color));
        this.hole.insert(new Circle(x + width, y, radius, color));
        this.hole.insert(new Circle(x + (width/2), y, radius, color));       
        this.hole.insert(new Circle(x, y + height, radius, color));
        this.hole.insert(new Circle(x + width, y + height, radius, color));
        this.hole.insert(new Circle(x + (width/2), y + height, radius, color));
	}
	
	/**
	 * Desenha todas as áreas da mesa.
	 * @param window Tela onde o jogo acontece.
	 */
	public void drawArea(Interface window) {
        for(int i = 0 ; i < this.area.size ; i++)
            this.area.getElement(i).draw(window);
	}
	
	/**
	 * Desenha todas as bordas da mesa.
	 * @param window Tela onde o jogo acontece.
	 */
	public void drawBorder(Interface window) {
        for(int i = 0 ; i < this.border.size ; i++)
            this.border.getElement(i).draw(window);
	}
	
	/**
	 * Desenha todas as caçapas da mesa.
	 * @param window Tela onde o jogo acontece.
	 */
	public void drawHole(Interface window) {
        for(int i = 0 ; i < this.hole.size ; i++)
            this.hole.getElement(i).draw(window);
	}
	
	/**
	 * Desenha todos os elementos da mesa.
	 * @param window Tela onde o jogo acontece.
	 */
	public void draw(Interface window) {
		this.drawArea(window);
		this.drawBorder(window);
		this.drawHole(window);
	}
	
	/**
	 * Retorna a borda do indice específicado. 
	 * @param index Índice do elemento procurado.
	 * @return Retorna a borda procurada.
	 */
	public Area getBorder(int index) {
		return this.border.getElement(index);
	}
	
	/**
	 * Retorna a caçapa do indice específicado. 
	 * @param index Índice do elemento procurado.
	 * @return Retorna a caçapa procurada.
	 */
	public Circle getHole(int index) {
		return this.hole.getElement(index);
	}
}