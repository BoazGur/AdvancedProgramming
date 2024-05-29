package graph;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class TopicManagerSingleton {
    public static class TopicManager {
        private static final TopicManager instance;
        private ConcurrentHashMap<String, Topic> nameTopicMap;

        static {
            instance = new TopicManager();
        }

        private TopicManager() { 
            this.nameTopicMap = new ConcurrentHashMap<String, Topic>();
        }

        public Topic getTopic(String str) {
            if (nameTopicMap.containsKey(str))
                return nameTopicMap.get(str);

            Topic topic = new Topic(str);
            nameTopicMap.put(str, topic);
            
            return topic;
        }

        public Collection<Topic> getTopics() {
            return nameTopicMap.values();
        }

        public void clear() {
            nameTopicMap.clear();   
        }
    }

    public static TopicManager get() {
        return TopicManager.instance;
    }
}
