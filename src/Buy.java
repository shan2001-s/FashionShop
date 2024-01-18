import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Buy extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField lblprice;
	private JButton btnNewButton;
	private JLabel lblNewLabel_1;
	private JTextField txtname;
	private JButton btnNewButton_1;
	
	private int row = 0;


	/**
	 * Create the frame.
	 */
	public Buy(ArrayList arr) {
		double total =0;
		setTitle("Order Items");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 357);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 407, 144);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 row = table.getSelectedRow();
			//	int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				String name = table.getModel().getValueAt(row, 1).toString();
				txtname.setText(name);
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id", "Name", "Price"
			}
		));
		try {
		
			Connection conn = DatabaseConnection.initiallizeDatabase();
			((DefaultTableModel)table.getModel()).setRowCount(0);
			Object row[] = new Object[3];
			for(int i =0; i < arr.size();i++) {
				String sql = "select * from item where id ="+ arr.get(i)+"";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					row[0] = rs.getObject("id");
					row[1] = rs.getObject("name");
					row[2] = rs.getDouble("price");
					total += rs.getDouble("price");
					((DefaultTableModel)table.getModel()).addRow(row);
					
				}
			}
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Total Price");
		lblNewLabel.setBounds(80, 195, 63, 19);
		contentPane.add(lblNewLabel);
		
		lblprice = new JTextField();
		lblprice.setText(String.valueOf(total));
		lblprice.setBounds(154, 194, 96, 20);
		contentPane.add(lblprice);
		lblprice.setColumns(10);
		
		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Thank you");
			}
		});
		btnNewButton.setBounds(309, 193, 89, 23);
		contentPane.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(51, 256, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		txtname = new JTextField();
		txtname.setBounds(99, 253, 96, 20);
		contentPane.add(txtname);
		txtname.setColumns(10);
		
		btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arr.remove(row);
				Buy.this.setVisible(false);
				Buy buy = new Buy(arr);
				buy.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(243, 252, 89, 23);
		contentPane.add(btnNewButton_1);
	}
}
