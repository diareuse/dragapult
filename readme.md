# Dragapult

[![Codacy grade](https://img.shields.io/codacy/grade/a9f217ff552d4cdfb690e78cb93c20e7)][code-quality]
![GitHub Release](https://img.shields.io/github/v/release/diareuse/dragapult)

![GitHub commit activity](https://img.shields.io/github/commit-activity/y/diareuse/dragapult)
![GitHub commits since latest release](https://img.shields.io/github/commits-since/diareuse/dragapult/latest)

Dragapult is a Gradle plugin that simplifies managing and generating string resources for your multi-platform projects
from a single source of truth.

## Gradle Plugin Usage <sub>for Android</sub>

### 1. Apply the plugin

Apply the Dragapult plugin to your project's `/build.gradle` file:

```groovy
plugins {
    id "io.github.diareuse.dragapult" version "x.y.z" apply false
}
```

Apply the Dragapult plugin to your app project's `/app/build.gradle` file:

```groovy
plugins {
    id "io.github.diareuse.dragapult" version "x.y.z"
}
```

---

### 2. Configure sources

Dragapult can fetch your localization files from both local and remote sources.

> ℹ️ Local configurations are useful primarily in cases where the file is committed to this repository.

```groovy
dragapult {
    // For local files 
    local("local-file") {
        file = project.file("input.json")
        inputFileType = "json"
    }
}
```

> ℹ️ Remote configurations can be fetched in an optimized manner from a remote server. This allows easily integrating
> changes without changing the repository, though it might produce irreproducible builds. Use with caution.

```groovy
dragapult {
    // For remote files
    remote("my-host-file") {
        inputFileType = "json"
        url "http://my.host/input.json"
        headers {
            header "X-Foo", "bar"
        }
        authorization {
            bearer "i-am-token"
        }
    }
}
```

---

### 3. Generate resources

Dragapult creates a corresponding Gradle task for each configured source. The task name is generated based on the source
name.

For example, for the `local-file` source, you can run the following command to generate the resources:

```bash
# generateDragapult<Variant><Configuration Name><Strings>
# ex. for Variant=Debug, Configuration Name=local-file
./gradlew generateDragapultDebugLocalFileStrings
```

---

## CLI usage

### 1. Check for usage

```bash
./dragapult -h
```

---

### 2. Consume current resources

> ℹ️ Consume resources already present in your project. Once consumed, edit the generated file and then use `generate`.
>
> ⚠️ Avoid editing generated files at all costs.

```bash
./dragapult consume -h
```

```bash
./dragapult consume\
  -i/--input-directory  res/values\
  -o/--output-file      translations.json\
  -t/--input-type       android\
  -r/--output-type      json
```

---

### 3. Generate string resources

> ℹ️ Generate produces predictable, up-to-spec files which can be readily compiled in your projects.
>
> ⚠️ Prefer not committing these files to source control.

```bash
./dragapult generate -h
```

```bash
./dragapult generate\
  -i/--input-file          translations.json\
  -o/--output-directory    res/values\
  -t/--output-type         android\
  -s/--input-type          json\
  -b/--allow-blank-values
```

---

## Extend this library

[Sources][dragapult-lib-sources]

```groovy
dependencies {
    implementation "io.github.diareuse:dragapult-lib:x.y.z"
}
```

---

[code-quality]: https://app.codacy.com/gh/diareuse/dragapult/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade

[code-coverage]: https://app.codacy.com/gh/diareuse/dragapult/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage

[dragapult-lib-sources]: dragapult-lib/src/main/java/app/dragapult