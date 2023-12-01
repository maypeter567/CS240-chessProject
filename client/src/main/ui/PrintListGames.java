package ui;

import result.ListGamesResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PrintListGames {
    public void print(ListGamesResult listGamesResult) {
        if (listGamesResult.getGames() != null) {
            int j = 0;
            for (var i : listGamesResult.getGames()) {
                System.out.println(j + ". " + i.getGameName() + ", w:" + i.getWhiteUsername() + " b:" + i.getBlackUsername() + " ID:" + i.getGameID());
            }
        } else {
            System.out.println("there are no games to print");
        }
    }
}
