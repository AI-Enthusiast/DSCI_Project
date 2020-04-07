package GrandCentral;

import java.util.ArrayList;
import java.util.List;

import entityTypes.Entity;

public class LLNodeEntity {
	LLNodeEntity prev = null;
	Entity element;
	LLNodeEntity next = null;
	public LLNodeEntity(LLNodeEntity prev,Entity element,LLNodeEntity next){
		this.prev = prev;
		this.element = element;
		this.next = next;
	}
	public LLNodeEntity(LLNodeEntity prev,Entity element){
		this.prev = prev;
		this.element = element;
	}
	public LLNodeEntity(Entity element) {
		this.element = element;
	}
	
}
