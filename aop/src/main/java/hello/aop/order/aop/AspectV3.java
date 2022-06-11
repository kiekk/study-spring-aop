package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution (* hello.aop.order..*(..))")
    private void allOrder() {
    }  // pointcut signature

    // 클래스 이름이 *Service
    @Pointcut("execution (* *..*Service.*(..))")
    private void allService() {
    }

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[log] {}", proceedingJoinPoint.getSignature());   // join point signature

        return proceedingJoinPoint.proceed();
    }

    // hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", proceedingJoinPoint.getSignature());
            Object result = proceedingJoinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", proceedingJoinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", proceedingJoinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", proceedingJoinPoint.getSignature());
        }
    }

}
