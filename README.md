[ ![Download](https://api.bintray.com/packages/arasthel/maven/spannedgridlayoutmanager/images/download.svg) ](https://bintray.com/arasthel/maven/spannedgridlayoutmanager/_latestVersion) [![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# SpannedGridLayoutManager

**SpannedGridLayouManager** is a layout manager that will resize and reorder views based on a provided `SpanSize`.

**IMPORTANT**: as the goal of this LayoutManager is to fill all gaps if possible, views may be placed in an order which may not be related to their positions. That is, if `View #9` can fill a gap before `View #8`, it will be placed in an previous position, ignoring the normal ordering.

![video](art/spannedgridlayout.gif)

## Usage

Gradle dependency:

```groovy
dependencies {
	implementation 'com.arasthel:spannedgridlayoutmanager:3.0.2'
}
```

When you create a new `SpannedGridLayoutManager` you must provide:

* An `Orientation`: `Orientation.VERTICAL` or `Orientation.HORIZONTAL`.
* The **span count** to divide the layout.

**Kotlin** Example:

```kotlin
val spannedGridLayoutManager = SpannedGridLayoutManager(
                orientation = SpannedGridLayoutManager.Orientation.VERTICAL, 
                spans = 4)
```

**Java** Example:

```java
SpannedGridLayoutManager spannedGridLayoutManager = new SpannedGridLayoutManager(
			SpannedGridLayoutManager.Orientation.VERTICAL, 4);
```


To set the `SpanSize` for each view, you need to use `SpannedGridLayoutManager.SpanSizeLookup`. This class has a lambda that receives an adapter position and must return an `SpanSize`:

**Kotlin:**

```kotlin
spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookUp { position -> 
    SpanSize(2, 2)
}
recyclerview.layoutManager = spannedGridLayoutManager
```

**Java:**

```java
// If your project has Java 8 support for lambdas
spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup({ position ->
    new SpanSize(2, 2);
}));

// If your project uses Java 6 or 7. Yup, it's ugly as hell
spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>(){
  @Override public SpanSize invoke(Integer position) {
    return new SpanSize(2, 2);
  }
}));

recyclerview.setLayoutManager(spannedGridLayoutManager);
```

If the logic needed to calculate this values is very complex, they can be cached by setting `spanSizeLookup.usesCache = true` or `spanSizeLookup.setUsesCache(true)`. If you need to invalidate them, just call `spanSizeLookup.invalidateCache()`. 
 
### About restoring scroll position:

As this *LayoutManager* may change the order in screen of its items, it has some issues with restoring scroll position when the sizes of the items change drastically. To work around this, restoring the scroll position when recreating this *LayoutManager* is disabled by default.

If you are fairly sure that the position of the items won't change much, you can enable it again using:

```kotlin
spannedLayoutManager.itemOrderIsStable = true
```

## Migrating from 1.X to 2.X

Due to critical layout issues, the API for using SpanSizes had to change. The only changes you should have to do in your code are:

```kotlin
    val width = 1
    val height = 2

    // OLD
    // holder.itemView.layoutParams = RecyclerView.LayoutParams(width, height)

    // NEW
    holder.itemView.layoutParams = SpanLayoutParams(SpanSize(width, height))
```

Just use the new `SpanLayoutParams` instead of generic `RecyclerView.LayoutParams`.

## Migrating from 2.X to 3.X

Sadly, due to more limitations of the initial design and bugs, most of the LayoutManager's layouting and recycling process had to be re-written. This depended on some breaking API changes since otherwise there would have been lots of unnecessary recycling of views and performance loss when the spans are calculated.

Because of this, while you would set the *SpanSizes* using `SpanLayoutParams`, **these are no longer needed**. You can safely delete those from your adapter.

Instead of that, you must use the `SpannedGridLayoutManager.SpanSizeLookup` like you would do with a `GridLayoutManager`. You can find more info on the [Usage section](#usage). 

## Animations

To have animations as shown in the sample, you must:

* Use `setHasStableIds(true)` in your adapter.
* Override `getItemId` method to return a stable id - that is, it won't change for the same position.
* Use `adapter.notifyDatasetChanged()` to trigger the layout process.

## Implementation details

This library uses `Rects` to find the empty gaps and choose where a view will be placed. The underlying algorithm is explained in [this paper](free_space_algorithm.pdf).

* Initially, there will be **1** free-space **Rect** `(0, 0, spanCount, Int.MAX_VALUE)` for `Orientation.VERTICAL` or `(0, 0, Int.MAX_VALUE, spanCount)` for `Orientation.HORIZONTAL`. 
* When a view must added, it will search for the free rects that are **adjacent** to the view's `Rect` or that intersects it.
* It will iterate over these rects looking for those that are adjacent to the view **and don't contain it**, which will be stored. If a rect doesn't match this criteria, it will be **removed** from the list of free rects and divided in **4 possible sub-rects** - left, top, right, bottom - like this:

![Sub rects](art/sub_rects.png)

* For each of that subrects, if none of the previously stored **adjacent** free rects and none of the rects in the list of free rects intersects with it, it will be added to this list as a valid free rect.

You can see this code in `SpannedGridLayoutManager`'s `subtract(Rect)` method.

In version 3.0 however, to ensure no layout issues happened, the whole rects are pre-calculated and cached on every relayout of the whole LayoutManager. These are some elapsed time results that I got from testing:

```
Android emulator: 
    - 500 items: 10ms
    - 1000 items: 18ms
    - 10000 items: 35ms
    - 50000 items: 128ms
    
OnePlus 3T:
    - 500 items: 12ms
    - 1000 items: 20ms
    - 10000 items: 135ms
    - 50000 items: 530ms
    
OnePlus 6T:
    - 500 items: 5ms
    - 1000 items: 10ms
    - 10000 items: 55ms
    - 50000 items: 200ms
``` 

They aren't that bad, but they're also not great for huge amounts of items and could cause performance issues if you have infinite scrolling. I will try to improve this and calculate rects by batches, although this might not give exact results in the placement of the views.

## License - MIT

```
MIT License

Copyright © 2017 Jorge Martín Espinosa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
