/*
   name;Shaun Russell
   Date;14/03/2024
   desc;hangman GUI
*/

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.event.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.RadioButton;
import javafx.scene.input.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.control.*;

/*
   PSEAUDOCDE
   Assign variables,buttons,constructors,buttons and lines
   create the main stage with panes and adding all panes and boxes together to create the main layout of the game
   create the hangman figure using lines and circle in a regular pane
   create keyboard using a for loop that runs through the alphabet in one VBox but each line of keyboard inside a HBox assigning each charactar to a button
   that will subsquently fill dashes to create a word.
   create hint menu in a menu
   create game menu in a menu that sets its own gamemode by number of lives and a diferenet word
   close the game in a private void, have inside the exit menu
   create the hangman game in a private void,have inside the button handler that gains a body part after each question is wrong a set text will appear if user has either lost or won that game
   initilize the hangman in a private void,have inside each menu game handler
   fetch the secret word hint for each gamemode in a private void, have inside each menu game handler

   
   


*/

public class HangmanGUI extends Application {
     
   
   //letters for keyboard
   private String letter;
    
   //menu
   private MenuItem menuItemEasyGame;
   private MenuItem menuItemPlayGame; 
   private MenuItem menuItemDifGame;


   //buttons
   private Button btn;
   private Button button1;
   private Button button2;
   //Keyboard buttons
   private Button[] btnArray;
   int btnNo;  
   //labels
   private Label lbl; 
   private Label hint;
   
   //the Hangman
   private Line gallows;
   private Line rope;
   private Line beam;
   private Line top;
   private Line body;
   private Line leftArm;
   private Line rightArm;
   private Line leftLeg;
   private Line rightLeg;
   
   //circle for the head
   private Circle head;

   
   // boxes and panes
   private VBox box;
   private BorderPane pane;
   
   //handlers and constructors from other files
   private HangmanGame game;
   private WordGenerator word;
   
   //life integers for the gamemodes
   private int live=0;
   private int normal=8;
   private int easy=10;
   private int diff=5;
   //set the stage to be able to exit the game
   Stage stage;
   //String to use for hint
   private String secretword;
   private String secr;   

