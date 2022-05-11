package com.logrecord.aop;

import com.alibaba.fastjson.JSON;
import com.logrecord.annotation.LogRecordAnnotation;
import com.logrecord.parser.LogEvaluationContext;
import com.logrecord.parser.LogParser;
import com.logrecord.parser.LogRecordContext;
import com.logrecord.parser.LogRootObject;
import com.logrecord.service.IPersistenceService;
import com.logrecord.service.QueryOldExecutor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author yeyu
 * @since 2022-05-09 15:31
 * 日志切面
 */
@Aspect
public class LogAspect {

    @Resource
    private LogParser logParser;

    @Resource
    private IPersistenceService logPersistenceService;

    @Resource
    private QueryOldExecutor queryOldExecutor;

    @Pointcut("@annotation(com.logrecord.annotation.LogRecordAnnotation)")
    public void logAspect() {
    }

    private void doBefore(Method method, Class<?> targetClass, Object[] args) {
        if (args.length == 1) {
            LogRecordContext.putVars(LogRecordContext.PARAMS, args[0]);
        } else {
            LogRecordContext.putVars(LogRecordContext.PARAMS, args);
        }

        LogRootObject rootObject = new LogRootObject(method, targetClass, args);
        LogRecordContext.putVars(LogRecordContext.ROOT_OBJECT, rootObject);

        LogEvaluationContext context = new LogEvaluationContext(rootObject);

        LogRecordAnnotation annotation = method.getAnnotation(LogRecordAnnotation.class);

        if (annotation.needOld()) {
            // 需要查询旧数据
            Object actualBizNo = logParser.parse(annotation.bizNo(), context,rootObject);
            LogRecordContext.putVars(LogRecordContext.BIZ_NO, actualBizNo);

            Object oldData = queryOldExecutor.execute(annotation.serviceKey(), actualBizNo);
            LogRecordContext.putVars(LogRecordContext.OLD_DATA, oldData);
            // 刷新context以便SpEL在解析时能够使用oldData
            logParser.refreshContext(context);
        }
        // 解析日志content并保存
        Object content = logParser.parseTemplate(annotation.content(), context);
        LogRecordContext.putVars(LogRecordContext.CONTENT, content);
    }

    @Around(value = "logAspect()", argNames = "point")
    public Object logAround(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = point.getTarget().getClass();
        Object[] args = point.getArgs();
        doBefore(method, targetClass, args);
        Object result;
        try {
            long start = System.currentTimeMillis();
            result = point.proceed(args);
            LogRecordContext.putVars(LogRecordContext.RESULT, JSON.toJSONString(result));
            long end = System.currentTimeMillis();

            LogRecordContext.putVars(LogRecordContext.COST, end - start);
        } catch (Throwable e) {
            // 保存异常信息
            LogRecordContext.putVars(LogRecordContext.EXCEPTION, e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            // 存储日志
            logPersistenceService.saveLog();
            // 清空内容
            LogRecordContext.clear();
        }
        return result;
    }


}
