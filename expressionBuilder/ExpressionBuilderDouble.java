import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * Algorithm: Recursive backtracking
 * 
 * Let Expression = Expression OP Expression | value OP Expression | Expression OP value | value | None
 * where OP = PLUS | MINUS | PROD | DIV 
 * and value : Integer    
 * 
 * @author tapomay
 * @date 04/15/16
 */
public class ExpressionBuilderDouble {

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

    /**
     * Returns new Expressions by joining expression as second operand in (value operator expression)
     * @param exprList
     * @param op
     * @param x
     * @return
     */
    private List<Expression> buildValueFirst(List<Expression> exprList, OP op, Integer x) {
        List<Expression> ret = exprList.stream().map(e -> new Expression(new Value(x), e, op))
            .collect(Collectors.toList());
        return ret;
    }

    /**
     * Returns new Expressions by joining expression as first operand in (expression operator value)
     * @param exprList
     * @param op
     * @param x
     * @return
     */
    private List<Expression> buildExprFirst(List<Expression> exprList, OP op, Integer x) {
        List<Expression> ret = exprList.stream().map(e -> new Expression(e, new Value(x), op))
            .collect(Collectors.toList());
        return ret;
    }

    /**
     * Recursive solution:
     * Find all possible expressions formed from Integers in arr that evaluate to N.
     * @param N
     * @param arr
     * @return
     */
    public List<Expression> findExpressions(Double N, List<Integer> arr) {
//        System.out.println(N + " : " + arr);
        List<Expression> resultExprList = new ArrayList<>();
        if (N == 0) {// base condition 1: return empty
            return resultExprList;
        }
        if (arr.size() == 1) { // base condition 2
            String arrVal = arr.get(0).toString() + ".00000";
            String expectedVal = String.format("%.5f", N);
            if (arrVal.equals(expectedVal)) {
                resultExprList.add(new Value(arr.get(0))); //return single value
            }
            return resultExprList;
        }

        for (int i = 0; i < arr.size(); i++) {
            Integer x = arr.get(i);
            List<Integer> restArr = new ArrayList<Integer>(arr);
            restArr.remove(i);

            double rem = x / N;
            List<Expression> expressions = findExpressions(rem, restArr);
            List<Expression> newExpr = buildValueFirst(expressions, OP.DIV, x);
            resultExprList.addAll(newExpr);

            rem = N / x;
            expressions = findExpressions(rem, restArr);
            newExpr = buildValueFirst(expressions, OP.PROD, x);
            resultExprList.addAll(newExpr);

            rem = N * x;
            expressions = findExpressions(rem, restArr);
            newExpr = buildExprFirst(expressions, OP.DIV, x);
            resultExprList.addAll(newExpr);

            rem = N + x;
            expressions = findExpressions(rem, restArr);
            newExpr = buildExprFirst(expressions, OP.MINUS, x);
            resultExprList.addAll(newExpr);

            rem = x - N;
            expressions = findExpressions(rem, restArr);
            newExpr = buildValueFirst(expressions, OP.MINUS, x);
            resultExprList.addAll(newExpr);

            rem = N - x;
            expressions = findExpressions(rem, restArr);
            newExpr = buildValueFirst(expressions, OP.PLUS, x);
            resultExprList.addAll(newExpr);

        }

        return resultExprList;
    }

    public static void main(String[] args) {

        ExpressionBuilderDouble test = new ExpressionBuilderDouble();
        Integer N = 10;
        Integer[] input = { 6, 3, 1 };
        List<Integer> arr = Arrays.asList(input);
        if (args.length > 1) {
            N = Integer.parseInt(args[0]);
            arr = new ArrayList<>();
            ;
            for (int i = 1; i < args.length; i++) {
                arr.add(Integer.parseInt(args[i].trim()));
            }
        } else {
            System.out.println("Invalid inputs");
            return;
        }
        List<Expression> findExpressions = test.findExpressions(N.doubleValue(), arr);
        if (findExpressions.size() > 0) {
            System.out.println(findExpressions.get(0).toString());
            System.out.println("All expressions: ");
            Set<String> expressionStrs = new HashSet<>();
            for (Expression e : findExpressions) {
                expressionStrs.add(e.toString());
            }
            System.out.println(expressionStrs);
        } else {
            System.out.println("none");
        }
    }

}
