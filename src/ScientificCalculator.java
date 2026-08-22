import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScientificCalculator extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BG =
            new Color(245, 247, 252);

    private static final Color WHITE =
            Color.WHITE;

    private static final Color TEXT =
            new Color(30, 42, 60);

    private static final Color SECONDARY =
            new Color(90, 110, 145);

    private static final Color PURPLE =
            new Color(140, 75, 210);

    private static final Color LIGHT_PURPLE =
            new Color(239, 230, 250);

    private static final Color BLUE =
            new Color(48, 115, 220);

    private static final Color GREEN =
            new Color(30, 175, 125);

    private static final Color RED =
            new Color(220, 65, 75);

    private static final Color ORANGE =
            new Color(240, 145, 35);

    private static final Color CYAN =
            new Color(40, 170, 200);

    private static final Color BORDER =
            new Color(215, 221, 235);

    private static final Color NAVY =
            new Color(25, 50, 85);

    // =========================================================
    // FRACTION CONVERSION
    // =========================================================

    private void convertDisplayedNumberToFraction() {

        String valueText = display.getText().trim();

        if (valueText.isEmpty() || valueText.equals("0")) {
            display.requestFocusInWindow();
            return;
        }

        try {
            double value = Double.parseDouble(valueText);

            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new NumberFormatException();
            }

            String fraction = toFraction(value);

            addHistory(valueText + " = " + fraction);
            display.setText(fraction);
            display.setCaretPosition(display.getText().length());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only a numeric answer can be converted to a fraction.",
                    "Fraction Conversion",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        display.requestFocusInWindow();
    }

    private String toFraction(double value) {

        if (Math.abs(value) < 1e-12) {
            return "0";
        }

        boolean negative = value < 0;
        value = Math.abs(value);

        long whole = (long) Math.floor(value);
        double remainder = value - whole;

        if (remainder < 1e-12) {
            return (negative ? "-" : "") + whole;
        }

        final long maxDenominator = 100000;
        long bestNumerator = 0;
        long bestDenominator = 1;
        double bestError = Double.MAX_VALUE;

        for (long denominator = 1; denominator <= maxDenominator; denominator++) {
            long numerator = Math.round(remainder * denominator);
            double candidate = (double) numerator / denominator;
            double error = Math.abs(remainder - candidate);

            if (error < bestError) {
                bestError = error;
                bestNumerator = numerator;
                bestDenominator = denominator;
            }

            if (error < 1e-10) {
                break;
            }
        }

        long gcd = gcd(bestNumerator, bestDenominator);
        bestNumerator /= gcd;
        bestDenominator /= gcd;

        if (bestNumerator == 0) {
            return (negative ? "-" : "") + whole;
        }

        String sign = negative ? "-" : "";

        if (whole == 0) {
            return sign + bestNumerator + "/" + bestDenominator;
        }

        // Return an improper fraction so it can be reused in expressions.
        long improperNumerator =
                whole * bestDenominator + bestNumerator;

        return sign + improperNumerator + "/" + bestDenominator;
    }

    private long gcd(long a, long b) {

        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a == 0 ? 1 : a;
    }

    // =========================================================
    // HISTORY
    // =========================================================

    private static final String HISTORY_FILE =
            System.getProperty("user.home")
                    + File.separator
                    + "CalculatorSuite"
                    + File.separator
                    + "scientific_history.txt";

    private final List<String> history =
            new ArrayList<>();

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField display;

    private JLabel modeLabel;

    private boolean degreeMode = true;

    // =========================================================
    // CONSTRUCTORS
    // =========================================================

    public ScientificCalculator() {
        this(null);
    }

    public ScientificCalculator(JFrame parent) {

        setTitle(
                "Scientific Calculator - Krish Bhardwaj"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1050, 680)
        );

        loadHistory();

        createUI();

        setupKeyboard();

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(parent);
    }

    // =========================================================
    // MAIN UI
    // =========================================================

    private void createUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(BG);

        root.setBorder(
                new EmptyBorder(
                        30,
                        32,
                        28,
                        32
                )
        );

        // Header
        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        // Calculator body
        JPanel calculator =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );

        calculator.setBackground(WHITE);

        calculator.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                28,
                                28,
                                25,
                                28
                        )
                )
        );

        // Display
        calculator.add(
                createDisplay(),
                BorderLayout.NORTH
        );

        // Main keypad
        JPanel main =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                24,
                                0
                        )
                );

        main.setOpaque(false);

        main.add(
                createScientificFunctions()
        );

        main.add(
                createNumericKeypad()
        );

        calculator.add(
                main,
                BorderLayout.CENTER
        );

        root.add(
                calculator,
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        header.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        24,
                        0
                )
        );

        JPanel left =
                new JPanel();

        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Scientific Calculator"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        title.setForeground(PURPLE);

        JLabel subtitle =
                new JLabel(
                        "Advanced expression calculator"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(SECONDARY);

        left.add(title);

        left.add(
                Box.createVerticalStrut(4)
        );

        left.add(subtitle);

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        right.setOpaque(false);

        JButton historyButton =
                createButton(
                        "History",
                        GREEN,
                        Color.WHITE
                );

        historyButton.setPreferredSize(
                new Dimension(
                        100,
                        45
                )
        );

        historyButton.addActionListener(
                e -> showHistory()
        );

        JButton backButton =
                createButton(
                        "BACK",
                        NAVY,
                        Color.WHITE
                );

        backButton.setPreferredSize(
                new Dimension(
                        110,
                        45
                )
        );

        backButton.addActionListener(
                e -> backToDashboard()
        );

        right.add(historyButton);

        right.add(backButton);

        header.add(
                left,
                BorderLayout.WEST
        );

        header.add(
                right,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // DISPLAY
    // =========================================================

    private JPanel createDisplay() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                7
                        )
                );

        panel.setOpaque(false);

        JPanel labelPanel =
                new JPanel(
                        new BorderLayout()
                );

        labelPanel.setOpaque(false);

        JLabel expression =
                new JLabel(
                        "Expression"
                );

        expression.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        expression.setForeground(SECONDARY);

        modeLabel =
                new JLabel("DEG");

        modeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        modeLabel.setForeground(GREEN);

        labelPanel.add(
                expression,
                BorderLayout.WEST
        );

        labelPanel.add(
                modeLabel,
                BorderLayout.EAST
        );

        panel.add(
                labelPanel,
                BorderLayout.NORTH
        );

        display =
                new JTextField("0");

        display.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        display.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        display.setForeground(TEXT);

        display.setBackground(
                new Color(
                        249,
                        251,
                        255
                )
        );

        display.setCaretColor(PURPLE);

        display.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                CYAN,
                                1
                        ),
                        new EmptyBorder(
                                12,
                                15,
                                12,
                                15
                        )
                )
        );

        panel.add(
                display,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // SCIENTIFIC FUNCTIONS
    // =========================================================

    private JPanel createScientificFunctions() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        panel.setOpaque(false);

        JLabel heading =
                new JLabel(
                        "Scientific Functions"
                );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        heading.setForeground(PURPLE);

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        JPanel grid =
                new JPanel(
                        new GridLayout(
                                4,
                                4,
                                10,
                                10
                        )
                );

        grid.setOpaque(false);

        addFunction(
                grid,
                "sin",
                "sin("
        );

        addFunction(
                grid,
                "cos",
                "cos("
        );

        addFunction(
                grid,
                "tan",
                "tan("
        );

        addFunction(
                grid,
                "sin⁻¹",
                "asin("
        );

        addFunction(
                grid,
                "cos⁻¹",
                "acos("
        );

        addFunction(
                grid,
                "tan⁻¹",
                "atan("
        );

        addFunction(
                grid,
                "log",
                "log("
        );

        addFunction(
                grid,
                "ln",
                "ln("
        );

        addFunction(
                grid,
                "√",
                "sqrt("
        );

        addFunction(
                grid,
                "x²",
                "^2"
        );

        addFunction(
                grid,
                "x³",
                "^3"
        );

        addFunction(
                grid,
                "xʸ",
                "^"
        );

        addFunction(
                grid,
                "π",
                "pi"
        );

        addFunction(
                grid,
                "e",
                "e"
        );

        addFunction(
                grid,
                "!",
                "!"
        );

        JButton mode =
                createButton(
                        "DEG/RAD",
                        GREEN,
                        Color.WHITE
                );

        mode.addActionListener(
                e -> toggleMode()
        );

        grid.add(mode);

        JButton fraction =
                createButton(
                        "a/b",
                        BLUE,
                        Color.WHITE
                );

        fraction.addActionListener(
                e -> convertDisplayedNumberToFraction()
        );

        grid.add(fraction);

        panel.add(
                grid,
                BorderLayout.CENTER
        );

        JLabel keyboard =
                new JLabel(
                        "Keyboard: 0-9   +   -   *   /   ^   Enter   Backspace   Delete   Esc"
                );

        keyboard.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        keyboard.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        keyboard.setForeground(SECONDARY);

        panel.add(
                keyboard,
                BorderLayout.SOUTH
        );

        return panel;
    }

    private void addFunction(
            JPanel panel,
            String text,
            String value
    ) {

        JButton button =
                createButton(
                        text,
                        LIGHT_PURPLE,
                        PURPLE
                );

        button.addActionListener(
                e -> insert(value)
        );

        panel.add(button);
    }

    // =========================================================
    // NUMERIC KEYPAD
    // =========================================================

    private JPanel createNumericKeypad() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        panel.setOpaque(false);

        JLabel heading =
                new JLabel(
                        "Numeric Keypad"
                );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        heading.setForeground(BLUE);

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        JPanel grid =
                new JPanel(
                        new GridLayout(
                                5,
                                4,
                                10,
                                10
                        )
                );

        grid.setOpaque(false);

        addNumber(
                grid,
                "7"
        );

        addNumber(
                grid,
                "8"
        );

        addNumber(
                grid,
                "9"
        );

        addOperator(
                grid,
                "/"
        );

        addNumber(
                grid,
                "4"
        );

        addNumber(
                grid,
                "5"
        );

        addNumber(
                grid,
                "6"
        );

        addOperator(
                grid,
                "*"
        );

        addNumber(
                grid,
                "1"
        );

        addNumber(
                grid,
                "2"
        );

        addNumber(
                grid,
                "3"
        );

        addOperator(
                grid,
                "-"
        );

        addNumber(
                grid,
                "0"
        );

        addNumber(
                grid,
                "."
        );

        JButton percent =
                createButton(
                        "%",
                        CYAN,
                        Color.WHITE
                );

        percent.addActionListener(
                e -> insert("%")
        );

        grid.add(percent);

        addOperator(
                grid,
                "+"
        );

        JButton clear =
                createButton(
                        "C",
                        RED,
                        Color.WHITE
                );

        clear.addActionListener(
                e -> clearDisplay()
        );

        grid.add(clear);

        JButton back =
                createButton(
                        "Backspace",
                        ORANGE,
                        Color.WHITE
                );

        back.addActionListener(
                e -> backspace()
        );

        grid.add(back);

        JButton left =
                createButton(
                        "(",
                        new Color(
                                248,
                                250,
                                255
                        ),
                        TEXT
                );

        left.addActionListener(
                e -> insert("(")
        );

        grid.add(left);

        JButton right =
                createButton(
                        ")",
                        new Color(
                                248,
                                250,
                                255
                        ),
                        TEXT
                );

        right.addActionListener(
                e -> insert(")")
        );

        grid.add(right);

        panel.add(
                grid,
                BorderLayout.CENTER
        );

        JButton equal =
                createButton(
                        "=",
                        BLUE,
                        Color.WHITE
                );

        equal.setPreferredSize(
                new Dimension(
                        0,
                        45
                )
        );

        equal.addActionListener(
                e -> calculate()
        );

        panel.add(
                equal,
                BorderLayout.SOUTH
        );

        return panel;
    }

    private void addNumber(
            JPanel panel,
            String text
    ) {

        JButton button =
                createButton(
                        text,
                        new Color(
                                248,
                                250,
                                255
                        ),
                        TEXT
                );

        button.addActionListener(
                e -> insert(text)
        );

        panel.add(button);
    }

    private void addOperator(
            JPanel panel,
            String text
    ) {

        JButton button =
                createButton(
                        text,
                        PURPLE,
                        Color.WHITE
                );

        button.addActionListener(
                e -> insert(text)
        );

        panel.add(button);
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        button.setBackground(
                background
        );

        button.setForeground(
                foreground
        );

        button.setFocusPainted(false);

        button.setOpaque(true);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        return button;
    }

    // =========================================================
    // INSERT
    // =========================================================

    private void insert(
            String value
    ) {

        String current =
                display.getText();

        int caret =
                display.getCaretPosition();

        if (
                current.equals("0")
                        && !value.equals(".")
                        && !value.equals("%")
                        && !value.equals(")")
        ) {

            display.setText(value);

            display.setCaretPosition(
                    value.length()
            );

        } else {

            String result =
                    current.substring(
                            0,
                            caret
                    )
                            + value
                            + current.substring(
                            caret
                    );

            display.setText(result);

            display.setCaretPosition(
                    caret + value.length()
            );
        }

        display.requestFocusInWindow();
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearDisplay() {

        display.setText("0");

        display.setCaretPosition(1);

        display.requestFocusInWindow();
    }

    // =========================================================
    // BACKSPACE
    // =========================================================

    private void backspace() {

        String text =
                display.getText();

        int caret =
                display.getCaretPosition();

        if (
                caret <= 0
        ) {

            return;
        }

        if (
                text.length() <= 1
        ) {

            clearDisplay();

            return;
        }

        String result =
                text.substring(
                        0,
                        caret - 1
                )
                        + text.substring(
                        caret
                );

        display.setText(result);

        display.setCaretPosition(
                caret - 1
        );
    }

    // =========================================================
    // DEG / RAD
    // =========================================================

    private void toggleMode() {

        degreeMode =
                !degreeMode;

        modeLabel.setText(
                degreeMode
                        ? "DEG"
                        : "RAD"
        );

        display.requestFocusInWindow();
    }

    // =========================================================
    // CALCULATE
    // =========================================================

    private void calculate() {

        String expression =
                display.getText().trim();

        if (
                expression.isEmpty()
        ) {

            return;
        }

        try {

            Parser parser =
                    new Parser(
                            expression,
                            degreeMode
                    );

            double result =
                    parser.parse();

            if (
                    Double.isNaN(result)
                            || Double.isInfinite(result)
            ) {

                throw new RuntimeException(
                        "Invalid mathematical result"
                );
            }

            String answer =
                    formatResult(result);

            addHistory(
                    expression
                            + " = "
                            + answer
            );

            display.setText(answer);

            display.setCaretPosition(
                    answer.length()
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid expression\n\n"
                            + ex.getMessage(),
                    "Calculation Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        display.requestFocusInWindow();
    }

    // =========================================================
    // FORMAT RESULT
    // =========================================================

    private String formatResult(
            double value
    ) {

        if (
                Math.abs(value)
                        < 0.000000000001
        ) {

            value = 0;
        }

        if (
                Math.abs(
                        value
                                - Math.rint(value)
                )
                        < 0.000000000001
        ) {

            return String.format(
                    "%.0f",
                    value
            );
        }

        return String.format(
                "%.12f",
                value
        )
                .replaceAll(
                        "0+$",
                        ""
                )
                .replaceAll(
                        "\\.$",
                        ""
                );
    }

    // =========================================================
    // FRACTION CONVERSION
    // =========================================================

    private void convertDisplayedNumberToFraction1() {

        String valueText = display.getText().trim();

        if (valueText.isEmpty() || valueText.equals("0")) {
            display.requestFocusInWindow();
            return;
        }

        try {
            double value = Double.parseDouble(valueText);

            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new NumberFormatException();
            }

            String fraction = toFraction(value);

            addHistory(valueText + " = " + fraction);
            display.setText(fraction);
            display.setCaretPosition(display.getText().length());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only a numeric answer can be converted to a fraction.",
                    "Fraction Conversion",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        display.requestFocusInWindow();
    }

    private String toFraction1(double value) {

        if (Math.abs(value) < 1e-12) {
            return "0";
        }

        boolean negative = value < 0;
        value = Math.abs(value);

        long whole = (long) Math.floor(value);
        double remainder = value - whole;

        if (remainder < 1e-12) {
            return (negative ? "-" : "") + whole;
        }

        final long maxDenominator = 100000;
        long bestNumerator = 0;
        long bestDenominator = 1;
        double bestError = Double.MAX_VALUE;

        for (long denominator = 1; denominator <= maxDenominator; denominator++) {
            long numerator = Math.round(remainder * denominator);
            double candidate = (double) numerator / denominator;
            double error = Math.abs(remainder - candidate);

            if (error < bestError) {
                bestError = error;
                bestNumerator = numerator;
                bestDenominator = denominator;
            }

            if (error < 1e-10) {
                break;
            }
        }

        long gcd = gcd(bestNumerator, bestDenominator);
        bestNumerator /= gcd;
        bestDenominator /= gcd;

        if (bestNumerator == 0) {
            return (negative ? "-" : "") + whole;
        }

        String sign = negative ? "-" : "";

        if (whole == 0) {
            return sign + bestNumerator + "/" + bestDenominator;
        }

        // Return an improper fraction so it can be reused in expressions.
        long improperNumerator =
                whole * bestDenominator + bestNumerator;

        return sign + improperNumerator + "/" + bestDenominator;
    }

    private long gcd1(long a, long b) {

        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a == 0 ? 1 : a;
    }

    // =========================================================
    // HISTORY
    // =========================================================

    private void addHistory(
            String calculation
    ) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy, hh:mm:ss a"
                );

        String date =
                LocalDateTime.now()
                        .format(formatter);

        history.add(
                calculation
                        + "    |    "
                        + date
        );

        saveHistory();
    }

    private void saveHistory() {

        createHistoryFolder();

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(
                                        HISTORY_FILE
                                )
                        )
        ) {

            for (
                    String item : history
            ) {

                writer.write(item);

                writer.newLine();
            }

        } catch (IOException ignored) {
        }
    }

    private void loadHistory() {

        File file =
                new File(
                        HISTORY_FILE
                );

        if (
                !file.exists()
        ) {

            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line;

            while (
                    (line = reader.readLine())
                            != null
            ) {

                if (
                        !line.trim().isEmpty()
                ) {

                    history.add(line);
                }
            }

        } catch (IOException ignored) {
        }
    }

    private void createHistoryFolder() {

        File file =
                new File(
                        HISTORY_FILE
                );

        File parent =
                file.getParentFile();

        if (
                parent != null
                        && !parent.exists()
        ) {

            parent.mkdirs();
        }
    }

    // =========================================================
    // HISTORY WINDOW
    // =========================================================

    private void showHistory() {

        JFrame frame =
                new JFrame(
                        "Scientific Calculation History"
                );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(
                1000,
                750
        );

        frame.setLocationRelativeTo(this);

        JPanel root =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        root.setBackground(BG);

        root.setBorder(
                new EmptyBorder(
                        28,
                        25,
                        25,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        "Scientific Calculation History"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(PURPLE);

        root.add(
                title,
                BorderLayout.NORTH
        );

        JTextArea area =
                new JTextArea();

        area.setEditable(false);

        area.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        area.setForeground(TEXT);

        area.setBackground(WHITE);

        StringBuilder text =
                new StringBuilder();

        for (
                int i = history.size() - 1;
                i >= 0;
                i--
        ) {

            text.append(
                    history.get(i)
            );

            text.append("\n\n");
        }

        if (
                text.length() == 0
        ) {

            text.append(
                    "No calculation history yet."
            );
        }

        area.setText(
                text.toString()
        );

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setBorder(
                new LineBorder(
                        new Color(
                                120,
                                130,
                                150
                        ),
                        1
                )
        );

        root.add(
                scroll,
                BorderLayout.CENTER
        );

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        bottom.setOpaque(false);

        JButton clear =
                createButton(
                        "Clear History",
                        RED,
                        Color.WHITE
                );

        clear.setPreferredSize(
                new Dimension(
                        155,
                        50
                )
        );

        clear.addActionListener(
                e -> {

                    int choice =
                            JOptionPane.showConfirmDialog(
                                    frame,
                                    "Clear all scientific calculation history?",
                                    "Clear History",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );

                    if (
                            choice
                                    == JOptionPane.YES_OPTION
                    ) {

                        history.clear();

                        saveHistory();

                        area.setText(
                                "No calculation history yet."
                        );
                    }
                }
        );

        JButton close =
                createButton(
                        "Close",
                        BLUE,
                        Color.WHITE
                );

        close.setPreferredSize(
                new Dimension(
                        100,
                        50
                )
        );

        close.addActionListener(
                e -> frame.dispose()
        );

        bottom.add(clear);

        bottom.add(close);

        root.add(
                bottom,
                BorderLayout.SOUTH
        );

        frame.setContentPane(root);

        frame.setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        frame.setVisible(true);
    }

    // =========================================================
    // KEYBOARD
    // =========================================================

    private void setupKeyboard() {

        display.addKeyListener(
                new KeyAdapter() {

                    @Override
                    public void keyPressed(
                            KeyEvent e
                    ) {

                        switch (
                                e.getKeyCode()
                        ) {

                            case KeyEvent.VK_ENTER:

                                calculate();

                                e.consume();

                                break;

                            case KeyEvent.VK_ESCAPE:

                                clearDisplay();

                                e.consume();

                                break;

                            case KeyEvent.VK_DELETE:

                                clearDisplay();

                                e.consume();

                                break;

                            case KeyEvent.VK_BACK_SPACE:

                                backspace();

                                e.consume();

                                break;

                            default:

                                break;
                        }
                    }
                }
        );

        display.requestFocusInWindow();
    }

    // =========================================================
    // EXPRESSION PARSER
    // =========================================================

    private static class Parser {

        private final String input;

        private final boolean degreeMode;

        private int position = 0;

        Parser(
                String expression,
                boolean degreeMode
        ) {

            this.input =
                    expression
                            .replace(
                                    "×",
                                    "*"
                            )
                            .replace(
                                    "÷",
                                    "/"
                            )
                            .replace(
                                    "−",
                                    "-"
                            )
                            .replace(
                                    "π",
                                    "pi"
                            )
                            .replaceAll(
                                    "\\s+",
                                    ""
                            );

            this.degreeMode =
                    degreeMode;
        }

        // =====================================================
        // PARSE
        // =====================================================

        double parse() {

            if (
                    input.isEmpty()
            ) {

                throw new RuntimeException(
                        "Empty expression"
                );
            }

            double result =
                    parseExpression();

            if (
                    position < input.length()
            ) {

                throw new RuntimeException(
                        "Unexpected character: "
                                + input.charAt(position)
                );
            }

            return result;
        }

        // =====================================================
        // +
        // =====================================================

        private double parseExpression() {

            double result =
                    parseTerm();

            while (
                    position < input.length()
            ) {

                if (
                        match('+')
                ) {

                    result +=
                            parseTerm();

                } else if (
                        match('-')
                ) {

                    result -=
                            parseTerm();

                } else {

                    break;
                }
            }

            return result;
        }

        // =====================================================
        // *
        // =====================================================

        private double parseTerm() {

            double result =
                    parsePower();

            while (
                    position < input.length()
            ) {

                if (
                        match('*')
                ) {

                    result *=
                            parsePower();

                } else if (
                        match('/')
                ) {

                    double divisor =
                            parsePower();

                    if (
                            divisor == 0
                    ) {

                        throw new ArithmeticException(
                                "Cannot divide by zero"
                        );
                    }

                    result /=
                            divisor;

                } else if (
                        startsPrimary()
                ) {

                    // Implicit multiplication
                    // 2(3+4)
                    // 2sin(30)
                    // 5pi

                    result *=
                            parsePower();

                } else {

                    break;
                }
            }

            return result;
        }

        // =====================================================
        // POWER
        // =====================================================

        private double parsePower() {

            double result =
                    parseUnary();

            if (
                    match('^')
            ) {

                double exponent =
                        parsePower();

                result =
                        Math.pow(
                                result,
                                exponent
                        );
            }

            return result;
        }

        // =====================================================
        // UNARY
        // =====================================================

        private double parseUnary() {

            if (
                    match('+')
            ) {

                return parseUnary();
            }

            if (
                    match('-')
            ) {

                return -parseUnary();
            }

            return parsePostfix();
        }

        // =====================================================
        // POSTFIX
        // =====================================================

        private double parsePostfix() {

            double result =
                    parsePrimary();

            while (
                    position < input.length()
            ) {

                if (
                        match('!')
                ) {

                    result =
                            factorial(
                                    result
                            );

                } else if (
                        match('%')
                ) {

                    result /=
                            100.0;

                } else {

                    break;
                }
            }

            return result;
        }

        // =====================================================
        // PRIMARY
        // =====================================================

        private double parsePrimary() {

            if (
                    position >= input.length()
            ) {

                throw new RuntimeException(
                        "Incomplete expression"
                );
            }

            // Parentheses
            if (
                    match('(')
            ) {

                double result =
                        parseExpression();

                if (
                        !match(')')
                ) {

                    throw new RuntimeException(
                            "Missing ')'"
                    );
                }

                return result;
            }

            char c =
                    input.charAt(position);

            // Number
            if (
                    Character.isDigit(c)
                            || c == '.'
            ) {

                return parseNumber();
            }

            // Function / constant
            if (
                    Character.isLetter(c)
            ) {

                String word =
                        parseWord();

                return evaluateWord(
                        word
                );
            }

            throw new RuntimeException(
                    "Unexpected character: "
                            + c
            );
        }

        // =====================================================
        // NUMBER
        // =====================================================

        private double parseNumber() {

            int start =
                    position;

            boolean decimal =
                    false;

            boolean exponent =
                    false;

            while (
                    position < input.length()
            ) {

                char c =
                        input.charAt(position);

                if (
                        Character.isDigit(c)
                ) {

                    position++;

                } else if (
                        c == '.'
                                && !decimal
                ) {

                    decimal = true;

                    position++;

                } else if (
                        (c == 'e'
                                || c == 'E')
                                && !exponent
                ) {

                    exponent = true;

                    position++;

                    if (
                            position < input.length()
                                    && (
                                    input.charAt(position) == '+'
                                            || input.charAt(position) == '-'
                            )
                    ) {

                        position++;
                    }

                } else {

                    break;
                }
            }

            String number =
                    input.substring(
                            start,
                            position
                    );

            double numerator = Double.parseDouble(number);

            // Support fraction expressions such as 1/2 or 5/4.
            // Division still works normally when the slash is not followed by a number.
            if (position < input.length() && input.charAt(position) == '/') {
                int slash = position;
                position++;

                int denominatorStart = position;
                while (position < input.length()
                        && (Character.isDigit(input.charAt(position))
                        || input.charAt(position) == '.')) {
                    position++;
                }

                if (denominatorStart < position) {
                    String denominatorText = input.substring(
                            denominatorStart,
                            position
                    );
                    double denominator = Double.parseDouble(denominatorText);

                    if (denominator == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }

                    return numerator / denominator;
                }

                position = slash;
            }

            return numerator;
        }

        // =====================================================
        // WORD
        // =====================================================

        private String parseWord() {

            int start =
                    position;

            while (
                    position < input.length()
                            && Character.isLetter(
                            input.charAt(position)
                    )
            ) {

                position++;
            }

            return input.substring(
                    start,
                    position
            ).toLowerCase();
        }

        // =====================================================
        // WORD EVALUATION
        // =====================================================

        private double evaluateWord(
                String word
        ) {

            switch (word) {

                case "pi":
                    return Math.PI;

                case "e":
                    return Math.E;

                case "sin":
                    return Math.sin(
                            angle(
                                    functionArgument()
                            )
                    );

                case "cos":
                    return Math.cos(
                            angle(
                                    functionArgument()
                            )
                    );

                case "tan":
                    return Math.tan(
                            angle(
                                    functionArgument()
                            )
                    );

                case "asin":
                    return inverseAngle(
                            Math.asin(
                                    functionArgument()
                            )
                    );

                case "acos":
                    return inverseAngle(
                            Math.acos(
                                    functionArgument()
                            )
                    );

                case "atan":
                    return inverseAngle(
                            Math.atan(
                                    functionArgument()
                            )
                    );

                case "log":
                    return Math.log10(
                            functionArgument()
                    );

                case "ln":
                    return Math.log(
                            functionArgument()
                    );

                case "sqrt":
                    return Math.sqrt(
                            functionArgument()
                    );

                case "abs":
                    return Math.abs(
                            functionArgument()
                    );

                default:

                    throw new RuntimeException(
                            "Unknown function: "
                                    + word
                    );
            }
        }

        // =====================================================
        // FUNCTION ARGUMENT
        // =====================================================

        private double functionArgument() {

            if (
                    match('(')
            ) {

                double value =
                        parseExpression();

                if (
                        !match(')')
                ) {

                    throw new RuntimeException(
                            "Missing ')' after function"
                    );
                }

                return value;
            }

            return parseUnary();
        }

        // =====================================================
        // ANGLE
        // =====================================================

        private double angle(
                double value
        ) {

            return degreeMode
                    ? Math.toRadians(value)
                    : value;
        }

        private double inverseAngle(
                double value
        ) {

            return degreeMode
                    ? Math.toDegrees(value)
                    : value;
        }

        // =====================================================
        // FACTORIAL
        // =====================================================

        private double factorial(
                double value
        ) {

            if (
                    value < 0
                            || value != Math.floor(value)
            ) {

                throw new RuntimeException(
                        "Factorial requires a non-negative integer"
                );
            }

            if (
                    value > 170
            ) {

                throw new RuntimeException(
                        "Number too large"
                );
            }

            double result = 1;

            for (
                    int i = 2;
                    i <= (int) value;
                    i++
            ) {

                result *= i;
            }

            return result;
        }

        // =====================================================
        // PRIMARY START
        // =====================================================

        private boolean startsPrimary() {

            if (
                    position >= input.length()
            ) {

                return false;
            }

            char c =
                    input.charAt(position);

            return
                    c == '('
                            || c == '.'
                            || Character.isDigit(c)
                            || Character.isLetter(c);
        }

        // =====================================================
        // MATCH
        // =====================================================

        private boolean match(
                char character
        ) {

            if (
                    position < input.length()
                            && input.charAt(position)
                            == character
            ) {

                position++;

                return true;
            }

            return false;
        }
    }

    // =========================================================
    // BACK TO DASHBOARD
    // =========================================================

    private void backToDashboard() {

        dispose();

        Dashboard dashboard =
                new Dashboard();

        dashboard.setVisible(true);
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    ScientificCalculator calculator =
                            new ScientificCalculator();

                    calculator.setVisible(
                            true
                    );
                }
        );
    }
}