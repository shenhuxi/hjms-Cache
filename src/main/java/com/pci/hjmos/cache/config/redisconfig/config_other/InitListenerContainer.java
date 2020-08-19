package com.pci.hjmos.cache.config.redisconfig.config_other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisShardInfo;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/12 17:03
 * @Modified By
 */
@Component
public class InitListenerContainer implements ApplicationRunner {
    @Autowired
    private DefaultListableBeanFactory beanFactory;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private MessageListener keyExpiredListener;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(redisProperties.getCluster() == null)
            return;
        RedisClusterConnection redisClusterConnection = redisConnectionFactory.getClusterConnection();
        if (redisClusterConnection != null) {
            Iterable<RedisClusterNode> nodes = redisClusterConnection.clusterGetNodes();
            for (RedisClusterNode node : nodes) {
                if (node.isMaster()) {
                    String containerBeanName = "messageContainer" + node.hashCode();
                    if (beanFactory.containsBean(containerBeanName)) {
                        return;
                    }
                    JedisConnectionFactory factory = new JedisConnectionFactory(
                            new JedisShardInfo(node.getHost(), node.getPort()));
                    BeanDefinitionBuilder containerBeanDefinitionBuilder = BeanDefinitionBuilder
                            .genericBeanDefinition(RedisMessageListenerContainer.class);
                    containerBeanDefinitionBuilder.addPropertyValue("connectionFactory", factory);
                    containerBeanDefinitionBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
                    containerBeanDefinitionBuilder.setLazyInit(false);
                    beanFactory.registerBeanDefinition(containerBeanName,
                            containerBeanDefinitionBuilder.getRawBeanDefinition());

                    RedisMessageListenerContainer container = beanFactory
                            .getBean(containerBeanName, RedisMessageListenerContainer.class);
                    String listenerBeanName = "messageListener" + node.hashCode();
                    if (beanFactory.containsBean(listenerBeanName)) {
                        return;
                    }
                    container.addMessageListener(keyExpiredListener, new PatternTopic("__keyevent@*__:expired"));
                    container.start();
                }
            }
        }
    }
}
