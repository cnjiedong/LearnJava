package com.ljd.mysql;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class ObjectGenerater {

    public static Connection getMySQLConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://drds92of41bn9r2n.drds.aliyun.boc:3306/breeding_dev", "breeding_dev", "Breeding_dev123");
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
        tables.add(new String("bc_center_event"));

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
            StringBuilder eoAttr = new StringBuilder();
            StringBuilder eoGetSet = new StringBuilder();
            StringBuilder dtoAttr = new StringBuilder();
            StringBuilder dtoGetSet = new StringBuilder();
            while (rs.next()) {
                String fieldName = rs.getString("Field");
                String comment = rs.getString("Comment");
                String type = rs.getString("Type");
                System.out.println(fieldName + "\t:\t" + comment);
                //if(skipFields(fieldName,false)){
                //    continue;
               // }
                eoAttr.append("    /**").append(comment).append("*/\n");
                eoAttr.append("    ").append("@Column(name = \"").append(fieldName).append("\")\n");
                eoAttr.append("    ").append(convertFiledType(type)).append("  ").append(convertName(fieldName)).append(";\n");
                eoGetSet.append(generateGetterSetter(convertFiledType(type),convertName(fieldName)) );
                if(skipFields(fieldName,true)){
                    continue;
                }
                dtoAttr.append("    /**").append(comment).append("*/\n");
                dtoAttr.append("    ").append(convertFiledType(type,true)).append("  ").append(convertName(fieldName)).append(";\n");
                dtoGetSet.append(generateGetterSetter(convertFiledType(type, true),convertName(fieldName)) );
            }
            rs.close();
            System.out.println(eoAttr);
            FileOutputStream fEoOut = new FileOutputStream(new File("/Users/jiedong/智能繁殖/" + table + "Eo.java"));
            fEoOut.write(getClassHead(table, true).getBytes());
            fEoOut.write(eoAttr.toString().getBytes());
            fEoOut.write(eoGetSet.toString().getBytes());
            fEoOut.write("\n}".getBytes());
            fEoOut.close();

            /*FileOutputStream fDtoOut = new FileOutputStream(new File("/Users/jiedong/智能繁殖/" + table + "Dto.java"));
            fDtoOut.write(getClassHead(table, false).getBytes());
            fDtoOut.write(dtoAttr.toString().getBytes());
            fDtoOut.write(dtoGetSet.toString().getBytes());
            fDtoOut.write("\n}".getBytes());
            fDtoOut.close();*/


        }
        stmt.close();
        conn.close();

    }
    /*
import com.dtyunxi.eo.BaseEo;

import javax.persistence.Column;
import javax.persistence.Table;*/

    public static String getClassHead(String tablename, boolean eo){
        String className = tablename + (eo ? "Eo" : "Dto");
        StringBuilder classHead = new StringBuilder();
        classHead.append("package com.yingzi.center.breeding.").append(eo?"eo" : "dto").append(";\n\n");
        if(eo) {
            classHead.append("import javax.persistence.Column;\n");
            classHead.append("import javax.persistence.Table;\n");
            classHead.append("import com.dtyunxi.eo.BaseEo;\n");
        }
        else{
            classHead.append("import com.dtyunxi.vo.BaseVo;\n");
        }

        classHead.append("\n/**\n").append("* @ClassName: ").append(className).append("\n");
        classHead.append("* @Description: (XXXX设置单)\n");
        classHead.append("* @Author XXX\n");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createDate = simpleDateFormat.format(date);
        classHead.append("* @Date ").append(createDate).append("\n");
        classHead.append("* @Version V1.0\n");
        classHead.append("* @Copyright Guangzhou Shadow Holding Co.,Ltd\n").append("**/\n");

        /**
         * @ClassName: BreedingSetEo
         * @Description: (品种设置单)
         * @Author XIAN
         * @Date 2018-04-07
         * @Version V1.0
         * @Copyright Guangzhou Shadow Holding Co.,Ltd
         * */
        /*
        @Table(name = "bc_set_breeding")
        public class BreedingSetEo extends BaseEo{*/
        if(eo) {
            classHead.append("@Table(name = \"").append(tablename).append("\")\n");
        }
        classHead.append("public class ").append(className).append(" extends Base").append(eo ? "Eo{" : "Vo{").append("\n\n");
        return classHead.toString();
    }

    public static String generateGetterSetter(String type, String attr){
        StringBuilder getSet = new StringBuilder();
        String funcAttr = attr.substring(0,1).toUpperCase() + attr.substring(1,attr.length());
        getSet.append("\n    public ").append(type).append(" get").append(funcAttr).append("(){\n");
        getSet.append("        return ").append(attr).append(";\n");
        getSet.append("    }\n\n");

        getSet.append("    public void set").append(funcAttr).append("(").append(type).append(" ").append(attr).append("){\n");
        getSet.append("        this.").append(attr).append(" = ").append(attr).append(";\n");
        getSet.append("    }");
        return getSet.toString();
    }

    public static boolean skipFields(String fieldName, boolean dto){
        if(fieldName.equals("id") || fieldName.equals("tenant_id") || fieldName.equals("dr") || fieldName.equals("create_person")
                || fieldName.equals("create_time") || fieldName.equals("update_person") || fieldName.equals("update_time")
                || (dto && fieldName.equals("master_org_id"))){
            return true;
        }
        return false;
    }
    public static String convertFiledType(String type) {
        return convertFiledType(type, false);
    }

    public static String convertFiledType(String type, boolean dto ){
        if(type.startsWith("varchar") || type.startsWith("mediumtext")){
            return "String";
        }
        if(type.startsWith("bigint")){
            return "Long";
        }
        if(type.startsWith("datetime")){
            return  dto?"String":"Date";
        }
        if(type.startsWith("date")){
            return  dto?"String":"Date";
        }
        if(type.startsWith("decimal")){
            return "Double";
        }
        if(type.startsWith("tinyint")){
            return "Integer";
        }

        if(type.startsWith("smallint")){
            return "Integer";
        }
        if(type.startsWith("int")){
            String sLengh = type.replace("int","").replace("(","").replace(")","");
            int length = Integer.valueOf(sLengh);
            if(length>9){
                return "Long";
            }
            return "Integer";
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
         String sName = newName.toString();
         if(sName.endsWith("id")){
             sName = sName.substring(0,sName.length()-2) + "Id";
         }

         if(sName.endsWith("date")){
             sName = sName.substring(0,sName.length()-"date".length()) + "Date";
         }
        if(sName.endsWith("type")){
            sName = sName.substring(0,sName.length()-"type".length()) + "Type";
        }

        if(sName.endsWith("weight")){
            sName = sName.substring(0,sName.length()-"weight".length()) + "Weight";
        }
         return sName;
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

       /* List tables = getAllTableName();
        Map tablesComment = getCommentByTableName(tables);
        Set names = tablesComment.keySet();
        Iterator iter = names.iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            System.out.println("Table Name: " + name + ", Comment: " + tablesComment.get(name));
        }*/
        if(args.length<1){
            System.out.println("输入参数不足");
            return ;
        }
        System.out.println("Processs table " + args[0]);
        List tables = new ArrayList();
        tables.add(args[0]);
        getColumnCommentByTableName(tables);
    }

    public static void testTransaction(){
        try {
            Connection conn = getMySQLConnection();
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.execute("insert into tmp_test_transaction(id,note) values(2018000001, 'transaction')");

            Statement queryStmt = conn.createStatement();
            ResultSet rs =  queryStmt.executeQuery("select id,note from tmp_test_transaction");

            while(rs != null && rs.next()){
                String id = rs.getString("id");
                String note = rs.getString("note");

                //System.out.println("id:" + id + " ; note:" + note);
            }
            conn.commit();
            stmt.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}