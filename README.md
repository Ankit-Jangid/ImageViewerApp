# Image Viewer App

An Android application that allows users to browse, favorite, and download images from the Unsplash API.
The app is built using Jetpack Compose for UI, MVVM architecture, and dependency injection using Hilt.

## Features
- **Home Screen**: Displays a grid of images fetched from the Unsplash API.
- **Detail Screen**: Diplay the detailed image and shows info about photographer and has options to mark as favorite and download locally.
- **Favorites Screen**: Displays images that the user has marked as favorite.

 

## Permissions

- **Storage Permission**: For downloading images to the user's device (requires runtime permission on Android versions before Q).

## API Key
 - **API Key**: This app requires API key from Unsplash, for the time being that is added inside project.
 But has rate limitations so in case if it does not work, the Key can replaced at AppConstants file and it will 
 work as expected.
 https://unsplash.com/documentation



```xml
Permissions required:
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
## Demo Video


<video src="https://github.com/Ankit-Jangid/ImageViewerApp/blob/test-branch/image_Viewer_app_demo.mp4" controls width="100%" />
