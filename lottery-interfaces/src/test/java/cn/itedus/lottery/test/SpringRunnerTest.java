package cn.itedus.lottery.test;

import cn.itedus.lottery.domain.strategy.model.req.DrawReq;
import cn.itedus.lottery.domain.strategy.service.draw.IDrawExec;
import cn.itedus.lottery.infrastructure.dao.IActivityDao;
import cn.itedus.lottery.infrastructure.dao.IAwardDao;
import cn.itedus.lottery.infrastructure.dao.IStrategyDao;
import cn.itedus.lottery.infrastructure.dao.IStrategyDetailDao;
import cn.itedus.lottery.infrastructure.po.Activity;
import cn.itedus.lottery.infrastructure.po.Award;
import cn.itedus.lottery.infrastructure.po.Strategy;
import cn.itedus.lottery.infrastructure.po.StrategyDetail;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringRunnerTest {

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IAwardDao awardDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IDrawExec drawExec;

    @Test
    public void test_drawExec(){
        drawExec.doDrawExec(new DrawReq("小傅哥", 100001L));
        drawExec.doDrawExec(new DrawReq("小佳佳", 100001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 100001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 100001L));
    }

    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100001L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("neilnan");
        activityDao.insert(activity);
    }

    @Test
    public void test_insert_strategy(){
        Strategy strategy = new Strategy();
        strategy.setStrategyId(100001L);
        strategy.setStrategyDesc("neil用于测试的策略");
        strategy.setStrategyMode(1);
        strategy.setGrantType(3);
        strategy.setGrantDate(new Date());
        strategy.setExtInfo("null");
        strategyDao.addStrategy(strategy);

    }

    @Test
    public void test_insert_award(){
        Award award = new Award();
//        award.setAwardId("10001");
//        award.setAwardType(3);
//        award.setAwardCount(3);
//        award.setAwardName("彩电");
//        award.setAwardContent("null");

//        award.setAwardId("10002");
//        award.setAwardType(3);
//        award.setAwardCount(5);
//        award.setAwardName("冰箱");
//        award.setAwardContent("null");

        award.setAwardId("10003");
        award.setAwardType(3);
        award.setAwardCount(10);
        award.setAwardName("洗衣机");
        award.setAwardContent("null");

        awardDao.addAward(award);
    }

    @Test
    public void test_insert_strategyDetail(){
        StrategyDetail strategyDetail = new StrategyDetail();
//        strategyDetail.setStrategyId(100001L);
//        strategyDetail.setAwardId("10001");
//        strategyDetail.setAwardCount("3");
//        strategyDetail.setAwardRate(BigDecimal.valueOf(20));

//        strategyDetail.setStrategyId(100001L);
//        strategyDetail.setAwardId("10002");
//        strategyDetail.setAwardCount("5");
//        strategyDetail.setAwardRate(BigDecimal.valueOf(30));

        strategyDetail.setStrategyId(100001L);
        strategyDetail.setAwardId("10003");
        strategyDetail.setAwardCount("10");
        strategyDetail.setAwardRate(BigDecimal.valueOf(50));

        strategyDetailDao.addStrategyDetail(strategyDetail);
    }

    @Test
    public void test_select_strategyDetailList(){
        List<StrategyDetail> strategyDetailList = strategyDetailDao.queryStrategyDetailList(100001L);
        for (StrategyDetail strategyDetail : strategyDetailList) {
            log.info("测试结果：{}", JSON.toJSONString(strategyDetail));
        }
    }

    @Test
    public void test_select() {
        Activity activity = activityDao.queryActivityById(100001L);
        log.info("测试结果：{}", JSON.toJSONString(activity));
    }
}
