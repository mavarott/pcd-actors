package it.unipd.math.pcd.actors;
import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

/**
 * Created by mattia on 28/01/16.
 */
public final class ConcreteActorSystem extends AbsActorSystem {
    @Override
    public void stop(ActorRef<?> actor_) {
        if (getMap().containsKey(actor_)) {
            AbsActor absactor_ = (AbsActor) getActor(actor_);
            absactor_.stopIncomingMessages();
            getMap().remove(actor_);
        }
        else {
            throw new NoSuchActorException();
        }
    }
    @Override
    public void stop() {
        for (ActorRef<?> actor_ : this.getMap().keySet()) {
            this.stop(actor_);
        }
    }

    @Override
    protected ActorRef createActorReference(ActorMode mode_) {
        if (mode_.equals(ActorMode.LOCAL)) {
            return new ConcreteActorRef(this);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
