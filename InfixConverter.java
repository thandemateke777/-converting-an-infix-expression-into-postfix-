import java.util.Stack;
import java.util.Scanner;

/**
 * Assignment: Infix to Postfix and Prefix Converter
 * 
 * PSEUDOCODE:
 * ============================================================
 * FUNCTION infixToPostfix(expression):
 *   1. Initialize empty Stack and empty output string
 *   2. FOR each character ch in expression:
 *        a. IF ch is an operand (letter/digit):
 *              Append ch to output
 *        b. ELSE IF ch is '(':
 *              Push ch onto stack
 *        c. ELSE IF ch is ')':
 *              WHILE stack top != '(':
 *                  Pop from stack and append to output
 *              Pop '(' from stack (discard it)
 *        d. ELSE (ch is an operator +, -, *, /, ^):
 *              WHILE stack is not empty AND
 *                    stack top != '(' AND
 *                    precedence(stack.top) >= precedence(ch):
 *                  Pop from stack and append to output
 *              Push ch onto stack
 *   3. WHILE stack is not empty:
 *        Pop from stack and append to output
 *   4. RETURN output
 *
 * FUNCTION infixToPrefix(expression):
 *   1. Reverse the infix expression
 *   2. Swap every '(' with ')' and vice versa
 *   3. Apply infixToPostfix() on the modified expression
 *   4. Reverse the result
 *   5. RETURN the reversed result (= Prefix)
 * ============================================================
 */
public class InfixConverter {

    // Returns operator precedence
    static int precedence(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^':           return 3;
            default:            return -1;
        }
    }

    // Check if character is an operand (letter or digit)
    static boolean isOperand(char ch) {
        return Character.isLetterOrDigit(ch);
    }

    // Convert Infix to Postfix
    public static String infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // Skip spaces
            if (ch == ' ') continue;

            if (isOperand(ch)) {
                // Operand: add directly to output
                output.append(ch);

            } else if (ch == '(') {
                // Left parenthesis: push to stack
                stack.push(ch);

            } else if (ch == ')') {
                // Right parenthesis: pop until '(' found
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                stack.pop(); // Remove '(' from stack

            } else {
                // Operator: pop higher/equal precedence operators first
                while (!stack.isEmpty()
                        && stack.peek() != '('
                        && precedence(stack.peek()) >= precedence(ch)) {
                    output.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        // Pop remaining operators from stack
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }

        return output.toString();
    }

    // Convert Infix to Prefix
    public static String infixToPrefix(String expression) {
        // Step 1: Reverse the expression
        StringBuilder reversed = new StringBuilder(expression).reverse();

        // Step 2: Swap parentheses
        for (int i = 0; i < reversed.length(); i++) {
            char ch = reversed.charAt(i);
            if (ch == '(')      reversed.setCharAt(i, ')');
            else if (ch == ')') reversed.setCharAt(i, '(');
        }

        // Step 3: Get postfix of modified expression
        String postfix = infixToPostfix(reversed.toString());

        // Step 4: Reverse postfix to get prefix
        return new StringBuilder(postfix).reverse().toString();
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("   Infix to Postfix and Prefix Converter  ");
        System.out.println("===========================================");
        System.out.print("Enter an infix expression: ");
        String infix = scanner.nextLine().trim();

        String postfix = infixToPostfix(infix);
        String prefix  = infixToPrefix(infix);

        System.out.println("\nResults:");
        System.out.println("  Infix   : " + infix);
        System.out.println("  Postfix : " + postfix);
        System.out.println("  Prefix  : " + prefix);
        System.out.println("===========================================");

        scanner.close();
    }
}

/*
 * ============================================================
 * SAMPLE TEST CASES
 * ============================================================
 * Input:  A+B*C
 * Postfix: ABC*+
 * Prefix:  +A*BC
 *
 * Input:  (A+B)*(C-D)
 * Postfix: AB+CD-*
 * Prefix:  *+AB-CD
 *
 * Input:  A+B*(C^D-E)
 * Postfix: ABCD^E-*+
 * Prefix:  +A*B-^CDE
 * ============================================================
 */
