package com.salifm.projstats;

import java.io.File;

class CliWindow implements Window {
    CliWindow(String dir, Walker walker) {
        File file = new File(dir);
        add("name", file.getName());
        add("size", Unit.get(walker.getSize()));
        add("files", walker.getFiles());
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
