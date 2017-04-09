package cis.nlp.model;

import java.util.ArrayList;

import cis.nlp.io.DirectoryContents;
import cis.nlp.io.DirectorySavedResult;

public class LineSplit {
	private String target = DirectorySavedResult.getDirectoryToSaveResult();
	private int isTokenize;
	public void setTypeCorpus(int isTokenize){
		this.isTokenize = isTokenize;
	}
	public void setOuput(){
		if(isTokenize == 0){
			target += "tokenized-split";
		} else {
			target += "nontokenized-split";
		}
	}
	public void split(ArrayList<String> pathname){
		setOuput();
		for (String path : pathname) {
			System.out.println(path);
			String[] oldName = path.split("/");
			cis.nlp.count.LineSplit ls = new cis.nlp.count.LineSplit();
			ls.docFileVaTachCau(path, target + "/" + oldName[oldName.length - 1]);
		}
	}
	public void process(String fileInput) {
		
		ArrayList<String> listPath = DirectoryContents.getFileTxt(fileInput);
		split(listPath);
		System.out.println("Split ....... Ok");
	}
}
