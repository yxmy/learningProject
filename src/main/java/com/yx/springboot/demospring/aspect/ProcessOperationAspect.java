package com.yx.springboot.demospring.aspect;

import com.yx.springboot.demospring.annotation.ProcessLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author yuanxin
 * @date 2021/4/17
 */
@Aspect
@Component
public class ProcessOperationAspect {

    @Pointcut("execution(* com.yx.springboot.demospring.controller.DemoController.*(..))")
    public void log() {
    }

    @Before("log()&&@annotation(processLock)")
    public Object doBefore(JoinPoint joinPoint, ProcessLock processLock) throws Exception {
        System.out.println("******拦截前的逻辑******");
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());

        System.out.println("拦截的注解的参数：");
        System.out.println(Arrays.toString(processLock.lockParam()));
        System.out.println(processLock.execMode());

        return "index111";
    }

    @Around("log()&&@annotation(processLock)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, ProcessLock processLock) throws Throwable {
        System.out.println("环绕通知：");
        System.out.println(Arrays.toString(processLock.lockParam()));
        System.out.println(processLock.execMode());
        Object result;
        result = proceedingJoinPoint.proceed();
        return result;
    }

    @After("log()")
    public void doAfter() {
        System.out.println("******拦截后的逻辑******");
    }

}
