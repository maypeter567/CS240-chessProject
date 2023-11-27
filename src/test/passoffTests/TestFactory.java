package passoffTests;

import chess.*;
import chess.pieces.*;

/**
 * Used for testing your code
 */
public class TestFactory {

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard(){
        return new ChessBoardImp();
    }

    public static ChessGame getNewGame(){
        return new ChessGameImp();
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        String colour;
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            colour = "w";
        } else {
            colour = "b";
        }
        if (type == ChessPiece.PieceType.KNIGHT) {
            return new Knight(colour);
        } else if (type == ChessPiece.PieceType.PAWN) {
            return new Pawn(colour);
        } else if (type == ChessPiece.PieceType.KING) {
            return new King(colour);
        } else if (type == ChessPiece.PieceType.ROOK) {
            return new Rook(colour);
        } else if (type == ChessPiece.PieceType.BISHOP) {
            return new Bishop(colour);
        } else if (type == ChessPiece.PieceType.QUEEN) {
            return new Queen(colour);
        }
		return null;
    }

    public static ChessPosition getNewPosition(Integer row, Integer col){
		return new ChessPositionImp(row-1, col-1);
    }

    public static ChessMove getNewMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){
		return new ChessMoveImp(startPosition, endPosition, promotionPiece);
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort(){
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime(){
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 3000L;
    }
    //------------------------------------------------------------------------------------------------------------------
}
