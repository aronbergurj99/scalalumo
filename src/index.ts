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

function cleanSelectors(rules: Rule[]): string[] {
  const cleanedSelectors: string[] = []
  rules.forEach(rule => {
    let fixed = rule.selectors![0].replace(/\\(\d+|.)/g, (_, group) => {
      if (Number.isInteger(Number(group))) {
        return String.fromCharCode(parseInt(group, 16));
      }
  
      if (group == "i") {
        // Bug in vaadin
        return ":i";
      }
  
      return group;
    });



    if (fixed.split(/[\s:]+/).length == 1) {
      if (fixed[0] === ".") {
        fixed = fixed.substring(1) // remove .
  
        if (fixed[0] === '-') {
          fixed = "neg" + fixed
        }
  
        fixed = fixed.replace(/-/g, "_")
        cleanedSelectors.push(fixed)
      }
    }
  })

  return cleanedSelectors
}

const cleanedSelectors = cleanSelectors(flattenedRules)

const modifiers = ['2xl', 'xl', 'lg', 'md', 'sm']

function readTemplate(filename: String) {
  return fs.readFileSync(path.join(import.meta.dirname, `./templates/${filename}.hbs`), "utf-8")
}

Handlebars.registerPartial({
  slumoMacro: Handlebars.compile(readTemplate("slumoMacro")),
  lumoUtil: Handlebars.compile(readTemplate("lumoUtil"))
})

const compiledHtml = Handlebars.compile(readTemplate("scalalumo"))({
  modifiers,
  standards: cleanedSelectors,
});
console.log(compiledHtml)