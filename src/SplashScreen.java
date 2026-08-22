import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private int progress = 0;

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BG =
            new Color(246, 248, 252);

    private static final Color PURPLE =
            new Color(156, 55, 220);

    private static final Color CYAN =
            new Color(0, 184, 220);

    private static final Color TEXT =
            new Color(27, 39, 58);

    private static final Color MUTED =
            new Color(99, 116, 145);

    private JProgressBar progressBar;
    private JLabel statusLabel;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public SplashScreen() {

        createUI();

        setSize(650, 390);

        setLocationRelativeTo(null);
    }

    // =========================================================
    // UI
    // =========================================================

    private void createUI() {

        JPanel root =
                new JPanel(new BorderLayout());

        root.setBackground(BG);

        root.setBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 221, 235),
                        1
                )
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel center = new JPanel();

        center.setOpaque(false);

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        // APP NAME
        JLabel title =
                new JLabel("CALCULATOR SUITE");

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        36
                )
        );

        title.setForeground(TEXT);

        // DEVELOPER
        JLabel developer =
                new JLabel(
                        "Developed by TEAM 1"
                );

        developer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        developer.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        developer.setForeground(PURPLE);

        // TAGLINE
        JLabel tagline =
                new JLabel(
                        "Smart Tools. Faster Results."
                );

        tagline.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        tagline.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        tagline.setForeground(MUTED);

        center.add(
                Box.createVerticalGlue()
        );

        center.add(title);

        center.add(
                Box.createVerticalStrut(8)
        );

        center.add(developer);

        center.add(
                Box.createVerticalStrut(6)
        );

        center.add(tagline);

        center.add(
                Box.createVerticalStrut(45)
        );

        // =====================================================
        // PROGRESS BAR
        // =====================================================

        progressBar =
                new JProgressBar(0, 100);

        progressBar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        progressBar.setPreferredSize(
                new Dimension(480, 8)
        );

        progressBar.setMaximumSize(
                new Dimension(480, 8)
        );

        progressBar.setStringPainted(false);

        progressBar.setBorderPainted(false);

        progressBar.setBackground(
                new Color(225, 229, 238)
        );

        progressBar.setForeground(PURPLE);

        center.add(progressBar);

        center.add(
                Box.createVerticalStrut(12)
        );

        // STATUS
        statusLabel =
                new JLabel(
                        "Starting Calculator Suite..."
                );

        statusLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        statusLabel.setForeground(MUTED);

        center.add(statusLabel);

        center.add(
                Box.createVerticalGlue()
        );

        root.add(
                center,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setOpaque(false);

        footer.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        30,
                        20,
                        30
                )
        );

        JLabel version =
                new JLabel(
                        "Calculator Suite  •  Version 1.0"
                );

        version.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        version.setForeground(MUTED);

        footer.add(
                version,
                BorderLayout.CENTER
        );

        root.add(
                footer,
                BorderLayout.SOUTH
        );

        setContentPane(root);
    }

    // =========================================================
    // START SPLASH
    // =========================================================

    public void start() {

        setVisible(true);

        Timer timer =
                new Timer(35, null);

        timer.addActionListener(e -> {

            progress += 2;

            progressBar.setValue(progress);

            updateStatus();

            if (progress >= 100) {

                timer.stop();

                dispose();

                openDashboard();
            }
        });

        timer.start();
    }

    // =========================================================
    // STATUS
    // =========================================================

    private void updateStatus() {

        if (progress < 20) {

            statusLabel.setText(
                    "Starting Calculator Suite..."
            );

        } else if (progress < 40) {

            statusLabel.setText(
                    "Loading calculator modules..."
            );

        } else if (progress < 60) {

            statusLabel.setText(
                    "Preparing basic calculator..."
            );

        } else if (progress < 80) {

            statusLabel.setText(
                    "Preparing scientific calculator..."
            );

        } else if (progress < 95) {

            statusLabel.setText(
                    "Preparing CGPA calculator..."
            );

        } else {

            statusLabel.setText(
                    "Opening dashboard..."
            );
        }
    }

    // =========================================================
    // OPEN DASHBOARD
    // =========================================================

    private void openDashboard() {

        SwingUtilities.invokeLater(() -> {

            try {

                Dashboard dashboard =
                        new Dashboard();

                dashboard.setVisible(true);

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Unable to open Dashboard.\n\n"
                                + e.getMessage(),
                        "Calculator Suite Error",
                        JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();
            }
        });
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            SplashScreen splash =
                    new SplashScreen();

            splash.start();
        });
    }
}