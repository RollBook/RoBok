import com.fall.robok.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author FAll
 * @date 2022/9/22 23:29
 */
public class test {
    @Autowired
    UserMapper mapper;

    @Test
    public void test(){
        System.out.println(mapper);
    }
}
