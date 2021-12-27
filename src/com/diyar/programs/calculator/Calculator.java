package com.diyar.programs.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator extends JFrame {
    private final int NUMBER_COUNT = 10;
    private double a = 0;
    private double b = 0;
    private double result = 0;
    private boolean aIsAssigned = false;
    private boolean aIsRemovedFromInput = false;
    private boolean bIsAssigned = false;
    private String operationSign = "";

    private JTextField input = new JTextField("0");
    private JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

    private JButton btnAdd = new JButton("+"),
                    btnSubtract = new JButton("-"),
                    btnMultiply = new JButton("*"),
                    btnDivide = new JButton("/"),
                    btnClear = new JButton("C"),
                    btnBackspace = new JButton("<X"),
                    btnCalculate = new JButton("="),
                    btnPoint = new JButton(","),
                    btnSquare = new JButton("^2"),
                    btnChangeSign = new JButton("+/-"),
                    btnZero = new JButton("0");

    private ArrayList<JButton> numberButtons = new ArrayList<>(),
                                operationSignButtons = new ArrayList<>(),
                                otherButtons = new ArrayList<>();

    public Calculator() {
        super("Calculator");
        setBounds(200, 100, 350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);
        requestFocusInWindow();

        input.setPreferredSize(new Dimension(400, 80));
        input.setFont(new Font(input.getFont().getName(), input.getFont().getStyle(), 40));
        input.setHorizontalAlignment(JTextField.RIGHT);
        input.setEditable(false);

        //initialize number buttons
        numberButtons.add(new JButton("Empty"));
        for (int i = 1; i < NUMBER_COUNT; i++)
            numberButtons.add(new JButton("" + i));

        operationSignButtons.addAll(Arrays.asList(btnAdd, btnSubtract, btnMultiply, btnDivide));
        otherButtons.addAll(Arrays.asList(btnPoint, btnBackspace, btnClear, btnSquare, btnChangeSign, btnZero, btnCalculate));

        createGUI();
        addActionListenersToButtons();
        addKeyListenersToButtons();
        unsetButtonsFocusable();
    }

    private void createGUI() {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(input, BorderLayout.NORTH);
        this.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        //add buttons in a certain order
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSquare);
        buttonPanel.add(btnBackspace);
        buttonPanel.add(btnCalculate);
        buttonPanel.add(numberButtons.get(7));
        buttonPanel.add(numberButtons.get(8));
        buttonPanel.add(numberButtons.get(9));
        buttonPanel.add(btnAdd);
        buttonPanel.add(numberButtons.get(4));
        buttonPanel.add(numberButtons.get(5));
        buttonPanel.add(numberButtons.get(6));
        buttonPanel.add(btnSubtract);
        buttonPanel.add(numberButtons.get(1));
        buttonPanel.add(numberButtons.get(2));
        buttonPanel.add(numberButtons.get(3));
        buttonPanel.add(btnMultiply);
        buttonPanel.add(btnChangeSign);
        buttonPanel.add(btnZero);
        buttonPanel.add(btnPoint);
        buttonPanel.add(btnDivide);

        setFontSize(operationSignButtons, 20);
        setFontSize(otherButtons, 20);
        setFontSize(numberButtons, 24);
        btnZero.setFont(new Font(btnZero.getFont().getName(), btnZero.getFont().getStyle(), 24));
    }

    private void addActionListenersToButtons() {
        //numbers 1-9
        for (JButton button : numberButtons)
            button.addActionListener(e -> {
                if (input.getText().equals("0") || !aIsRemovedFromInput) {
                    input.setText(button.getText());
                    aIsRemovedFromInput = true;
                } else input.setText(input.getText() + button.getText());
            });

        //number 0
        btnZero.addActionListener(e -> {
            if (input.getText().equals("0") || !aIsRemovedFromInput) {
                input.setText(btnZero.getText());
                aIsRemovedFromInput = true;
            } else input.setText(input.getText() + btnZero.getText());
        });

        btnPoint.addActionListener(e -> {
            if (!input.getText().contains(btnPoint.getText()) || !aIsRemovedFromInput) {
                input.setText(input.getText() + btnPoint.getText());
                aIsRemovedFromInput = true;
            }
        });

        btnClear.addActionListener(e -> {
            input.setText("0");
            operationSign = "";
            aIsAssigned = bIsAssigned = aIsRemovedFromInput = false;
        });

        btnBackspace.addActionListener(e -> {
            if (input.getText().length() > 1)
                input.setText(input.getText().substring(0, input.getText().length() - 1));
            else input.setText("0");
        });

        //operation signs
        for (JButton button : operationSignButtons)
            button.addActionListener(e -> {
                if (!aIsAssigned) {
                    a = Double.parseDouble(input.getText().replace(",", "."));
                    aIsAssigned = true;
                    aIsRemovedFromInput = false;
                }
                operationSign = button.getText();
            });

        btnCalculate.addActionListener(e -> {
            if(!bIsAssigned) b = Double.parseDouble(input.getText().replace(",", "."));
            switch (operationSign) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    result = a / b;
                    break;
                default:
                    result = a;
            }
            outputResult(result);
            a = result;
            bIsAssigned = aIsRemovedFromInput = false;
        });

        btnChangeSign.addActionListener(e -> {
            if (!input.getText().equals("0")) input.setText("-" + input.getText());
        });

        btnSquare.addActionListener(e -> {
            double d = Double.parseDouble(input.getText().replace(",", "."));
            outputResult(d * d);
        });
    }

    private void addKeyListenersToButtons() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1: case KeyEvent.VK_NUMPAD1:
                        numberButtons.get(1).doClick();
                        break;
                    case KeyEvent.VK_2: case KeyEvent.VK_NUMPAD2:
                        numberButtons.get(2).doClick();
                        break;
                    case KeyEvent.VK_3: case KeyEvent.VK_NUMPAD3:
                        numberButtons.get(3).doClick();
                        break;
                    case KeyEvent.VK_4: case KeyEvent.VK_NUMPAD4:
                        numberButtons.get(4).doClick();
                        break;
                    case KeyEvent.VK_5: case KeyEvent.VK_NUMPAD5:
                        numberButtons.get(5).doClick();
                        break;
                    case KeyEvent.VK_6: case KeyEvent.VK_NUMPAD6:
                        numberButtons.get(6).doClick();
                        break;
                    case KeyEvent.VK_7: case KeyEvent.VK_NUMPAD7:
                        numberButtons.get(7).doClick();
                        break;
                    case KeyEvent.VK_8: case KeyEvent.VK_NUMPAD8:
                        numberButtons.get(8).doClick();
                        break;
                    case KeyEvent.VK_9: case KeyEvent.VK_NUMPAD9:
                        numberButtons.get(9).doClick();
                        break;
                    case KeyEvent.VK_0: case KeyEvent.VK_NUMPAD0:
                        btnZero.doClick();
                        break;
                    case KeyEvent.VK_ESCAPE: case KeyEvent.VK_DELETE:
                        btnClear.doClick();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        btnBackspace.doClick();
                        break;
                    case KeyEvent.VK_ENTER: case KeyEvent.VK_EQUALS:
                        btnCalculate.doClick();
                        break;
                    case KeyEvent.VK_ADD: case KeyEvent.VK_PLUS:
                        btnAdd.doClick();
                        break;
                    case KeyEvent.VK_SUBTRACT: case KeyEvent.VK_MINUS:
                        btnSubtract.doClick();
                        break;
                    case KeyEvent.VK_MULTIPLY: case KeyEvent.VK_ASTERISK:
                        btnMultiply.doClick();
                        break;
                    case KeyEvent.VK_DIVIDE: case KeyEvent.VK_SLASH:
                        btnDivide.doClick();
                        break;
                    case KeyEvent.VK_PERIOD: case KeyEvent.VK_COMMA: case KeyEvent.VK_DECIMAL:
                        btnPoint.doClick();
                }
            }
        });
    }

    private void unsetButtonsFocusable() {
        for (JButton button : numberButtons)
            button.setFocusable(false);
        for (JButton button : operationSignButtons)
            button.setFocusable(false);
        for (JButton button : otherButtons)
            button.setFocusable(false);
    }

    private void outputResult(double res) {
        if (res - Math.floor(res) == 0)
            input.setText((long) res + "");
        else input.setText(new DecimalFormat("0.######").format(res));
    }

    private void setFontSize(ArrayList<JButton> buttonList, int buttonFontSize) {
        Font font = new Font(buttonList.get(0).getFont().getName(), buttonList.get(0).getFont().getStyle(),
                buttonFontSize);
        for (JButton button: buttonList)
            button.setFont(font);
    }

    public static void main(String[] args) {
        new Calculator().setVisible(true);
    }
}