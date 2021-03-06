package com.green.core.datasource.aop;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.green.common.enums.DatasourceEnum;
import com.green.core.datasource.DataSourceContextHolder;
import com.green.core.datasource.annotation.DataSource;

@Aspect
@Component
@Order(-1)
public class DataSourceAspect{
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Pointcut(value = "@annotation(com.green.core.datasource.annotation.DataSource)")
	private void cut() {
	}

	@Around("cut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Signature signature = point.getSignature();
		MethodSignature methodSignature = null;
		if (!(signature instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		methodSignature = (MethodSignature) signature;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(methodSignature.getName(),
				methodSignature.getParameterTypes());

		DataSource datasource = currentMethod.getAnnotation(DataSource.class);
		if (datasource != null) {
			DataSourceContextHolder.setDataSourceType(datasource.name());
			log.debug("设置数据源为：" + datasource.name());
		} else {
			DataSourceContextHolder.setDataSourceType(DatasourceEnum.DATA_SOURCE_GREEN);
			log.debug("设置数据源为：dataSourceCurrent");
		}

		try {
			return point.proceed();
		} finally {
			log.debug("清空数据源信息！");
			DataSourceContextHolder.clearDataSourceType();
		}
	}	
}
