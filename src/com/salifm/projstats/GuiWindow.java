package com.salifm.projstats;

import javax.swing.*;

class GuiWindow implements Window {
    private JFrame frame;
    private int labelx = 20;
    private int labely = 0;

    GuiWindow(String dir, Walker walker) {
        this.frame = new JFrame("projstats - " + dir);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(400,400);
        add("path", dir);
        add("files ", walker.getFiles());
        add("folders", walker.getFolders());
        add("lines", walker.getLines());
        add("empty lines", walker.getEmptyLines());
        this.frame.setLayout(null);
        this.frame.setVisible(true);
    }

    public void add(String name, String text) {
        JLabel label = new JLabel(name + ": " + text);
        label.setBounds(this.labelx, nextLabel(), 350, 30);
        this.frame.add(label);
    }

    public void add(String name, int text) {
        add(name, String.valueOf(text));
    }

    public void add(String name, long text) {
        add(name, String.valueOf(text));
    }

    private int nextLabel() {
        this.labely += 30;
        return this.labely;
    }
}