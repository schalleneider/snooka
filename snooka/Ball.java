package snooka;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Class Ball - Classe usada na criação e interação das bolas do jogo.
 * 
 * @author Willians Schallemberger Schneider
 * @version 2.00 
 * @docRoot
 * 
 * Date: 22.11.2005
 * 
 */
public class Ball extends Circle {
	/** Tempo de incremento usado entre um movimento e outro. */
	private final double increaseTime = 0.03;
	/** Aceleração da bola. */
	private final double acceleration = Game.gravity * Game.atrrition;
	
	/** Raio da bola. */
	private double radius;
	/** Massa da bola. */
	private double weight;
	
	/** Coordenada 'x' do centro da bola, referente à posição inicial do movimento atual. */
	private double centerX0;
	/** Coordenada 'y' do centro da bola, referente à posição inicial do movimento atual. */
	private double centerY0;
	
	/** Coordenada 'x' do centro da bola */
	private double centerX;
	/** Coordenada 'y' do centro da bola */
	private double centerY;
	
	/** Velocidade atual da bola. */
	private double speedy;
	/** Velocidade inicial da bola, referente ao movimento atual. */
	private double speedy0;
	
	/** Tempo do movimento atual. */
	private double time;
	/** Angulo do movimento em relação à origem da janela (canto superior esquerdo). */
	private double angle;
	
	/** Indica se a bola está em movimento. "true" em caso afirmativo, "false" caso contrário. */
	boolean isMoving;	
	/** Indica se a bola foi encaçapada. "true" em caso afirmativo, "false" caso contrário. */
	boolean isKilled;
	
	/**
	 * Construtor da classe Ball.
	 * @param x Coordenada 'x', do centro da bola.
	 * @param y Coordenada 'y', do centro da bola.
	 * @param radius Raio da bola.
	 * @param weight Massa da bola.
	 * @param color Cor com que a bola será pintada.
	 */
	public Ball(double x, double y, double radius, double weight, Color color) {
		super(x - radius, y - radius, radius, color);
				
		this.centerX = x;
		this.centerY = y;
				
		this.weight = weight;
		this.radius = radius;
		
		this.newMoviment(0, 0);
		
		this.isMoving = false;
		this.isKilled = false;
	}
	
	/**
	 * Retorna um ponto com as coordenadas do centro da bola.
	 * @return Ponto com as coordenadas da bola.
	 */
	public Point2D getPoint() {
		return new Point2D.Double(this.centerX, this.centerY);
	}
	
	/**
	 * Atualiza os parâmetros da bola, para o início de um movimento.
	 * @param speedy Velocidade inicial da bola.
	 * @param angle Angulo, em graus, da velocidade.
	 */
	public void newMoviment(double speedy, double angle) {
		this.angle = angle;
		this.speedy = speedy;
		
		this.time = 0.0;
		this.speedy0 = this.speedy;
				
		this.centerX0 = this.centerX;
		this.centerY0 = this.centerY;
		
		this.isMoving = (int)this.speedy != 0 ? true : false;
	}
	
	/**
	 * Incrementa o tempo do movimento da bola.
	 */
	public void increaseTime() {
		this.time += 0.07;
	}
	
	/**
	 * Move a bola, de acordo com o tempo atual dela.
	 */
    public void move() {    	
    	if(this.isMoving) {
    		this.speedy = this.speedy0 + (this.acceleration * time);
    		
    		this.centerX = this.centerX0 + (this.speedy0 * Math.cos(Math.toRadians(this.angle)) * this.time) + (0.5 * this.acceleration * Math.cos(Math.toRadians(this.angle)) * this.time * this.time);
    		this.centerY = this.centerY0 + (this.speedy0 * Math.sin(Math.toRadians(this.angle)) * this.time) + (0.5 * this.acceleration * Math.sin(Math.toRadians(this.angle)) * this.time * this.time);
    		    		
    		this.isMoving = this.speedy < 2.0 ? false : true;    		
    	}
    }
    
