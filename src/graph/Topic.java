package graph;

import java.util.HashSet;
import java.util.Set;

public class Topic {
    public final String name;
    private Set<Agent> subs;
    private Set<Agent> pubs;

    Topic(String name) {
        this.name = name;
        this.subs = new HashSet<Agent>();
        this.pubs = new HashSet<Agent>();
    }

    public Set<Agent> getSubs() {
        return new HashSet<Agent>(subs);
    }

    public Set<Agent> getPubs() {
        return new HashSet<Agent>(pubs);
    }

    public void subscribe(Agent agent) {
        subs.add(agent);
    }

    public void unsubscribe(Agent agent) {
        subs.remove(agent);
    }

    public void publish(Message msg) {
        for (Agent agent : subs)
            agent.callback(name, msg);
    }

    public void addPublisher(Agent agent) {
        pubs.add(agent);
    }

    public void removePublisher(Agent agent) {
        pubs.remove(agent);
    }
}
