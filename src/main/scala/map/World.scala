package map

import entities.{Bacterium, DecisionMaker, Entity, Sugar}
import utils.{Config, Vector}

import scala.collection.mutable
import scala.util.Random

class World {
    val random: Random = new Random()
    val decisionMaker: DecisionMaker = new DecisionMaker()

    val fields: List[List[Field]] =
        for (x <- (0 until Config.MapHeight).toList) yield {
            for (y <- (0 until Config.MapWidth).toList)
                yield new Field(new Vector(x, y))
        }

    val entities: mutable.Set[Entity] = mutable.Set(
        new Bacterium(this, Config.startPosition, decisionMaker, Config.startEnergy)
    )

    def update(): Unit = {
        addSugar()

        entities
          .filter(entity => entity.isInstanceOf[Bacterium])
          .foreach(bacterium => bacterium.asInstanceOf[Bacterium].makeAction())
    }

    def addBacterium(position: Vector): Unit = {
        val newBacterium = new Bacterium(this, position, decisionMaker, Config.DivisionEnergy)

        fields(position.x)(position.y).addEntity(newBacterium)
        entities.add(newBacterium)
    }

    def removeBacterium(bacterium: Bacterium): Unit = {
        fields(bacterium.position.x)(bacterium.position.y).deleteEntity()
        entities.remove(bacterium)
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
        }
    }

    def removeSugar(position: Vector): Unit = {
        val field = fields(position.x)(position.y)

        entities.remove(field.entityAt.get)
        field.deleteEntity()
    }

    def isEmptyField(position: Vector): Boolean = !fields(position.x)(position.y).isEntity

    def isSugarAt(position: Vector): Boolean =
        fields(position.x)(position.y)
          .entityAt
          .getOrElse(false)
          .isInstanceOf[Sugar]

    def isPossiblePosition(position: Vector): Boolean =
        position.x >= 0 && position.y >= 0 && position.x < Config.MapWidth && position.y < Config.MapHeight

    def moveBacterium(bacterium: Bacterium, oldPosition: Vector): Unit = {
        fields(oldPosition.x)(oldPosition.y).deleteEntity()
        fields(bacterium.position.x)(bacterium.position.y).addEntity(bacterium)
    }

    def colonySize: Int = entities.count(entity => entity.isInstanceOf[Bacterium])

    def directionToClosestSugar(bacterium: Bacterium, speed: Int): Vector = {
        if (entities.exists(entity => entity.isInstanceOf[Sugar])) {
            val sugar = closestSugar(bacterium)
            val difference = sugar.position - bacterium.position

            difference.direction(speed)
        } else new Vector(0, 0)
    }

    def distanceToClosestSugar(bacterium: Bacterium): Option[Int] = {
        if (entities.exists(entity => entity.isInstanceOf[Sugar])) {
            Some(distance(bacterium, closestSugar(bacterium)))
        } else {
            None
        }
    }

    private def closestSugar(bacterium: Bacterium): Sugar = {
        entities
          .toArray
          .filter(entity => entity.isInstanceOf[Sugar])
          .reduce((a, b) => closer(bacterium, a, b))
          .asInstanceOf[Sugar]
    }

    private def closer(reference: Entity, a: Entity, b: Entity): Entity = {
        if (distance(reference, a) <= distance(reference, b)) a else b
    }

    private def distance(a: Entity, b: Entity): Int = {
        (a.position - b.position).abs
    }
}
