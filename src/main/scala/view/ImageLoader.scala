package view

import java.io.FileInputStream

import scalafx.scene.image.Image

object ImageLoader {
    private val animal: Image = new Image(new FileInputStream("src/main/resources/images/bacterium.png"))
    private val sugar: Image = new Image(new FileInputStream("src/main/resources/images/sugar.png"))

    val images: Map[String, Image] = Map("Bacterium" -> animal, "Sugar" -> sugar)
}
