import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by ashish on 12/11/15.
 */
public class Purchasing {
    JLabel label_name = new JLabel("Medicine");

    JLabel label_bno = new JLabel("Batch No.");
    final JTextField textField_bno = new JTextField();
    JLabel label_qty = new JLabel("Quantity");
    final JTextField textField_qty = new JTextField();
    JLabel label_party = new JLabel("Supplier's Name");
    final JTextField textField_party = new JTextField();
    JLabel label_billno = new JLabel("Bill No");
    final JTextField textField_billno = new JTextField();
    JLabel label_billdate = new JLabel("Bill Date");
    final JTextField textField_billdate = new JTextField();
    JLabel label_expdate = new JLabel("Expiry Date");
    final JTextField textField_expdate = new JTextField();

    private  JTextField textField_name;
    private final JComboBox jComboBox_name = new JComboBox();
    private final Vector<String> v = new Vector<>();
    public void Purchasing(){
        final JFrame jFrame = new JFrame();
        jFrame.setSize(600, 600);
        label_name.setBounds(100, 100, 100, 25);
        jFrame.add(label_name);
////        textField_name.setBounds(250, 100, 200, 25);
//        jFrame.add(textField_name);
        label_bno.setBounds(100, 140, 100, 25);
        jFrame.add(label_bno);
        textField_bno.setBounds(250, 140, 200, 25);
        jFrame.add(textField_bno);
        label_qty.setBounds(100, 180, 100, 25);
        jFrame.add(label_qty);
        textField_qty.setBounds(250, 180, 200, 25);
        jFrame.add(textField_qty);
        label_party.setBounds(100, 220, 120, 25);
        jFrame.add(label_party);
        textField_party.setBounds(250, 220, 200, 25);
        jFrame.add(textField_party);
        label_billno.setBounds(100, 260, 100, 25);
        jFrame.add(label_billno);
        textField_billno.setBounds(250, 260, 200, 25);
        jFrame.add(textField_billno);
        label_billdate.setBounds(100, 300, 100, 25);
        jFrame.add(label_billdate);
        textField_billdate.setBounds(250, 300, 200, 25);
        jFrame.add(textField_billdate);
        label_expdate.setBounds(100, 340, 100, 25);
        jFrame.add(label_expdate);
        textField_expdate.setBounds(250, 340, 200, 25);
        jFrame.add(textField_expdate);
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
                textField_qty.setText(null);
                textField_party.setText(null);
                textField_billdate.setText(null);
                textField_billno.setText(null);
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
                    PreparedStatement preparedStatement = con.prepareStatement("insert into stock.purchase values(?,?,?,?,?,?)");
                    preparedStatement.setString(1, String.valueOf(textField_name.getText()));
                    preparedStatement.setString(2, textField_bno.getText());
                    preparedStatement.setInt(3, Integer.parseInt(textField_qty.getText()));
                    preparedStatement.setString(4, textField_party.getText());
                    preparedStatement.setInt(5, Integer.parseInt(textField_billno.getText()));
                    preparedStatement.setString(6, textField_billno.getText());
                    preparedStatement.executeUpdate();

                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                textField_name.setText(null);
                textField_bno.setText(null);
                textField_qty.setText(null);
                textField_party.setText(null);
                textField_billdate.setText(null);
                textField_billno.setText(null);
                textField_expdate.setText(null);

            }
        });
//-----------------------------------------------------------------------------------------------
//                                      Suggestion Box
// -----------------------------------------------------------------------------------------------
        jComboBox_name.setEditable(true);
        textField_name = (JTextField) jComboBox_name.getEditor().getEditorComponent();
        textField_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String text = textField_name.getText();
                        if (text.length() == 0) {
                            jComboBox_name.hidePopup();
                            setModel(new DefaultComboBoxModel(v), null);
                            System.out.println("Inside the length 0 case");
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(v, text);
                            if (m.getSize() == 0 || hide_flag) {
                                jComboBox_name.hidePopup();
                                hide_flag = false;
                                System.out.println("Inside the length not 0 case");
                            } else {
                                setModel(m, text);
                                jComboBox_name.showPopup();
                                System.out.println("Inside the !length showpopup case");
                            }
                        }
                    }
                });
            }
            public void keyPressed(KeyEvent e) {
                String text = textField_name.getText();
                int code = e.getKeyCode();
                if(code==KeyEvent.VK_ENTER) {
                    if(!v.contains(text)) {
                        v.addElement(text);
                        Collections.sort(v);
                        setModel(getSuggestedModel(v, text), text);
                        System.out.println(text);
                    }
                    hide_flag = true;
                }else if(code==KeyEvent.VK_ESCAPE) {
                    hide_flag = true;
                }else if(code==KeyEvent.VK_RIGHT) {
                    for(int i=0;i<v.size();i++) {
                        String str = v.elementAt(i);
                        if(str.startsWith(text)) {
                            jComboBox_name.setSelectedIndex(-1);
                            textField_name.setText(str);
                            return;
                        }
                    }
                }
            }
        });


        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");

            java.sql.Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from purchase");
            while (resultSet.next()){

                v.addElement(resultSet.getString("name"));

            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        setModel(new DefaultComboBoxModel(v), "");
//        JPanel p = new JPanel(new BorderLayout());

        jFrame.setTitle("Purchase Entry");

        jComboBox_name.setBounds(250, 100, 200, 25);

        jFrame.add(jComboBox_name);


        JLabel label = new JLabel(" ");
        jFrame.add(label);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);




    }
    private boolean hide_flag = false;
    private void setModel(DefaultComboBoxModel mdl, String str) {
        jComboBox_name.setModel(mdl);
        jComboBox_name.setSelectedIndex(-1);
        textField_name.setText(str);

    }
    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(String s: list) {
            if(s.startsWith(text)) m.addElement(s);
        }
        return m;
    }

    public static void main(String args[]){
        Purchasing purchasing = new Purchasing();
        purchasing.Purchasing();

    }
}
