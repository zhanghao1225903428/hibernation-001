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
		        //1.创建配置对象
				Configuration config=new Configuration().configure();
				//2.创建服务注册对象
				ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
				//3.获得session工厂
				sessionFactory = config.buildSessionFactory(serviceRegistry);
				//4.获得session
				session = sessionFactory.getCurrentSession();
				if(session!=null)
				{
					System.out.println("Session 创建成功");
				}
				else{
					System.out.println("Session 创建失败");
				}
				//5.开启事务
			//	transaction = session.beginTransaction();
	}
}
