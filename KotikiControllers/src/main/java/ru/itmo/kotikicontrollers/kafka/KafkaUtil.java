package ru.itmo.kotikicontrollers.kafka;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.itmo.kotikicontrollers.model.Message;

import java.util.Map;

@Component
public class KafkaUtil {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public boolean sendMessage(String topicName, Map<String, String> data) throws Exception{

        String message = serializeToJSON(data);
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, message);

        final boolean[] sendResult = new boolean[1];
        sendResult[0] = true;
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                sendResult[0] = true;
            }
            @Override
            public void onFailure(Throwable ex) {
                sendResult[0] = false;
            }
        });
        if(!sendResult[0]) throw new Exception("Cant send");
        return sendResult[0];
    }

    public static String serializeToJSON(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static <T> T deserializeFromJSON(String json, Class<T> type){
        Gson gson = new Gson();
        T object = gson.fromJson(json, type);
        return object;
    }
}
