package graph;

import graph.TopicManagerSingleton.TopicManager;

public class PlusAgent implements Agent {
    private String subs[];
    private String pubs[];
    private String topicInput1;
    private String topicInput2;
    private String topicOutput;
    private double x;
    private double y;

    private TopicManager topicManager;

    public PlusAgent(String subs[], String pubs[]) {
        this.subs = subs.clone();
        this.pubs = pubs.clone();

        if (subs.length < 2){
            this.topicInput1 = "0";
            this.topicInput2 = "0";
        } else {
            this.topicInput1 = this.subs[0];
            this.topicInput2 = this.subs[1];
        }

        if (pubs.length < 1)
            this.topicOutput = "0";
        else
            this.topicOutput = this.pubs[0];

        topicManager = TopicManagerSingleton.get();
        topicManager.getTopic(this.topicInput1).subscribe(this);
        topicManager.getTopic(this.topicInput2).subscribe(this);
        topicManager.getTopic(this.topicOutput).addPublisher(this);

        this.x = Double.NaN;
        this.y = Double.NaN;
    }

    @Override
    public String getName() {
        return "plus";
    }

    @Override
    public void reset() {
        this.topicInput1 = "0";
        this.topicInput2 = "0";
    }

    @Override
    public void callback(String topic, Message msg) {
        double msgNum = msg.asDouble;

        if (Double.isNaN(msgNum))
            return;
        
        if (topic.equals(topicInput1))
            x = msgNum;
        
        if (topic.equals(topicInput2))
            y = msgNum; 

        if (Double.isNaN(x) | Double.isNaN(y)) return;

        Message output = new Message(x + y);
        topicManager.getTopic(topicOutput).publish(output);
    }

    @Override
    public void close() { }
}
