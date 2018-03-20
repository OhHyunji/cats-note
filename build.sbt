name := "cats-note"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.1",
  "com.twitter" %% "finagle-http" % "18.3.0"
)

scalacOptions ++= Seq(
  "-language:higherKinds"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")