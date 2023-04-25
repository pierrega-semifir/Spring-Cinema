package piga.spring.cinema;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class PayloadController{

    @PostMapping
    public Object test (@RequestBody Payload payload) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("payload", payload); // request body
        ret.put("now", new Date());
        return ret;
    }
}