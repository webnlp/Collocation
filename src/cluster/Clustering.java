package cluster;

import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.file.SuperData;
import cis.nlp.io.DirectorySavedResult;
import cis.nlp.model.LoadNgram;

public class Clustering {
	private LoadNgram load;
	private int isTokenized = 0; //0: non-tokenize ; 1: tokenize;
	private String folderOutput = DirectorySavedResult.getDirectoryToSaveResult();;
	public Clustering(int isTokenized) {
		this.isTokenized = isTokenized;
		setFolderOutput();
		init();
	}
	private void setFolderOutput(){
		if(isTokenized == 1){
			folderOutput += "cluster-tokenized/";
		} else {
			folderOutput += "cluster-nontokenized/";
		}
	}
	private void init(){
		load = new LoadNgram();
		load.setType(isTokenized == 1);
	}
	public void clustering(){
		Cluster bigramCluster = clusterNgram(load.loadNgramAsUnigram("bigram").getOneCount());
		bigramCluster.write(folderOutput + "bi-cluster.txt");
		System.out.println("bigram cluster success");
		Cluster trigramCluster = clusterNgram(load.loadNgramAsUnigram("trigram").getOneCount());
		trigramCluster.write(folderOutput + "tri-cluster.txt");
		
		Cluster fourgramCluser = clusterNgram(load.loadNgramAsUnigram("fourgram").getOneCount());
		fourgramCluser.write(folderOutput + "four-cluster.txt");
	}
	public Cluster clusterNgram(Hashtable<String, SuperData> ngramAsUnigram){
		Cluster cl = new Cluster();
		Enumeration<String> ens = ngramAsUnigram.keys();
		while(ens.hasMoreElements()){
			String ngram = ens.nextElement();
			SuperData sd = ngramAsUnigram.get(ngram);
			Hashtable<String, Integer> occurOnFile = sd.getOccurOnFile();
			Enumeration<String> files = occurOnFile.keys();
			while(files.hasMoreElements()){
				String filename = files.nextElement();
				Integer filenameInInt = Integer.parseInt(filename);
				cl.add(filenameInInt, ngram);
			}
		}
		return cl;
	}
	
}
