package de.ait_tr.g_38_jp_shop.logging;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* de.ait_tr.g_38_jp_shop.service.ProductServiceImpl.*(..))")
    public void productService() {}

    @Before("productService()")
    public void productServiceInvocation(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length > 0) {
            logger.info("Method {} in ProductServiceImpl was called with parameters {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        } else {
            logger.info("Method {} in ProductServiceImpl was called without parameters", joinPoint.getSignature().getName());
        }
    }

    @After("productService()")
    public void productServiceProcessing(JoinPoint joinPoint) {
        logger.info("Method {} in ProductServiceImpl finished working", joinPoint.getSignature().getName());
    }

    @Pointcut("execution(* de.ait_tr.g_38_jp_shop.service.*.*(..))")
    public void anyService() {}

    @Before("anyService()")
    public void serviceInvocation(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length > 0) {
            logger.info("Method {} was called with parameters {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
        } else {
            logger.info("Method {} was called without parameters", joinPoint.getSignature().toShortString());
        }
    }

    @After("anyService()")
    public void serviceProcessing(JoinPoint joinPoint) {
        logger.info("Method {} finished working", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "anyService()", returning = "result")
    public void afterServiceReturningResult(JoinPoint joinPoint, Object result) {
        logger.info("Method {} successfully returned result: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "anyService()", throwing = "exception")
    public void afterServiceThrowingException(JoinPoint joinPoint, Exception exception) {
        logger.warn("Method {} threw an exception: {}",
                joinPoint.getSignature().toShortString(),
                exception.getMessage()
        );
    }

//    @Around("getAllProducts()")
//    public Object aroundGettingAllProducts(ProceedingJoinPoint joinPoint) {
//        String methodName = joinPoint.getSignature().toShortString();
//        logger.info("Method {} started", methodName);
//
//        List<ProductDto> result = null;
//
//        try {
//            result = ((List<ProductDto>) joinPoint.proceed())
//                    .stream()
//                    .filter(x -> x.getPrice().compareTo(new BigDecimal(100)) > 0)
//                    .toList();
//        } catch (Throwable e) {
//            logger.error("Method {} threw an exception: {}", methodName, e.getMessage());
//        }
//
//        logger.info("Method {} finished", methodName);
//        return result;
//    }
}
