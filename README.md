# WordScramble

An Android word-scramble game written in Java. Players rearrange a randomly selected word, submit up to three guesses, and can reveal the answer or start a new round. A successful guess triggers a dictionary lookup.

**Status:** earlier learning project, preserved as a source-code example. It has not been validated against current external API availability or modern Android toolchains.

## Features

- Random-word retrieval with Retrofit callbacks.
- Letter shuffling and case-insensitive answer checks.
- Three-attempt limit, answer reveal, and round reset.
- Dictionary definitions after a correct answer.
- Loading states and network-error messages.

## Stack

Java · Android Views/XML · Retrofit · Gson · AndroidX · Gradle

The committed configuration targets Android API 34, supports API 24 and newer, and uses Android Gradle Plugin 8.6.0 with a Gradle 8.7 wrapper. Use JDK 17 for that toolchain ([Android compatibility requirements](https://developer.android.com/build/releases/agp-8-6-0-release-notes)).

## Open the project

1. Clone this repository and open its root in Android Studio.
2. Install Android SDK platform 34 and select JDK 17 for Gradle.
3. Sync the Gradle project.
4. Run the `app` module on an emulator or Android device with internet access.

## Source map

| File | Purpose |
| --- | --- |
| `app/src/main/java/com/example/wordscramblegame/MainActivity.java` | Round state, guesses, word shuffling, UI updates, and network callbacks. |
| `app/src/main/java/com/example/wordscramblegame/ApiClient.java` | Retrofit clients for random-word and dictionary services. |
| `WordApi.java` / `DictionaryApi.java` | External API interfaces. |
| `DictionaryResponse.java` | Dictionary response model. |
| `app/src/main/res/layout/activity_main.xml` | Game screen. |

## Limitations and next improvements

- **External services:** words and definitions require the random-word and dictionary services to be reachable. There is no offline fallback.
- **Shuffle edge case:** the recursive shuffle retries when the result equals the original; words whose letters cannot form a different arrangement need a bounded fallback.
- **Tests:** the committed tests are Android template tests, not coverage of the round logic.
- **Images:** the active game uses bundled images. Pixabay-related classes exist, but the activity does not currently use them to fetch images.

## Attribution and reuse

External APIs, dependencies, the Gradle wrapper, and bundled artwork have their own terms. The provenance of the bundled artwork has not been documented, so this repository should not be treated as permission to redistribute those images.
