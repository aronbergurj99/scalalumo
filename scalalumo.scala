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

    def srOnly: LumoUtil = this
    def bgBase: LumoUtil = this
    def bgTransparent: LumoUtil = this
    def bgTint: LumoUtil = this
    def bgTint90: LumoUtil = this
    def bgTint80: LumoUtil = this
    def bgTint70: LumoUtil = this
    def bgTint60: LumoUtil = this
    def bgTint50: LumoUtil = this
    def bgTint40: LumoUtil = this
    def bgTint30: LumoUtil = this
    def bgTint20: LumoUtil = this
    def bgTint10: LumoUtil = this
    def bgTint5: LumoUtil = this
    def bgShade: LumoUtil = this
    def bgShade90: LumoUtil = this
    def bgShade80: LumoUtil = this
    def bgShade70: LumoUtil = this
    def bgShade60: LumoUtil = this
    def bgShade50: LumoUtil = this
    def bgShade40: LumoUtil = this
    def bgShade30: LumoUtil = this
    def bgShade20: LumoUtil = this
    def bgShade10: LumoUtil = this
    def bgShade5: LumoUtil = this
    def bgContrast: LumoUtil = this
    def bgContrast90: LumoUtil = this
    def bgContrast80: LumoUtil = this
    def bgContrast70: LumoUtil = this
    def bgContrast60: LumoUtil = this
    def bgContrast50: LumoUtil = this
    def bgContrast40: LumoUtil = this
    def bgContrast30: LumoUtil = this
    def bgContrast20: LumoUtil = this
    def bgContrast10: LumoUtil = this
    def bgContrast5: LumoUtil = this
    def bgPrimary: LumoUtil = this
    def bgPrimary50: LumoUtil = this
    def bgPrimary10: LumoUtil = this
    def bgError: LumoUtil = this
    def bgError50: LumoUtil = this
    def bgError10: LumoUtil = this
    def bgSuccess: LumoUtil = this
    def bgSuccess50: LumoUtil = this
    def bgSuccess10: LumoUtil = this
    def bgWarning: LumoUtil = this
    def bgWarning10: LumoUtil = this
    def border0: LumoUtil = this
    def borderDashed: LumoUtil = this
    def borderDotted: LumoUtil = this
    def border: LumoUtil = this
    def borderB: LumoUtil = this
    def borderE: LumoUtil = this
    def borderL: LumoUtil = this
    def borderR: LumoUtil = this
    def borderS: LumoUtil = this
    def borderT: LumoUtil = this
    def borderContrast: LumoUtil = this
    def borderContrast90: LumoUtil = this
    def borderContrast80: LumoUtil = this
    def borderContrast70: LumoUtil = this
    def borderContrast60: LumoUtil = this
    def borderContrast50: LumoUtil = this
    def borderContrast40: LumoUtil = this
    def borderContrast30: LumoUtil = this
    def borderContrast20: LumoUtil = this
    def borderContrast10: LumoUtil = this
    def borderContrast5: LumoUtil = this
    def borderPrimary: LumoUtil = this
    def borderPrimary50: LumoUtil = this
    def borderPrimary10: LumoUtil = this
    def borderError: LumoUtil = this
    def borderError50: LumoUtil = this
    def borderError10: LumoUtil = this
    def borderSuccess: LumoUtil = this
    def borderSuccess50: LumoUtil = this
    def borderSuccess10: LumoUtil = this
    def borderWarning: LumoUtil = this
    def borderWarningStrong: LumoUtil = this
    def borderWarning10: LumoUtil = this
    def roundedNone: LumoUtil = this
    def roundedS: LumoUtil = this
    def roundedM: LumoUtil = this
    def roundedL: LumoUtil = this
    def roundedFull: LumoUtil = this
    def backdropBlurNone: LumoUtil = this
    def backdropBlurSm: LumoUtil = this
    def backdropBlur: LumoUtil = this
    def backdropBlurMd: LumoUtil = this
    def backdropBlurLg: LumoUtil = this
    def backdropBlurXl: LumoUtil = this
    def backdropBlur2xl: LumoUtil = this
    def backdropBlur3xl: LumoUtil = this
    def contentCenter: LumoUtil = this
    def contentEnd: LumoUtil = this
    def contentStart: LumoUtil = this
    def contentAround: LumoUtil = this
    def contentBetween: LumoUtil = this
    def contentEvenly: LumoUtil = this
    def contentStretch: LumoUtil = this
    def itemsBaseline: LumoUtil = this
    def itemsCenter: LumoUtil = this
    def itemsEnd: LumoUtil = this
    def itemsStart: LumoUtil = this
    def itemsStretch: LumoUtil = this
    def selfAuto: LumoUtil = this
    def selfBaseline: LumoUtil = this
    def selfCenter: LumoUtil = this
    def selfEnd: LumoUtil = this
    def selfStart: LumoUtil = this
    def selfStretch: LumoUtil = this
    def flex1: LumoUtil = this
    def flexAuto: LumoUtil = this
    def flexNone: LumoUtil = this
    def flexCol: LumoUtil = this
    def flexColReverse: LumoUtil = this
    def flexRow: LumoUtil = this
    def flexRowReverse: LumoUtil = this
    def flexGrow: LumoUtil = this
    def flexGrow0: LumoUtil = this
    def flexShrink: LumoUtil = this
    def flexShrink0: LumoUtil = this
    def flexNowrap: LumoUtil = this
    def flexWrap: LumoUtil = this
    def flexWrapReverse: LumoUtil = this
    def gapXs: LumoUtil = this
    def gapS: LumoUtil = this
    def gapM: LumoUtil = this
    def gapL: LumoUtil = this
    def gapXl: LumoUtil = this
    def gapXXs: LumoUtil = this
    def gapXS: LumoUtil = this
    def gapXM: LumoUtil = this
    def gapXL: LumoUtil = this
    def gapXXl: LumoUtil = this
    def gapYXs: LumoUtil = this
    def gapYS: LumoUtil = this
    def gapYM: LumoUtil = this
    def gapYL: LumoUtil = this
    def gapYXl: LumoUtil = this
    def gridFlowCol: LumoUtil = this
    def gridFlowRow: LumoUtil = this
    def gridCols1: LumoUtil = this
    def gridCols2: LumoUtil = this
    def gridCols3: LumoUtil = this
    def gridCols4: LumoUtil = this
    def gridCols5: LumoUtil = this
    def gridCols6: LumoUtil = this
    def gridCols7: LumoUtil = this
    def gridCols8: LumoUtil = this
    def gridCols9: LumoUtil = this
    def gridCols10: LumoUtil = this
    def gridCols11: LumoUtil = this
    def gridCols12: LumoUtil = this
    def gridRows1: LumoUtil = this
    def gridRows2: LumoUtil = this
    def gridRows3: LumoUtil = this
    def gridRows4: LumoUtil = this
    def gridRows5: LumoUtil = this
    def gridRows6: LumoUtil = this
    def justifyCenter: LumoUtil = this
    def justifyEnd: LumoUtil = this
    def justifyStart: LumoUtil = this
    def justifyAround: LumoUtil = this
    def justifyBetween: LumoUtil = this
    def justifyEvenly: LumoUtil = this
    def colSpan1: LumoUtil = this
    def colSpan2: LumoUtil = this
    def colSpan3: LumoUtil = this
    def colSpan4: LumoUtil = this
    def colSpan5: LumoUtil = this
    def colSpan6: LumoUtil = this
    def colSpan7: LumoUtil = this
    def colSpan8: LumoUtil = this
    def colSpan9: LumoUtil = this
    def colSpan10: LumoUtil = this
    def colSpan11: LumoUtil = this
    def colSpan12: LumoUtil = this
    def colSpanFull: LumoUtil = this
    def rowSpan1: LumoUtil = this
    def rowSpan2: LumoUtil = this
    def rowSpan3: LumoUtil = this
    def rowSpan4: LumoUtil = this
    def rowSpan5: LumoUtil = this
    def rowSpan6: LumoUtil = this
    def rowSpanFull: LumoUtil = this
    def aspectSquare: LumoUtil = this
    def aspectVideo: LumoUtil = this
    def boxBorder: LumoUtil = this
    def boxContent: LumoUtil = this
    def block: LumoUtil = this
    def flex: LumoUtil = this
    def grid: LumoUtil = this
    def hidden: LumoUtil = this
    def inline: LumoUtil = this
    def inlineBlock: LumoUtil = this
    def inlineFlex: LumoUtil = this
    def inlineGrid: LumoUtil = this
    def overflowAuto: LumoUtil = this
    def overflowHidden: LumoUtil = this
    def overflowScroll: LumoUtil = this
    def absolute: LumoUtil = this
    def fixed: LumoUtil = this
    def static: LumoUtil = this
    def sticky: LumoUtil = this
    def relative: LumoUtil = this
    def negBottomXs: LumoUtil = this
    def negBottomS: LumoUtil = this
    def negBottomM: LumoUtil = this
    def negBottomL: LumoUtil = this
    def negBottomXl: LumoUtil = this
    def negBottomFull: LumoUtil = this
    def bottom0: LumoUtil = this
    def bottomXs: LumoUtil = this
    def bottomS: LumoUtil = this
    def bottomM: LumoUtil = this
    def bottomL: LumoUtil = this
    def bottomXl: LumoUtil = this
    def bottomAuto: LumoUtil = this
    def bottomFull: LumoUtil = this
    def negEndXs: LumoUtil = this
    def negEndS: LumoUtil = this
    def negEndM: LumoUtil = this
    def negEndL: LumoUtil = this
    def negEndXl: LumoUtil = this
    def negEndFull: LumoUtil = this
    def end0: LumoUtil = this
    def endXs: LumoUtil = this
    def endS: LumoUtil = this
    def endM: LumoUtil = this
    def endL: LumoUtil = this
    def endXl: LumoUtil = this
    def endAuto: LumoUtil = this
    def endFull: LumoUtil = this
    def negStartXs: LumoUtil = this
    def negStartS: LumoUtil = this
    def negStartM: LumoUtil = this
    def negStartL: LumoUtil = this
    def negStartXl: LumoUtil = this
    def negStartFull: LumoUtil = this
    def start0: LumoUtil = this
    def startXs: LumoUtil = this
    def startS: LumoUtil = this
    def startM: LumoUtil = this
    def startL: LumoUtil = this
    def startXl: LumoUtil = this
    def startAuto: LumoUtil = this
    def startFull: LumoUtil = this
    def negTopXs: LumoUtil = this
    def negTopS: LumoUtil = this
    def negTopM: LumoUtil = this
    def negTopL: LumoUtil = this
    def negTopXl: LumoUtil = this
    def negTopFull: LumoUtil = this
    def top0: LumoUtil = this
    def topXs: LumoUtil = this
    def topS: LumoUtil = this
    def topM: LumoUtil = this
    def topL: LumoUtil = this
    def topXl: LumoUtil = this
    def topAuto: LumoUtil = this
    def topFull: LumoUtil = this
    def invisible: LumoUtil = this
    def visible: LumoUtil = this
    def z0: LumoUtil = this
    def z10: LumoUtil = this
    def z20: LumoUtil = this
    def z30: LumoUtil = this
    def z40: LumoUtil = this
    def z50: LumoUtil = this
    def zAuto: LumoUtil = this
    def shadowNone: LumoUtil = this
    def shadowXs: LumoUtil = this
    def shadowS: LumoUtil = this
    def shadowM: LumoUtil = this
    def shadowL: LumoUtil = this
    def shadowXl: LumoUtil = this
    def h0: LumoUtil = this
    def hXs: LumoUtil = this
    def hS: LumoUtil = this
    def hM: LumoUtil = this
    def hL: LumoUtil = this
    def hXl: LumoUtil = this
    def hAuto: LumoUtil = this
    def hFull: LumoUtil = this
    def hScreen: LumoUtil = this
    def maxHFull: LumoUtil = this
    def maxHScreen: LumoUtil = this
    def minH0: LumoUtil = this
    def minHFull: LumoUtil = this
    def minHScreen: LumoUtil = this
    def iconS: LumoUtil = this
    def iconM: LumoUtil = this
    def iconL: LumoUtil = this
    def wXs: LumoUtil = this
    def wS: LumoUtil = this
    def wM: LumoUtil = this
    def wL: LumoUtil = this
    def wXl: LumoUtil = this
    def wAuto: LumoUtil = this
    def wFull: LumoUtil = this
    def maxWFull: LumoUtil = this
    def maxWScreenSm: LumoUtil = this
    def maxWScreenMd: LumoUtil = this
    def maxWScreenLg: LumoUtil = this
    def maxWScreenXl: LumoUtil = this
    def maxWScreen2xl: LumoUtil = this
    def minW0: LumoUtil = this
    def minWFull: LumoUtil = this
    def negMXs: LumoUtil = this
    def negMS: LumoUtil = this
    def negMM: LumoUtil = this
    def negML: LumoUtil = this
    def negMXl: LumoUtil = this
    def m0: LumoUtil = this
    def mXs: LumoUtil = this
    def mS: LumoUtil = this
    def mM: LumoUtil = this
    def mL: LumoUtil = this
    def mXl: LumoUtil = this
    def mAuto: LumoUtil = this
    def negMbXs: LumoUtil = this
    def negMbS: LumoUtil = this
    def negMbM: LumoUtil = this
    def negMbL: LumoUtil = this
    def negMbXl: LumoUtil = this
    def mb0: LumoUtil = this
    def mbXs: LumoUtil = this
    def mbS: LumoUtil = this
    def mbM: LumoUtil = this
    def mbL: LumoUtil = this
    def mbXl: LumoUtil = this
    def mbAuto: LumoUtil = this
    def negMeXs: LumoUtil = this
    def negMeS: LumoUtil = this
    def negMeM: LumoUtil = this
    def negMeL: LumoUtil = this
    def negMeXl: LumoUtil = this
    def me0: LumoUtil = this
    def meXs: LumoUtil = this
    def meS: LumoUtil = this
    def meM: LumoUtil = this
    def meL: LumoUtil = this
    def meXl: LumoUtil = this
    def meAuto: LumoUtil = this
    def negMxXs: LumoUtil = this
    def negMxS: LumoUtil = this
    def negMxM: LumoUtil = this
    def negMxL: LumoUtil = this
    def negMxXl: LumoUtil = this
    def mx0: LumoUtil = this
    def mxXs: LumoUtil = this
    def mxS: LumoUtil = this
    def mxM: LumoUtil = this
    def mxL: LumoUtil = this
    def mxXl: LumoUtil = this
    def mxAuto: LumoUtil = this
    def negMlXs: LumoUtil = this
    def negMlS: LumoUtil = this
    def negMlM: LumoUtil = this
    def negMlL: LumoUtil = this
    def negMlXl: LumoUtil = this
    def ml0: LumoUtil = this
    def mlXs: LumoUtil = this
    def mlS: LumoUtil = this
    def mlM: LumoUtil = this
    def mlL: LumoUtil = this
    def mlXl: LumoUtil = this
    def mlAuto: LumoUtil = this
    def negMrXs: LumoUtil = this
    def negMrS: LumoUtil = this
    def negMrM: LumoUtil = this
    def negMrL: LumoUtil = this
    def negMrXl: LumoUtil = this
    def mr0: LumoUtil = this
    def mrXs: LumoUtil = this
    def mrS: LumoUtil = this
    def mrM: LumoUtil = this
    def mrL: LumoUtil = this
    def mrXl: LumoUtil = this
    def mrAuto: LumoUtil = this
    def negMsXs: LumoUtil = this
    def negMsS: LumoUtil = this
    def negMsM: LumoUtil = this
    def negMsL: LumoUtil = this
    def negMsXl: LumoUtil = this
    def ms0: LumoUtil = this
    def msXs: LumoUtil = this
    def msS: LumoUtil = this
    def msM: LumoUtil = this
    def msL: LumoUtil = this
    def msXl: LumoUtil = this
    def msAuto: LumoUtil = this
    def negMtXs: LumoUtil = this
    def negMtS: LumoUtil = this
    def negMtM: LumoUtil = this
    def negMtL: LumoUtil = this
    def negMtXl: LumoUtil = this
    def mt0: LumoUtil = this
    def mtXs: LumoUtil = this
    def mtS: LumoUtil = this
    def mtM: LumoUtil = this
    def mtL: LumoUtil = this
    def mtXl: LumoUtil = this
    def mtAuto: LumoUtil = this
    def negMyXs: LumoUtil = this
    def negMyS: LumoUtil = this
    def negMyM: LumoUtil = this
    def negMyL: LumoUtil = this
    def negMyXl: LumoUtil = this
    def my0: LumoUtil = this
    def myXs: LumoUtil = this
    def myS: LumoUtil = this
    def myM: LumoUtil = this
    def myL: LumoUtil = this
    def myXl: LumoUtil = this
    def myAuto: LumoUtil = this
    def p0: LumoUtil = this
    def pXs: LumoUtil = this
    def pS: LumoUtil = this
    def pM: LumoUtil = this
    def pL: LumoUtil = this
    def pXl: LumoUtil = this
    def pb0: LumoUtil = this
    def pbXs: LumoUtil = this
    def pbS: LumoUtil = this
    def pbM: LumoUtil = this
    def pbL: LumoUtil = this
    def pbXl: LumoUtil = this
    def pe0: LumoUtil = this
    def peXs: LumoUtil = this
    def peS: LumoUtil = this
    def peM: LumoUtil = this
    def peL: LumoUtil = this
    def peXl: LumoUtil = this
    def px0: LumoUtil = this
    def pxXs: LumoUtil = this
    def pxS: LumoUtil = this
    def pxM: LumoUtil = this
    def pxL: LumoUtil = this
    def pxXl: LumoUtil = this
    def pl0: LumoUtil = this
    def plXs: LumoUtil = this
    def plS: LumoUtil = this
    def plM: LumoUtil = this
    def plL: LumoUtil = this
    def plXl: LumoUtil = this
    def pr0: LumoUtil = this
    def prXs: LumoUtil = this
    def prS: LumoUtil = this
    def prM: LumoUtil = this
    def prL: LumoUtil = this
    def prXl: LumoUtil = this
    def ps0: LumoUtil = this
    def psXs: LumoUtil = this
    def psS: LumoUtil = this
    def psM: LumoUtil = this
    def psL: LumoUtil = this
    def psXl: LumoUtil = this
    def pt0: LumoUtil = this
    def ptXs: LumoUtil = this
    def ptS: LumoUtil = this
    def ptM: LumoUtil = this
    def ptL: LumoUtil = this
    def ptXl: LumoUtil = this
    def py0: LumoUtil = this
    def pyXs: LumoUtil = this
    def pyS: LumoUtil = this
    def pyM: LumoUtil = this
    def pyL: LumoUtil = this
    def pyXl: LumoUtil = this
    def transitionNone: LumoUtil = this
    def transitionAll: LumoUtil = this
    def transition: LumoUtil = this
    def transitionColors: LumoUtil = this
    def transitionOpacity: LumoUtil = this
    def transitionShadow: LumoUtil = this
    def transitionTransform: LumoUtil = this
    def text2xs: LumoUtil = this
    def textXs: LumoUtil = this
    def textS: LumoUtil = this
    def textM: LumoUtil = this
    def textL: LumoUtil = this
    def textXl: LumoUtil = this
    def text2xl: LumoUtil = this
    def text3xl: LumoUtil = this
    def fontThin: LumoUtil = this
    def fontExtralight: LumoUtil = this
    def fontLight: LumoUtil = this
    def fontNormal: LumoUtil = this
    def fontMedium: LumoUtil = this
    def fontSemibold: LumoUtil = this
    def fontBold: LumoUtil = this
    def fontExtrabold: LumoUtil = this
    def fontBlack: LumoUtil = this
    def lineClamp1: LumoUtil = this
    def lineClamp2: LumoUtil = this
    def lineClamp3: LumoUtil = this
    def lineClamp4: LumoUtil = this
    def lineClamp5: LumoUtil = this
    def lineClamp6: LumoUtil = this
    def leadingNone: LumoUtil = this
    def leadingXs: LumoUtil = this
    def leadingS: LumoUtil = this
    def leadingM: LumoUtil = this
    def listNone: LumoUtil = this
    def textLeft: LumoUtil = this
    def textCenter: LumoUtil = this
    def textRight: LumoUtil = this
    def textJustify: LumoUtil = this
    def textHeader: LumoUtil = this
    def textBody: LumoUtil = this
    def textSecondary: LumoUtil = this
    def textTertiary: LumoUtil = this
    def textDisabled: LumoUtil = this
    def textPrimary: LumoUtil = this
    def textPrimaryContrast: LumoUtil = this
    def textError: LumoUtil = this
    def textErrorContrast: LumoUtil = this
    def textSuccess: LumoUtil = this
    def textSuccessContrast: LumoUtil = this
    def textWarning: LumoUtil = this
    def textWarningContrast: LumoUtil = this
    def lineThrough: LumoUtil = this
    def noUnderline: LumoUtil = this
    def underline: LumoUtil = this
    def overflowClip: LumoUtil = this
    def overflowEllipsis: LumoUtil = this
    def capitalize: LumoUtil = this
    def lowercase: LumoUtil = this
    def uppercase: LumoUtil = this
    def whitespaceNormal: LumoUtil = this
    def whitespaceBreakSpaces: LumoUtil = this
    def whitespaceNowrap: LumoUtil = this
    def whitespacePre: LumoUtil = this
    def whitespacePreLine: LumoUtil = this
    def whitespacePreWrap: LumoUtil = this
}

