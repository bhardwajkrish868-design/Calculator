import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PercentageCalculator extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BG = new Color(246, 248, 252);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(28, 40, 60);
    private static final Color MUTED = new Color(95, 115, 145);

    private static final Color GREEN = new Color(0, 185, 145);
    private static final Color BLUE = new Color(45, 110, 220);
    private static final Color PURPLE = new Color(150, 65, 215);
    private static final Color ORANGE = new Color(245, 157, 20);
    private static final Color RED = new Color(220, 65, 75);

    private static final Color BORDER =
            new Color(218, 224, 234);

    private static final Color NAVY =
            new Color(25, 50, 85);

    // =========================================================
    // HISTORY FILE
    // =========================================================

    private static final String HISTORY_FILE =
            System.getProperty("user.home")
                    + File.separator
                    + "CalculatorSuite"
                    + File.separator
                    + "percentage_history.txt";

    // =========================================================
    // UI VARIABLES
    // =========================================================

    private CardLayout cardLayout;
    private JPanel centerPanel;

    private JPanel calculatorPanel;
    private JPanel historyPanel;

    private DefaultTableModel historyModel;
    private JTable historyTable;

    private JTextField searchField;

    private final DecimalFormat decimalFormat =
            new DecimalFormat("0.########");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public PercentageCalculator() {
        this(null);
    }

    public PercentageCalculator(JFrame parent) {

        setTitle("Percentage Calculator");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1100, 720)
        );

        createUI();

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(parent);
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(BG);

        setContentPane(root);

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        // IMPORTANT:
        // Direct CardLayout reference
        cardLayout = new CardLayout();

        centerPanel =
                new JPanel(cardLayout);

        centerPanel.setBackground(BG);

        calculatorPanel =
                createCalculatorPanel();

        historyPanel =
                createHistoryPanel();

        centerPanel.add(
                calculatorPanel,
                "CALCULATOR"
        );

        centerPanel.add(
                historyPanel,
                "HISTORY"
        );

        root.add(
                centerPanel,
                BorderLayout.CENTER
        );

        root.add(
                createFooter(),
                BorderLayout.SOUTH
        );
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(WHITE);

        header.setBorder(
                new CompoundBorder(
                        new MatteBorder(
                                0,
                                0,
                                1,
                                0,
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                45,
                                20,
                                45
                        )
                )
        );

        // LEFT

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
                        "PERCENTAGE CALCULATOR"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(GREEN);

        JLabel subtitle =
                new JLabel(
                        "Calculate percentages quickly and keep your history"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        left.add(title);

        left.add(
                Box.createVerticalStrut(4)
        );

        left.add(subtitle);

        // RIGHT

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        right.setOpaque(false);

        JButton history =
                createButton(
                        "History",
                        PURPLE,
                        Color.WHITE
                );

        history.setPreferredSize(
                new Dimension(
                        110,
                        44
                )
        );

        history.addActionListener(
                e -> showHistory()
        );

        JButton back =
                createButton(
                        "BACK",
                        NAVY,
                        Color.WHITE
                );

        back.setPreferredSize(
                new Dimension(
                        110,
                        44
                )
        );

        back.addActionListener(
                e -> backToDashboard()
        );

        right.add(history);
        right.add(back);

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
    // CALCULATOR PANEL
    // =========================================================

    private JPanel createCalculatorPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                18,
                                18
                        )
                );

        panel.setBackground(BG);

        panel.setBorder(
                new EmptyBorder(
                        25,
                        45,
                        25,
                        45
                )
        );

        panel.add(
                createPercentageCard()
        );

        panel.add(
                createWhatPercentCard()
        );

        panel.add(
                createIncreaseCard()
        );

        panel.add(
                createDecreaseCard()
        );

        panel.add(
                createDiscountCard()
        );

        panel.add(
                createMarksCard()
        );

        return panel;
    }

    // =========================================================
    // PERCENTAGE OF NUMBER
    // =========================================================

    private JPanel createPercentageCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(0, 10)
        );

        card.add(
                createTitle(
                        "Percentage of Number"
                ),
                BorderLayout.NORTH
        );

        JPanel fields =
                createFieldsPanel();

        JTextField percent =
                createField();

        JTextField number =
                createField();

        fields.add(
                createLabel("Percentage (%)")
        );

        fields.add(percent);

        fields.add(
                createLabel("Number")
        );

        fields.add(number);

        card.add(
                fields,
                BorderLayout.CENTER
        );

        JLabel result =
                createResult();

        JButton calculate =
                createButton(
                        "Calculate",
                        GREEN,
                        Color.WHITE
                );

        calculate.addActionListener(
                e -> {

                    try {

                        double p =
                                getValue(percent);

                        double n =
                                getValue(number);

                        double answer =
                                p * n / 100.0;

                        String output =
                                format(answer);

                        result.setText(
                                p + "% of "
                                        + n
                                        + " = "
                                        + output
                        );

                        saveHistory(
                                "Percentage of Number",
                                p + "% of " + n,
                                output
                        );

                    } catch (Exception ex) {

                        result.setText(
                                "Enter valid numbers"
                        );
                    }
                }
        );

        card.add(
                createBottom(
                        result,
                        calculate
                ),
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // A IS WHAT % OF B
    // =========================================================

    private JPanel createWhatPercentCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(0, 10)
        );

        card.add(
                createTitle(
                        "A is What % of B?"
                ),
                BorderLayout.NORTH
        );

        JPanel fields =
                createFieldsPanel();

        JTextField a =
                createField();

        JTextField b =
                createField();

        fields.add(
                createLabel("Value A")
        );

        fields.add(a);

        fields.add(
                createLabel("Value B")
        );

        fields.add(b);

        card.add(
                fields,
                BorderLayout.CENTER
        );

        JLabel result =
                createResult();

        JButton calculate =
                createButton(
                        "Calculate",
                        BLUE,
                        Color.WHITE
                );

        calculate.addActionListener(
                e -> {

                    try {

                        double valueA =
                                getValue(a);

                        double valueB =
                                getValue(b);

                        if (valueB == 0) {

                            result.setText(
                                    "B cannot be zero"
                            );

                            return;
                        }

                        double answer =
                                valueA / valueB * 100;

                        String output =
                                format(answer) + "%";

                        result.setText(
                                format(valueA)
                                        + " is "
                                        + output
                                        + " of "
                                        + format(valueB)
                        );

                        saveHistory(
                                "A is What % of B",
                                valueA + " / " + valueB,
                                output
                        );

                    } catch (Exception ex) {

                        result.setText(
                                "Enter valid numbers"
                        );
                    }
                }
        );

        card.add(
                createBottom(
                        result,
                        calculate
                ),
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // INCREASE
    // =========================================================

    private JPanel createIncreaseCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(0, 10)
        );

        card.add(
                createTitle(
                        "Percentage Increase"
                ),
                BorderLayout.NORTH
        );

        JPanel fields =
                createFieldsPanel();

        JTextField oldValue =
                createField();

        JTextField newValue =
                createField();

        fields.add(
                createLabel("Old Value")
        );

        fields.add(oldValue);

        fields.add(
                createLabel("New Value")
        );

        fields.add(newValue);

        card.add(
                fields,
                BorderLayout.CENTER
        );

        JLabel result =
                createResult();

        JButton calculate =
                createButton(
                        "Calculate",
                        PURPLE,
                        Color.WHITE
                );

        calculate.addActionListener(
                e -> {

                    try {

                        double old =
                                getValue(oldValue);

                        double newer =
                                getValue(newValue);

                        if (old == 0) {

                            result.setText(
                                    "Old value cannot be zero"
                            );

                            return;
                        }

                        double answer =
                                (newer - old)
                                        / old
                                        * 100;

                        String output =
                                format(answer) + "%";

                        result.setText(
                                "Increase = "
                                        + output
                        );

                        saveHistory(
                                "Percentage Increase",
                                old + " → " + newer,
                                output
                        );

                    } catch (Exception ex) {

                        result.setText(
                                "Enter valid numbers"
                        );
                    }
                }
        );

        card.add(
                createBottom(
                        result,
                        calculate
                ),
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // DECREASE
    // =========================================================

    private JPanel createDecreaseCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(0, 10)
        );

        card.add(
                createTitle(
                        "Percentage Decrease"
                ),
                BorderLayout.NORTH
        );

        JPanel fields =
                createFieldsPanel();

        JTextField oldValue =
                createField();

        JTextField newValue =
                createField();

        fields.add(
                createLabel("Original Value")
        );

        fields.add(oldValue);

        fields.add(
                createLabel("New Value")
        );

        fields.add(newValue);

        card.add(
                fields,
                BorderLayout.CENTER
        );

        JLabel result =
                createResult();

        JButton calculate =
                createButton(
                        "Calculate",
                        ORANGE,
                        Color.WHITE
                );

        calculate.addActionListener(
                e -> {

                    try {

                        double old =
                                getValue(oldValue);

                        double newer =
                                getValue(newValue);

                        if (old == 0) {

                            result.setText(
                                    "Original value cannot be zero"
                            );

                            return;
                        }

                        double answer =
                                (old - newer)
                                        / old
                                        * 100;

                        String output =
                                format(answer) + "%";

                        result.setText(
                                "Decrease = "
                                        + output
                        );

                        saveHistory(
                                "Percentage Decrease",
                                old + " → " + newer,
                                output
                        );

                    } catch (Exception ex) {

                        result.setText(
                                "Enter valid numbers"
                        );
                    }
                }
        );

        card.add(
                createBottom(
                        result,
                        calculate
                ),
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // DISCOUNT
    // =========================================================

    private JPanel createDiscountCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(0, 10)
        );

        card.add(
                createTitle(
                        "Discount Calculator"
                ),
                BorderLayout.NORTH
        );

        JPanel fields =
                createFieldsPanel();

        JTextField price =
                createField();

        JTextField discount =
                createField();

        fields.add(
                createLabel("Original Price")
        );

        fields.add(price);

        fields.add(
                createLabel("Discount (%)")
        );

        fields.add(discount);

        card.add(
                fields,
                BorderLayout.CENTER
        );

        JLabel result =
                createResult();

        JButton calculate =
                createButton(
                        "Calculate",
                        GREEN,
                        Color.WHITE
                );

        calculate.addActionListener(
                e -> {

                    try {

                        double original =
                                getValue(price);

                        double percent =
                                getValue(discount);

                        double saved =
                                original
                                        * percent
                                        / 100;

                        double finalPrice =
                                original - saved;

                        String output =
                                "Final Price = "
                                        + format(finalPrice);

                        result.setText(
                                "Save "
                                        + format(saved)
                                        + "  |  "
                                        + output
                        );

                        saveHistory(
                                "Discount Calculator",
                                percent
                                        + "% discount on "
                                        + original,
                                output
                        );

                    } catch (Exception ex) {

                        result.setText(
                                "Enter valid numbers"
                        );
                    }
                }
        );

        card.add(
                createBottom(
                        result,
                        calculate
                ),
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // MARKS
    // =========================================================

    private JPanel createMarksCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(0, 10)
        );

        card.add(
                createTitle(
                        "Marks Percentage"
                ),
                BorderLayout.NORTH
        );

        JPanel fields =
                createFieldsPanel();

        JTextField obtained =
                createField();

        JTextField total =
                createField();

        fields.add(
                createLabel("Marks Obtained")
        );

        fields.add(obtained);

        fields.add(
                createLabel("Total Marks")
        );

        fields.add(total);

        card.add(
                fields,
                BorderLayout.CENTER
        );

        JLabel result =
                createResult();

        JButton calculate =
                createButton(
                        "Calculate",
                        BLUE,
                        Color.WHITE
                );

        calculate.addActionListener(
                e -> {

                    try {

                        double marks =
                                getValue(obtained);

                        double maximum =
                                getValue(total);

                        if (maximum <= 0) {

                            result.setText(
                                    "Total marks must be greater than zero"
                            );

                            return;
                        }

                        if (
                                marks < 0
                                        || marks > maximum
                        ) {

                            result.setText(
                                    "Invalid marks"
                            );

                            return;
                        }

                        double answer =
                                marks
                                        / maximum
                                        * 100;

                        String output =
                                format(answer) + "%";

                        result.setText(
                                "Percentage = "
                                        + output
                        );

                        saveHistory(
                                "Marks Percentage",
                                marks + " / " + maximum,
                                output
                        );

                    } catch (Exception ex) {

                        result.setText(
                                "Enter valid numbers"
                        );
                    }
                }
        );

        card.add(
                createBottom(
                        result,
                        calculate
                ),
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // HISTORY PANEL
    // =========================================================

    private JPanel createHistoryPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(0, 15)
                );

        panel.setBackground(BG);

        panel.setBorder(
                new EmptyBorder(
                        25,
                        45,
                        20,
                        45
                )
        );

        // SEARCH

        JPanel top =
                new JPanel(
                        new BorderLayout(10, 0)
                );

        top.setOpaque(false);

        JLabel searchLabel =
                new JLabel(
                        "Search:"
                );

        searchLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        searchLabel.setForeground(TEXT);

        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        JButton search =
                createButton(
                        "Search",
                        BLUE,
                        Color.WHITE
                );

        search.setPreferredSize(
                new Dimension(
                        100,
                        42
                )
        );

        search.addActionListener(
                e -> filterHistory()
        );

        searchField.addActionListener(
                e -> filterHistory()
        );

        top.add(
                searchLabel,
                BorderLayout.WEST
        );

        top.add(
                searchField,
                BorderLayout.CENTER
        );

        top.add(
                search,
                BorderLayout.EAST
        );

        panel.add(
                top,
                BorderLayout.NORTH
        );

        // TABLE

        String[] columns = {
                "Type",
                "Calculation",
                "Result",
                "Date & Time"
        };

        historyModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        historyTable =
                new JTable(
                        historyModel
                );

        historyTable.setRowHeight(42);

        historyTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        historyTable.setForeground(TEXT);

        historyTable.setBackground(WHITE);

        historyTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        historyTable.setShowGrid(false);

        historyTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        historyTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );

        JScrollPane scroll =
                new JScrollPane(
                        historyTable
                );

        scroll.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        // ACTION BUTTONS

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        actions.setOpaque(false);

        JButton back =
                createButton(
                        "Back",
                        GREEN,
                        Color.WHITE
                );

        back.setPreferredSize(
                new Dimension(
                        90,
                        42
                )
        );

        back.addActionListener(
                e -> showCalculator()
        );

        JButton delete =
                createButton(
                        "Delete Selected",
                        ORANGE,
                        Color.WHITE
                );

        delete.setPreferredSize(
                new Dimension(
                        145,
                        42
                )
        );

        delete.addActionListener(
                e -> deleteSelected()
        );

        JButton clear =
                createButton(
                        "Clear History",
                        RED,
                        Color.WHITE
                );

        clear.setPreferredSize(
                new Dimension(
                        125,
                        42
                )
        );

        clear.addActionListener(
                e -> clearHistory()
        );

        actions.add(back);
        actions.add(delete);
        actions.add(clear);

        panel.add(
                actions,
                BorderLayout.SOUTH
        );

        return panel;
    }

    // =========================================================
    // SHOW HISTORY
    // =========================================================

    private void showHistory() {

        searchField.setText("");

        reloadHistory();

        cardLayout.show(
                centerPanel,
                "HISTORY"
        );
    }

    // =========================================================
    // SHOW CALCULATOR
    // =========================================================

    private void showCalculator() {

        cardLayout.show(
                centerPanel,
                "CALCULATOR"
        );
    }

    // =========================================================
    // SAVE HISTORY
    // =========================================================

    private void saveHistory(
            String type,
            String calculation,
            String result
    ) {

        createHistoryFolder();

        String date =
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "dd MMM yyyy, hh:mm:ss a"
                                )
                        );

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(
                                        HISTORY_FILE,
                                        true
                                )
                        )
        ) {

            writer.write(
                    clean(type)
                            + "|"
                            + clean(calculation)
                            + "|"
                            + clean(result)
                            + "|"
                            + clean(date)
            );

            writer.newLine();

        } catch (IOException ex) {

            System.err.println(
                    "History save error: "
                            + ex.getMessage()
            );
        }
    }

    // =========================================================
    // RELOAD HISTORY
    // =========================================================

    private void reloadHistory() {

        historyModel.setRowCount(0);

        File file =
                new File(
                        HISTORY_FILE
                );

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

                String[] parts =
                        line.split(
                                "\\|",
                                -1
                        );

                if (parts.length >= 4) {

                    historyModel.insertRow(
                            0,
                            new Object[]{
                                    parts[0],
                                    parts[1],
                                    parts[2],
                                    parts[3]
                            }
                    );
                }
            }

        } catch (IOException ex) {

            System.err.println(
                    "History load error: "
                            + ex.getMessage()
            );
        }
    }

    // =========================================================
    // FILTER HISTORY
    // =========================================================

    private void filterHistory() {

        String query =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        historyModel.setRowCount(0);

        File file =
                new File(
                        HISTORY_FILE
                );

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

                String[] parts =
                        line.split(
                                "\\|",
                                -1
                        );

                if (parts.length < 4) {
                    continue;
                }

                String combined =
                        line.toLowerCase();

                if (
                        query.isEmpty()
                                || combined.contains(query)
                ) {

                    historyModel.insertRow(
                            0,
                            new Object[]{
                                    parts[0],
                                    parts[1],
                                    parts[2],
                                    parts[3]
                            }
                    );
                }
            }

        } catch (IOException ex) {

            System.err.println(
                    "History search error: "
                            + ex.getMessage()
            );
        }
    }

    // =========================================================
    // DELETE SELECTED
    // =========================================================

    private void deleteSelected() {

        int row =
                historyTable.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a history entry first.",
                    "History",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String type =
                historyModel
                        .getValueAt(row, 0)
                        .toString();

        String calculation =
                historyModel
                        .getValueAt(row, 1)
                        .toString();

        String result =
                historyModel
                        .getValueAt(row, 2)
                        .toString();

        String date =
                historyModel
                        .getValueAt(row, 3)
                        .toString();

        removeHistoryEntry(
                type,
                calculation,
                result,
                date
        );

        filterHistory();
    }

    // =========================================================
    // REMOVE HISTORY ENTRY
    // =========================================================

    private void removeHistoryEntry(
            String type,
            String calculation,
            String result,
            String date
    ) {

        File file =
                new File(
                        HISTORY_FILE
                );

        if (!file.exists()) {
            return;
        }

        File temp =
                new File(
                        HISTORY_FILE
                                + ".tmp"
                );

        boolean deleted = false;

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        );

                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(temp)
                        )
        ) {

            String line;

            while (
                    (line = reader.readLine())
                            != null
            ) {

                String[] parts =
                        line.split(
                                "\\|",
                                -1
                        );

                if (
                        !deleted
                                && parts.length >= 4
                                && parts[0].equals(type)
                                && parts[1].equals(calculation)
                                && parts[2].equals(result)
                                && parts[3].equals(date)
                ) {

                    deleted = true;

                    continue;
                }

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException ex) {

            return;
        }

        if (file.delete()) {

            temp.renameTo(file);
        }
    }

    // =========================================================
    // CLEAR HISTORY
    // =========================================================

    private void clearHistory() {

        if (historyTable.getRowCount() == 0) {
            return;
        }

        int answer =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete all percentage calculation history?",
                        "Clear History",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                answer
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        File file =
                new File(
                        HISTORY_FILE
                );

        if (file.exists()) {
            file.delete();
        }

        historyModel.setRowCount(0);

        searchField.setText("");
    }

    // =========================================================
    // HISTORY FOLDER
    // =========================================================

    private static void createHistoryFolder() {

        File file =
                new File(
                        HISTORY_FILE
                );

        File folder =
                file.getParentFile();

        if (
                folder != null
                        && !folder.exists()
        ) {

            folder.mkdirs();
        }
    }

    // =========================================================
    // CARD
    // =========================================================

    private JPanel createCard() {

        JPanel card =
                new JPanel();

        card.setBackground(WHITE);

        card.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        return card;
    }

    // =========================================================
    // FIELDS PANEL
    // =========================================================

    private JPanel createFieldsPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                8,
                                8
                        )
                );

        panel.setOpaque(false);

        return panel;
    }

    // =========================================================
    // TITLE
    // =========================================================

    private JLabel createTitle(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    // =========================================================
    // LABEL
    // =========================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        label.setForeground(MUTED);

        return label;
    }

    // =========================================================
    // RESULT
    // =========================================================

    private JLabel createResult() {

        JLabel label =
                new JLabel(
                        "Result will appear here"
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(BLUE);

        return label;
    }

    // =========================================================
    // FIELD
    // =========================================================

    private JTextField createField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(TEXT);

        field.setBackground(
                new Color(
                        249,
                        251,
                        255
                )
        );

        field.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                7,
                                9,
                                7,
                                9
                        )
                )
        );

        return field;
    }

    // =========================================================
    // BOTTOM
    // =========================================================

    private JPanel createBottom(
            JLabel result,
            JButton calculate
    ) {

        JPanel bottom =
                new JPanel(
                        new BorderLayout(
                                0,
                                7
                        )
                );

        bottom.setOpaque(false);

        JPanel resultBox =
                new JPanel(
                        new BorderLayout()
                );

        resultBox.setBackground(
                new Color(
                        248,
                        250,
                        255
                )
        );

        resultBox.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                8,
                                9,
                                8,
                                9
                        )
                )
        );

        resultBox.add(
                result,
                BorderLayout.CENTER
        );

        bottom.add(
                resultBox,
                BorderLayout.NORTH
        );

        bottom.add(
                calculate,
                BorderLayout.SOUTH
        );

        return bottom;
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
                        13
                )
        );

        button.setBackground(background);

        button.setForeground(foreground);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // GET VALUE
    // =========================================================

    private double getValue(
            JTextField field
    ) {

        String text =
                field.getText().trim();

        if (text.isEmpty()) {
            throw new NumberFormatException();
        }

        return Double.parseDouble(text);
    }

    // =========================================================
    // FORMAT
    // =========================================================

    private String format(
            double value
    ) {

        return decimalFormat.format(value);
    }

    // =========================================================
    // CLEAN
    // =========================================================

    private String clean(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value
                .replace("|", "/")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(WHITE);

        footer.setBorder(
                new CompoundBorder(
                        new MatteBorder(
                                1,
                                0,
                                0,
                                0,
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                45,
                                10,
                                45
                        )
                )
        );

        JLabel left =
                new JLabel(
                        "Calculator Suite  •  Percentage Calculator"
                );

        left.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        left.setForeground(MUTED);

        JLabel right =
                new JLabel(
                        "Developed by Krish Bhardwaj"
                );

        right.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        right.setForeground(MUTED);

        footer.add(
                left,
                BorderLayout.WEST
        );

        footer.add(
                right,
                BorderLayout.EAST
        );

        return footer;
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

                    PercentageCalculator calculator =
                            new PercentageCalculator();

                    calculator.setVisible(true);
                }
        );
    }
}