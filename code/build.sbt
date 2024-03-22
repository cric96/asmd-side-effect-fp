<<<<<<< HEAD
val scala3Version = "3.3.1"
=======
val scala3Version = "3.2.1"
>>>>>>> d35be0f (:rocket: slides)

lazy val root = project
  .in(file("."))
  .settings(
    name := "code-examples",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    scalacOptions ++= Seq(
      "-Ykind-projector:underscores",
    ),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "requests" % "0.8.0",
    )
<<<<<<< HEAD
  )
=======
  )
>>>>>>> d35be0f (:rocket: slides)
