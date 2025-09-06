package org.example.shortlink.admin.toolkit;

import java.security.SecureRandom;

/**
 * 分组ID6位随机生成器
 */
public final class RandomGenerator {
    // 定义字符集：包含数字和大小写英文字母
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // 使用安全的随机数生成器
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成6位包含数字与英文字母的随机字符串
     * @return 6位随机字符串
     */
    public static String generateRandom() {
        // 定义结果字符串的长度为6
        int length = 6;
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 从字符集中随机选择一个字符的索引
            int index = RANDOM.nextInt(CHARACTERS.length());
            // 将选中的字符添加到结果中
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
}
