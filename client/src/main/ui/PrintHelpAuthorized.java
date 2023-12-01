package ui;

public class PrintHelpAuthorized {
    public void print() {
        System.out.println("create <NAME> - a game");
        System.out.println("list - games");
        System.out.println("join <ID> [WHITE|BLACK|<EMPTY>] - a game");
        System.out.println("observe <ID> - a game");
        System.out.println("logout - when you are done");
        System.out.println("quit - playing chess");
        System.out.println("help - with possible commands");
    }
}
