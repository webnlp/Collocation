package cis.nlp.file;

import java.util.ArrayList;

public class Document{
	private String id;
	private ArrayList<String> content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<String> getContent() {
		return content;
	}
	public void setContent(ArrayList<String> content) {
		this.content = content;
	}
	
}
