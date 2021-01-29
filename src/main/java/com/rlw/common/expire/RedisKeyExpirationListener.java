package com.rlw.common.expire;

import com.rlw.entity.Order;
import com.rlw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {


    @Autowired
    private OrderService orderService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对 redis 数据失效事件，进行数据处理
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        /*订单超时关闭*/
        String temp = message.toString();
        String name = temp.substring(0,temp.indexOf(":"));
        System.out.println("当前过期消息是"+name);
        if(name.equals("order")){
            String orderId = temp.substring(temp.indexOf(":")+1);
            Order order = orderService.getById(orderId);
            order.setOrderState("已关闭");
            orderService.saveOrUpdate(order);
        }
    }
}
