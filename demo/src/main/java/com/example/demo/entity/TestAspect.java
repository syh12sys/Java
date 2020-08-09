package com.example.demo.entity;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

//@Aspect
@Component
// 切面是一个编程层面的分离系统代码和业务代码的手段
// 利用切面捕获service所有的异常并打印，之后异常要继续向外抛，否则会影响异常的正常处理流程，例如事物功能
public class TestAspect {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置切入的包名
     */
    @Pointcut("execution(* com.example.demo.mapper.*.*(..))")
    public void controllerMethod()
    {
    }

    @Around("controllerMethod()")
    public Object aroundControllerAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            try {
                Signature signature = joinPoint.getSignature();
                if (signature != null) {
                    String clazzName = signature.getDeclaringTypeName();
                    String method = signature.getName();
                    log.info(clazzName,method);
                }
            } catch (Exception e) {
                log.error("aroundControllerAdvice", e);
            }
        }
    }

    @AfterThrowing(value = "controllerMethod()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception){
        Signature signature = joinPoint.getSignature();
        if (signature != null)
        {
            String clazzName = signature.getDeclaringTypeName();
            String method = signature.getName();
            String param =  methodParams(joinPoint, signature);
            log.error("{}.{}.({})",clazzName,method,param,exception);
        }
    }

    private String methodParams(JoinPoint pjp, Signature signature) {
        StringBuffer sb = new StringBuffer();
        try {
            CodeSignature cs = (CodeSignature) signature;
            String[] paramNames = cs.getParameterNames();
            Object[] paramValues = pjp.getArgs();

            for (int i = 0; i < paramNames.length; i++) {
                sb.append(paramNames[i]).append("=").append(toStr(paramValues[i]));
                if (i < paramNames.length - 1) {
                    sb.append(", ");
                }
            }
        } catch (Exception e) {
            log.error("error when generate method param info", e);
        }
        return sb.toString();
    }

    private String toStr(Object o) {
        if (o == null)
        {
            return "nil";
        }
        return o.toString();
    }
}
