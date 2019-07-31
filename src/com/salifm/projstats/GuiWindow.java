package com.salifm.projstats;

import javax.swing.*;
import java.util.List;

class GuiWindow extends Window {
    private JFrame frame;
    private int labely = 0;

    GuiWindow(String dir) {
        super(dir);
        this.frame = new JFrame("projstats - " + dir);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(400,600);
        this.frame.setLayout(null);
    }

    @Override
    void show(Walker walker) {
        super.show(walker);
        this.frame.setVisible(true);
    }

    @Override
    void add(String name, String text) {
        JLabel label = new JLabel(name + text);
        label.setBounds(20, nextLabel(), 350, 26);
        this.frame.add(label);
    }

    @Override
    void add(String name, List<String[]> list) {
        add(name, "");
        list.stream().limit(10).forEach(arr -> {
            add("  ", String.format("%s (%s)",arr[0], arr[1]));
        });
        if (list.size() > 10) {
            int other = list.stream().skip(10).mapToInt(i -> {
                return Integer.parseInt(i[1]);
            }).sum();
            add("  [other]: ", other);
        }
    }

    private int nextLabel() {
        this.labely += 26;
        return this.labely;
    }
}