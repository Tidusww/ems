package java;

/**
 * Created by tidus on 2018/1/19.
 */

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试基础配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring-mvc.xml", "classpath*:applicationContext.xml","classpath*:applicationContext-shiro.xml","classpath*:applicationContext-datasource.xml","classpath*:mybatis-config.xml" })
public class BaseTest {

}