    /**
     * Atualiza a área desenhável da bola, com os parâmetros atuais.
     */
    public void modify() {
    	super.circle.setFrame((this.centerX - this.radius), (this.centerY - this.radius), (2 * this.radius), (2 * this.radius));
    }
    
    /**
     * Redesenha a bola, com as coordenadas atuais, na tela de jogo específicada.
     * @param window Tela onde o jogo acontece.
     */
    public void refresh(Interface window) {
    	if(this.isMoving) {
    		super.erase(window, Game.tableAreaColor);
        	this.modify();
        	super.draw(window);
    	}
    }
    
    /**
     * Verifica se houve intersecção entre uma bola e uma área retangular.
     * @param area Área a ser verificada.
     * @return Se houver intersecção, retorna 'true'. Em caso contrário, 'false'.
     */
    public boolean intersects(Area area) {
    	Ball arg0 = new Ball(this.centerX, this.centerY, this.radius, this.weight, this.color);
    	arg0.time = this.time + 2 * this.increaseTime;
    	arg0.angle = this.angle;
    	arg0.speedy = this.speedy;
    	arg0.speedy0 = this.speedy0;
    	arg0.centerX0 = this.centerX0;
    	arg0.centerY0 = this.centerY0;
    	arg0.isKilled = this.isKilled;
    	arg0.isMoving = this.isMoving;
    	
    	Area arg1 = area;
    	
    	arg0.move();
    	arg0.modify();
    	
    	return arg0.circle.intersects(arg1.toRectangle2D());
    }
    
    /**
     * Verifica se houve intersecção entre uma bola e um círculo.
     * @param circle Círculo a ser verificado.
     * @return Se houver intersecção, retorna 'true'. Em caso contrário, 'false'.
     */
    public boolean intersects(Circle circle) {
    	Ball arg0 = new Ball(this.centerX, this.centerY, this.radius, this.weight, this.color);    	
    	arg0.time = this.time + 2 * this.increaseTime;
    	arg0.angle = this.angle;
    	arg0.speedy = this.speedy;
    	arg0.speedy0 = this.speedy0;
    	arg0.centerX0 = this.centerX0;
    	arg0.centerY0 = this.centerY0;
    	arg0.isKilled = this.isKilled;
    	arg0.isMoving = this.isMoving;
    	
    	Circle arg1 = circle;
    	
    	arg0.move();
    	arg0.modify();
    	
    	return arg0.circle.intersects(arg1.toRectangle2D());
    }
    
    /**
     * Verifica se houve intersecção entre duas bolas.
     * @param ball Bola a ser verificada.
     * @return Se houver intersecção, retorna 'true'. Em caso contrário, 'false'.
     */
    public boolean intersects(Ball ball) {
    	if(this.equals(ball)) return false;
	    	
    	Ball arg0 = new Ball(this.centerX, this.centerY, this.radius, this.weight, this.color);    	
    	arg0.time = this.time + 2 * this.increaseTime;
    	arg0.angle = this.angle;
    	arg0.speedy = this.speedy;
    	arg0.speedy0 = this.speedy0;
    	arg0.centerX0 = this.centerX0;
    	arg0.centerY0 = this.centerY0;
    	arg0.isKilled = this.isKilled;
    	arg0.isMoving = this.isMoving;
    	
    	Ball arg1 = new Ball(ball.centerX, ball.centerY, ball.radius, ball.weight, ball.color);    	
    	arg1.time = ball.time + 2 * this.increaseTime;
    	arg1.angle = ball.angle;
    	arg1.speedy = ball.speedy;
    	arg1.speedy0 = ball.speedy0;
    	arg1.centerX0 = ball.centerX0;
    	arg1.centerY0 = ball.centerY0;
    	arg1.isKilled = ball.isKilled;
    	arg1.isMoving = ball.isMoving;
    	
    	arg0.move();
    	arg0.modify();
    	
    	arg1.move();
    	arg1.modify();
    	
    	return arg0.circle.intersects(arg1.toRectangle2D());
    }
    
