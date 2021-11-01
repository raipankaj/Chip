# Chip
[![](https://jitpack.io/v/raipankaj/Chip.svg)](https://jitpack.io/#raipankaj/Chip)

Add chips to your apps built with Jetpack Compose!

To get started with Chip just add the maven url and the Chip dependency

<b>build.gradle (Project level)</b>
```groovy
allprojects {
    repositories {
    ...
    //Add this url
    maven { url 'https://jitpack.io' }
    }
}
```
If you are using Android Studio Arctic Fox and above where you don't have allProjects in build.gradle then add following maven url in <b>settings.gradle</b> like below
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //Add this url
        maven { url 'https://jitpack.io' }
        jcenter() // Warning: this repository is going to shut down soon
    }
}
```

Once you have added the maven url now add the Chip dependency in the <b>build.gradle (module level)</b>
```groovy
implementation 'com.github.raipankaj:Chip:1.0.0'
```

Congratulations, you have successfully added the dependency. 
Now to get started with Chip add the following code snippet
```kotlin
val chipAssistItem = listOf(
                        ChipItem("Account", ChipType.Assist(Icons.Default.AccountBox)),
                        ChipItem("Search", ChipType.Assist(Icons.Default.Search)),
                        ChipItem("Filter", ChipType.Assist(Icons.Default.List))
                    )

Chip(labels = chipAssistItem) { selectedChip ->
    //Your logic based on selected chip
}
```
<br>
Chip composable provide an ability to change the background color of chip, default text color and text color when chip is selected.
Here are all the parameters accepted by Chip composable.

```kotlin
fun Chip(labels: List<ChipItem>,
         defaultTextColor: Color = Color.Black,
         selectedTextColor: Color = Color.White,
         chipColor: Color = Color.Blue.copy(0.5f),
         chipOnClick: (String) -> Unit) 

```
<br>
The various types of Chips that are currently supported are
<br>
1. Assist<br>
2. Filter<br>
3. Suggestion<br>
<br>

[![Demo](https://github.com/raipankaj/Chip/blob/main/chip.gif)](https://youtu.be/d0_tH6FfWuo)

<br>
Note: If you like this library, then please hit the star button! :smiley:
