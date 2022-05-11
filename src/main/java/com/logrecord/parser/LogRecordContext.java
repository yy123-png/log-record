package com.logrecord.parser;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yeyu
 * @since 2022-05-10 14:48
 * 日志线程上下文 存储必要信息
 */
@Data
public class LogRecordContext {

    public static final String PARAMS = "params";

    public static final String ROOT_OBJECT = "rootObject";

    public static final String BIZ_NO = "bizNo";

    public static final String OLD_DATA = "oldData";

    public static final String CONTENT = "content";

    public static final String RESULT = "result";

    public static final String COST = "cost";

    public static final String EXCEPTION = "exception";

    private final static TransmittableThreadLocal<Map<String,Object>> vars = new TransmittableThreadLocal<>();

    public static void putVars(String name,Object object) {
        Map<String, Object> stringObjectMap = vars.get();
        if (CollectionUtils.isEmpty(stringObjectMap)) {
            stringObjectMap = new HashMap<>();
            stringObjectMap.put(name,object);
            vars.set(stringObjectMap);
        } else {
            stringObjectMap.put(name,object);
        }
    }

    public static Map<String,Object> getVars() {
        return vars.get();
    }

    public static void clear() {
        vars.remove();
    }
}
