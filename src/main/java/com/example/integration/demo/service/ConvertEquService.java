package com.example.integration.demo.service;

import java.util.List;

public class ConvertEquService extends BaseConvertService {

    @Override
    protected List dealMsg(List payLoad) {
        for (Object o : payLoad) {
            System.out.println("------------------------------------");
            System.out.println(o);
        }
        return payLoad;
    }


}
