package cis.nlp.file;

import java.util.ArrayList;

import cis.nlp.io.EncodeFileName;

public class Document{
	private String id;
	private ArrayList<String> content;
	
	public Document(String id, ArrayList<String> content) {
		this.id = id;
		this.content = content;
	}
	public Document() {
	}
	public String getId() {
		return EncodeFileName.getFileEncode(id);
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
