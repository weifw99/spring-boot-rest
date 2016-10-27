package spring.boot.rest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Mtime on 2016/10/27.
 */
@Aspect
@Component
public class RestServiceMonitor {
    private Logger logger = LoggerFactory.getLogger(RestServiceMonitor.class);

    ThreadLocal<Long> time=new ThreadLocal<Long>();
    ThreadLocal<String> tag=new ThreadLocal<String>();

    @Pointcut("execution(* spring.boot.rest..*Controller.*(..))")
    public void controllerMethod(){
        logger.info("我是一个切入点");
    }

    /**
     * 在所有标注@Log的地方切入
     * @param joinPoint
     */
    @Before("controllerMethod()") //目标方法执行前执行
    public void beforeExec(JoinPoint joinPoint){
        logger.info("RestServiceMonitor.beforeExec");
        //info(joinPoint);
        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
        Method method=ms.getMethod();
    }
    @Around("controllerMethod()")
    public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("RestServiceMonitor.aroundExec");
        Object o = pjp.proceed();
        logger.info("RestServiceMonitor.aroundExec");
        return o;
    }
    @After("controllerMethod()") //目标方法执行后执行
    public void afterExec(JoinPoint joinPoint){
        logger.info("RestServiceMonitor.afterExec");
        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
        Method method=ms.getMethod();
    }
    @AfterReturning("controllerMethod()") //目标方法返回后执行，如果发生异常不执行
    public void afterReturningExec(JoinPoint joinPoint){
        logger.info("RestServiceMonitor.afterReturningExec");
        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
        Method method=ms.getMethod();
    }
    @AfterThrowing("controllerMethod()") //异常时执行
    public void afterThrowingExec(JoinPoint joinPoint){
        logger.info("RestServiceMonitor.afterThrowingExec");
        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
        Method method=ms.getMethod();
    }


    private void info(JoinPoint joinPoint){
        logger.info("--------------------------------------------------");
        logger.info("King:\t"+joinPoint.getKind());
        logger.info("Target:\t"+joinPoint.getTarget().toString());
        Object[] os=joinPoint.getArgs();
        logger.info("Args:");
        for(int i=0;i<os.length;i++){
            logger.info("\t==>参数["+i+"]:\t"+os[i].toString());
        }
        logger.info("Signature:\t"+joinPoint.getSignature());
        logger.info("SourceLocation:\t"+joinPoint.getSourceLocation());
        logger.info("StaticPart:\t"+joinPoint.getStaticPart());
        logger.info("--------------------------------------------------");
    }
}
