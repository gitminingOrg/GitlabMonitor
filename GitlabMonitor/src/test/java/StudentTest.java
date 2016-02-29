import javax.annotation.Resource;

import org.gitmining.monitor.dao.StudentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:monitor-config.xml"})
public class StudentTest {
	@Resource StudentDao studentDao;
	@Test
	public void testSelectCommit(){
		studentDao.selectStudentCommitRange("a", "2016-01-01", "2016-05-05");
	}
}
