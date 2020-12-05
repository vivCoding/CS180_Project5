# CS180 Project5
## Team 15-3

**Account Class:**
	* This class is called to hold information regarding any info in a user's account.
  	* Constructor
    	* The constructor sets parameters equal to private variables in the class and also initializes 3 Account arrayLists. Parameters include String username, String password,
		String email, String phoneNumber, String bio, and String interests. The arrayLists initialized are friends, friendRequests, and requestedFriends. 
  	* getUserName
   		* Gets the username.
	* getPassword
 		* Gets the password.
 	* setUserName
    	* Sets the username.
 	* setPassword
   		* Sets the password
   	* getEmail
   		* Gets the email.
   	* getPhoneNumber
		* Gets the phone number.
   	* getBio
		* Gets the bio.
  	* getInterests
		* Gets the interests.
   	* setEmail
		* Sets the email.
   	* setPhoneNumber
		* Sets the phone number.
  	* setBio
		* Sets the bio.
   	* setInterests
		* Sets the interests.
	* getFriends
		* Gets the friends and returns as an arrayList.
	* getFriendRequests
		* Gets the friend requests and returns as an arrayList.
	* getRequestedFriends
		* Gets the users that you have requested to friend and returns as an arrayList.
	* isFriendsWith
		* Checks whether one user is friends with another.
	* hasRequested
		* Checks whether another user has requested to be a friend and is in our friend requests arrayList.
	* sendFriendRequest
		* Sends friend request to another user and makes sure that you aren't currently already friends.
	* acceptDeclineFriendRequest
		* Accepts or denies friend requests, 1 is returned if successful and else, -1 is returned.
	* removeFriend
		* Removes friend from list and unfriends the person. 1 returned if successful and -1 else.
	* userInList
		* Helps find a certain user in a list. Index in the list is returned if the user exists and else, -1 is returned.
**Manager Class:**
	* This class acts as a database for storing all accounts and manages retrieval of of specific accounts. Serves as the backbone of the backend.
	* Constructor
		* 
