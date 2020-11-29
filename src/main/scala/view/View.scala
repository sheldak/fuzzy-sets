package view

import core.Config
import map.World

import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color

class View(world: World) extends BorderPane {
    val canvas = new Canvas(1000, 1000)
    val context: GraphicsContext = canvas.graphicsContext2D
    var x = 3

    children.add(canvas)

    def get(): Int = {
        x
    }

    def get_incr(): Int = {
        x += 1
        x
    }

    def update(): Unit = {
        get_incr()

        draw_background()
        draw_entities()

        context.fill = Color.rgb(255, 0, 0)
        context.fillRect(100, 100, 10*x, 10*x)
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
