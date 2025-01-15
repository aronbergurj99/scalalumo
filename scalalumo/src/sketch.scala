package scalalumo

import scala.quoted.*
import scala.annotation.unused
import scala.language.implicitConversions

extension (inline lumoUtil: LumoUtil) {
  inline def css: String =
    ${ slumoImpl('lumoUtil)}
}

def methodNameToLumoUtilitiesClass(rawName: String) = {
  rawName.replace("_", "-")
}

implicit inline def slumo(inline lumoUtil: LumoUtil): String =
  ${ slumoImpl('lumoUtil)}

def slumoImpl(lumoExpr: Expr[LumoUtil])(using Quotes): Expr[String] = {
  import quotes.reflect.*

  def extractClassNames(term: Term, prefix: String = ""): List[String] = {
    var stack = List((term, prefix))
    var classNames = List.empty[String]

    while (stack.nonEmpty) {
      stack.headOption.foreach { (currentTerm, currentPrefix ) =>
        println(s"term: $currentTerm, prefix: $currentPrefix")
        stack = stack.drop(1)

        currentTerm match
          case Inlined(_, _, inner) => {
            stack = (inner, currentPrefix) :: stack
          }
          case Select(inner, name) => {
            val methodName = methodNameToLumoUtilitiesClass(name)
            val className = s"$currentPrefix${methodName}"
            classNames = classNames :+ className
            stack = (inner, currentPrefix) :: stack
          }
          case Ident("lumo") => {}
          case Ident("lumobps") => {}
          case Apply(Select(inner, name), args) => {
            val methodName = methodNameToLumoUtilitiesClass(name)
            println(s"inside args: $args")
            val innerClasses = args.flatMap(arg => extractClassNames(arg, s"$currentPrefix${methodName}:"))
            classNames = classNames ++ innerClasses
            stack = (inner, currentPrefix) :: stack
          }
          case unexpectedTerm =>
            report.errorAndAbort(s"Unexpected term: $unexpectedTerm")
      }
    }

    classNames
  }

  val term = lumoExpr.asTerm
  val classNames = extractClassNames(term)
  val combinedClasses = classNames.mkString(" ")
  Expr(combinedClasses)
}

case class LumoUtil() {
  def bg_base: LumoUtil = this
  def bg_transparent: LumoUtil = this
  def flex_col: LumoUtil = this

  def md(@unused styles: BreakPoints): LumoUtil = this
}

case class BreakPoints() {
  def flex_col: BreakPoints = this
}

val lumo = LumoUtil()
val lumobps = BreakPoints()

