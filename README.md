[ ![Download](https://api.bintray.com/packages/arasthel/maven/spannedgridlayoutmanager/images/download.svg) ](https://bintray.com/arasthel/maven/spannedgridlayoutmanager/_latestVersion) [![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# SpannedGridLayoutManager

**SpannedGridLayouManager** is a layout manager that will resize and reorder views based on a provided `SpanSize`.

**IMPORTANT**: as the goal of this LayoutManager is to fill all gaps if possible, views may be placed in an order which may not be related to their positions. That is, if `View #9` can fill a gap before `View #8`, it will be placed in an previous position, ignoring the normal ordering.

![video](art/spannedgridlayout.gif)

## Usage

Gradle dependency:

```groovy
dependencies {
	compile 'com.arasthel:spannedgridlayoutmanager:1.0.2'
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
recyclerview.layoutManager = spannedGridLayoutManager
```

**Java** Example:

```java
SpannedGridLayoutManager spannedGridLayoutManager = new SpannedGridLayoutManager(
			SpannedGridLayoutManager.Orientation.VERTICAL, 4);
recyclerview.setLayoutManager(spannedGridLayoutManager);
```


To set the `SpanSize` for each view, you would do it on the `RecyclerView.Adapter`. I would do it in `onBindViewHolder` method, since you have access to `position` and can use several sizes depending on the position or your model's data.

**Kotlin:**

```kotlin
override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val width = 1
    val height = 2

    holder.itemView.layoutParams = RecyclerView.LayoutParams(width, height)
}
```
**Java:**

```java
@Override
public void onBindViewHolder(ViewHolder holder, int position) {
    int width = 1;
    int height = 2;

    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(width, height));
}
```

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