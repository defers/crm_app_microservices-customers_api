package com.defers.crm.customers.output;

public interface MessageSender<K, V> {
    void send(V producedObject);
}
