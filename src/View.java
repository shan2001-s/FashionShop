import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JLabel lblimage;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JTextField txtname;
	private JTextField txtqty;
	private JTextField txtprice;
	private JTextField txtbrand;



	/**
	 * Create the frame.
	 */
	public View() {
		setTitle("view");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 588, 443);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 49, 442, 155);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=table.getSelectedRow();
				String value = table.getModel().getValueAt(row, 0).toString();
				System.out.println(value);
				try {
					Connection con = DatabaseConnection.initiallizeDatabase();
					String sql = "select * from item where id = '"+value+ "'" ;
					PreparedStatement stmt = con.prepareStatement(sql);
					
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						txtname.setText(rs.getString("name"));
						txtqty.setText(String.valueOf(rs.getInt("qty")));
						txtprice.setText(String.valueOf( rs.getDouble("price")));
						txtbrand.setText(rs.getString("brand"));
						// Fetch the image as a byte array
				        byte[] imageData = rs.getBytes("image");

				        // Convert the byte array to an ImageIcon
				        ImageIcon imageIcon = new ImageIcon(imageData);

				        // Set the ImageIcon to the JLabel
				        lblimage.setIcon(imageIcon);
								}
				}
				catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id", "Name", "Quantity", "Price", "Brand"
			}
		));
		try {
			Connection con = DatabaseConnection.initiallizeDatabase();
			PreparedStatement stmt = con.prepareStatement("select * from item");
			ResultSet rs = stmt.executeQuery();
			Object [] row = new Object[5];
			while(rs.next()) {
				row[0] = rs.getInt("id");
				row[1] = rs.getString("name");
				row[2] = rs.getInt("qty");
				row[3] = rs.getDouble("price");
				row[4] = rs.getString("brand");
				((DefaultTableModel)table.getModel()).addRow(row);
			}
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Items view");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(235, 11, 131, 39);
		contentPane.add(lblNewLabel);
		
		lblimage = new JLabel("");
		lblimage.setBounds(61, 239, 131, 127);
		contentPane.add(lblimage);
		
		lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(247, 239, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Quantity");
		lblNewLabel_2.setBounds(247, 264, 48, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Price");
		lblNewLabel_3.setBounds(247, 289, 48, 14);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Brand");
		lblNewLabel_4.setBounds(247, 314, 48, 14);
		contentPane.add(lblNewLabel_4);
		
		txtname = new JTextField();
		txtname.setBounds(320, 236, 96, 20);
		contentPane.add(txtname);
		txtname.setColumns(10);
		
		txtqty = new JTextField();
		txtqty.setColumns(10);
		txtqty.setBounds(320, 261, 96, 20);
		contentPane.add(txtqty);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(320, 286, 96, 20);
		contentPane.add(txtprice);
		
		txtbrand = new JTextField();
		txtbrand.setColumns(10);
		txtbrand.setBounds(320, 311, 96, 20);
		contentPane.add(txtbrand);
	}
}
