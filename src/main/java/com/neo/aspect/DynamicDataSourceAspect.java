package com.neo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.neo.route.RoutingDataSourceContext;

@Aspect
@Component
public class DynamicDataSourceAspect {

	@Pointcut("execution(* com.neo.service.*.*(..))")
	public void pointCut() {

	}

	@After(value = "pointCut()") // 后置通知
	public void testAfter(JoinPoint point) {
		// 获得当前访问的class
		Class<?> className = point.getTarget().getClass();
		// 获得访问的方法名
		String methodName = point.getSignature().getName();
		// 得到方法的参数的类型
		Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
		System.out.println("after pointcut: " + className + " | " + methodName + " | " + argClass);
		RoutingDataSourceContext.clearDBType();
	}

}
