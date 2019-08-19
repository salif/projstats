package com.salifm.projstats;

import java.io.*;
import java.util.*;

class Walker {
    private long size;
    private int files;
    private int folders;
    private long lines;
    private long emptyLines;
    private String dir;
    private static List<String> skipDirs = new ArrayList<>(List.of(".git", "node_modules"));
    private static List<String> skipFiles = new ArrayList<>();
    private boolean wait;
    private boolean list;
    private boolean list_skipped;
    private Map<String, Integer> extensions;

    Walker(String dir) {
        this.dir = dir;
        this.wait = Main.wait;
        this.list = Main.list;
        this.list_skipped = Main.list_skipped;
        this.extensions = new HashMap<>();
        Printer.print("scanning...");
        walk(dir);
        Printer.println();
    }

    private void walk(String dir) {
        // Local variable files hides a class field. Therefore altered the variable name
        File[] fileList = new File(dir).listFiles();

        for (final File f : fileList) {
            if (f.isDirectory()) {
                if (!skipDirs.contains(f.getName())) {
                    this.folders++;
                    walk(f.getAbsolutePath());
                } else if (list_skipped) {
                    Printer.printf("%nskipped: %s", f.getAbsolutePath());
                }
            }
            if (f.isFile()) {
                try {
                    walkInFile(f.getAbsolutePath(), f.length());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void walkInFile(String name, long size) throws IOException {
        long fileLines = 0;
        long fileEmptyLines = 0;
        boolean isBinary = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.isBlank()) fileEmptyLines++;
                fileLines++;
                if (charsBinary(line)) {
                    isBinary = true;
                    break;
                }
            }
        }

        if (isBinary || skipFiles.contains(name)) {
            if (this.list_skipped) {
                Printer.printf("%nskipped: %s", name);
            }
            return;
        }

        this.lines += fileLines;
        this.emptyLines += fileEmptyLines;
        this.files++;
        this.size += size;

        if (this.list) {
            Printer.printf("%n%s", name);
        } else if (this.list_skipped) {
            // don't show progressbar if 'list_skipped' is true
        } else if (this.wait) {
            Printer.print(".");
        }
        registerExtension(name);
    }

    private static boolean charsBinary(String line) {
        for (char c : line.toCharArray()) {
            if (c < 0x09) {
                return true;
            }
        }
        return false;
    }

    private void registerExtension(String name) {
        int ind = name.lastIndexOf('.');
        int p = name.lastIndexOf('/');
        String extension = "";
        if (ind > p) {
            extension = name.substring(ind + 1);
        } else {
            extension = "[none]";
        }
        if (this.extensions.containsKey(extension)) {
            this.extensions.put(extension, this.extensions.get(extension) + 1);
        } else {
            this.extensions.put(extension, 1);
        }
    }

    long getSize() {
        return this.size;
    }

    int getFiles() {
        return this.files;
    }

    int getFolders() {
        return this.folders;
    }

    long getLines() {
        return this.lines;
    }

    long getEmptyLines() {
        return this.emptyLines;
    }

    String getDirectory() {
        return this.dir;
    }

    List<String[]> getExtensions() {
        StringBuilder output = new StringBuilder();
        List<String[]> a = new ArrayList<>();
        this.extensions.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(e -> {
            output.append(e.getKey()).append(" -> ").append(e.getValue()).append("\n");
            a.add(new String[]{e.getKey(), String.valueOf(e.getValue())});
        });
        return a;
    }

    static void checkDir(String dir) {
        if (skipDirs.contains(dir)) {
            Printer.println(dir.concat(" is skipped"));
        } else {
            Printer.println(dir.concat(" is not skipped"));
        }
    }

    static void checkFile(String file) {
        if (skipFiles.contains(file)) {
            Printer.println(file.concat(" is skipped"));
            return;
        }

        boolean isBinary = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (charsBinary(line)) {
                    isBinary = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isBinary) {
            Printer.println(file.concat(" is skipped"));
        } else {
            Printer.println(file.concat(" is not skipped"));
        }
    }

    static void addSkipDir(String dir) {
        skipDirs.add(dir);
    }

    static void addSkipFile(String file) {
        skipFiles.add(file);
    }
}
