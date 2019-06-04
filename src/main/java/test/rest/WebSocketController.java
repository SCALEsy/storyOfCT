package test.rest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import test.bean.result.Result;
import test.bean.result.ResultType;
import test.bean.socket.Msg;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@SendTo("/topic/test")
public class WebSocketController {
    private AtomicInteger count = new AtomicInteger();
    private AtomicInteger tag = new AtomicInteger();
    private AtomicInteger chance = new AtomicInteger(10);

    @MessageMapping("/receive")

    public Result<String> test(Msg msg) {
        String res = msg.getName() + "加入了强化房间";
        return new Result<>(ResultType.SUCCESS, res);
    }

    @MessageMapping("/hit")
    //@SendTo("/topic/test")
    public Result<String> hit(Msg msg) {
        count.incrementAndGet();
        String res = msg.getName() + "强化了CT" + count.get() + "次;+";
        Random random = new Random(System.currentTimeMillis());
        int i = random.nextInt() % 100;
        System.out.println(chance.get());
        if (i <= chance.get()) {
            tag.incrementAndGet();
            res += tag.get() + "成功，恭喜!";
            if (tag.get() >= 10) {
                chance.addAndGet(-1);
            }
        } else if (i <= 15 && i > chance.get()) {
            res += (tag.get() + 1) + "失败，CT毁坏，恭喜!";
            tag.set(0);
            chance.set(10);
        } else {
            res += (tag.get() + 1) + "失败，恭喜!";
        }

        return new Result<>(ResultType.SUCCESS, res);
    }
}
