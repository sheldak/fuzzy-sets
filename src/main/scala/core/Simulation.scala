package core

import map.World
import view.View

class Simulation(view: View, world: World) extends Thread {

    def get(): Unit = {
        val y = view.get_incr()
    }

    override def run(): Unit = {
        while(true) {
            Thread.sleep(1000)
            world.update()
            view.update()
        }
    }
}
