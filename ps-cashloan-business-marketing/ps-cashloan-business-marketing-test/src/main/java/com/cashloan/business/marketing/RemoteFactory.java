package com.cashloan.business.marketing;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

public class RemoteFactory {

    private static String address = "zookeeper://127.0.0.1:2181";//这里写上zkServer的IP
//    private static String address = "zookeeper://192.168.100.181:2181";//开发
//    private static String address = "zookeeper://192.168.100.141:2181";//测试
    private static String version = "1.0.0";
    private static String protocol = "dubbo";
    
    
    public static <T>T getRemote(Class<T> T){
        return getRemote(T,address,version,protocol);
    }
    
    public static <T>T getRemote(Class<T> T,String version){
        return getRemote(T,address,version,protocol);
    }
    
    public static <T> T getRemote(Class<T> T, String address, String version, String protocol){
        ApplicationConfig application = new ApplicationConfig();
        application.setName(T.getName());
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(address);
        ReferenceConfig<T> reference = new ReferenceConfig<T>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能�?�成内存和连接泄�?
        reference.setInterface(T);
        reference.setVersion(version);
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setProtocol(protocol);
        T remote = reference.get();
        return remote;
    }
    
}
