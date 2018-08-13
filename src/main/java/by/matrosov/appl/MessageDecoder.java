package by.matrosov.appl;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(String s) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
        message.setX(jsonObject.getJsonNumber("x").doubleValue());
        message.setY(jsonObject.getJsonNumber("y").doubleValue());
        message.setSize(jsonObject.getJsonNumber("size").doubleValue());
        message.setColor(jsonObject.getJsonNumber("color").toString());
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        boolean flag = true;
        try {
            Json.createReader(new StringReader(s)).readObject();
        }catch (Exception ignored){
            flag = false;
        }
        return flag;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}