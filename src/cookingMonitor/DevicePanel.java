package cookingMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DevicePanel extends JPanel {

    private AppController appController;
    private JList<String> deviceList;
    private DefaultListModel<String> deviceListModel;

    public DevicePanel(AppController controller) {
        this.appController = controller;

        setLayout(new BorderLayout());
        add(new JLabel("Devices"), BorderLayout.NORTH);

        deviceListModel = new DefaultListModel<>();
        deviceList = new JList<>(deviceListModel);
        add(new JScrollPane(deviceList), BorderLayout.CENTER);

        JButton connectBtn = new JButton("Connect");
        add(connectBtn, BorderLayout.SOUTH);

        connectBtn.addActionListener(e -> {
            String selected = deviceList.getSelectedValue();
            if (selected != null) {
                appController.connectDevice(selected);
            }
        });
    }

    public void refreshDevices() {
        deviceListModel.clear();
        List<String> ids = appController.getThermometerManager().getDeviceIds();
        for (String id : ids) {
            deviceListModel.addElement(id);
        }
    }
}
