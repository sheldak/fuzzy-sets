package map

import core.{Config, DecisionMaker}
import entities.{Bacterium, Entity, Sugar}
import utils.Vector

import scala.collection.mutable
import scala.util.Random

class World {
    val random: Random = new Random()
    val decisionMaker: DecisionMaker = new DecisionMaker()

    val fields: List[List[Field]] =
        for (x <- (0 until Config.MapHeight).toList) yield {
            for (y <- (0 until Config.MapWidth).toList)
                yield new Field(x, y)
        }

    val entities: mutable.Set[Entity] = mutable.Set(new Bacterium(this, new Vector(100, 100), decisionMaker))

    def update(): Unit = {
        addSugar()

        entities
          .filter(entity => entity.isInstanceOf[Bacterium])
          .foreach(bacterium => bacterium.asInstanceOf[Bacterium].makeAction())
    }

    def addSugar(): Unit = {
        for (_ <- 0 until Config.NewSugar) {
            var x: Int = 0
            var y: Int = 0

            do {
                x = random.between(0, Config.MapWidth)
                y = random.between(0, Config.MapHeight)
            } while (fields(x)(y).entityAt.isDefined)

            val newSugar = new Sugar(new Vector(x, y))
            fields(x)(y).addEntity(newSugar)
            entities.add(newSugar)
            println(x, y)
        }
    }

    def getDirectionToClosestSugar(bacterium: Bacterium, speed: Int): Vector = {
        if (entities.exists(entity => entity.isInstanceOf[Sugar])) {
            val sugar = getClosestSugar(bacterium)
            val difference = sugar.position - bacterium.position

            difference.direction(speed)
        } else new Vector(0, 0)
    }

    def getDistanceToClosestSugar(bacterium: Bacterium): Option[Int] = {
        if (entities.exists(entity => entity.isInstanceOf[Sugar])) {
            Some(getDistance(bacterium, getClosestSugar(bacterium)))
        } else {
            None
        }
    }

    private def getClosestSugar(bacterium: Bacterium): Sugar = {
        entities
          .toArray
          .filter(entity => entity.isInstanceOf[Sugar])
          .reduce((a, b) => getCloser(bacterium, a, b))
          .asInstanceOf[Sugar]
    }

    private def getCloser(reference: Entity, a: Entity, b: Entity): Entity = {
        if (getDistance(reference, a) <= getDistance(reference, b)) a else b
    }

    private def getDistance(a: Entity, b: Entity): Int = {
        (a.position - b.position).abs
    }
}
