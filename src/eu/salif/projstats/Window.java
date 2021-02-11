package eu.salif.projstats;

import java.io.File;
import java.util.List;

abstract class Window {
    private String dir;

    Window(String dir) {
        this.dir = dir;
    }

    void show(Walker walker) {
        this.dir = walker.getDirectory();
        add("name: ", new File(walker.getDirectory()).getName());
        add("size: ", Unit.get(walker.getSize()));
        add("files: ", walker.getFiles());
        add("folders: ", walker.getFolders());
        add("lines: ", walker.getLines());
        add("empty lines: ", walker.getEmptyLines());
        add("skipped files: ", walker.getSkippedFiles());
        if (!Main.no_ext) {
            add("extensions: ", walker.getExtensions());
        }
    }

    abstract void add(String name, String text);

    void add(String name, int text) {
        add(name, String.valueOf(text));
    }

    void add(String name, long text) {
        add(name, String.valueOf(text));
    }

    void add(String name, List<String[]> list) {
        add(name, "");
        list.forEach(arr -> {
            add("  ", String.format("%s (%s)", arr[0], arr[1]));
        });
    }
}
