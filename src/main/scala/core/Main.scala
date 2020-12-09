package core

import map.World
import utils.{Config, Fuzzyficator}
import view.View

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object Main extends JFXApp {
    Fuzzyficator.drawCharts()

    val world = new World
    val view = new View(world)

    val borderPane = new Scene(view, Config.ViewWidth, Config.ViewHeight)

    val thread = new Simulation(view, world)
    thread.start()

    stage = new PrimaryStage {
        title = "Fuzzy Sets"
        scene = borderPane
    }
}
