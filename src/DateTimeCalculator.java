import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.SpinnerNumberModel;

/**
 * Date & Time Calculator
 *
 * Dashboard -> Date & Time Calculator
 * BACK -> Dashboard
 *
 * Features:
 * 1. Date Difference
 * 2. Date Operations
 * 3. Age Calculator
 * 4. Live Current Date & Time
 */
public class DateTimeCalculator extends JFrame {

    // =========================================================
    // COLORS - MATCHING CALCULATOR SUITE DASHBOARD
    // =========================================================

    private static final Color BACKGROUND =
            new Color(246, 248, 252);

    private static final Color WHITE =
            Color.WHITE;

    private static final Color NAVY =
            new Color(25, 50, 85);

    private static final Color TEXT =
            new Color(25, 45, 75);

    private static final Color MUTED =
            new Color(90, 115, 150);

    private static final Color BORDER =
            new Color(215, 222, 232);

    private static final Color BLUE =
            new Color(50, 120, 225);

    private static final Color GREEN =
            new Color(25, 180, 130);

    private static final Color PURPLE =
            new Color(145, 75, 215);

    private static final Color ORANGE =
            new Color(245, 145, 25);

    private Dashboard dashboard;

    private JLabel currentDateLabel;
    private JLabel currentDayLabel;
    private JLabel currentTimeLabel;

    private Timer clockTimer;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DateTimeCalculator() {
        this(null);
    }

