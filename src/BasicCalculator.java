import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BasicCalculator extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BG =
            new Color(238, 241, 247);

    private static final Color PANEL =
            Color.WHITE;

    private static final Color BORDER =
            new Color(220, 224, 232);

    private static final Color PURPLE =
            new Color(132, 69, 211);

    private static final Color BLUE =
            new Color(42, 112, 220);

    private static final Color RED =
            new Color(224, 61, 76);

    private static final Color ORANGE =
            new Color(246, 146, 37);

    private static final Color CYAN =
            new Color(35, 174, 201);

    private static final Color GREEN =
            new Color(36, 174, 126);

    private static final Color NAVY =
            new Color(25, 50, 85);

    private static final Color TEXT =
            new Color(35, 38, 45);

    private static final Color LIGHT_BUTTON =
            new Color(252, 250, 252);

    // =========================================================
    // HISTORY FILE
    // =========================================================

    private static final String HISTORY_FILE =
            System.getProperty("user.home")
                    + File.separator
                    + "CalculatorSuite"
                    + File.separator
                    + "calculator_history.txt";

    // =========================================================
    // DISPLAY
    // =========================================================

    private final JTextField expressionDisplay =
            new JTextField();

    private final JTextField display =
            new JTextField("0");

    // =========================================================
    // HISTORY
    // =========================================================

    private final List<String> history =
            new ArrayList<>();

    // =========================================================
    // CALCULATION VARIABLES
    // =========================================================

    private double firstValue = 0;

    private String operator = "";

    private boolean waitingForNumber = true;

    private String expression = "";

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public BasicCalculator(JFrame parent) {

        setTitle(
                "Basic Calculator - Krish Bhardwaj"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        700
                )
        );

        createHistoryFolder();

        loadHistory();

        createUI();

        installKeyboardSupport();

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(parent);
    }

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public BasicCalculator() {

        this(null);
    }

    // =========================================================
    // HISTORY FOLDER
    // =========================================================

    private void createHistoryFolder() {

        File file =
                new File(HISTORY_FILE);

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
    // LOAD HISTORY
    // =========================================================

    private void loadHistory() {

        File file =
                new File(HISTORY_FILE);

        if (!file.exists()) {
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

        } catch (IOException e) {

            System.out.println(
                    "Unable to load history: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // SAVE HISTORY
    // =========================================================

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

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save calculation history.\n"
                            + e.getMessage(),
                    "History Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout(
                                18,
                                18
                        )
                );

        root.setBackground(BG);

        root.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        setContentPane(root);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        header.setPreferredSize(
                new Dimension(
                        0,
                        90
                )
        );

        // =====================================================
        // TITLE
        // =====================================================

        JPanel titleBox =
                new JPanel();

        titleBox.setOpaque(false);

        titleBox.setLayout(
                new BoxLayout(
                        titleBox,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Basic Calculator"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                PURPLE
        );

        JLabel subtitle =
                new JLabel(
                        "Fast calculations with keyboard support"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(
                        105,
                        108,
                        118
                )
        );

        titleBox.add(title);

        titleBox.add(
                Box.createVerticalStrut(5)
        );

        titleBox.add(subtitle);

        // =====================================================
        // HEADER BUTTONS
        // =====================================================

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                20
                        )
                );

        actions.setOpaque(false);

        JButton historyButton =
                createHeaderButton(
                        "History",
                        GREEN,
                        135
                );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JButton backButton =
                createHeaderButton(
                        "BACK",
                        NAVY,
                        110
                );

        historyButton.addActionListener(
                e -> showHistory()
        );

        backButton.addActionListener(
                e -> backToDashboard()
        );

        actions.add(
                historyButton
        );

        actions.add(
                backButton
        );

        header.add(
                titleBox,
                BorderLayout.WEST
        );

        header.add(
                actions,
                BorderLayout.EAST
        );

        root.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // CALCULATOR PANEL
        // =====================================================

        JPanel calculatorPanel =
                new JPanel(
                        new BorderLayout(
                                18,
                                18
                        )
                );

        calculatorPanel.setBackground(
                PANEL
        );

        calculatorPanel.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                18,
                                20
                        )
                )
        );

        // =====================================================
        // DISPLAY PANEL
        // =====================================================

        JPanel displayPanel =
                new JPanel(
                        new BorderLayout()
                );

        displayPanel.setBackground(
                Color.WHITE
        );

        displayPanel.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                CYAN,
                                2
                        ),
                        new EmptyBorder(
                                8,
                                15,
                                8,
                                15
                        )
                )
        );

        // =====================================================
        // EXPRESSION
        // =====================================================

        expressionDisplay.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        expressionDisplay.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        20
                )
        );

        expressionDisplay.setForeground(
                new Color(
                        120,
                        123,
                        132
                )
        );

        expressionDisplay.setBackground(
                Color.WHITE
        );

        expressionDisplay.setEditable(false);

        expressionDisplay.setFocusable(false);

        expressionDisplay.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        5,
                        0,
                        5
                )
        );

        expressionDisplay.setText("");

        displayPanel.add(
                expressionDisplay,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN DISPLAY
        // =====================================================

        display.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        display.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        42
                )
        );

        display.setForeground(
                TEXT
        );

        display.setBackground(
                Color.WHITE
        );

        display.setEditable(false);

        display.setFocusable(false);

        display.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        5,
                        10,
                        5
                )
        );

        displayPanel.add(
                display,
                BorderLayout.CENTER
        );

        displayPanel.setPreferredSize(
                new Dimension(
                        100,
                        120
                )
        );

        calculatorPanel.add(
                displayPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // KEYPAD
        // =====================================================

        JPanel keypad =
                new JPanel(
                        new GridLayout(
                                5,
                                4,
                                12,
                                12
                        )
                );

        keypad.setOpaque(false);

        // ROW 1

        addButton(
                keypad,
                "C",
                RED
        );

        addButton(
                keypad,
                "Backspace",
                ORANGE
        );

        addButton(
                keypad,
                "%",
                CYAN
        );

        addButton(
                keypad,
                "/",
                PURPLE
        );

        // ROW 2

        addButton(
                keypad,
                "7",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "8",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "9",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "*",
                PURPLE
        );

        // ROW 3

        addButton(
                keypad,
                "4",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "5",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "6",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "-",
                PURPLE
        );

        // ROW 4

        addButton(
                keypad,
                "1",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "2",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "3",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "+",
                PURPLE
        );

        // ROW 5

        addButton(
                keypad,
                "0",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                ".",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "00",
                LIGHT_BUTTON
        );

        addButton(
                keypad,
                "=",
                BLUE
        );

        calculatorPanel.add(
                keypad,
                BorderLayout.CENTER
        );

        // =====================================================
        // KEYBOARD INFO
        // =====================================================

        JLabel keyboardInfo =
                new JLabel(
                        "Keyboard: 0-9  |  +  -  *  /  %  .  |  Enter =  |  Backspace  |  Delete / Esc = Clear"
                );

        keyboardInfo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        keyboardInfo.setForeground(
                new Color(
                        130,
                        133,
                        140
                )
        );

        keyboardInfo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        calculatorPanel.add(
                keyboardInfo,
                BorderLayout.SOUTH
        );

        root.add(
                calculatorPanel,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // HEADER BUTTON
    // =========================================================

    private JButton createHeaderButton(
            String text,
            Color color,
            int width
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        width,
                        48
                )
        );

        return button;
    }

    // =========================================================
    // CALCULATOR BUTTON
    // =========================================================

    private void addButton(
            JPanel panel,
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        text.equals("Backspace")
                                ? 14
                                : 22
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setOpaque(true);

        if (
                color.equals(
                        LIGHT_BUTTON
                )
        ) {

            button.setBackground(
                    LIGHT_BUTTON
            );

            button.setForeground(
                    TEXT
            );

            button.setBorder(
                    new LineBorder(
                            new Color(
                                    232,
                                    228,
                                    233
                            ),
                            1
                    )
            );

        } else {

            button.setBackground(
                    color
            );

            button.setForeground(
                    Color.WHITE
            );

            button.setBorderPainted(false);
        }

        button.addActionListener(
                e -> handle(text)
        );

        panel.add(button);
    }

    // =========================================================
    // HANDLE
    // =========================================================

    private void handle(
            String key
    ) {

        try {

            if (
                    key.matches("\\d+")
            ) {

                enterNumber(key);

            } else if (
                    key.equals(".")
            ) {

                enterDecimal();

            } else if (
                    key.equals("C")
            ) {

                clear();

            } else if (
                    key.equals("Backspace")
            ) {

                backspace();

            } else if (
                    key.equals("%")
            ) {

                percentage();

            } else if (
                    "+-*/".contains(key)
            ) {

                setOperator(key);

            } else if (
                    key.equals("=")
            ) {

                calculate();
            }

        } catch (Exception ex) {

            display.setText(
                    "Error"
            );

            expressionDisplay.setText("");

            firstValue = 0;

            operator = "";

            expression = "";

            waitingForNumber = true;
        }
    }

    // =========================================================
    // NUMBER
    // =========================================================

    private void enterNumber(
            String number
    ) {

        if (
                waitingForNumber
                        || display.getText()
                        .equals("0")
                        || display.getText()
                        .equals("Error")
        ) {

            display.setText(
                    number
            );

            waitingForNumber = false;

        } else {

            display.setText(
                    display.getText()
                            + number
            );
        }

        updateExpressionDisplay();
    }

    // =========================================================
    // DECIMAL
    // =========================================================

    private void enterDecimal() {

        if (
                waitingForNumber
                        || display.getText()
                        .equals("Error")
        ) {

            display.setText(
                    "0."
            );

            waitingForNumber = false;

        } else if (
                !display.getText()
                        .contains(".")
        ) {

            display.setText(
                    display.getText()
                            + "."
            );
        }

        updateExpressionDisplay();
    }

    // =========================================================
    // OPERATOR
    // =========================================================

    private void setOperator(
            String op
    ) {

        if (
                display.getText()
                        .equals("Error")
        ) {

            clear();
        }

        if (
                !operator.isEmpty()
                        && !waitingForNumber
        ) {

            calculate();
        }

        try {

            firstValue =
                    Double.parseDouble(
                            display.getText()
                    );

        } catch (Exception e) {

            clear();

            return;
        }

        operator = op;

        expression =
                format(firstValue)
                        + " "
                        + getDisplayOperator(op)
                        + " ";

        expressionDisplay.setText(
                expression
        );

        waitingForNumber = true;
    }

    // =========================================================
    // CALCULATE
    // =========================================================

    private void calculate() {

        if (
                operator.isEmpty()
        ) {

            return;
        }

        double secondValue;

        try {

            secondValue =
                    Double.parseDouble(
                            display.getText()
                    );

        } catch (Exception e) {

            display.setText(
                    "Error"
            );

            expressionDisplay.setText("");

            operator = "";

            waitingForNumber = true;

            return;
        }

        double result;

        switch (operator) {

            case "+":

                result =
                        firstValue
                                + secondValue;

                break;

            case "-":

                result =
                        firstValue
                                - secondValue;

                break;

            case "*":

                result =
                        firstValue
                                * secondValue;

                break;

            case "/":

                if (
                        secondValue == 0
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Cannot divide by zero.",
                            "Calculation Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    display.setText(
                            "Error"
                    );

                    expressionDisplay.setText("");

                    firstValue = 0;

                    operator = "";

                    expression = "";

                    waitingForNumber = true;

                    return;
                }

                result =
                        firstValue
                                / secondValue;

                break;

            default:

                return;
        }

        // =====================================================
        // SAVE HISTORY
        // =====================================================

        String fullExpression =
                format(firstValue)
                        + " "
                        + getDisplayOperator(operator)
                        + " "
                        + format(secondValue)
                        + " = "
                        + format(result);

        history.add(
                0,
                fullExpression
        );

        saveHistory();

        // =====================================================
        // UPDATE SCREEN
        // =====================================================

        expressionDisplay.setText(
                format(firstValue)
                        + " "
                        + getDisplayOperator(operator)
                        + " "
                        + format(secondValue)
                        + " ="
        );

        display.setText(
                format(result)
        );

        firstValue = result;

        operator = "";

        expression = "";

        waitingForNumber = true;
    }

    // =========================================================
    // DISPLAY OPERATOR
    // =========================================================

    private String getDisplayOperator(
            String op
    ) {

        switch (op) {

            case "*":
                return "×";

            case "/":
                return "÷";

            default:
                return op;
        }
    }

    // =========================================================
    // UPDATE EXPRESSION
    // =========================================================

    private void updateExpressionDisplay() {

        if (
                !operator.isEmpty()
        ) {

            expressionDisplay.setText(
                    expression
                            + display.getText()
            );
        }
    }

    // =========================================================
    // PERCENTAGE
    // =========================================================

    private void percentage() {

        try {

            double value =
                    Double.parseDouble(
                            display.getText()
                    );

            value =
                    value / 100.0;

            display.setText(
                    format(value)
            );

            waitingForNumber = true;

            updateExpressionDisplay();

        } catch (Exception e) {

            display.setText(
                    "Error"
            );

            expressionDisplay.setText("");

            waitingForNumber = true;
        }
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clear() {

        display.setText(
                "0"
        );

        expressionDisplay.setText(
                ""
        );

        firstValue = 0;

        operator = "";

        expression = "";

        waitingForNumber = true;
    }

    // =========================================================
    // BACKSPACE
    // =========================================================

    private void backspace() {

        String value =
                display.getText();

        if (
                value.equals("Error")
                        || value.length() <= 1
        ) {

            display.setText(
                    "0"
            );

        } else {

            display.setText(
                    value.substring(
                            0,
                            value.length() - 1
                    )
            );
        }

        updateExpressionDisplay();
    }

    // =========================================================
    // FORMAT
    // =========================================================

    private String format(
            double value
    ) {

        if (
                Double.isNaN(value)
                        || Double.isInfinite(value)
        ) {

            return "Error";
        }

        if (
                value == (long) value
        ) {

            return String.valueOf(
                    (long) value
            );
        }

        return String.valueOf(
                value
        );
    }

    // =========================================================
    // HISTORY WINDOW
    // =========================================================

    private void showHistory() {

        JFrame historyFrame =
                new JFrame(
                        "Calculation History"
                );

        historyFrame.setSize(
                900,
                650
        );

        historyFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        historyFrame.setLocationRelativeTo(
                this
        );

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(BG);

        // =====================================================
        // HISTORY HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                PURPLE
        );

        header.setPreferredSize(
                new Dimension(
                        0,
                        85
                )
        );

        header.setBorder(
                new EmptyBorder(
                        0,
                        30,
                        0,
                        30
                )
        );

        JLabel title =
                new JLabel(
                        "CALCULATION HISTORY"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        root.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // LIST
        // =====================================================

        DefaultListModel<String> listModel =
                new DefaultListModel<>();

        for (
                String item : history
        ) {

            listModel.addElement(
                    item
            );
        }

        if (
                history.isEmpty()
        ) {

            listModel.addElement(
                    "No calculations yet."
            );
        }

        JList<String> historyList =
                new JList<>(
                        listModel
                );

        historyList.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        historyList.setBackground(
                Color.WHITE
        );

        historyList.setForeground(
                TEXT
        );

        historyList.setFixedCellHeight(
                50
        );

        historyList.setBorder(
                new EmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        historyList
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        root.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM BUTTONS
        // =====================================================

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                15,
                                15
                        )
                );

        bottom.setBackground(BG);

        JButton clearHistory =
                createActionButton(
                        "Clear History",
                        RED,
                        160
                );

        clearHistory.addActionListener(
                e -> {

                    if (
                            history.isEmpty()
                    ) {

                        JOptionPane.showMessageDialog(
                                historyFrame,
                                "History is already empty.",
                                "History",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        return;
                    }

                    int result =
                            JOptionPane.showConfirmDialog(
                                    historyFrame,
                                    "Are you sure you want to clear all calculation history?",
                                    "Clear History",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );

                    if (
                            result
                                    == JOptionPane.YES_OPTION
                    ) {

                        history.clear();

                        saveHistory();

                        listModel.clear();

                        listModel.addElement(
                                "No calculations yet."
                        );
                    }
                }
        );

        JButton close =
                createActionButton(
                        "Close",
                        BLUE,
                        110
                );

        close.addActionListener(
                e ->
                        historyFrame.dispose()
        );

        bottom.add(
                clearHistory
        );

        bottom.add(
                close
        );

        root.add(
                bottom,
                BorderLayout.SOUTH
        );

        historyFrame.setContentPane(
                root
        );

        historyFrame.setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        historyFrame.setVisible(
                true
        );
    }

    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private JButton createActionButton(
            String text,
            Color color,
            int width
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setBackground(
                color
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        width,
                        50
                )
        );

        return button;
    }

    // =========================================================
    // BACK TO DASHBOARD
    // =========================================================

    private void backToDashboard() {

        dispose();

        Dashboard dashboard =
                new Dashboard();

        dashboard.setVisible(
                true
        );
    }

    // =========================================================
    // KEYBOARD SUPPORT
    // =========================================================

    private void installKeyboardSupport() {

        KeyboardFocusManager
                .getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(
                        new KeyEventDispatcher() {

                            @Override
                            public boolean dispatchKeyEvent(
                                    KeyEvent e
                            ) {

                                if (
                                        e.getID()
                                                != KeyEvent.KEY_PRESSED
                                ) {

                                    return false;
                                }

                                if (!isActive()) {

                                    return false;
                                }

                                int keyCode =
                                        e.getKeyCode();

                                // NUMBER ROW

                                if (
                                        keyCode >= KeyEvent.VK_0
                                                && keyCode <= KeyEvent.VK_9
                                                && !e.isAltDown()
                                ) {

                                    char number =
                                            (char) (
                                                    '0'
                                                            + (
                                                            keyCode
                                                                    - KeyEvent.VK_0
                                                    )
                                            );

                                    handle(
                                            String.valueOf(
                                                    number
                                            )
                                    );

                                    return true;
                                }

                                // NUMPAD

                                if (
                                        keyCode >= KeyEvent.VK_NUMPAD0
                                                && keyCode <= KeyEvent.VK_NUMPAD9
                                ) {

                                    char number =
                                            (char) (
                                                    '0'
                                                            + (
                                                            keyCode
                                                                    - KeyEvent.VK_NUMPAD0
                                                    )
                                            );

                                    handle(
                                            String.valueOf(
                                                    number
                                            )
                                    );

                                    return true;
                                }

                                // ENTER

                                if (
                                        keyCode
                                                == KeyEvent.VK_ENTER
                                ) {

                                    handle("=");

                                    return true;
                                }

                                // PLUS

                                if (
                                        keyCode
                                                == KeyEvent.VK_ADD
                                                || (
                                                keyCode
                                                        == KeyEvent.VK_EQUALS
                                                        && e.isShiftDown()
                                        )
                                ) {

                                    handle("+");

                                    return true;
                                }

                                // MINUS

                                if (
                                        keyCode
                                                == KeyEvent.VK_MINUS
                                                || keyCode
                                                == KeyEvent.VK_SUBTRACT
                                ) {

                                    handle("-");

                                    return true;
                                }

                                // MULTIPLY

                                if (
                                        keyCode
                                                == KeyEvent.VK_MULTIPLY
                                                || (
                                                keyCode
                                                        == KeyEvent.VK_8
                                                        && e.isShiftDown()
                                        )
                                ) {

                                    handle("*");

                                    return true;
                                }

                                // DIVIDE

                                if (
                                        keyCode
                                                == KeyEvent.VK_SLASH
                                                || keyCode
                                                == KeyEvent.VK_DIVIDE
                                ) {

                                    handle("/");

                                    return true;
                                }

                                // DECIMAL

                                if (
                                        keyCode
                                                == KeyEvent.VK_PERIOD
                                                || keyCode
                                                == KeyEvent.VK_DECIMAL
                                ) {

                                    handle(".");

                                    return true;
                                }

                                // PERCENTAGE

                                if (
                                        keyCode
                                                == KeyEvent.VK_5
                                                && e.isShiftDown()
                                ) {

                                    handle("%");

                                    return true;
                                }

                                // BACKSPACE

                                if (
                                        keyCode
                                                == KeyEvent.VK_BACK_SPACE
                                ) {

                                    handle(
                                            "Backspace"
                                    );

                                    return true;
                                }

                                // DELETE

                                if (
                                        keyCode
                                                == KeyEvent.VK_DELETE
                                ) {

                                    handle("C");

                                    return true;
                                }

                                // ESCAPE

                                if (
                                        keyCode
                                                == KeyEvent.VK_ESCAPE
                                ) {

                                    handle("C");

                                    return true;
                                }

                                return false;
                            }
                        }
                );
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    BasicCalculator calculator =
                            new BasicCalculator(
                                    null
                            );

                    calculator.setVisible(
                            true
                    );
                }
        );
    }
}