package processresult;

import java.util.ArrayList;
import java.util.Hashtable;

import cis.nlp.io.ReadFile;
import cis.nlp.io.WriteFile;

public class FilteringCandidateOnDictionary {
	private Hashtable<String, Boolean> vnDictionary = new Hashtable<>();
	private Hashtable<String, Boolean> medicalDictionay = new Hashtable<>();
	private ArrayList<String> listCandidate = new ArrayList<>();
	private ReadFile rf;
	private WriteFile wf;
	private String pathCandidate;
	private String pathVNDictionary = "tdvn.txt";
	private String pathMedicalDictionary = "tdyh.txt";
	public FilteringCandidateOnDictionary(String fileName) {
		setFileName(fileName);
		buildListCandidate();
		buildVnDictionay();
		buildMedicalDictionay();
	}
	private void buildVnDictionay(){
		rf = new ReadFile();
		rf.open(pathVNDictionary);
		ArrayList<String> listVNDictionary = rf.read();
		rf.close();
		for (String token : listVNDictionary) {
			String[] elems = token.split(" ");
			if(elems.length > 1){
				vnDictionary.put(token, true);
			}
		}
	}
	
	private void buildMedicalDictionay(){
		rf = new ReadFile();
		rf.open(pathMedicalDictionary);
		ArrayList<String> listMedicalDictionay = rf.read();
		rf.close();
		for (String token : listMedicalDictionay) {
			String[] elems = token.split(" ");
			if(elems.length > 1){
				medicalDictionay.put(token, true);
			}
		}
	}
	
	public void buildListCandidate(){
		rf = new ReadFile();
		rf.open(pathCandidate);
		listCandidate = rf.read();
		rf.close();
	}
	
	public void filterCandidateOnDictionary(){
		ArrayList<String> candsOnDictionary = new ArrayList<>();
		for (String cand : listCandidate) {
			String[] elems = cand.split(",");
			if(vnDictionary.get(elems[0]) != null || medicalDictionay.get(elems[0]) != null){
				candsOnDictionary.add(cand);
			}
		}
		for (String string : candsOnDictionary) {
			listCandidate.remove(string);
		}
	}
	
	public void writeNewResult(){
		wf = new WriteFile();
		wf.open(pathCandidate);
		for (String string : listCandidate) {
			wf.write(string + "\n");
		}
		wf.close();
	}
	
	public void setFileName(String fileName){
		this.pathCandidate = fileName;
	}
	
	public static void main(String[] args) {
		FilteringCandidateOnDictionary filter = new FilteringCandidateOnDictionary("/home/zic/Desktop/NLP_RESULT2/cands-nontokenized/cands-bi.txt");
		filter.filterCandidateOnDictionary();
		filter.writeNewResult();
		
		filter = new FilteringCandidateOnDictionary("/home/zic/Desktop/NLP_RESULT2/cands-nontokenized/cands-tri.txt");
		filter.filterCandidateOnDictionary();
		filter.writeNewResult();
	}
}