package com.luv2code.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

	// add a new advice for @AfterReturning on the findAccounts methods
	
	@AfterReturning(
			pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
			returning="result")
	public void afterReturningFindAccountsAdvice(
			JoinPoint theJoinPoint, List<Account> result
			) {
		
		// print witch method we are advising on
		String method =theJoinPoint.getSignature().toShortString();
		System.out.println("\n======>>>>> Executing @AfterReturning on method: "
				+method);
		
		// print out the result of the method call
		System.out.println("\n======>>>>> result is: " + result);
		
		// let's post-process the data ... let's modify it: :-)
		
		// convert the account names to uppercase
		convertAccountNamesToUpperCase(result);
		
	}
	
	private void convertAccountNamesToUpperCase(List<Account> result) {
		
		// loop through accounts
		for (Account theAccount:result) {
		
		// get the upper case version of names
		String theUpperName=theAccount.getName().toUpperCase();
			
		// update the name on the account
		theAccount.setName(theUpperName);
		
		}
		System.out.println("\n======>>>>> result is: " + result);
	}

	@Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
		
		System.out.println("\n=======>>>> Executing @Before advice on method");
		
		// display the method signature 
		MethodSignature methodSig=(MethodSignature) theJoinPoint.getSignature();
		
		System.out.println("Method: "+methodSig);
		
		// display method arguments
		
		// get args
		Object[] args=theJoinPoint.getArgs();
		
		// loop thru args
		for (Object tempArg:args) {
			
			System.out.println(tempArg);
			
			if (tempArg instanceof Account) {
				
				Account theAccount=(Account) tempArg;
				System.out.println("account name: "+ theAccount.getName());
				System.out.println("account name: "+ theAccount.getLevel());
			}
		}
		
	}
	
	

	
}
