package snooka;

import java.awt.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Class File - Classe usada para leitura e gravação de arquivos de configurações.
 * 
 * @author  Willians Schallemberger Schneider
 * @version  2.00 
 * @docRoot
 * 
 * Date: 22.11.2005
 */
public class File {
	/** Tempo do passo de simulação, lida do arquivo e usada na gravação de save. */    
	private double fileDelay;
    /** Raio da bola, lida do arquivo e usada na gravação de save. */
	private double fileRadius;
	
    /** Parâmetros da mesa, lidos do arquivo e usados na gravação de save. */
	private FileTable fileTable = null;
	/** Lista com os parâmetros de todas as bolas, lidos do arquivo e usados na gravação de save. */
    private List<FileBall> fileBall = new List<FileBall>();
    /** Lista com os parâmetros dos dois jogadores, lidos do arquivo e usados na gravação de save. */
	private List<FilePlayer> filePlayer = new List<FilePlayer>();
	
    /** Contador de bolas brancas encontradas no arquivo. */
	private int checkWhiteBall = 0;
	
	/**
	 * Lê um arquivo de configurações, do tipo 'new'.
	 * @param fileName Nome do arquivo a ser lido.
	 * @return 'true' caso o arquivo tenha sido lido corretamente e 'false' caso contrário.
	 */
	public boolean readNew(String fileName) {
		boolean result = readLoad(fileName);
		String[] parameters = {"player", "player", "-1"};
		this.filePlayer = new List<FilePlayer>();
		this.filePlayer.insert(new FilePlayer(parameters));
		this.filePlayer.insert(new FilePlayer(parameters));
		return result;
	}
	
