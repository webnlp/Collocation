package cis.nlp.model;

import java.util.ArrayList;

import cis.nlp.io.DirectoryContents;
import vn.hus.nlp.tokenizer.VietTokenizer;

public class VNTokenizer {
	private static final String Result = "/home/zic/Desktop/NLP_RESULT/vntokenizer";
	
	private void vntokenizer(ArrayList<String> listPathtxt){
		int i = getEndNumberFile();
		for (String path : listPathtxt) {
			VietTokenizer vt = new VietTokenizer();
			vt.tokenize(path, Result +"/"+ i + ".txt");
			i++;
		}
	}
	private int getEndNumberFile(){
		ArrayList<String> filepath = DirectoryContents.getFileTxt(Result);
		return filepath.size();
	}
	public void tokenize(String folderpath) {
		System.out.println(folderpath);
		ArrayList<String> filepath = DirectoryContents.getFileTxt(folderpath);
		vntokenizer(filepath);
	}
}
