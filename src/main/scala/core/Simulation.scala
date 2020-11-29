package core

import map.World
import view.View
import utils.Config

import java.time

class Simulation(view: View, world: World) extends Thread {
    override def run(): Unit = {
        while(true) {
            val startTime = time.LocalTime.now().getNano

            world.update()
            view.update()

            val endTime = time.LocalTime.now().getNano
            val executionTime = if (endTime - startTime > 0) endTime - startTime else 1_000_000_000 + endTime - startTime

            val timeToSleep = Math.max(Config.frameTime - executionTime, 0)
            Thread.sleep(timeToSleep / 1_000_000, timeToSleep % 1_000_000)
        }
    }
}
