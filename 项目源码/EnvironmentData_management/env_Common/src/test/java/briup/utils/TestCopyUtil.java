package briup.utils;


import com.briup.study.Student;
import com.briup.utils.CopyUtils;
import org.junit.Test;

/**
 * @Auther: vanse(lc)
 * @Date: 2023/9/22-09-22-15:04
 * @Description：com.briup.utils
 */
public class TestCopyUtil {
    @Test
    public void test() throws Exception {
        Student s = new Student(1,"tom",18);
        Student copy = CopyUtils.copy(s,"age","name");
        System.out.println("copy = " + copy);

//        System.out.println(Integer.parseInt("10", 16));
//        System.out.println(Integer.parseInt("8", 16));
//        System.out.println(Integer.parseInt("2", 16));


    }
}
