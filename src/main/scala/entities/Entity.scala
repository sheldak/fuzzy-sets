package entities

import core.{Decision, Division, Move, DecisionMaker}
import utils.Vector
import map.World

sealed trait Entity {
    var position: Vector
    val name: String
}

class Bacterium(val world: World, var position: Vector, val decisionMaker: DecisionMaker) extends Entity {
    val name: String = "Bacterium"

    var energy: Int = 30

    def makeAction(): Unit = {
        decisionMaker.decide(world, this) match {
            case Division => divide()
            case Move(speed: Int) => move(speed)
        }
    }

    def divide(): Unit = {

    }

    def move(speed: Int): Unit = {
        println("Speed:", speed)
        println(world.getDirectionToClosestSugar(this, speed))
        position += world.getDirectionToClosestSugar(this, speed)
    }
}

class Sugar(var position: Vector) extends Entity {
    val name: String = "Sugar"
}
