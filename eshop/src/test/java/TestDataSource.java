import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

public class TestDataSource {
    @Test
    public void test() throws Exception {
        ApplicationContext apc = new ClassPathXmlApplicationContext("beans.xml");
        DataSource ds =(DataSource) apc.getBean("dataSource");
        Connection conn = ds.getConnection();
        System.out.println(conn);
    }
}
