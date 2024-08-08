Udemefy App
=====================================
Udemyfy is an Android application that allows users to explore various courses, view detailed information about them, and save their favorite courses to a "Watch Later" list. The app is designed with a focus on user privacy and seamless user experience, featuring a robust authentication system with randomly generated avatars and an intuitive, interactive UI.
# preview
![Preview1](MergedImages(1).png)
![Preview2](MergedImages(2).png)
![Preview3](MergedImages(3).png)

# Features
- **Explore Courses**: Browse a variety of courses and view detailed information.
- **Watch Later List**: Save courses to a watch later list for easy access.
- **User Authentication**: Secure authentication using Firebase Authentication, including random profile picture generation.
- **Interactive UI**: Engaging user interface with smooth navigation and responsive design.

# Libraries and technologies used
### UI Libraries
- **Glide**: For efficient image loading and caching.
- **Circular Image**: To display images in a circular format.
- **View-Pager2**: For implementing swipeable views.
- **Circle Indicator**: For adding indicators to the View-Pager2.
- **View-Binding**: To simplify view binding and avoid findViewById calls.
- **Graph Navigation**: For seamless navigation between fragments.
### Dependency Injection
- **Koin** : The app uses Koin for dependency injection, providing a lightweight and easy-to-use solution for managing dependencies.
### Back-End Services
#### The app leverages Firebase and external APIs to manage data:
- Firebase:
    - Authentication: Provides secure user authentication. 
    - Realtime Database: Stores user data, including the watch later list.
- APIs:
    - Courses API: Fetches course data from an external source. The app currently uses the following API: [USDA Code JSON](https://www.usda.gov/sites/default/files/documents/code.json).

### Code Architecture
The Udemyfy app follows the MVVM (Model-View-ViewModel) architecture pattern, ensuring a clean separation of concerns and making the codebase more maintainable.
### Key Architectural Components:
- **MVVM**: Provides a structured way to manage UI-related data, separating the user interface logic from business logic.
- **State-Flow & Channels**: Utilized for reactive programming, handling state and data streams efficiently.
- **Kotlin-Coroutines**: Enables asynchronous programming, allowing for smoother and more responsive UI interactions.