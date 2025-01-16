import { Command } from "commander"
import { Option } from "./types.js"

export const generate = new Command()
    .name("generate")
    .description("generate your scalalumo.scala")
    .option(
        "-o, --output, <output>",
        "the output path. defaults to the current directory",
        "./scalawind.scala"
    )
    .option(
        "-p, --package-name <packageName>",
        "the package name. defaults to scalalumo",
        "scalalumo",
    )
    .action(async (opts: Option) => {
        console.log(opts)
    })