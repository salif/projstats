package com.salifm.projstats;

import java.text.DecimalFormat;

class Unit {
    static String get(Long bytes) {
        String resultI = calc(bytes, 1024.0, 1048576.0, 1073741824.0, 1099511627776.0, "i");
        String result = calc(bytes, 1000.0, 1000000.0, 1000000000.0, 1000000000000.0, "");
        return resultI + " / " + result;
    }

    static String calc(long bytes, double unK, double unM, double unG, double unT, String iOrNot) {

        double kb = bytes / unK;
        double mb = bytes / unM;
        double gb = bytes / unG;
        double tb = bytes / unT;

        DecimalFormat dec = new DecimalFormat("0.00");
        if (tb > 1) {
            return dec.format(tb).concat(" T").concat(iOrNot).concat("B");
        } else if (gb > 1) {
            return dec.format(gb).concat(" G").concat(iOrNot).concat("B");
        } else if (mb > 1) {
            return dec.format(mb).concat(" M").concat(iOrNot).concat("B");
        } else if (kb > 1) {
            return dec.format(kb).concat(" K").concat(iOrNot).concat("B");
        } else {
            return dec.format(bytes).concat(" B");
        }
    }
}
