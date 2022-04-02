package snooka;

/**
 * Class List - Classe usada na manipulação de listas ligadas.
 * 
 * @author  Willians Schallemberger Schneider
 * @version  2.00 
 * @param  <T>  Tipo de objeto que será inserido na lista. 
 * @docRoot  
 * 
 * Date: 22.11.2005
 */
public class List<T> {
    /** Tamanho atual da lista. */
	public long size;	
	/** Atalho para algum elemento da lista. Definido pelo usuário. */
	public T user;
	
	/** Cabeça da lista. */
    private Node<T> head; // Cabeça da lista.
	/** Referência para o último elemento da lista. */
    private Node<T> last; // Último elemento da lista.
	
	/**
	 * Construtor da classe List.
	 */
	public List() {
		head = new Node<T>(null);
		last = head;
        size = 0;
	}
	
	/**
	 * Insere um elemento no fim da lista.
	 * @param element Elemento a ser inserido.
	 */
	public void insert(T element) {
		this.last.next = new Node<T>(element);
		this.last = this.last.next;
		this.size++;
	}
    
	/**
	 * Retorna o elemento do índice específicado.
	 * @param index Índice do elemento. 
	 * @return Elemento do índice específicado.
	 */
    public T getElement(long index) {
        Node<T> list = this.head.next;
        for(int aux = 0 ; aux < index && list != null ; aux++) list = list.next;
        if(list == null) return this.last.element;
        return list.element;
    }
	
    /**
	 * Class Node - Classe que cria um nó, guardando um elemento e uma referência para o próximo.
	 * 
	 * @author  Willians Schallemberger Schneider
	 * @version  2.00
	 * @param  <T>  Tipo de objeto que será inserido na nó.  
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 */
	@SuppressWarnings("hiding")
	private class Node<T> {
		/** Elemento a ser guardado na lista. */
		private T element;
		/** Referência para o próximo nó da lista. */
        private Node<T> next;
		
        /**
         * Construtor da classe Node.
         * @param element Elemento a ser inserido.
         */
		private Node(T element) {
			this.element = element;
			this.next = null;
		}
	}
}
