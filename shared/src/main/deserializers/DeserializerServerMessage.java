package deserializers;

import com.google.gson.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.allMessages.ErrorGameMessage;
import webSocketMessages.serverMessages.allMessages.LoadGameMessage;
import webSocketMessages.serverMessages.allMessages.NotificationGameMessage;

import java.lang.reflect.Type;

public class DeserializerServerMessage implements JsonDeserializer<ServerMessage> {
    @Override
    public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        GsonBuilder gson = new GsonBuilder();
        String commandType = jsonElement.getAsJsonObject().get("CommandType").getAsString();
        return switch (commandType) {
            case "LOAD_GAME" -> gson.create().fromJson(jsonElement, LoadGameMessage.class);
            case "ERROR" -> gson.create().fromJson(jsonElement, ErrorGameMessage.class);
            case "NOTIFICATION" -> gson.create().fromJson(jsonElement, NotificationGameMessage.class);
            default -> throw new UnsupportedOperationException("the class is wrong for DeserializerServerMessage gson");
        };
    }
}
