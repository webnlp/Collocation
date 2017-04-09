package cis.nlp.file;

import java.util.Enumeration;
import java.util.Hashtable;

public class SuperData {
	private Hashtable<String, Integer> occurOnFile = new Hashtable<>();
	private int numberOccurence = 0;
	public SuperData(String filePath, int numberOccurence) {
		occurOnFile.put(getFileName(filePath), 1);
		this.numberOccurence = numberOccurence;
	}
	public SuperData(String superDataInString) {
		String[] elems = superDataInString.split(",");
		numberOccurence = Integer.parseInt(elems[0]);
		for(int i = 1; i < elems.length; i++){
			String e[] = elems[i].split(":");
			occurOnFile.put(e[0], Integer.parseInt(e[1]));
		}
	}
	public String getFileNames() {
		String allFilesName = "";
		Enumeration<String> files = occurOnFile.keys();
		while(files.hasMoreElements()){
			String file = files.nextElement();
			allFilesName += file + ":" + occurOnFile.get(file) + ",";
		}
		return allFilesName;
	}
	public void setFileNames(String filePath) {
		String fileName = getFileName(filePath);
		if(occurOnFile.get(fileName) == null){
			occurOnFile.put(fileName, 1);
		} else {
			occurOnFile.put(fileName, occurOnFile.get(fileName) + 1);
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
