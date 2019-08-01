package com.example.integration.demo.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;

public class ConvertEquService {

    public Message<Object> convert(Message<Object> msg){

        System.out.println(msg.getHeaders());
        System.out.println("------------------------------------");
        //消息体里是JSON数组
        Object o = msg.getPayload();
        System.out.println(o);
        Map map = new HashMap<>();
        map.put("TEST", "SUCCESS");
        return new GenericMessage<>(map);
    }
}
