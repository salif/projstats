package com.salifm.projstats;

import java.util.List;

public interface Window {
    void add(String name, String text);
    void add(String name, int text);
    void add(String name, long text);
    void add(String name, List<String[]> list);
}
