package dietcourtserver.model;

import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LPWizard;
import scpsolver.problems.LinearProgram;

import java.util.*;

public class LPTest {
    public static void main(String[] args) {
        optimizeMenu(Arrays.asList(1.0, 4.0, 8.0));
    }

    public static List<Double> optimizeMenu(List<Double> list) {

        List<Double> proteins = new ArrayList<>();
        for(Double d : list) {
            proteins.add(d);
        }
        double[] target = new double[proteins.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = proteins.get(i).doubleValue();  // java 1.4 style
            // or:
            target[i] = proteins.get(i);                // java 1.5+ style (outboxing)
        }
        LinearProgram lp = new LinearProgram(target);

        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{3.0,1.0}, 8.0, "c1"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{0.0,4.0}, 4.0, "c2"));
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(new double[]{2.0,0.0}, 2.0, "c3"));
        lp.setMinProblem(true);

        LPWizard lpw = new LPWizard();
        lpw.plus("x1",5.0).plus("x2",10.0);
        lpw.addConstraint("c1",8,"<=").plus("x1",3.0).plus("x2",1.0);
        lpw.addConstraint("c2",4,"<=").plus("x2",4.0);
        lpw.addConstraint("c3", 2, ">=").plus("x1",2.0);


        LPWizard alpw = new LPWizard();

        int i = 0;
        int j = 0;
        for(Double d : list) {
            i++;
            lpw.plus("x" + Integer.toString(++j), d).plus("x" + Integer.toString(++j), d + 1);
            lpw.addConstraint("c1", 8, ">=").plus("x" + Integer.toString(i), d);
            lpw.addConstraint("c2", 4, ">=").plus("x" + Integer.toString(i), d + 1);
        }
        LinearProgramSolver solver  = SolverFactory.newDefault();
        double[] sol = solver.solve(lpw.getLP());

        return new ArrayList<Double>();
    }
}
