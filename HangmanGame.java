/*  Written By:  Shaun Russell
	 Date:29/03/2024
	 Desc:Hangman class
	 File:
*/
///////////////////////////////////////////////////
///*****YOU MUST COMMENT THIS CODE*****////////////
////////////YOU CANNOT CHANGE THIS CODE////////////
///////////////////////////////////////////////////
/*
   uml diagram
   ~~~~~~~~~~~
   string secretword
   Char dashes
   int lives
   ~~~~~~~~~~~~~~~~
   construcor
    HangmanGame(string wordIn)
   ~~~~~~~~~~~~~~~~~
   methods
   guessletter(char letterIn) void
   gameOver() Boolean
   showSecretWord() String
   showDashes() String
   fillDshes() Void
   getLives() Integer
   setLives(int LivesIn) void
   ~~~~~~~~~~~~~~~~~#
*/
public class HangmanGame
{
   //variables
   private String secretWord;//the random secret word
   private char[] dashes; //no.of dashes appear
   private int lives; //no. of lives
	
	//constructor of the game
   public HangmanGame(String wordIn)
   {
      secretWord = wordIn;//input secret word from the word generator
      dashes = new char[secretWord.length()];//have correct no. of dahes for word
      fillDashes();
      lives = 8;  //default
   }
   
   //guesses the letter from inputed charactar
   public void guessLetter(char letterIn)
   {
      boolean found = false;
      for(int i = 0; i<secretWord.length(); i++)//loops through the secret word
      {
         if(letterIn == secretWord.charAt(i)){
            dashes[i] = letterIn;//if letter entered is correct the charactar will be inputed
            found = true;
         }	
      }
      if(!found)
         lives--;//if isnt found a life is lost
   
   }
	

   public boolean gameOver()
   {
      if(secretWord.equalsIgnoreCase(showDashes()) || lives == 0)//if the word is correct or no remaining lives the game is ended
         return true;
      else 
         return false;
   		
   	//return secretWord.equalsIgnoreCase(showDashes()) || lives == 0;
   }
	
	
   public String showSecretWord()
   {
      return secretWord;//displays the secret word of that game
   }

   //displays the number of dashes of the word
   public String showDashes()
   {
      String s ="";
      for(int i = 0; i< dashes.length; i++)
         s +=dashes[i];
   	
      return s;
   }
	
	//fills in the correct amount to be displayed to the user
   public void fillDashes()
   {
      for(int i = 0; i< dashes.length; i++)//the length of the word is displayed by '-' in a for loop
         dashes[i]='-';		
   }
	

   public int getLives()
   {
      return lives;//retrieves number of lives
   }
	
	
   public void setLives(int livesIn)
   {
      lives = livesIn;//sets the number of lives for each game mode
   }
}