    public DateTimeCalculator(Dashboard dashboard) {

        this.dashboard = dashboard;

        setTitle("Date & Time Calculator");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1100, 1100)
        );

        buildUI();

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        startLiveClock();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(new BorderLayout());

        root.setBackground(
                BACKGROUND
        );

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        root.add(
                createMainContent(),
                BorderLayout.CENTER
        );

        root.add(
                createFooter(),
                BorderLayout.SOUTH
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

        header.setBackground(
                WHITE
        );

        header.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        20,
                        30
                )
        );

        header.setPreferredSize(
                new Dimension(
                        0,
                        125
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Date & Time Tools"
                );

        title.setForeground(
                NAVY
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Calculate dates, age and time differences quickly."
                );

        subtitle.setForeground(
                MUTED
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        return header;
    }

    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private JPanel createMainContent() {

        JPanel main =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                26,
                                26
                        )
                );

        main.setBackground(
                BACKGROUND
        );

        main.setBorder(
                new EmptyBorder(
                        0,
                        30,
                        25,
                        30
                )
        );

        // -----------------------------------------------------
        // 1. DATE DIFFERENCE
        // -----------------------------------------------------

        main.add(
                createToolCard(
                        "DATE & TIME",
                        "Date Difference",
                        "Find the exact difference between two dates in days and weeks.",
                        "CALCULATE",
                        BLUE,
                        e -> showDateDifference()
                )
        );

        // -----------------------------------------------------
        // 2. DATE OPERATIONS
        // -----------------------------------------------------

        main.add(
                createToolCard(
                        "DATE & TIME",
                        "Date Operations",
                        "Add or subtract days, weeks, months or years from any date.",
                        "CALCULATE",
                        GREEN,
                        e -> showDateOperations()
                )
        );

        // -----------------------------------------------------
        // 3. AGE CALCULATOR
        // -----------------------------------------------------

        main.add(
                createToolCard(
                        "DATE & TIME",
                        "Age Calculator",
                        "Calculate your exact age in years, months and days.",
                        "CALCULATE",
                        PURPLE,
                        e -> showAgeCalculator()
                )
        );

        // -----------------------------------------------------
        // 4. CURRENT DATE & TIME
        // -----------------------------------------------------

        main.add(
                createCurrentDateTimeCard()
        );

        return main;
    }

    // =========================================================
    // TOOL CARD
    // =========================================================

    private JPanel createToolCard(
            String smallTitle,
            String title,
            String description,
            String buttonText,
            Color accent,
            java.awt.event.ActionListener action
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                WHITE
        );

        card.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        // Top color line
        JPanel colorBar =
                new JPanel();

        colorBar.setBackground(
                accent
        );

        colorBar.setPreferredSize(
                new Dimension(
                        0,
                        7
                )
        );

        card.add(
                colorBar,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // CONTENT
        // -----------------------------------------------------

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(
                        22,
                        32,
                        10,
                        32
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel category =
                new JLabel(
                        smallTitle
                );

        category.setForeground(
                accent
        );

        category.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        category.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel heading =
                new JLabel(
                        title
                );

        heading.setForeground(
                NAVY
        );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel desc =
                new JLabel(
                        "<html><div style='text-align:left;'>"
                                + description
                                + "</div></html>"
                );

        desc.setForeground(
                MUTED
        );

        desc.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        desc.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(
                category
        );

        content.add(
                Box.createVerticalStrut(14)
        );

        content.add(
                heading
        );

        content.add(
                Box.createVerticalStrut(22)
        );

        content.add(
                desc
        );

        card.add(
                content,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BUTTON
        // -----------------------------------------------------

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.setBorder(
                new EmptyBorder(
                        0,
                        32,
                        25,
                        32
                )
        );

        JButton button =
                new JButton(
                        buttonText
                );

        button.setBackground(
                accent
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

        button.setPreferredSize(
                new Dimension(
                        180,
                        52
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.addActionListener(
                action
        );

        buttonPanel.add(button);

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // CURRENT DATE & TIME CARD
    // =========================================================

    private JPanel createCurrentDateTimeCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                WHITE
        );

        card.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        JPanel colorBar =
                new JPanel();

        colorBar.setBackground(
                ORANGE
        );

        colorBar.setPreferredSize(
                new Dimension(
                        0,
                        7
                )
        );

        card.add(
                colorBar,
                BorderLayout.NORTH
        );

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(
                        8,
                        32,
                        20,
                        32
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel live =
                new JLabel(
                        "LIVE"
                );

        live.setForeground(
                ORANGE
        );

        live.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        JLabel heading =
                new JLabel(
                        "Current Date & Time"
                );

        heading.setForeground(
                NAVY
        );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        currentDateLabel =
                new JLabel(
                        "22 August 2026"
                );

        currentDateLabel.setForeground(
                NAVY
        );

        currentDateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        currentDayLabel =
                new JLabel(
                        "Saturday"
                );

        currentDayLabel.setForeground(
                MUTED
        );

        currentDayLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        currentTimeLabel =
                new JLabel(
                        "05:18:21 pm"
                );

        currentTimeLabel.setForeground(
                ORANGE
        );

        currentTimeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );

        content.add(live);

        content.add(
                Box.createVerticalStrut(16)
        );

        content.add(heading);

        content.add(
                Box.createVerticalStrut(22)
        );

        content.add(currentDateLabel);

        content.add(
                Box.createVerticalStrut(6)
        );

        content.add(currentDayLabel);

        content.add(
                Box.createVerticalStrut(15)
        );

        content.add(currentTimeLabel);

        card.add(
                content,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // DATE DIFFERENCE
    // =========================================================

    private void showDateDifference() {

        JTextField startField =
                createDateField(
                        LocalDate.now()
                                .minusDays(7)
                                .toString()
                );

        JTextField endField =
                createDateField(
                        LocalDate.now()
                                .toString()
                );

        JPanel panel =
                createDialogPanel(
                        "Date Difference"
                );

        addDialogRow(
                panel,
                "Start Date:",
                startField
        );

        addDialogRow(
                panel,
                "End Date:",
                endField
        );

        addDialogInfo(
                panel,
                "Format: YYYY-MM-DD"
        );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Date Difference",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                result != JOptionPane.OK_OPTION
        ) {
            return;
        }

        try {

            LocalDate start =
                    LocalDate.parse(
                            startField.getText().trim()
                    );

            LocalDate end =
                    LocalDate.parse(
                            endField.getText().trim()
                    );

            long days =
                    Math.abs(
                            ChronoUnit.DAYS.between(
                                    start,
                                    end
                            )
                    );

            long weeks =
                    days / 7;

            long remainingDays =
                    days % 7;

            Period period =
                    Period.between(
                            start.isBefore(end)
                                    ? start
                                    : end,
                            start.isBefore(end)
                                    ? end
                                    : start
                    );

            String message =
                    "Difference\n\n"
                            + "Years: "
                            + period.getYears()
                            + "\n"
                            + "Months: "
                            + period.getMonths()
                            + "\n"
                            + "Days: "
                            + period.getDays()
                            + "\n\n"
                            + "Total Days: "
                            + days
                            + "\n"
                            + "Weeks: "
                            + weeks
                            + " weeks "
                            + remainingDays
                            + " days";

            showResult(
                    "Date Difference Result",
                    message,
                    BLUE
            );

        } catch (Exception ex) {

            showInvalidDate();
        }
    }

    // =========================================================
    // DATE OPERATIONS
    // =========================================================

    private void showDateOperations() {

        JTextField dateField =
                createDateField(
                        LocalDate.now().toString()
                );

        JSpinner valueSpinner =
                new JSpinner(
                        new SpinnerNumberModel(
                                10,
                                -100000,
                                100000,
                                1
                        )
                );

        JComboBox<String> operation =
                new JComboBox<>(
                        new String[]{
                                "Add",
                                "Subtract"
                        }
                );

        JComboBox<String> unit =
                new JComboBox<>(
                        new String[]{
                                "Days",
                                "Weeks",
                                "Months",
                                "Years"
                        }
                );

        JPanel panel =
                createDialogPanel(
                        "Date Operations"
                );

        addDialogRow(
                panel,
                "Date:",
                dateField
        );

        addDialogRow(
                panel,
                "Value:",
                valueSpinner
        );

        addDialogRow(
                panel,
                "Operation:",
                operation
        );

        addDialogRow(
                panel,
                "Unit:",
                unit
        );

        addDialogInfo(
                panel,
                "Format: YYYY-MM-DD"
        );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Date Operations",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                result != JOptionPane.OK_OPTION
        ) {
            return;
        }

        try {

            LocalDate date =
                    LocalDate.parse(
                            dateField.getText().trim()
                    );

            int value =
                    ((Number) valueSpinner.getValue())
                            .intValue();

            boolean subtract =
                    operation
                            .getSelectedItem()
                            .toString()
                            .equals("Subtract");

            if (subtract) {
                value = -value;
            }

            String selectedUnit =
                    unit.getSelectedItem()
                            .toString();

            LocalDate resultDate;

            switch (selectedUnit) {

                case "Days":
                    resultDate =
                            date.plusDays(value);
                    break;

                case "Weeks":
                    resultDate =
                            date.plusWeeks(value);
                    break;

                case "Months":
                    resultDate =
                            date.plusMonths(value);
                    break;

                case "Years":
                    resultDate =
                            date.plusYears(value);
                    break;

                default:
                    resultDate = date;
            }

            String message =
                    "Original Date: "
                            + date
                            + "\n\n"
                            + "Operation: "
                            + operation.getSelectedItem()
                            + " "
                            + Math.abs(value)
                            + " "
                            + selectedUnit
                            + "\n\n"
                            + "Result Date:\n"
                            + resultDate
                            + "\n"
                            + formatLongDate(resultDate);

            showResult(
                    "Date Operations Result",
                    message,
                    GREEN
            );

        } catch (Exception ex) {

            showInvalidDate();
        }
    }

    // =========================================================
    // AGE CALCULATOR
    // =========================================================

    private void showAgeCalculator() {

        JTextField dobField =
                createDateField(
                        "2006-07-09"
                );

        JPanel panel =
                createDialogPanel(
                        "Age Calculator"
                );

        addDialogRow(
                panel,
                "Date of Birth:",
                dobField
        );

        addDialogInfo(
                panel,
                "Format: YYYY-MM-DD"
        );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Age Calculator",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                result != JOptionPane.OK_OPTION
        ) {
            return;
        }

        try {

            LocalDate dob =
                    LocalDate.parse(
                            dobField.getText().trim()
                    );

            LocalDate today =
                    LocalDate.now();

            if (
                    dob.isAfter(today)
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Date of birth cannot be in the future.",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Period age =
                    Period.between(
                            dob,
                            today
                    );

            long totalDays =
                    ChronoUnit.DAYS.between(
                            dob,
                            today
                    );

            String message =
                    "Date of Birth: "
                            + dob
                            + "\n\n"
                            + "Your Age:\n"
                            + age.getYears()
                            + " Years  "
                            + age.getMonths()
                            + " Months  "
                            + age.getDays()
                            + " Days\n\n"
                            + "Total Days: "
                            + totalDays
                            + "\n\n"
                            + "Today: "
                            + formatLongDate(today);

            showResult(
                    "Age Calculator Result",
                    message,
                    PURPLE
            );

        } catch (Exception ex) {

            showInvalidDate();
        }
    }

    // =========================================================
    // DIALOG PANEL
    // =========================================================

    private JPanel createDialogPanel(
            String title
    ) {

        JPanel panel =
                new JPanel();

        panel.setBackground(
                WHITE
        );

        panel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        10,
                        15
                )
        );

        panel.setLayout(
                new GridBagLayout()
        );

        return panel;
    }

    // =========================================================
    // DIALOG ROW
    // =========================================================

    private void addDialogRow(
            JPanel panel,
            String label,
            Component component
    ) {

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        JLabel labelComponent =
                new JLabel(
                        label
                );

        labelComponent.setForeground(
                TEXT
        );

        labelComponent.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = panel.getComponentCount();

        panel.add(
                labelComponent,
                gbc
        );

        gbc.gridx = 1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        if (
                component instanceof JTextField
        ) {

            component.setPreferredSize(
                    new Dimension(
                            180,
                            35
                    )
            );
        }

        panel.add(
                component,
                gbc
        );
    }

    // =========================================================
    // DIALOG INFO
    // =========================================================

    private void addDialogInfo(
            JPanel panel,
            String text
    ) {

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 1;

        gbc.gridy =
                panel.getComponentCount();

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.insets =
                new Insets(
                        2,
                        8,
                        8,
                        8
                );

        JLabel info =
                new JLabel(
                        text
                );

        info.setForeground(
                MUTED
        );

        info.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        panel.add(
                info,
                gbc
        );
    }

    // =========================================================
    // DATE FIELD
    // =========================================================

    private JTextField createDateField(
            String value
    ) {

        JTextField field =
                new JTextField(
                        value
                );

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(
                TEXT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(
                                        180,
                                        195,
                                        215
                                )
                        ),
                        new EmptyBorder(
                                5,
                                8,
                                5,
                                8
                        )
                )
        );

        return field;
    }

    // =========================================================
    // RESULT
    // =========================================================

    private void showResult(
            String title,
            String message,
            Color accent
    ) {

        JTextArea area =
                new JTextArea(
                        message
                );

        area.setEditable(false);

        area.setFocusable(false);

        area.setBackground(
                WHITE
        );

        area.setForeground(
                TEXT
        );

        area.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        area.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                WHITE
        );

        JPanel top =
                new JPanel();

        top.setBackground(
                accent
        );

        top.setPreferredSize(
                new Dimension(
                        0,
                        6
                )
        );

        panel.add(
                top,
                BorderLayout.NORTH
        );

        panel.add(
                area,
                BorderLayout.CENTER
        );

        JOptionPane.showMessageDialog(
                this,
                panel,
                title,
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // =========================================================
    // INVALID DATE
    // =========================================================

    private void showInvalidDate() {

        JOptionPane.showMessageDialog(
                this,
                "Please enter a valid date.\n\n"
                        + "Use this format:\n"
                        + "YYYY-MM-DD\n\n"
                        + "Example: 2026-08-22",
                "Invalid Date",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =========================================================
    // FORMAT LONG DATE
    // =========================================================

    private String formatLongDate(
            LocalDate date
    ) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMMM yyyy"
                );

        return date.format(
                formatter
        );
    }

    // =========================================================
    // LIVE CLOCK
    // =========================================================

    private void startLiveClock() {

        clockTimer =
                new Timer(
                        1000,
                        e -> updateLiveDateTime()
                );

        clockTimer.start();

        updateLiveDateTime();
    }

    private void updateLiveDateTime() {

        LocalDateTime now =
                LocalDateTime.now();

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(
                        "dd MMMM yyyy"
                );

        DateTimeFormatter dayFormatter =
                DateTimeFormatter.ofPattern(
                        "EEEE"
                );

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern(
                        "hh:mm:ss a"
                );

        currentDateLabel.setText(
                now.format(
                        dateFormatter
                )
        );

        currentDayLabel.setText(
                now.format(
                        dayFormatter
                )
        );

        currentTimeLabel.setText(
                now.format(
                        timeFormatter
                )
        );
    }

    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(
                WHITE
        );

        footer.setBorder(
                new EmptyBorder(
                        10,
                        30,
                        10,
                        30
                )
        );

        footer.setPreferredSize(
                new Dimension(
                        0,
                        62
                )
        );

        JLabel left =
                new JLabel(
                        "Date & Time Calculator  •  Calculator Suite"
                );

        left.setForeground(
                MUTED
        );

        left.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        JButton back =
                new JButton(
                        "BACK"
                );

        back.setBackground(
                NAVY
        );

        back.setForeground(
                Color.WHITE
        );

        back.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        back.setFocusPainted(false);

        back.setBorderPainted(false);

        back.setPreferredSize(
                new Dimension(
                        110,
                        40
                )
        );

        back.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        back.addActionListener(
                e -> backToDashboard()
        );

        footer.add(
                left,
                BorderLayout.WEST
        );

        footer.add(
                back,
                BorderLayout.EAST
        );

        return footer;
    }

    // =========================================================
    // BACK TO DASHBOARD
    // =========================================================

    private void backToDashboard() {

        if (
                clockTimer != null
        ) {

            clockTimer.stop();
        }

        dispose();

        if (
                dashboard != null
        ) {

            dashboard.setVisible(
                    true
            );

            dashboard.toFront();

        } else {

            Dashboard newDashboard =
                    new Dashboard();

            newDashboard.setVisible(
                    true
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    DateTimeCalculator calculator =
                            new DateTimeCalculator();

                    calculator.setVisible(
                            true
                    );
                }
        );
    }
}