case class BreakPoint() {
    def itemsBaseline: BreakPoint = this
    def itemsCenter: BreakPoint = this
    def itemsEnd: BreakPoint = this
    def itemsStart: BreakPoint = this
    def itemsStretch: BreakPoint = this
    def flexCol: BreakPoint = this
    def flexRow: BreakPoint = this
    def gridCols1: BreakPoint = this
    def gridCols2: BreakPoint = this
    def gridCols3: BreakPoint = this
    def gridCols4: BreakPoint = this
    def gridCols5: BreakPoint = this
    def gridCols6: BreakPoint = this
    def gridCols7: BreakPoint = this
    def gridCols8: BreakPoint = this
    def gridCols9: BreakPoint = this
    def gridCols10: BreakPoint = this
    def gridCols11: BreakPoint = this
    def gridCols12: BreakPoint = this
    def block: BreakPoint = this
    def flex: BreakPoint = this
    def grid: BreakPoint = this
    def hidden: BreakPoint = this
    def inline: BreakPoint = this
    def inlineBlock: BreakPoint = this
    def inlineFlex: BreakPoint = this
    def inlineGrid: BreakPoint = this
    def absolute: BreakPoint = this
    def fixed: BreakPoint = this
    def relative: BreakPoint = this
    def static: BreakPoint = this
    def sticky: BreakPoint = this
    def text2xs: BreakPoint = this
    def textXs: BreakPoint = this
    def textS: BreakPoint = this
    def textM: BreakPoint = this
    def textL: BreakPoint = this
    def textXl: BreakPoint = this
    def text2xl: BreakPoint = this
    def text3xl: BreakPoint = this
}