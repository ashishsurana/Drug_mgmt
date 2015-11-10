import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ashish on 6/11/15.
 */
public class Stock {
    public static void main(String[] args){
        final JFrame jFrame = new JFrame();
        jFrame.setLayout(null);
        jFrame.setSize(600, 600);
        jFrame.setTitle("Stock");
        jFrame.setVisible(true);

        JLabel label_name = new JLabel("Medicine Name ");
        label_name.setBounds(100, 100, 150, 25);
        jFrame.add(label_name);
        final JTextField textField_name = new JTextField();
        textField_name.setBounds(250, 100, 200, 25);
        jFrame.add(textField_name);

        JButton button_getstock = new JButton("Get Stock");
        button_getstock.setBounds(100,140,250,25);
        jFrame.add(button_getstock);
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
        clear.setBounds(400,450,100,25);
        jFrame.add(clear);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textField_name.setText(null);
            }
        });

    }
}
