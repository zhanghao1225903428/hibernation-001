import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



//测试类
public class StudentsTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init()
	{
		//1.创建配置对象
		Configuration config=new Configuration().configure();
		//2.创建服务注册对象
		ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		//3.获得session工厂
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		//4.获得session
		session = sessionFactory.openSession();
		if(session!=null)
		{
			System.out.println("Session 创建成功");
		}
		else{
			System.out.println("Session 创建失败");
		}
		//5.开启事务
		transaction = session.beginTransaction();

	}
	@After
	public void destory(){
		//提交事务
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void SaveStudents()
	{
		//hibernate 插入操作
		
		//生成学生对象
		Students s=new Students();
		s.setSname("张三");
		s.setBirthday(new Date());
		s.setAddress("北京");
		s.setGender("男");
	    System.out.println(s.toString());
	    session.save(s);   //保存对象进数据库
	}
	@Test
	public void GetAndLoadStudent()
	{
		//hibernate load操作
		//load 采用代理的方式，延时加载。只是返回一个带有id的代理对象，等到真正用到才进行查找
		//load()查询为空则抛出objectNotFoundexception
		Students students=(Students)session.load(Students.class, 1);
		//get()立刻发送sql语句，不管是否调用
		//get()查询为空则返回null
		//Students students=(Students)session.get(Students.class, 1);
		System.out.println(students.toString());
	}
	
	@Test
	public void UpdateStudent()
	{
		//hibernate 更新操作
		Students students=(Students)session.load(Students.class, 1);
		students.setGender("女");
		session.update(students);
	}
	
	@Test
	public void DeleteStudent()
	{
		//hibernate 删除操作
		Students students=(Students)session.load(Students.class, 1);
		session.delete(students);
	}
	
	@Test
	public void testWriteBlob() throws Exception
	{
		//向数据库中写入Blob类型数据文件
		Students s=new Students();
		s.setSname("李四");
		s.setBirthday(new Date());
		s.setAddress("上海");
		s.setGender("男");
		File file=new java.io.File("E:"+File.separator+"1"+File.separator+"boy.jpg");
		InputStream inputStream=new FileInputStream(file);
		Blob image=Hibernate.getLobCreator(session).createBlob(inputStream,inputStream.available());
	    s.setPicture(image);
        session.save(s);
        
	}
	@Test
	public void testReadBlob() throws Exception
	{
		//从数据库写回本地Blob类型
		Students students=(Students)session.get(Students.class,1);
		Blob image=students.getPicture();
		InputStream inputStream=image.getBinaryStream();
		File file=new File("E:\\destory.jpg");
		OutputStream outputStream=new FileOutputStream(file);
		
		byte [] buffer=new byte[inputStream.available()];
		inputStream.read(buffer);
		outputStream.write(buffer);
	    inputStream.close();
	    outputStream.close();		
	}
	
	
}
