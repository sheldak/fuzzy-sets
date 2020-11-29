package entities

import map.World
import utils.Config

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet

sealed trait Decision
case object Division extends Decision
case class Move(speed: Int) extends Decision

class DecisionMaker {
    val divideThreshold: Double = 7
    val goIndicator: Double = 5.5
    val goFastIndicator: Double = 1

    val goSpeed: Double = 3
    val goFastSpeed: Double = 10

    def decide(world: World, bacterium: Bacterium): Decision = {
        val energy = bacterium.energy

        val distance = world
          .distanceToClosestSugar(bacterium)
          .getOrElse(Config.MapWidth + Config.MapHeight)

        val colonySize = world.colonySize

        val fis: FIS = FIS.load(Config.FuzzyficationFile, false)
        val fuzzyRuleSet: FuzzyRuleSet = fis.getFuzzyRuleSet()

        fuzzyRuleSet.setVariable("energy", energy)
        fuzzyRuleSet.setVariable("distance", distance)
        fuzzyRuleSet.setVariable("colonySize", colonySize)

        fuzzyRuleSet.evaluate()

        val decision: Double = fuzzyRuleSet.getVariable("decision").defuzzify()

        if (decision >= divideThreshold)
            Division
        else
            Move(calculateSpeed(decision))
    }

    def calculateSpeed(decision: Double): Int = {
        if (decision >= goIndicator)
            (goSpeed * (divideThreshold - decision) / (divideThreshold - goIndicator)).asInstanceOf[Int]
        else if (decision >= goFastIndicator)
            (goSpeed * (decision - goFastIndicator) + goFastSpeed * (goIndicator - decision) /
              (goIndicator - goFastIndicator)).asInstanceOf[Int]
        else
            goFastSpeed.asInstanceOf[Int]
    }
}
