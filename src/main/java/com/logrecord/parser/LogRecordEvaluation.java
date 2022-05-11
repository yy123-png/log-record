package com.logrecord.parser;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yeyu
 * @since 2022-05-11 15:37
 */
public class LogRecordEvaluation extends CachedExpressionEvaluator {

    private final ParserContext parserContext = ParserContext.TEMPLATE_EXPRESSION;

    private final Map<ExpressionKey, Expression> cache = new ConcurrentHashMap<>(16);

    public String parseExpression(Method method, Class<?> targetClass, String expression, EvaluationContext context) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method,targetClass);
        return getExpression(cache, methodKey, expression).getValue(context, String.class);
    }

    public String parseExpression(String expression, EvaluationContext context) {
        return getParser().parseExpression(expression, parserContext).getValue(context, String.class);
    }

}
