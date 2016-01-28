package it.unipd.math.pcd.actors;

/**
 * Created by mattia on 28/01/16.
 */
public final class ConcreteActorRef<T extends Message> implements ActorRef<T> {
    private ConcreteActorSystem actorSystem_;
    public ConcreteActorRef(ConcreteActorSystem as_) {
        actorSystem_ = as_;
    }


    @Override
    public void send(T message, ActorRef to) {
        AbsActor absactor_ = (AbsActor) actorSystem_.getActor(to);
        absactor_.receiveMessage(message, this);
    }

    @Override
    public int compareTo(ActorRef actorRef_) {
        return (this == actorRef_) ? 0 : -1;
    }
}