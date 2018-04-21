package com.ljd.mysql;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateTableEntity {

    public static Connection getMySQLConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://drds92of41bn9r2n.drds.aliyun.boc:3306/poc_test", "poc_test", "poc_test_1");
        return conn;
    }


    public static List getAllTableName() throws Exception {
        List tables = new ArrayList();
        Connection conn = getMySQLConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES ");
        while (rs.next()) {
            String tableName = rs.getString(1);
            tables.add(tableName);
        }
        //先测试一个
        tables.clear();
        tables.add(new String("bc_center_herd"));

        rs.close();
        stmt.close();
        conn.close();
        return tables;
    }


    public static Map getCommentByTableName(List tableName) throws Exception {
        Map map = new HashMap();
        Connection conn = getMySQLConnection();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < tableName.size(); i++) {
            String table = (String) tableName.get(i);
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table);
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);
                String comment = parse(createDDL);
                map.put(table, comment);
            }
            rs.close();
        }
        stmt.close();
        conn.close();
        return map;
    }

    public static void getColumnCommentByTableName(List tableName) throws Exception {
        Map map = new HashMap();
        Connection conn = getMySQLConnection();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < tableName.size(); i++) {
            String table = (String) tableName.get(i);
            ResultSet rs = stmt.executeQuery("show full columns from " + table);
            System.out.println("【" + table + "】");
            StringBuilder code = new StringBuilder();
            while (rs.next()) {
                String fieldName = rs.getString("Field");
                String comment = rs.getString("Comment");
                String type = rs.getString("Type");
                System.out.println(fieldName + "\t:\t" + comment);
                if(skipFields(fieldName)){
                    continue;
                }
                code.append("    /**").append(comment).append("*/\n");
                code.append("    ").append("@Column(name = \"").append(fieldName).append("\")\n");
                code.append("    ").append(convertFiledType(type)).append("  ").append(convertName(fieldName)).append(";\n");
            }
            rs.close();
            System.out.println(code);
            FileOutputStream fout = new FileOutputStream(new File("d:/" + table + ".java"));
        }
        stmt.close();
        conn.close();

    }

    public static boolean skipFields(String fieldName){
        if(fieldName.equals("id") || fieldName.equals("tenant_id") || fieldName.equals("dr") || fieldName.equals("create_person")
                || fieldName.equals("create_time") || fieldName.equals("update_person") || fieldName.equals("update_time")){
            return true;
        }
        return false;
    }

    public static String convertFiledType(String type){
        if(type.startsWith("varchar")){
            return "String";
        }
        if(type.startsWith("bigint")){
            return "Long";
        }
        if(type.startsWith("datetime")){
            return "Date";
        }
        if(type.startsWith("decimal")){
            return "Double";
        }
        if(type.startsWith("tinyint")){
            return "int";
        }
        if(type.startsWith("int")){
            return "int";
        }
        return "unknownType";
    }

    public static String convertName(String name){
        StringBuilder newName = new StringBuilder();
        boolean upppCase = false;
         for(int i=0;i<name.length();i++){
             String  s = String.valueOf(name.charAt(i));
             if(s.equals("_")){
                 upppCase = true;
                 continue;
             }

             if(upppCase){
                 newName.append(s.toUpperCase());
                 upppCase = false;
                 continue;
             }
             newName.append(s);
         }
         return newName.toString();
    }

    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }

    public static void main(String[] args) throws Exception {
        List tables = getAllTableName();
        Map tablesComment = getCommentByTableName(tables);
        Set names = tablesComment.keySet();
        Iterator iter = names.iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            System.out.println("Table Name: " + name + ", Comment: " + tablesComment.get(name));
        }

        getColumnCommentByTableName(tables);
    }
}