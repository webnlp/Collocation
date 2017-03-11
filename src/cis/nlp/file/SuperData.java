package cis.nlp.file;

import java.util.ArrayList;

public class SuperData {
	private ArrayList<String> fileNames = new ArrayList<>();
	private int numberOccurence = 0;
	public SuperData(String filePath, int numberOccurence) {
		fileNames.add(getFileName(filePath));
		this.numberOccurence = numberOccurence;
	}
	public SuperData(String superDataInString) {
		String[] elems = superDataInString.split(",");
		numberOccurence = Integer.parseInt(elems[0]);
		for(int i = 1; i < elems.length; i++){
			fileNames.add(elems[i]);
		}
	}
	public String getFileNames() {
		String allFilesName = "";
		for (String name : fileNames) {
			allFilesName += name + ",";
		}
		return allFilesName;
	}
	public void setFileNames(String filePath) {
		String fileName = getFileName(filePath);
		if(!fileNames.contains(fileName)){
			fileNames.add(fileName);
		}
	}
	public int getNumberOccurence() {
		return numberOccurence;
	}
	public void setNumberOccurence(int numberOccurence) {
		this.numberOccurence = numberOccurence;
	}
	public String getFileName(String pathToFile){
		String[] elems = pathToFile.split("/");
		return elems[elems.length - 1];
	}
	@Override
	public String toString() {
		return numberOccurence + "," + getFileNames();
	}
	
}
