package eu.salif.projstats;

class Printer {

    static void print(Object obj) {
        System.out.print(obj);
    }

    static void println() {
        System.out.println();
    }

    static void println(Object obj) {
        System.out.println(obj);
    }

    static void printf(String format, Object ... args) {
        System.out.printf(format, args);
    }

}
