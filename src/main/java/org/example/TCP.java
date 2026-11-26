package org.example;

import org.example.utils.u16;

public class TCP {
    private final String host;
    private final u16 port;

    public TCP(String host, u16 port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port.getValue();
    }

    @Override
    public String toString() {
        return "tcp://" +
                host +
                ":" +
                port.getValue();
    }
}
