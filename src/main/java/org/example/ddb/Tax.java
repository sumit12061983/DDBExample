package org.example.ddb;

import java.util.Map;

public class Tax {
    private String taxName;
    private Map<String, String> properties;

    public Tax(String taxName, Map<String, String> properties) {
        this.taxName = taxName;
        this.properties = properties;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
