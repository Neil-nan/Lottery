package cn.itedus.lottery.domain.strategy.model.aggregates;

import cn.itedus.lottery.infrastructure.po.Strategy;
import cn.itedus.lottery.infrastructure.po.StrategyDetail;
import lombok.Data;

import java.util.List;

public class StrategyRich {

    //策略ID
    private Long strategyId;

    //策略配置
    private Strategy strategy;

    //策略明细
    private List<StrategyDetail> strategyDetail;

    public StrategyRich() {
    }

    public StrategyRich(Long strategyId, Strategy strategy, List<StrategyDetail> strategyDetail) {
        this.strategyId = strategyId;
        this.strategy = strategy;
        this.strategyDetail = strategyDetail;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<StrategyDetail> getStrategyDetailList() {
        return strategyDetail;
    }

    public void setStrategyDetailList(List<StrategyDetail> strategyDetail) {
        this.strategyDetail = strategyDetail;
    }
}
