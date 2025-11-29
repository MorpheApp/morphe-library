# 📚 Morphe Library

Library containing common utilities for Morphe.

## ❓ About

Morphe Library powers projects such as [Morphe Manager](https://github.com/MorpheApp/morphe-manager),
[Morphe CLI](https://github.com/MorpheApp/morphe-cli) with common utilities and functionalities
by providing shared code.

## 💪 Features

Some of the features the Morphe Library provides are:

- 📝 **Signing APKs**: Read and write keystores, and sign APK files
- 🧩 **Common utility functions**: Various APIs for Morphe patches such as JSON serialization,
  reading and setting patch options, calculating the most common compatible version for a set of patches and more
- 💾 **Install and uninstall APKs**: Install and uninstall APK files via ADB or locally,
  the Android package manager, or by mounting using root permissions
- 📦 **Repackage patched files to an APK**: Apply patched files from
  [Morphe Patcher](https://github.com/morphe/morphe-patcher) to an APK file, and align & sign the APK file automatically

## 🚀 How to get started

To use Morphe Library in your project, follow these steps:

1. [Add the repository](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package)
   to your project
2. Add the dependency to your project:

   ```kt
    dependencies {
        implementation("app.morphe:morphe-library:{$version}")
    }
   ```

## 📚 Everything else

### 📙 Contributing

Thank you for considering contributing to Morphe Library.
You can find the contribution guidelines [here](CONTRIBUTING.md).

### 🛠️ Building

To build Morphe Library,
you can follow the [Morphe documentation](https://github.com/MorpheApp/morphe-documentation).

Morphe Patches are licensed under the [GNU GPL v3.0](https://www.gnu.org/licenses/gpl-3.0.html), with additional conditions under Section 7:

- **Name Restriction (7c):** The name **"Morphe"** may not be used for derivative works.  
  Derivatives must adopt a distinct identity unrelated to "Morphe."

See the [LICENSE](./LICENSE) file for full terms.
