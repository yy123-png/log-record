package com.logrecord.parser;

import lombok.Data;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author yeyu
 * @since 2022-05-10 14:17
 * SpEL解析器
 */
@Data
public class LogParser {
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final ParserContext parserContext = ParserContext.TEMPLATE_EXPRESSION;

    public Object parse(String expression,LogEvaluationContext context) {
        return spelExpressionParser.parseExpression(expression).getValue(context);
    }

    public Object parseTemplate(String expression,LogEvaluationContext context) {
        return spelExpressionParser.parseExpression(expression,parserContext).getValue(context);
    }

    public void refreshContext(LogEvaluationContext context) {
        Map<String, Object> vars = LogRecordContext.getVars();
        if (!CollectionUtils.isEmpty(vars)) {
            context.setVariables(vars);
        }
    }

}
