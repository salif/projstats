package com.salifm.projstats;

public class Main {
    static boolean cli = true;
    static boolean gui = true;
    static boolean wait = true;
    static boolean list = false;
    static boolean list_skipped = false;
    
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        for (String arg : args) {
            switch (arg) {
                case "--help":
                    printHelp();
                    return;
                case "--cli":
                    gui = false;
                    break;
                case "--gui":
                    cli = false;
                    break;
                case "--wait":
                    wait = false;
                    break;
                case "--list":
                    list = true;
                    break;
                case "--list-skipped":
                    list_skipped = true;
                    break;
                default:
                    if (arg.startsWith("--check-dir=")) {
                        Walker.checkDir(arg.substring(12));
                        return;
                    } else if (arg.startsWith("--check-file=")) {
                        Walker.checkFile(arg.substring(13));
                        return;
                    } else if (arg.startsWith("--skip-dir=")) {
                        Walker.addSkipDir(arg.substring(11));
                    } else if (arg.startsWith("--skip-file=")) {
                        Walker.addSkipFile(arg.substring(12));
                    }            
            }
        }

        Walker walker = new Walker(path, wait, list, list_skipped);
        if (cli) {
            Window window = new CliWindow(path);
            window.show(walker);
        }
        if (gui) {
            Window window = new GuiWindow(path);
            window.show(walker);
        }
    }

    private static void printHelp() {
        Printer.printf("%n  Usage: %n%n" +
                "      projstats [options] [directory]%n%n" +
                "  options:%n%n" +
                "      --cli                    Run without GUI%n" +
                "      --gui                    Run without CLI%n" +
                "      --wait                   Run without progressbar%n" +
                "      --list                   List files%n" +
                "      --list-skipped           List skipped files and dirs%n" +
                "      --check-dir=dir          Check if directory is skipped%n" +
                "      --check-file=file        Check if file is skipped%n" +
                "      --skip-dir=dir           Skip directory%n" +
                "      --skip-file=file         Skip file%n" +
                "      --help                   Print help%n%n");
    }
}
