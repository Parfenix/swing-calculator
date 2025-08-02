package parfenix.code.app;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
         "AC", "+/-", "%", "÷",
         "7", "8", "9", "×",
         "4", "5", "6", "-",
         "1", "2", "3", "+",
         "√", "0", ".", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String a = "0";
    String operator = null;
    String b = null;

    Calculator() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonsPanel.setBackground(customBlack);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(buttonsPanel);

        for (String value : buttonValues) {
            RoundButton button = getRoundButton(value);

            buttonsPanel.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("=")) {
                            if (a != null) {
                                b = displayLabel.getText();
                                double numA = Double.parseDouble(a);
                                double numB = Double.parseDouble(b);

                                switch (operator) {
                                    case "+" -> displayLabel.setText(removeZeroDecimal(numA + numB));
                                    case "-" -> displayLabel.setText(removeZeroDecimal(numA - numB));
                                    case "×" -> displayLabel.setText(removeZeroDecimal(numA * numB));
                                    case "÷" -> displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }

                        } else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                a = displayLabel.getText();
                                displayLabel.setText("0");
                                b = "0";
                            }
                            operator = buttonValue;
                        }
                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (buttonValue.equals("+/-")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        } else if (buttonValue.equals("%")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    } else {
                        if (buttonValue.equals(".")) {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if (buttonValue.equals("√")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay = Math.sqrt(numDisplay);
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        } else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        }
        frame.setVisible(true);
    }

    private RoundButton getRoundButton(String value) {
        RoundButton button = new RoundButton(value);

        if (Arrays.asList(topSymbols).contains(value)) {
            button.setBackground(customLightGray);
            button.setForeground(customBlack);
        } else if (Arrays.asList(rightSymbols).contains(value)) {
            button.setBackground(customOrange);
            button.setForeground(Color.white);
        } else {
            button.setBackground(customDarkGray);
            button.setForeground(Color.white);
        }
        return button;
    }

    void clearAll() {
        a = "0";
        operator = null;
        b = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
