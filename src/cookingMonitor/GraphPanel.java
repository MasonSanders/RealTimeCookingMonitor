package cookingMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel implements TemperatureListener {

    private List<TemperatureReading> readings;

    public GraphPanel() {
        this.readings = new ArrayList<>();
        setPreferredSize(new Dimension(300, 400));
    }

    @Override
    public void onTemperatureUpdate(TemperatureReading reading) {
        readings.add(reading);
        repaint();
    }

    public void updateGraph() {
        repaint();
    }

    public void clearGraph() {
        readings.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (readings.isEmpty()) return;

        int x = 10;
        int yBase = 350;

        g.drawString("Temperature Graph", 10, 20);

        for (TemperatureReading r : readings) {
            int y = yBase - (int) r.getValue();
            g.fillOval(x, y, 5, 5);
            x += 10;
        }
    }
}
