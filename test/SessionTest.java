import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

public class SessionTest {
	SessionFactory  sessionFactory;
	Session session;
	
	@Test
	public void testgetcurrentSession()
	{
		        //1.�������ö���
				Configuration config=new Configuration().configure();
				//2.��������ע�����
				ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
				//3.���session����
				sessionFactory = config.buildSessionFactory(serviceRegistry);
				//4.���session
				session = sessionFactory.getCurrentSession();
				if(session!=null)
				{
					System.out.println("Session �����ɹ�");
				}
				else{
					System.out.println("Session ����ʧ��");
				}
				//5.��������
			//	transaction = session.beginTransaction();
	}
}
