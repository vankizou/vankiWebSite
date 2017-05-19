package com.zoufanqi.service.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {

    private Resource addressConfig;
    private String addressKeyPrefix;

    private JedisCluster jedisCluster;
    private Integer timeout;
    private Integer maxRedirections;

    private GenericObjectPoolConfig genericObjectPoolConfig;

    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

    @Override
    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }

    @Override
    public Class<? extends JedisCluster> getObjectType() {

        if (this.jedisCluster == null) {
            return JedisCluster.class;
        }
        return jedisCluster.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        try {

            Properties prop = new Properties();
            prop.load(this.addressConfig.getInputStream());
            Set<HostAndPort> hostSet = new HashSet<HostAndPort>();
            for (Object key : prop.keySet()) {
                if (key == null) continue;

                if (!key.toString().startsWith(addressKeyPrefix)) {
                    continue;
                }

                String val = prop.get(key).toString();

                boolean matches = p.matcher(val).matches();

                if (!matches) {
                    throw new IllegalArgumentException("illegal ip and port");
                }

                String[] ipAndPort = val.split(":");

                HostAndPort hp = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                hostSet.add(hp);

            }

        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }


    public Resource getAddressConfig() {
        return addressConfig;
    }

    public void setAddressConfig(Resource addressConfig) {
        this.addressConfig = addressConfig;
    }

    public String getAddressKeyPrefix() {
        return addressKeyPrefix;
    }

    public void setAddressKeyPrefix(String addressKeyPrefix) {
        this.addressKeyPrefix = addressKeyPrefix;
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getMaxRedirections() {
        return maxRedirections;
    }

    public void setMaxRedirections(Integer maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public GenericObjectPoolConfig getGenericObjectPoolConfig() {
        return genericObjectPoolConfig;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
        this.genericObjectPoolConfig = genericObjectPoolConfig;
    }

}
