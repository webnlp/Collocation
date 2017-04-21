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
	
	public ArrayList<Point> evaluation(ListCollocation list){
		int np = 0, nn = 0, lastPosition = 0;
		double precision = 0, recall = 0;
		Point point = new Point(precision, recall, "");
		ArrayList<Point> listPoint = new ArrayList<>();
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
						listPoint.add(point);
					}
				} else {
					nn ++;
				}
			}
		}
		listPoint.get(listPoint.size() - 1).setB(lastPosition, listCollocation.size());
		return listPoint;
	}
	public String getEvaluationInString(){
		ArrayList<Point> mlePoint = evaluation(mle);
		ArrayList<Point> pmiPoint = evaluation(pmi);
		ArrayList<Point> dicePoint = evaluation(dice);
		ArrayList<Point> tscorePoint = evaluation(tscore);
		ArrayList<Point> llPoint = evaluation(ll);
		
		Point[] ps = new Point[5];
		ps[0] = getPoint(mlePoint);
		ps[1] = getPoint(pmiPoint);
		ps[2] = getPoint(dicePoint);
		ps[3] = getPoint(tscorePoint);
		ps[4] = getPoint(llPoint);
		
		Point[] lastPoint = new Point[5];
		lastPoint[0] = mlePoint.get(mlePoint.size() - 1);
		lastPoint[1] = pmiPoint.get(pmiPoint.size() - 1);
		lastPoint[2] = dicePoint.get(dicePoint.size() - 1);
		lastPoint[3] = tscorePoint.get(tscorePoint.size() - 1);
		lastPoint[4] = llPoint.get(llPoint.size() - 1);
		
		for(int i = 0; i < ps.length; i++){
			for(int j = ps.length; j > i; j--){
				if(ps[i].getPrecision() > ps[j].getPrecision() 
						&& lastPoint[i].getB() < lastPoint[j].getB()){
					Point tmp = ps[i];
					ps[i] = ps[j];
					ps[j] = tmp;
					
					tmp = lastPoint[i];
					lastPoint[i] = lastPoint[j];
					lastPoint[j] = tmp;
				}
			}
		}
		String res = "";
		for(int i = 0; i < lastPoint.length; i++){
			res += ps[i] + " " + lastPoint[i].getB() + "\n";
		}
		return res;
	}
	private Point getPoint(ArrayList<Point> listPoint){
		for (Point point : listPoint) {
			if(point.getRecall() == 0.95){
				return point;
			}
		}
		return new Point(0, 0, "null");
	}
	public void getAllEvaluation(){
		WriteFile wf = new WriteFile();
		String fileName = "evaluation_" + tscore.getType() + ".txt";
		wf.open(fileName);
		wf.write(getEvaluationInString());
		wf.close();
	}
}
