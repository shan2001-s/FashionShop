import java.awt.BorderLayout;
import javax.swing.JFileChooser;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class InsertItem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtbrand;
	private JTextField txtname;
	private JTextField txtprice;
	private JTextField txtqty;

	FileInputStream fis;

	/**
	 * Create the frame.
	 */ 
	public InsertItem() {
		setTitle("Insert Item");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fashion Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(140, 11, 193, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Brand");
		lblNewLabel_1.setBounds(37, 98, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name");
		lblNewLabel_1_1.setBounds(37, 73, 48, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setBounds(37, 123, 48, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Quantity");
		lblNewLabel_1_3.setBounds(37, 150, 48, 14);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Image");
		lblNewLabel_1_4.setBounds(37, 175, 48, 14);
		contentPane.add(lblNewLabel_1_4);
		
		txtbrand = new JTextField();
		txtbrand.setBounds(113, 95, 122, 20);
		contentPane.add(txtbrand);
		txtbrand.setColumns(10);
		
		txtname = new JTextField();
		txtname.setColumns(10);
		txtname.setBounds(113, 70, 122, 20);
		contentPane.add(txtname);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(113, 120, 122, 20);
		contentPane.add(txtprice);
		
		txtqty = new JTextField();
		txtqty.setColumns(10);
		txtqty.setBounds(113, 147, 122, 20);
		contentPane.add(txtqty);
		
	
		JButton btn = new JButton("Browse");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter (".imges","jpg","gif","png");
				chooser.addChoosableFileFilter(filter);
				int result = chooser.showSaveDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					File image = new File(path);
					try {
						fis = new FileInputStream(image);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
				}
				else if (result == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "Please choose image");
				}
				
				
			}
		});
		btn.setBounds(113, 171, 89, 23);
		contentPane.add(btn);
		
		JButton btnsave = new JButton("Save");
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtname.getText();
				String brand = txtbrand.getText();
				double price = Double.parseDouble(txtprice.getText());
				int qty = Integer.parseInt(txtqty.getText());
				
				try {
					Connection con = DatabaseConnection.initiallizeDatabase();
					String sql = "insert into item(name,brand,price,qty,image)values(?,?,?,?,?)";
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setString(1,name);
					stmt.setString(2,brand);
					stmt.setDouble(3,price);
					stmt.setInt(4,qty);
					stmt.setBinaryStream(5,fis);
					stmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "successfully saved");
					txtname.setText("");
					txtbrand.setText("");
					txtprice.setText("");
					txtbrand.setText("");
					txtqty.setText("");
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		btnsave.setBounds(209, 220, 89, 34);
		contentPane.add(btnsave);
	}
}
