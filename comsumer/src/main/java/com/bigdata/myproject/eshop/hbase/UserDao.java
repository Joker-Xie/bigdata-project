package com.bigdata.myproject.eshop.hbase;

import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;


/*
* User实现类
* */

public class UserDao {
    private static final byte[] TABLE_NAME = Bytes.toBytes("user");
    private static final byte[] INFO_FAM = Bytes.toBytes("info");
    private static final byte[] USER_COL = Bytes.toBytes("user");
    private static final byte[] NAME_COL = Bytes.toBytes("name");
    private static final byte[] EMAIL_COL = Bytes.toBytes("email");
    private static final byte[] PASS_COL = Bytes.toBytes("password");
    public static final byte[] TWEETS_COL = Bytes.toBytes("tweet_count");
    private HConnection conn ;

    public UserDao(HConnection conn){
        this.conn = conn;
    }

    private static Get mkGet(String user){
        Get g = new Get(Bytes.toBytes(user));
        g.addFamily(INFO_FAM);
        return g;
    }

    private static Put mkPut(User u){
        Put p = new Put(Bytes.toBytes(u.user));
        p.add(INFO_FAM,USER_COL,Bytes.toBytes(u.user));
        p.add(INFO_FAM,NAME_COL,Bytes.toBytes(u.name));
        p.add(INFO_FAM,EMAIL_COL,Bytes.toBytes(u.email));
        p.add(INFO_FAM,PASS_COL,Bytes.toBytes(u.password));
        return p;
    }

    private static Delete mkDel(String user){
        Delete d = new Delete(Bytes.toBytes(user));
        return d;
    }

    public void  addUser(String user,
                         String name,
                         String email,
                         String password) throws IOException {
        HTableInterface htable = conn.getTable(TABLE_NAME);
        Put p = mkPut(new User(user,name,email,password)) ;
        htable.put(p);
        htable.close();
    }

    public User getUser(String user) throws IOException {
        HTableInterface htable = conn.getTable(TABLE_NAME);
        Get g = mkGet(user);
        Result rs = htable.get(g);
        if (rs.isEmpty()){
            return null;
        }
        User u = new User(rs);
        htable.close();
        return u;
    }

    public void deleteUser(String user) throws IOException {
        HTableInterface htable = conn.getTable(TABLE_NAME);
        Delete d = mkDel(user);
        htable.close();
    }
    //内外类
    private static class User extends com.bigdata.myproject.eshop.hbase.User{
        private User (Result r){
            this(r.getValue(INFO_FAM,USER_COL),
                    r.getValue(INFO_FAM,NAME_COL),
                    r.getValue(INFO_FAM,EMAIL_COL),
                    r.getValue(INFO_FAM,PASS_COL),
                    r.getValue(INFO_FAM,TWEETS_COL) == null ? Bytes.toBytes(0L):r.getValue(INFO_FAM,TWEETS_COL)
            );
        }
        private User(byte[] user,byte[] name,byte[] email,byte[] password,byte[] tweetCount){
            this(Bytes.toString(user),
                    Bytes.toString(name),
                    Bytes.toString(email),
                    Bytes.toString(password));
            this.tweetCount = Bytes.toLong(tweetCount);
        }

        private User(String user,
                     String name,
                     String email,
                     String password){
            this.user = user;
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }
}
