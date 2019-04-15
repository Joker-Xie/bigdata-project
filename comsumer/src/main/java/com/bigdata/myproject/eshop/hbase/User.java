package com.bigdata.myproject.eshop.hbase;

public abstract class User {
    public String user;
    public String name;
    public String email;
    public String password;
    protected long tweetCount;

    @Override
    public String toString() {
        return String.format("<User: %s, %s, %s>",user,name,email);
    }
}
