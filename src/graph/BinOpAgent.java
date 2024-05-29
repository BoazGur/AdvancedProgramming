package graph;

import java.util.function.BinaryOperator;

import graph.TopicManagerSingleton.TopicManager;

public class BinOpAgent implements Agent {
    private String name;
    private String topicInput1;
    private String topicInput2;
    private String topicOutput;
    private BinaryOperator<Double> operator;

    private TopicManager topicManager;

    private double numInput1;
    private double numInput2;

    public BinOpAgent(String name, String topicInput1, String topicInput2, String topicOutput,
            BinaryOperator<Double> operator) {
        this.name = name;
        this.topicInput1 = topicInput1;
        this.topicInput2 = topicInput2;
        this.topicOutput = topicOutput;
        this.operator = operator;

        topicManager = TopicManagerSingleton.get();
        topicManager.getTopic(this.topicInput1).subscribe(this);
        topicManager.getTopic(this.topicInput2).subscribe(this);
        topicManager.getTopic(this.topicOutput).addPublisher(this);

        numInput1 = Double.NaN;
        numInput2 = Double.NaN;
    }

    @Override
    public String getName() {
        return name;
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
            return; // message is not a double

        if (topic.equals(topicInput1))
            numInput1 = msgNum; // if the topic matches the first input
        
        if (topic.equals(topicInput2))
            numInput2 = msgNum; // if the topic matches the second input
        
        if (Double.isNaN(numInput1) | Double.isNaN(numInput2)) return; // if there is a number not initialized

        Message output = new Message(operator.apply(numInput1, numInput2));
        topicManager.getTopic(topicOutput).publish(output);
    }

    @Override
    public void close() {}

}
