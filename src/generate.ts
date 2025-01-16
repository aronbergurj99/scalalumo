import fs from 'fs'
import path from 'path'

import css, { Rule, Comment, AtRule } from "css";
import Handlebars from 'handlebars';

import styles from './styles.js';

type RuleProperty = Rule | Comment | AtRule;

const MODIFIERS = ['xxl', 'xl', 'lg', 'md', 'sm']

const METHOD_NAME_PATTERN = /^[a-zA-Z_][a-zA-Z0-9_]*$/;

function readTemplate(filename: String) {
  return fs.readFileSync(path.join(import.meta.dirname, `./templates/${filename}.hbs`), "utf-8")
}

Handlebars.registerPartial({
  slumoMacro: Handlebars.compile(readTemplate("slumoMacro")),
  lumoUtil: Handlebars.compile(readTemplate("lumoUtil"))
})

const template = Handlebars.compile(readTemplate("scalalumo"))

const parsedCss = css.parse(styles);


function hasRules(obj: any): obj is { rules: any } {
  return "rules" in obj;
}

function flattenRules(rules: RuleProperty[]): Rule[] {
  return rules
    .filter((rule) => rule.type !== "comment")
    .flatMap((rule) => {
      if (hasRules(rule)) {
        return flattenRules(rule.rules);
      }
      return [rule];
    }) as Rule[];
}


export function toCamelCase(methodName: string) {
  // if method name is in shape w_1/2
  const percentRegex = /[a-z]_([0-9]+\/[0-9]+)/g
  const dotRegex = /[a-z]_([0-9]+\.[0-9]+)/g
  if (percentRegex.test(methodName) || dotRegex.test(methodName)) {
      return methodName
  }
  let hasLeadingUnderscore = methodName.startsWith('_')
  // if method name is leading with underscore, preserve it and transform the rest
  if (hasLeadingUnderscore) {
      methodName = methodName.slice(1)
  }

  methodName = methodName.replace(/_([a-z])/g, (_, letter) => {
      return letter.toUpperCase()
  })

  methodName = methodName.replace(/_([0-9])/g, "$1")

  if (hasLeadingUnderscore) {
      methodName = "_" + methodName
  }

  return methodName
}

function isValidMethodName(name: string) {
  return METHOD_NAME_PATTERN.test(name);
}

const lumoClassToScalaMethod = (s: String, camelCase = false) => {
  let methodName = s.replace(/-/g, '_').replace(/^\@/, '$').replace(/%/, '')

  if(camelCase) {
    methodName = toCamelCase(methodName)
  }

  if(isValidMethodName(methodName)) {
    return methodName
  }

  return "\`"+ methodName + "\`"
};

function rulesToQualifiedSelectors(rules: Rule[]): string[] {
  // Remove unwanted characters and fix bug in vaadin
  return rules.map(rule => rule.selectors![0].replace(/\\(\d+|.)/g, (_, group) => {
    if (Number.isInteger(Number(group))) {
      return String.fromCharCode(parseInt(group, 16));
    }

    if (group == "i") {
      // Bug in vaadin
      return ":i";
    }

    return group;
  })).filter(value => {
    // There are still some unwanted selectors that need to be filtererd out.

    // Remove selectors that are multiple selectors
    if (value.split(/\s+/).length > 1) {
      return false
    }

    // Remove selectors that don't start with .
    return value[0] === "."
  })
}

function withBreakpoints(selectors: string[]): string[] {
  const results = new Set<string>()

  selectors.forEach(selector => {
    const split = selector.split(":")
    if (split.length > 1) {
      results.add(lumoClassToScalaMethod(split[1], true))
    }
  })

  return Array.from(results)
}

function lumoUtility(selectors: string[]): string[] {
  const results: string[] = []

  selectors.forEach(selector => {
    if (!selector.includes(":")) {
      let withoutDot = selector.substring(1)
      if (withoutDot[0] === "-") {
        withoutDot = "neg" + withoutDot
      }

      results.push(lumoClassToScalaMethod(withoutDot, true))
    }
  })

  return results
}


export function generateContent(options: Object): string {
  const flattenedRules = flattenRules(parsedCss.stylesheet?.rules || [])
  const qualifiedSelectors = rulesToQualifiedSelectors(flattenedRules)


  return template({
    modifiers: MODIFIERS,
    standards: lumoUtility(qualifiedSelectors),
    withBreakpoints: withBreakpoints(qualifiedSelectors)
  })
}


export function writeToDisk(path: string, content: string) {
  fs.writeFileSync(path, content, 'utf8');
}

export default function generate(options: Object): void {
  writeToDisk("./scalalumo.scala", generateContent({}))
}
