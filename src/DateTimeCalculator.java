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

public class DateTimeCalculator extends JFrame {

    // =========================================================
    // COLORS
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

    // =========================================================
    // VARIABLES
    // =========================================================

    private Dashboard dashboard;

    private final java.util.List<String> history =
            new java.util.ArrayList<>();

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
                new Dimension(1100, 700)
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
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(BACKGROUND);

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

        header.setBackground(WHITE);

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

        title.setForeground(NAVY);

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

        subtitle.setForeground(MUTED);

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

        main.setBackground(BACKGROUND);

        main.setBorder(
                new EmptyBorder(
                        0,
                        30,
                        25,
                        30
                )
        );

        // -----------------------------------------------------
        // DATE DIFFERENCE
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
        // DATE OPERATIONS
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
        // AGE CALCULATOR
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
        // CURRENT DATE & TIME
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

        card.setBackground(WHITE);

        card.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        JPanel colorBar =
                new JPanel();

        colorBar.setBackground(accent);

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

        category.setForeground(accent);

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

        heading.setForeground(NAVY);

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

        desc.setForeground(MUTED);

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

        content.add(category);

        content.add(
                Box.createVerticalStrut(14)
        );

        content.add(heading);

        content.add(
                Box.createVerticalStrut(22)
        );

        content.add(desc);

        card.add(
                content,
                BorderLayout.CENTER
        );

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

        button.setBackground(accent);

        button.setForeground(Color.WHITE);

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

        button.addActionListener(action);

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

        card.setBackground(WHITE);

        card.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        JPanel colorBar =
                new JPanel();

        colorBar.setBackground(ORANGE);

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

        live.setForeground(ORANGE);

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

        heading.setForeground(NAVY);

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        currentDateLabel =
                new JLabel(
                        ""
                );

        currentDateLabel.setForeground(NAVY);

        currentDateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        currentDayLabel =
                new JLabel(
                        ""
                );

        currentDayLabel.setForeground(MUTED);

        currentDayLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        currentTimeLabel =
                new JLabel(
                        ""
                );