   @Override
   // Override the start method in the Application class
   public void start(Stage primaryStage) {
   
      //set the stage
     stage = primaryStage;
       
     //create word constructor into the game
     word= new WordGenerator();
     game= new HangmanGame(word.getWord());//game
         
     //create pane
     pane=new BorderPane();
     pane.setPadding(new Insets(20));
     
     //create game and hint label
      lbl = new Label();
      hint= new Label();
      
     //creating and displaying a title
      Label titleLabel = new Label("        Hangman Game");
      //customising labels
      titleLabel.setStyle("-fx-font-size: 27px; -fx-text-fill: #333;");
      hint.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
      lbl.setStyle("-fx-font-size: 20px;");



      //creates a menubar
      MenuBar menuBar = new MenuBar(creategamepane(),CreatehintMenu());
     //Assign methods to VBox and Hbox and set the layout
     box= new VBox(lbl,createrowButtons());
     VBox box2= new VBox(titleLabel,menuBar,hint);//creates the layout inside the VBox
     HBox box1=  new HBox(createMan());
     //set the layouts
     pane.setCenter(box1);
     pane.setBottom(box);
     pane.setTop(box2);
     
    
           
      // Create a scene and place it in the stage
      Scene scene = new Scene(pane);
      //add in the css
      scene.getStylesheets().add("simple.css");//implement css
      primaryStage.setTitle("Hangman"); // Set the stage title
      primaryStage.setScene(scene); // Place the scene in the stage
      primaryStage.show(); // Display the stage
      
   }
   //method to create the hangman figure
   private Pane createMan()
   {
      //create the pane and insets for the figure
      Pane pane=new Pane();
      pane.setPadding(new Insets(10));

      // Draw the base frame  gallows,beam,top,rope
        gallows = new Line(50, 300, 150, 300);
        beam = new Line(100, 300, 100, 50);
        rope = new Line(200,75,200,50);
        top = new Line(100, 50, 200, 50);
        
        //add the base frame onto the pane
        pane.getChildren().addAll(gallows,beam,rope,top);
        
        // Draw the head and set color and transparnecy
        head = new Circle(200, 100, 25);
        head.setFill(Color.TRANSPARENT);
        head.setStroke(Color.BLACK);
        //add to pane
        pane.getChildren().add(head);
        
        // Draw the body ,arms and legs
        body = new Line(200, 125, 200, 200);
        leftArm = new Line(200, 140, 175, 175);
        rightArm = new Line(200, 140, 225, 175);
        leftLeg = new Line(200, 200, 175, 250);
        rightLeg = new Line(200, 200, 225, 250);   
             
        //add all the body parts to the pane
        pane.getChildren().addAll(body,leftArm,rightArm,leftLeg,rightLeg);
                  
      //return the pane to complete the method    
      return pane;
         
   
   }
   //create the keybaord
   private VBox createrowButtons()
   {
         //created one main VBox and put 3 seperate HBox inside the Vbox to create the keyboard
        VBox main= new VBox();
        HBox pane1 = new HBox();
        HBox pane2 = new HBox();
        HBox pane3 = new HBox();
        
        //set insets and padding for each pane
        pane1.setPadding(new Insets(10));
        pane1.setAlignment(Pos.CENTER); 
        
        pane2.setPadding(new Insets(10));
        pane2.setAlignment(Pos.CENTER); 

        pane3.setPadding(new Insets(10));
        pane3.setAlignment(Pos.CENTER); 

        //create a button array to store the value of all buttons created alongside a button counter
        btnArray = new Button[26];
        btnNo=0;//button counter
        
        //create 3 for loops
        for(char i ='a'; i<= 'j';i++)
        {
         //add buttons to array,assign the char version of it  and add to pane
         Button btn = new Button(String.valueOf(i));
         btnArray[btnNo]= btn;
         btnNo++;
         pane1.getChildren().add(btn);
         
         //event handler
         btn.setOnAction(e -> {
         
            //from the for loop store the charactars from the button pressed and set in the lbl text
            game.guessLetter(((Button)e.getSource()).getText().charAt(0));
            lbl.setText("letters:  "+game.showDashes()+ "Lives:   " + game.getLives());
            //create the set game with lives
            live=game.getLives();
            create();
            
          
         
            btn.setDisable(true);//disable button once used
            
            
          } );
         }
         for(char i ='k'; i<= 's';i++)
        {
         //add buttons to array,assign the char of it  and add to pane
         Button btn = new Button(String.valueOf(i));
         btnArray[btnNo]= btn;
         btnNo++;
         pane2.getChildren().add(btn);
         
         //handler
         btn.setOnAction(e -> {
         
            //from the for loop store the charactars from the button pressed and set in the lbl text
            game.guessLetter(((Button)e.getSource()).getText().charAt(0));
           lbl.setText("letters:  "+game.showDashes()+ "  Lives:   " + game.getLives());//shows lives and dashes
           //create the set game
           live=game.getLives();
           create();
            
            btn.setDisable(true);//disable button once used
                        
          } );
        }
        for(char i ='t'; i<= 'z';i++)
        {
         //add buttons to array,assign the char of it  and add to pane
         Button btn = new Button(String.valueOf(i));
         btnArray[btnNo]= btn;
         btnNo++;
         pane3.getChildren().add(btn);
         
         btn.setOnAction(e -> {
         
            //from the for loop store the charactars from the button pressed and set in the lbl text
            game.guessLetter(((Button)e.getSource()).getText().charAt(0));
            lbl.setText("letters:   "+game.showDashes()+ "  Lives:  " + game.getLives());//shows lives and dashes
            //create the set game with lives
            live=game.getLives();
            create();
           
            //disable buttons
            btn.setDisable(true);//disable button once used
            

            
          } );

         }
         //add all hboxes to main vbox
         main.getChildren().addAll(pane1,pane2,pane3);
         
        return main;    //return the VBox    
        }
             
     

   
   public Menu CreatehintMenu()
   {
      //create menu and menu item
      Menu hints = new Menu("Hint");
      MenuItem press = new MenuItem("Press for hint");
      //handler when hint is pressed
      press.setOnAction(e -> secret());
      //add to menu
      hints.getItems().add(press);
      
      //return menu
      return hints;
   
   }
   //create the manu with all the buttons
    public Menu creategamepane()
   {
       // Create the Game menu
      Menu menuGame = new Menu("_Game");
      // Create the Options menu
         
    //create  menuItems for Game menu eg. game modes and exit
      menuItemPlayGame = new MenuItem("Play: 8 lives");
      menuItemEasyGame = new MenuItem("Easy mode:  10 lives");
      menuItemDifGame = new MenuItem("Dificult mode: 5 lives");
      MenuItem menuItemExit = new MenuItem("E_xit");
            
      
   
   //add eventHandlers to menu items
   //regular mode
     menuItemPlayGame.setOnAction(e -> {
          //create the sperate word for each game played
          game= new HangmanGame(word.getWord());
          //store and assign the secret word for the hint
          secret();
          //set the game to be ready with set lives dashes and correct parts of hangman visible and set the labels for the game
          initilize();
          game.setLives(normal);
          gallows.setVisible(true); 
          beam.setVisible(true);
          //disable all buttons upon new game
           for (Button btn : btnArray) {
                     btn.setDisable(false);
            }
          //set the label text for the game played
          live=game.getLives();
          game.fillDashes();
          lbl.setText(lbl.getText()+"Letters  "+game.showDashes()+ "Lives:  " + game.getLives());
          
        });
      //event handler for the easy gamemode
      
      menuItemEasyGame.setOnAction(e ->  {
         game= new HangmanGame(word.getWord());
         //store and assign the secret word for the hint
         secret();
         //set the game to be ready with set lives dashes and correct parts of hangman visible and set the labels for the game
         initilize();
         game.setLives(easy);
         
         for (Button btn : btnArray) {
                     btn.setDisable(false);
          }
         //fill in the nessacities of the game
         game.fillDashes();
         live=game.getLives();
         lbl.setText(lbl.getText()+"Letters:  "+game.showDashes()+ "    lives: "+game.getLives());
         
       });
       
      //event handler for the dificult game mode
      menuItemDifGame.setOnAction(e -> { 
         game= new HangmanGame(word.getWord());
         //store and assign the secret word for the hint
         secret();
         //initilize the game and all of its components to correct format
         initilize();
         game.setLives(diff);
         //set some parts visible for the harder game mode
         rope.setVisible(true);
         gallows.setVisible(true); 
         beam.setVisible(true);
         top.setVisible(true);
         head.setVisible(true);

         //reset buttons when game is created
         for (Button btn : btnArray) {
                btn.setDisable(false);
         }
         //fill in the nessacities of the game
         game.fillDashes();
         live=game.getLives();
         lbl.setText(lbl.getText()+"Letters:  "+game.showDashes()+ "     lives:  "+game.getLives());
         
       });
       //to allow the user to exit the game
       menuItemExit.setOnAction(e -> exit());
       

               
   // use  add  menuItems for Game menu
      menuGame.getItems().addAll(menuItemPlayGame,menuItemEasyGame, 
                  menuItemDifGame, new SeparatorMenuItem() ,   menuItemExit);
                  
       return menuGame;//returns menu
   
   }
   //get the secret word for a hint
   private void secret()
   {
       //get the word from the game
       secretword=game.showSecretWord();
       //retrieve the first two letters
       secr= secretword.substring(0,2);
       //set the label text
       hint.setText("Hint:  "+ secr);

   }
   //to exit the game
   private void exit()
   {
      //closes the stage
      stage.close();
   }
   
