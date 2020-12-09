package utils

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet

object Fuzzyficator {
    private val fuzzyRuleSet: FuzzyRuleSet = FIS
      .load(Config.FuzzyficationFile, false)
      .getFuzzyRuleSet()

    def evaluateDecision(energy: Int, distance: Int, colonySize: Int): Double = {
        fuzzyRuleSet.setVariable("energy", energy)
        fuzzyRuleSet.setVariable("distance", distance)
        fuzzyRuleSet.setVariable("colonySize", colonySize)

        fuzzyRuleSet.evaluate()

        fuzzyRuleSet.getVariable("decision").defuzzify()
    }

    def drawCharts(): Unit = fuzzyRuleSet.chart()
}