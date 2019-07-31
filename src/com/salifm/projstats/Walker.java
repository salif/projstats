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
    private List<String> forSkip;
    private boolean wait;
    private boolean list;
    private Map<String, Integer> extensions;

    Walker(String dir, boolean wait, boolean list) {
        this.wait = wait;
        this.list = list;
        this.forSkip = new ArrayList<>(List.of(".git", "node_modules"));
        this.extensions = new HashMap<>();
        System.out.print("scanning...");
        walk(dir);
        System.out.println();
    }

    private void walk(String dir) {

        File[] files = new File(dir).listFiles();

        assert files != null;
        for (final File f : files) {
            if (f.isDirectory()) {
                if (skip(f)) {
                    this.folders++;
                    walk(f.getAbsolutePath());
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

    private boolean skip(File f) {
        return !this.forSkip.contains(f.getName());
    }

    private void walkIntoFile(String name, long size) throws IOException {
        String[] commands = {"file", "-bi", name};
        String output;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(commands).getInputStream()))) {
            output = bufferedReader.readLine();
        }
        if (output.contains("binary")) {
            return;
        }
        this.files++;
        this.size += size;
        if (this.list) {
            System.out.printf("%n%s", name);
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
}
