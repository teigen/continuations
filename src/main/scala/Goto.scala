import scala.util.continuations._

object Goto extends Run {
	
	/* based on code from http://vimeo.com/13304075 about 37 minutes*/ 
	
	val labels = new collection.mutable.HashMap[String, Unit => Unit]
	
	def label(name:String) = shift { 
		k:(Unit => Unit) =>
			labels(name) = k
			k()
	}
	
	def goto(label:String) = shift {
		k:(Unit => Unit) =>
			labels(label)()
	}
	
	def cpsUnit: Unit @suspendable = ()
	
	def run = reset {
		var i = 0
		println("begin")
		label("loop")
		println(i)
		i += 1
		if(i < 5)
			goto("loop")
		else
			cpsUnit
		println("done")
	}
	
	/* 
	Problem: is there any way to avoid the "cpsUnit" ?
	without the else block the compiler fails with
	
	[error]  found   : Unit
	[error]  required: Unit @scala.util.continuations.cpsParam[Unit,Unit]
	[error] 		if(i < 5)
	[error] 		^
	
	some background:
		http://www.scala-lang.org/node/8039
		http://scala-programming-language.1934581.n4.nabble.com/error-then-and-else-parts-must-both-be-cps-code-or-neither-of-them-td2288582.html				
	*/
}