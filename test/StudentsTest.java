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



//������
public class StudentsTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init()
	{
		//1.�������ö���
		Configuration config=new Configuration().configure();
		//2.��������ע�����
		ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		//3.���session����
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		//4.���session
		session = sessionFactory.openSession();
		if(session!=null)
		{
			System.out.println("Session �����ɹ�");
		}
		else{
			System.out.println("Session ����ʧ��");
		}
		//5.��������
		transaction = session.beginTransaction();

	}
	@After
	public void destory(){
		//�ύ����
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void SaveStudents()
	{
		//hibernate �������
		
		//����ѧ������
		Students s=new Students();
		s.setSname("����");
		s.setBirthday(new Date());
		s.setAddress("����");
		s.setGender("��");
	    System.out.println(s.toString());
	    session.save(s);   //�����������ݿ�
	}
	@Test
	public void GetAndLoadStudent()
	{
		//hibernate load����
		//load ���ô���ķ�ʽ����ʱ���ء�ֻ�Ƿ���һ������id�Ĵ�����󣬵ȵ������õ��Ž��в���
		//load()��ѯΪ�����׳�objectNotFoundexception
		Students students=(Students)session.load(Students.class, 1);
		//get()���̷���sql��䣬�����Ƿ����
		//get()��ѯΪ���򷵻�null
		//Students students=(Students)session.get(Students.class, 1);
		System.out.println(students.toString());
	}
	
	@Test
	public void UpdateStudent()
	{
		//hibernate ���²���
		Students students=(Students)session.load(Students.class, 1);
		students.setGender("Ů");
		session.update(students);
	}
	
	@Test
	public void DeleteStudent()
	{
		//hibernate ɾ������
		Students students=(Students)session.load(Students.class, 1);
		session.delete(students);
	}
	
	@Test
	public void testWriteBlob() throws Exception
	{
		//�����ݿ���д��Blob���������ļ�
		Students s=new Students();
		s.setSname("����");
		s.setBirthday(new Date());
		s.setAddress("�Ϻ�");
		s.setGender("��");
		File file=new java.io.File("E:"+File.separator+"1"+File.separator+"boy.jpg");
		InputStream inputStream=new FileInputStream(file);
		Blob image=Hibernate.getLobCreator(session).createBlob(inputStream,inputStream.available());
	    s.setPicture(image);
        session.save(s);
        
	}
	@Test
	public void testReadBlob() throws Exception
	{
		//�����ݿ�д�ر���Blob����
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
