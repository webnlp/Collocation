package cis.nlp.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewCollocation extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnClose;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewCollocation frame = new ViewCollocation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewCollocation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnClose, BorderLayout.SOUTH);
	}
	public void initModel(ArrayList<String> list){
		model = new DefaultTableModel();
		model.addColumn("Maximum-likelihood estimates");
		model.addColumn("Pointwise mutual information");
		model.addColumn("T-Score");
		model.addColumn("Dice");
		model.addColumn("Log-Likekihood esitmates");
		
		int numberOfCandidates = list.size();
		for (int i = 0; i < numberOfCandidates; i++) {
			String[] rowData = list.get(i).split(",");
			model.addRow(rowData);
		}
		table.setModel(model);
	}
}
