package com.mock.services;

import java.util.HashMap;
import java.util.Map;

public class BaseService {

    private static Map<String, Double> accountMap= new HashMap<>();

    /**
     * Set account Number
     * @param account
     */
    public void setAccountToMap(String account){
        accountMap.put(account, 0.00);
    }

    /**
     * set value if account is exists
     * @param account
     * @param amount
     * @return account added status
     */
    public boolean addToAccountMap(String account, double amount){
        boolean hasAccount = accountMap.containsKey(account);
        if(hasAccount && amount > 0){
            double balnce = accountMap.get(account);
            accountMap.put(account, balnce+amount);
        }
        return hasAccount;
    }

    /**
     * validate if account is available
     * @param account
     * @return acount status
     */
    public boolean isAccountAvailable(String account){
        return accountMap.containsKey(account);

    }

    /**
     * get account balance
     * @param account
     * @return account balance
     */
    public double getAccountBalance(String account){
        boolean hasAccount = accountMap.containsKey(account);
        if(hasAccount){
            return accountMap.get(account);
        }
        return 0.00;
    }

    public boolean pullFromAccount(String account, double amount){
        boolean hasAccount = accountMap.containsKey(account);
        if(hasAccount && amount > 0){
            double balnce = accountMap.get(account);
            // validate if withdrawal amount is  less than account balance
            hasAccount = balnce > amount;
            if(hasAccount){
                accountMap.put(account, balnce-amount);
            }
        }
        return hasAccount;
    }

    /**
     * flush account data
     * @return
     */
    public boolean dataFlush(){
        try {
            accountMap.clear();
            return true;
        }catch (Exception ex){
            ex.getStackTrace();
        }
       return false;
    }
}
