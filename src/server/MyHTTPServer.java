package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import servlets.Servlet;

public class MyHTTPServer extends Thread implements HTTPServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    
    private ConcurrentMap<String, Servlet> getMap;
    private ConcurrentMap<String, Servlet> postMap;
    private ConcurrentMap<String, Servlet> deleteMap;

    public MyHTTPServer(int port, int nThreads) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.pool = Executors.newFixedThreadPool(nThreads);

        this.getMap = new ConcurrentHashMap<>();
        this.postMap = new ConcurrentHashMap<>();
        this.deleteMap = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    @Override
    public void addServlet(String httpCommand, String uri, Servlet s) {
        if (httpCommand.equals("GET")) {
            getMap.put(uri, s);
        } else if (httpCommand.equals("POST")) {
            postMap.put(uri, s);
        } else if (httpCommand.equals("DELETE")) {
            deleteMap.put(uri, s);
        }
    }

    @Override
    public void removeServlet(String httpCommand, String uri) {
        if (httpCommand.equals("GET")) {
            getMap.remove(uri);
        } else if (httpCommand.equals("POST")) {
            postMap.remove(uri);
        } else if (httpCommand.equals("DELETE")) {
            deleteMap.remove(uri);
        }
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }
}
