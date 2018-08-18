package com.adpanshi.cashloan.core.common.util;

import java.util.Random;

/**
 * @Description: 生成各种随机数工具类
 * @author ppchen
 * @date  2017年8月9日 下午5:23:32
 *
 */
public class RandomUtil {
	
	
	/**
	 * 生成随机邀请码
	 * @param len 长度
	 * @return
	 */
	public static String randomNumAlph(int len) {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        byte[][] list = {
            {48, 57},
            {97, 122},
            {65, 90}
        };
        for (int i = 0; i < len; i++) {
            byte[] o = list[random.nextInt(list.length)];
            byte value = (byte) (random.nextInt(o[1] - o[0] + 1) + o[0]);
            sb.append((char) value);
        }
        return sb.toString();
    }
}
