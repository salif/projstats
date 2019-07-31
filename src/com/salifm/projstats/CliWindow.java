package com.salifm.projstats;

import java.io.File;
import java.util.List;

class CliWindow implements Window {
    CliWindow(String dir, Walker walker) {
        File file = new File(dir);
        add("name: ", file.getName());
        add("size: ", Unit.get(walker.getSize()));
        add("files: ", walker.getFiles());
        add("folders: ", walker.getFolders());
        add("lines: ", walker.getLines());
        add("empty lines: ", walker.getEmptyLines());
        add("extensions: ", walker.getExtensions());
    }

    public void add(String name, String text) {
        System.out.println(name + text);
    }

    public void add(String name, int text) {
        add(name, String.valueOf(text));
    }

    public void add(String name, long text) {
        add(name, String.valueOf(text));
    }

    public void add(String name, List<String[]> list) {
        add(name, "");
        for (String[] arr : list) {
            add("> ", String.format("%s (%s)",arr[0], arr[1]));
        }
    }
}
