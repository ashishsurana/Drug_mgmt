import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by ashish on 6/11/15.
 */
public class Stock {
    JTextField textField_name = new JTextField();
    JComboBox jComboBox_name = new JComboBox();
    Vector<String> vector_name = new Vector<>();
    public Stock() {
        final JFrame jFrame = new JFrame();

        jFrame.setSize(600, 600);
        jFrame.setTitle("Stock");

        JLabel label_name = new JLabel("Medicine Name ");
        label_name.setBounds(100, 100, 150, 25);
        jFrame.add(label_name);

//        textField_name.setBounds(250, 100, 200, 25);
//        jFrame.add(textField_name);

        JButton button_getstock = new JButton("Get Stock");
        button_getstock.setBounds(100,140,250,25);
        jFrame.add(button_getstock);
//-----------------------------------------BUTTON ACTIONS----------------------------------
        button_getstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //To do
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

        JButton submit = new JButton("Submit");
        submit.setBounds(250, 450, 100, 25);
        jFrame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

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
            ResultSet resultSet = statement.executeQuery("select * from purchase ");//where name = '" + textField_name.getText() + "'");
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
            public void keyPressed(KeyEvent e) {
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

                            } else {
                                setModel(m, string, "name");
                                jComboBox_name.showPopup();

                            }
                        }
                    }
                });
            }

            @Override
            public void keyTyped(KeyEvent e) {
                String text = textField_name.getText();
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER) {
                    if (!vector_name.contains(text)) {
                        vector_name.addElement(text);
                        Collections.sort(vector_name);
                        setModel(getSuggestedModel(vector_name, text), text, "name");
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









        jComboBox_name.setBounds(250, 100, 200, 25);
        jFrame.add(jComboBox_name);

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
//        else if (token.equalsIgnoreCase("bno")){
//            jComboBox_bno.setModel(mdl);
//            jComboBox_bno.setSelectedIndex(-1);
//            textField_bno.setText(str);
//        }
//        else if (token.equalsIgnoreCase("doctor")){
//            jComboBox_drname.setModel(mdl);
//            jComboBox_drname.setSelectedIndex(-1);
//            textField_drname.setText(str);
//        }
    }
    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(String s: list) {
            if(s.startsWith(text)) m.addElement(s);
        }
        return m;
    }
    public static void main(String[] args){
        Stock  stock = new Stock();

    }
}
