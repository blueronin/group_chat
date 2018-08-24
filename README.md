# Trial project: Group chat


Requirements:
* The user should be able to login (could be just picking a nickname or full signup process).
* 2 people should not have the same nickname.
* It should have one or more chat rooms with the whole message history.
* There should be an input for the user to send messages.
* Messages in the chat must show the sender info.
* The chat should have some form of bot/integration with youtube or giphy:
  * Giphy:
    * serach gif and show it.
  * Youtube
    * that search on youtube(using api) with format:
    * /youtube song_name/artist
    * must place a youtube embed player with first result
 
Tech that must be used, front end:
* JS - Backbone or React (redux its a bonus)
* HTML/CSS

It should have tests. Just a few are enought but tests... 

Backend:
* Whatever you like (we use python a lot but not required).

Platform:
* Run on heroku free plan, or whereever you like but the site should be reachable from anywhere.

# To create your entry

* Fork this repository
* Create a Github pull request with the implementation and URL to checkit



SUBMISSION NOTES

Here is my group chat application

Users can create an account with their email and set a username
Users can create chats and specify members to add via their username
  Only members of a chat can access chat and send messages in it
  User can add any member in the database to the chat (not just their contacts)
  Chats can have 1 or more members (single or group chat supported)
Users can add contacts via their username
Chat message activity includes space for user to input message and a send button
The star button is for youtube search, unfortunately this feature was not completed, so if you input a query and press the star button you will get a message saying the feature is unavailable
Users can access their profile from the main activity via the hamburger menu

!!BUG!!
There is a major bug in this app that causes it to crash upon REGISTERING a new user. The new user IS created and registered successfully. If you open the app again you will be logged in as that new user and the app will work fine. I located the line and error description but have not been able to fix it, in Android Studio's debug mode there is no error which made it difficult to find the true cause. (The error happens while loading the user's existing chats)
If you log out and log in as that new user it will also work fine.
