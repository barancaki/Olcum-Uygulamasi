import calculation.*;
import manager.CalculationManager;
import model.MeasurementData;
import processor.FileProcessor;
import processor.OutputWriter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private JTextField folderPathField;
    private JCheckBox avgBox, maxBox, minBox, stdDevBox, medianBox, freqBox, globalBox;
    private JButton browseButton, calculateButton;
    private JTextArea resultArea;

    public MainFrame() {
        setTitle("Ölçüm Analiz Uygulaması");
        setSize(600, 500);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        folderPathField = new JTextField(30);
        folderPathField.setEditable(false);
        browseButton = new JButton("Klasör Seç");
        topPanel.add(folderPathField);
        topPanel.add(browseButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel checkPanel = new JPanel(new GridLayout(4, 2));
        avgBox = new JCheckBox("Ortalama");
        maxBox = new JCheckBox("Maksimum");
        minBox = new JCheckBox("Minimum");
        stdDevBox = new JCheckBox("Standart Sapma");
        medianBox = new JCheckBox("Medyan");
        freqBox = new JCheckBox("Frekans");
        globalBox = new JCheckBox("Global Hesapla");

        checkPanel.add(avgBox);
        checkPanel.add(maxBox);
        checkPanel.add(minBox);
        checkPanel.add(stdDevBox);
        checkPanel.add(medianBox);
        checkPanel.add(freqBox);
        checkPanel.add(globalBox);

        add(checkPanel, BorderLayout.CENTER);

        calculateButton = new JButton("Hesapla");
        add(calculateButton, BorderLayout.SOUTH);

        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.EAST);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        browseButton.addActionListener(e -> chooseFolder());
        calculateButton.addActionListener(e -> performCalculations());
    }

    private void chooseFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            folderPathField.setText(selectedFolder.getAbsolutePath());
        }
    }

    private void performCalculations() {
        String folderPath = folderPathField.getText();
        if (folderPath.isEmpty()) {
            resultArea.setText("Lütfen klasör seçin.");
            return;
        }

        boolean global = globalBox.isSelected();
        CalculationManager manager = new CalculationManager();

        if (avgBox.isSelected()) manager.addCalculator(new AverageCalculator());
        if (maxBox.isSelected()) manager.addCalculator(new MaxCalculator());
        if (minBox.isSelected()) manager.addCalculator(new MinCalculator());
        if (stdDevBox.isSelected()) manager.addCalculator(new StdDevCalculator());
        if (medianBox.isSelected()) manager.addCalculator(new MedianCalculator());
        if (freqBox.isSelected()) manager.addCalculator(new FrequencyCalculator());

        List<MeasurementData> sicaklikVerileri = FileProcessor.readMeasurements(folderPath, "sicaklik");
        List<MeasurementData> nemVerileri = FileProcessor.readMeasurements(folderPath, "nem");

        StringBuilder sb = new StringBuilder("📊 HESAPLAMALAR:\n\n");

        sb.append("🔥 Sicaklik:\n");
        Map<String, List<String>> sicaklikSonuc = manager.calculateAll(sicaklikVerileri, global);
        for (Map.Entry<String, List<String>> entry : sicaklikSonuc.entrySet()) {
            sb.append("▶ ").append(entry.getKey()).append(":\n");
            entry.getValue().forEach(line -> sb.append(line).append("\n"));
            OutputWriter.writeToFile(folderPath, "sicaklik", entry.getKey(), entry.getValue());
        }

        sb.append("\n💧 Nem:\n");
        Map<String, List<String>> nemSonuc = manager.calculateAll(nemVerileri, global);
        for (Map.Entry<String, List<String>> entry : nemSonuc.entrySet()) {
            sb.append("▶ ").append(entry.getKey()).append(":\n");
            entry.getValue().forEach(line -> sb.append(line).append("\n"));
            OutputWriter.writeToFile(folderPath, "nem", entry.getKey(), entry.getValue());
        }

        sb.append("\n✅ Hesaplamalar başariyla tamamlandi.\nSonuçlar sonuc klasörüne yazildi.");
        resultArea.setText(sb.toString());
    }
}
