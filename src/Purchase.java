
import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.sql.*;
import java.util.*;
import java.util.List;

public class Purchase  {



    public static Vector<String> vector = new Vector<String>();
    final JFrame jFrame = new JFrame();
    JLabel label_name = new JLabel("Medicine");
    public static JTextField textField_name = new JTextField();
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
	public static void main(String[] args) {
        Purchase purchase = new Purchase();
        purchase.initcomponents();
//        purchase.opertations();


    }//endOfMain



    public  void initcomponents(){
        jFrame.setSize(600, 600);
        jFrame.setTitle("Purchase Entry");
        label_name.setBounds(100, 100, 100, 25);
        jFrame.add(label_name);
        textField_name.setBounds(250, 100, 200, 25);
        jFrame.add(textField_name);
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


        jFrame.setLayout(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


//    private void setContent(DefaultComboBoxModel model,String string){
//        jComboBox_name.setModel(model);
//        jComboBox_name.setSelectedIndex(-1);
//        textField_name.setText(string);
//    }
//
//    public DefaultComboBoxModel suggested(List<String> list,String string){
//        DefaultComboBoxModel model = new DefaultComboBoxModel();
//        for (String s : list){
//            if (s.startsWith(string))
//                model.addElement(s);
//        }
//        return  model;
//    }
}//End of main class