    /**
     * Calcula a colisão entre a bola e a borda da mesa.
     * @param index Índice da borda a ser colidida.
     */
    public void collides(long index) {
    	double degree = (index % 2) == 0 ? 360 - this.angle : 180 - this.angle;
    	
    	this.newMoviment(this.speedy, degree);
    	this.modify();
    }
    
    /**
     * Calcula a colisão entre bolas.
     * @param ball Bola a ser colidida.
     */
    public void collides(Ball ball) {
	    if(this.isKilled || ball.isKilled) return;
    	
	    double collisionAngle = Math.toDegrees(Math.atan( Math.abs(this.centerY - ball.centerY) / Math.abs(this.centerX - ball.centerX)));
	    
	    double correctionAngle1 = 0;
	    double correctionAngle2 = 0;
	    
	    // Primeiro quadrante. -- Bugado
	    if(this.centerX > ball.centerX && this.centerY < ball.centerY) {
	    	if(collisionAngle < 40) {
	    		correctionAngle1 = 360;
	    		correctionAngle2 = 180;
	    	}
	    	else if(collisionAngle > 50) {
	    		correctionAngle1 = 180;
	    		correctionAngle2 = 360;
	    	}
	    }
	    
	    // Segundo quadrante.
	    if(this.centerX <= ball.centerX && this.centerY <= ball.centerY) {
	    	correctionAngle1 = 180;
	    	correctionAngle2 = 360;
	    }
	    
	    // Terceiro quadrante. -- Bugado
	    if(this.centerX < ball.centerX && this.centerY > ball.centerY) {
	    	if(collisionAngle < 40) {
	    		correctionAngle1 = 180;
	    		correctionAngle2 = 360;
	    	}
	    	else if(collisionAngle > 50) {
	    		correctionAngle1 = 360;
	    		correctionAngle2 = 180;
	    	}
	    }
	    
	    // Quarto quadrante.
	    if(this.centerX >= ball.centerX && this.centerY >= ball.centerY) {
	    	correctionAngle1 = 360;
	    	correctionAngle2 = 180;
	    }
	    
	    double v2t = ball.speedy * Math.sin(Math.toRadians(180 - ball.angle - collisionAngle));
	    
	    double v1t = this.speedy * Math.sin(Math.toRadians(this.angle - collisionAngle));
	    double v1n = this.speedy * Math.cos(Math.toRadians(this.angle - collisionAngle));
	    
	    double v1nf = v1n * (this.weight - ball.weight) / (this.weight + ball.weight);
	    double v2nf = v1n * (2 * this.weight) / (this.weight + ball.weight);
	    
	    double v1f = Math.pow(Math.pow(v1t, 2) + Math.pow(v1nf, 2), 0.5);
	    double av1f = correctionAngle1 + collisionAngle - Math.toDegrees(Math.atan(v1t / v1nf));
	    
	    double v2f = Math.pow(Math.pow(v2t, 2) + Math.pow(v2nf, 2), 0.5);
	    double av2f = correctionAngle2 + collisionAngle - Math.toDegrees(Math.atan(v2t / v2nf));
	    
	    ball.newMoviment(v2f, av2f);
	    ball.modify();

	    this.newMoviment(v1f, av1f);
	    this.modify();
    }
    
    /**
     * Atualiza os parâmetros da bola, quando esta colide com a caçapa.
     * @param window Tela onde o jogo acontece.
     */
    public void kill(Interface window) {
    	this.centerX = -this.radius;
    	this.centerY = -this.radius;
    	this.refresh(window);
    	this.isMoving = false;
    	this.isKilled = true;
    }
    
    /**
     * Retorna uma bola para a mesa, caso ela tenha caído dentro da caçapa.
     * @param window Tela onde o jogo acontece.
     * @param width Largura da mesa de jogo.
     * @param height Altura da mesa de jogo.
     */
    public void back(Interface window, double width, double height) {
    	this.centerX = width / 8;
    	this.centerY = height / 2;
    	this.isMoving = true;
    	this.refresh(window);
    	this.isMoving = false;
    	this.isKilled = false;
    }
}