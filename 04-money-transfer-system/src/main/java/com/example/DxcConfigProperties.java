package com.example;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dxc.config")
public class DxcConfigProperties {

    private  String property1;
    private  String property2;
    private  String property3;

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }


    public String getProperty3() {
        return property3;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public void setProperty3(String property3) {
        this.property3 = property3;
    }

    @Override
    public String toString() {
        return "DxcConfigProperties{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", property3='" + property3 + '\'' +
                '}';
    }
}
