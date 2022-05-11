package com.logrecord.component;

import com.logrecord.TestParam;
import com.logrecord.service.IQueryOldService;
import org.springframework.stereotype.Component;

/**
 * @author yeyu
 * @since 2022-05-11 14:50
 */
@Component
public class TestQueryOldService implements IQueryOldService {
    @Override
    public String key() {
        return "TEST";
    }

    @Override
    public Object apply(Object param) {
        System.out.println("apply query old method..");
        TestParam oldParam = new TestParam();
        oldParam.setName("abc");
        return oldParam;
    }
}
