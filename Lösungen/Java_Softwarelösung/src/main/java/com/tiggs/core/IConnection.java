package com.tiggs.core;

public interface IConnection {
    Object connect();

    void close();
}
