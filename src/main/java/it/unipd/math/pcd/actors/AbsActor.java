/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {
    private Mailbox<T> mailbox_ = new Mailbox<>();
    protected boolean is_Stopped = false;
    protected Thread messages_executor = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!is_Stopped) {
                synchronized (mailbox_) {
                    try {
                        while (mailbox_.sizeOfMailbox() == 0)
                            mailbox_.wait();
                        T message_ = mailbox_.getMessage();
                        sender = mailbox_.getSender();
                        mailbox_.removeFromMailbox();
                        receive(message_);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (mailbox_) {
                while (mailbox_.sizeOfMailbox() != 0) {
                    T message_ = mailbox_.getMessage();
                    sender = mailbox_.getSender();
                    mailbox_.removeFromMailbox();
                    receive(message_);
                }
            }
        }
    });

    public AbsActor() {
        messages_executor.start();
    }
    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    protected final void stopIncomingMessages() {
        is_Stopped = true;
    }

    protected final void receiveMessage(T message, ActorRef<T> sender) {
        synchronized (mailbox_) {
            if (!is_Stopped) {
                MailMessage<T> message_ = new MailMessage<>(message, sender);
                mailbox_.addToMailbox(message_);
                mailbox_.notifyAll();
            }
        }
    }
}
