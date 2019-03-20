package com.jiangwei.test.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/30
 */
public class Test2 {

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\Administrator\\Tencent Files\\415313909\\FileRecv\\MobileFile\\user.csv");
        File file2 = new File("C:\\Users\\jiangwei\\Desktop\\user.csv");

        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(file2));

        String line ;
        int i = 0 ;
        while((line = br.readLine())!=null){
            if(i>0){
                String[] strings = line.split(",");
                //第三个参数是时间
                String addTime = strings[2];
                long time = Long.parseLong(addTime) * 1000;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                //秒转为时间
                String format = sdf.format(new Date(time));
                System.out.println(format);
                //将毫秒数替换为格式化后的时间
                String replace = line.replace(addTime, format);
                //写出
                bw.write(replace);
                bw.newLine();
            }else{
                bw.write(line);
                bw.newLine();
            }
            i++ ;
        }
        br.close();
        bw.flush();
        bw.close();
    }
}
