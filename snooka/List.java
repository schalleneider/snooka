package snooka;

/**
 * Class List - Classe usada na manipula��o de listas ligadas.
 * 
 * @author  Willians Schallemberger Schneider
 * @version  2.00 
 * @param  <T>  Tipo de objeto que ser� inserido na lista. 
 * @docRoot  
 * 
 * Date: 22.11.2005
 */
public class List<T> {
    /** Tamanho atual da lista. */
	public long size;	
	/** Atalho para algum elemento da lista. Definido pelo usu�rio. */
	public T user;
	
	/** Cabe�a da lista. */
    private Node<T> head; // Cabe�a da lista.
	/** Refer�ncia para o �ltimo elemento da lista. */
    private Node<T> last; // �ltimo elemento da lista.
	
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
	 * Retorna o elemento do �ndice espec�ficado.
	 * @param index �ndice do elemento. 
	 * @return Elemento do �ndice espec�ficado.
	 */
    public T getElement(long index) {
        Node<T> list = this.head.next;
        for(int aux = 0 ; aux < index && list != null ; aux++) list = list.next;
        if(list == null) return this.last.element;
        return list.element;
    }
	
    /**
	 * Class Node - Classe que cria um n�, guardando um elemento e uma refer�ncia para o pr�ximo.
	 * 
	 * @author  Willians Schallemberger Schneider
	 * @version  2.00
	 * @param  <T>  Tipo de objeto que ser� inserido na n�.  
	 * @docRoot
	 * 
	 * Date: 22.11.2005
	 */
	@SuppressWarnings("hiding")
	private class Node<T> {
		/** Elemento a ser guardado na lista. */
		private T element;
		/** Refer�ncia para o pr�ximo n� da lista. */
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
