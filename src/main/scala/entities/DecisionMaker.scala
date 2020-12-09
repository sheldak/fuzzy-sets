package entities

import map.World
import utils.{Config, Fuzzyficator}

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

        val distance = Math.min(
            Config.MaxDecisionDistance,
            world
              .distanceToClosestSugar(bacterium)
              .getOrElse(Config.MapWidth + Config.MapHeight)
        )

        val colonySize = Math.min(world.colonySize, Config.MaxDecisionColonySize)

        val decision: Double = Fuzzyficator.evaluateDecision(energy, distance, colonySize)

        if (decision >= divideThreshold)
            Division
        else
            Move(calculateSpeed(decision))
    }

    private def calculateSpeed(decision: Double): Int = {
        if (decision >= goIndicator)
            (goSpeed * (divideThreshold - decision) / (divideThreshold - goIndicator)).asInstanceOf[Int]
        else if (decision >= goFastIndicator)
            (goSpeed * (decision - goFastIndicator) + goFastSpeed * (goIndicator - decision) /
              (goIndicator - goFastIndicator)).asInstanceOf[Int]
        else
            goFastSpeed.asInstanceOf[Int]
    }
}
