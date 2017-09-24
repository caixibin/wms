import com.alibaba.druid.filter.config.ConfigTools;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;


public class test {
    @Test
    public void test1()throws Exception{
        Object admin = ConfigTools.encrypt("admin");
        System.out.println(admin);
    }
    @Test
    public void testMd5(){
        SimpleHash admin = new SimpleHash("md5","1","wewe",1);
        System.out.println(admin);
    }
}
