import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Sell extends JFrame {

	
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public Sell() {
		setTitle("Sell");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 468, 359);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane =  new JScrollPane();
		scrollPane.setBounds(10, 11, 436, 235);
		contentPane.add(scrollPane);
		
		ArrayList <Integer> arr = new ArrayList<Integer>();
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int value = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				arr.add(value);
			}
		});
		scrollPane.setViewportView(table);
		String[] col = {"id", "Name","Brand","Price","Photo"};
		DefaultTableModel model = new DefaultTableModel(null,col) {

			public Class <?> getColumnClass(int column){
				if(column == 4) return ImageIcon.class;
				return Object.class;
			}
		};
		
		try {
			Connection con = DatabaseConnection.initiallizeDatabase();
			PreparedStatement stmt = con.prepareStatement("select * from item");
			ResultSet rs = stmt.executeQuery();
			Object row[] = new Object [5];
			while(rs.next()) {
				row[0] = rs.getInt("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("brand");
				row[3] = rs.getDouble("price");
				row[4] = new ImageIcon(rs.getBytes("image"));
				model.addRow(row);
			}
			table.setRowHeight(180);
			table.setModel((model));
			
			JButton btnNewButton = new JButton("Buy");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					///
					Buy buy = new Buy(arr);
					buy.setVisible(true);
					setVisible(false);
					
				}
			});
			btnNewButton.setBounds(180, 276, 89, 23);
			contentPane.add(btnNewButton);
			}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
}
