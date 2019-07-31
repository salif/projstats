package com.salifm.projstats;

public class Main {
    public static void main(String[] args) {
        Window window;
        String path = System.getProperty("user.dir");

        boolean cli = true;
        boolean gui = true;
        boolean wait = true;
        boolean list = false;

        for (int i = 0; i < args.length; i++) {
            switch(args[i]) {
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
            }
        }

        Walker walker = new Walker(path, wait, list);
        if(cli) {
            window = new CliWindow(path, walker);
        }
        if(gui) {
            window = new GuiWindow(path, walker);
        }
    }

    private static void printHelp() {
        System.out.printf("%n  Usage: %n%n" +
                "      projstats [options]%n%n" +
                "  options:%n%n" +
                "      --cli%n" +
                "      --gui%n" +
                "      --wait%n" +
                "      --list%n" +
                "      --help%n%n");
    }
}
