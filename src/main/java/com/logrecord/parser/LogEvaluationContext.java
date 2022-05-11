package com.logrecord.parser;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author yeyu
 * @since 2022-05-10 14:17
 * SpEL解析上下文
 */
public class LogEvaluationContext extends MethodBasedEvaluationContext {

    public LogEvaluationContext(LogRootObject rootObject, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, rootObject.getMethod(), rootObject.getArgs(), parameterNameDiscoverer);
        Map<String, Object> vars = LogRecordContext.getVars();
        if (!CollectionUtils.isEmpty(vars)) {
            this.setVariables(vars);
        }
    }
}
