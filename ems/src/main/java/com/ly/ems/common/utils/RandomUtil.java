package com.ly.ems.common.utils;

import java.util.Random;

/**
 * Created by tidus on 2018/10/1.
 */
public class RandomUtil {

    /**
     * 根据传入的中间概率，判断是否中奖
     *
     * @param lotteryPercent 0.00~100.00 中奖百分比
     * @return
     */
    public static boolean lottery(Float lotteryPercent) {
        if (lotteryPercent == 100.00f) {
            return true;
        }
        Random random = new Random();
        Float lotteryResult = random.nextFloat() * 100;
        return lotteryResult <= lotteryPercent;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 30; i++) {
            System.out.println(lottery(30.00f) ? "中奖了" : "没中奖");
        }
    }
}
