package br.com.leandro.config;

import com.ibm.mq.jakarta.jms.MQXAConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import io.quarkus.arc.DefaultBean;
import jakarta.ejb.DependsOn;
import jakarta.ejb.Startup;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.transaction.TransactionManager;
import org.jboss.narayana.jta.jms.ConnectionFactoryProxy;
import org.jboss.narayana.jta.jms.TransactionHelperImpl;

@Singleton
@Startup
@DependsOn("TransactionHelper")
public class MQConfig {

    private ConnectionFactory connectionFactory;

    @Inject
    private TransactionManager transactionManager;

    @Produces
    @DefaultBean
    @Default
    public ConnectionFactory factory() throws JMSException {

        MQXAConnectionFactory factory = new MQXAConnectionFactory();

        factory.setQueueManager("QM1");
        factory.setChannel("DEV.APP.SVRCONN");
        factory.setHostName("localhost");
        factory.setPort(1414);
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

        connectionFactory = factory;

        TransactionHelperImpl helper = new TransactionHelperImpl(transactionManager);

        ConnectionFactoryProxy proxy = new ConnectionFactoryProxy(factory, helper);

        return proxy;
    }

}
