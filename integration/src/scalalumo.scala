package scalalumo

import scala.quoted.*
import scala.annotation.unused
import scala.language.implicitConversions

extension (inline lumoUtil: LumoUtil) {
  inline def css: String =
    ${ slumoImpl('lumoUtil)}
}

implicit inline def slumo(inline lumoUtil: LumoUtil): String =
  ${ slumoImpl('lumoUtil)}

def convertCamelCaseToSnakeCase(methodName: String): String = {
  val step1 = "[A-Z]".r.replaceAllIn(
    methodName,
    m => s"_${m.group(0).toLowerCase}"
  )

  val step2 = "([a-z]+)([0-9]+)".r.replaceAllIn(
    step1,
    m => {
      val p1 = m.group(1)
      val p2 = m.group(2)
      s"${p1}_${p2}"
    }
  )

  val step3 = "_([a-z]+)([0-9]+)".r.replaceAllIn(
    step2,
    m => {
      val p1 = m.group(1)
      val p2 = m.group(2)
      s"_${p1}_${p2}"
    }
  )

  step3.toLowerCase
}

def replaceNegPrefix(input: String): String =
  if input.startsWith("neg-") then input.replaceFirst("^neg-", "-")
  else input

def methodNameToLumoUtilitiesClass(rawName: String) = {
  if (rawName == "xxl")
  then "2xl"
  else replaceNegPrefix(convertCamelCaseToSnakeCase(rawName).replace("_", "-"))
}

def slumoImpl(lumoExpr: Expr[LumoUtil])(using Quotes): Expr[String] = {
  import quotes.reflect.*

  def extractClassNames(term: Term, prefix: String = ""): List[String] = {
    var stack = List((term, prefix))
    var classNames = List.empty[String]

    while (stack.nonEmpty) {
      stack.headOption.foreach { (currentTerm, currentPrefix ) =>
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
          case Ident("lumobp") => {}
          case Apply(Select(inner, name), args) => {
            val methodName = methodNameToLumoUtilitiesClass(name)
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
  val classNames = extractClassNames(term).reverse
  val combinedClasses = classNames.mkString(" ")
  Expr(combinedClasses)
}


val lumo   = LumoUtil()
val lumobp = BreakPoint()

case class LumoUtil() {
    def xxl(@unused styles: BreakPoint): LumoUtil = this
    def xl(@unused styles: BreakPoint): LumoUtil = this
    def lg(@unused styles: BreakPoint): LumoUtil = this
    def md(@unused styles: BreakPoint): LumoUtil = this
    def sm(@unused styles: BreakPoint): LumoUtil = this

    /**
    * class: sr-only
    */
    def srOnly: LumoUtil = this
    /**
    * class: bg-base
    */
    def bgBase: LumoUtil = this
    /**
    * class: bg-transparent
    */
    def bgTransparent: LumoUtil = this
    /**
    * class: bg-tint
    */
    def bgTint: LumoUtil = this
    /**
    * class: bg-tint-90
    */
    def bgTint90: LumoUtil = this
    /**
    * class: bg-tint-80
    */
    def bgTint80: LumoUtil = this
    /**
    * class: bg-tint-70
    */
    def bgTint70: LumoUtil = this
    /**
    * class: bg-tint-60
    */
    def bgTint60: LumoUtil = this
    /**
    * class: bg-tint-50
    */
    def bgTint50: LumoUtil = this
    /**
    * class: bg-tint-40
    */
    def bgTint40: LumoUtil = this
    /**
    * class: bg-tint-30
    */
    def bgTint30: LumoUtil = this
    /**
    * class: bg-tint-20
    */
    def bgTint20: LumoUtil = this
    /**
    * class: bg-tint-10
    */
    def bgTint10: LumoUtil = this
    /**
    * class: bg-tint-5
    */
    def bgTint5: LumoUtil = this
    /**
    * class: bg-shade
    */
    def bgShade: LumoUtil = this
    /**
    * class: bg-shade-90
    */
    def bgShade90: LumoUtil = this
    /**
    * class: bg-shade-80
    */
    def bgShade80: LumoUtil = this
    /**
    * class: bg-shade-70
    */
    def bgShade70: LumoUtil = this
    /**
    * class: bg-shade-60
    */
    def bgShade60: LumoUtil = this
    /**
    * class: bg-shade-50
    */
    def bgShade50: LumoUtil = this
    /**
    * class: bg-shade-40
    */
    def bgShade40: LumoUtil = this
    /**
    * class: bg-shade-30
    */
    def bgShade30: LumoUtil = this
    /**
    * class: bg-shade-20
    */
    def bgShade20: LumoUtil = this
    /**
    * class: bg-shade-10
    */
    def bgShade10: LumoUtil = this
    /**
    * class: bg-shade-5
    */
    def bgShade5: LumoUtil = this
    /**
    * class: bg-contrast
    */
    def bgContrast: LumoUtil = this
    /**
    * class: bg-contrast-90
    */
    def bgContrast90: LumoUtil = this
    /**
    * class: bg-contrast-80
    */
    def bgContrast80: LumoUtil = this
    /**
    * class: bg-contrast-70
    */
    def bgContrast70: LumoUtil = this
    /**
    * class: bg-contrast-60
    */
    def bgContrast60: LumoUtil = this
    /**
    * class: bg-contrast-50
    */
    def bgContrast50: LumoUtil = this
    /**
    * class: bg-contrast-40
    */
    def bgContrast40: LumoUtil = this
    /**
    * class: bg-contrast-30
    */
    def bgContrast30: LumoUtil = this
    /**
    * class: bg-contrast-20
    */
    def bgContrast20: LumoUtil = this
    /**
    * class: bg-contrast-10
    */
    def bgContrast10: LumoUtil = this
    /**
    * class: bg-contrast-5
    */
    def bgContrast5: LumoUtil = this
    /**
    * class: bg-primary
    */
    def bgPrimary: LumoUtil = this
    /**
    * class: bg-primary-50
    */
    def bgPrimary50: LumoUtil = this
    /**
    * class: bg-primary-10
    */
    def bgPrimary10: LumoUtil = this
    /**
    * class: bg-error
    */
    def bgError: LumoUtil = this
    /**
    * class: bg-error-50
    */
    def bgError50: LumoUtil = this
    /**
    * class: bg-error-10
    */
    def bgError10: LumoUtil = this
    /**
    * class: bg-success
    */
    def bgSuccess: LumoUtil = this
    /**
    * class: bg-success-50
    */
    def bgSuccess50: LumoUtil = this
    /**
    * class: bg-success-10
    */
    def bgSuccess10: LumoUtil = this
    /**
    * class: bg-warning
    */
    def bgWarning: LumoUtil = this
    /**
    * class: bg-warning-10
    */
    def bgWarning10: LumoUtil = this
    /**
    * class: border-0
    */
    def border0: LumoUtil = this
    /**
    * class: border-dashed
    */
    def borderDashed: LumoUtil = this
    /**
    * class: border-dotted
    */
    def borderDotted: LumoUtil = this
    /**
    * class: border
    */
    def border: LumoUtil = this
    /**
    * class: border-b
    */
    def borderB: LumoUtil = this
    /**
    * class: border-e
    */
    def borderE: LumoUtil = this
    /**
    * class: border-l
    */
    def borderL: LumoUtil = this
    /**
    * class: border-r
    */
    def borderR: LumoUtil = this
    /**
    * class: border-s
    */
    def borderS: LumoUtil = this
    /**
    * class: border-t
    */
    def borderT: LumoUtil = this
    /**
    * class: border-contrast
    */
    def borderContrast: LumoUtil = this
    /**
    * class: border-contrast-90
    */
    def borderContrast90: LumoUtil = this
    /**
    * class: border-contrast-80
    */
    def borderContrast80: LumoUtil = this
    /**
    * class: border-contrast-70
    */
    def borderContrast70: LumoUtil = this
    /**
    * class: border-contrast-60
    */
    def borderContrast60: LumoUtil = this
    /**
    * class: border-contrast-50
    */
    def borderContrast50: LumoUtil = this
    /**
    * class: border-contrast-40
    */
    def borderContrast40: LumoUtil = this
    /**
    * class: border-contrast-30
    */
    def borderContrast30: LumoUtil = this
    /**
    * class: border-contrast-20
    */
    def borderContrast20: LumoUtil = this
    /**
    * class: border-contrast-10
    */
    def borderContrast10: LumoUtil = this
    /**
    * class: border-contrast-5
    */
    def borderContrast5: LumoUtil = this
    /**
    * class: border-primary
    */
    def borderPrimary: LumoUtil = this
    /**
    * class: border-primary-50
    */
    def borderPrimary50: LumoUtil = this
    /**
    * class: border-primary-10
    */
    def borderPrimary10: LumoUtil = this
    /**
    * class: border-error
    */
    def borderError: LumoUtil = this
    /**
    * class: border-error-50
    */
    def borderError50: LumoUtil = this
    /**
    * class: border-error-10
    */
    def borderError10: LumoUtil = this
    /**
    * class: border-success
    */
    def borderSuccess: LumoUtil = this
    /**
    * class: border-success-50
    */
    def borderSuccess50: LumoUtil = this
    /**
    * class: border-success-10
    */
    def borderSuccess10: LumoUtil = this
    /**
    * class: border-warning
    */
    def borderWarning: LumoUtil = this
    /**
    * class: border-warning-strong
    */
    def borderWarningStrong: LumoUtil = this
    /**
    * class: border-warning-10
    */
    def borderWarning10: LumoUtil = this
    /**
    * class: rounded-none
    */
    def roundedNone: LumoUtil = this
    /**
    * class: rounded-s
    */
    def roundedS: LumoUtil = this
    /**
    * class: rounded-m
    */
    def roundedM: LumoUtil = this
    /**
    * class: rounded-l
    */
    def roundedL: LumoUtil = this
    /**
    * class: rounded-full
    */
    def roundedFull: LumoUtil = this
    /**
    * class: backdrop-blur-none
    */
    def backdropBlurNone: LumoUtil = this
    /**
    * class: backdrop-blur-sm
    */
    def backdropBlurSm: LumoUtil = this
    /**
    * class: backdrop-blur
    */
    def backdropBlur: LumoUtil = this
    /**
    * class: backdrop-blur-md
    */
    def backdropBlurMd: LumoUtil = this
    /**
    * class: backdrop-blur-lg
    */
    def backdropBlurLg: LumoUtil = this
    /**
    * class: backdrop-blur-xl
    */
    def backdropBlurXl: LumoUtil = this
    /**
    * class: backdrop-blur-2xl
    */
    def backdropBlur2xl: LumoUtil = this
    /**
    * class: backdrop-blur-3xl
    */
    def backdropBlur3xl: LumoUtil = this
    /**
    * class: content-center
    */
    def contentCenter: LumoUtil = this
    /**
    * class: content-end
    */
    def contentEnd: LumoUtil = this
    /**
    * class: content-start
    */
    def contentStart: LumoUtil = this
    /**
    * class: content-around
    */
    def contentAround: LumoUtil = this
    /**
    * class: content-between
    */
    def contentBetween: LumoUtil = this
    /**
    * class: content-evenly
    */
    def contentEvenly: LumoUtil = this
    /**
    * class: content-stretch
    */
    def contentStretch: LumoUtil = this
    /**
    * class: items-baseline
    */
    def itemsBaseline: LumoUtil = this
    /**
    * class: items-center
    */
    def itemsCenter: LumoUtil = this
    /**
    * class: items-end
    */
    def itemsEnd: LumoUtil = this
    /**
    * class: items-start
    */
    def itemsStart: LumoUtil = this
    /**
    * class: items-stretch
    */
    def itemsStretch: LumoUtil = this
    /**
    * class: self-auto
    */
    def selfAuto: LumoUtil = this
    /**
    * class: self-baseline
    */
    def selfBaseline: LumoUtil = this
    /**
    * class: self-center
    */
    def selfCenter: LumoUtil = this
    /**
    * class: self-end
    */
    def selfEnd: LumoUtil = this
    /**
    * class: self-start
    */
    def selfStart: LumoUtil = this
    /**
    * class: self-stretch
    */
    def selfStretch: LumoUtil = this
    /**
    * class: flex-1
    */
    def flex1: LumoUtil = this
    /**
    * class: flex-auto
    */
    def flexAuto: LumoUtil = this
    /**
    * class: flex-none
    */
    def flexNone: LumoUtil = this
    /**
    * class: flex-col
    */
    def flexCol: LumoUtil = this
    /**
    * class: flex-col-reverse
    */
    def flexColReverse: LumoUtil = this
    /**
    * class: flex-row
    */
    def flexRow: LumoUtil = this
    /**
    * class: flex-row-reverse
    */
    def flexRowReverse: LumoUtil = this
    /**
    * class: flex-grow
    */
    def flexGrow: LumoUtil = this
    /**
    * class: flex-grow-0
    */
    def flexGrow0: LumoUtil = this
    /**
    * class: flex-shrink
    */
    def flexShrink: LumoUtil = this
    /**
    * class: flex-shrink-0
    */
    def flexShrink0: LumoUtil = this
    /**
    * class: flex-nowrap
    */
    def flexNowrap: LumoUtil = this
    /**
    * class: flex-wrap
    */
    def flexWrap: LumoUtil = this
    /**
    * class: flex-wrap-reverse
    */
    def flexWrapReverse: LumoUtil = this
    /**
    * class: gap-xs
    */
    def gapXs: LumoUtil = this
    /**
    * class: gap-s
    */
    def gapS: LumoUtil = this
    /**
    * class: gap-m
    */
    def gapM: LumoUtil = this
    /**
    * class: gap-l
    */
    def gapL: LumoUtil = this
    /**
    * class: gap-xl
    */
    def gapXl: LumoUtil = this
    /**
    * class: gap-x-xs
    */
    def gapXXs: LumoUtil = this
    /**
    * class: gap-x-s
    */
    def gapXS: LumoUtil = this
    /**
    * class: gap-x-m
    */
    def gapXM: LumoUtil = this
    /**
    * class: gap-x-l
    */
    def gapXL: LumoUtil = this
    /**
    * class: gap-x-xl
    */
    def gapXXl: LumoUtil = this
    /**
    * class: gap-y-xs
    */
    def gapYXs: LumoUtil = this
    /**
    * class: gap-y-s
    */
    def gapYS: LumoUtil = this
    /**
    * class: gap-y-m
    */
    def gapYM: LumoUtil = this
    /**
    * class: gap-y-l
    */
    def gapYL: LumoUtil = this
    /**
    * class: gap-y-xl
    */
    def gapYXl: LumoUtil = this
    /**
    * class: grid-flow-col
    */
    def gridFlowCol: LumoUtil = this
    /**
    * class: grid-flow-row
    */
    def gridFlowRow: LumoUtil = this
    /**
    * class: grid-cols-1
    */
    def gridCols1: LumoUtil = this
    /**
    * class: grid-cols-2
    */
    def gridCols2: LumoUtil = this
    /**
    * class: grid-cols-3
    */
    def gridCols3: LumoUtil = this
    /**
    * class: grid-cols-4
    */
    def gridCols4: LumoUtil = this
    /**
    * class: grid-cols-5
    */
    def gridCols5: LumoUtil = this
    /**
    * class: grid-cols-6
    */
    def gridCols6: LumoUtil = this
    /**
    * class: grid-cols-7
    */
    def gridCols7: LumoUtil = this
    /**
    * class: grid-cols-8
    */
    def gridCols8: LumoUtil = this
    /**
    * class: grid-cols-9
    */
    def gridCols9: LumoUtil = this
    /**
    * class: grid-cols-10
    */
    def gridCols10: LumoUtil = this
    /**
    * class: grid-cols-11
    */
    def gridCols11: LumoUtil = this
    /**
    * class: grid-cols-12
    */
    def gridCols12: LumoUtil = this
    /**
    * class: grid-rows-1
    */
    def gridRows1: LumoUtil = this
    /**
    * class: grid-rows-2
    */
    def gridRows2: LumoUtil = this
    /**
    * class: grid-rows-3
    */
    def gridRows3: LumoUtil = this
    /**
    * class: grid-rows-4
    */
    def gridRows4: LumoUtil = this
    /**
    * class: grid-rows-5
    */
    def gridRows5: LumoUtil = this
    /**
    * class: grid-rows-6
    */
    def gridRows6: LumoUtil = this
    /**
    * class: justify-center
    */
    def justifyCenter: LumoUtil = this
    /**
    * class: justify-end
    */
    def justifyEnd: LumoUtil = this
    /**
    * class: justify-start
    */
    def justifyStart: LumoUtil = this
    /**
    * class: justify-around
    */
    def justifyAround: LumoUtil = this
    /**
    * class: justify-between
    */
    def justifyBetween: LumoUtil = this
    /**
    * class: justify-evenly
    */
    def justifyEvenly: LumoUtil = this
    /**
    * class: col-span-1
    */
    def colSpan1: LumoUtil = this
    /**
    * class: col-span-2
    */
    def colSpan2: LumoUtil = this
    /**
    * class: col-span-3
    */
    def colSpan3: LumoUtil = this
    /**
    * class: col-span-4
    */
    def colSpan4: LumoUtil = this
    /**
    * class: col-span-5
    */
    def colSpan5: LumoUtil = this
    /**
    * class: col-span-6
    */
    def colSpan6: LumoUtil = this
    /**
    * class: col-span-7
    */
    def colSpan7: LumoUtil = this
    /**
    * class: col-span-8
    */
    def colSpan8: LumoUtil = this
    /**
    * class: col-span-9
    */
    def colSpan9: LumoUtil = this
    /**
    * class: col-span-10
    */
    def colSpan10: LumoUtil = this
    /**
    * class: col-span-11
    */
    def colSpan11: LumoUtil = this
    /**
    * class: col-span-12
    */
    def colSpan12: LumoUtil = this
    /**
    * class: col-span-full
    */
    def colSpanFull: LumoUtil = this
    /**
    * class: row-span-1
    */
    def rowSpan1: LumoUtil = this
    /**
    * class: row-span-2
    */
    def rowSpan2: LumoUtil = this
    /**
    * class: row-span-3
    */
    def rowSpan3: LumoUtil = this
    /**
    * class: row-span-4
    */
    def rowSpan4: LumoUtil = this
    /**
    * class: row-span-5
    */
    def rowSpan5: LumoUtil = this
    /**
    * class: row-span-6
    */
    def rowSpan6: LumoUtil = this
    /**
    * class: row-span-full
    */
    def rowSpanFull: LumoUtil = this
    /**
    * class: aspect-square
    */
    def aspectSquare: LumoUtil = this
    /**
    * class: aspect-video
    */
    def aspectVideo: LumoUtil = this
    /**
    * class: box-border
    */
    def boxBorder: LumoUtil = this
    /**
    * class: box-content
    */
    def boxContent: LumoUtil = this
    /**
    * class: block
    */
    def block: LumoUtil = this
    /**
    * class: flex
    */
    def flex: LumoUtil = this
    /**
    * class: grid
    */
    def grid: LumoUtil = this
    /**
    * class: hidden
    */
    def hidden: LumoUtil = this
    /**
    * class: inline
    */
    def inline: LumoUtil = this
    /**
    * class: inline-block
    */
    def inlineBlock: LumoUtil = this
    /**
    * class: inline-flex
    */
    def inlineFlex: LumoUtil = this
    /**
    * class: inline-grid
    */
    def inlineGrid: LumoUtil = this
    /**
    * class: overflow-auto
    */
    def overflowAuto: LumoUtil = this
    /**
    * class: overflow-hidden
    */
    def overflowHidden: LumoUtil = this
    /**
    * class: overflow-scroll
    */
    def overflowScroll: LumoUtil = this
    /**
    * class: absolute
    */
    def absolute: LumoUtil = this
    /**
    * class: fixed
    */
    def fixed: LumoUtil = this
    /**
    * class: static
    */
    def static: LumoUtil = this
    /**
    * class: sticky
    */
    def sticky: LumoUtil = this
    /**
    * class: relative
    */
    def relative: LumoUtil = this
    /**
    * class: neg-bottom-xs
    */
    def negBottomXs: LumoUtil = this
    /**
    * class: neg-bottom-s
    */
    def negBottomS: LumoUtil = this
    /**
    * class: neg-bottom-m
    */
    def negBottomM: LumoUtil = this
    /**
    * class: neg-bottom-l
    */
    def negBottomL: LumoUtil = this
    /**
    * class: neg-bottom-xl
    */
    def negBottomXl: LumoUtil = this
    /**
    * class: neg-bottom-full
    */
    def negBottomFull: LumoUtil = this
    /**
    * class: bottom-0
    */
    def bottom0: LumoUtil = this
    /**
    * class: bottom-xs
    */
    def bottomXs: LumoUtil = this
    /**
    * class: bottom-s
    */
    def bottomS: LumoUtil = this
    /**
    * class: bottom-m
    */
    def bottomM: LumoUtil = this
    /**
    * class: bottom-l
    */
    def bottomL: LumoUtil = this
    /**
    * class: bottom-xl
    */
    def bottomXl: LumoUtil = this
    /**
    * class: bottom-auto
    */
    def bottomAuto: LumoUtil = this
    /**
    * class: bottom-full
    */
    def bottomFull: LumoUtil = this
    /**
    * class: neg-end-xs
    */
    def negEndXs: LumoUtil = this
    /**
    * class: neg-end-s
    */
    def negEndS: LumoUtil = this
    /**
    * class: neg-end-m
    */
    def negEndM: LumoUtil = this
    /**
    * class: neg-end-l
    */
    def negEndL: LumoUtil = this
    /**
    * class: neg-end-xl
    */
    def negEndXl: LumoUtil = this
    /**
    * class: neg-end-full
    */
    def negEndFull: LumoUtil = this
    /**
    * class: end-0
    */
    def end0: LumoUtil = this
    /**
    * class: end-xs
    */
    def endXs: LumoUtil = this
    /**
    * class: end-s
    */
    def endS: LumoUtil = this
    /**
    * class: end-m
    */
    def endM: LumoUtil = this
    /**
    * class: end-l
    */
    def endL: LumoUtil = this
    /**
    * class: end-xl
    */
    def endXl: LumoUtil = this
    /**
    * class: end-auto
    */
    def endAuto: LumoUtil = this
    /**
    * class: end-full
    */
    def endFull: LumoUtil = this
    /**
    * class: neg-start-xs
    */
    def negStartXs: LumoUtil = this
    /**
    * class: neg-start-s
    */
    def negStartS: LumoUtil = this
    /**
    * class: neg-start-m
    */
    def negStartM: LumoUtil = this
    /**
    * class: neg-start-l
    */
    def negStartL: LumoUtil = this
    /**
    * class: neg-start-xl
    */
    def negStartXl: LumoUtil = this
    /**
    * class: neg-start-full
    */
    def negStartFull: LumoUtil = this
    /**
    * class: start-0
    */
    def start0: LumoUtil = this
    /**
    * class: start-xs
    */
    def startXs: LumoUtil = this
    /**
    * class: start-s
    */
    def startS: LumoUtil = this
    /**
    * class: start-m
    */
    def startM: LumoUtil = this
    /**
    * class: start-l
    */
    def startL: LumoUtil = this
    /**
    * class: start-xl
    */
    def startXl: LumoUtil = this
    /**
    * class: start-auto
    */
    def startAuto: LumoUtil = this
    /**
    * class: start-full
    */
    def startFull: LumoUtil = this
    /**
    * class: neg-top-xs
    */
    def negTopXs: LumoUtil = this
    /**
    * class: neg-top-s
    */
    def negTopS: LumoUtil = this
    /**
    * class: neg-top-m
    */
    def negTopM: LumoUtil = this
    /**
    * class: neg-top-l
    */
    def negTopL: LumoUtil = this
    /**
    * class: neg-top-xl
    */
    def negTopXl: LumoUtil = this
    /**
    * class: neg-top-full
    */
    def negTopFull: LumoUtil = this
    /**
    * class: top-0
    */
    def top0: LumoUtil = this
    /**
    * class: top-xs
    */
    def topXs: LumoUtil = this
    /**
    * class: top-s
    */
    def topS: LumoUtil = this
    /**
    * class: top-m
    */
    def topM: LumoUtil = this
    /**
    * class: top-l
    */
    def topL: LumoUtil = this
    /**
    * class: top-xl
    */
    def topXl: LumoUtil = this
    /**
    * class: top-auto
    */
    def topAuto: LumoUtil = this
    /**
    * class: top-full
    */
    def topFull: LumoUtil = this
    /**
    * class: invisible
    */
    def invisible: LumoUtil = this
    /**
    * class: visible
    */
    def visible: LumoUtil = this
    /**
    * class: z-0
    */
    def z0: LumoUtil = this
    /**
    * class: z-10
    */
    def z10: LumoUtil = this
    /**
    * class: z-20
    */
    def z20: LumoUtil = this
    /**
    * class: z-30
    */
    def z30: LumoUtil = this
    /**
    * class: z-40
    */
    def z40: LumoUtil = this
    /**
    * class: z-50
    */
    def z50: LumoUtil = this
    /**
    * class: z-auto
    */
    def zAuto: LumoUtil = this
    /**
    * class: shadow-none
    */
    def shadowNone: LumoUtil = this
    /**
    * class: shadow-xs
    */
    def shadowXs: LumoUtil = this
    /**
    * class: shadow-s
    */
    def shadowS: LumoUtil = this
    /**
    * class: shadow-m
    */
    def shadowM: LumoUtil = this
    /**
    * class: shadow-l
    */
    def shadowL: LumoUtil = this
    /**
    * class: shadow-xl
    */
    def shadowXl: LumoUtil = this
    /**
    * class: h-0
    */
    def h0: LumoUtil = this
    /**
    * class: h-xs
    */
    def hXs: LumoUtil = this
    /**
    * class: h-s
    */
    def hS: LumoUtil = this
    /**
    * class: h-m
    */
    def hM: LumoUtil = this
    /**
    * class: h-l
    */
    def hL: LumoUtil = this
    /**
    * class: h-xl
    */
    def hXl: LumoUtil = this
    /**
    * class: h-auto
    */
    def hAuto: LumoUtil = this
    /**
    * class: h-full
    */
    def hFull: LumoUtil = this
    /**
    * class: h-screen
    */
    def hScreen: LumoUtil = this
    /**
    * class: max-h-full
    */
    def maxHFull: LumoUtil = this
    /**
    * class: max-h-screen
    */
    def maxHScreen: LumoUtil = this
    /**
    * class: min-h-0
    */
    def minH0: LumoUtil = this
    /**
    * class: min-h-full
    */
    def minHFull: LumoUtil = this
    /**
    * class: min-h-screen
    */
    def minHScreen: LumoUtil = this
    /**
    * class: icon-s
    */
    def iconS: LumoUtil = this
    /**
    * class: icon-m
    */
    def iconM: LumoUtil = this
    /**
    * class: icon-l
    */
    def iconL: LumoUtil = this
    /**
    * class: w-xs
    */
    def wXs: LumoUtil = this
    /**
    * class: w-s
    */
    def wS: LumoUtil = this
    /**
    * class: w-m
    */
    def wM: LumoUtil = this
    /**
    * class: w-l
    */
    def wL: LumoUtil = this
    /**
    * class: w-xl
    */
    def wXl: LumoUtil = this
    /**
    * class: w-auto
    */
    def wAuto: LumoUtil = this
    /**
    * class: w-full
    */
    def wFull: LumoUtil = this
    /**
    * class: max-w-full
    */
    def maxWFull: LumoUtil = this
    /**
    * class: max-w-screen-sm
    */
    def maxWScreenSm: LumoUtil = this
    /**
    * class: max-w-screen-md
    */
    def maxWScreenMd: LumoUtil = this
    /**
    * class: max-w-screen-lg
    */
    def maxWScreenLg: LumoUtil = this
    /**
    * class: max-w-screen-xl
    */
    def maxWScreenXl: LumoUtil = this
    /**
    * class: max-w-screen-2xl
    */
    def maxWScreen2xl: LumoUtil = this
    /**
    * class: min-w-0
    */
    def minW0: LumoUtil = this
    /**
    * class: min-w-full
    */
    def minWFull: LumoUtil = this
    /**
    * class: neg-m-xs
    */
    def negMXs: LumoUtil = this
    /**
    * class: neg-m-s
    */
    def negMS: LumoUtil = this
    /**
    * class: neg-m-m
    */
    def negMM: LumoUtil = this
    /**
    * class: neg-m-l
    */
    def negML: LumoUtil = this
    /**
    * class: neg-m-xl
    */
    def negMXl: LumoUtil = this
    /**
    * class: m-0
    */
    def m0: LumoUtil = this
    /**
    * class: m-xs
    */
    def mXs: LumoUtil = this
    /**
    * class: m-s
    */
    def mS: LumoUtil = this
    /**
    * class: m-m
    */
    def mM: LumoUtil = this
    /**
    * class: m-l
    */
    def mL: LumoUtil = this
    /**
    * class: m-xl
    */
    def mXl: LumoUtil = this
    /**
    * class: m-auto
    */
    def mAuto: LumoUtil = this
    /**
    * class: neg-mb-xs
    */
    def negMbXs: LumoUtil = this
    /**
    * class: neg-mb-s
    */
    def negMbS: LumoUtil = this
    /**
    * class: neg-mb-m
    */
    def negMbM: LumoUtil = this
    /**
    * class: neg-mb-l
    */
    def negMbL: LumoUtil = this
    /**
    * class: neg-mb-xl
    */
    def negMbXl: LumoUtil = this
    /**
    * class: mb-0
    */
    def mb0: LumoUtil = this
    /**
    * class: mb-xs
    */
    def mbXs: LumoUtil = this
    /**
    * class: mb-s
    */
    def mbS: LumoUtil = this
    /**
    * class: mb-m
    */
    def mbM: LumoUtil = this
    /**
    * class: mb-l
    */
    def mbL: LumoUtil = this
    /**
    * class: mb-xl
    */
    def mbXl: LumoUtil = this
    /**
    * class: mb-auto
    */
    def mbAuto: LumoUtil = this
    /**
    * class: neg-me-xs
    */
    def negMeXs: LumoUtil = this
    /**
    * class: neg-me-s
    */
    def negMeS: LumoUtil = this
    /**
    * class: neg-me-m
    */
    def negMeM: LumoUtil = this
    /**
    * class: neg-me-l
    */
    def negMeL: LumoUtil = this
    /**
    * class: neg-me-xl
    */
    def negMeXl: LumoUtil = this
    /**
    * class: me-0
    */
    def me0: LumoUtil = this
    /**
    * class: me-xs
    */
    def meXs: LumoUtil = this
    /**
    * class: me-s
    */
    def meS: LumoUtil = this
    /**
    * class: me-m
    */
    def meM: LumoUtil = this
    /**
    * class: me-l
    */
    def meL: LumoUtil = this
    /**
    * class: me-xl
    */
    def meXl: LumoUtil = this
    /**
    * class: me-auto
    */
    def meAuto: LumoUtil = this
    /**
    * class: neg-mx-xs
    */
    def negMxXs: LumoUtil = this
    /**
    * class: neg-mx-s
    */
    def negMxS: LumoUtil = this
    /**
    * class: neg-mx-m
    */
    def negMxM: LumoUtil = this
    /**
    * class: neg-mx-l
    */
    def negMxL: LumoUtil = this
    /**
    * class: neg-mx-xl
    */
    def negMxXl: LumoUtil = this
    /**
    * class: mx-0
    */
    def mx0: LumoUtil = this
    /**
    * class: mx-xs
    */
    def mxXs: LumoUtil = this
    /**
    * class: mx-s
    */
    def mxS: LumoUtil = this
    /**
    * class: mx-m
    */
    def mxM: LumoUtil = this
    /**
    * class: mx-l
    */
    def mxL: LumoUtil = this
    /**
    * class: mx-xl
    */
    def mxXl: LumoUtil = this
    /**
    * class: mx-auto
    */
    def mxAuto: LumoUtil = this
    /**
    * class: neg-ml-xs
    */
    def negMlXs: LumoUtil = this
    /**
    * class: neg-ml-s
    */
    def negMlS: LumoUtil = this
    /**
    * class: neg-ml-m
    */
    def negMlM: LumoUtil = this
    /**
    * class: neg-ml-l
    */
    def negMlL: LumoUtil = this
    /**
    * class: neg-ml-xl
    */
    def negMlXl: LumoUtil = this
    /**
    * class: ml-0
    */
    def ml0: LumoUtil = this
    /**
    * class: ml-xs
    */
    def mlXs: LumoUtil = this
    /**
    * class: ml-s
    */
    def mlS: LumoUtil = this
    /**
    * class: ml-m
    */
    def mlM: LumoUtil = this
    /**
    * class: ml-l
    */
    def mlL: LumoUtil = this
    /**
    * class: ml-xl
    */
    def mlXl: LumoUtil = this
    /**
    * class: ml-auto
    */
    def mlAuto: LumoUtil = this
    /**
    * class: neg-mr-xs
    */
    def negMrXs: LumoUtil = this
    /**
    * class: neg-mr-s
    */
    def negMrS: LumoUtil = this
    /**
    * class: neg-mr-m
    */
    def negMrM: LumoUtil = this
    /**
    * class: neg-mr-l
    */
    def negMrL: LumoUtil = this
    /**
    * class: neg-mr-xl
    */
    def negMrXl: LumoUtil = this
    /**
    * class: mr-0
    */
    def mr0: LumoUtil = this
    /**
    * class: mr-xs
    */
    def mrXs: LumoUtil = this
    /**
    * class: mr-s
    */
    def mrS: LumoUtil = this
    /**
    * class: mr-m
    */
    def mrM: LumoUtil = this
    /**
    * class: mr-l
    */
    def mrL: LumoUtil = this
    /**
    * class: mr-xl
    */
    def mrXl: LumoUtil = this
    /**
    * class: mr-auto
    */
    def mrAuto: LumoUtil = this
    /**
    * class: neg-ms-xs
    */
    def negMsXs: LumoUtil = this
    /**
    * class: neg-ms-s
    */
    def negMsS: LumoUtil = this
    /**
    * class: neg-ms-m
    */
    def negMsM: LumoUtil = this
    /**
    * class: neg-ms-l
    */
    def negMsL: LumoUtil = this
    /**
    * class: neg-ms-xl
    */
    def negMsXl: LumoUtil = this
    /**
    * class: ms-0
    */
    def ms0: LumoUtil = this
    /**
    * class: ms-xs
    */
    def msXs: LumoUtil = this
    /**
    * class: ms-s
    */
    def msS: LumoUtil = this
    /**
    * class: ms-m
    */
    def msM: LumoUtil = this
    /**
    * class: ms-l
    */
    def msL: LumoUtil = this
    /**
    * class: ms-xl
    */
    def msXl: LumoUtil = this
    /**
    * class: ms-auto
    */
    def msAuto: LumoUtil = this
    /**
    * class: neg-mt-xs
    */
    def negMtXs: LumoUtil = this
    /**
    * class: neg-mt-s
    */
    def negMtS: LumoUtil = this
    /**
    * class: neg-mt-m
    */
    def negMtM: LumoUtil = this
    /**
    * class: neg-mt-l
    */
    def negMtL: LumoUtil = this
    /**
    * class: neg-mt-xl
    */
    def negMtXl: LumoUtil = this
    /**
    * class: mt-0
    */
    def mt0: LumoUtil = this
    /**
    * class: mt-xs
    */
    def mtXs: LumoUtil = this
    /**
    * class: mt-s
    */
    def mtS: LumoUtil = this
    /**
    * class: mt-m
    */
    def mtM: LumoUtil = this
    /**
    * class: mt-l
    */
    def mtL: LumoUtil = this
    /**
    * class: mt-xl
    */
    def mtXl: LumoUtil = this
    /**
    * class: mt-auto
    */
    def mtAuto: LumoUtil = this
    /**
    * class: neg-my-xs
    */
    def negMyXs: LumoUtil = this
    /**
    * class: neg-my-s
    */
    def negMyS: LumoUtil = this
    /**
    * class: neg-my-m
    */
    def negMyM: LumoUtil = this
    /**
    * class: neg-my-l
    */
    def negMyL: LumoUtil = this
    /**
    * class: neg-my-xl
    */
    def negMyXl: LumoUtil = this
    /**
    * class: my-0
    */
    def my0: LumoUtil = this
    /**
    * class: my-xs
    */
    def myXs: LumoUtil = this
    /**
    * class: my-s
    */
    def myS: LumoUtil = this
    /**
    * class: my-m
    */
    def myM: LumoUtil = this
    /**
    * class: my-l
    */
    def myL: LumoUtil = this
    /**
    * class: my-xl
    */
    def myXl: LumoUtil = this
    /**
    * class: my-auto
    */
    def myAuto: LumoUtil = this
    /**
    * class: p-0
    */
    def p0: LumoUtil = this
    /**
    * class: p-xs
    */
    def pXs: LumoUtil = this
    /**
    * class: p-s
    */
    def pS: LumoUtil = this
    /**
    * class: p-m
    */
    def pM: LumoUtil = this
    /**
    * class: p-l
    */
    def pL: LumoUtil = this
    /**
    * class: p-xl
    */
    def pXl: LumoUtil = this
    /**
    * class: pb-0
    */
    def pb0: LumoUtil = this
    /**
    * class: pb-xs
    */
    def pbXs: LumoUtil = this
    /**
    * class: pb-s
    */
    def pbS: LumoUtil = this
    /**
    * class: pb-m
    */
    def pbM: LumoUtil = this
    /**
    * class: pb-l
    */
    def pbL: LumoUtil = this
    /**
    * class: pb-xl
    */
    def pbXl: LumoUtil = this
    /**
    * class: pe-0
    */
    def pe0: LumoUtil = this
    /**
    * class: pe-xs
    */
    def peXs: LumoUtil = this
    /**
    * class: pe-s
    */
    def peS: LumoUtil = this
    /**
    * class: pe-m
    */
    def peM: LumoUtil = this
    /**
    * class: pe-l
    */
    def peL: LumoUtil = this
    /**
    * class: pe-xl
    */
    def peXl: LumoUtil = this
    /**
    * class: px-0
    */
    def px0: LumoUtil = this
    /**
    * class: px-xs
    */
    def pxXs: LumoUtil = this
    /**
    * class: px-s
    */
    def pxS: LumoUtil = this
    /**
    * class: px-m
    */
    def pxM: LumoUtil = this
    /**
    * class: px-l
    */
    def pxL: LumoUtil = this
    /**
    * class: px-xl
    */
    def pxXl: LumoUtil = this
    /**
    * class: pl-0
    */
    def pl0: LumoUtil = this
    /**
    * class: pl-xs
    */
    def plXs: LumoUtil = this
    /**
    * class: pl-s
    */
    def plS: LumoUtil = this
    /**
    * class: pl-m
    */
    def plM: LumoUtil = this
    /**
    * class: pl-l
    */
    def plL: LumoUtil = this
    /**
    * class: pl-xl
    */
    def plXl: LumoUtil = this
    /**
    * class: pr-0
    */
    def pr0: LumoUtil = this
    /**
    * class: pr-xs
    */
    def prXs: LumoUtil = this
    /**
    * class: pr-s
    */
    def prS: LumoUtil = this
    /**
    * class: pr-m
    */
    def prM: LumoUtil = this
    /**
    * class: pr-l
    */
    def prL: LumoUtil = this
    /**
    * class: pr-xl
    */
    def prXl: LumoUtil = this
    /**
    * class: ps-0
    */
    def ps0: LumoUtil = this
    /**
    * class: ps-xs
    */
    def psXs: LumoUtil = this
    /**
    * class: ps-s
    */
    def psS: LumoUtil = this
    /**
    * class: ps-m
    */
    def psM: LumoUtil = this
    /**
    * class: ps-l
    */
    def psL: LumoUtil = this
    /**
    * class: ps-xl
    */
    def psXl: LumoUtil = this
    /**
    * class: pt-0
    */
    def pt0: LumoUtil = this
    /**
    * class: pt-xs
    */
    def ptXs: LumoUtil = this
    /**
    * class: pt-s
    */
    def ptS: LumoUtil = this
    /**
    * class: pt-m
    */
    def ptM: LumoUtil = this
    /**
    * class: pt-l
    */
    def ptL: LumoUtil = this
    /**
    * class: pt-xl
    */
    def ptXl: LumoUtil = this
    /**
    * class: py-0
    */
    def py0: LumoUtil = this
    /**
    * class: py-xs
    */
    def pyXs: LumoUtil = this
    /**
    * class: py-s
    */
    def pyS: LumoUtil = this
    /**
    * class: py-m
    */
    def pyM: LumoUtil = this
    /**
    * class: py-l
    */
    def pyL: LumoUtil = this
    /**
    * class: py-xl
    */
    def pyXl: LumoUtil = this
    /**
    * class: transition-none
    */
    def transitionNone: LumoUtil = this
    /**
    * class: transition-all
    */
    def transitionAll: LumoUtil = this
    /**
    * class: transition
    */
    def transition: LumoUtil = this
    /**
    * class: transition-colors
    */
    def transitionColors: LumoUtil = this
    /**
    * class: transition-opacity
    */
    def transitionOpacity: LumoUtil = this
    /**
    * class: transition-shadow
    */
    def transitionShadow: LumoUtil = this
    /**
    * class: transition-transform
    */
    def transitionTransform: LumoUtil = this
    /**
    * class: text-2xs
    */
    def text2xs: LumoUtil = this
    /**
    * class: text-xs
    */
    def textXs: LumoUtil = this
    /**
    * class: text-s
    */
    def textS: LumoUtil = this
    /**
    * class: text-m
    */
    def textM: LumoUtil = this
    /**
    * class: text-l
    */
    def textL: LumoUtil = this
    /**
    * class: text-xl
    */
    def textXl: LumoUtil = this
    /**
    * class: text-2xl
    */
    def text2xl: LumoUtil = this
    /**
    * class: text-3xl
    */
    def text3xl: LumoUtil = this
    /**
    * class: font-thin
    */
    def fontThin: LumoUtil = this
    /**
    * class: font-extralight
    */
    def fontExtralight: LumoUtil = this
    /**
    * class: font-light
    */
    def fontLight: LumoUtil = this
    /**
    * class: font-normal
    */
    def fontNormal: LumoUtil = this
    /**
    * class: font-medium
    */
    def fontMedium: LumoUtil = this
    /**
    * class: font-semibold
    */
    def fontSemibold: LumoUtil = this
    /**
    * class: font-bold
    */
    def fontBold: LumoUtil = this
    /**
    * class: font-extrabold
    */
    def fontExtrabold: LumoUtil = this
    /**
    * class: font-black
    */
    def fontBlack: LumoUtil = this
    /**
    * class: line-clamp-1
    */
    def lineClamp1: LumoUtil = this
    /**
    * class: line-clamp-2
    */
    def lineClamp2: LumoUtil = this
    /**
    * class: line-clamp-3
    */
    def lineClamp3: LumoUtil = this
    /**
    * class: line-clamp-4
    */
    def lineClamp4: LumoUtil = this
    /**
    * class: line-clamp-5
    */
    def lineClamp5: LumoUtil = this
    /**
    * class: line-clamp-6
    */
    def lineClamp6: LumoUtil = this
    /**
    * class: leading-none
    */
    def leadingNone: LumoUtil = this
    /**
    * class: leading-xs
    */
    def leadingXs: LumoUtil = this
    /**
    * class: leading-s
    */
    def leadingS: LumoUtil = this
    /**
    * class: leading-m
    */
    def leadingM: LumoUtil = this
    /**
    * class: list-none
    */
    def listNone: LumoUtil = this
    /**
    * class: text-left
    */
    def textLeft: LumoUtil = this
    /**
    * class: text-center
    */
    def textCenter: LumoUtil = this
    /**
    * class: text-right
    */
    def textRight: LumoUtil = this
    /**
    * class: text-justify
    */
    def textJustify: LumoUtil = this
    /**
    * class: text-header
    */
    def textHeader: LumoUtil = this
    /**
    * class: text-body
    */
    def textBody: LumoUtil = this
    /**
    * class: text-secondary
    */
    def textSecondary: LumoUtil = this
    /**
    * class: text-tertiary
    */
    def textTertiary: LumoUtil = this
    /**
    * class: text-disabled
    */
    def textDisabled: LumoUtil = this
    /**
    * class: text-primary
    */
    def textPrimary: LumoUtil = this
    /**
    * class: text-primary-contrast
    */
    def textPrimaryContrast: LumoUtil = this
    /**
    * class: text-error
    */
    def textError: LumoUtil = this
    /**
    * class: text-error-contrast
    */
    def textErrorContrast: LumoUtil = this
    /**
    * class: text-success
    */
    def textSuccess: LumoUtil = this
    /**
    * class: text-success-contrast
    */
    def textSuccessContrast: LumoUtil = this
    /**
    * class: text-warning
    */
    def textWarning: LumoUtil = this
    /**
    * class: text-warning-contrast
    */
    def textWarningContrast: LumoUtil = this
    /**
    * class: line-through
    */
    def lineThrough: LumoUtil = this
    /**
    * class: no-underline
    */
    def noUnderline: LumoUtil = this
    /**
    * class: underline
    */
    def underline: LumoUtil = this
    /**
    * class: overflow-clip
    */
    def overflowClip: LumoUtil = this
    /**
    * class: overflow-ellipsis
    */
    def overflowEllipsis: LumoUtil = this
    /**
    * class: capitalize
    */
    def capitalize: LumoUtil = this
    /**
    * class: lowercase
    */
    def lowercase: LumoUtil = this
    /**
    * class: uppercase
    */
    def uppercase: LumoUtil = this
    /**
    * class: whitespace-normal
    */
    def whitespaceNormal: LumoUtil = this
    /**
    * class: whitespace-break-spaces
    */
    def whitespaceBreakSpaces: LumoUtil = this
    /**
    * class: whitespace-nowrap
    */
    def whitespaceNowrap: LumoUtil = this
    /**
    * class: whitespace-pre
    */
    def whitespacePre: LumoUtil = this
    /**
    * class: whitespace-pre-line
    */
    def whitespacePreLine: LumoUtil = this
    /**
    * class: whitespace-pre-wrap
    */
    def whitespacePreWrap: LumoUtil = this
}

case class BreakPoint() {
    /**
    * class: items-baseline
    */
    def itemsBaseline: BreakPoint = this
    /**
    * class: items-center
    */
    def itemsCenter: BreakPoint = this
    /**
    * class: items-end
    */
    def itemsEnd: BreakPoint = this
    /**
    * class: items-start
    */
    def itemsStart: BreakPoint = this
    /**
    * class: items-stretch
    */
    def itemsStretch: BreakPoint = this
    /**
    * class: flex-col
    */
    def flexCol: BreakPoint = this
    /**
    * class: flex-row
    */
    def flexRow: BreakPoint = this
    /**
    * class: grid-cols-1
    */
    def gridCols1: BreakPoint = this
    /**
    * class: grid-cols-2
    */
    def gridCols2: BreakPoint = this
    /**
    * class: grid-cols-3
    */
    def gridCols3: BreakPoint = this
    /**
    * class: grid-cols-4
    */
    def gridCols4: BreakPoint = this
    /**
    * class: grid-cols-5
    */
    def gridCols5: BreakPoint = this
    /**
    * class: grid-cols-6
    */
    def gridCols6: BreakPoint = this
    /**
    * class: grid-cols-7
    */
    def gridCols7: BreakPoint = this
    /**
    * class: grid-cols-8
    */
    def gridCols8: BreakPoint = this
    /**
    * class: grid-cols-9
    */
    def gridCols9: BreakPoint = this
    /**
    * class: grid-cols-10
    */
    def gridCols10: BreakPoint = this
    /**
    * class: grid-cols-11
    */
    def gridCols11: BreakPoint = this
    /**
    * class: grid-cols-12
    */
    def gridCols12: BreakPoint = this
    /**
    * class: block
    */
    def block: BreakPoint = this
    /**
    * class: flex
    */
    def flex: BreakPoint = this
    /**
    * class: grid
    */
    def grid: BreakPoint = this
    /**
    * class: hidden
    */
    def hidden: BreakPoint = this
    /**
    * class: inline
    */
    def inline: BreakPoint = this
    /**
    * class: inline-block
    */
    def inlineBlock: BreakPoint = this
    /**
    * class: inline-flex
    */
    def inlineFlex: BreakPoint = this
    /**
    * class: inline-grid
    */
    def inlineGrid: BreakPoint = this
    /**
    * class: absolute
    */
    def absolute: BreakPoint = this
    /**
    * class: fixed
    */
    def fixed: BreakPoint = this
    /**
    * class: relative
    */
    def relative: BreakPoint = this
    /**
    * class: static
    */
    def static: BreakPoint = this
    /**
    * class: sticky
    */
    def sticky: BreakPoint = this
    /**
    * class: text-2xs
    */
    def text2xs: BreakPoint = this
    /**
    * class: text-xs
    */
    def textXs: BreakPoint = this
    /**
    * class: text-s
    */
    def textS: BreakPoint = this
    /**
    * class: text-m
    */
    def textM: BreakPoint = this
    /**
    * class: text-l
    */
    def textL: BreakPoint = this
    /**
    * class: text-xl
    */
    def textXl: BreakPoint = this
    /**
    * class: text-2xl
    */
    def text2xl: BreakPoint = this
    /**
    * class: text-3xl
    */
    def text3xl: BreakPoint = this
}