package cn.itedus.lottery.rpc.res;

import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.rpc.dto.ActivityDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityRes implements Serializable {

    private Result result;
    private ActivityDto activity;

    public ActivityRes(Result result, ActivityDto activity) {
        this.result = result;
        this.activity = activity;
    }
}
