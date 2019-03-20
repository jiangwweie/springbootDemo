package com.jiangwei.test.io;

import java.io.*;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/30
 */
public class Test {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\jiangwei\\Desktop\\1.csv");
        File file2 = new File("C:\\Users\\jiangwei\\Desktop\\一体机device插入sql文件.txt");
        File file3 = new File("C:\\Users\\jiangwei\\Desktop\\一体机device对接sql文件.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file3));

        String line ;
        int i = 71 ;
        int j = 19 ;
        while((line = br.readLine())!=null){
            String[] strings = line.split(",");
            String code1 = strings[0];
            code1 = code1.replace(" ","");
            String code2 = strings[1];
            code2 = code2.replace(" ","");
            String sql = "UPDATE `ytj`.`t_device` SET `code1`='"+code1+"', `code2`='"+code2+"' WHERE `id`='"+(i++)+"';";
            String sql2 = "INSERT INTO `ytj`.`t_docking` (`id`, `machine_code1`, `machine_code2`, `project_id` ) VALUES ('"+(j++)+"', '"+code1+"', '"+code2+"', '17102001');";
            System.out.println(sql);
            bw.write(sql);
            bw.newLine();
            bw2.write(sql2);
            bw2.newLine();
        }
        br.close();
        bw.flush();
        bw.close();
        bw2.flush();
        bw2.close();
    }
}
