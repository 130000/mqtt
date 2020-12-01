package org.example.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.config.PublishMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MQTTServerController {
    @PostMapping
    public String send(@RequestParam String message){
        PublishMessage publishMessage=new PublishMessage();
        try {
            publishMessage.send(message);
        } catch (MqttException e) {
            e.printStackTrace();
            return "消息发送失败";
        }
        return "消息发送成功";
    }
}