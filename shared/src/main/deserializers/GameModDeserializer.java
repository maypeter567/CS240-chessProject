package deserializers;

import chess.ChessPiece;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import models.GameMod;

public class GameModDeserializer {
    public GameMod deserializer(String stringToChange) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(ChessPiece.class, new PieceAdapter());
        return gsonBuilder.create().fromJson(stringToChange, GameMod.class);
    }
}
