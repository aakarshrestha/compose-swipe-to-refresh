# ComposeCollapsingToolbar

Release version [![](https://jitpack.io/v/aakarshrestha/ComposeCollapsingToolbar.svg)](https://jitpack.io/#aakarshrestha/ComposeCollapsingToolbar)

This is a simple library designed in Jetpack Compose where app bar will collaps when a list is scrolled.

![collapsingToolbar](https://user-images.githubusercontent.com/15058925/113520336-55929180-9560-11eb-9af1-ab8e0efad93b.gif)

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

```
@Composable
fun CollapsableToolbar(
    isBottomAppBarVisible: Boolean? = true,
    contentToAddInTopAppBar: @Composable () -> Unit,
    body: @Composable () -> Unit,
    contentToAddInBottomAppBar: @Composable () -> Unit = {}
)
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

# License

```

Permission is hereby granted, free of charge, 
to any person obtaining a copy of this software and associated 
documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to permit persons
to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included 
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS 
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN 
AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH 
THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

```
