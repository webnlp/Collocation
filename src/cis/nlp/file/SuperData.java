package cis.nlp.file;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

public class SuperData {
	private Hashtable<String, Integer> occurOnFile = new Hashtable<>();
	private Hashtable<Integer, StringBuilder> reverseOccurOnFile = new Hashtable<>();
	private int numberOccurence = 0;
	public SuperData(String filePath, int numberOccurence) {
		occurOnFile.put(getFileName(filePath), 1);
		this.numberOccurence = numberOccurence;
	}
	public SuperData(String superDataInString) {
		String[] elems = superDataInString.split("\\|");
		
		numberOccurence = Integer.parseInt(elems[0]);
		for(int i = 1; i < elems.length; i++){
			if(elems[i].contains(":")){
				String e[] = elems[i].split(":");
				String listFileNameInString[] = e[0].split(",");
				for (String fileName : listFileNameInString) {
					try {
						occurOnFile.put(fileName, Integer.parseInt(e[1]));
					} catch(Exception ee){
						JOptionPane.showMessageDialog(null, superDataInString);
					}
				}
			}
		}
	}
	public void setReverseOccurOnFile(){
		Enumeration<String> fileNames = occurOnFile.keys();
		while(fileNames.hasMoreElements()){
			String fileName = fileNames.nextElement();
			Integer numberOfOccurence = occurOnFile.get(fileName);
			if(reverseOccurOnFile.get(numberOfOccurence) == null){
				reverseOccurOnFile.put(numberOfOccurence, new StringBuilder(fileName));
			} else {
				StringBuilder listFileNameInString = reverseOccurOnFile.get(numberOfOccurence);
				listFileNameInString.append(",");
				listFileNameInString.append(fileName);
				reverseOccurOnFile.put(numberOfOccurence, listFileNameInString);
				
			}
		}
	}
	public String getResultSuperData() {
		String allFilesName = "";
		Enumeration<Integer> listNumberOccur = reverseOccurOnFile.keys();
		while(listNumberOccur.hasMoreElements()){
			Integer number = listNumberOccur.nextElement();
			allFilesName += reverseOccurOnFile.get(number) + ":" + number + "|";
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
	public Hashtable<String, Integer> getOccurOnFile(){
		return occurOnFile;
	}
	@Override
	public String toString() {
		setReverseOccurOnFile();
		return numberOccurence + "|" + getResultSuperData();
	}
	
}
