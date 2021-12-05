Original App Design Project - README Template
===

# Game Hub

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This app allows for video game users to view the all their video games. They will be able to find a description and reviews of any video game on the market. This allows the user to make a better selection before making a purchase. The user can also like their favorite video game through the app. This will allow them to view all liked video games after signing in on a different device. This means they'd be able to show friends and followers their favorite video games.

### App Evaluation
- **Category:** Entertainment
- **Story:** We want a platform where users can feel at home and come together over things that they are passionate about. Not only will they be able to use the platform to expand their knowledge and interests, but being able to collectively "like" games and potentially find new sources of content for those games is really what it's about.
- **Market:** Users who are interested in videogames, enthusiasts and hobbyists.
- **Habit:** The intention for this app will be to create a place that the user can go to find new games and create their own collection of liked games. It's intentionally designed to be something that can be picked up and put down at any time, without being too addictive for any unecessary reason.
- **Scope:** Users will be able to create an account, log in, search a videogame database based on hot topics such as "Top rated", "New releases" and "Trending" or search by genre. Once games are populated on the screen, the user will be able to click on any game of their choosing to bring up a more detailed page that will host a game image, video (if applicable), description, and any other integrations available for that game (Youtube/Twitch/Store links if applicable).

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [X]  Account creation/login
- [X]  Timeline (games)
- [X]  Details page for each game
    - [X] Display game image and title
    - [X] Show game description
    - [X] Allow the user to like a game
- [X] User profile page
    - [X] Can view who is signed in as well as displays temporary profile picture
    - [X] User can sign out
- [X] Search for games
- [X] User can find games by genre, top tending, new releases, etc.
- [X] Display a list of games with game title, image, and short description of game

**Optional Nice-to-have Stories**

- [X] Display ratings/reviews for games on the details page (In form of pie chart)
- [X] Allow user to view all liked games
- [ ] Send user to twitch to watch the game being played (twitch integration)
- [ ] Youtube trailer on game details page
- [X] Upload picture to a profile

### 2. Screen Archetypes

* Login Screen
   * Login
* Registration Screen
   * Account creation
* Homepage
   * Timeline (games)
   * Search for games
   * User can select games by genre, top tending, new releases, etc.
* Game List Page
   * Display a list of games with game title, image, and short description of game
* Details Page
   * Display game image and title
   * Ability to like the game 
   * Show game description
   * Send user to buy page if they choose to buy
* Profile Page
   * Can view who is signed in as well as displays temporary profile picture
   * User can sign out

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Feed
* Search Games
* User Profile

**Flow Navigation** (Screen to Screen)

* **Login**
   * Login -> Homepage
   * Login -> Account Creation
* **Account Creation**
   * Account Creation -> Login
* **Home Feed**
    * Home Feed -> Game List Page
* **Game List Page**
    * Game List Page -> Game Details
    * Game List Page -> Home Feed
* **Game Details**
    * Game Details -> Game List Page
* **User Profile**
    * User Profile -> Login

## Wireframes
<img src="Wireframe.PNG" width=800>

<img src="DetailedWireframe.png" width=800>

### Interactive Prototype
<img src="Detailed.gif" width=350>

## Schema 
[This section will be completed in Unit 9]
### Models

#### User

| Property | Type | Description |
|--- | --- | --- |
|objectId | String | Unique id for the user (default field) |
|name | String | Full name of the user |
|username | String | Username of the user (default field) |
|password | String | Password of the user (hidden field) |
|email | String | Email of the user |
|profilePic | File | Profile pic of the user |
|createdAt | DateTime | Date and time user was created (default field) |

#### Game

| Property | Type | Description |
|--- | --- | --- |
|objectId | String | Unique id for the game (default field) |
|name | String | Name of the game |
|publisher | String | Name of the publisher |
|description | String | Description of the game |
|imagePath | String | Path to the cover image of the game |
|createdAt | DateTime | Date and time user was created (default field) |

#### LikedGames

| Property | Type | Description |
|--- | --- | --- |
|objectId | String | Unique id for the game (default field) |
|name | String | Name of the liked game |
|likes | Number | Number of likes |
|dislikes | Number | Number of dislikes |
|createdAt | DateTime | Date and time user was created (default field) |

### Networking
#### List of network requests by screen
 * Registering Screen 
    * (Create/POST) create a new user 
      ```java
      User user = new User();
      user.setName(name);
      user.setUserName(username);
      user.setPassword(password);
      user.setProfilePic(profilePicFile);
      user.setLikedGames(likedGames);
      user.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if (e != null){
                  Log.e(TAG, "Error saving user in backend", e);
                  return;
              }
          //TODO: Login user and redirect to the user profile screen
          }
      });
      ```
 * Stream Screen 
    * (Read/GET) Query all user liked games
    ```java
     ParseQuery<game> query = ParseQuery.getQuery(Games.class);
     query.include(Games.KEY_USER);
     query.setLimit(20);
     query.whereEqualTo(Games.KEY_USER, ParseUser.getCurrentUser());
     query.addDescendingOrder(Games.KEY_CREATED_KEY);
     query.findInBackground(new FindCallback<Games>() {
         @Override
         public void done(List<Games> games, ParseException e) {
             if (e!= null){
                 Log.e(TAG,"Couldn't find content",e);
                 return;
             }
             
             //TODO: Do something with Games
     });
    ```
     * (Create/POST) Create a new user liked game 
     ```java
      LikedGame game = new LikedGame();
      game.setName(name)
      game.setLikes(0)
      game.setDislikes(0)
      game.setUser(user)
      game.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if (e != null){
                  Log.e(TAG, "Error saving game in backend", e);
                  return;
              }
          //TODO: Show heart next to game
          }
      });
     ```
 * Settings Screen 
    * (Update/PUT) Update game like count
    * (Update/PUT) Update game dislike count 
    ```java
      ParseQuery<ParseObject> query = ParseQuery.getQuery("LikedGames");

      // Retrieve the object by id
      query.getInBackground("<game.getId()>", (object, e) -> {
        if (e == null) {
          //TODO: Update likes and dislikes
      });
    ```
- [OPTIONAL: List endpoints if using existing API such as Yelp]

## Sprint 1
<img src="Sprint1.gif" width=350>

## Sprint 2
<img src="Sprint2.gif" width=350>
