package map

import entities.Entity
import utils.Vector

class Field(position: Vector) {
    var entityAt: Option[Entity] = None

    def addEntity(entity: Entity): Unit = {
        entityAt = Some(entity)
    }

    def deleteEntity(): Unit = {
        entityAt = None
    }

    def isEntity: Boolean = entityAt.isDefined

    override def toString: String = f"($position.x,$position.y)"
}
