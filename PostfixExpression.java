// Christopher Fritz
// CPS593	12/4/2014
// HW 5 Postfix Expression Calculator



import java.io.*;
import java.util.*;
import java.lang.Math.*;
import java.util.Scanner;
import java.util.regex.Pattern;
			
public class PostfixExpression
{
	public static final Pattern CHARACTER = Pattern.compile("\\S.*?");  
    public static final Pattern UNSIGNED_DOUBLE = Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][-+]?\\d+)?.*?");
	public static void main(String[] args)
	{
		try
		{
			BufferedReader inputStream = new BufferedReader(new FileReader("in.dat"));
			String line = inputStream.readLine();
			System.out.println();
			System.out.println("Hello! This is a postfix expression calculator.");
			System.out.println();
			while (line != null)
			{
				//processing of the line
				System.out.println("The value of \"" + line + "\" is " + evaluatePostfixLine(line));
				System.out.println();
				line = inputStream.readLine();
			}
			inputStream.close();
			System.out.println("Bye-bye!");
			System.out.println();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Input file not found!");
		}
		catch (IOException e)
		{
			System.out.println("File is empty!");
		}
	}
	
	public static double evaluatePostfixLine(String s)
	{
		// Store operands here
		Stack<Double> operands = new Stack<Double>();
		// This scans the line for operands and operators
		Scanner input = new Scanner(s);
		// Token
		String next;
		// Second operand for non-commutative operations
		Double oper2;
		while (input.hasNext())
		{
			if (input.hasNext(UNSIGNED_DOUBLE))
			{
				next = input.findInLine(UNSIGNED_DOUBLE);
				operands.push(new Double(next));
			}
			else
			{
				next = input.findInLine(CHARACTER);
				char first = next.charAt(0);
				// Perform known operations on operands stack
				// Test for each case: if stack is empty, IllegalArgumentException("Input is not balanced")
				if (operands.peek() == null)
					throw new IllegalArgumentException("Input is not balanced: too many operators!");
				switch (first)
				{
					// Cases: 
					
					// +, -, *, /    arithmetic operators
					case '+':
						operands.push(operands.pop() + operands.pop());
						break;
					case '-': 
						// Non-commutative, must store second operand
						oper2 = new Double(operands.pop());
						operands.push(operands.pop() - oper2);
						break; 
					case '*':
						operands.push(operands.pop() * operands.pop());
						break;
					case '/': 
						// Non-commutative, must store second operand
						oper2 = new Double(operands.pop());
						operands.push(operands.pop() / oper2);
						break;	
					// _             unary negation
					case '_':
						operands.push(-1 * operands.pop());
						break;
					// #             square root
					case '#':
						operands.push(Math.sqrt(operands.pop()));
						break;
					// ^             exponentiation (a b ^ = a raised to the power b
					case '^':
						oper2 = new Double(operands.pop());
						operands.push(Math.pow(operands.pop(), oper2));
						break;
				}
			}
		}
		// Input is fully read, now check that there is only one operand left.
		double result = operands.pop();
		//try
		//{
		//	operands.peek()
		//}
		//catch
		//{
		//	throw new IllegalArgumentException("Input is not balanced: too many operands!");
		//}
		return result;
	}
}