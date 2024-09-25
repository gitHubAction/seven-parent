package com.seven.mybatis.config.typehandler;

import com.alibaba.fastjson.JSONObject;

/**
 * @author shanhonghao
 * @date 2018-10-25 09:32
 */
public class JSONObjectTypeHandler extends JSONTypeHandler<JSONObject> {

    public JSONObjectTypeHandler(Class<JSONObject> type) {
        super(type);
    }

    @Override
    public JSONObject toObject(String jsonString) {
        return JSONObject.parseObject(jsonString);
    }

}
