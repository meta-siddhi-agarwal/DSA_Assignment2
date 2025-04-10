package pac;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class EvaluateInfixExpression {

	static Scanner scannerObject = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			String infixExpression = getExpressionFromUser();
			if (infixExpression.length() == 0)
				throw new Exception();
			String infixArray[] = tokenizeExpression(infixExpression);

			int value = evaluateInfixExp(infixArray);
			if (value == -1)
				System.out.println("Please enter valid arithmetic infix expression");
			else
				System.out.println("Result of infix expression is " + value);

		} catch (ArithmeticException e) {
			System.out.println("Cannot divide by zero");
		} catch (Exception e) {
			if (e.getMessage() != null)
				System.out.println(e.getMessage());
			else
				System.out.println("Please enter valid arithmetic infix expression");
		}
	}

	/**
	 * function will convert infix expression to tokenized array
	 * 
	 * @param expression->provided by the user
	 * @return->tokenized string array
	 */
	static public String[] tokenizeExpression(String expression) {

		String infixArray[] = expression.split("");
		String tokenizedArray[] = new String[infixArray.length + 2];
		tokenizedArray[0] = "(";
		tokenizedArray[tokenizedArray.length - 1] = ")";

		for (int tokenizedArrayIndex = 1; tokenizedArrayIndex < tokenizedArray.length - 1; tokenizedArrayIndex++) {
			tokenizedArray[tokenizedArrayIndex] = infixArray[tokenizedArrayIndex - 1];
		}
		return tokenizedArray;
	}

	/**
	 * function wil evaluate infix expression
	 * 
	 * @param expressionArray->tokenized infix array
	 * @throws Exception ->in case of invalid infix expression
	 * @return->value of the infix expression
	 */
	static public int evaluateInfixExp(String expressionArray[]) throws Exception {
		Stack<String> operatorStack = new Stack<>();
		Stack<String> operandStack = new Stack<>();

		for (int expressionArrayIndex = 0; expressionArrayIndex < expressionArray.length; expressionArrayIndex++) {

			if ("(".equals(expressionArray[expressionArrayIndex])) {
				operatorStack.push(expressionArray[expressionArrayIndex]);
			} else if (")".equals(expressionArray[expressionArrayIndex])) {

				if (isOperator(operatorStack.peek())) {
					do {
						String operator = operatorStack.pop();
						String operand1 = operandStack.pop();
						String operand2 = operandStack.pop();

						int value = calculateValue(operand2, operand1, operator);
						operandStack.push(value + "");
					} while (isOperator(operatorStack.peek()));
				}
				operatorStack.pop();
			} else if (!(isOperator(expressionArray[expressionArrayIndex]))) {
				String numberString = "";
				int number = Integer.parseInt(expressionArray[expressionArrayIndex]);
				while (expressionArrayIndex < expressionArray.length && number >= 0 && number <= 9) {

					numberString += expressionArray[expressionArrayIndex];
					expressionArrayIndex++;
					if (!(isOperator(expressionArray[expressionArrayIndex]))
							&& !(expressionArray[expressionArrayIndex].equals("("))
							&& !(expressionArray[expressionArrayIndex].equals(")")))
						number = Integer.parseInt(expressionArray[expressionArrayIndex]);
					else {
						expressionArrayIndex--;
						break;
					}
				}
				operandStack.push(numberString);
			} else if ((isOperator(expressionArray[expressionArrayIndex]))) {
				String firstOperator = expressionArray[expressionArrayIndex] + "";

				if ("<".equals(expressionArray[expressionArrayIndex])
						&& expressionArrayIndex < expressionArray.length - 1
						&& "=".equals(expressionArray[expressionArrayIndex + 1])) {
					firstOperator += expressionArray[expressionArrayIndex + 1];
					expressionArrayIndex++;
				} else if (">".equals(expressionArray[expressionArrayIndex])
						&& expressionArrayIndex < expressionArray.length - 1
						&& "=".equals(expressionArray[expressionArrayIndex + 1])) {
					firstOperator += expressionArray[expressionArrayIndex + 1];
					expressionArrayIndex++;
				} else if ("=".equals(expressionArray[expressionArrayIndex])
						&& expressionArrayIndex < expressionArray.length - 1
						&& "=".equals(expressionArray[expressionArrayIndex + 1])) {
					firstOperator += expressionArray[expressionArrayIndex + 1] + "";
					expressionArrayIndex++;
				} else if ("!".equals(expressionArray[expressionArrayIndex])
						&& expressionArrayIndex < expressionArray.length - 1
						&& "=".equals(expressionArray[expressionArrayIndex + 1])) {
					firstOperator += expressionArray[expressionArrayIndex + 1];
					expressionArrayIndex++;
				} else if ("&".equals(expressionArray[expressionArrayIndex])
						&& expressionArrayIndex < expressionArray.length - 1
						&& "&".equals(expressionArray[expressionArrayIndex + 1])) {
					firstOperator += expressionArray[expressionArrayIndex + 1];
					expressionArrayIndex++;
				} else if ("|".equals(expressionArray[expressionArrayIndex])
						&& expressionArrayIndex < expressionArray.length - 1
						&& "|".equals(expressionArray[expressionArrayIndex + 1])) {
					firstOperator += expressionArray[1 + expressionArrayIndex];
					expressionArrayIndex++;
				} else {
				}
				if ("(".equals(operatorStack.peek())) {
					operatorStack.push(firstOperator);
				} else {
					String secondOperator = operatorStack.peek() + "";

					int firstOperandPrecedence = getPrecedence(firstOperator);
					int secondOperandPrecedence = getPrecedence(secondOperator);
					if (firstOperandPrecedence > secondOperandPrecedence) {
						operatorStack.push(firstOperator);
					} else {
						while (firstOperandPrecedence < secondOperandPrecedence) {
							String operator = (operatorStack.pop());
							String operand1 = operandStack.pop();
							String operand2 = operandStack.pop();
							int value = calculateValue(operand2, operand1, operator);
							operandStack.push(value + "");

							firstOperandPrecedence = getPrecedence(firstOperator);
							secondOperator = operatorStack.peek();
							secondOperandPrecedence = getPrecedence(secondOperator);
						}
						operatorStack.push(firstOperator);
					}
				}
			}
		}

		if (!operatorStack.isEmpty())
			throw new Exception();
		return Integer.parseInt(operandStack.pop());
	}

	/**
	 * will cal. value of a particular sub-expression
	 * 
	 * @param firstStringOperand->first   operand
	 * @param secondStringOperand->second operand
	 * @param operator->what              is the operator(eg. +, -)could be
	 *                                    arithmetic, logical, boolean
	 * @return->value of the sub-expression
	 */
	static int calculateValue(String firstStringOperand, String secondStringOperand, String operator) {
		int firstOperand = Integer.parseInt(firstStringOperand);
		int secondOperand = Integer.parseInt(secondStringOperand);
		switch (operator) {
			case "+":
				return firstOperand + secondOperand;

			case "-":
				return firstOperand - secondOperand;

			case "*":
				return firstOperand * secondOperand;

			case "/":
				return firstOperand / secondOperand;

			case "<":
				if (firstOperand < secondOperand)
					return 1;
				else
					return 0;

			case ">":
				if (firstOperand > secondOperand)
					return 1;
				else
					return 0;

			case "<=":
				if (firstOperand <= secondOperand)
					return 1;
				else
					return 0;

			case ">=":
				if (firstOperand >= secondOperand)
					return 1;
				else
					return 0;

			case "==":
				if (firstOperand == secondOperand)
					return 1;
				else
					return 0;

			case "!=":
				if (firstOperand != secondOperand)
					return 1;
				else
					return 0;

			default:
				return -1;
		}
	}

	/**
	 * will get precedence of a particular operator
	 * 
	 * @param operator->what is the operator(eg. +, -)could be arithmetic, logical,
	 *                       boolean
	 * @return precedence of the operator
	 */
	static int getPrecedence(String operator) {
		int precedence = 0;
		switch (operator) {
			case "!":
				precedence = 5;
				break;

			case "*":
			case "/":
				precedence = 4;
				break;

			case "+":
			case "-":
				precedence = 3;
				break;

			case "<":
			case ">":
			case "<=":
			case ">=":
				precedence = 2;
				break;

			case "==":
			case "!=":
				precedence = 1;
				break;

			case "&&":
			case "||":
				precedence = 0;
				break;

			default:
				precedence = -1;
		}
		return precedence;
	}

	/**
	 * checks whether a char is operator or not
	 * 
	 * @param ch->denotes char
	 * @return->whether a char is operator or not
	 */
	static public boolean isOperator(String ch) {
		List<String> operatorList = new ArrayList<>();
		operatorList.add("+");
		operatorList.add("-");
		operatorList.add("*");
		operatorList.add("/");
		operatorList.add("<");
		operatorList.add(">");
		operatorList.add("<=");
		operatorList.add(">=");
		operatorList.add("==");
		operatorList.add("!=");
		operatorList.add("&&");
		operatorList.add("||");
		operatorList.add("!");
		operatorList.add("|");
		operatorList.add("&");
		operatorList.add("=");
		if (operatorList.contains(ch))
			return true;
		else
			return false;
	}

	/**
	 * will get expression from user
	 * 
	 * @return->string infix expression
	 * @throws Exception->incase expression is invalid
	 */
	static public String getExpressionFromUser() throws Exception {
		System.out.println("Enter an infix integer expression without spaces");
		String expression = scannerObject.nextLine();
		expression = expression.toLowerCase();
		if (expression.contains(" "))
			throw new Exception("Please enter expression without spaces");
		for (int expressionIndex = 0; expressionIndex < expression.length(); expressionIndex++) {
			if (expression.charAt(expressionIndex) >= 97 && expression.charAt(expressionIndex) <= 122) {
				throw new Exception("Please enter valid infix integer expression");
			}
		}
		return expression;
	}
}
