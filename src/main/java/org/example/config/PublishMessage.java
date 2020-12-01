package org.example.config;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishMessage {
    private final int QOS = 1;
    private final String host = "tcp://localhost:1883";
    private final String username = "admin";
    private final String password = "public";
    private final String clientId = "emqx_test";
    private final String subTopic="testtopic/#";
    private final String pubTopic="testtopic/1";;
    public void send(String content) throws MqttException {
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        MqttClient mqttClient = new MqttClient(host, clientId, memoryPersistence);
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setCleanSession(true);
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                throwable.printStackTrace();
                System.out.println("连接断开，可以重连");
            }
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("Client 接收消息主题 : " + s);
                System.out.println("Client 接收消息Qos : " + mqttMessage.getQos());
                System.out.println("Client 接收消息内容 : " + new String(mqttMessage.getPayload()));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                System.out.println("deliveryComplete---------"+iMqttDeliveryToken.isComplete());
            }
        });
        System.out.println("connecting to broker:"+host);
        mqttClient.connect(mqttConnectOptions);
        System.out.println("connected");
        System.out.println("publishing content:"+content);
        mqttClient.subscribe(subTopic);
        MqttMessage mqttMessage=new MqttMessage(content.getBytes());
        mqttMessage.setQos(QOS);
        mqttClient.publish(pubTopic,mqttMessage);
        System.out.println("mqtt published");

    }
}
