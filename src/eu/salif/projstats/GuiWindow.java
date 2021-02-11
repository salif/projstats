package eu.salif.projstats;

import javax.swing.*;
import java.util.List;

class GuiWindow extends Window {
    private JFrame frame;
    private JFileChooser fileChooser;
    private JButton chooseBtn;
    private int labely = 30;

    GuiWindow(String dir) {
        super(dir);
        frame = new JFrame("projstats - " + dir);
        fileChooser = new JFileChooser(dir);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooseBtn = new JButton(dir);
        chooseBtn.setToolTipText("Choose Directory");
        chooseBtn.setBounds(20, 5, 350, 26);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(null);
        frame.add(chooseBtn);

        // action listener for chooseBtn
        chooseBtn.addActionListener(al -> {
            fileChooser.showOpenDialog(frame);
        });

        // action listener for fileChooser
        fileChooser.addActionListener(al -> {
            var selected = fileChooser.getSelectedFile();
            chooseBtn.setText(selected.toString());
            for (var component : frame.getContentPane().getComponents()) {
                if (component instanceof JLabel) {
                    frame.remove(component);
                }
            }
            frame.revalidate();
            frame.repaint();
            frame.setTitle("projstats - " + selected.toString());
            labely = 30;
            show(new Walker(selected.toString()));
        });
    }

    @Override
    void show(Walker walker) {
        super.show(walker);
        frame.setVisible(true);
    }

    @Override
    void add(String name, String text) {
        JLabel label = new JLabel(name + text);
        label.setBounds(20, nextLabel(), 350, 26);
        frame.add(label);
    }

    @Override
    void add(String name, List<String[]> list) {
        add(name, "");
        list.stream().limit(10).forEach(arr -> {
            add("  ", String.format("%s (%s)", arr[0], arr[1]));
        });
        if (list.size() > 10) {
            int other = list.stream().skip(10).mapToInt(i -> Integer.parseInt(i[1])).sum();
            add("  [other]: ", other);
        }
    }

    private int nextLabel() {
        this.labely += 26;
        return this.labely;
    }
}