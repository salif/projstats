package com.salifm.projstats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Walker {
    private long size;
    private int files;
    private int folders;
    private long lines;
    private long emptyLines;
    private static List<String> skipDirs = new ArrayList<>(List.of(".git", "node_modules"));
    private static List<String> skipFiles = new ArrayList<>();
    private boolean wait;
    private boolean list;
    private boolean list_skipped;
    private Map<String, Integer> extensions;

    Walker(String dir, boolean wait, boolean list, boolean list_skipped) {
        this.wait = wait;
        this.list = list;
        this.list_skipped = list_skipped;
        this.extensions = new HashMap<>();
        System.out.print("scanning...");
        walk(dir);
        System.out.println();
    }

    private void walk(String dir) {

        File[] files = new File(dir).listFiles();

        for (final File f : files) {
            if (f.isDirectory()) {
                if (!skipDirs.contains(f.getName())) {
                    this.folders++;
                    walk(f.getAbsolutePath());
                } else if (list_skipped) {
                    System.out.printf("%nskipped: %s", f.getAbsolutePath());
                }
            }
            if (f.isFile()) {
                try {
                    walkIntoFile(f.getAbsolutePath(), f.length());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void walkIntoFile(String name, long size) throws IOException {
        String[] commands = {"file", "-bi", name};
        String output = "";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(commands).getInputStream()))) {
            output = bufferedReader.readLine();
        }
        if (output.contains("binary") || skipFiles.contains(name)) {
            if (this.list_skipped) {
                System.out.printf("%nskipped: %s", name);
            }
            return;
        }
        this.files++;
        this.size += size;
        if (this.list) {
            System.out.printf("%n%s", name);
        } else if (this.list_skipped) {
            // don't show progressbar if 'list_skipped' is true
        } else if (this.wait) {
            System.out.print(".");
        }
        registerExtension(name);
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.isBlank()) this.emptyLines++;
                this.lines++;
            }
        }
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
            System.out.println(dir.concat(" is skipped"));
        } else {
            System.out.println(dir.concat(" is not skipped"));
        }
    }

    static void checkFile(String file) {
        if (skipFiles.contains(file)) {
            System.out.println(file.concat(" is skipped"));
            return;
        }
        String[] commands = {"file", "-bi", file};
        String output = "";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(commands).getInputStream()))) {
            output = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (output.contains("binary")) {
            System.out.println(file.concat(" is skipped"));
        } else {
            System.out.println(file.concat(" is not skipped"));
        }
    }

    static void addSkipDir(String dir) {
        skipDirs.add(dir);
    }

    static void addSkipFile(String file) {
        skipFiles.add(file);
    }
}
