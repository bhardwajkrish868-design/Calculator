import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class About extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color NAVY =
            new Color(25, 50, 85);

    private static final Color BLUE =
            new Color(45, 105, 190);

    private static final Color CYAN =
            new Color(20, 180, 210);

    private static final Color PURPLE =
            new Color(145, 65, 215);

    private static final Color GREEN =
            new Color(20, 180, 145);

    private static final Color ORANGE =
            new Color(245, 155, 15);

    private static final Color RED =
            new Color(225, 70, 80);

    private static final Color BACKGROUND =
            new Color(246, 248, 252);

    private static final Color TEXT =
            new Color(25, 45, 75);

    private static final Color MUTED =
            new Color(90, 115, 150);

    private static final Color BORDER =
            new Color(215, 222, 232);

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public About() {

        setTitle("About Calculator Suite");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );

        buildUI();

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);
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
                createContent(),
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
                NAVY
        );

        header.setPreferredSize(
                new Dimension(
                        0,
                        125
                )
        );

        header.setBorder(
                new EmptyBorder(
                        22,
                        38,
                        20,
                        38
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
                        "CALCULATOR SUITE"
                );

        title.setForeground(
                Color.WHITE
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
                        "About this application"
                );

        subtitle.setForeground(
                new Color(
                        210,
                        225,
                        245
                )
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        left.add(title);

        left.add(
                Box.createVerticalStrut(5)
        );

        left.add(subtitle);

        // No X button here

        header.add(
                left,
                BorderLayout.WEST
        );

        return header;
    }

    // =========================================================
    // CONTENT
    // =========================================================

    private JPanel createContent() {

        JPanel content =
                new JPanel();

        content.setBackground(
                BACKGROUND
        );

        content.setBorder(
                new EmptyBorder(
                        22,
                        45,
                        18,
                        45
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // DEVELOPER
        // =====================================================

        JLabel developer =
                new JLabel(
                        "TEAM 1"
                );

        developer.setForeground(
                TEXT
        );

        developer.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        developer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel role =
                new JLabel(
                        "Developer"
                );

        role.setForeground(
                BLUE
        );

        role.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        role.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(
                developer
        );

        content.add(
                Box.createVerticalStrut(2)
        );

        content.add(
                role
        );

        content.add(
                Box.createVerticalStrut(18)
        );

        // =====================================================
        // ABOUT APPLICATION
        // =====================================================

        JPanel aboutCard =
                createSection(
                        "About the Application"
                );

        JTextArea aboutText =
                new JTextArea(
                        "Calculator Suite is a Java Swing desktop "
                                + "application developed by Krish Bhardwaj. "
                                + "It combines multiple useful calculation "
                                + "tools into one simple and easy-to-use "
                                + "application."
                );

        aboutText.setEditable(false);

        aboutText.setFocusable(false);

        aboutText.setOpaque(false);

        aboutText.setLineWrap(true);

        aboutText.setWrapStyleWord(true);

        aboutText.setForeground(
                MUTED
        );

        aboutText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        aboutCard.add(
                aboutText,
                BorderLayout.CENTER
        );

        content.add(
                aboutCard
        );

        content.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // MODULE TITLE
        // =====================================================

        JLabel modulesTitle =
                new JLabel(
                        "Calculator Modules"
                );

        modulesTitle.setForeground(
                TEXT
        );

        modulesTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        modulesTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(
                modulesTitle
        );

        content.add(
                Box.createVerticalStrut(9)
        );

        // =====================================================
        // MODULE CARDS
        // =====================================================

        JPanel modules =
                new JPanel(
                        new GridLayout(
                                1,
                                5,
                                14,
                                0
                        )
                );

        modules.setOpaque(false);

        modules.add(
                createModuleCard(
                        "Basic Calculator",
                        "Basic arithmetic calculations",
                        CYAN
                )
        );

        modules.add(
                createModuleCard(
                        "Scientific Calculator",
                        "Advanced mathematical functions",
                        PURPLE
                )
        );

        modules.add(
                createModuleCard(
                        "CGPA Calculator",
                        "Grades, credits and CGPA",
                        GREEN
                )
        );

        modules.add(
                createModuleCard(
                        "Percentage Calculator",
                        "Marks and percentage",
                        BLUE
                )
        );

        modules.add(
                createModuleCard(
                        "Date & Time Calculator",
                        "Age and date operations",
                        ORANGE
                )
        );

        content.add(
                modules
        );

        content.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // APPLICATION FEATURES
        // =====================================================

        JPanel features =
                createSection(
                        "Application Features"
                );

        JPanel featureGrid =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                20,
                                8
                        )
                );

        featureGrid.setOpaque(false);

        addFeature(
                featureGrid,
                "Full Screen Support",
                CYAN
        );

        addFeature(
                featureGrid,
                "Calculation History",
                PURPLE
        );

        addFeature(
                featureGrid,
                "Keyboard Support",
                GREEN
        );

        addFeature(
                featureGrid,
                "Clean User Interface",
                BLUE
        );

        addFeature(
                featureGrid,
                "Scientific Functions",
                ORANGE
        );

        addFeature(
                featureGrid,
                "Credit Based CGPA",
                RED
        );

        addFeature(
                featureGrid,
                "Date & Time Tools",
                CYAN
        );

        addFeature(
                featureGrid,
                "Fast Calculations",
                PURPLE
        );

        features.add(
                featureGrid,
                BorderLayout.CENTER
        );

        content.add(
                features
        );

        content.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // TECHNOLOGY
        // =====================================================

        JPanel technology =
                createSection(
                        "Technology"
                );

        JLabel technologyText =
                new JLabel(
                        "Java Swing     •     Java     •     Object-Oriented Programming"
                );

        technologyText.setForeground(
                MUTED
        );

        technologyText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        technology.add(
                technologyText,
                BorderLayout.CENTER
        );

        content.add(
                technology
        );

        content.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // VERSION
        // =====================================================

        JLabel version =
                new JLabel(
                        "Calculator Suite Version 1.0"
                );

        version.setForeground(
                MUTED
        );

        version.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        version.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(
                version
        );

        // No JScrollPane

        return content;
    }

    // =========================================================
    // SECTION CARD
    // =========================================================

    private JPanel createSection(
            String title
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        panel.setBackground(
                Color.WHITE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        JLabel heading =
                new JLabel(
                        title
                );

        heading.setForeground(
                TEXT
        );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        return panel;
    }

    // =========================================================
    // MODULE CARD
    // =========================================================

    private JPanel createModuleCard(
            String title,
            String description,
            Color accent
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
                        BORDER
                )
        );

        JPanel bar =
                new JPanel();

        bar.setBackground(
                accent
        );

        bar.setPreferredSize(
                new Dimension(
                        0,
                        5
                )
        );

        card.add(
                bar,
                BorderLayout.NORTH
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel name =
                new JLabel(
                        title
                );

        name.setForeground(
                TEXT
        );

        name.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
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
                        11
                )
        );

        textPanel.add(
                name
        );

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(
                descriptionLabel
        );

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // FEATURE
    // =========================================================

    private void addFeature(
            JPanel grid,
            String text,
            Color color
    ) {

        JPanel item =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        item.setOpaque(false);

        JLabel bullet =
                new JLabel(
                        "●"
                );

        bullet.setForeground(
                color
        );

        bullet.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(
                MUTED
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        item.add(
                bullet
        );

        item.add(
                Box.createHorizontalStrut(8)
        );

        item.add(
                label
        );

        grid.add(
                item
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
                        58
                )
        );

        footer.setBorder(
                BorderFactory.createMatteBorder(
                        1,
                        0,
                        0,
                        0,
                        BORDER
                )
        );

        footer.setBorder(
                new EmptyBorder(
                        9,
                        40,
                        9,
                        40
                )
        );

        JLabel developer =
                new JLabel(
                        "Developed by TEAM 1"
                );

        developer.setForeground(
                MUTED
        );

        developer.setFont(
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
                        12
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
                developer,
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

                    About about =
                            new About();

                    about.setVisible(true);
                }
        );
    }
}