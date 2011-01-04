import sbt._

class Project(info:ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {
	val continuations = compilerPlugin("org.scala-lang.plugins" % "continuations" % buildScalaVersion)	
	override def compileOptions = super.compileOptions ++ compileOptions("-P:continuations:enable")
}