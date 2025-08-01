package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder {
    public static void createTextFieldRoundedBorder(JTextField textField, int radius, Color bgColor, Color borderColor) {
        textField.setOpaque(false);
        textField.setBackground(bgColor);

        textField.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(borderColor);
                g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(6, 10, 6, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });

        if (!(textField instanceof JPasswordField)) {
            textField.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
                @Override
                protected void paintSafely(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(bgColor);
                    g2.fillRoundRect(0, 0, textField.getWidth(), textField.getHeight(), radius, radius);
                    g2.dispose();
                    super.paintSafely(g);
                }
            });
        }
    }


    public static Border createButtonRoundedBorder(JButton button, int radius, Color backgroundColor) {
        button.setBackground(backgroundColor);

        Border border = new Border() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D.setColor(backgroundColor);
                graphics2D.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius)); //Creates a round rectangle border
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(10, 20, 10, 20); //Padding around button
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        };
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(border);

        //Button Color Follows the Shape of the Button Border
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(button.getBackground());
                g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radius, radius);

                super.paint(g2d, c);
                g2d.dispose();
            }
        });

        return border;
    }

    public static JPanel createRoundedPanel(int radius, Color backgroundColor, Color borderColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(backgroundColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            }
        };
        panel.setOpaque(false);
        panel.setBackground(backgroundColor);
        return panel;
    }
}

