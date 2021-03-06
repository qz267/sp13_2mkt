package com.secondmarket.common;

public enum CommonStrings {
	DATABASENAME("secondmarket"),
	PEOPLE_COLL("Investor"),
	COMPANY_COLL("Company"),
	FINANCIAL_ORG("Financial_Org");
	
    private String label;
 
    private CommonStrings(String label) {
        this.label = label;
    }
 
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
