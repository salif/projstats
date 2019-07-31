package com.salifm.projstats;

import javax.swing.*;

class CliWindow implements Window {
    CliWindow(String dir, Walker walker) {
        add("path", dir);
        add("files ", walker.getFiles());
        add("folders", walker.getFolders());
        add("lines", walker.getLines());
        add("empty lines", walker.getEmptyLines());
    }

    public void add(String name, String text) {
        System.out.println(name + ": " + text);
    }

    public void add(String name, int text) {
        add(name, String.valueOf(text));
    }

    public void add(String name, long text) {
        add(name, String.valueOf(text));
    }
}
