package cn.edu.guet.IO;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @Description:(��ȡ��Ƶ/��Ƶʱ��---ʹ��File�ļ���ʽ)
 * @author: HeShengjin
 * @date:   2021��6��23�� ����1:24:43
 * @Copyright:
 */
public class test {
    private final static String DURATION_START = "Duration:";

    private final static String KEY_FOR_HOUR = "hour";
    private final static String KEY_FOR_MINUTE = "minute";
    private final static String KEY_FOR_SECONED = "seconed";
    private final static String KEY_FOR_MILLSECONED = "millseconed";

    //Сʱ * 60 = ��
    //�� * 60 = ��
    private final static BigDecimal GAP_60 = new BigDecimal("60");
    //��* 1000 = ����
    private final static BigDecimal GAP_1000 = new BigDecimal("1000");

    /**
     *  TYPE_0:Сʱ
     *  TYPE_1:����
     *  TYPE_2:����
     *  TYPE_3:����
     */
    public final static int TYPE_0 = 0;
    public final static int TYPE_1 = 1;
    public final static int TYPE_2 = 2;
    public final static int TYPE_3 = 3;

    //ffmpegִ������
    private final static String file_absolute_path = "I:\\��ҫ��Ƶ����.mp4";

    public static void main(String[] args) {
        try {
            System.out.println(String.format("��ȡʱ����%s ����", duration4File(new File(file_absolute_path)).doubleValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static BigDecimal duration4File(final File file) throws Exception {
        BigDecimal duration = new BigDecimal("00");
        Map<String,String> map = null;

        Map<String,String> mapTmp = null;
        //ִ��ffmpeg����

        //��
        duration = duration.add(new BigDecimal(map.get(KEY_FOR_HOUR)).multiply(GAP_60).multiply(GAP_60))
                .add(new BigDecimal(map.get(KEY_FOR_MINUTE)).multiply(GAP_60))
                .add(new BigDecimal(map.get(KEY_FOR_SECONED)));

        return duration;
    }

}

