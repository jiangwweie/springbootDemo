package com.jiangwei.test.io;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/13
 */
public class Test5 {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\jiangwei\\Desktop\\allUid.csv");

        File file2 = new File("C:\\Users\\jiangwei\\Desktop\\insetSql.txt");
        File file3 = new File("C:\\Users\\jiangwei\\Desktop\\allUid.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file3));
        String sql = "insert into storedemo.t_member_source (uid,source ,addtime) values  ";
        StringBuffer sb = new StringBuffer(sql);
        bw.write(sb.toString());
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            line = line.trim();
            bw2.write(line+", ");

            line  = " ( "+line+", '17102001' , '1542085992' ), ";
            bw.write(line);
            bw.newLine();
        }
        br.close();
        bw.flush();
        bw.close();
        bw2.flush();
        bw2.close();



    }
}
