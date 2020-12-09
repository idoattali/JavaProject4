package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Client {
    private String _host;
    private int _port;
    private Lock _lock;
    private Socket _client;
    private PrintWriter _out;

    public Client(String host, int port, Lock lock)
    {
        _host = host;
        _port = port;
        _lock = lock;
        try {
            _client = new Socket(_host, _port);
            _out = new PrintWriter(_client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetVariable(String key, double value)
    {
        _lock.lock();
        try {
            _out.println("set " + key + " " + value);
        } finally {
            _lock.unlock();
        }
    }

    public void Disconnect()
    {
        _out.println("bye");
        _out.close();
        try {
            _client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
