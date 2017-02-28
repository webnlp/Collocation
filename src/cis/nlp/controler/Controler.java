package cis.nlp.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import cis.nlp.calculate.Candidate;
import cis.nlp.count.CountTriGram;
import cis.nlp.count.CountUnigram;
import cis.nlp.io.WriteFile;
import cis.nlp.model.AnalyzeCandsCount;
import cis.nlp.model.Collocations;
import cis.nlp.model.CountNgram;
import cis.nlp.model.LineSplit;
import cis.nlp.model.LoadNgram;
import cis.nlp.model.VNTokenizer;
import cis.nlp.model.WriteHistory;
import cis.nlp.view.MainView;
import cis.nlp.view.SelectFolderCount;
import cis.nlp.view.ViewCollocation;

public class Controler {
	private VNTokenizer tokenize;
	private LineSplit split;
	private CountNgram count;
	private WriteHistory history;
	private MainView mainView;
	private DateFormat dateFormat;
	private Collocations collocations;
	public Controler() {
		mainView = new MainView();
		mainView.setVisible(true);
		
		tokenize = new VNTokenizer();
		
		split = new LineSplit();
		
		count = new CountNgram();
		
		history = new WriteHistory();
		
		GetFolderRaw folderRaw = new GetFolderRaw();
		mainView.addActionFolderRaw(folderRaw);
		
		GetFolderWordTokenized folderWordTokenized = new GetFolderWordTokenized();
		mainView.addActionTokenized(folderWordTokenized);
		
		WordTokenize tokenize = new WordTokenize();
		mainView.addActionTokenize(tokenize);
		
		Preprocess pp = new Preprocess();
		mainView.addActionPreprocess(pp);
		
		Count count = new Count();
		mainView.addActionCountNgram(count);
		
		WriteCurrentHistory currentHistory = new WriteCurrentHistory();
		mainView.addActionWriteHistory(currentHistory);
		
		GetCandidates getCandidates = new GetCandidates();
		mainView.addActionGetCandsCount(getCandidates);
		
		ShowCollocations showCollocations = new ShowCollocations();
		mainView.addActionCollocation(showCollocations);
	}
	class GetFolderRaw implements ActionListener{

		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent e) {
			String path = "";
			JFileChooser choose = new JFileChooser();
			choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int k = choose.showOpenDialog(null);
			
			if(k == choose.APPROVE_OPTION){
				path = choose.getCurrentDirectory().getPath();
				mainView.getFolderRaw().setText(path);
			}
		}
		
	}
	
	class GetFolderWordTokenized implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = "";
			JFileChooser choose = new JFileChooser();
			choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			choose.showOpenDialog(null);
			
			path = choose.getSelectedFile().getAbsolutePath();
			mainView.getFolderWordTokenize().setText(path);
		}
		
	}
	class WordTokenize implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String folderRawPath = mainView.getFolderRaw().getText();
			String begin = getTime();
			double sizeMbytes = Math.round(sizeInMb(folderRawPath));
			tokenize.tokenize(folderRawPath);
			String end = getTime();
			
			mainView.getTextArea().append("Tokenize " + sizeMbytes + "Mb" + "\n begin " + begin + "\n end " + end +"\n\n"); 
		}
		
	}
	class Preprocess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("here");
			int isTokenized = JOptionPane.showConfirmDialog(null, "Did the corpus tokenized");
			String begin = getTime();
			String path = mainView.getFolderWordTokenize().getText();
			System.out.println(path);
			double sizeMbytes = sizeInMb(path);
			split.setTypeCorpus(isTokenized);
			split.process(path);
			String end = getTime();
			MainView.getTextArea().append("Line split " + sizeMbytes + "Mb" + "\n begin " + begin + "\n end " + end +"\n\n");
		}
		
	}
	class Count implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ControlerFolderCountSelected controlerFolderCountSelected = new ControlerFolderCountSelected();
			
		}
		
	}
	class GetCandidates implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			AnalyzeCandsCount candsCount;
			int isTokenized = JOptionPane.showConfirmDialog(null, "Did the corpus tokenize??");
			String begin = getTime();
			if(isTokenized == 0){
				candsCount = new AnalyzeCandsCount(true);
				ArrayList<Candidate> candsBigram = candsCount.getAnalyzeBigramCount();
				ArrayList<Candidate> candsTrigram = candsCount.getAnalyzeTrigramCount();
				System.out.println(candsBigram.get(0));
				WriteFile wf = new WriteFile();
				wf.open("NLP_RESULT/cands-tokenized/cands-bi.txt");
				wf.writeCandidates(candsBigram, candsCount.getLoadNgram().getTotalFrequencyBigram());
				wf.close();
				
				wf.open("NLP_RESULT/cands-tokenized/cands-tri.txt");
				wf.writeCandidates(candsTrigram, candsCount.getLoadNgram().getTotalFrequencyTrigram());
				wf.close();
				String end = getTime();
				mainView.getTextArea().append("Get candidates from tokenized corpus: \n"
						+ "size in MB: " + sizeInMb("NLP_RESULT/tokenized") + "\n"
						+ "begin: " + begin + "\n" + "end: " + end);
			} else if(isTokenized == 1){
				candsCount = new AnalyzeCandsCount(false);
				ArrayList<Candidate> candsBigram = candsCount.getAnalyzeBigramCount();
				ArrayList<Candidate> candsTrigram = candsCount.getAnalyzeTrigramCount();
				
				WriteFile wf = new WriteFile();
				wf.open("NLP_RESULT/cands-nontokenized/cands-bi.txt");
				wf.writeCandidates(candsBigram, candsCount.getLoadNgram().getTotalFrequencyBigram());
				wf.close();
				
				wf.open("NLP_RESULT/cands-nontokenized/cands-tri.txt");
				wf.writeCandidates(candsTrigram, candsCount.getLoadNgram().getTotalFrequencyTrigram());
				wf.close();
				String end = getTime();
				mainView.getTextArea().append("Get candidates from non-tokenized corpus: \n"
						+ "size in MB: " + sizeInMb("NLP_RESULT/nontokenized") + "\n"
						+ "begin: " + begin + "\n" + "end: " + end);
				
			} else {
				JOptionPane.showMessageDialog(null, "If you want to try again, \n please press button one more time");
			}
			
		}
		
	}
	class ShowCollocations implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int isTokenized = JOptionPane.showConfirmDialog(null, "Do you want to view collocation from tokenized corpus?");
			if(isTokenized == 0 || isTokenized == 1){
				collocations = new Collocations(isTokenized);
				collocations.calculate("cands-bi");
				collocations.calculate("cands-tri");
				
				ViewCollocation viewCollocation = new ViewCollocation();
				viewCollocation.setVisible(true);
				String fileToShow = "";
				while(!fileToShow.equals("cands-bi") && !fileToShow.equals("cands-tri")){
					fileToShow = JOptionPane.showInputDialog("Do you want to watch cands-bi or cands-tri collocation? \n"
							+ "cands-bi: Candidates of bigram ; cands-tri: Candidates of trigram");
					System.out.println(fileToShow);
				}
				viewCollocation.initModel(collocations.showCollocation(fileToShow));
				viewCollocation.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "The view was canceled! \n If you want to see collocation: press butoon Collocation again");
			}
		}
		
	}
	class WriteCurrentHistory implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String currentHistory = mainView.getTextArea().getText();
			try {
				history.writeHistory(currentHistory + "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mainView.getTextArea().setText("History had wrote completely!\n\n");
		}
		
	}
	public double sizeInMb(String folder){
		File file = new File(folder);
		System.out.println(folder);
		double size = 0;
		for (File child : file.listFiles()) {
			size += child.length();
		}
		size = size / (1024 * 1024 * 1.0);
		return Math.round(size * 10) / 10.0;
	}
	public String getTime(){
		dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
}

