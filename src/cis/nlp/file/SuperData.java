package cis.nlp.file;

import java.util.ArrayList;

public class SuperData {
	private ArrayList<String> fileNames = null;
	private int numberOccurence = 0;
	public ArrayList<String> getFileNames() {
		return fileNames;
	}
	public void setFileNames(ArrayList<String> fileNames) {
		this.fileNames = fileNames;
	}
	public int getNumberOccurence() {
		return numberOccurence;
	}
	public void setNumberOccurence(int numberOccurence) {
		this.numberOccurence = numberOccurence;
	}
}
