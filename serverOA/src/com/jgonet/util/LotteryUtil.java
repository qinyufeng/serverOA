package com.jgonet.util;

import java.util.*;
import java.util.Map.Entry;
/**
 * 抽奖工具类 概率方式
 * @author zyh
 *
 */
public class LotteryUtil {
    /**
     * 抽奖
     *
     * @param originalRates
     *         原始的概率列表，保证顺序和奖品对应
     * @return
     *         奖品的索引
     */
    public static int lottery(List<Double> originalRates) {
        if (originalRates == null || originalRates.isEmpty()) {
            return -1;
        }
        int size = originalRates.size();
        // 计算总概率，总概率可能不为1
        double sumRate = 0d;
        for (double rate : originalRates) {
            sumRate += rate;
        }
        // 计算每个物品在总概率为1的基础下的概率情况
        List<Double> sortOriginalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : originalRates) {
            tempSumRate += rate;
            sortOriginalRates.add(tempSumRate / sumRate);
        }
        // 根据区块值来获取抽取到的物品索引   从0开始
        Random ran = new Random();
        double nextDouble = ran.nextDouble();
        sortOriginalRates.add(nextDouble);
        Collections.sort(sortOriginalRates);
        return sortOriginalRates.indexOf(nextDouble);
    }
    
    public static void main(String[] args) {
		List<Double>list = new ArrayList<Double>();
		list.add(0.1);
		list.add(0.2);
		list.add(0.5);
		list.add(0.2);
		for(int i = 0; i<50; i++){
			System.out.println(LotteryUtil.lottery(list));
			
		}
		
	}
}