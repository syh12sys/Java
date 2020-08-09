package com.example.demo.service;

import com.example.demo.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class DynamicDataSourceApsect {
    // 切注解是 @annotation，不是 @Annotation
    @Pointcut("@annotation(com.example.demo.service.DS)")
    public void datasourcePointCut() {
    }

    @Around(value = "datasourcePointCut()")
    public Object aroundDatasourcePointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        // 前改后还
        String key = getDsAnnotation(joinPoint).value();
        DynamicDataSourceContextHolder.setKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.RemoveKey();
        }
    }

    private DS getDsAnnotation(ProceedingJoinPoint joinPoint) {
        Class<?> target = joinPoint.getTarget().getClass();
        DS dsAnnotation = target.getAnnotation(DS.class);
        if (Objects.nonNull(dsAnnotation)) {
            return dsAnnotation;
        } else {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getMethod().getAnnotation(DS.class);
        }
    }
}