   //create the base of the game when an answer is incorrect a part of the man is drawn
   private void create()
   {
       //each life lost sets a new part of the hangman
       if(live==9)
       {
         gallows.setVisible(true);//use of set visible
       
       }
       if(live==8)
       {
        beam.setVisible(true);

       }
      if(live==7)
       {
           top.setVisible(true); 

       }
       if(live==6)
       {
          rope.setVisible(true); 

       }
       if(live==5)
       {
           head.setVisible(true); 

       }
       if(live==4)
       {
           body.setVisible(true); 

       }
       if(live==3)
       {
             leftArm.setVisible(true); 

       }
       if(live==2)
       {
             rightArm.setVisible(true); 

       }
       if(live==1)
       {
             leftLeg.setVisible(true); 

       }
       if(live==0)
       {
             rightLeg.setVisible(true); 
             
             //display secret word when lost and reset buttons
             if(live==0){
               lbl.setText("You lost\nSecret word is " +game.showSecretWord());	
               //disable all buttons when game is over
                for (Button btn : btnArray) {
                     btn.setDisable(false);
                  }
              }
       }
       //if user guesses the secret word
      if(secretword.equalsIgnoreCase(game.showDashes()))
       {
          //display when game is won with secret word
          lbl.setText("You win "+  game.showSecretWord());
          //enable all buttons
          for (Button btn : btnArray) {
                     btn.setDisable(false);
                  }

       }
    
           
       

   }
  

    //initilize the game upon starting
    public void initilize()
    {      

          //set both texts to null when starting new round start a new round
          lbl.setText("");
          hint.setText("");

          //set all parts of hangman to invisible
          top.setVisible(false);
          gallows.setVisible(false); 
          beam.setVisible(false);
          rope.setVisible(false);
          head.setVisible(false);
          body.setVisible(false);
          leftArm.setVisible(false);
          rightArm.setVisible(false);
          leftLeg.setVisible(false);
          rightLeg.setVisible(false);
          
      
     
    
    }
}