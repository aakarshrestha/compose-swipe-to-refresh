# ComposeCollapsingToolbar

Release version [![](https://jitpack.io/v/aakarshrestha/ComposeCollapsingToolbar.svg)](https://jitpack.io/#aakarshrestha/ComposeCollapsingToolbar)

This is a simple library designed in Jetpack Compose UI where app bar will collaps when a list is scrolled.

# Download
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add it in your app build.gradle:
```
dependencies {
    implementation "com.github.aakarshrestha:ComposeCollapsingToolbar:version"
}
```

# Usage

```
@Composable
    fun App() {
        CollapsableToolbar(
            contentToAddInTopAppBar = {
                Text(text = "Title", modifier = Modifier.padding(start = 16.dp))
            },
            body = {
                LazyColumn {
                    items(25) { item ->
                        Text(text = "Item $item", modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth())
                    }
                }
            },
            contentToAddInBottomAppBar = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize().align(alignment = Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Inbox", modifier = Modifier.fillMaxHeight().padding(16.dp), textAlign = TextAlign.Center)
                        Text(text = "Outbox", modifier = Modifier.fillMaxHeight().padding(16.dp), textAlign = TextAlign.Center)
                        Text(text = "Settings", modifier = Modifier.fillMaxHeight().padding(16.dp), textAlign = TextAlign.Center)
                    }
                }
            }
        )
    }
```

CollapsableToolbar method has the following arguments:
```
isBottomAppBarVisible: Boolean? = true
```
_isBottomAppBarVisible_, by default is set to true and will be visible. To hide the BottomAppbar, you can set the value to false.

```
contentToAddInTopAppBar: @Composable () -> Unit
```
_contentToAddInTopAppBar_ is a composable function where another composable function can be passed. For example, if you want to add a title to the top toolbar, you can pass it in contentToAddInTopAppBar.

```
body: @Composable () -> Unit
```
_body_ is where you can pass another composable function. For example, you can pass a list in it.

```
contentToAddInBottomAppBar: @Composable () -> Unit = {}
```
_contentToAddInBottomAppBar_ is another composable function. Adding any composable function in contentToAddInBottomAppBar will be added in the BottomAppBar.
