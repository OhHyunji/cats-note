name := "cats-note"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1"

scalacOptions ++= Seq(
  "-language:higherKinds"
)
