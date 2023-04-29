package g56531.qwirkle;

import g56531.qwirkle.model.Direction;
import g56531.qwirkle.model.Game;
import g56531.qwirkle.model.GridView;
import g56531.qwirkle.model.QwirkleException;
import g56531.qwirkle.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static List askNamesPlayer(int nbPlayer) {
        ArrayList<String> playerNames = new ArrayList<>();
        for (int i = 0; i < nbPlayer; i++) {
            Scanner clavier = new Scanner(System.in);
            System.out.print("Nom du joueur " + (i + 1) + ":");
            String player = clavier.next();
            playerNames.add(player);
            System.out.println();
        }
        return playerNames;
    }

    private static int askNbPlayers() {
        Scanner clavier = new Scanner(System.in);
        System.out.println("Combien de joueur êtes vous ? (entre 2-4)");
        int nbPlayers = clavier.nextInt();
        while (nbPlayers < 2 || nbPlayers > 4) {
            System.out.println("Nombre de joueur incorrect veuillez " +
                    "entrer un nombre entre 2 et 4");
            nbPlayers = clavier.nextInt();
        }
        return nbPlayers;
    }

    private static String[] askPLay() {
        boolean invalidCommand = true;
        Scanner clavier = new Scanner(System.in);
        System.out.print("Veuillez entreune commande pour jouer " +
                "(entrez h pour plus d'infos sur les differente commande) : ");
        String commandePlay = clavier.nextLine();
        // split transforme la phrase en un tableau
        String[] commandSplit = commandePlay.split(" ");
        while (invalidCommand)
            if (commandSplit[0].equals("o")
                    && commandSplit.length == 4) {
                invalidCommand = false;

            } else if (commandSplit[0].equals("l")
                    && commandSplit.length > 4) {
                invalidCommand = false;

            } else if (commandSplit[0].equals("f")
                    && commandSplit.length > 1) {
                invalidCommand = false;

            } else if (commandSplit[0].equals("m")
                    && commandSplit.length > 2) {
                invalidCommand = false;
            } else if (commandSplit[0].equals("q")) {
                invalidCommand = false;
            } else if (commandSplit[0].equals("p")) {
                invalidCommand = false;
            } else if (commandSplit[0].equals("h")) {
                View.displayHelp();
                View.displayError("Veuillez entreune commande pour jouer " +
                        "(entrez h pour plus d'infos sur les differente commande) : ");
                commandePlay = clavier.nextLine();
                commandSplit = commandePlay.split(" ");
            } else {
                View.displayError("Veuillez entreune commande pour jouer " +
                        "(entrez h pour plus d'infos sur les differente commande) : ");
                commandePlay = clavier.nextLine();
                commandSplit = commandePlay.split(" ");

            }
        return commandSplit;
    }

    private static int[] tabIntoList(String[] mouv, Game game, int n) {
        String typeMouv = mouv[0];
        List<Integer> list = new ArrayList();
        int[] numberList = new int[mouv.length - n];
        for (int i = 0; n < mouv.length; i++) {
            try {
                int x = Integer.parseInt(mouv[n]);
                numberList[i] = x;
                n++;
            } catch (NumberFormatException e) {
            }
        }
        return numberList;
    }

    private static void playMouv(String[] mouv, Game game) {
        if (mouv[0].equals("f")) {
            do {
                try {
                    game.first(Direction.LEFT, tabIntoList(mouv, game, 1));
                    break;
                } catch (QwirkleException e) {
                    View.displayError(e.getMessage());
                    mouv = askPLay();
                    continue;
                }
            } while (true);
        } else if (mouv[0].equals("m")) {
            game.play(tabIntoList(mouv, game, 1));
        } else if (mouv[0].equals("o")) {
            int[] playInt = tabIntoList(mouv, game, 1);
            do {
                try {
                    game.play(playInt[0], playInt[1], playInt[2]);
                    break;
                } catch (QwirkleException e) {
                    View.displayError(e.getMessage());
                    mouv = askPLay();
                    continue;
                }
            } while (true);
        } else if (mouv[0].equals("l")) {
            int[] playInt = tabIntoList(mouv, game, 4);
            int row = Integer.parseInt(mouv[1]);
            int col = Integer.parseInt(mouv[2]);
            Direction d = null;
            switch (mouv[3]) {
                case "u":
                    d = Direction.UP;
                    break;
                case "l":
                    d = Direction.LEFT;
                    break;
                case "r":
                    d = Direction.RIGHT;
                    break;
                case "d":
                    d = Direction.DOWN;
                    break;
            }
            System.out.println(d);
            game.play(row, col, d, playInt);
        }
    }

    public static void main(String[] args) {

        int nbPlayers = askNbPlayers();
        List namePlayers = askNamesPlayer(nbPlayers);

        Game game = new Game(namePlayers);
        GridView gridView = game.getGrid();
        while (true) {
            System.out.println();
            View.display(game.getPlayers());
            String[] mouv = askPLay();
            playMouv(mouv, game);

            View.display(gridView);
            game.pass();
        }

    }
}
