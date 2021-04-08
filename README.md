Release version [![](https://jitpack.io/v/aakarshrestha/ComposeCollapsingToolbar.svg)](https://jitpack.io/#aakarshrestha/ComposeCollapsingToolbar)

Compose swipe to refresh is a small library that implements pull to refresh action - Jetpack compose.

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
    implementation "com.github.aakarshrestha:compose-swipe-to-refresh:version"
}
```

# SwipeToRefresh

**Swipe to refresh demo**

![swipeToRefresh](https://user-images.githubusercontent.com/15058925/114078473-fb9f0e00-9876-11eb-9e19-b51ddf2d86c3.gif)

# Usage

SwipeToRefresh method.
```
@Composable
fun SwipeToRefresh(
    isRefreshing: Boolean,
    progressBarColor: Color? = null,
    pullToRefreshTextColor: Color? = null,
    refreshSectionBackgroundColor: Color? = null,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
)

```
SwipeToRefresh method takes the following params:
1. isRefreshing - to indicate whether or not refreshing is in progress
2. progressBarColor - is optional but can be used to provide custom color
3. pullToRefreshTextColor - is optional but can be used to provide custom color
4. refreshSectionBackgroundColor - is optional but can be used to provide custom color
5. onRefresh - is a callback method where, e.g: you can make a network call and once the call is done, update isRefreshing to false
6. content - is a composable function that take another composable function. You can add top app bar and others. 


Implementation:
Check out the sample app to see how it works.
```
val isRefreshing = rememberSaveable { mutableStateOf(false) } //required

SwipeToRefresh(
	isRefreshing = isRefreshing.value,
	onRefresh = {
	    isRefreshing.value = true

	    //make a fake network call and when done, update the isRefreshing to false
	    fakeNetworkCall {
		isRefreshing.value = false //required
	    }
	},
	refreshSectionBackgroundColor = MaterialTheme.colors.primary,

	content = {
		// add your content here, e.g: top app bar and else
		// checkout the sample project
	}

)
```

# Coming soon
- **pagerSnapHelper** for Jetpack compose

# License

```
Copyright 2021 Aakar Shrestha

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
