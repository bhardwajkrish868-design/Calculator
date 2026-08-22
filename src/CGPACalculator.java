import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CGPACalculator extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color NAVY = new Color(27, 39, 82);
    private static final Color BACKGROUND = new Color(245, 247, 252);
    private static final Color BLUE = new Color(45, 125, 230);
    private static final Color PURPLE = new Color(140, 80, 210);
    private static final Color GREEN = new Color(25, 175, 120);
    private static final Color RED = new Color(220, 65, 75);
    private static final Color ORANGE = new Color(240, 145, 40);
    private static final Color TEXT = new Color(35, 42, 65);

    // =========================================================
    // HISTORY FILE
    // =========================================================

    private static final String HISTORY_FILE =
            System.getProperty("user.home")
                    + File.separator
                    + "CalculatorSuite"
                    + File.separator
                    + "cgpa_history.txt";

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTable table;
    private DefaultTableModel model;

    private JLabel cgpaValue;
    private JLabel creditsValue;
    private JLabel subjectsValue;

    // =========================================================
    // PARENT FRAME
    // =========================================================

    private final JFrame parent;

    // =========================================================
    // HISTORY
    // =========================================================

    private final ArrayList<HistoryEntry> history =
            new ArrayList<>();

    private static class HistoryEntry {

        String dateTime;
        double cgpa;
        double totalCredits;
        int subjects;

        HistoryEntry(
                String dateTime,
                double cgpa,
                double totalCredits,
                int subjects
        ) {
            this.dateTime = dateTime;
            this.cgpa = cgpa;
            this.totalCredits = totalCredits;
            this.subjects = subjects;
        }
    }

    // =========================================================
    // CONSTRUCTORS
    // =========================================================

    public CGPACalculator() {
        this(null);
    }

    public CGPACalculator(JFrame parent) {

        this.parent = parent;

        setTitle("CGPA Calculator - Krish Bhardwaj");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1000, 650)
        );

        createHistoryFolder();
        loadHistory();
        createUI();

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);
    }

    // =========================================================
    // HISTORY FOLDER
    // =========================================================

    private void createHistoryFolder() {

        File file = new File(HISTORY_FILE);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
    }

    // =========================================================
    // LOAD HISTORY
    // =========================================================

    private void loadHistory() {

        File file = new File(HISTORY_FILE);

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

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts =
                        line.split("\\|", -1);

                if (parts.length != 4) {
                    continue;
                }

                try {

                    String dateTime = parts[0];

                    double cgpa =
                            Double.parseDouble(parts[1]);

                    double credits =
                            Double.parseDouble(parts[2]);

                    int subjects =
                            Integer.parseInt(parts[3]);

                    history.add(
                            new HistoryEntry(
                                    dateTime,
                                    cgpa,
                                    credits,
                                    subjects
                            )
                    );

                } catch (NumberFormatException ignored) {
                    // Ignore damaged history line
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Unable to load CGPA history: "
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

            for (HistoryEntry entry : history) {

                writer.write(
                        entry.dateTime
                                + "|"
                                + entry.cgpa
                                + "|"
                                + entry.totalCredits
                                + "|"
                                + entry.subjects
                );

                writer.newLine();
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save CGPA history.\n"
                            + e.getMessage(),
                    "History Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // MAIN UI
    // =========================================================

    private void createUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(BACKGROUND);

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        JPanel content =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        content.setBackground(BACKGROUND);

        content.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        35,
                        25,
                        35
                )
        );

        content.add(
                createStatistics(),
                BorderLayout.NORTH
        );

        content.add(
                createSubjectSection(),
                BorderLayout.CENTER
        );

        root.add(
                content,
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

        header.setBackground(NAVY);

        header.setPreferredSize(
                new Dimension(0, 95)
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        30,
                        15,
                        30
                )
        );

        JLabel title =
                new JLabel(
                        "Krish Bhardwaj  |  CGPA Calculator"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        JPanel headerButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        headerButtons.setOpaque(false);

        JButton backButton =
                createButton(
                        "Back",
                        RED
                );

        backButton.setPreferredSize(
                new Dimension(105, 45)
        );

        backButton.addActionListener(
                e -> goBack()
        );

        JButton historyButton =
                createButton(
                        "History",
                        PURPLE
                );

        historyButton.setPreferredSize(
                new Dimension(120, 45)
        );

        historyButton.addActionListener(
                e -> showHistory()
        );

        headerButtons.add(backButton);
        headerButtons.add(historyButton);

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                headerButtons,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // BACK BUTTON
    // =========================================================

    private void goBack() {

        dispose();

        if (parent != null) {

            parent.setVisible(true);
            parent.setState(JFrame.NORMAL);
            parent.toFront();

        } else {

            // No Dashboard dependency here.
            // Calculator simply closes when opened directly.
            System.out.println(
                    "Returned from CGPA Calculator."
            );
        }
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private JPanel createStatistics() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        panel.setOpaque(false);

        cgpaValue =
                new JLabel("0.00");

        creditsValue =
                new JLabel("0");

        subjectsValue =
                new JLabel(
                        String.valueOf(
                                model == null
                                        ? 5
                                        : model.getRowCount()
                        )
                );

        panel.add(
                createStatCard(
                        "CURRENT CGPA",
                        cgpaValue,
                        PURPLE
                )
        );

        panel.add(
                createStatCard(
                        "TOTAL CREDITS",
                        creditsValue,
                        GREEN
                )
        );

        panel.add(
                createStatCard(
                        "TOTAL SUBJECTS",
                        subjectsValue,
                        ORANGE
                )
        );

        return panel;
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            JLabel value,
            Color color
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                215,
                                220,
                                235
                        )
                )
        );

        JPanel bar =
                new JPanel();

        bar.setBackground(color);

        bar.setPreferredSize(
                new Dimension(
                        7,
                        70
                )
        );

        card.add(
                bar,
                BorderLayout.WEST
        );

        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        text.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        JLabel heading =
                new JLabel(title);

        heading.setForeground(Color.GRAY);

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        value.setForeground(TEXT);

        value.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        text.add(heading);

        text.add(
                Box.createVerticalStrut(5)
        );

        text.add(value);

        card.add(
                text,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // SUBJECT SECTION
    // =========================================================

    private JPanel createSubjectSection() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        215,
                                        220,
                                        235
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel heading =
                new JLabel(
                        "Subject Details"
                );

        heading.setForeground(TEXT);

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        createTable();

        JScrollPane scrollPane =
                new JScrollPane(table);

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        panel.add(
                createButtonPanel(),
                BorderLayout.SOUTH
        );

        return panel;
    }

    // =========================================================
    // TABLE
    // =========================================================

    private void createTable() {

        String[] columns = {
                "No.",
                "Subject",
                "Credits",
                "Grade",
                "Grade Point",
                "Credit Point"
        };

        model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return column == 1
                                || column == 2
                                || column == 3;
                    }
                };

        table =
                new JTable(model);

        table.setRowHeight(38);

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        table.getTableHeader()
                .setBackground(BLUE);

        table.getTableHeader()
                .setForeground(Color.WHITE);

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        JComboBox<String> grades =
                new JComboBox<>(
                        new String[]{
                                "O",
                                "E",
                                "A",
                                "B",
                                "C",
                                "D",
                                "F"
                        }
                );

        table.getColumnModel()
                .getColumn(3)
                .setCellEditor(
                        new DefaultCellEditor(
                                grades
                        )
                );

        addDefaultSubjects();
    }

    // =========================================================
    // DEFAULT SUBJECTS
    // =========================================================

    private void addDefaultSubjects() {

        for (int i = 1; i <= 5; i++) {

            model.addRow(
                    new Object[]{
                            i,
                            "Subject " + i,
                            4,
                            "O",
                            "10.0",
                            "40.0"
                    }
            );
        }
    }

    // =========================================================
    // BUTTON PANEL
    // =========================================================

    private JPanel createButtonPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setOpaque(false);

        JPanel left =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                5
                        )
                );

        left.setOpaque(false);

        JButton add =
                createButton(
                        "Add Subject",
                        GREEN
                );

        add.addActionListener(
                e -> addSubject()
        );

        JButton remove =
                createButton(
                        "Remove Subject",
                        RED
                );

        remove.addActionListener(
                e -> removeSubject()
        );

        JButton reset =
                createButton(
                        "Reset",
                        ORANGE
                );

        reset.addActionListener(
                e -> resetCalculator()
        );

        JButton graph =
                createButton(
                        "CGPA Graph",
                        PURPLE
                );

        graph.addActionListener(
                e -> showGraph()
        );

        left.add(add);
        left.add(remove);
        left.add(reset);
        left.add(graph);

        panel.add(
                left,
                BorderLayout.WEST
        );

        JButton calculate =
                createButton(
                        "Calculate CGPA",
                        BLUE
                );

        calculate.addActionListener(
                e -> calculateCGPA()
        );

        panel.add(
                calculate,
                BorderLayout.EAST
        );

        return panel;
    }

    // =========================================================
    // ADD SUBJECT
    // =========================================================

    private void addSubject() {

        int number =
                model.getRowCount() + 1;

        model.addRow(
                new Object[]{
                        number,
                        "Subject " + number,
                        4,
                        "O",
                        "10.0",
                        "40.0"
                }
        );

        subjectsValue.setText(
                String.valueOf(
                        model.getRowCount()
                )
        );
    }

    // =========================================================
    // REMOVE SUBJECT
    // =========================================================

    private void removeSubject() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a subject first.",
                    "Remove Subject",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        model.removeRow(row);

        for (int i = 0;
             i < model.getRowCount();
             i++) {

            model.setValueAt(
                    i + 1,
                    i,
                    0
            );
        }

        subjectsValue.setText(
                String.valueOf(
                        model.getRowCount()
                )
        );
    }

    // =========================================================
    // CALCULATE CGPA
    // =========================================================

    private void calculateCGPA() {

        if (table.isEditing()) {

            table.getCellEditor()
                    .stopCellEditing();
        }

        double totalCredits = 0;
        double totalPoints = 0;

        for (int i = 0;
             i < model.getRowCount();
             i++) {

            double credit;

            try {

                credit =
                        Double.parseDouble(
                                model.getValueAt(
                                        i,
                                        2
                                ).toString()
                        );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid credits in Subject "
                                + (i + 1),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (credit <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Credits must be greater than zero.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String grade =
                    model.getValueAt(
                            i,
                            3
                    ).toString();

            double point =
                    getGradePoint(grade);

            double creditPoint =
                    credit * point;

            totalCredits += credit;
            totalPoints += creditPoint;

            model.setValueAt(
                    String.format(
                            "%.1f",
                            point
                    ),
                    i,
                    4
            );

            model.setValueAt(
                    String.format(
                            "%.1f",
                            creditPoint
                    ),
                    i,
                    5
            );
        }

        if (totalCredits <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Total credits must be greater than zero.",
                    "CGPA Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        double cgpa =
                totalPoints / totalCredits;

        cgpaValue.setText(
                String.format(
                        "%.2f",
                        cgpa
                )
        );

        creditsValue.setText(
                String.format(
                        "%.0f",
                        totalCredits
                )
        );

        subjectsValue.setText(
                String.valueOf(
                        model.getRowCount()
                )
        );

        // =====================================================
        // DATE & TIME
        // =====================================================

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy, hh:mm:ss a"
                );

        String dateTime =
                LocalDateTime.now()
                        .format(formatter);

        // =====================================================
        // SAVE HISTORY
        // =====================================================

        history.add(
                new HistoryEntry(
                        dateTime,
                        cgpa,
                        totalCredits,
                        model.getRowCount()
                )
        );

        saveHistory();

        JOptionPane.showMessageDialog(
                this,
                "Your CGPA is "
                        + String.format(
                                "%.2f",
                                cgpa
                        ),
                "CGPA Result",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // GRADE POINT
    // =========================================================

    private double getGradePoint(
            String grade
    ) {

        switch (
                grade.toUpperCase()
        ) {

            case "O":
                return 10;

            case "E":
                return 9;

            case "A":
                return 8;

            case "B":
                return 7;

            case "C":
                return 6;

            case "D":
                return 5;

            case "F":
                return 0;

            default:
                return 0;
        }
    }

    // =========================================================
    // RESET
    // =========================================================

    private void resetCalculator() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Reset all subject entries?",
                        "Reset Calculator",
                        JOptionPane.YES_NO_OPTION
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        model.setRowCount(0);

        addDefaultSubjects();

        cgpaValue.setText("0.00");

        creditsValue.setText("0");

        subjectsValue.setText("5");
    }

    // =========================================================
    // HISTORY WINDOW
    // =========================================================

    private void showHistory() {

        JFrame historyFrame =
                new JFrame(
                        "CGPA History"
                );

        historyFrame.setSize(
                1100,
                750
        );

        historyFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        historyFrame.setLocationRelativeTo(this);

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(BACKGROUND);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(PURPLE);

        header.setPreferredSize(
                new Dimension(
                        0,
                        85
                )
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        30,
                        0,
                        30
                )
        );

        JLabel title =
                new JLabel(
                        "CGPA HISTORY"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
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
        // HISTORY TABLE
        // =====================================================

        String[] columns = {
                "DATE & TIME",
                "CGPA",
                "TOTAL CREDITS",
                "SUBJECTS"
        };

        DefaultTableModel historyModel =
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

        for (
                int i = history.size() - 1;
                i >= 0;
                i--
        ) {

            HistoryEntry entry =
                    history.get(i);

            historyModel.addRow(
                    new Object[]{
                            entry.dateTime,
                            String.format(
                                    "%.2f",
                                    entry.cgpa
                            ),
                            String.format(
                                    "%.0f",
                                    entry.totalCredits
                            ),
                            entry.subjects
                    }
            );
        }

        if (history.isEmpty()) {

            historyModel.addRow(
                    new Object[]{
                            "No history yet.",
                            "-",
                            "-",
                            "-"
                    }
            );
        }

        JTable historyTable =
                new JTable(
                        historyModel
                );

        historyTable.setRowHeight(52);

        historyTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        historyTable.setForeground(TEXT);

        historyTable.setBackground(Color.WHITE);

        historyTable.setGridColor(
                new Color(
                        220,
                        224,
                        232
                )
        );

        historyTable.setShowGrid(true);

        historyTable.setSelectionBackground(
                new Color(
                        225,
                        215,
                        245
                )
        );

        historyTable.setSelectionForeground(TEXT);

        historyTable.getTableHeader()
                .setBackground(PURPLE);

        historyTable.getTableHeader()
                .setForeground(Color.WHITE);

        historyTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                15
                        )
                );

        historyTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                45
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        historyTable
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
                                12,
                                12
                        )
                );

        bottom.setBackground(BACKGROUND);

        JButton clearButton =
                createButton(
                        "Clear History",
                        RED
                );

        clearButton.setPreferredSize(
                new Dimension(
                        165,
                        50
                )
        );

        clearButton.addActionListener(
                e -> {

                    if (history.isEmpty()) {

                        JOptionPane.showMessageDialog(
                                historyFrame,
                                "History is already empty.",
                                "CGPA History",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        return;
                    }

                    int result =
                            JOptionPane.showConfirmDialog(
                                    historyFrame,
                                    "Are you sure you want to clear all CGPA history?",
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

                        historyModel.setRowCount(0);

                        historyModel.addRow(
                                new Object[]{
                                        "No history yet.",
                                        "-",
                                        "-",
                                        "-"
                                }
                        );

                        JOptionPane.showMessageDialog(
                                historyFrame,
                                "CGPA history cleared successfully.",
                                "History",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
        );

        JButton closeButton =
                createButton(
                        "Close",
                        BLUE
                );

        closeButton.setPreferredSize(
                new Dimension(
                        120,
                        50
                )
        );

        closeButton.addActionListener(
                e -> historyFrame.dispose()
        );

        bottom.add(clearButton);
        bottom.add(closeButton);

        root.add(
                bottom,
                BorderLayout.SOUTH
        );

        historyFrame.setContentPane(root);

        historyFrame.setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        historyFrame.setVisible(true);
    }

    // =========================================================
    // CGPA GRAPH
    // =========================================================

    private void showGraph() {

        if (history.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Calculate CGPA first.",
                    "CGPA Graph",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        JFrame graph =
                new JFrame(
                        "CGPA Performance Graph"
                );

        graph.setSize(
                900,
                600
        );

        graph.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        graph.setLocationRelativeTo(this);

        graph.add(
                new GraphPanel()
        );

        graph.setVisible(true);
    }

    // =========================================================
    // GRAPH PANEL
    // =========================================================

    private class GraphPanel
            extends JPanel {

        @Override
        protected void paintComponent(
                Graphics graphics
        ) {

            super.paintComponent(graphics);

            Graphics2D g =
                    (Graphics2D) graphics;

            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int width = getWidth();
            int height = getHeight();

            int left = 70;
            int right = 40;
            int top = 60;
            int bottom = 70;

            int graphWidth =
                    width - left - right;

            int graphHeight =
                    height - top - bottom;

            // =================================================
            // TITLE
            // =================================================

            g.setColor(TEXT);

            g.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            24
                    )
            );

            g.drawString(
                    "CGPA Performance",
                    left,
                    35
            );

            // =================================================
            // GRID
            // =================================================

            for (int i = 0; i <= 10; i++) {

                int y =
                        height
                                - bottom
                                - (
                                i * graphHeight / 10
                        );

                g.setColor(
                        new Color(
                                225,
                                228,
                                238
                        )
                );

                g.drawLine(
                        left,
                        y,
                        width - right,
                        y
                );

                g.setColor(Color.GRAY);

                g.setFont(
                        new Font(
                                "Segoe UI",
                                Font.PLAIN,
                                11
                        )
                );

                g.drawString(
                        String.valueOf(i),
                        45,
                        y + 4
                );
            }

            // =================================================
            // AXIS
            // =================================================

            g.setColor(Color.DARK_GRAY);

            g.drawLine(
                    left,
                    top,
                    left,
                    height - bottom
            );

            g.drawLine(
                    left,
                    height - bottom,
                    width - right,
                    height - bottom
            );

            int count = history.size();

            int[] x = new int[count];
            int[] y = new int[count];

            // =================================================
            // POINTS
            // =================================================

            for (int i = 0; i < count; i++) {

                double value =
                        history.get(i).cgpa;

                if (count == 1) {

                    x[i] =
                            left
                                    + graphWidth / 2;

                } else {

                    x[i] =
                            left
                                    + i * graphWidth
                                    / (count - 1);
                }

                y[i] =
                        height
                                - bottom
                                - (int) (
                                value
                                        * graphHeight
                                        / 10
                        );
            }

            // =================================================
            // LINE
            // =================================================

            g.setColor(PURPLE);

            g.setStroke(
                    new BasicStroke(4)
            );

            for (int i = 0;
                 i < count - 1;
                 i++) {

                g.drawLine(
                        x[i],
                        y[i],
                        x[i + 1],
                        y[i + 1]
                );
            }

            // =================================================
            // POINTS + VALUES
            // =================================================

            for (int i = 0;
                 i < count;
                 i++) {

                g.setColor(BLUE);

                g.fillOval(
                        x[i] - 7,
                        y[i] - 7,
                        14,
                        14
                );

                g.setColor(TEXT);

                g.setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                12
                        )
                );

                g.drawString(
                        String.format(
                                "%.2f",
                                history.get(i).cgpa
                        ),
                        x[i] - 15,
                        y[i] - 15
                );
            }
        }
    }

    // =========================================================
    // COMMON BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        20,
                        12,
                        20
                )
        );

        return button;
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    CGPACalculator calculator =
                            new CGPACalculator();

                    calculator.setVisible(true);
                }
        );
    }
}