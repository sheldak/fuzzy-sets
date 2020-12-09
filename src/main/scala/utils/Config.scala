package utils


object Config {
    val frameTime: Int = 150_000_000

    val ViewWidth: Int = 1000
    val ViewHeight: Int = 1000

    val MapWidth: Int = 125
    val MapHeight: Int = 125

    val FieldSize: Int = 8

    val FuzzyficationFile = "src/main/resources/fuzzyfication/decision.fcl"

    val startPosition: Vector = new Vector(50, 50)
    val startEnergy: Int = 100

    val NewSugar: Int = 5

    val MaxEnergy: Int = 200
    val EnergyFromSugar: Int = 50
    val MoveEnergy: Int = 10
    val DivisionEnergy: Int = 50

    val MaxDecisionDistance: Int = 40
    val MaxDecisionColonySize: Int = 100

    val Offsets: List[Vector] = List(
        new Vector(0, 0),
        new Vector(-1, 0), new Vector(0, -1), new Vector(1, 0), new Vector(0, 1),
        new Vector(-1, -1), new Vector(1, -1), new Vector(1, 1), new Vector(-1, 1)
    )
}
