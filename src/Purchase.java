import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Purchase {
	private String name;
	private String party;
	private String bach_no;
	private String bill_date;
	private String qty;
	private String billno;

	
	public static void main(String[] args) {
		final JFrame jFrame = new JFrame();
		jFrame.setSize(600, 600);
        jFrame.setTitle("Purchase Entry");

		JLabel label_name = new JLabel("Medicine");
		label_name.setBounds(100, 100, 100, 25);
		jFrame.add(label_name);
        JTextField textField_name = new JTextField();
        textField_name.setBounds(250,100,200,25);
        jFrame.add(textField_name);

        JLabel label_bno = new JLabel("Batch No.");
        label_bno.setBounds(100, 140, 100, 25);
        jFrame.add(label_bno);
        JTextField textField_bno = new JTextField();
        textField_bno.setBounds(250, 140, 200, 25);
        jFrame.add(textField_bno);

        JLabel label_qty = new JLabel("Quantity");
        label_qty.setBounds(100, 180, 100, 25);
        jFrame.add(label_qty);
        JTextField textField_qty = new JTextField();
        textField_qty.setBounds(250, 180, 200, 25);
        jFrame.add(textField_qty);

        JLabel label_party = new JLabel("Supplier's Name");
        label_party.setBounds(100, 220, 120, 25);
        jFrame.add(label_party);
        JTextField textField_party = new JTextField();
        textField_party.setBounds(250, 220, 200, 25);
        jFrame.add(textField_party);

        JLabel label_billno = new JLabel("Bill No");
        label_billno.setBounds(100, 260, 100, 25);
        jFrame.add(label_billno);
        JTextField textField_billno = new JTextField();
        textField_billno.setBounds(250, 260, 200, 25);
        jFrame.add(textField_billno);

        JLabel label_billdate = new JLabel("Bill Date");
        label_billdate.setBounds(100, 300, 100, 25);
        jFrame.add(label_billdate);
        JTextField textField_billdate = new JTextField();
        textField_billdate.setBounds(250, 300, 200, 25);
        jFrame.add(textField_billdate);


//        AutoCompleteDecorator.decorate(list, textField_name, ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
//        AutoSuggestor autoSuggestor;// = new AutoSuggestor()

        JComboBox comboBox = new JComboBox();
        Object[] elements = new Object[] {"Cat", "Dog", "Lion", "Mouse"};
//        AutoCompleteSupport.install(comboBox, GlazedLists.eventListOf(elements));

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
                //to do
            }
        });

		jFrame.setLayout(null);
		jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void getter(){
		System.out.print("Yes");
	}

}
