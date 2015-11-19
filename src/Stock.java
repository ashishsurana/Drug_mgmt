import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by ashish on 6/11/15.
 */
public class Stock {
    JTextField textField_name;// = new JTextField();
    JComboBox jComboBox_name = new JComboBox();
    Vector<String> vector_name = new Vector<>();
    final JFrame jFrame = new JFrame();
    JPanel master = new JPanel(new CardLayout());
    CardLayout cardLayout = (CardLayout) master.getLayout();
    private boolean hide_flag = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public Stock() {

        master.setSize(600, 600);
        master.add(initpanel1(), "P1");
        master.add(initpanel2(),"P2");
        master.add(initpanel3(),"P3");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout.show(master,"P1");
        jFrame.add(master);
        jFrame.setSize(600, 600);
        jFrame.setTitle("Stock");
        jFrame.setLayout(cardLayout);
        jFrame.setVisible(true);

    }

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
    private JScrollPane inittable(){

        Vector<String> vector2 = new Vector<>();
        vector2.addElement("BATCH No.");
        vector2.addElement("QTY");
        vector2.addElement("EXP");
        vector2.addElement("PARTY");
        vector2.addElement("BILL No.");

        JTable jTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(0,5);

        model.setColumnIdentifiers(vector2);
        jTable.setModel(model);
        jTable.setEnabled(false);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(50, 200, 500, 100);


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchase where name = ? ");
            preparedStatement.setString(1, textField_name.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Vector<String> temp = new Vector<>();
                temp.addElement(resultSet.getString("bno"));
                temp.addElement(resultSet.getString("qty"));
                java.sql.Date date = new java.sql.Date(resultSet.getDate("expdate").getTime());
                temp.addElement(String.valueOf(dateFormat.format(date)));
                temp.addElement(resultSet.getString("party"));
                temp.addElement(resultSet.getString("billno"));
                model.addRow(temp);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception Caught ");
        }

        return jScrollPane;
    }

    private JPanel initpanel1(){

        final JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setSize(600,600);
        final JLabel label_name = new JLabel("Medicine Name ");
        label_name.setBounds(100, 100, 150, 25);
        p1.add(label_name);

        final JLabel label_medname = new JLabel();
        label_medname.setBounds(50,175,100,25);
        p1.add(label_medname);

//-----------------------------------------BUTTON ACTIONS----------------------------------
        JButton button_getstock = new JButton("Get Stock");
        button_getstock.setBounds(100, 140, 250, 25);
        p1.add(button_getstock);
        button_getstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (textField_name.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Field cannot be empty");
                } else {
                    p1.add(inittable());
                    label_medname.setText(textField_name.getText());
                }

            }
        });

        final JButton cancel = new JButton("Cancel");
        cancel.setBounds(100, 450, 100, 25);
        p1.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jFrame.dispose();
            }
        });
        JButton  button_getdb = new JButton("Get Stock");
        button_getdb.setBounds(240, 450, 120, 25);
        p1.add(button_getdb);
        button_getdb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                cardLayout.next(master);
                cardLayout.show(master,"P2");
            }
        });
        JButton clear = new JButton("Clear");
        clear.setBounds(400, 450, 100, 25);
        p1.add(clear);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textField_name.setText(null);
            }
        });
        JButton button_sortexp = new JButton("Sort Expiry");
        button_sortexp.setBounds(240, 480, 120, 25);
        p1.add(button_sortexp);
        button_sortexp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(master,"P3");
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
        p1.add(jComboBox_name);

        return p1;
    }
    private JPanel initpanel2(){
        JPanel p2 = new JPanel();
        p2.setSize(600, 600);
        p2.setLayout(null);


        Vector<String> vector2 = new Vector<>();
        vector2.addElement("ITEM");
        vector2.addElement("BATCH No.");
        vector2.addElement("QTY");
        vector2.addElement("EXP");
        vector2.addElement("PARTY");
        vector2.addElement("BILL No.");
        vector2.addElement("BILL DATE");

        JTable jTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(0,7 );

        model.setColumnIdentifiers(vector2);
//        jTable=null;
        jTable.setModel(model);
        jTable.setEnabled(false);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(0, 0,600,500);



        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchase order by name ASC");
            ResultSet resultSet = preparedStatement.executeQuery();
            java.sql.Date date;
            while (resultSet.next()){
                Vector<String> temp = new Vector<>();
                temp.addElement(resultSet.getString("name"));
                temp.addElement(resultSet.getString("bno"));
                temp.addElement(resultSet.getString("qty"));
                date = new java.sql.Date(resultSet.getDate("expdate").getTime());
                temp.addElement(String.valueOf(dateFormat.format(date)));
                temp.addElement(resultSet.getString("party"));
                temp.addElement(resultSet.getString("billno"));
                date = new java.sql.Date(resultSet.getDate("expdate").getTime());
                temp.addElement(String.valueOf(dateFormat.format(date)));
                model.addRow(temp);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        p2.add(jScrollPane);
        JButton button_ok = new JButton("Close");
        button_ok.setBounds(250, 550, 100, 25);
        button_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.next(master);
            }
        });
        p2.add(button_ok);



        return p2;
    }
    private JPanel initpanel3(){
        JPanel p3 = new JPanel();
        p3.setSize(600, 600);
        p3.setLayout(null);


        Vector<String> vector3 = new Vector<>();
        vector3.addElement("ITEM");
        vector3.addElement("BATCH No.");
        vector3.addElement("QTY");
        vector3.addElement("EXP");
        vector3.addElement("PARTY");
        vector3.addElement("BILL No.");
        vector3.addElement("BILL DATE");

        JTable jTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(0,7 );

        model.setColumnIdentifiers(vector3);
//        jTable=null;
        jTable.setModel(model);
        jTable.setEnabled(false);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(0, 0,600,500);



        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock", "admin", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchase order by expdate ASC");
            ResultSet resultSet = preparedStatement.executeQuery();
            java.sql.Date date;
            while (resultSet.next()){
                Vector<String> temp = new Vector<>();
                temp.addElement(resultSet.getString("name"));
                temp.addElement(resultSet.getString("bno"));
                temp.addElement(resultSet.getString("qty"));
                date = new java.sql.Date(resultSet.getDate("expdate").getTime());
                temp.addElement(String.valueOf(dateFormat.format(date)));
                temp.addElement(resultSet.getString("party"));
                temp.addElement(resultSet.getString("billno"));
                date = new java.sql.Date(resultSet.getDate("expdate").getTime());
                temp.addElement(String.valueOf(dateFormat.format(date)));
                model.addRow(temp);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        p3.add(jScrollPane);
        JButton button_ok = new JButton("Close");
        button_ok.setBounds(250, 550, 100, 25);
        button_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.next(master);
            }
        });
        p3.add(button_ok);



        return p3;
    }

    public static void main(String[] args){
        Stock stock = new Stock();
    }
}
