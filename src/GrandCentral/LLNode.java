package GrandCentral;

import java.util.ArrayList;
import java.util.List;

public class LLNode<T> {
	LLNode<T>prev = null;
	T element;
	LLNode<T>next = null;
	List<String>children = new ArrayList<String>();
	public LLNode(LLNode<T>prev,T element,LLNode<T>next){
		this.prev = prev;
		this.element = element;
		this.next = next;
	}
	public LLNode(LLNode<T>prev,T element){
		this.prev = prev;
		this.element = element;
	}
	public LLNode(T element) {
		this.element = element;
	}
	
}
