package parfenix.code.app;

import javax.swing.*;
import java.awt.*;

public class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setOpaque(true);
        setFocusable(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.white);
        setFont(new Font("Arial", Font.PLAIN, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillOval(0,0, getWidth(), getHeight());

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = Math.min(getWidth(), getHeight());
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius / 2, 2);
    }
}
