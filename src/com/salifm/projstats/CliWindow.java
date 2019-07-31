package com.salifm.projstats;

class CliWindow extends Window {
    CliWindow(String dir) {
        super(dir);
    }

    @Override
    public void add(String name, String text) {
        System.out.println(name + text);
    }
}
