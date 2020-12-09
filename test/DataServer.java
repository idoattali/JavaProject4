package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class DataServer {
    private Thread _thread;
    private boolean _stop;
    private HashMap<String, Double> _data;
    private boolean _loading;
    private Lock _lock;

    public DataServer(HashMap<String, Double> sharedMemory, HashMap<String, String> bindMap, Lock lock, int port, int interval)
    {
         _stop = false;
         _loading = true;
         _data = new HashMap<>();
         _data.put("simX", 0.0);
         _data.put("simY", 0.0);
         _data.put("simZ", 0.0);
         _thread = new Thread(() -> OpenDataServer(sharedMemory, bindMap, port, interval));
         _thread.start();
         _lock = lock;
    }

    public void Disconnect()
    {
        _stop = true;
        try {
            _thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void OpenDataServer(HashMap<String, Double> sharedMemory, HashMap<String, String> bindMap, int port, int interval)
    {
        try {
            ServerSocket server=new ServerSocket(port);
            server.setSoTimeout(1);
            while(!_stop){
                try{
                    Socket client=server.accept();
                    BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String line;
                    while(!_stop){
                        try{
                            line=in.readLine();
                            _lock.lock();
                            try {
                                String[] splittedLine = line.split(",");
                                double simX = Double.parseDouble(splittedLine[0]);
                                double simY = Double.parseDouble(splittedLine[1]);
                                double simZ = Double.parseDouble(splittedLine[2]);

                                _data.replace("simX", simX);
                                _data.replace("simY", simY);
                                _data.replace("simZ", simZ);

                                _loading = false;

                                //ReplaceByBind("simX", simX, sharedMemory, bindMap);
                                //ReplaceByBind("simY", simY, sharedMemory, bindMap);
                                //ReplaceByBind("simZ", simZ, sharedMemory, bindMap);
                            } finally {
                                _lock.unlock();
                            }

                        }catch(NumberFormatException e){}
                    }
                    in.close();
                    client.close();
                    int i = 5;
                }catch(SocketTimeoutException e){}
            }
            server.close();
        } catch (IOException e) {}
    }

    private void ReplaceByBind(String where, double value, HashMap<String, Double> sharedMemory, HashMap<String, String> bindMap)
    {
        if (bindMap.containsValue(where))
        {
            for (Map.Entry<String, String> entrySet : bindMap.entrySet())
            {
                if (entrySet.getValue().equals(where))
                {
                    sharedMemory.replace(entrySet.getKey(), value);
                }
            }
        }
    }

    public void WaitForInitialize()
    {
        while (_loading)
        {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double GetData(String which) {
        return _data.get(which);
    }
}
