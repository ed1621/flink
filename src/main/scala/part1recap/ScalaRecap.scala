package part1recap

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

object ScalaRecap {

  val abool: Boolean = false
  var aVar: Int = 56
  aVar += 1

  //expression
  val anIf = if(2>3) "bigger" else "smaller"

  val theUnit = println("hello") // Unit === "void"

  //OOP
  class Animal
  class Cat extends Animal
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  // inheritance: extends <= 1 class but inherit from >= 0 traits
  class Crocodile extends Animal with Carnivore {
    override def eat(animal: Animal): Unit = println(s"eating $animal")
  }

  //singleton
  object MySingleton

  // companions
  object Carnivore

  // case class
  case class Person(name: String, age: Int)

  //generics
  class MyList[A] // variance mods

  //method notation
  val three = 1 + 2
  val three_2 = 1.+(2)

  // FP
  val incrementer: Int => Int = x => x + 1
  val incremented: Int = incrementer(4)

  //map flatmap filter
  val processedList = List(1,2,3).map(incrementer)
  val aLongerList = List(1,2,3).flatMap(x => List(x, x + 1))

  // for-comprehensions
  val checkerboard = List(1,2,3).flatMap(n => List('a', 'b', 'c').map(c => (n, c)))
  val checkerboard_v2 = for {
    n <- List(1,2,3)
    c <- List('a', 'b', 'c')
  } yield (n,c)

  // options and try
  val aoption: Option[Int] = Option(43)
  val doubleOption = aoption.map(_ * 2)

  val anAttempt: Try[Int] = Try(12)
  val modifyAttempt = anAttempt.map(_ * 10)

  // pattern matching
  val anUnknown: Any = 45
  val medal = anUnknown match {
    case 1 => "gold"
    case 2 => "silver"
    case 3 => "bronze"
    case _ => "no medal"
  }

  val optionDesc = aoption match {
    case Some(value) => s"the option is not empty: $value"
    case None => "the option is empty"
  }

  // futures
  implicit val ecP: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val aFuture = Future(1 + 41)

  //register callback when it finishes
  val aPartialFunction: PartialFunction[Try[Int], Unit] = {
    case Success(value) => println(s"the async meaning of life it $value")
    case Failure(exception) => println(s"the meaning of value failed: $exception")
  }

  aFuture.onComplete(aPartialFunction)

  // map, flatmap, filter, ....
  val doubledAsyncMOL: Future[Int] = aFuture.map(_ * 2)

  // implicits
  // 1 - implicit arguments and values
  implicit val timeout: Int = 3000 // scala 3 : implicit val == given instance
  def setTimeout(f: () => Unit)(implicit tout: Int) = { // scala 3 : implicit tout: Int == using tout: Int
     Thread.sleep(tout)
    f()
  }

  setTimeout(() => println("timeout")) // (timeout)

  // 2 - extension methods
  implicit class MyRichInt(number: Int) { // scala 3 : implicit class === extension
    def isEven: Boolean = number % 2 == 0
  }

  val is2Even = 2.isEven // wraps the number in the MyRichInt class

  // 3 - conversions - DISCOURAGED
  implicit def string2Person(name: String): Person = Person(name, 57) // given Conversion[String, Person] = Person(_, 57) ... I think?

  val daniel: Person = "Daniel" // runs string2Person("Daniel")

  //instructions vs expressions

  def main(args: Array[String]): Unit = {

  }

}
