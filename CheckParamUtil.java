package com.anc.splen.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CheckParamUtil {
    Map<String, Object> map = new HashMap<>();

    /**
     * 添加需要檢查的參數
     * @param obj 實際參數
     * @param paramName 參數名稱
     * @return CheckParametersUtil
     */
    public CheckParamUtil put(String paramName, Object obj) {
        map.put(paramName, obj);
        return this;
    }
    /**
     * CheckParametersUtil Instance
     * @return CheckParamUtil
     */
    public static CheckParamUtil getInstance(){
        return new CheckParamUtil();
    }

    /**
     * Check Param
     * @return DataMessage
     * @throws Exception
     */
    public void checkParam() throws Exception {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            if(isEmpty(entry.getValue())){
                sb.append(String.format("[%s]",entry.getKey()));
            }
        }
        if(!isEmptyTrim(sb.toString())){
            throw new Exception("Param Name " + sb.toString() + " is Null Or Empty!!");
        }
    }

    public String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public boolean isEmpty(String string) {
        return toString(string).isEmpty();
    }

    public boolean isEmpty(Object obj) {
        return toString(obj).isEmpty();
    }

    public boolean isEmptyTrim(String string) {
        return toString(string).trim().isEmpty();
    }

    public boolean isEmptyTrim(Object obj) {
        return toString(obj).trim().isEmpty();
    }
}
