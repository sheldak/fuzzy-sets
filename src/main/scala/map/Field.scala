package map

import entities.Entity

class Field(x: Int, y: Int) {
    var entityAt: Option[Entity] = None

    def addEntity(entity: Entity): Unit = {
        entityAt = Some(entity)
    }

    def deleteEntity(): Unit = {
        entityAt = None
    }

    override def toString: String = f"($x,$y)"
}
