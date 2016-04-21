import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 * Algorithm: Recursive backtracking
 * 
 * Let Expression = Expression OP Expression | value OP Expression | Expression OP value | value | None
 * where OP = PLUS | MINUS | PROD | DIV 
 * and value : IntegerZA    
 * 
 * f(N:int, xarr:int[]) : Expression[]
 *     resultExpr = Expression[]
 *     if N == 0:
 *         return []
 *     if xarr.size == 1:
 *         if xarr[0] == N:
 *             return [N]
 *         else:
 *             return []
 *         
 *     for each x in xArr
 *         restArr = xArr.delete(x)
 *         
 *         if x > N :
 *             
 *             //try division first
 *             if x%N == 0: 
 *                 rem = x / N
 *                 expr = f(rem, restArr) such that x / expr  = N
 *                 resultExpr.addAll(x, DIV, expr)
 *             
 *             //then try subtraction
 *             rem = x - N
 *             expr = f(rem, restArr) such that x - resultExpr = N
 *             resultExpr.addAll(x, MINUS, expr)
 *         
 *         else if x <= N:
 *             //try multiplication
 *             if N%x == 0:
 *                 rem = N / x
 *                 expr  = f(rem, restArr) such that x * resultExpr = N
 *                 resultExpr.addAll(x, PROD, expr)
 *
 *             //try addition
 *             rem = N - x
 *             resultExpr = f(rem, restArr) such that x + resultExpr = n
 *             resultExpr.addAll(x, PLUS, expr)
 *              
 *             //try non-commutative division
 *             rem = N * x
 *             expr = f(rem, restArr)
 *             resultExpr.addAll(expr, DIV, x)
 *
 *             //try non-commutative subtraction
 *             rem = N + x
 *             expr = f(rem, restArr)
 *             resultExpr.addAll(expr, MINUS, x)
 *
 *     return resultExpr
 *             
 * @author tapomay
 * @date 04/15/16
 */
public class ExpressionBuilder {

    public enum OP {
        PLUS("+"), MINUS("-"), PROD("*"), DIV("/"), OPEN("("), CLOSE(")");
        String val;

        OP(
            String val) {
            this.val = val;
        }
    };

    public class Expression {
        private static final String SPACE = " ";
        private Expression e1;
        private Expression e2;
        private OP operator;

        public Expression(
            Expression e1, Expression e2, OP operator) {
            super();
            this.e1 = e1;
            this.e2 = e2;
            this.operator = operator;
        }

        public String toString() {
            return OP.OPEN.val + e1.toString() + SPACE + operator.val + SPACE + e2.toString() + OP.CLOSE.val;
        }

    }

    public class Value extends Expression {

        private Integer value;

        public Value(
            Integer val) {
            super(null, null, null);
            this.value = val;
        }

        public Integer getValue() {
            return this.value;
        }

        public String toString() {
            return (value != null) ? value.toString() : "NULL";
        }
    }

    private List<Expression> buildValueFirst(List<Expression> exprList, OP op, Integer x) {
        List<Expression> ret = exprList.stream().map(e -> new Expression(new Value(x), e, op))
            .collect(Collectors.toList());
        return ret;
    }

    private List<Expression> buildExprFirst(List<Expression> exprList, OP op, Integer x) {
        List<Expression> ret = exprList.stream().map(e -> new Expression(e, new Value(x), op))
            .collect(Collectors.toList());
        return ret;
    }

    public List<Expression> findExpressions(int N, List<Integer> arr) {

        List<Expression> resultExprList = new ArrayList<>();
        if(N == 0){
            return resultExprList;
        }
        if (arr.size() == 1) {
            if (arr.get(0) == N) {
                resultExprList.add(new Value(N));
            }
            return resultExprList;
        }

        for (int i = 0; i < arr.size(); i++) {
            Integer x = arr.get(i);
            List<Integer> restArr = new ArrayList<Integer>(arr);
            restArr.remove(i);

            if (x > N) {
                if (x % N == 0) {
                    int rem = x / N;
                    List<Expression> expressions = findExpressions(rem, restArr);
                    List<Expression> newExpr = buildValueFirst(expressions, OP.DIV, x);
                    resultExprList.addAll(newExpr);
                }

                int rem = x - N;
                List<Expression> expressions = findExpressions(rem, restArr);
                List<Expression> newExpr = buildValueFirst(expressions, OP.MINUS, x);
                resultExprList.addAll(newExpr);
            } 
            else if (x <= N) {
                if (N % x == 0) {
                    int rem = N / x;
                    List<Expression> expressions = findExpressions(rem, restArr);
                    List<Expression> newExpr = buildValueFirst(expressions, OP.PROD, x);
                    resultExprList.addAll(newExpr);
                }
                int rem = N - x;
                List<Expression> expressions = findExpressions(rem, restArr);
                List<Expression> newExpr = buildValueFirst(expressions, OP.PLUS, x);
                resultExprList.addAll(newExpr);

                rem = N * x;
                expressions = findExpressions(rem, restArr);
                newExpr = buildExprFirst(expressions, OP.DIV, x);
                resultExprList.addAll(newExpr);

                rem = N + x;
                expressions = findExpressions(rem, restArr);
                newExpr = buildExprFirst(expressions, OP.MINUS, x);
                resultExprList.addAll(newExpr);
            }
        }

        return resultExprList;
    }
    
    public static void main(String[] args) {
        
        ExpressionBuilder test = new ExpressionBuilder();
        int N = 10;
        Integer[] input = {6, 3, 1};
        List<Integer> arr = Arrays.asList(input);
        if(args.length > 1) {
            N = Integer.parseInt(args[0]);
            arr = new ArrayList<>();;
            for (int i = 1; i < args.length; i++) {
                arr.add(Integer.parseInt(args[i].trim()));
            }
        }
        else {
            System.out.println("Invalid inputs");
            return;
        }
        List<Expression> findExpressions = test.findExpressions(N, arr);
        if(findExpressions.size() > 0) {
            System.out.println(findExpressions.get(0).toString());
            System.out.println("All expressions: ");
            System.out.println(findExpressions);
        }
        else {
            System.out.println("none");
        }
    }

}
