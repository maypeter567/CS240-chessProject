package ui;

import chess.ChessBoard;
import chess.ChessBoardImp;
import chess.ChessPiece;
import chess.ChessPositionImp;
import models.GameMod;

import java.util.HashMap;
import java.util.Map;

public class PrintJoinGame {
    ChessBoard chessBoardImp;
    Map<Integer, String> mapLetters = new HashMap<>();
    
    public PrintJoinGame() {
        mapLetters.put(0, "A");
        mapLetters.put(1, "B");
        mapLetters.put(2, "C");
        mapLetters.put(3, "D");
        mapLetters.put(4, "E");
        mapLetters.put(5, "F");
        mapLetters.put(6, "G");
        mapLetters.put(7, "H");
    }
    
    public void print(GameMod gameMod) {
        chessBoardImp = gameMod.getGame().getBoard();
        printWhite(gameMod);
        printBlack(gameMod);
    }
    
    private void printWhite(GameMod gameMod) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(gameMod.getWhiteUsername()).append("\n").append(" |");
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(i).append("|");
        }
        stringBuilder.append("\n");
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(mapLetters.get(i)).append("|");
            for (int j = 0; j < 8; j++) {
                var piece = chessBoardImp.getPiece(new ChessPositionImp(i, j));
                if (piece != null) {
                    stringBuilder.append(piece).append("|");
                } else {
                    stringBuilder.append(" |");
                }
            }
            stringBuilder.append(mapLetters.get(i)).append("\n");
        }
        stringBuilder.append(" |");
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(i).append("|");
        }
        stringBuilder.append("\n").append(gameMod.getBlackUsername()).append("\n\n");
        System.out.print(stringBuilder);
    }
    
    private void printBlack(GameMod gameMod) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(gameMod.getBlackUsername()).append("\n").append(" |");
        for (int i = 7; i >= 0; i--) {
            stringBuilder.append(i).append("|");
        }
        stringBuilder.append("\n");
        for (int i = 7; i >= 0; i--) {
            stringBuilder.append(mapLetters.get(i)).append("|");
            for (int j = 0; j < 8; j++) {
                var piece = chessBoardImp.getPiece(new ChessPositionImp(i, j));
                    if (piece != null) {
                        stringBuilder.append(piece).append("|");
                    } else {
                        stringBuilder.append(" |");
                    }
                }
                stringBuilder.append(mapLetters.get(i)).append("\n");
            }
        stringBuilder.append(" |");
        for (int i = 7; i >= 0; i--) {
            stringBuilder.append(i).append("|");
        }
        stringBuilder.append("\n").append(gameMod.getWhiteUsername()).append("\n\n");
        System.out.print(stringBuilder);
    }
}
