package com.logrecord.parser;

import lombok.Data;
import org.springframework.expression.ParserContext;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author yeyu
 * @since 2022-05-10 14:17
 * SpEL解析器
 */
@Data
public class LogParser {
    private final LogRecordEvaluation logRecordEvaluation = new LogRecordEvaluation();

    private final ParserContext parserContext = ParserContext.TEMPLATE_EXPRESSION;

    public Object parse(String expression,LogEvaluationContext context,LogRootObject rootObject) {
        return logRecordEvaluation.parseExpression(rootObject.getMethod(),rootObject.getTargetClass(),
                expression,context);
    }

    public Object parseTemplate(String expression,LogEvaluationContext context) {
        return logRecordEvaluation.parseExpression(expression,context);
    }

    public void refreshContext(LogEvaluationContext context) {
        Map<String, Object> vars = LogRecordContext.getVars();
        if (!CollectionUtils.isEmpty(vars)) {
            context.setVariables(vars);
        }
    }

}
