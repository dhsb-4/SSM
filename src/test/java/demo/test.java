package demo;

import com.util.PageUtils;
import com.web.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring.xml"})
public class test {

    @Resource
    private TestService testService;

    @Resource
    private RedisTemplate<String, Object> templat;

    @Test
    public void test1(){
        List<?> list = testService.userAll();
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void test2(){
        System.out.println(templat.opsForValue().get("java"));
    }
}
