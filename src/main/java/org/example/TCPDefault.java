package org.example;

public enum TCPDefault {
    PCSX2("127.0.0.1", 28011),
    RPCS3("127.0.0.1", 28012);

    private final String host;
    private final int port;

    TCPDefault(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
