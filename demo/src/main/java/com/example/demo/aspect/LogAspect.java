package com.example.demo.aspect;

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

@Aspect
@Component
public class LogAspect {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置切入的包名
     */
    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void controllerMethod()
    {

    }

    @Around("controllerMethod()")
    public Object aroundControllerAdvice(ProceedingJoinPoint joinPoint)
    {
        try
        {
            return joinPoint.proceed();
        } catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }finally
        {
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
        return null;
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
