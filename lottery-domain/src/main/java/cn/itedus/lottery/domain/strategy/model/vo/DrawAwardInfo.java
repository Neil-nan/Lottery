package cn.itedus.lottery.domain.strategy.model.vo;

/**
 * @description: 中奖奖品信息
 * @author：Neilnan
 * @date: 2023/5/13
 * @Copyright：
 */
public class DrawAwardInfo {

    /**
     * 奖品ID
     */
    private String rewardId;

    /**
     * 奖品名称
     */
    private String awardName;

    public DrawAwardInfo() {
    }

    public DrawAwardInfo(String rewardId, String awardName) {
        this.rewardId = rewardId;
        this.awardName = awardName;
    }
}
