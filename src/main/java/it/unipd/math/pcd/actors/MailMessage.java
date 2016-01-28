package it.unipd.math.pcd.actors;

/**
 * Created by mattia on 28/01/16.
 */
public class MailMessage<T extends Message> {
    private T message_;
    private ActorRef<T> sender_;

    public MailMessage(T message, ActorRef<T> sender) {
        message_ = message;
        sender_ = sender;
    }

    public T getMessage_() {
        return message_;
    }

    public ActorRef<T> getSender_() {
        return sender_;
    }
}
