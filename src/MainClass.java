import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainClass {

	public static void main(String[] args) {

		final JFrame frame = new JFrame();
        frame.setSize(600, 600);



        JButton button_purchase = new JButton("Purchase");
        button_purchase.setBounds(50, 50, 200, 25);

        JButton button_sales = new JButton("Sales");
        button_sales.setBounds(50, 80, 200, 25);

        JButton button_stock = new JButton("Clr Stock");
        button_stock.setBounds(50, 110, 200, 25);

        JButton quit = new JButton("Quit");
        quit.setBounds(50, 140, 200, 25);

        button_purchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Purchase.main(null);
            }
        });

        button_sales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Sales.main(null);
            }
        });

        button_stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Stock.main(null);
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });

//        JTextField jTextField = new JTextField();
//        jTextField.setBounds(50, 170, 200, 25);





        frame.add(button_purchase);
        frame.add(button_sales);
        frame.add(button_stock);
        frame.add(quit);

        frame.setLayout(null);
        frame.setVisible(true);


	}


}
