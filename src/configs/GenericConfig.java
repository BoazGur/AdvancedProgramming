package configs;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import graph.Agent;
import graph.ParallelAgent;

public class GenericConfig implements Config {
    private List<Agent> agents;
    private String confFile;

    private final int capacity = 50;

    public GenericConfig() {
        agents = new ArrayList<>();
    }

    public void setConfFile(String confFile) {
        this.confFile = confFile;
    }

    @Override
    public void create() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(confFile));

            if (lines.size() % 3 != 0) return;

            for (int i = 0; i < lines.size(); i += 3) {
                String[] subs = lines.get(i + 1).split(",");
                String[] pubs = lines.get(i + 2).split(",");

                Class<?> _class = Class.forName(lines.get(i));
                Constructor<?> constructor = _class.getConstructor(subs.getClass(), pubs.getClass());
                Agent agent = (Agent) constructor.newInstance(subs, pubs);

                Agent parallelAgent = new ParallelAgent(agent, capacity);
                agents.add(parallelAgent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Generic Config";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
        for (Agent agent : agents) {
            agent.close();
        }
    }

}
