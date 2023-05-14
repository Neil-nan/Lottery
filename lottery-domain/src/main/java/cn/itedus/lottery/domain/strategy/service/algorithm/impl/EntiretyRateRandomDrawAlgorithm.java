package cn.itedus.lottery.domain.strategy.service.algorithm.impl;

import cn.itedus.lottery.domain.strategy.model.vo.AwardRateInfo;
import cn.itedus.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 必中奖策略抽奖，排掉已经中奖的概率，重新计算中奖范围
 * @author：Neilnan
 * @date: 2023/5/13
 * @Copyright：
 */
@Component("entiretyRateRandomDrawAlgorithm")
public class EntiretyRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {

        BigDecimal differenceDenominator = BigDecimal.ZERO;

        //删除掉不在抽奖范围的奖品ID集合
        List<AwardRateInfo> differenceAwardRateList = new ArrayList<>();
        List<AwardRateInfo> awardRateIntervalValList = awardRateInfoMap.get(strategyId);
        for(AwardRateInfo awardRateInfo : awardRateIntervalValList){
            String awardId = awardRateInfo.getAwardId();
            //去除已经抽完的奖品
            if(excludeAwardIds.contains(awardId)){
                continue;
            }
            //重新记录剩余的奖品
            differenceAwardRateList.add(awardRateInfo);
            //记录剩余中奖概率
            differenceDenominator = differenceDenominator.add(awardRateInfo.getAwardRate());
        }

        // 前置判断：奖品列表为0，返回NULL
        //都抽完了
        if(differenceAwardRateList.size() == 0){
            return null;
        }

        // 前置判断：奖品列表为1，直接返回
        if(differenceAwardRateList.size() == 1){
            return differenceAwardRateList.get(0).getAwardId();
        }

        //获取随机概率值
        int randomVal = this.generateSecureRandomIntCode(100);

        //循环获取奖品
        String awardId = null;
        int cursorVal = 0;
        for (AwardRateInfo awardRateInfo : differenceAwardRateList) {
            //第一个参数是除数，第二个参数代表保留几位小数，第三个代表的是使用的模式。(直接进位)
            int rateVal = awardRateInfo.getAwardRate().divide(differenceDenominator, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            if(randomVal <= (cursorVal + rateVal)){
                awardId = awardRateInfo.getAwardId();
                break;
            }
            cursorVal += rateVal;
        }

        //返回中奖结果
        return awardId;
    }
}