	/**
	 * Lê um arquivo de configurações, do tipo 'save'.
	 * @param fileName Nome do arquivo a ser lido.
	 * @return 'true' caso o arquivo tenha sido lido corretamente e 'false' caso contrário.
	 */
	public boolean readLoad(String fileName) {
		
		FileReader file = null;
		BufferedReader buffer = null;		
		
		String fileLine = new String();
		String[] paramLine = new String[5];		
		String[] parameters = {"player", "player", "-1"};
		
		try {
			file = new FileReader(fileName);
		} 
		catch (FileNotFoundException e) {
			 JOptionPane.showMessageDialog(null, "Arquivo de configurações: '" + fileName + "' não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		buffer = new BufferedReader(file);
				
		try {
			do {
				fileLine = buffer.readLine();
				
				if(fileLine == null) break;
				
				paramLine = fileLine.split("\\s+");
				
				for(int i = 0 ; i < paramLine.length ; i++) paramLine[i] = paramLine[i].trim();
				
				if(paramLine[0].toLowerCase().equals("mesa") && paramLine.length == 3) {
					this.fileTable = new FileTable(paramLine);
				}
				
				else if(paramLine[0].toLowerCase().equals("jogador") && paramLine.length == 3) {
					this.filePlayer.insert(new FilePlayer(paramLine));
				}
				
				else if(paramLine[0].toLowerCase().equals("bola") && paramLine.length == 5) {
					this.fileBall.insert(new FileBall(paramLine));
				}
				
				else if(paramLine[0].toLowerCase().equals("passo_simulacao") && paramLine.length == 2) {
					this.fileDelay = Double.parseDouble(paramLine[1]);
				}
				
				else if(paramLine[0].toLowerCase().equals("raio") && paramLine.length == 2) {
					this.fileRadius = Double.parseDouble(paramLine[1]);
				}
				
				// Parâmetro desconhecido.
				else if(!paramLine[0].toLowerCase().equals("")) {
					JOptionPane.showMessageDialog(null, "Arquivo de configurações: '" + fileName + "'; inválido." + "\n" + "Parâmetro desconhecido." + "\n" + "Não foi possível carregar o jogo.", "Erro", JOptionPane.ERROR_MESSAGE);
					return false;
				}				
			} while (true);
			
			if(this.checkWhiteBall < 1) {
				JOptionPane.showMessageDialog(null, "Arquivo de configurações: '" + fileName + "'; inválido." + "\n" + "Não foi possível encontrar nenhuma bola branca." + "\n" + "Não será possível carregar o jogo.", "Erro", JOptionPane.ERROR_MESSAGE);
				return false;
			}			
			else if(this.checkWhiteBall > 1) {
				JOptionPane.showMessageDialog(null, "Arquivo de configurações: '" + fileName + "'; inválido." + "\n" + "Foram encontradas duas ou mais bolas brancas no arquivo." + "\n" + "Não será possível carregar o jogo.", "Erro", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} 
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao ler o arquivo de configurações: '" + fileName + "'.", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(this.filePlayer.size != 2) {
			this.filePlayer = new List<FilePlayer>();
			this.filePlayer.insert(new FilePlayer(parameters));
			this.filePlayer.insert(new FilePlayer(parameters));
		}
		
		return true;
	}
	
	/**
	 * Cria um arquivo de configurações do tipo 'save', com base nos parâmetros da classe.
	 * @param fileName Nome do arquivo a ser criado.
	 */
	public void writeSave(String fileName) {
		FileWriter file = null;
		BufferedWriter buffer = null;		
		
		String fileLine = new String();
		
		try {
			file = new FileWriter(fileName);
		} 
		catch (IOException e) {
			 JOptionPane.showMessageDialog(null, "Não foi possível criar o arquivo de configurações: '" + fileName + "'. Verifique se você possui privilégios para criar arquivos nesta pasta.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		buffer = new BufferedWriter(file);
		
		try {
			
			fileLine = "mesa " + this.fileTable.width + " " + this.fileTable.height;			
			buffer.write(fileLine);			
			buffer.newLine();
			
			fileLine = "raio " + this.fileRadius;
			buffer.write(fileLine);
			buffer.newLine();
			
			fileLine = "passo_simulacao " + this.fileDelay;
			buffer.write(fileLine);
			buffer.newLine();
			
			for(int i = 0 ; i < this.filePlayer.size ; i++) {
				fileLine = "jogador " + this.filePlayer.getElement(i).name + " " + this.filePlayer.getElement(i).points;
				buffer.write(fileLine);
				buffer.newLine();
			}
			
			for(int i = 0 ; i < this.fileBall.size ; i++) {
				fileLine = "bola " + this.fileBall.getElement(i).toString(this.fileBall.getElement(i).color) + " " + this.fileBall.getElement(i).x + " " + this.fileBall.getElement(i).y + " " + this.fileBall.getElement(i).weight;
				buffer.write(fileLine);
				buffer.newLine();
			}
			
			buffer.flush();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível escrever no arquivo de configurações: '" + fileName + "'.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Retorna o valor do passo da simulação, lido de um arquivo de configurações.
	 * @return Valor do passo da simulação.
	 */
	public double getFileDelay() {
		return this.fileDelay;
	}
	
	/**
	 * Retorna o valor do raio das bolas, lido de uma arquivo de configurações.
	 * @return Valor do raio das bolas.
	 */
	public double getFileRadius() {
		return this.fileRadius;
	}
	
	/**
	 * Retorna um objeto com os parâmetros da mesa, lidos de um arquivo de configurações.
	 * @return Objeto com os parâmetros.
	 */
	public FileTable getFileTable() {
		return this.fileTable;
	}
	
	/**
	 * Retorna uma lista com os parâmetros de todas as bolas, lidos de um arquivo de configurações.
	 * @return Lista com os parâmetros.
	 */
	public List<FileBall> getFileBalls() {
		return this.fileBall;
	}
	
	/**
	 * Retorna uma lista com os parâmetros dos dois jogadores, lidos de um arquivo de configurações.
	 * @return Lista com os parâmetrod dos dois jogadores.
	 */
	public List<FilePlayer> getFilePlayers() {
		return this.filePlayer;
	}
	
	/**
	 * Class FileTable - Classe usada para guardar os parâmetros da mesa, lidos de um arquivo.
	 * 
	 * @author  Willians Schallemberger Schneider
	 * @version  2.00 
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 */
	class FileTable {
        /** Largura da mesa, lida do arquivo e usada na gravação de save. */
		private double width;
        /** Altura da mesa, lida do arquivo e usada na gravação de save. */
		private double height;
		
		/**
		 * Construtor da classe FileTable.
		 * @param fileLine Linha do arquivo de configurações, onde os parâmetros da mesa estão.
		 */
		private FileTable(String[] fileLine) {
			this.width = Double.parseDouble(fileLine[1]);
			this.height = Double.parseDouble(fileLine[2]);
		}
		
		/**
		 * Retorna a largura da mesa, lido de um arquivo.
		 * @return Largura da mesa.
		 */
		double getWidth() {
			return this.width;
		}
		
		/**
		 * Retorna a altura da mesa, lido de um arquivo.
		 * @return Altura da mesa.
		 */
		double getHeight() {
			return this.height;
		}
	}
	
	/**
	 * Class FilePlayer - Classe usada para guardar os parâmetros dos jogadores, lidos de um arquivo.
	 * 
	 * @author  Willians Schallemberger Schneider
	 * @version  2.00 
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 */
	class FilePlayer {
        /** Nome do jogador, lido do arquivo e usado na gravação de save. */
		private String name;
        /** Pontuação do joagdor, lida do arquivo e usada na gravação de save. */
		private int points;
		
		/**
		 * Construtor da classe FilePlayer.
		 * @param fileLine Linha do arquivo de configurações, onde os parâmetros do jogador estão.
		 */
		private FilePlayer(String[] fileLine) {
			this.name = fileLine[1];
			this.points = Integer.parseInt(fileLine[2]);
		}
		
		/**
		 * Retorna o nome do jogador, lido de um arquivo.
		 * @return Nome do jogador.
		 */
		String getName() {
			return this.name;
		}
		
		/**
		 * Retorna a pontuação do jogador, lida de um arquivo. 
		 * @return Pontuação do jogador.
		 */
		int getPoints() {
			return this.points;
		}
		
		/**
		 * Atribui um novo nome para o jogador, para ser gravado no arquivo.
		 * @param name Novo nome a ser atribuído.
		 */
		void setName(String name) {
			this.name = name;
		}
		
		/**
		 * Atribui uma nova pontuação para o jogador, para ser gravado no arquivo.
		 * @param points Novo valor de pontos para o jogador.
		 */
		void setPoints(int points) {
			this.points = points;
		}
	}
	
	/**
	 * Class FileBall - Classe usada para guardar os parâmetros de uma bola, lidos de um arquivo.
	 * 
	 * @author  Willians Schallemberger Schneider
	 * @version  2.00 
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 */
	class FileBall{
		/** Coordenada 'x' da bola, lida do arquivo e usada para gravação de save. */
        private double x;
		/** Coordenada 'y' da bola, lida do arquivo e usada para gravação de save. */
        private double y;
		/** Massa da bola, lida do arquivo e usada para gravação de save. */
        private double weight;
		/** Raio da bola, lida do arquivo e usada para gravação de save. */
        private double radius;
		/** Cor da bola, lida do arquivo e usada para gravação de save. */
        private Color color;
		
		/**
		 * Construtor da classe FileBall.
		 * @param fileLine Linha do arquivo de configurações, onde os parâmetros de uma bola estão.
		 */
		private FileBall(String[] fileLine) {
			this.radius = fileRadius;
			this.color = this.toColor(fileLine[1]);
			this.x = Double.parseDouble(fileLine[2]);
			this.y = Double.parseDouble(fileLine[3]);
			this.weight = Double.parseDouble(fileLine[4]);	
			if(this.color.equals(Color.WHITE)) checkWhiteBall++;
		}
		
		/**
		 * Converte uma string com um nome de cor, para uma cor. 
		 * @param color String com o nome da cor.
		 * @return A cor convertida.
		 */
		private Color toColor(String color) {
			if(color.toLowerCase().equals("azul")) return Color.BLUE;
			if(color.toLowerCase().equals("rosa")) return Color.PINK;
			if(color.toLowerCase().equals("preta")) return Color.BLACK;
			if(color.toLowerCase().equals("verde")) return Color.GREEN;
			if(color.toLowerCase().equals("vermelha")) return Color.RED;
			if(color.toLowerCase().equals("branca")) return Color.WHITE;
			if(color.toLowerCase().equals("laranja")) return Color.ORANGE;
			if(color.toLowerCase().equals("amarela")) return Color.YELLOW;
			return Color.GRAY;
		}
		
		/**
		 * Converte uma cor para o seu nome correspondente, em String.
		 * @param color Cor a ser convertida.
		 * @return String com o nome da cor.
		 */
		private String toString(Color color) {
			if(color.equals(Color.BLUE)) return "azul";
			if(color.equals(Color.PINK)) return "rosa";
			if(color.equals(Color.BLACK)) return "preta";
			if(color.equals(Color.GREEN)) return "verde";
			if(color.equals(Color.RED)) return "vermelha";
			if(color.equals(Color.WHITE)) return "branca";
			if(color.equals(Color.ORANGE)) return "laranja";
			if(color.equals(Color.YELLOW)) return "amarela";
			return "cinza";
		}
		
		/**
		 * Retorna a coordenada 'x' da bola, lida de um arquivo.
		 * @return Coordenada 'x' da bola.
		 */
		double getX() {
			return this.x;			
		}
		
		/**
		 * Retorna a coordenada 'y' da bola, lida de um arquivo.
		 * @return Coordenada 'y' da bola.
		 */
		double getY() {
			return this.y;
		}
		
		/**
		 * Retorna a massa da bola, lida de um arquivo.
		 * @return Massa da bola.
		 */
		double getWeight() {
			return this.weight;
		}
		
		/**
		 * Retorna o raio da bola, lido de um arquivo.
		 * @return Raio da bola.
		 */
		double getRadius() {
			return this.radius;
		}
		
		/**
		 * Retorna a cor da bola, lida de um arquivo.
		 * @return Cor da bola.
		 */
		Color getColor() {
			return this.color;
		}
		
		/**
		 * Atribui um novo valor para a coordenada 'x' da bola, para ser gravado no arquivo.
		 * @param x Novo valor da coordenada 'x'.
		 */
		void setX(double x) {
			this.x = x;
		}
		
		/**
		 * Atribui um novo valor para a coordenada 'y' da bola, para ser gravado no arquivo.
		 * @param y Novo valor da coordenada 'y'.
		 */
		void setY(double y) {
			this.y = y;
		}
	}
}
