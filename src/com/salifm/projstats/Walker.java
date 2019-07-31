package com.salifm.projstats;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Walker {
    private int files;
    private int folders;
    private long lines;
    private long emptyLines;
    private List<String> forSkip;

    Walker(String dir) {
        this.forSkip = new ArrayList<String>(List.of(".git", "node_modules"));
        walk(dir);
        System.out.println();
    }

    private void walk(String dir) {

        File[] files = new File(dir).listFiles();

        assert files != null;
        for (final File f : files) {
            if (f.isDirectory()) {
                this.folders++;
                if (skip(f)) walk(f.getAbsolutePath());
            }
            if (f.isFile()) {
                this.files++;
                try {
                    walkIntoFile(f.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean skip(File f) {
        return !this.forSkip.contains(f.getName());
    }

    private void walkIntoFile(String name) throws IOException {
        String[] commmands = {"file", "-bi", name};
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(commmands).getInputStream()));
        String output = bufferedReader.readLine();
        bufferedReader.close();
        if (output.contains("binary")) {
            return;
        }
        System.out.print(".");
        BufferedReader reader = new BufferedReader(new FileReader(name));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            if (line.isBlank()) this.emptyLines++;
            this.lines++;
        }
        reader.close();
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
}
