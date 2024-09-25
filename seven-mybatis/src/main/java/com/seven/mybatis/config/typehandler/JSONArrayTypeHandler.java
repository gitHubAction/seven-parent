package com.seven.mybatis.config.typehandler;

import com.alibaba.fastjson.JSONArray;

/**
 * @author shanhonghao
 * @date 2018-10-25 09:30
 */
public class JSONArrayTypeHandler extends JSONTypeHandler<JSONArray> {

    public JSONArrayTypeHandler(Class<JSONArray> type) {
        super(type);
    }

    @Override
    public JSONArray toObject(String jsonString) {
        return JSONArray.parseArray(jsonString);
    }

}
