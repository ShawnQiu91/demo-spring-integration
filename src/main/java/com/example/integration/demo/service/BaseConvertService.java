package com.example.integration.demo.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;

public abstract class BaseConvertService<S,T> {
    private List<S> payLoad;

    public BaseConvertService<S,T>  getSource(Message<List<S>> msg){
        payLoad = msg.getPayload();
        return this;
    }

    public  Message<List<T>> convert(){
        return new GenericMessage<>(dealMsg(this.payLoad));
    }


    protected abstract List<T> dealMsg(List<S> payLoad);

}
