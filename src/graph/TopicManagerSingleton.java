package graph;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class TopicManagerSingleton {
    static class TopicManager {
        private static final TopicManager instance;
        private ConcurrentHashMap<String, Topic> map;

        private TopicManager() { }

        static {
            instance = new TopicManager();
        }

        public Topic getTopic(String str) {
            if (map.containsKey(str))
                return map.get(str);

            Topic topic = new Topic(str);
            map.put(str, topic);
            
            return topic;
        }

        public Collection<Topic> getTopics() {
            return map.values();
        }

        public void clear() {
            map.clear();   
        }
    }

    public static TopicManager get() {
        return TopicManager.instance;
    }
}
