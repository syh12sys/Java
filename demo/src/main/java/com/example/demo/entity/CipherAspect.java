package com.example.demo.entity;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CipherAspect {
    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.demo.mapper.UserMapper.*(..))")
    public void ciperPointCut() {
    }

    @Around("ciperPointCut()")
    public Object aroundCiperPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object retObject = joinPoint.proceed();
            Cipher cipher = getDsAnnotation(joinPoint);
            if (cipher == null || !cipher.value().equals("decrypt")) {
                return retObject;
            }
            UserEntity userEntity = (UserEntity) retObject;
            if (userEntity == null || userEntity.getPhoneNumber() == null) {
                return retObject;
            }
            // TODO: 解密
            return userEntity;

        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @After(value = "ciperPointCut()")
    void afterCiperPointCut(JoinPoint joinPoint) {
        Cipher cipher = getDsAnnotation(joinPoint);
        if (cipher != null && cipher.value().equals("decrypt")) {
           Object[] args = joinPoint.getArgs();
           Object target = joinPoint.getTarget();
           MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
           Class<?> returnType = methodSignature.getReturnType();
           String methodName = methodSignature.getName();
           String[] parameterNames = methodSignature.getParameterNames();
           Class<?>[] parameterTypes = methodSignature.getParameterTypes();
           log.info("afterCiperPointCut");
        }
    }

    @Before(value = "ciperPointCut()")
    void beforeCiperPointCut(JoinPoint joinPoint) {
        Cipher cipher = getDsAnnotation(joinPoint);
        if (cipher == null || !cipher.value().equals("encrypt")) {
            return;
        }

        Object[] args = joinPoint.getArgs();
        if (args == null) {
            return;
        }

        UserEntity userEntity = (UserEntity) args[0];
        if (userEntity == null || userEntity.getPhoneNumber() == null) {
            return;
        }

        UserEntity newUserEntity = (UserEntity) userEntity.clone();
        if (newUserEntity == null) {
            return;
        }

        // TODO: 加密

        args[0] = newUserEntity;
    }

    private Cipher getDsAnnotation(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(Cipher.class);
    }
}
