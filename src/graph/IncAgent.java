package graph;

import graph.TopicManagerSingleton.TopicManager;

public class IncAgent implements Agent {
    private String subs[];
    private String pubs[];
    private String topicInput;
    private String topicOutput;
    private double x;

    private TopicManager topicManager;

    public IncAgent(String subs[], String pubs[]) {
        this.subs = subs.clone();
        this.pubs = pubs.clone();

        if (subs.length < 1)
            this.topicInput = "0";
        else 
            this.topicInput = this.subs[0];

        if (pubs.length < 1)
            this.topicOutput = "0";
        else
            this.topicOutput = this.pubs[0];

        topicManager = TopicManagerSingleton.get();
        topicManager.getTopic(this.topicInput).subscribe(this);
        topicManager.getTopic(this.topicOutput).addPublisher(this);

        this.x = Double.NaN;
    }

    @Override
    public String getName() {
        return "inc";
    }

    @Override
    public void reset() {
        this.topicInput = "0";
    }

    @Override
    public void callback(String topic, Message msg) {
        double msgNum = msg.asDouble;

        if (Double.isNaN(msgNum))
            return;
        
        if (topic.equals(topicInput))
            x = msgNum;

        if (Double.isNaN(x)) return;

        Message output = new Message(x + 1);
        topicManager.getTopic(topicOutput).publish(output);
    }

    @Override
    public void close() { }
}
