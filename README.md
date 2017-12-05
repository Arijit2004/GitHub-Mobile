The Android application is using a mixed architecture not exactly MVP (not MVC at all)

The libraries used
 1. Glide
 2. Retrofit
 3. OkHTTP
 4. Butterknive
 5. Cardview-v7  

 - Profile data is cached
 - Repo details are not cached and retrieved on clcik of repo selection

 On logout, access token and auth code along with profile data is being removed.
 
 Flow :
 Initially auth code is retrieved from Git and then API request is made to get teh access code. 
 Once Access code is available, user private repo and and user details are retrieved and displayed in card view in RecyclerView 
 Also search option is added to search repo as per star and display in recycler view. 

 Improvement :
 Repo details also need to be cached but then we need to create a push mechanism in server (onChange of any repo details) to send a pust to app so that app can fetch the latest repo details. (schedule api request is not a good option) 

 For any query, send a mail to : arijitroy2004@gmail.com
 

