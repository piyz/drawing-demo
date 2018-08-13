package by.matrosov.appl;


import javax.websocket.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@javax.websocket.server.ServerEndpoint(value = "/serverEndpoint", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class ServerEndpoint {

    private static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void handleOpen(Session userSession){
        users.add(userSession);
    }

    @OnClose
    public void handleClose(Session userSession){
        users.remove(userSession);
    }

    @OnError
    public void handleError(Throwable t){
        System.out.println(t);
    }

    @OnMessage
    public void handleMessage(Message incomingMessage, Session userSession) throws IOException, EncodeException {
        Message outMessage = new Message();
        outMessage.setX(incomingMessage.getX());
        outMessage.setY(incomingMessage.getY());
        outMessage.setSize(incomingMessage.getSize());
        outMessage.setColor(incomingMessage.getColor());
        Iterator<Session> iterator = users.iterator();
        Session tempSession = null;
        while (iterator.hasNext()){
            tempSession = iterator.next();
            if (!tempSession.equals(userSession)){
                tempSession.getBasicRemote().sendObject(outMessage);
            }
        }
    }
}