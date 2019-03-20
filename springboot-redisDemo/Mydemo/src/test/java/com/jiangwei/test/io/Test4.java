package com.jiangwei.test.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/13
 */
public class Test4 {

    public static void main(String[] args) throws IOException {
        String sql = "SELECT  DISTINCT(a.uid) FROM ( SELECT uid FROM  `t_measure_bloodfat`  WHERE machine_code = '4F9UID03F09E' UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_blooduricacid`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_bodyfat`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_electrocardio`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_height`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_height_weight`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_hemoglobin`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_pulmonary`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_pulse`  WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_urinalysis` WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_measure_weight` WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_physiology_glucose` WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_physiology_oxygen` WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_physiology_pressure` WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM  `t_physiology_rate` WHERE machine_code = '4F9UID03F09E') UNION ALL\n" +
                "(SELECT uid FROM `t_physiology_temperature`  WHERE machine_code = '4F9UID03F09E')) a";

        sql = sql.replace("=", "in");
        String re = " ('ZZJ05A1809210012','ZZJ05A1809210013','ZZJ05A1809210014','ZZJ05A1809210015','ZZJ05A1809210016','ZZJ05A1809210018','ZZJ05A1809210020','ZZJ05A1809210022','ZZJ05A1809210027','ZZJ05A1809210028') ";
        sql = sql.replace("'4F9UID03F09E'", re);
        System.out.println(sql);

        File file = new File("C:\\Users\\jiangwei\\Desktop\\sql.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(sql);
        bw.flush();
        bw.close();


    }
}
