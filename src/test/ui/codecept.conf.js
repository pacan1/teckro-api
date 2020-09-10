const { setHeadlessWhen } = require('@codeceptjs/configure');

// turn on headless mode when running with HEADLESS=true environment variable
// export HEADLESS=true && npx codeceptjs run
setHeadlessWhen(process.env.HEADLESS);

exports.config = {
  tests: 'bookin_test/*_test.js',
  output: './output',
  multiple: {
    multibrowser: {
      browsers: ["firefox", "chrome", "webkit"]
    }
  },
  helpers: {
    Playwright: {
      url: 'http://localhost:4200/',
      show: true,
      // browser: 'firefox'
    }
  },
  include: {
    I: './steps_file.js'
  },
  bootstrap: null,
  mocha: {},
  name: 'ui',
  plugins: {
    retryFailedStep: {
      enabled: true
    },
    screenshotOnFail: {
      enabled: true
    },
    rerun: {
      minSuccess: 1,
      maxReruns: 2
    },
    allure: {
      outputDir: './output',
      enableScreenshotDiffPlugin: true
    }
  }
}