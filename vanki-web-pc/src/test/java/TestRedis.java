import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.service.UserService;
import com.zoufanqi.service.redis.RedisTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by vanki on 16/10/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws ZouFanqiException {
//        this.redisTemplate.sadd("1", "11", "12", "13");
//        this.redisTemplate.sadd("2", "21", "22", "23");
        System.out.println(this.redisTemplate.srem("1", "11"));
        System.out.println(this.redisTemplate.smembers("1"));
        System.out.println(this.redisTemplate.smembers("2"));
        System.out.println(this.redisTemplate.sismember("1", "11"));
        System.out.println(this.redisTemplate.sismember("1", "111"));
    }
}
