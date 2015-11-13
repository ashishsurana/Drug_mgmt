import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Collections;
import java.util.Vector;

public class Sales {
	private String pname;
	private String date;
	private String med;
	private String qty;
	private String bno;

	final JTextField textField_name;
	final JComboBox jComboBox_name = new JComboBox();
	final Vector<String> vector_name = new Vector<>();

	final JTextField textField_bno;
	final JComboBox jComboBox_bno = new JComboBox();
	final Vector<String> vector_bno = new Vector<>();

    JTextField textField_drname;
    final JComboBox jComboBox_drname = new JComboBox();
    final Vector<String> vector_drname = new Vector<>();
	public Sales() {

		final JFrame jFrame = new JFrame();
		jFrame.setSize(600, 600);
        jFrame.setTitle("Sales Entry");

		JLabel label_name = new JLabel("Medicine Name ");
		label_name.setBounds(100,100,200,25);
		jFrame.add(label_name);

//		textField_name.setBounds(250, 100, 200, 25);
//		jFrame.add(textField_name);
		JLabel label_bno = new JLabel("Batch No ");
		label_bno.setBounds(100,140,200,25);
		jFrame.add(label_bno);

//		textField_bno.setBounds(250,140,200,25);
//		jFrame.add(textField_bno);
		JLabel label_qty = new JLabel("Quantity  ");
		label_qty.setBounds(100,180,200,25);
		jFrame.add(label_qty);
		final JTextField textField_qty;// = new JTextField();
//		textField_qty.setBounds(250, 180, 200, 25);
//		jFrame.add(textField_qty);
		JLabel label_billno = new JLabel("BIll No.  ");
		label_billno.setBounds(100,220,200,25);
		jFrame.add(label_billno);
		final JTextField textField_billno = new JTextField();
		textField_billno.setBounds(250, 220, 200, 25);
		jFrame.add(textField_billno);
		JLabel label_drname = new JLabel("By Doctor  ");
		label_drname.setBounds(100,260,200,25);
		jFrame.add(label_drname);
//		final JTextField textField_drname = new JTextField();
//		textField_drname.setBounds(250, 260, 200, 25);
//		jFrame.add(textField_drname);
		JLabel label_pname  = new JLabel("Patient Name");
		label_pname.setBounds(100,300,200,25);
		jFrame.add(label_pname);
		final JTextField textField_pname = new JTextField();
		textField_pname.setBounds(250, 300, 200, 25);
		jFrame.add(textField_pname);
		JLabel label_date  = new JLabel("Date");
		label_date.setBounds(100,340,200,25);
		jFrame.add(label_date);
		final JTextField textField_date = new JTextField();
		textField_date.setBounds(250, 340, 200, 25);
		jFrame.add(textField_date);



// ------------------------------------------------------------------------------------
//                                       BUTTONS
//------------------------------------------------------------------------------------
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(100, 450, 100, 25);
		jFrame.add(cancel);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				jFrame.dispose();
			}
		});
		JButton clear = new JButton("Clear");
		clear.setBounds(400, 450, 100, 25);
		jFrame.add(clear);
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				textField_name.setText(null);
				textField_bno.setText(null);
//				textField_qty.setText(null);
                textField_billno.setText(null);
                textField_drname.setText(null);
                textField_pname.setText(null);
				textField_date.setText(null);
			}
		});

		JButton submit = new JButton("Submit");
		submit.setBounds(250, 450, 100, 25);
		jFrame.add(submit);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String url = new String("jdbc:mysql://127.0.0.1:3306/stock");
				String user = new String("admin");
				String pass = new String("password");
				try {
					Connection con = DriverManager.getConnection(url, user, pass);
					PreparedStatement preparedStatement = con.prepareStatement("insert into stock.sales values(?,?,?,?,?,?,?)");
					preparedStatement.setString(1, String.valueOf(textField_name.getText()));
                    preparedStatement.setString(2, textField_bno.getText());
//					preparedStatement.setInt(3, Integer.parseInt(textField_qty.getText()));
					preparedStatement.setInt(3, Integer.parseInt(textField_billno.getText()));
					preparedStatement.setInt(4, Integer.parseInt(textField_billno.getText()));
					preparedStatement.setString(5, textField_pname.getText());
					preparedStatement.setString(6, textField_drname.getText());
					preparedStatement.setString(7, textField_date.getText());
					preparedStatement.executeUpdate();

					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getErrorCode());
