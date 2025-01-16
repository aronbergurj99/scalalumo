import { Command } from "commander"
import { generate } from "./command.js"

process.on("SIGINT", () => process.exit(0))
process.on("SIGTERM", () => process.exit(0))

async function main() {
    const program = new Command()
        .name("scalalumo")
        .description("generate scalalumo code")

    program.addCommand(generate)
    program.parse()
}

main()