package com.xteam.ggq.model.dbrouting;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(0)
public class CrmAspect {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Around("@annotation(read) &amp;&amp; execution(* com.xteam.ggq.model.service.*.*(..))")
    public Object aroundRead(ProceedingJoinPoint pjp, DBRead read) throws Throwable {

        String origType = ContextHolder.getDataSourceType();
        try {
            ContextHolder.setDataSourceType(ContextHolder.ConnectionType.READ);
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.warn("error while processing read method", throwable);
            throw throwable;
        } finally {
            if (origType != null) {
                ContextHolder.setDataSourceType(origType);
            } else {
                ContextHolder.release();
            }
        }
    }

    @Around("@annotation(write) &amp;&amp; execution(* com.xteam.ggq.model.service.*.*(..))")
    public Object aroundWrite(ProceedingJoinPoint pjp, DBWrite write) throws Throwable {

        String origType = ContextHolder.getDataSourceType();
        try {
            ContextHolder.setDataSourceType(ContextHolder.ConnectionType.READ_WRITE);
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.warn("error while processing write method", throwable);
            throw throwable;
        } finally {
            if (origType != null) {
                ContextHolder.setDataSourceType(origType);
            } else {
                ContextHolder.release();
            }
        }
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional) &amp;&amp; execution(* com.xteam.ggq.model.service.*.*(..))")
    public Object aroundTransactional(ProceedingJoinPoint pjp) throws Throwable {
        return aroundWrite(pjp, null);
    }

}