//                    if (e.getErrorCode()==1366){
//                        JOptionPane.showMessageDialog(null, "Response Failed! Check the datails again");
//                    }
				}
                textField_name.setText(null);
                textField_bno.setText(null);
//				textField_qty.setText(null);
                textField_billno.setText(textField_billno.getText());
                textField_drname.setText(textField_drname.getText());
                textField_pname.setText(textField_pname.getText());
                textField_date.setText(textField_date.getText());

			}
		});


// ------------------------------------------------------------------------------------
//                                       Auto Suggestion NAME
//------------------------------------------------------------------------------------

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
			java.sql.Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from purchase");
			while (resultSet.next()){
				vector_name.addElement(resultSet.getString("name"));
			}

		}
		catch (SQLException e2){
			e2.printStackTrace();
		}

		jComboBox_name.setEditable(true);
		textField_name = (JTextField) jComboBox_name.getEditor().getEditorComponent();
		textField_name.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {

				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						String string = textField_name.getText();
						if (string.length() == 0) {
							jComboBox_name.hidePopup();
							setModel(new DefaultComboBoxModel(vector_name), null,"name");
//                            System.out.println("Inside the length 0 case");
						} else {
							DefaultComboBoxModel m = getSuggestedModel(vector_name, string);
							if (m.getSize() == 0 || hide_flag) {
								jComboBox_name.hidePopup();
								hide_flag = false;
//                                System.out.println("Inside the length not 0 case");
							} else {
								setModel(m, string,"name");
								jComboBox_name.showPopup();
//                                System.out.println("Inside the !length showpopup case");
							}
						}
					}
				});
			}

			@Override
			public void keyPressed(KeyEvent e) {
				String text = textField_name.getText();
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_ENTER) {
					if (!vector_name.contains(text)) {
						vector_name.addElement(text);
						Collections.sort(vector_name);
						setModel(getSuggestedModel(vector_name, text), text,"name");
						System.out.println(text);
					}
					hide_flag = true;
				} else if (code == KeyEvent.VK_ESCAPE) {
					hide_flag = true;
				} else if (code == KeyEvent.VK_RIGHT) {
					for (int i = 0; i < vector_name.size(); i++) {
						String str = vector_name.elementAt(i);
						if (str.startsWith(text)) {
							jComboBox_name.setSelectedIndex(-1);
							textField_name.setText(str);
							return;
						}
					}
				}
			}
		});
		setModel(new DefaultComboBoxModel(vector_name), "", "name");



//------------------------------------------------------------------------------------
//                                       Auto Suggestion BATCHNO
//------------------------------------------------------------------------------------
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
			java.sql.Statement statement = con.createStatement();

			ResultSet resultSet = statement.executeQuery("select * from purchase ");//where name = '" + textField_name.getText() + "'");
			while (resultSet.next()){
				vector_bno.addElement(resultSet.getString("bno"));
			}

		}
		catch (SQLException e2){
			e2.printStackTrace();
		}

		jComboBox_bno.setEditable(true);
		textField_bno = (JTextField) jComboBox_bno.getEditor().getEditorComponent();
		textField_bno.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {

				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						String string = textField_bno.getText();
						if (string.length() == 0) {
							jComboBox_bno.hidePopup();
							setModel(new DefaultComboBoxModel(vector_bno), null, "bno");
						} else {
							DefaultComboBoxModel m = getSuggestedModel(vector_bno, string);
							if (m.getSize() == 0 || hide_flag2) {
								jComboBox_bno.hidePopup();
								hide_flag2 = false;
							} else {
								setModel(m, string, "bno");
								jComboBox_bno.showPopup();
							}
						}
					}
				});
			}

			@Override
			public void keyPressed(KeyEvent e) {
				String text = textField_bno.getText();
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_ENTER) {
					if (!vector_bno.contains(text)) {
						vector_bno.addElement(text);
						Collections.sort(vector_bno);
						setModel(getSuggestedModel(vector_bno, text), text, "bno");
					}
					hide_flag2 = true;
				} else if (code == KeyEvent.VK_ESCAPE) {
					hide_flag2 = true;
				} else if (code == KeyEvent.VK_RIGHT) {
					for (int i = 0; i < vector_bno.size(); i++) {
						String str = vector_bno.elementAt(i);
						if (str.startsWith(text)) {
							jComboBox_bno.setSelectedIndex(-1);
							textField_bno.setText(str);
							return;
						}
					}
				}
			}
		});
		setModel(new DefaultComboBoxModel(vector_bno), "", "bno");
