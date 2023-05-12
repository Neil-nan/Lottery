package cn.itedus.lottery.infrastructure.po;

import lombok.Data;

import java.util.Date;

/**
 * 奖品配置
 */
@Data
public class Award {

    //自增ID
    private Long id;

    //奖品ID
    private String awardId;

    //奖品类型（文字描述、1兑换码、2优惠券、3实物奖品、4暂无）
    private Integer awardType;

    //奖品数量
    private Integer awardCount;

    //奖品名称
    private String awardName;

    //奖品内容「文字描述、Key、码」'
    private String awardContent;

    //创建时间
    private Date createTime;

    //修改时间
    private Date updateTime;
}
