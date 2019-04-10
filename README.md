# List News MVP

This is an Android app that shows a list of news from an online source.

App features are similar to [List News MVVM](https://github.com/densiology/News-List-MVVM) which I also made. It only differs in code (how it was made).

## App Features
![sc_List News 2](https://user-images.githubusercontent.com/12168036/55845349-0fbeb080-5b74-11e9-9925-a313941f87fa.jpg)![sc_List News 3](https://user-images.githubusercontent.com/12168036/55845354-164d2800-5b74-11e9-9152-2255888d825e.jpg)
* Lazy loading list of news (new set is loaded as user scrolls down)
* Read the whole news in a webview (click an item to open)
* Save news to Favorites for offline viewing (long click an item for option to save)

## Code Features
* Model-View-Presenter (MVP) architecture
* Volley for networking tasks
* SQLite for storage purposes
