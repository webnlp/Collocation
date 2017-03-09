package cis.nlp.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;

import cis.nlp.count.CountUnigram;
import cis.nlp.model.CountNgram;
import cis.nlp.model.LoadNgram;
import cis.nlp.view.MainView;
import cis.nlp.view.SelectFolderCount;

public class ControlerFolderCountSelected {
	private SelectFolderCount selectFolderCount;
	private SimpleDateFormat dateFormat;
	private CountNgram count;
	private String path;

	public ControlerFolderCountSelected() {
		selectFolderCount = new SelectFolderCount();
		selectFolderCount.setVisible(true);
		
		count = new CountNgram();
		
		
		GetFolder getFolder = new GetFolder();
		selectFolderCount.addActionGetFolder(getFolder);
		
		UniAndBiFromFolder uniAndBiFromFolder = new UniAndBiFromFolder();
		selectFolderCount.addActionCountUniAndBigram(uniAndBiFromFolder);
		
		TriFromFolder triFromFolder = new TriFromFolder();
		selectFolderCount.addAcitonCountTrigram(triFromFolder);
		
		FourFromFolder fourFromFolder = new FourFromFolder();
		selectFolderCount.addAcitonCount4gram(fourFromFolder);
	}

	class UniAndBiFromFolder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String begin = getTime();
			double sizeInMbytes = sizeInMb(path);
			boolean isTokenized = selectFolderCount.getTypeOfCorpus();
			count.setType(isTokenized);
			count.setFileInput(path);
			count.processUniBigram();
			int[] numberOfNgram = count.getCount();
			String end = getTime();
			MainView.getTextArea()
					.append("Count n-gram from tokenized corpus: " + sizeInMbytes + "Mb" + "\n Unigram: "
							+ numberOfNgram[0] + "\n Bigram: " + numberOfNgram[1] + "\n begin " + begin + "\n end "
							+ end + "\n\n");
		}

	}
	class TriFromFolder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String begin = getTime();
			double sizeInMbytes = sizeInMb(path);
			LoadNgram load = new LoadNgram();
			boolean isTokenized = selectFolderCount.getTypeOfCorpus();
			load.setType(isTokenized);
			CountUnigram loadUnigram = load.loadUnigram();
			System.out.println("Unigram loaded sucessfully! " + loadUnigram.getN());
			count.setType(isTokenized);
			count.setFileInput(path);
			count.processTrigram(loadUnigram);
			String end = getTime();
			MainView.getTextArea().append("Count trigram " + sizeInMbytes + "Mb"
					+ "\n Trigram: Done!! "
					+ "\n begin " + begin + "\n end " + end + "\n\n");
		}
		
	}
	class FourFromFolder implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String begin = getTime();
			double sizeInMbytes = sizeInMb(path);
			LoadNgram load = new LoadNgram();
			boolean isTokenized = selectFolderCount.getTypeOfCorpus();
			load.setType(isTokenized);
			CountUnigram loadUnigram = load.loadUnigram();
			CountUnigram loadTriAsUnigram = load.loadNgramAsUnigram("trigram");
			CountUnigram loadBiAsUnigram = load.loadNgramAsUnigram("bigram");
			count.setType(isTokenized);
			count.setFileInput(path);
//			count.process4gramV3_1(loadTriAsUnigram, loadUnigram);
			count.process4gramV2_2(loadBiAsUnigram);
			String end = getTime();
			MainView.getTextArea().append("Count trigram " + sizeInMbytes + "Mb"
					+ "\n Trigram: Done!! "
					+ "\n begin " + begin + "\n end " + end + "\n\n");
		}
		
	}
	class GetFolder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser choose = new JFileChooser();
			choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			choose.showOpenDialog(null);
			
			path = choose.getSelectedFile().getAbsolutePath();
			selectFolderCount.setTextPath().setText(path);
		}

	}

	public double sizeInMb(String folder) {
		File file = new File(folder);
		double size = 0;
		for (File child : file.listFiles()) {
			size += child.length();
		}
		size = size / (1024 * 1024 * 1.0);
		return Math.round(size * 10) / 10.0;
	}

	public String getTime() {
		dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
}
