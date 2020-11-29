package view

import map.World
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color
import utils.Config

class View(world: World) extends BorderPane {
    val canvas = new Canvas(1000, 1000)
    val context: GraphicsContext = canvas.graphicsContext2D

    children.add(canvas)

    def update(): Unit = {
        draw_background()
        draw_entities()
    }

    def draw_background(): Unit = {
        context.fill = Color.Green
        context.fillRect(0, 0, Config.ViewWidth, Config.ViewHeight)
    }

    def draw_entities(): Unit = {
        world.entities.foreach(entity => context.drawImage(
                ImageLoader.images(entity.name),
                entity.position.x * Config.FieldSize,
                entity.position.y * Config.FieldSize
            )
        )
    }
}
