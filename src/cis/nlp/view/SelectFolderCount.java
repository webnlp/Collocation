package cis.nlp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class SelectFolderCount extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnU_B;
	private JButton btnTri;
	private JTextField textField;
	private JCheckBox chckbxType;
	private JButton btnFolder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SelectFolderCount dialog = new SelectFolderCount();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SelectFolderCount() {
		setBounds(100, 100, 449, 178);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(86, 51, 286, 27);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		btnFolder = new JButton("Folder");
		btnFolder.setBounds(86, 14, 86, 25);
		contentPanel.add(btnFolder);
		
		chckbxType = new JCheckBox("Tokenized");
		chckbxType.setBounds(195, 15, 140, 25);
		contentPanel.add(chckbxType);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnU_B = new JButton("Count Uni and Bigram");
				buttonPane.add(btnU_B);
			}
			{
				btnTri = new JButton("Count Trigram");
				btnTri.setActionCommand("OK");
				buttonPane.add(btnTri);
				getRootPane().setDefaultButton(btnTri);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public void addActionCountUniAndBigram(ActionListener ac){
		btnU_B.addActionListener(ac);
	}
	public void addAcitonCountTrigram(ActionListener ac){
		btnTri.addActionListener(ac);
	}
	public void addActionGetFolder(ActionListener ac){
		btnFolder.addActionListener(ac);
	}
	public JTextField setTextPath(){
		return textField;
	}
	public boolean getTypeOfCorpus(){
		return chckbxType.isSelected();
	}
}