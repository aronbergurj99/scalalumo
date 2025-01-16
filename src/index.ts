import fs from 'fs'
import path from 'path'

import css, { Rule, Comment, AtRule } from "css";
import Handlebars from 'handlebars';

import { accessibility } from "@vaadin/vaadin-lumo-styles/utilities/accessibility.js";
import { background } from "@vaadin/vaadin-lumo-styles/utilities/background.js";
import { border } from "@vaadin/vaadin-lumo-styles/utilities/border.js";
import { filter } from "@vaadin/vaadin-lumo-styles/utilities/filter.js";
import { flexboxAndGrid } from "@vaadin/vaadin-lumo-styles/utilities/flexbox-grid.js";
import { layout } from "@vaadin/vaadin-lumo-styles/utilities/layout.js";
import { shadows } from "@vaadin/vaadin-lumo-styles/utilities/shadows.js";
import { sizing } from "@vaadin/vaadin-lumo-styles/utilities/sizing.js";
import { spacing } from "@vaadin/vaadin-lumo-styles/utilities/spacing.js";
import { transition } from "@vaadin/vaadin-lumo-styles/utilities/transition.js";
import { typography } from "@vaadin/vaadin-lumo-styles/utilities/typography.js";

const styles = [
  accessibility,
  background,
  border,
  filter,
  flexboxAndGrid,
  layout,
  shadows,
  sizing,
  spacing,
  transition,
  typography,
];

const parsedCss = css.parse(
  styles.reduce((acc, current) => acc + current.cssText, "")
);

type RuleProperty = Rule | Comment | AtRule;

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

const flattenedRules = flattenRules(parsedCss.stylesheet?.rules || []);

const methodNamePattern = /^[a-zA-Z_][a-zA-Z0-9_]*$/;

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
  return methodNamePattern.test(name);
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

function cleanSelectors(rules: Rule[]): string[] {
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

const cleanedSelectors = cleanSelectors(flattenedRules)

const modifiers = ['xxl', 'xl', 'lg', 'md', 'sm']

function readTemplate(filename: String) {
  return fs.readFileSync(path.join(import.meta.dirname, `./templates/${filename}.hbs`), "utf-8")
}

Handlebars.registerPartial({
  slumoMacro: Handlebars.compile(readTemplate("slumoMacro")),
  lumoUtil: Handlebars.compile(readTemplate("lumoUtil"))
})


const content = Handlebars.compile(readTemplate("scalalumo"))({
  modifiers,
  standards: lumoUtility(cleanedSelectors),
  withBreakpoints: withBreakpoints(cleanedSelectors),
});


export function writeToDisk(path: string, content: string) {
  fs.writeFileSync(path, content, 'utf8');
}

writeToDisk("./scalalumo.scala", content)