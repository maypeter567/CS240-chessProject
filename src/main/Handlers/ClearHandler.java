package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import result.ClearResult;
import service.Clear;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    public Object clear(Request req, Response res) throws DataAccessException {
        return null;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        Clear clearObject = new Clear();
        
        ClearResult obj = clearObject.clear();
        return serializer.toJson(obj);
    }
}
