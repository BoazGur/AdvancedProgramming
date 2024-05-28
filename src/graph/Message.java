package graph;

import java.util.Date;

public class Message {
    public final char[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String message) {
        this.asText = message;
        this.data = message.toCharArray();
        this.asDouble = toDouble(message);
        this.date = new Date();
    }

    public Message(char[] message) {
        this(new String(message));
    }

    public Message(double message) {
        this(Double.toString(message));
    }

    private double toDouble(String str) {
        double d = 0;
        try {
            d = Double.parseDouble(str);
        } catch (Exception e) {
            d = Double.NaN;
        }

        return d;
    }
}
