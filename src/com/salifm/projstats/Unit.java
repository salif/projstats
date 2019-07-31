package com.salifm.projstats;

import java.text.DecimalFormat;

class Unit {
    static String get(Long bytes) {
        String result = "";

        double k = bytes / 1024.0;
        double m = bytes / 1048576.0;
        double g = bytes / 1073741824.0;
        double t = bytes / 1099511627776.0;

        DecimalFormat dec = new DecimalFormat("0.00");

        if ( t>1 ) {
            result = dec.format(t).concat(" TiB");
        } else if ( g>1 ) {
            result = dec.format(g).concat(" GiB");
        } else if ( m>1 ) {
            result = dec.format(m).concat(" MiB");
        } else if ( k>1 ) {
            result = dec.format(k).concat(" KiB");
        } else {
            result = dec.format(bytes).concat(" B");
        }
        return result;
    }
}
