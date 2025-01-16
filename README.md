# Scalalimo
Zero-Runtime Typesafe Vaadin Lumo Utility classes in Scala inspired by [scalawind](https://github.com/nguyenyou/scalawind)

## How to use

Install the CLI, using any node package manager that you prefer:

```bash
$ npm install scalalumo --save-dev
```

Then, add the `postinstall` script to your `package.json` to make sure the code will automatically run after install:

```json
"scripts": {
  "postinstall": "scalalumo generate",
}
```

After generating, you will have a `scalalumo.scala` file at the root of your project. You can move it to your preferred location and rename the package however you like.

Then, `import scalalumo.*` and you're ready to go.

```scala
import scalalumo.*

div(
  cls := lumo.bgPrimary.mM.shadowM.flex.flexRow.md(lumobp.flexCol.text2xl).css,
  ...
)

// ↓ ↓ ↓ ↓ ↓ ↓

<div class="bg-primary m-m shadow-m flex flex-row md:flex-col md:text-2xl">
  ...
</div>
```

That's it.

## Customize Generated Code

The Scalalumo CLI supports `-o` to specify the output path and `-p` to specify the generated package name. For example:

```bash
$ scalalumo generate -o ./src/main/scala/myapp/scalalumo.scala -p scalalumo
```