        currentTimeLabel.setForeground(ORANGE);

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
                            startField
                                    .getText()
                                    .trim()
                    );

            LocalDate end =
                    LocalDate.parse(
                            endField
                                    .getText()
                                    .trim()
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
                            dateField
                                    .getText()
                                    .trim()
                    );

            int value =
                    ((Number)
                            valueSpinner
                                    .getValue())
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
                    unit
                            .getSelectedItem()
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
                            dobField
                                    .getText()
                                    .trim()
                    );

            LocalDate today =
                    LocalDate.now();

            if (dob.isAfter(today)) {

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

        panel.setBackground(WHITE);

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

        labelComponent.setForeground(TEXT);

        labelComponent.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 0;

        gbc.gridy =
                panel.getComponentCount();

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

        info.setForeground(MUTED);

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

        field.setForeground(TEXT);

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
    // RESULT DIALOG
    // =========================================================

    private void showResult(
            String title,
            String message,
            Color accent
    ) {

        addToHistory(
                title,
                message
        );

        JDialog dialog =
                new JDialog(
                        this,
                        title,
                        true
                );

        dialog.setSize(
                550,
                450
        );

        dialog.setMinimumSize(
                new Dimension(
                        500,
                        400
                )
        );

        dialog.setLocationRelativeTo(this);

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(WHITE);

        // -----------------------------------------------------
        // TOP
        // -----------------------------------------------------

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );

        top.setBackground(WHITE);

        top.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        15,
                        25
                )
        );

        JPanel accentBar =
                new JPanel();

        accentBar.setBackground(accent);

        accentBar.setPreferredSize(
                new Dimension(
                        5,
                        45
                )
        );

        top.add(
                accentBar,
                BorderLayout.WEST
        );

        JLabel heading =
                new JLabel(
                        title
                );

        heading.setForeground(NAVY);

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        heading.setBorder(
                new EmptyBorder(
                        0,
                        15,
                        0,
                        0
                )
        );

        top.add(
                heading,
                BorderLayout.CENTER
        );

        main.add(
                top,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // CONTENT
        // -----------------------------------------------------

        JTextArea area =
                new JTextArea(
                        message
                );

        area.setEditable(false);

        area.setFocusable(false);

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setBackground(WHITE);

        area.setForeground(TEXT);

        area.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        area.setBorder(
                new EmptyBorder(
                        10,
                        30,
                        10,
                        30
                )
        );

        main.add(
                area,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BUTTON
        // -----------------------------------------------------

        JPanel footer =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                15,
                                12
                        )
                );

        footer.setBackground(WHITE);

        JButton close =
                new JButton(
                        "CLOSE"
                );

        close.setBackground(accent);

        close.setForeground(Color.WHITE);

        close.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        close.setFocusPainted(false);

        close.setBorderPainted(false);

        close.setPreferredSize(
                new Dimension(
                        110,
                        40
                )
        );

        close.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        close.addActionListener(
                e -> dialog.dispose()
        );

        footer.add(close);

        main.add(
                footer,
                BorderLayout.SOUTH
        );

        dialog.setContentPane(main);

        dialog.setVisible(true);
    }

    // =========================================================
    // HISTORY
    // =========================================================

    private void addToHistory(
            String title,
            String message
    ) {

        String time =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                                "dd MMM yyyy, hh:mm:ss a"
                        )
                );

        // No Unicode separator characters.
        // This prevents square/box characters.

        String entry =
                title
                        + "\n"
                        + time
                        + "\n\n"
                        + message;

        history.add(
                0,
                entry
        );
    }

    // =========================================================
    // MODERN HISTORY DIALOG
    // =========================================================

    private void showHistory() {

        final JDialog dialog =
                new JDialog(
                        this,
                        "Calculation History",
                        true
                );

        dialog.setSize(
                900,
                680
        );

        dialog.setMinimumSize(
                new Dimension(
                        760,
                        560
                )
        );

        dialog.setLocationRelativeTo(this);

        dialog.setResizable(true);

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(WHITE);

        header.setBorder(
                new EmptyBorder(
                        22,
                        28,
                        20,
                        28
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
                        "Calculation History"
                );

        title.setForeground(NAVY);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        JLabel subtitle;

        if (history.isEmpty()) {

            subtitle =
                    new JLabel(
                            "No calculations recorded yet."
                    );

        } else {

            subtitle =
                    new JLabel(
                            history.size()
                                    + " calculation"
                                    + (
                                    history.size() == 1
                                            ? ""
                                            : "s"
                            )
                                    + " saved in this session."
                    );
        }

        subtitle.setForeground(MUTED);

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
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

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // HISTORY AREA
        // =====================================================

        JPanel historyPanel =
                new JPanel();

        historyPanel.setBackground(
                BACKGROUND
        );

        historyPanel.setLayout(
                new BoxLayout(
                        historyPanel,
                        BoxLayout.Y_AXIS
                )
        );

        historyPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        if (history.isEmpty()) {

            JPanel emptyCard =
                    createEmptyHistoryCard();

            historyPanel.add(
                    emptyCard
            );

        } else {

            for (String entry : history) {

                JPanel card =
                        createHistoryCard(
                                entry
                        );

                historyPanel.add(card);

                historyPanel.add(
                        Box.createVerticalStrut(
                                12
                        )
                );
            }
        }

        JScrollPane scrollPane =
                new JScrollPane(
                        historyPanel
                );

        scrollPane.setBorder(null);

        scrollPane.setBackground(
                BACKGROUND
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(WHITE);

        footer.setBorder(
                new EmptyBorder(
                        14,
                        25,
                        14,
                        25
                )
        );

        JLabel countLabel =
                new JLabel(
                        history.size()
                                + " calculation"
                                + (
                                history.size() == 1
                                        ? ""
                                        : "s"
                        )
                );

        countLabel.setForeground(MUTED);

        countLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        footer.add(
                countLabel,
                BorderLayout.WEST
        );

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttons.setOpaque(false);

        // -----------------------------------------------------
        // CLEAR ALL
        // -----------------------------------------------------

        JButton clearButton =
                createModernButton(
                        "CLEAR ALL",
                        NAVY,
                        135
                );

        clearButton.addActionListener(
                e -> {

                    if (history.isEmpty()) {

                        return;
                    }

                    showClearHistoryConfirmation(
                            dialog
                    );
                }
        );

        // -----------------------------------------------------
        // CLOSE
        // -----------------------------------------------------

        JButton closeButton =
                createModernButton(
                        "CLOSE",
                        BLUE,
                        110
                );

        closeButton.addActionListener(
                e -> dialog.dispose()
        );

        buttons.add(clearButton);

        buttons.add(closeButton);

        footer.add(
                buttons,
                BorderLayout.EAST
        );

        mainPanel.add(
                footer,
                BorderLayout.SOUTH
        );

        dialog.setContentPane(
                mainPanel
        );

        dialog.setVisible(true);
    }

    // =========================================================
    // HISTORY CARD
    // =========================================================

    private JPanel createHistoryCard(
            String entry
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        // =====================================================
        // ACCENT BAR
        // =====================================================

        JPanel accent =
                new JPanel();

        accent.setBackground(BLUE);

        accent.setPreferredSize(
                new Dimension(
                        5,
                        0
                )
        );

        card.add(
                accent,
                BorderLayout.WEST
        );

        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content =
                new JPanel();

        content.setBackground(WHITE);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBorder(
                new EmptyBorder(
                        0,
                        16,
                        0,
                        8
                )
        );

        String[] lines =
                entry.split(
                        "\n",
                        -1
                );

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        if (lines.length > 0) {

            JLabel entryTitle =
                    new JLabel(
                            lines[0]
                    );

            entryTitle.setForeground(NAVY);

            entryTitle.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            18
                    )
            );

            content.add(
                    entryTitle
            );
        }

        // -----------------------------------------------------
        // DATE / TIME
        // -----------------------------------------------------

        if (lines.length > 1) {

            JLabel entryTime =
                    new JLabel(
                            lines[1]
                    );

            entryTime.setForeground(MUTED);

            entryTime.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            12
                    )
            );

            content.add(
                    Box.createVerticalStrut(
                            4
                    )
            );

            content.add(
                    entryTime
            );
        }

        // -----------------------------------------------------
        // RESULT
        // -----------------------------------------------------

        StringBuilder result =
                new StringBuilder();

        for (
                int i = 3;
                i < lines.length;
                i++
        ) {

            result.append(
                    lines[i]
            );

            if (
                    i < lines.length - 1
            ) {

                result.append("\n");
            }
        }

        JTextArea resultArea =
                new JTextArea(
                        result.toString()
                );

        resultArea.setEditable(false);

        resultArea.setFocusable(false);

        resultArea.setLineWrap(true);

        resultArea.setWrapStyleWord(true);

        resultArea.setBackground(WHITE);

        resultArea.setForeground(TEXT);

        resultArea.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        resultArea.setBorder(
                new EmptyBorder(
                        12,
                        0,
                        0,
                        0
                )
        );

        content.add(
                resultArea
        );

        card.add(
                content,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // EMPTY HISTORY CARD
    // =========================================================

    private JPanel createEmptyHistoryCard() {

        JPanel card =
                new JPanel();

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                55,
                                30,
                                55,
                                30
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "No Calculation History"
                );

        title.setForeground(NAVY);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel text =
                new JLabel(
                        "Your calculations will appear here."
                );

        text.setForeground(MUTED);

        text.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        text.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(text);

        return card;
    }

    // =========================================================
    // MODERN BUTTON
    // =========================================================

    private JButton createModernButton(
            String text,
            Color color,
            int width
    ) {

        JButton button =
                new JButton(
                        text
                );

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

        button.setPreferredSize(
                new Dimension(
                        width,
                        42
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // CLEAR HISTORY CONFIRMATION
    // =========================================================

    private void showClearHistoryConfirmation(
            JDialog historyDialog
    ) {

        final JDialog confirm =
                new JDialog(
                        historyDialog,
                        "Clear History",
                        true
                );

        confirm.setSize(
                420,
                220
        );

        confirm.setLocationRelativeTo(
                historyDialog
        );

        confirm.setResizable(false);

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(WHITE);

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(NAVY);

        header.setBorder(
                new EmptyBorder(
                        16,
                        20,
                        16,
                        20
                )
        );

        JLabel title =
                new JLabel(
                        "Clear History"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        main.add(
                header,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // MESSAGE
        // -----------------------------------------------------

        JPanel content =
                new JPanel();

        content.setBackground(WHITE);

        content.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        10,
                        20
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel message =
                new JLabel(
                        "Are you sure you want to clear"
                );

        message.setForeground(TEXT);

        message.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        message.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel message2 =
                new JLabel(
                        "all calculation history?"
                );

        message2.setForeground(TEXT);

        message2.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        message2.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(message);

        content.add(
                Box.createVerticalStrut(3)
        );

        content.add(message2);

        main.add(
                content,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );

        buttons.setBackground(WHITE);

        JButton cancel =
                createModernButton(
                        "CANCEL",
                        MUTED,
                        100
                );

        cancel.addActionListener(
                e -> confirm.dispose()
        );

        JButton clear =
                createModernButton(
                        "CLEAR",
                        NAVY,
                        100
                );

        clear.addActionListener(
                e -> {

                    history.clear();

                    confirm.dispose();

                    historyDialog.dispose();

                    showHistory();
                }
        );

        buttons.add(cancel);

        buttons.add(clear);

        main.add(
                buttons,
                BorderLayout.SOUTH
        );

        confirm.setContentPane(main);

        confirm.setVisible(true);
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

        footer.setBackground(WHITE);

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

        left.setForeground(MUTED);

        left.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        // =====================================================
        // HISTORY BUTTON
        // =====================================================

        JButton historyButton =
                new JButton(
                        "HISTORY"
                );

        historyButton.setBackground(BLUE);

        historyButton.setForeground(Color.WHITE);

        historyButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        historyButton.setFocusPainted(false);

        historyButton.setBorderPainted(false);

        historyButton.setPreferredSize(
                new Dimension(
                        120,
                        40
                )
        );

        historyButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        historyButton.addActionListener(
                e -> showHistory()
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JButton back =
                new JButton(
                        "BACK"
                );

        back.setBackground(NAVY);

        back.setForeground(Color.WHITE);

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

        JPanel rightButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        rightButtons.setOpaque(false);

        rightButtons.add(
                historyButton
        );

        rightButtons.add(
                back
        );

        footer.add(
                left,
                BorderLayout.WEST
        );

        footer.add(
                rightButtons,
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