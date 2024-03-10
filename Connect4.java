public class Connect4 {

    public static void main(String[] args) {
        // int state = 0;
        boolean useGui = true;// sets if we want the cli or gui.

        if (useGui) {
            playGuiGame();
        } else {
            playCliGame();
        }
    }

    private static void playGuiGame() {
        int state = 0;
        Gui Gui = new Gui();

        while (state != -1) {// checks if program is in quitting stage
            switch (state) {
                case 0:// runtime
                    state = handleGuiRuntime(Gui);
                    break;
                case 1:// endgame with winner
                    Gui.showWon();
                    if (Gui.getQuit()) {
                        state = -1;
                    } else if (Gui.getNewGame()) {
                        Gui = new Gui();
                        state = 0;
                    }
                    break;
                case 2:// endgame with drawgame
                    Gui.showDraw();
                    if (Gui.getQuit()) {
                        state = -1;
                    } else if (Gui.getNewGame()) {
                        Gui = new Gui();
                        state = 0;
                    }
                    break;
            }
        }
    }

    private static void playCliGame() {
        int state = 0;
        Cli Cli = new Cli();
        while (state != -1) {// checks if program is in quitting stage
            switch (state) {
                case 0:
                    state = handleCliRuntime(Cli);
                    break;
                case 1:// prints endgame with winner
                    state = handleCliEndGame(Cli);

                    break;
                case 2:// prints end game eith draw game
                    Cli.showDraw();
                    if (Cli.getQuit()) {
                        state = -1;
                    } else if (Cli.getNewGame()) {
                        Cli = new Cli();
                        state = 0;
                    }
                    break;
            }
        }
    }

    private static int handleCliEndGame(Cli cli) {
        cli.showWin();
        // showEndGame.run();
        if (cli.getQuit()) {
            return -1;
        } else if (cli.getNewGame()) {
            cli = new Cli();
            return 0;
        }
        return 0;
    }

    

    private static int handleGuiRuntime(Gui gui) {
        gui.updateBoard();
        if (gui.getHasWon()) {
            return 1;
        } else if (gui.getHasDraw()) {
            return 2;
        } else if (gui.getNewGame()) {
            gui = new Gui();
            return 0;
        }
        return 0;
    }

    private static int handleCliRuntime(Cli cli) {
        cli.runtime();
        if (cli.getHasWon()) {
            return 1;
        } else if (cli.getHasDraw()) {
            return 2;
        }
        return 0;
    }

}