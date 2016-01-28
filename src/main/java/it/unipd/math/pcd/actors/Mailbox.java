package it.unipd.math.pcd.actors;

import java.util.LinkedList;

/**
 * Created by mattia on 28/01/16.
 */
public class Mailbox<T extends Message> {

    private LinkedList<MailMessage<T>> list = new LinkedList<>();

    public void addToMailbox(MailMessage<T> message) {
        list.addFirst(message);
    }

    public void removeFromMailbox() {
        list.removeLast();
    }

    public int sizeOfMailbox() {
        return list.size();
    }

    public T getMessage() {
        return list.getLast().getMessage_();
    }

    public ActorRef<T> getSender() {
        return list.getLast().getSender_();
    }
}
