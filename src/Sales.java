import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sales {
	private String pname;
	private String date;
	private String med;
	private String qty;
	private String bno;
	public static void main(String[] args) {
		final JFrame jFrame = new JFrame();
		jFrame.setSize(600, 600);
		jFrame.setVisible(true);
		jFrame.setLayout(null);
		jFrame.setTitle("Sales Entry");

		/*JLabel label_ = new JLabel("");
		label_.setBounds(100, 100, 100, 25);
		jFrame.add(label_);
		JTextField textField_ = new JTextField();
		textField_.setBounds(250,100, 100, 25);
		jFrame.add(textField_);
		*/

		JLabel label_name = new JLabel("Medicine Name");
		label_name.setBounds(100, 100, 200, 25);
		jFrame.add(label_name);
		final JTextField textField_name = new JTextField();
		textField_name.setBounds(250, 100, 100, 25);
		jFrame.add(textField_name);

		JLabel label_bno = new JLabel("Batch No.");
		label_bno.setBounds(100, 140, 100, 25);
		jFrame.add(label_bno);
		final JTextField textField_bno = new JTextField();
		textField_bno.setBounds(250,140, 100, 25);
		jFrame.add(textField_bno);

		JLabel label_qty = new JLabel("Quantity");
		label_qty.setBounds(100, 180, 100, 25);
		jFrame.add(label_qty);
		final JTextField textField_qty = new JTextField();
		textField_qty.setBounds(250, 180, 100, 25);
		jFrame.add(textField_qty);

		JLabel label_billno = new JLabel("Bill No.");
		label_billno.setBounds(100, 220, 100, 25);
		jFrame.add(label_billno);
		final JTextField textField_billno = new JTextField();
		textField_billno.setBounds(250, 220, 100, 25);
		jFrame.add(textField_billno);



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
		clear.setBounds(400,450,100,25);
		jFrame.add(clear);
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				textField_name.setText(null);
				textField_bno.setText(null);
				textField_qty.setText(null);
//				textField_pa.setText(null);
//				textField_billdate.setText(null);
				textField_billno.setText(null);
			}
		});

	}

}
