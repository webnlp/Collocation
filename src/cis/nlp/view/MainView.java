package cis.nlp.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField folderRaw;
	private JTextField folderWordTokenize;
	
	private static JTextArea textArea;
	private JButton btnFolderRaw;
	private JButton btnTokenize;
	private JButton btnWordTokenizer;
	private JButton btn;
	private JButton btnCollocation;
	private JButton btnPreprocess;
	private JButton btnWriteHistory;
	private JButton btnCands;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainView frame = new MainView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 36, 339, 307);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(345, 0, 178, 343);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		folderRaw = new JTextField();
		folderRaw.setBounds(0, 31, 178, 24);
		panel_1.add(folderRaw);
		folderRaw.setColumns(10);
		
		btnFolderRaw = new JButton("Folder Raw");
		btnFolderRaw.setBounds(0, 0, 166, 24);
		panel_1.add(btnFolderRaw);
		
		folderWordTokenize = new JTextField();
		folderWordTokenize.setBounds(0, 150, 178, 24);
		panel_1.add(folderWordTokenize);
		folderWordTokenize.setColumns(10);
		
		btnWordTokenizer = new JButton("Word Tokenizer");
		btnWordTokenizer.setBounds(0, 113, 166, 25);
		panel_1.add(btnWordTokenizer);
		
		btn = new JButton("Count ngram");
		btn.setBounds(22, 234, 132, 25);
		panel_1.add(btn);
		
		btnCollocation = new JButton("Collocation");
		btnCollocation.setBounds(0, 307, 166, 24);
		panel_1.add(btnCollocation);
		
		btnTokenize = new JButton("Tokenize");
		btnTokenize.setBounds(0, 67, 166, 24);
		panel_1.add(btnTokenize);
		
		btnPreprocess = new JButton("Pre-process");
		btnPreprocess.setBounds(0, 186, 166, 24);
		panel_1.add(btnPreprocess);
		
		btnCands = new JButton("Cands");
		btnCands.setBounds(38, 271, 97, 24);
		panel_1.add(btnCands);
		
		JLabel lblHistory = new JLabel("History");
		lblHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistory.setBounds(0, 0, 133, 25);
		contentPane.add(lblHistory);
		
		btnWriteHistory = new JButton("Write History");
		btnWriteHistory.setBounds(169, 0, 128, 25);
		contentPane.add(btnWriteHistory);
		
	}


	public JTextField getFolderRaw() {
		return folderRaw;
	}

	public void setFolderRaw(JTextField folderRaw) {
		this.folderRaw = folderRaw;
	}

	public JTextField getFolderWordTokenize() {
		return folderWordTokenize;
	}

	public void setFolderWordTokenize(JTextField folderWordTokenize) {
		this.folderWordTokenize = folderWordTokenize;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	public JButton getBtnFolderRaw() {
		return btnFolderRaw;
	}

	public void setBtnFolderRaw(JButton btnFolderRaw) {
		this.btnFolderRaw = btnFolderRaw;
	}

	public JButton getBtnTokenize() {
		return btnTokenize;
	}

	public void setBtnTokenize(JButton btnTokenize) {
		this.btnTokenize = btnTokenize;
	}

	public JButton getBtnWordTokenizer() {
		return btnWordTokenizer;
	}

	public void setBtnWordTokenizer(JButton btnWordTokenizer) {
		this.btnWordTokenizer = btnWordTokenizer;
	}

	public JButton getBtnCountAndCutoff() {
		return btn;
	}

	public void setBtnCountAndCutoff(JButton btnCountAndCutoff) {
		this.btn = btnCountAndCutoff;
	}

	public JButton getBtnCollocation() {
		return btnCollocation;
	}

	public void setBtnCollocation(JButton btnCollocation) {
		this.btnCollocation = btnCollocation;
	}
	
	public void addActionFolderRaw(ActionListener ac){
		btnFolderRaw.addActionListener(ac);
	}
	
	public void addActionTokenize(ActionListener ac){
		btnTokenize.addActionListener(ac);
	}
	
	public void addActionPreprocess(ActionListener ac){
		btnPreprocess.addActionListener(ac);
	}
	
	public void addActionCountNgram(ActionListener ac){
		btn.addActionListener(ac);
	}
	public void addActionWriteHistory(ActionListener ac){
		btnWriteHistory.addActionListener(ac);
	}
	public void addActionTokenized(ActionListener ac){
		btnWordTokenizer.addActionListener(ac);
	}
	public void addActionGetCandsCount(ActionListener ac){
		btnCands.addActionListener(ac);
	}
	public void addActionCollocation(ActionListener ac){
		btnCollocation.addActionListener(ac);
	}
}
