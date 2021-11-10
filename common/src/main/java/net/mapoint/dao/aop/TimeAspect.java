package net.mapoint.dao.aop;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import net.mapoint.dao.entity.CreatedUpdatedAt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TimeAspect {

    @Before("execution (* net.mapoint.dao.*.add(..))")
    public void setCreatedAndUpdatedTime(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        processArgs(args, true);
    }

    @Before("execution (* net.mapoint.dao.*.addAll(..))")
    public void setCreatedAndUpdatedTimeForAll(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            Collection collection = (Collection) arg;
            processArgs(collection.toArray(), true);
        }
    }

    @Before("execution (* net.mapoint.dao.*.update*(..))")
    public void setUpdatedTime(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        processArgs(args, false);
    }

    private void processArgs(Object[] args, boolean isCreated) {
        for (Object obj : args) {
            if (obj instanceof CreatedUpdatedAt) {
                CreatedUpdatedAt at = (CreatedUpdatedAt) obj;
                if (isCreated) {
                    at.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                }
                at.setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            }
        }
    }
}