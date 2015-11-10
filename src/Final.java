import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by ashish on 10/11/15.
 */
public class Final {
    private static JTextField tf;
    private static JComboBox combo = new JComboBox();
    private static Vector<String> v = new Vector<>();
    public static void main(String args[]){
        JFrame jFrame = new JFrame();
        jFrame.setSize(300,300);
        combo.setEditable(true);
        tf = (JTextField) combo.getEditor().getEditorComponent();
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String text = tf.getText();
                        if (text.length() == 0) {
                            combo.hidePopup();
                            setModel(new DefaultComboBoxModel(v), null);
                            System.out.println("Inside the length 0 case");
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(v, text);
                            if (m.getSize() == 0 || hide_flag) {
                                combo.hidePopup();
                                hide_flag = false;
                                System.out.println("Inside the length not 0 case");
                            } else {
                                setModel(m, text);
                                combo.showPopup();
                                System.out.println("Inside the !length showpopup case");
                            }
                        }
                    }
                });
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
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
        tf.setBounds(5, 5, 100, 25);
        combo.setBounds(5, 5, 100, 25);


        jFrame.add(tf);
        jFrame.add(combo);
        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }//ed of main
    private static boolean hide_flag = false;
    private static void setModel(DefaultComboBoxModel mdl, String str) {
        combo.setModel(mdl);
        combo.setSelectedIndex(-1);
        tf.setText(str);
        System.out.println("Inside setModel");

    }
    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(String s: list) {
            if(s.startsWith(text)) m.addElement(s);
        }

        return m;
    }
}
