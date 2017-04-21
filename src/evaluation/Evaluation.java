package evaluation;

import java.util.ArrayList;

import cis.nlp.io.WriteFile;

public class Evaluation {
	private ListCollocation tscore, pmi, ll, mle, dice;
	private Sample sample;
	
	public Evaluation(String fileTscore, String filePmi, String fileMle, String fileDice, String fileLL, String fileNegative, String filePositive){
		tscore = new ListCollocation(fileTscore);
		pmi = new ListCollocation(filePmi);
		ll = new  ListCollocation(fileLL);
		mle = new ListCollocation(fileMle);
		dice = new ListCollocation(fileDice);
		
		sample = new Sample(filePositive, fileNegative);
	}
	
	public Point evaluation(ListCollocation list){
		int np = 0, nn = 0, lastPosition = 0;
		double precision = 0, recall = 0;
		Point point = new Point(precision, recall, "");
		
		ArrayList<Collocation> listCollocation = list.getListCollocation();
		int j = 0;
		for (Collocation collocation : listCollocation) {
			j++;
			if(sample.isExist(collocation)){
				if(sample.isExistOnPositive(collocation)){
					np ++;
					lastPosition = j;
					if(np % 5 == 0){
						precision = np / (np + nn);
						recall = np / sample.getSizePositive();
						point = new Point(precision, recall, list.getType());
					}
				} else {
					nn ++;
				}
			}
		}
		point.setB(lastPosition, listCollocation.size());
		return point;
	}
	
	public void getAllEvaluation(){
		WriteFile wf = new WriteFile();
		String fileName = "evaluation_" + tscore.getType() + ".txt";
		wf.open(fileName);
		wf.write(evaluation(mle).toString() + "\n");
		wf.write(evaluation(pmi).toString() + "\n");
		wf.write(evaluation(ll).toString() + "\n");
		wf.write(evaluation(dice).toString() + "\n");
		wf.write(evaluation(tscore).toString());
		wf.close();
	}
}
