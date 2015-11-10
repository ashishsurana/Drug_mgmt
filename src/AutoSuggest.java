import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

public class AutoSuggest {
    private final JTextField tf;
    private final JComboBox combo = new JComboBox();
    private final Vector<String> v = new Vector<>();
    public AutoSuggest() {
//        super(new BorderLayout());
        combo.setEditable(true);
        tf = (JTextField) combo.getEditor().getEditorComponent();
        tf.setBounds(5,5,100,25);
        tf.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String text = tf.getText();
                        if(text.length()==0) {
                            combo.hidePopup();
                            setModel(new DefaultComboBoxModel(v), null);
                            System.out.println("Inside the length 0 case");
                        }else{
                            DefaultComboBoxModel m = getSuggestedModel(v, text);
                            if(m.getSize()==0 || hide_flag) {
                                combo.hidePopup();
                                hide_flag = false;
                                System.out.println("Inside the length not 0 case");
                            }else{
                                setModel(m, text);
                                combo.showPopup();
                                System.out.println("Inside the !length showpopup case");
                            }
                        }
                    }
                });
            }
            public void keyPressed(KeyEvent e) {
                String text = tf.getText();
                int code = e.getKeyCode();
                if(code==KeyEvent.VK_ENTER) {
                    if(!v.contains(text)) {
                        v.addElement(text);
                        Collections.sort(v);
                        setModel(getSuggestedModel(v, text), text);
                    }
                    hide_flag = true;
                }else if(code==KeyEvent.VK_ESCAPE) {
                    hide_flag = true;
                }else if(code==KeyEvent.VK_RIGHT) {
                    for(int i=0;i<v.size();i++) {
                        String str = v.elementAt(i);
                        if(str.startsWith(text)) {
                            combo.setSelectedIndex(-1);
                            tf.setText(str);
                            return;
                        }
                    }
                }
            }
        });
//        String[] countries = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola","Argentina"
//                ,"Armenia","Austria","Bahamas","Bahrain", "Bangladesh","Barbados", "Belarus","Belgium",
//                "Benin","Bhutan","Bolivia","Bosnia & Herzegovina","Botswana","Brazil","Bulgaria",
//                "Burkina Faso","Burma","Burundi","Cambodia","Cameroon","Canada", "China","Colombia",
//                "Comoros","Congo","Croatia","Cuba","Cyprus","Czech Republic","Denmark", "Georgia",
//                "Germany","Ghana","Great Britain","Greece","Hungary","Holland","India","Iran","Iraq",
//                "Italy","Somalia", "Spain", "Sri Lanka", "Sudan","Suriname", "Swaziland","Sweden",
//                "Switzerland", "Syria","Uganda","Ukraine","United Arab Emirates","United Kingdom",
//                "United States","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam",
//                "Yemen","Zaire","Zambia","Zimbabwe"};
//        for(int i=0;i<countries.length;i++){
//            v.addElement(countries[i]);
//        }
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
        JFrame jFrame = new JFrame();
        jFrame.setSize(300, 300);

        combo.setBounds(50,10,100,25);
//        tf.setBounds(50, 5, 100, 25);
//        p.setBorder(BorderFactory.createTitledBorder("AutoSuggestion Box"));
//        p.add(combo, BorderLayout.NORTH);
//        add(p);
//        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        setPreferredSize(new Dimension(300, 150));
        jFrame.add(combo);
        JLabel label = new JLabel(" ");
        jFrame.add(label);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);

    }//End of Constructor
    private boolean hide_flag = false;
    private void setModel(DefaultComboBoxModel mdl, String str) {
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
    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
////        frame.getContentPane().add(new AutoSuggest());
//        frame.pack();
//        System.out.println("Inside Main");
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
        AutoSuggest autoSuggest = new AutoSuggest();
    }
}
