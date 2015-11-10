import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sales {
	private String pname;
	private String date;
	private String med;
	private String qty;
	private String bno;
	public static void main(String[] args) {
		final JFrame jFrame = new JFrame();
		jFrame.setSize(600, 600);

		JLabel label_name = new JLabel("Medicine Name :");
		label_name.setBounds(100,100,200,25);
		jFrame.add(label_name);
		final JTextField textField_name = new JTextField();
		textField_name.setBounds(250, 100, 200, 25);
		jFrame.add(textField_name);

		JLabel label_batchno = new JLabel("Batch No :");
		label_batchno.setBounds(100,140,200,25);
		jFrame.add(label_batchno);
		final JTextField textField_batchno = new JTextField();
		textField_batchno.setBounds(250,140,200,25);
		jFrame.add(textField_batchno);

		JLabel label_qty = new JLabel("Quantity : ");
		label_qty.setBounds(100,180,200,25);
		jFrame.add(label_qty);
		final JTextField textField_qty = new JTextField();
		textField_qty.setBounds(250,180,200,25);
		jFrame.add(textField_qty);

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
                textField_batchno.setText(null);
                textField_qty.setText(null);
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


                textField_name.setText(null);
                textField_qty.setText(null);


            }
        });

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLayout(null);
		jFrame.setVisible(true);
	}

}
