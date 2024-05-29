package graph;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ParallelAgent implements Agent {
    private Agent agent;
    private BlockingQueue<Message> msgQueue;
    private boolean stop;

    public ParallelAgent(Agent agent, int capacity) {
        this.agent = agent;
        this.msgQueue = new ArrayBlockingQueue<Message>(capacity);
        this.stop = false;

        Thread thread = new Thread(() -> {
            while (!stop) {
                if (!msgQueue.isEmpty()) {
                    try {
                        String topic = msgQueue.take().asText;
                        Message msg = msgQueue.take();
                        agent.callback(topic, msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    @Override
    public String getName() {
        return agent.getName();
    }

    @Override
    public void reset() {
        agent.reset();
    }

    @Override
    public void callback(String topic, Message msg) {
        try {
            msgQueue.put(new Message(topic));
            msgQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        stop = true;
    }

}
