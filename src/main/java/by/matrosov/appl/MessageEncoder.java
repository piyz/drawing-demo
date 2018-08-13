package by.matrosov.appl;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {
    @Override
    public String encode(Message message) throws EncodeException {
        return Json.createObjectBuilder()
                .add("x", message.getX())
                .add("y", message.getY())
                .add("size", message.getSize())
                .add("color", message.getColor())
                .build().toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
