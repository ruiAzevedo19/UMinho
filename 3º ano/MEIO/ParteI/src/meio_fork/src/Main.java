import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        /* calculate odds and costs */
        Double[][] probB1 = Odds.deliveryRequestProbs(Odds.b1);
        Double[][] probB2 = Odds.deliveryRequestProbs(Odds.b2);

        Double[][] costB1 = Odds.costsProbs(Odds.b1);
        Double[][] costB2 = Odds.costsProbs(Odds.b2);

        /* calculate all the decisions */
        Double[][][][] detachedDecisions = Odds.allDecisions(probB1,probB2,'P');
        Double[][][] decisions = Odds.mergeDecisions(detachedDecisions, 'P');

        /* calculate all the costs */
        Double[][][][] detachedCosts = Odds.allDecisions(costB1,costB2,'C');
        Odds.transferCost(detachedCosts);
        Double[][][] costs = Odds.mergeDecisions(detachedCosts,'C');

        /* calculates de best policy */
        Policy policy = Calculate.policy(decisions,costs);

        /* prints the ebst policy */

        System.out.println("Optimal policy = " + policy.getGain());
        Print.printVector(policy.getDecisions(), 169);
        Export.dumpMatricesIntoCSV(detachedDecisions, detachedCosts);
        /*Double[][] d = detachedDecisions[1][0];
        Print.printSumLines(d,Odds.states,Odds.states);
        Print.printMatrix(d,Odds.states,Odds.states);
        */
        /*Double[][] c = Odds.decisionM(Odds.b1, 1);
        Print.printSumLines(c,Odds.states,Odds.states);
        Print.printMatrix(c,Odds.states,Odds.states);*/
    }
}
