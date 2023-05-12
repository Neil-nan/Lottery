package cn.itedus.lottery.infrastructure.dao;

import cn.itedus.lottery.infrastructure.po.StrategyDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyDetailDao {

    void addStrategyDetail(StrategyDetail strategyDetail);

    List<StrategyDetail> queryStrategyDetailList(Long strategyId);
}
