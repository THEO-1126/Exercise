package cn.edu.guet.IO;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @Description:(获取视频/音频时长---使用File文件方式)
 * @author: HeShengjin
 * @date:   2021年6月23日 下午1:24:43
 * @Copyright:
 */
public class test {
    private final static String DURATION_START = "Duration:";

    private final static String KEY_FOR_HOUR = "hour";
    private final static String KEY_FOR_MINUTE = "minute";
    private final static String KEY_FOR_SECONED = "seconed";
    private final static String KEY_FOR_MILLSECONED = "millseconed";

    //小时 * 60 = 分
    //分 * 60 = 秒
    private final static BigDecimal GAP_60 = new BigDecimal("60");
    //秒* 1000 = 毫秒
    private final static BigDecimal GAP_1000 = new BigDecimal("1000");

    /**
     *  TYPE_0:小时
     *  TYPE_1:分钟
     *  TYPE_2:秒钟
     *  TYPE_3:毫秒
     */
    public final static int TYPE_0 = 0;
    public final static int TYPE_1 = 1;
    public final static int TYPE_2 = 2;
    public final static int TYPE_3 = 3;

    //ffmpeg执行命令
    private final static String file_absolute_path = "I:\\荣耀视频测试.mp4";

    public static void main(String[] args) {
        try {
            System.out.println(String.format("获取时长：%s 秒钟", duration4File(new File(file_absolute_path)).doubleValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static BigDecimal duration4File(final File file) throws Exception {
        BigDecimal duration = new BigDecimal("00");
        Map<String,String> map = null;

        Map<String,String> mapTmp = null;
        //执行ffmpeg命令

        //秒
        duration = duration.add(new BigDecimal(map.get(KEY_FOR_HOUR)).multiply(GAP_60).multiply(GAP_60))
                .add(new BigDecimal(map.get(KEY_FOR_MINUTE)).multiply(GAP_60))
                .add(new BigDecimal(map.get(KEY_FOR_SECONED)));

        return duration;
    }

}

