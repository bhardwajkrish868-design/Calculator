import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dashboard extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color NAVY =
            new Color(25, 50, 85);

    private static final Color BACKGROUND =
            new Color(246, 248, 252);

    private static final Color MUTED =
            new Color(90, 115, 150);

    private static final Color BORDER =
            new Color(215, 222, 232);

    private static final Color CYAN =
            new Color(20, 180, 210);

    private static final Color PURPLE =
            new Color(145, 65, 215);

    private static final Color GREEN =
            new Color(20, 180, 145);

    private static final Color BLUE =
            new Color(50, 110, 220);

    private static final Color ORANGE =
            new Color(245, 155, 15);

    private static final Color RED =
            new Color(225, 70, 80);

    // =========================================================
    // DATE / TIME
    // =========================================================

    private JLabel dateLabel;
    private JLabel timeLabel;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Dashboard() {

        setTitle("Calculator Suite");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(
                        1100,
                        700
                )
        );

        buildUI();

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        startClock();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(
                BACKGROUND
        );

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        root.add(
                createMainPanel(),
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
                Color.WHITE
        );

        header.setPreferredSize(
                new Dimension(
                        0,
                        145
                )
        );

        header.setBorder(
                new EmptyBorder(
                        24,
                        48,
                        22,
                        48
                )
        );

        // -----------------------------------------------------
        // LEFT SIDE
        // -----------------------------------------------------

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
                        "CALCULATOR SUITE"
                );

        title.setForeground(
                NAVY
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Smart Tools   •   Faster Results   •   Simple Interface"
                );

        subtitle.setForeground(
                MUTED
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        left.add(title);

        left.add(
                Box.createVerticalStrut(6)
        );

        left.add(subtitle);

        header.add(
                left,
                BorderLayout.WEST
        );

        // -----------------------------------------------------
        // DATE TIME
        // -----------------------------------------------------

        JPanel dateTime =
                new JPanel();

        dateTime.setBackground(
                new Color(
                        250,
                        247,
                        255
                )
        );

        dateTime.setPreferredSize(
                new Dimension(
                        170,
                        76
                )
        );

        dateTime.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                195,
                                145,
                                245
                        ),
                        1
                )
        );

        dateTime.setLayout(
                new BoxLayout(
                        dateTime,
                        BoxLayout.Y_AXIS
                )
        );

        dateLabel =
                new JLabel(
                        "Loading..."
                );

        dateLabel.setForeground(
                NAVY
        );

        dateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        dateLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        timeLabel =
                new JLabel(
                        "00:00:00"
                );

        timeLabel.setForeground(
                PURPLE
        );

        timeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        timeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        dateTime.add(
                Box.createVerticalStrut(12)
        );

        dateTime.add(
                dateLabel
        );

        dateTime.add(
                Box.createVerticalStrut(4)
        );

        dateTime.add(
                timeLabel
        );

        header.add(
                dateTime,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // MAIN PANEL
    // =========================================================

    private JPanel createMainPanel() {

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        main.setBackground(
                BACKGROUND
        );

        main.setBorder(
                new EmptyBorder(
                        32,
                        48,
                        22,
                        48
                )
        );

        // -----------------------------------------------------
        // HEADING
        // -----------------------------------------------------

        JPanel heading =
                new JPanel();

        heading.setOpaque(false);

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel headingTitle =
                new JLabel(
                        "Choose a tool to get started"
                );

        headingTitle.setForeground(
                NAVY
        );

        headingTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        29
                )
        );

        JLabel headingSubtitle =
                new JLabel(
                        "Select a calculator or learn more about Calculator Suite."
                );

        headingSubtitle.setForeground(
                MUTED
        );

        headingSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        heading.add(
                headingTitle
        );

        heading.add(
                Box.createVerticalStrut(5)
        );

        heading.add(
                headingSubtitle
        );

        main.add(
                heading,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // CARDS
        // -----------------------------------------------------

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                22,
                                22
                        )
                );

        cards.setOpaque(false);

        // =====================================================
        // CARD 1 - BASIC
        // =====================================================

        cards.add(
                createCard(
                        "Basic Calculator",
                        "Perform everyday mathematical calculations quickly.",
                        "Addition • Subtraction • Multiplication • Division",
                        "OPEN CALCULATOR",
                        CYAN,
                        () -> openBasicCalculator()
                )
        );

        // =====================================================
        // CARD 2 - SCIENTIFIC
        // =====================================================

        cards.add(
                createCard(
                        "Scientific Calculator",
                        "Solve advanced mathematical expressions and functions.",
                        "Trigonometry • Log • Powers • Roots",
                        "OPEN CALCULATOR",
                        PURPLE,
                        () -> openScientificCalculator()
                )
        );

        // =====================================================
        // CARD 3 - CGPA
        // =====================================================

        cards.add(
                createCard(
                        "CGPA Calculator",
                        "Calculate your CGPA using subjects, grades and credits.",
                        "Grades • Credits • CGPA • History",
                        "OPEN CALCULATOR",
                        GREEN,
                        () -> openCGPACalculator()
                )
        );

        // =====================================================
        // CARD 4 - PERCENTAGE
        // =====================================================

        cards.add(
                createCard(
                        "Percentage Calculator",
                        "Calculate percentage, marks, increase and discount.",
                        "Percentage • Marks • Increase • Discount",
                        "OPEN CALCULATOR",
                        BLUE,
                        () -> openPercentageCalculator()
                )
        );

        // =====================================================
        // CARD 5 - ABOUT
        // =====================================================

        cards.add(
                createCard(
                        "About Calculator Suite",
                        "Learn more about the application and its developer.",
                        "Java Swing • Calculator Suite • TEAM 1",
                        "OPEN ABOUT",
                        ORANGE,
                        () -> openAbout()
                )
        );

        // =====================================================
        // CARD 6 - DATE & TIME
        // =====================================================

        cards.add(
                createCard(
                        "Date & Time Calculator",
                        "Calculate age, date difference and date operations.",
                        "Age • Date Difference • Date Operations",
                        "OPEN CALCULATOR",
                        RED,
                        () -> openDateTimeCalculator()
                )
        );

        main.add(
                cards,
                BorderLayout.CENTER
        );

        return main;
    }

    // =========================================================
    // CARD CREATOR
    // =========================================================

    private JPanel createCard(
            String title,
            String description,
            String features,
            String buttonText,
            Color accent,
            Runnable action
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createLineBorder(
                        BORDER,
                        1
                )
        );

        // -----------------------------------------------------
        // TOP COLOR BAR
        // -----------------------------------------------------

        JPanel colorBar =
                new JPanel();

        colorBar.setBackground(
                accent
        );

        colorBar.setPreferredSize(
                new Dimension(
                        0,
                        6
                )
        );

        card.add(
                colorBar,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // CARD CONTENT
        // -----------------------------------------------------

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(
                        14,
                        24,
                        8,
                        24
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setForeground(
                NAVY
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                                + description
                                + "</html>"
                );

        descriptionLabel.setForeground(
                MUTED
        );

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        JLabel featureLabel =
                new JLabel(
                        "<html>"
                                + features
                                + "</html>"
                );

        featureLabel.setForeground(
                accent
        );

        featureLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        content.add(
                titleLabel
        );

        content.add(
                Box.createVerticalStrut(8)
        );

        content.add(
                descriptionLabel
        );

        content.add(
                Box.createVerticalStrut(11)
        );

        content.add(
                featureLabel
        );

        card.add(
                content,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BUTTON
        // -----------------------------------------------------

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
                        12
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setPreferredSize(
                new Dimension(
                        0,
                        45
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.addActionListener(
                e -> action.run()
        );

        JPanel buttonPanel =
                new JPanel(
                        new BorderLayout()
                );

        buttonPanel.setOpaque(false);

        buttonPanel.setBorder(
                new EmptyBorder(
                        0,
                        24,
                        18,
                        24
                )
        );

        buttonPanel.add(
                button,
                BorderLayout.CENTER
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // BASIC CALCULATOR
    // =========================================================

    private void openBasicCalculator() {

        try {

            BasicCalculator calculator =
                    new BasicCalculator(this);

            calculator.setVisible(true);

            setVisible(false);

        } catch (Exception ex) {

            showError(
                    "BasicCalculator.java",
                    ex
            );
        }
    }

    // =========================================================
    // SCIENTIFIC CALCULATOR
    // =========================================================

    private void openScientificCalculator() {

        try {

            ScientificCalculator calculator =
                    new ScientificCalculator(this);

            calculator.setVisible(true);

            setVisible(false);

        } catch (Exception ex) {

            showError(
                    "ScientificCalculator.java",
                    ex
            );
        }
    }

    // =========================================================
    // CGPA CALCULATOR
    // =========================================================

    private void openCGPACalculator() {

        try {

            CGPACalculator calculator =
                    new CGPACalculator(this);

            calculator.setVisible(true);

            setVisible(false);

        } catch (Exception ex) {

            showError(
                    "CGPACalculator.java",
                    ex
            );
        }
    }

    // =========================================================
    // PERCENTAGE CALCULATOR
    // =========================================================

    private void openPercentageCalculator() {

        try {

            PercentageCalculator calculator =
                    new PercentageCalculator(this);

            calculator.setVisible(true);

            setVisible(false);

        } catch (Exception ex) {

            showError(
                    "PercentageCalculator.java",
                    ex
            );
        }
    }

    // =========================================================
    // DATE TIME CALCULATOR
    // =========================================================

    private void openDateTimeCalculator() {

        try {

            DateTimeCalculator calculator =
                    new DateTimeCalculator(this);

            calculator.setVisible(true);

            setVisible(false);

        } catch (Exception ex) {

            showError(
                    "DateTimeCalculator.java",
                    ex
            );
        }
    }

    // =========================================================
    // ABOUT
    // =========================================================

    private void openAbout() {

        try {

            About about =
                    new About();

            about.setVisible(true);

            setVisible(false);

        } catch (Exception ex) {

            showError(
                    "About.java",
                    ex
            );
        }
    }

    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    private void showError(
            String fileName,
            Exception ex
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Unable to open "
                        + fileName
                        + ".\n\n"
                        + "Make sure the class exists "
                        + "and its constructor accepts "
                        + "Dashboard.\n\n"
                        + "Error: "
                        + ex.getMessage(),
                "Calculator Suite",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // =========================================================
    // LIVE CLOCK
    // =========================================================

    private void startClock() {

        Timer timer =
                new Timer(
                        1000,
                        e -> updateClock()
                );

        timer.start();

        updateClock();
    }

    private void updateClock() {

        LocalDateTime now =
                LocalDateTime.now();

        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy"
                );

        DateTimeFormatter timeFormat =
                DateTimeFormatter.ofPattern(
                        "hh:mm:ss a"
                );

        dateLabel.setText(
                now.format(
                        dateFormat
                )
        );

        timeLabel.setText(
                now.format(
                        timeFormat
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
                Color.WHITE
        );

        footer.setPreferredSize(
                new Dimension(
                        0,
                        45
                )
        );

        footer.setBorder(
                new EmptyBorder(
                        10,
                        48,
                        10,
                        48
                )
        );

        JLabel left =
                new JLabel(
                        "Calculator Suite  •  Java Swing"
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

        JLabel right =
                new JLabel(
                        "Developed by TEAM 1"
                );

        right.setForeground(
                MUTED
        );

        right.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

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
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    Dashboard dashboard =
                            new Dashboard();

                    dashboard.setVisible(
                            true
                    );
                }
        );
    }
}