package prog2.samcis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NumberConverterBoard extends JFrame {
    private JPanel numberPanel;
    private JPanel buttonPanel;

    private JButton convertButton, clearButton, exitButton;
    private JTextField decimalTF;
    private JTextArea binaryTA, octalTA, hexadecimalTA;
    private JLabel problemDisplayer;
    private JPanel headPanel;
    private ButtonsHandler buttonsHandler;

    public static void main(String[] args) {
        NumberConverterBoard board = new NumberConverterBoard();
    }

    public NumberConverterBoard() {
        setTitle("Number Converter");
        setSize(800, 400);
        headPanel = new JPanel();
        JLabel textArea = new JLabel();
        textArea.setText("This application helps you convert a decimal number to binary, octal and hexadecimal. Please enter the decimal number in the field provided. Then, press the convert key.");
        headPanel.add(textArea);
        numberPanel = new JPanel();
        setNumberPanel(numberPanel);
        buttonPanel = new JPanel();
        setButtonPanel(buttonPanel);
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, 1));
        pane.add(headPanel);
        pane.add(numberPanel);
        pane.add(buttonPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setButtonPanel(JPanel panel) {
        convertButton = new JButton("Convert");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
        buttonsHandler = new ButtonsHandler();
        convertButton.addActionListener(buttonsHandler);
        clearButton.addActionListener(buttonsHandler);
        exitButton.addActionListener(buttonsHandler);
        panel.add(convertButton);
        panel.add(clearButton);
        panel.add(exitButton);
    }

    public void setNumberPanel(JPanel panel) {
        JLabel decimalLabel = new JLabel("Decimal Number: ");
        decimalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        decimalTF = new JTextField(20);
        decimalTF.setFont(new Font("Arial", Font.BOLD, 20));

        binaryTA = new JTextArea(5, 20);
        binaryTA.setFont(new Font("Arial", Font.BOLD, 20));
        binaryTA.setEditable(false);

        octalTA = new JTextArea(5, 20);
        octalTA.setFont(new Font("Arial", Font.BOLD, 20));
        octalTA.setEditable(false);

        hexadecimalTA = new JTextArea(5, 20);
        hexadecimalTA.setFont(new Font("Arial", Font.BOLD, 20));
        hexadecimalTA.setEditable(false);

        panel.setLayout(new GridLayout(4, 2));
        panel.add(decimalLabel);
        panel.add(decimalTF);
        panel.add(new JLabel("Binary Number: "));
        panel.add(new JScrollPane(binaryTA));
        panel.add(new JLabel("Octal Number: "));
        panel.add(new JScrollPane(octalTA));
        panel.add(new JLabel("Hexadecimal Number: "));
        panel.add(new JScrollPane(hexadecimalTA));
    }

    private class ButtonsHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exitButton)
                System.exit(0);
            if (e.getSource() == clearButton) {
                decimalTF.setText("");
                binaryTA.setText("");
                octalTA.setText("");
                hexadecimalTA.setText("");
            }
            if (e.getSource() == convertButton) {
                try {
                    if (decimalTF.getText().equals("")) {
                        throw new Exception("No specified decimal number.");
                    } else {
                        double decimalNumber = Double.parseDouble(decimalTF.getText());
                        int integerPart = (int) decimalNumber;
                        double fractionalPart = decimalNumber - integerPart;
                        EquivalentNumbers number = new EquivalentNumbers();
                        number.setDecimalNumber(integerPart);

                        String binaryString = number.getBinaryString();
                        String octalString = number.getOctalString();
                        String hexadecimalString = number.getHexadecimalString();

                        // Add fractional part to binary representation
                        if (fractionalPart != 0) {
                            String fractionalBinary = convertFractionToBinary(fractionalPart);
                            binaryString += "." + fractionalBinary;
                        }

                        // Convert hexadecimal letters to uppercase
                        hexadecimalString = hexadecimalString.toUpperCase();

                        binaryTA.setText(binaryString);
                        octalTA.setText(octalString);
                        hexadecimalTA.setText(hexadecimalString);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(NumberConverterBoard.this, "One of the numbers does not follow the format of a number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(NumberConverterBoard.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private String convertFractionToBinary(double fraction) {
            StringBuilder binary = new StringBuilder();
            while (fraction != 0) {
                fraction *= 2;
                if (fraction >= 1) {
                    binary.append("1");
                    fraction -= 1;
                } else {
                    binary.append("0");
                }
            }
            return binary.toString();
        }
    }
}//end of class