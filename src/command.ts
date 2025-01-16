import { Command } from "commander"
import { Options } from "./types.js"

import handler from "./generate.js"

export const generate = new Command()
    .name("generate")
    .description("generate your scalalumo.scala")
    .option(
        "-o, --output, <output>",
        "the output path. defaults to the current directory",
        "./scalalumo.scala"
    )
    .option(
        "-p, --package-name <packageName>",
        "the package name. defaults to scalalumo",
        "scalalumo",
    )
    .action(async (opts: Options) => {
        handler(opts)
    })