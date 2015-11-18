

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by ashish on 6/11/15.
 */
public class Stock {
    JTextField textField_name;// = new JTextField();
    JComboBox jComboBox_name = new JComboBox();
    Vector<String> vector_name = new Vector<>();
    final JFrame jFrame = new JFrame();
    JPanel master = new JPanel(new CardLayout());
    public Stock() {


        jFrame.setSize(600, 600);
        jFrame.setTitle("Stock");

        final JLabel label_name = new JLabel("Medicine Name ");
        label_name.setBounds(100, 100, 150, 25);
        jFrame.add(label_name);

        final JLabel label_medname = new JLabel();
        label_medname.setBounds(50,180,100,25);
        jFrame.add(label_medname);


//-----------------------------------------BUTTON ACTIONS----------------------------------
        JButton button_getstock = new JButton("Get Stock");
        button_getstock.setBounds(100,140,250,25);
        jFrame.add(button_getstock);
        button_getstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (textField_name.getText().length()==0){
                    JOptionPane.showMessageDialog(null,"Field cannot be empty");
                }
                else {
                    inittable();
                    label_medname.setText(textField_name.getText());
                }

            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(100, 450, 100, 25);
        jFrame.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jFrame.dispose();
            }
        });
        JButton submit = new JButton("Get Stock");
        submit.setBounds(240, 450, 120, 25);
        jFrame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                jFrame.add(in);
            }
        });
        JButton clear = new JButton("Clear");
        clear.setBounds(400, 450, 100, 25);
        jFrame.add(clear);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textField_name.setText(null);
            }
        });
//========================================================================================
//                           Auto Suggestions for Medicine Name
//========================================================================================

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
            java.sql.Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct name from purchase");
            while (resultSet.next()){
                vector_name.addElement(resultSet.getString("name"));
            }
            Collections.sort(vector_name);
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
                            setModel(new DefaultComboBoxModel(vector_name), null, "name");
//                            System.out.println("Inside the length 0 case");
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(vector_name, string);
                            if (m.getSize() == 0 || hide_flag) {
                                jComboBox_name.hidePopup();
                                hide_flag = false;
//                                System.out.println("Inside the length not 0 case");
                            } else {
                                setModel(m, string, "name");
                                jComboBox_name.showPopup();
                            }
                        }
                    }
                });
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String text = textField_name.getText();
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_TAB) {
                    if (!vector_name.contains(text)) {
                        JOptionPane.showMessageDialog(null, "Stock Unavailable");
                        textField_name.setText(null);
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
//---------------------------End of name block







        jComboBox_name.setBounds(250, 100, 200, 25);
        jFrame.add(jComboBox_name);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
    }
    private boolean hide_flag = false;
    private void setModel(DefaultComboBoxModel mdl, String str,String token) {
        if (token.equalsIgnoreCase("name")){
            jComboBox_name.setModel(mdl);
            jComboBox_name.setSelectedIndex(-1);
            textField_name.setText(str);
        }

    }
    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(String s: list) {
            if(s.startsWith(text)) m.addElement(s);
        }
        return m;
    }
    private void inittable(){

        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        Vector<String> vector2 = new Vector<>();
        vector2.addElement("BATCH No.");
        vector2.addElement("QTY");
        vector2.addElement("EXP");
        vector2.addElement("PARTY");
        vector2.addElement("BILL No.");

        JTable jTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(0,5);

        model.setColumnIdentifiers(vector2);
//        jTable=null;
        jTable.setModel(model);
        jTable.setEnabled(false);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(50, 200,500,100);
        jFrame.add(jScrollPane);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchase where name = ? ");
            preparedStatement.setString(1, textField_name.getText());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Vector<String> temp = new Vector<>();
                temp.addElement(resultSet.getString("bno"));
                temp.addElement(resultSet.getString("qty"));
                temp.addElement(resultSet.getString("expdate"));
                temp.addElement(resultSet.getString("party"));
                temp.addElement(resultSet.getString("billno"));
                model.addRow(temp);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }
    private JPanel p2(){
        JPanel jPanel = new JPanel();
        jPanel.setBounds(10, 10, 400, 400);
        JButton button_ok = new JButton("Close");
        button_ok.setBounds(350, 650, 100, 25);
        jPanel.add(button_ok);
        button_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                JFrame.
            }
        });

        jFrame.add(jPanel);
        jFrame.setSize(800, 600);
        return jPanel;
    }
    private JPanel p1(){
        return new JPanel();
    }
    public static void main(String[] args){
        Stock  stock = new Stock();

    }
}
