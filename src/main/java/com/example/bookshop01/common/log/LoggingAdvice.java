package com.example.bookshop01.common.log;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAdvice {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

	// target �޼����� �Ķ���͵� ������ ����մϴ�.
	@Before("execution(* com.bookshop01.*.service.*.*(..)) or "
			+ "execution(* com.bookshop01.*.dao.*.*(..))")
	public void startLog(JoinPoint jp) {

		logger.info("-------------------------------------");
		logger.info("-------------------------------------");

		// ���޵Ǵ� ��� �Ķ���͵��� Object�� �迭�� �����ɴϴ�. 
		logger.info("1:" + Arrays.toString(jp.getArgs()));

		//�ش� Advice�� Ÿ���� �˾Ƴ��ϴ�. 
		logger.info("2:" + jp.getKind());

		// �����ϴ� ��� ��ü�� �޼ҵ忡 ���� ������ �˾Ƴ� �� ����մϴ�. 
		logger.info("3:" + jp.getSignature().getName());

		// target ��ü�� �˾Ƴ� �� ����մϴ�. 
		logger.info("4:" + jp.getTarget().toString());

		// Advice�� ���ϴ� ��ü�� �˾Ƴ� �� ����մϴ�. 
		logger.info("5:" + jp.getThis().toString());

	}
	
	@After("execution(* com.bookshop01.*.service.*.*(..)) or "
			+ "execution(* com.bookshop01.*.*.dao.*.*(..))")
	public void after(JoinPoint jp) { 
		logger.info("-------------------------------------");
		logger.info("-------------------------------------");

		// ���޵Ǵ� ��� �Ķ���͵��� Object�� �迭�� �����ɴϴ�. 
		logger.info("1:" + Arrays.toString(jp.getArgs()));

		// �ش� Advice�� Ÿ���� �˾Ƴ��ϴ�. 
		logger.info("2:" + jp.getKind());

		// �����ϴ� ��� ��ü�� �޼ҵ忡 ���� ������ �˾Ƴ� �� ����մϴ�.
		logger.info("3:" + jp.getSignature().getName());

		// target ��ü�� �˾Ƴ� �� ����մϴ�. 
		logger.info("4:" + jp.getTarget().toString());

		// Advice�� ���ϴ� ��ü�� �˾Ƴ� �� ����մϴ� 
		logger.info("5:" + jp.getThis().toString());
	
	}


	// target �޼ҵ��� ���� �ð��� �����մϴ�.
	@Around("execution(* com.bookshop01.*.service.*.*(..)) or "
			+ "execution(* com.bookshop01.*.dao.*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info(Arrays.toString(pjp.getArgs()));

		// ���� Ÿ���� �����ϴ� �κ��̴�. �� �κ��� ������ advice�� ����� �޼ҵ尡 ���������ʽ��ϴ�.
		Object result = pjp.proceed(); // proceed�� Exception ���� ���� Throwable�� ó���ؾ� �մϴ�.

		long endTime = System.currentTimeMillis();
		// target �޼ҵ��� ���� �ð��� ����Ѵ�.
		logger.info(pjp.getSignature().getName() + " : " + (endTime - startTime)); 
		logger.info("==============================");

		// Around�� ����� ��� �ݵ�� Object�� �����ؾ� �մϴ�.
		return result;
	}

}
