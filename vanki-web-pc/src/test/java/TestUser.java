import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.service.UserService;
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
public class TestUser {
    @Autowired
    private UserService userService;

    @Test
    public void test() throws ZouFanqiException {
    }
}
