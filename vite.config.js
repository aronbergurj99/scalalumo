import { resolve } from 'path'
import { defineConfig } from 'vite'
import { viteStaticCopy } from 'vite-plugin-static-copy'


export default defineConfig({
  plugins: [viteStaticCopy({
    targets: [
      {
        src: "src/templates/**/*.hbs",
        dest: "templates"
      }
    ]
  })],
  assetsInclude: ["**/*.hbs"],
  build: {
    ssr: true,
    lib: {
      entry: resolve(__dirname, 'src/index.ts'),
      name: 'MyLib',
      fileName: 'scalalumo',
    },
    rollupOptions: {
      external: [
        "fs",
        "css"
      ]
    }
  },
})