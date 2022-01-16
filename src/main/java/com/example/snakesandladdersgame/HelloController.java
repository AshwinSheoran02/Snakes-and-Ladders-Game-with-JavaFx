package com.example.snakesandladdersgame;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import javafx.animation.TranslateTransition;
import javafx.stage.Popup;
import javafx.scene.Scene;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;

import java.io.File;

import javafx.scene.transform.Translate;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Random;

import static com.example.snakesandladdersgame.movement.playerxx;

public class HelloController {
    public ImageView redt;   //red token image
    public ImageView bluet;  //blue token image
    Random rand = new Random();
    dice die;
    token redtok;
    token bluetok;
    players playera;
    players playerb;
    snakes snake;
    ladders ladder;
    movement movement1;
    movement movement2;
    //snakemovement snakemovement1;
    //snakemovement snakemovement2;
    int dicecount=0;  //numbr of turns
    int player1flag=0;   //value is 1 if player is player 1
    int player2flag=0;
    public int player1x=0;   //position of plyer1
    public int player2x=0;
    int redflag =0;   //1 if red token



    public void initialize(){
        die = new dice();
        bluetok = new token();
        redtok = new token();
        playera = new players();
        playerb = new players();
        snake = new snakes();
        ladder = new ladders();
        //movement1 = new movement();
        //movement2 = new movement();

        File f11 = new File("Images/gamestart.wav");   //for making game start sound
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(f11);
            DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(sound);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public Label welcomeText;
    @FXML
    private Label player1;
    @FXML
    ImageView player1win;  //player1win pop up
    @FXML
    ImageView player2win;
    @FXML
    ImageView dierotate;
    @FXML
    public Button rollb;  //roll button
    @FXML
    ImageView fireworks;


    @FXML
    private Label player2;
    @FXML
    ImageView dieImage;
    @FXML
    ImageView rtoken0;  //red token
    @FXML
    ImageView btoken0;
    @FXML
    ImageView celebrate;
    @FXML
    ImageView arrowdown;  //arrow above dice , disappears when die is rotating

    @FXML
    protected void onHelloButtonClick() {
    // on clicking roll button

        int rand_int1 = rand.nextInt(6) + 1;
        die.setDieImage(rand_int1, dieImage, dierotate, arrowdown);  //set number on dice
        dicecount = dicecount + 1;
        if (dicecount % 2 != 0) {  //it was player 1 chance
            //Player 1 moves
            //player1.setText("Player-1");
            //player2.setText("Player-2's Chance");
            playera.playeraunlock(player1, player2, rtoken0, btoken0);
            File f1 = new File("Images/red3d" + ".png");   //red token image
            redt.setImage(new Image(f1.toURI().toString()));
            if (rand_int1 == 1 && player1flag == 0) { //for unlocking the red token
                player1flag = 1;
                //TranslateTransition tt = new TranslateTransition(Duration.millis(2000), redt);
                //tt.setToY(-36);
                //tt.setToX(-20);
                //tt.play();
                redt.setX(-20);
                redt.setY(-36);
                //player1x=1;
            } else if (player1flag == 1) {   //if token is unlocked
//                for (int i = 0; i < rand_int1; i++) {
//                    //For moving the token
//                }
                //For moving the token
                player1x = rand_int1 + player1x;   // updating the location
                System.out.println("RED- " + (player1x + 1));
                redflag = 1;
                /*
                player1x = redtok.movetokenh(rand_int1, redt, player1x, redflag, welcomeText, player1win, player2win, celebrate, rollb, arrowdown);
                player1x = snake.eat(redt, player1x, redflag);
                player1x = ladder.climb(redt, player1x, redflag);
                */
                movement1 = new movement( rand_int1,   redt ,    player1x, redflag,   player1win,  player2win, celebrate, rollb,  arrowdown, redtok,  snake,  ladder , 0  , fireworks);
                movement1.start();
                try {
                    movement1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //player1x = snake.getplayerxx();
                //player1x= ladder.getplayerxx();
                //player1x = temp;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {    // to sleep after movement
                    e.printStackTrace();
                }
                player1x=redtok.getplayerzz();   // to update player location
                player1x = snake.eat(redt, player1x, redflag);
                player1x = ladder.climb(redt, player1x, redflag);
                //System.out.println("player1x= "+((int)player1x+1));

            }

        }
        if (dicecount % 2 == 0) {
            //Player 2 moves
            //player2.setText("Player-2");
            //player1.setText("Player-1's Chance");
            playerb.playerbunlock(player1, player2, rtoken0, btoken0);
            File f2 = new File("Images/blue3d" + ".png");
            bluet.setImage(new Image(f2.toURI().toString()));
            if (rand_int1 == 1 && player2flag == 0) { //for unlocking the token
                player2flag = 1;
                TranslateTransition tt = new TranslateTransition(Duration.millis(2000), bluet);
                bluet.setY(-36);
                bluet.setX(-4);
                //tt.setFromX(0);
                //tt.setFromX(0);
                //tt.setToY(-36);
                //tt.setToX(-4);
                tt.play();

                //player2x=1;
            } else if (player2flag == 1) {
                player2x = player2x + rand_int1;
                System.out.println("BLUE- " + (player2x + 1));
                redflag = 0;
                /*
                player2x =bluetok.movetokenh(rand_int1, bluet , player2x, redflag , welcomeText , player1win , player2win, celebrate , rollb , arrowdown);
                player2x = snake.eat(bluet , player2x , redflag);
                player2x = ladder.climb(bluet , player2x , redflag);

                 */
                movement2 = new movement( rand_int1,   bluet ,    player2x, redflag,   player1win,  player2win, celebrate, rollb,  arrowdown, bluetok,  snake,  ladder, 0  , fireworks);
                movement2.start();
                try {
                    movement2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //player1x = snake.getplayerxx();
                //player1x= ladder.getplayerxx();
                //player2x = temp;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                player2x= bluetok.getplayerzz();
                player2x = snake.eat(bluet , player2x , redflag);
                player2x = ladder.climb(bluet , player2x , redflag);
                //System.out.println("player2x= "+((int)player2x+1));

                //snakemovement2 = new snakemovement( bluet ,  redflag ,  snake , player2x);


            }
        }
    }


    @FXML
    public void getcoordinate(MouseEvent event){
        welcomeText.setText("X:"+String.valueOf(event.getSceneX())+"Y:"+String.valueOf(event.getSceneY()));
    }
}
///////
class movement<T,U,V,W,X,Y,Z> extends Thread{
    //public Object movement;
    //T = int
    //U = IMAGEVIEW
    //W = BUTTON
    //X = TOKEN
    //Y = SNAKES
    //Z = LADDER
    T rand_int1;
    U colt;
    T temp;
    public static int playerxx;
    T redflag;
    U player1win ;
    U player2win;
    U celebrate ;
    W rollb ;
    U arrowdown;
    X coltok;
    Y snake;
    Z ladder;
    U fireworks;


    movement( T rand_int1 ,  U colt ,  int playerxx , T redflag   , U player1win  , U player2win , U celebrate  , W rollb  , U arrowdown ,  X coltok , Y snake , Z ladder , T temp , U fireworks ){
        //Initializer class
        this.rand_int1 = rand_int1;
        this.colt = colt;
        this.playerxx = playerxx;
        this.redflag= redflag;
        this.player1win = player1win ;
        this.player2win=player2win;
        this.celebrate= celebrate ;
        this.rollb=rollb ;
        this.arrowdown= arrowdown;
        this.coltok=coltok;
        this.snake=snake;
        this.ladder=ladder;
        this.temp = temp;
        this.fireworks = fireworks;
    }

    @Override
    public void run(){
        playerxx =((token)coltok).movetokenh((int)rand_int1, (ImageView)colt , playerxx,(int) redflag ,  (ImageView)player1win , (ImageView)player2win,(ImageView) celebrate ,(Button)rollb ,(ImageView) arrowdown ,(snakes) snake, (ladders)ladder ,(ImageView) fireworks);


    }


}


class dice{
    public void setDieImage(int top ,ImageView dieImage , ImageView dierotate , ImageView arrowdown) {
        //dieImage.setImage(new Image("pig/resources/Dice" + top + ".png"));
        //File f = new File("Images/drotate.gif");
        File f0 = new File("Images/dicerollsoundfinal.wav");
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(f0);
            DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(sound);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        FadeTransition ft1 = new FadeTransition(Duration.millis(1500), dierotate);  //transition of 3d die

        File f = new File("Images/Dice" + top + ".png");
        dieImage.setImage(new Image(f.toURI().toString()));
        ft1.setFromValue(1);
        ft1.setToValue(0);
        FadeTransition ft2 = new FadeTransition(Duration.millis(1200), dieImage);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        FadeTransition ft3 = new FadeTransition(Duration.millis(600), arrowdown);  // arrow above the die
        ft3.setFromValue(1);
        ft3.setToValue(0);
        FadeTransition ft4 = new FadeTransition(Duration.millis(600), arrowdown);
        ft4.setFromValue(0);
        ft4.setToValue(1);

        /*RotateTransition rt = new RotateTransition();
        rt.setByAngle(360);
        rt.setNode(dieImage);
        rt.setDuration(Duration.millis(200));
        rt.play();*/
        ft1.play();
        ft2.play();
        ft3.play();
        ft4.play();
    }
}

class token{
    //Token class

    int playerzz;


    public int movetokenh ( int rand_int, ImageView token ,int playerx, int flag, ImageView
            player1win, ImageView player2win, ImageView celebrate, Button rollb, ImageView arrowdown , snakes snake , ladders ladder, ImageView fireworks){
        //Translate translate = new Translate(35);

        TranslateTransition animate = new TranslateTransition(Duration.millis(400), token);
        if (playerx + 1 > 100) {
            playerx = playerx - rand_int;  //if token tries to go beyond 100
        }
        else {
            if (((playerx + 1) >= 1 && (playerx + 1) <= 10) || ((playerx + 1) >= 21 && (playerx + 1) <= 30) || ((playerx + 1) >= 41 && (playerx + 1) <= 50) || ((playerx + 1) >= 61 && (playerx + 1) <= 70) || ((playerx + 1) >= 81 && (playerx + 1) <= 90)) {
                if (flag == 0) {
                    token.setX(-4);  //sets to that 1st point of  odd row according to the colour (to)
                } else {
                    token.setX(-20);  // this prevents overlapping of tokens in a block
                }
                ///For odd rows
                animate.setToX((playerx % 10) * 49);   //moves just  left or right according to the number (by)
                animate.setToY((-1) * (playerx / 10) * 49);
                File f4 = new File("Images/normaltokensoundfinal.wav");
                try {
                    AudioInputStream sound = AudioSystem.getAudioInputStream(f4);
                    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(sound);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        /*for (int i = 0 ; i< rand_int ; i++)
        {
            //System.out.println((playerx+ rand_int));
            token.setTranslateX( (playerx)* 49);

        }*/
            if (((playerx + 1) >= 11 && (playerx + 1) <= 20) || ((playerx + 1) >= 31 && (playerx + 1) <= 40) || ((playerx + 1) >= 51 && (playerx + 1) <= 60) || ((playerx + 1) >= 71 && (playerx + 1) <= 80) || ((playerx + 1) >= 91 && (playerx + 1) <= 100)) {
                if (flag == 0) {
                    token.setX(404);   //sets to that 1st point of  odd row according to the colour (to)
                } else {
                    token.setX(453);
                }
                //For even rows
                animate.setToX((-1) * (playerx % 10) * 49);  //moves just  left or right according to the number (by)
                animate.setToY((-1) * (playerx / 10) * 49);
                File f4 = new File("Images/normaltokensoundfinal.wav");
                try {
                    AudioInputStream sound = AudioSystem.getAudioInputStream(f4);
                    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(sound);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }

        }

        if (playerx + 1 == 100) {
            //Player Wins
            if (flag == 0) {
                //welcomeText.setText("Player-2 Wins");
                player2win.setOpacity(1);
                celebrate.setOpacity(1);  //confetti
                fireworks.setOpacity(1);
                arrowdown.setOpacity(0);
                rollb.setDisable(true);   //die won't move after winning

                File f2 = new File("Images/celebrationsound.wav");
                try {
                    AudioInputStream sound = AudioSystem.getAudioInputStream(f2);
                    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(sound);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }

            } else {
                //welcomeText.setText("Player-1 Wins");
                player1win.setOpacity(1);
                celebrate.setOpacity(1);
                fireworks.setOpacity(1);
                arrowdown.setOpacity(0);
                rollb.setDisable(true);


                File f2 = new File("Images/celebrationsound.wav");
                try {
                    AudioInputStream sound = AudioSystem.getAudioInputStream(f2);
                    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(sound);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }


        }
        animate.play();
        playerzz = playerx;
        return playerx;
    }
    public int getplayerzz(){
        return playerzz;
    }

    ////////
}

class players{
    //Player class
    //changing opacity of player 1 and 2
    public void playeraunlock(Label player1 , Label player2 , ImageView rtok , ImageView btok  ){
        //player1.setText("Player-1");
        player1.setOpacity(0.3);
        rtok.setOpacity(0.3);
        player2.setText("Player-2");
        player2.setOpacity(1);
        btok.setOpacity(1);

    }
    public void playerbunlock(Label player1 , Label player2 , ImageView rtok , ImageView btok  ){
        player1.setOpacity(1);
        rtok.setOpacity(1);
        //player1.setText("Player-1");
        //player2.setText("Player-2");
        player2.setOpacity(0.3);
        btok.setOpacity(0.3);

    }

}
class snakes{
    int playerxx;

    public int eat(ImageView token , int playerx , int flag  ) {
        TranslateTransition animate = new TranslateTransition(Duration.millis(400), token);


        HashMap<Integer, Integer> snakeslocodd = new HashMap<Integer, Integer>();   //2 hashmaps accordng to tails is on odd or even row
        HashMap<Integer, Integer> snakesloceven = new HashMap<Integer, Integer>();  //(head , tail)

        snakeslocodd.put(28, 10);
        snakeslocodd.put(37, 3);
        snakesloceven.put(47, 16);
        snakesloceven.put(75, 32);
        snakesloceven.put(94, 71);
        snakeslocodd.put(96, 42);
        if (snakeslocodd.containsKey(playerx + 1)) {
            System.out.println("Snake! ODD");
            //Snake eats
            if (flag == 0) {
                token.setX(-4);
            } else {
                token.setX(-20);
            }
            ///For odd rows
            animate.setToX( ((snakeslocodd.get(playerx + 1)-1) % 10) * 49);
            animate.setToY((-1) * ((snakeslocodd.get(playerx + 1)-1) / 10) * 49);
            playerx = snakeslocodd.get(playerx + 1)-1;
            File f8= new File("Images/tokensoundfinal.wav");  //snake eating sound
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f8);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

        }
        if (snakesloceven.containsKey(playerx + 1)) {
            System.out.println("Snake!");
            if(flag==0) {
                token.setX(404);
            }
            else{
                token.setX(453);
            }
            //For even rows
            animate.setToX((-1) * ((snakesloceven.get(playerx + 1)-1) % 10) * 49);
            animate.setToY((-1) * ((snakesloceven.get(playerx + 1)-1) / 10) * 49);
            playerx = snakesloceven.get(playerx + 1) -1;
            File f8= new File("Images/tokensoundfinal.wav");
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f8);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

        }
        animate.play();
        playerxx = playerx;
        return playerx;
    }
    public int getplayerxx(){
        System.out.println("Playerxx in snakes:"+(playerxx+1));
        return playerxx;
    }
}
class ladders{
    //Ladders class
    int playerxx;
    public int climb( ImageView token , int playerx , int flag){
        //for climbing the ladder
        TranslateTransition animate = new TranslateTransition(Duration.millis(400), token);

        HashMap<Integer, Integer> ladderslocodd = new HashMap<Integer, Integer>(); // (bottom , top)
        HashMap<Integer, Integer> laddersloceven = new HashMap<Integer, Integer>();
        laddersloceven.put(4,56);
        ladderslocodd.put(12,50);
        laddersloceven.put(14,55);
        laddersloceven.put(22,58);
        laddersloceven.put(41,79);
        ladderslocodd.put(54,88);
        if (ladderslocodd.containsKey(playerx + 1)) {
            System.out.println("Ladder! ODD");
            //Climb Ladder
            if (flag == 0) {
                token.setX(-4);
            } else {
                token.setX(-20);
            }
            ///For odd rows
            animate.setToX( ((ladderslocodd.get(playerx + 1)-1) % 10) * 49);
            animate.setToY((-1) * ((ladderslocodd.get(playerx + 1)-1) / 10) * 49);
            playerx = ladderslocodd.get(playerx + 1)-1;
            File f7= new File("Images/laddersoundfinal.wav");
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f7);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

        }
        if (laddersloceven.containsKey(playerx + 1)) {
            System.out.println("Ladder");
            if(flag==0) {
                token.setX(404);
            }
            else{
                token.setX(453);
            }
            //For even rows
            animate.setToX((-1) * ((laddersloceven.get(playerx + 1)-1) % 10) * 49);
            animate.setToY((-1) * ((laddersloceven.get(playerx + 1)-1) / 10) * 49);
            playerx = laddersloceven.get(playerx + 1) -1;
            File f7= new File("Images/laddersoundfinal.wav");
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f7);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

        }
        animate.play();
        /*if (flag ==0){
            player2x= playerx;
        }*/

        playerxx = playerx;
        return playerx;
    }
    public int getplayerxx(){
        System.out.println("Playerxx in Ladders:"+(playerxx+1));
        return playerxx;
    }
}
//////
//Links for reference
//https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fdice%2Bdots&psig=AOvVaw0Yeowz98a7XLhfPL-8fKUY&ust=1640252198222000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCJiR0d6N9_QCFQAAAAAdAAAAABAD
//
//        https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.alamy.com%2Fstock-photo%2Fyellow-ludo.html%3Fpage%3D3&psig=AOvVaw1XwvKyyMQT6j0q3LEwyUFg&ust=1640252275060000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCNCat4SO9_QCFQAAAAAdAAAAABAD
//
//        https://www.youtube.com/watch?v=0WEPWglzP0s
//        https://www.youtube.com/watch?v=FBVrgBNzC8k
//
//        https://www.youtube.com/watch?v=MK0l_TQH6aA
//        https://www.youtube.com/watch?v=H7FANXaanG4
//        https://www.youtube.com/watch?v=2tiso5gNjVY

//////

