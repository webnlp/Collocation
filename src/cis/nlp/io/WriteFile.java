package cis.nlp.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cis.nlp.calculate.Candidate;

/**
 * @author Do Quang Dat k59 HUS_CIS
 * Create a BufferWriter for write result
 */
public class WriteFile {
	private BufferedWriter bw;
	public WriteFile() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Open a bufferwriter to write out put
	 * @param fileName : path of file output
	 */
	public void open(String fileName){
		try {
			bw = new BufferedWriter(new FileWriter(new File(fileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLines(ArrayList<String> lines){
		for(String aline : lines){
			aline = aline.trim();
			aline = aline.replaceAll("//s+", " ");
			String quotation = String.valueOf('"');
			aline = aline.replaceAll(quotation, "");
			if(aline.length() != 0){
				try {
					bw.write(aline + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	public void writeCandidates(ArrayList<Candidate> cands, long totalFrequency){
		String result = totalFrequency + "\n";
		for (int i = 0; i < cands.size(); i++) {
			result += cands.get(i).toString() + "\n";
		}
		try {
			bw.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeHashTable(String string){
		try {
			bw.write(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
