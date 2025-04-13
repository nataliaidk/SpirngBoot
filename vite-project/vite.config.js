import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
})

// export default {
//   server: {
//     proxy: {
//       '/authors': 'http://localhost:8080'
//     }
//   }
// }
