name := "fuzzy-sets"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies += "org.scalafx" % "scalafx_2.13" % "14-R19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
}

lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m=>
    "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)
