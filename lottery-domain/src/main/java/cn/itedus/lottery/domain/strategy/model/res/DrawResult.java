package cn.itedus.lottery.domain.strategy.model.res;

import lombok.Data;


public class DrawResult {

    //用户ID
    private String uId;

    //策略ID
    private Long strategyId;

    //奖品ID
    private String rewardId;

    //奖品名称
    private String rewardName;

    public DrawResult() {
    }

    public DrawResult(String uId, Long strategyId, String rewardId, String rewardName) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.rewardId = rewardId;
        this.rewardName = rewardName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }
}
