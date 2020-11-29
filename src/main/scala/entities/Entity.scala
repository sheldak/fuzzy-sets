package entities

import core.{DecisionMaker, Division, Move}
import utils.{Config, Vector}
import map.World

sealed trait Entity {
    var position: Vector
    val name: String

    override def toString: String = f"${name}(${position.x}, ${position.y})"
}

class Bacterium( val world: World,
                 var position: Vector,
                 val decisionMaker: DecisionMaker,
                 val startEnergy: Int) extends Entity {

    val name: String = "Bacterium"
    var energy: Int = startEnergy

    def makeAction(): Unit = {
        decisionMaker.decide(world, this) match {
            case Division => divide()
            case Move(speed: Int) => move(speed)
        }
    }

    def divide(): Unit = {
        for (offset <- Config.Offsets) {
            val potentialPosition = position + offset
            if (world.isPossiblePosition(potentialPosition) && world.isEmptyField(potentialPosition)) {
                world.addBacterium(potentialPosition)
                energy -= Config.DivisionEnergy

                return
            }
        }
    }

    def move(speed: Int): Unit = {
        val currPosition = position

        val toSugarDirection = world.directionToClosestSugar(this, speed)

        for (moveOffset <- Config.Offsets if currPosition == position) {
            val potentialPosition = currPosition + toSugarDirection + moveOffset

            if (world.isPossiblePosition(potentialPosition)) {
                if (world.isSugarAt(potentialPosition)) {
                    position = potentialPosition
                    eat()
                } else if (world.isEmptyField(potentialPosition)) {
                    position = potentialPosition
                }
            }
        }

        world.moveBacterium(this, currPosition)

        energy -= Config.MoveEnergy
        if (energy == 0) die()
    }

    def eat(): Unit = {
        world.removeSugar(position)
        energy += Config.EnergyFromSugar
    }

    def die(): Unit = {
        world.removeBacterium(this)
    }
}

class Sugar(var position: Vector) extends Entity {
    val name: String = "Sugar"
}