//------------------------------------------------------------------------------------
//                                       Auto Suggestion DrName
//------------------------------------------------------------------------------------
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
            java.sql.Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from sales ");//where name = '" + textField_name.getText() + "'");
            while (resultSet.next()){
                vector_drname.addElement(resultSet.getString("drname"));
            }
        }
        catch (SQLException e2){
            e2.printStackTrace();
        }
        jComboBox_drname.setEditable(true);
        textField_drname = (JTextField) jComboBox_drname.getEditor().getEditorComponent();
        textField_drname.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String string = textField_drname.getText();
                        if (string.length() == 0) {
                            jComboBox_drname.hidePopup();
                            setModel(new DefaultComboBoxModel(vector_drname), null, "doctor");
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(vector_drname, string);
                            if (m.getSize() == 0 || hide_flag3) {
                                jComboBox_drname.hidePopup();
                                hide_flag3 = false;
                            } else {
                                setModel(m, string, "doctor");
                                jComboBox_drname.showPopup();
                            }
                        }
                    }
                });
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String text = textField_drname.getText();
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER) {
                    if (!vector_drname.contains(text)) {
                        vector_drname.addElement(text);
                        Collections.sort(vector_drname);
                        setModel(getSuggestedModel(vector_drname, text), text, "doctor");
                    }
                    hide_flag3 = true;
                } else if (code == KeyEvent.VK_ESCAPE) {
                    hide_flag3 = true;
                } else if (code == KeyEvent.VK_RIGHT) {
                    for (int i = 0; i < vector_drname.size(); i++) {
                        String str = vector_drname.elementAt(i);
                        if (str.startsWith(text)) {
                            jComboBox_drname.setSelectedIndex(-1);
                            textField_drname.setText(str);
                            return;
                        }
                    }
                }
            }
        });
        setModel(new DefaultComboBoxModel(vector_drname), "", "doctor");

//===========================================================================================


		jComboBox_name.setBounds(250, 100, 200, 25);
		jFrame.add(jComboBox_name);
		jComboBox_bno.setBounds(250, 140, 200, 25);
		jFrame.add(jComboBox_bno);
        jComboBox_drname.setBounds(250, 260, 200, 25);
        jFrame.add(jComboBox_drname);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLayout(null);
		jFrame.setVisible(true);
	}
	private boolean hide_flag = false;
	private boolean hide_flag2 = false;
	private boolean hide_flag3 = false;
	private void setModel(DefaultComboBoxModel mdl, String str,String token) {
		if (token.equalsIgnoreCase("name")){
			jComboBox_name.setModel(mdl);
			jComboBox_name.setSelectedIndex(-1);
			textField_name.setText(str);
		}
		else if (token.equalsIgnoreCase("bno")){
			jComboBox_bno.setModel(mdl);
			jComboBox_bno.setSelectedIndex(-1);
			textField_bno.setText(str);
		}
        else if (token.equalsIgnoreCase("doctor")){
            jComboBox_drname.setModel(mdl);
            jComboBox_drname.setSelectedIndex(-1);
            textField_drname.setText(str);
        }
	}
	private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		for(String s: list) {
			if(s.startsWith(text)) m.addElement(s);
		}
		return m;
	}

	public static void main(String[] args) {
		Sales sales  = new Sales();
		}

	}

