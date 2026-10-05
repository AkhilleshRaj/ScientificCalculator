import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ScientificCalculator extends JFrame implements ActionListener {

    JLabel display;
    JButton radBtn;

    String text = "0";          // what is shown on the screen
    boolean newNumber = false;  // true after we show a result
    boolean degMode = false;    // false = radians

    // for the expression evaluator
    String exp;
    int pos;

    Color darkBg = new Color(32, 32, 32);
    Color btnColor = new Color(51, 51, 51);
    Color red = new Color(192, 57, 43);

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(1000, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(darkBg);
        setLayout(new BorderLayout());

        // the screen
        display = new JLabel("0", SwingConstants.RIGHT);
        display.setOpaque(true);
        display.setBackground(new Color(17, 17, 17));
        display.setForeground(new Color(160, 240, 255));
        display.setFont(new Font("Arial", Font.BOLD, 46));
        display.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        display.setPreferredSize(new Dimension(0, 250));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(darkBg);
        top.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        top.add(display);
        add(top, BorderLayout.NORTH);

        // the buttons
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(darkBg);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 8, 10));

        // row 0
        addButton(panel, "(", 0, 0, 1, 1);
        addButton(panel, ")", 1, 0, 1, 1);
        addButton(panel, "%", 2, 0, 1, 1);

        // row 1
        addButton(panel, "1/x", 0, 1, 1, 1);
        addButton(panel, "x²", 1, 1, 1, 1);
        addButton(panel, "x³", 2, 1, 1, 1);
        addButton(panel, "y^x", 3, 1, 1, 1);
        addButton(panel, "C", 4, 1, 1, 1);
        addButton(panel, "+/-", 5, 1, 1, 1);
        addButton(panel, "/", 6, 1, 1, 1);
        addButton(panel, "*", 7, 1, 1, 1);

        // row 2
        addButton(panel, "x!", 0, 2, 1, 1);
        addButton(panel, "√", 1, 2, 1, 1);
        addButton(panel, "2√", 2, 2, 1, 1);
        addButton(panel, "log", 3, 2, 1, 1);
        addButton(panel, "7", 4, 2, 1, 1);
        addButton(panel, "8", 5, 2, 1, 1);
        addButton(panel, "9", 6, 2, 1, 1);
        addButton(panel, "-", 7, 2, 1, 1);

        // row 3
        addButton(panel, "sin", 0, 3, 1, 1);
        addButton(panel, "cos", 1, 3, 1, 1);
        addButton(panel, "tan", 2, 3, 1, 1);
        addButton(panel, "4", 4, 3, 1, 1);
        addButton(panel, "5", 5, 3, 1, 1);
        addButton(panel, "6", 6, 3, 1, 1);
        addButton(panel, "+", 7, 3, 1, 1);

        // row 4
        addButton(panel, "sinh", 0, 4, 1, 1);
        addButton(panel, "cosh", 1, 4, 1, 1);
        addButton(panel, "tanh", 2, 4, 1, 1);
        addButton(panel, "2^x", 3, 4, 1, 1);
        addButton(panel, "1", 4, 4, 1, 1);
        addButton(panel, "2", 5, 4, 1, 1);
        addButton(panel, "3", 6, 4, 1, 1);
        addButton(panel, "=", 7, 4, 1, 2);

        // row 5
        radBtn = addButton(panel, "Rad", 0, 5, 1, 1);
        addButton(panel, "π", 1, 5, 1, 1);
        addButton(panel, "Rnd", 3, 5, 1, 1);
        addButton(panel, "0", 4, 5, 3, 1);
        addButton(panel, ".", 6, 5, 1, 1);

        add(panel, BorderLayout.CENTER);
    }

    JButton addButton(JPanel panel, String label, int x, int y, int w, int h) {
        JButton b = new JButton(label);
        b.setFont(new Font("Arial", Font.PLAIN, 18));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setContentAreaFilled(true);

        if (label.equals("C") || label.equals("=")) {
            b.setBackground(red);
        } else {
            b.setBackground(btnColor);
        }

        b.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        panel.add(b, gbc);
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            if (cmd.length() == 1 && Character.isDigit(cmd.charAt(0))) {
                if (newNumber || text.equals("0")) {
                    text = cmd;
                } else {
                    text = text + cmd;
                }
                newNumber = false;

            } else if (cmd.equals(".")) {
                if (newNumber) {
                    text = "0.";
                    newNumber = false;
                } else {
                    text = text + ".";
                }

            } else if (cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
                text = text + cmd;
                newNumber = false;

            } else if (cmd.equals("y^x")) {
                text = text + "^";
                newNumber = false;

            } else if (cmd.equals("(")) {
                if (newNumber || text.equals("0")) {
                    text = "(";
                } else {
                    text = text + "(";
                }
                newNumber = false;

            } else if (cmd.equals(")") || cmd.equals("%")) {
                text = text + cmd;
                newNumber = false;

            } else if (cmd.equals("C")) {
                text = "0";
                newNumber = false;

            } else if (cmd.equals("=")) {
                showResult(calculate(text));

            } else if (cmd.equals("Rad") || cmd.equals("Deg")) {
                degMode = !degMode;
                if (degMode) {
                    radBtn.setText("Deg");
                } else {
                    radBtn.setText("Rad");
                }

            } else if (cmd.equals("π")) {
                showResult(Math.PI);

            } else if (cmd.equals("Rnd")) {
                showResult(Math.random());

            } else {
                double n = calculate(text);

                if (cmd.equals("+/-")) showResult(-n);
                else if (cmd.equals("1/x")) showResult(1 / n);
                else if (cmd.equals("x²")) showResult(n * n);
                else if (cmd.equals("x³")) showResult(n * n * n);
                else if (cmd.equals("√")) showResult(Math.sqrt(n));
                else if (cmd.equals("2√")) showResult(Math.cbrt(n));
                else if (cmd.equals("log")) showResult(Math.log10(n));
                else if (cmd.equals("2^x")) showResult(Math.pow(2, n));
                else if (cmd.equals("x!")) showResult(factorial(n));
                else if (cmd.equals("sin")) showResult(Math.sin(toRad(n)));
                else if (cmd.equals("cos")) showResult(Math.cos(toRad(n)));
                else if (cmd.equals("tan")) showResult(Math.tan(toRad(n)));
                else if (cmd.equals("sinh")) showResult(Math.sinh(n));
                else if (cmd.equals("cosh")) showResult(Math.cosh(n));
                else if (cmd.equals("tanh")) showResult(Math.tanh(n));
            }

            display.setText(text);

        } catch (Exception ex) {
            display.setText("Error");
            text = "0";
            newNumber = true;
        }
    }

    double toRad(double n) {
        if (degMode) {
            return Math.toRadians(n);
        }
        return n;
    }

    void showResult(double r) {
        if (Double.isNaN(r) || Double.isInfinite(r)) {
            throw new ArithmeticException("bad result");
        }
        if (r == Math.floor(r) && Math.abs(r) < 1e15) {
            text = String.valueOf((long) r);
        } else {
            text = String.valueOf(r);
        }
        newNumber = true;
    }

    double factorial(double n) {
        if (n < 0 || n != Math.floor(n) || n > 170) {
            throw new ArithmeticException("bad factorial");
        }
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result = result * i;
        }
        return result;
    }

    double calculate(String s) {
        exp = s;
        pos = 0;
        double result = addSub();
        if (pos < exp.length()) {
            throw new RuntimeException("bad expression");
        }
        return result;
    }

    double addSub() {
        double result = mulDiv();
        while (pos < exp.length()) {
            char c = exp.charAt(pos);
            if (c == '+') {
                pos++;
                result = result + mulDiv();
            } else if (c == '-') {
                pos++;
                result = result - mulDiv();
            } else {
                break;
            }
        }
        return result;
    }

    double mulDiv() {
        double result = power();
        while (pos < exp.length()) {
            char c = exp.charAt(pos);
            if (c == '*') {
                pos++;
                result = result * power();
            } else if (c == '/') {
                pos++;
                result = result / power();
            } else {
                break;
            }
        }
        return result;
    }

    double power() {
        double base = unary();
        if (pos < exp.length() && exp.charAt(pos) == '^') {
            pos++;
            return Math.pow(base, power());
        }
        return base;
    }

    double unary() {
        if (pos < exp.length() && exp.charAt(pos) == '-') {
            pos++;
            return -unary();
        }
        double value = primary();
        while (pos < exp.length() && exp.charAt(pos) == '%') {
            pos++;
            value = value / 100;
        }
        return value;
    }

    double primary() {
        if (pos >= exp.length()) {
            throw new RuntimeException("unexpected end");
        }
        if (exp.charAt(pos) == '(') {
            pos++;
            double value = addSub();
            if (pos < exp.length() && exp.charAt(pos) == ')') {
                pos++;
            }
            return value;
        }
        int start = pos;
        while (pos < exp.length() && (Character.isDigit(exp.charAt(pos)) || exp.charAt(pos) == '.')) {
            pos++;
        }
        if (start == pos) {
            throw new RuntimeException("number expected");
        }
        return Double.parseDouble(exp.substring(start, pos));
    }

    public static void main(String[] args) {
        ScientificCalculator calc = new ScientificCalculator();
        calc.setVisible(true);
    }
}