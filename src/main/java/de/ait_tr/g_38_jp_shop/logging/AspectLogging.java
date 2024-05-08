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

    @Pointcut("execution(* de.ait_tr.g_38_jp_shop.service.ProductServiceImpl.save(..))")
    public void saveProduct() {}

    @Before("saveProduct()")
    public void beforeSavingProduct(JoinPoint joinPoint) {
        logger.info("Method {} called with parameter {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @After("saveProduct()")
    public void afterSavingProduct() {
        logger.info("Method save of the class ProductServiceImpl finished its work");
    }

    @Pointcut("execution(* de.ait_tr.g_38_jp_shop.service.ProductServiceImpl.getById(..))")
    public void getProductById() {}

    @AfterReturning(pointcut = "getProductById()", returning = "result")
    public void afterReturningProductById(Object result) {
        logger.info("Method getById of the class ProductServiceImpl successfully returned result: {}", result);
    }

    @AfterThrowing(pointcut = "getProductById()", throwing = "exception")
    public void afterThrowingExceptionWhileGettingProductById(Exception exception) {
        logger.warn("Method getById of the class ProductServiceImpl threw an exception: {}", exception.getMessage());
    }

    @Around("getAllProducts()")
    public Object aroundGettingAllProducts(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Method {} started", methodName);

        List<ProductDto> result = null;

        try {
            result = ((List<ProductDto>) joinPoint.proceed())
                    .stream()
                    .filter(x -> x.getPrice().compareTo(new BigDecimal(100)) > 0)
                    .toList();
        } catch (Throwable e) {
            logger.error("Method {} threw an exception: {}", methodName, e.getMessage());
        }

        logger.info("Method {} finished", methodName);
        return result;
    }
}
