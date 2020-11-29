package core

import map.World
import utils.Config
import view.View

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.rule.{FuzzyRuleSet, Variable}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object ScalaFXHelloWorld extends JFXApp {
//    FuzzyObject.fuzzyDec()
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

object FuzzyObject {
    def fuzzy(): Unit = {
        val fileName: String = "src/main/scala/core/volume.fcl"
        val intensityLevel = 30
        val dayPart = 9
        val fis: FIS = FIS.load(fileName, false)

        val fuzzyRuleSet: FuzzyRuleSet = fis.getFuzzyRuleSet()
        fuzzyRuleSet.chart()

        fuzzyRuleSet.setVariable("intensityLevel", intensityLevel)
        fuzzyRuleSet.setVariable("dayPart", dayPart)

        fuzzyRuleSet.evaluate()

        fuzzyRuleSet.getVariable("intensityChange").chartDefuzzifier(true)

        println(fuzzyRuleSet)
    }

    def fuzzyDec(): Unit = {
        val fileName: String = "src/main/resources/fuzzyfication/decision.fcl"
        val energy = 10
        val distance = 10
        val colonySize = 10
        val fis: FIS = FIS.load(fileName, false)

        val fuzzyRuleSet: FuzzyRuleSet = fis.getFuzzyRuleSet()
        fuzzyRuleSet.chart()

        fuzzyRuleSet.setVariable("energy", energy)
        fuzzyRuleSet.setVariable("distance", distance)
        fuzzyRuleSet.setVariable("colonySize", colonySize)

        fuzzyRuleSet.evaluate()

        val variable: Variable = fuzzyRuleSet.getVariable("decision")
//        println(variable.)

        variable.chartDefuzzifier(true)
        println(fuzzyRuleSet)
    }
}
