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
].reduce((acc, current) => acc +current.cssText, "")

export default styles