package br.com.leandro.config;

//import com.ibm.mq.jakarta.jms.MQXAConnectionFactory;
//import io.quarkus.arc.DefaultBean;
//import jakarta.ejb.Startup;
//import jakarta.enterprise.inject.Default;
//import jakarta.enterprise.inject.Produces;
//import jakarta.inject.Inject;
//import jakarta.inject.Singleton;
//import jakarta.jms.ConnectionFactory;
//import jakarta.jms.JMSException;
//import jakarta.transaction.TransactionManager;
//import org.jboss.narayana.jta.jms.ConnectionFactoryProxy;
//import org.jboss.narayana.jta.jms.TransactionHelperImpl;
//
//@Singleton
//@Startup
//public class MQConfig {
//
//    @Inject
//    private TransactionManager transactionManager;
//
//    @Produces
//    @DefaultBean
//    @Default
//    public ConnectionFactory factory() throws JMSException {
//
//        MQXAConnectionFactory factory = new MQXAConnectionFactory();
//
//        factory.setQueueManager("QM1");
//        factory.setChannel("DEV.APP.SVRCONN");
//        factory.setHostName("localhost");
//        factory.setPort(1414);
//        factory.setTransportType(1);
//
//        TransactionHelperImpl helper = new TransactionHelperImpl(transactionManager);
//
//        ConnectionFactoryProxy proxy = new ConnectionFactoryProxy(factory, helper);
//
//        return proxy;
//    }
//
//}
