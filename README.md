Release version [![](https://jitpack.io/v/aakarshrestha/compose-swipe-to-refresh.svg)](https://jitpack.io/#aakarshrestha/compose-swipe-to-refresh)

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
    implementation "com.github.aakarshrestha:compose-swipe-to-refresh:[version]"
}
```

# 01. ComposePullToRefresh
ComposePullToRefresh library is inspired from google swipe to refresh in jetpack compose.

**Pull to refresh demo**

![pull-to-refresh](https://user-images.githubusercontent.com/15058925/115610246-96a3d900-a2b6-11eb-8de9-55ff3737209a.gif)

# Usage
```
@Composable
fun ComposePullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    indicatorTopPadding: Dp = 60.dp,
    indicatorColor: Color = MaterialTheme.colors.primary,
    content: @Composable () -> Unit
)
```

ComposePullToRefresh implements pull to refresh action with an indicator that gets adjusted when dragged and let go.
* @param isRefreshing initial value for pull to refresh
* @param onRefresh callback method which get triggered when pull to refresh indicator is pull down to a given position
* @param indicatorTopPadding padding for the pull to refresh indicator
* @param indicatorColor color to display in the progress bar
* @param content scrollable composable to be placed here

Implementation:
Check out the sample app to see how it works.

```
val refreshing = rememberSaveable { mutableStateOf(false) } **required**

ComposePullToRefresh(
	isRefreshing = refreshing.value,
	onRefresh = {

	    refreshing.value = true **required - need to set it to true when onRefresh callback method is called.**

	    fakeNetworkCall {
		refreshing.value = false **required - once the network call is done, need to set refreshing value to false**
	    }
	},
	indicatorColor = MaterialTheme.colors.onPrimary,
	content = {
	    Box(modifier = Modifier
		.fillMaxSize()
		.background(color = MaterialTheme.colors.primary)
	    ) {
		Column(
		    modifier = Modifier
			.fillMaxSize()
		) {
		    TopAppBarSection()
		    BodySection(
			musicList = musicList,
			heading = heading,
			title = title,
			subtitle = subtitle
		    )
		}
	    }
	}
)
```


#
#
#

# 02. SwipeToRefresh

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

# Check out Compose pager snap library - Jetpack compose android
- [ComposePagerSnapHelper https://github.com/aakarshrestha/compose-pager-snap-helper]

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
