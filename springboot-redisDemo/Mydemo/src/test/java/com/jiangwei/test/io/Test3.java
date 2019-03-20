package com.jiangwei.test.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/13
 */
public class Test3 {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\jiangwei\\Desktop\\new.csv");

        File file2 = new File("C:\\Users\\jiangwei\\Desktop\\distinct.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Set set = new HashSet();

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            line = line.trim();
            set.add(line);
        }
        br.close();
        System.out.println(set.size());
        String sql = "insert into storedemo.t_member_source (uid,source ,addtime) values  ";
        StringBuffer sb = new StringBuffer(sql);


    }
}
