package application;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RushIt extends Application implements Constants {
  private Levels type = Levels.EASY_LEVEL;
  private Maps map = Maps.DESERT_MAP;
  private Games game = Games.STANDARD;

  @Override
  public void start(Stage primaryStage) {
    Pane root = new Pane();

    // load pictures of the main menu
    Image image = new Image(getClass().
        getResourceAsStream("resorses/screen/mainImage.jpg"));
    ImageView img = new ImageView(image);
    img.setFitHeight(SCREEN_HEIGHT);
    img.setFitWidth(SCREEN_WIDTH);
    root.getChildren().add(img);

    // load and play sound of the main menu
    Media media = new Media(getClass().
        getResource("resorses/media/mainAudio.mp3").toString());
    MediaPlayer player = new MediaPlayer(media);
    player.setOnEndOfMedia(new Runnable() {
      public void run() {
        player.seek(Duration.ZERO);
      }
    });
    player.play();

    // write on main screen name of the game
    Text gameName = new Text("Rush It v0.5 2016");
    gameName.setFont(Font.loadFont(getClass().
        getResourceAsStream("resorses/font/Chiller-Regular.ttf"), 40));
    gameName.setFill(Color.RED);
    root.getChildren().add(gameName);
    gameName.setTranslateX(250);
    gameName.setTranslateY(710);
    gameName.setVisible(true);

    // first menu buttons
    MenuItems newGame = new MenuItems("New Game");
    MenuItems loadGame = new MenuItems("Load Game");
    MenuItems computerGame = new MenuItems("Computer Game");
    MenuItems highScores = new MenuItems("High Scores");
    MenuItems credits = new MenuItems("Credits");
    MenuItems exit = new MenuItems("Exit");
    PlaceMenu mainMenu = new PlaceMenu(newGame, loadGame, computerGame,
        highScores, credits, exit);

    // second menu buttons
    MenuItems rush = new MenuItems("Rush");
    MenuItems extraRush = new MenuItems("Extra Rush");
    MenuItems survival = new MenuItems("Survival");
    MenuItems superSurvival = new MenuItems("Super Survival");
    MenuItems backToMainMenu = new MenuItems("Back");
    PlaceMenu gameType = new PlaceMenu(rush, extraRush,
        survival, superSurvival, backToMainMenu);

    // third menu buttons
    MenuItems desert = new MenuItems("Desert");
    MenuItems field = new MenuItems("Field");
    MenuItems sandyRoad = new MenuItems("Sandy Road");
    MenuItems backToGameTypeMenu = new MenuItems("Back");
    PlaceMenu maps = new PlaceMenu(desert, field,
        sandyRoad, backToGameTypeMenu);
    MenuBoxs menuBox = new MenuBoxs(mainMenu);

    // the appointment of the first buttons
    newGame.setOnMouseClicked(event -> menuBox.setMenuBoxs(gameType));
    loadGame.setOnMouseClicked(event -> {
      StartGame startGame = new StartGame();
      game = Games.REPLAY;
      player.stop();
      startGame.start(primaryStage, type, map, game);
    });
    computerGame.setOnMouseClicked(event -> {
      StartGame startGame = new StartGame();
      game = Games.COMPUTER;
      player.stop();
      startGame.start(primaryStage, type, map, game);
    });
    exit.setOnMouseClicked(event -> System.exit(0));

    // the appointment of the second buttons
    rush.setOnMouseClicked(event -> {
      menuBox.setMenuBoxs(maps);
      type = Levels.EASY_LEVEL;
    });
    extraRush.setOnMouseClicked(event -> {
      menuBox.setMenuBoxs(maps);
      type = Levels.NORMAL_LEVEL;
    });
    survival.setOnMouseClicked(event -> {
      menuBox.setMenuBoxs(maps);
      type = Levels.HARD_LEVEL;
    });
    superSurvival.setOnMouseClicked(event -> {
      menuBox.setMenuBoxs(maps);
      type = Levels.STRONG_LEVEL;
    });
    backToMainMenu.setOnMouseClicked(event -> menuBox.setMenuBoxs(mainMenu));

    // the appointment of the third buttons
    desert.setOnMouseClicked(event -> {
      StartGame startGame = new StartGame();
      map = Maps.DESERT_MAP; // desert map = 1
      player.stop();
      startGame.start(primaryStage, type, map, game);
    });
    field.setOnMouseClicked(event -> {
      StartGame startGame = new StartGame();
      map = Maps.FIELD_MAP; // field map = 2
      player.stop();
      startGame.start(primaryStage, type, map, game);
    });
    sandyRoad.setOnMouseClicked(event -> {
      StartGame startGame = new StartGame();
      map = Maps.SANDY_ROAD_MAP; // sandy road map = 3
      player.stop();
      startGame.start(primaryStage, type, map, game);
    });
    backToGameTypeMenu.setOnMouseClicked(event -> menuBox.setMenuBoxs(gameType));

    root.getChildren().addAll(menuBox);
    menuBox.setVisible(true);

    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    // load main icon of the game
    Image icon = new Image(getClass().
        getResourceAsStream("resorses/icon/icon.png"));
    primaryStage.getIcons().add(icon);

    primaryStage.setTitle("Rush It");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  private static class MenuItems extends StackPane {
    public MenuItems(String nameOfItem) {
      // make buttons sections and make buttons animation
      Rectangle place = new Rectangle(250, 30, Color.BLACK);
      place.setOpacity(0.4);

      Text itemText = new Text(nameOfItem);
      itemText.setFill(Color.DARKGRAY);
      itemText.setFont(Font.loadFont(getClass().
          getResourceAsStream("resorses/font/Chiller-Regular.ttf"), 30));

      setAlignment(Pos.CENTER);
      getChildren().addAll(place, itemText);

      FillTransition itemAnimation = new
          FillTransition(Duration.seconds(0.4), place);
      setOnMouseEntered(event -> {
        itemAnimation.setFromValue(Color.RED);
        itemAnimation.setToValue(Color.BLACK);
        itemAnimation.setCycleCount(Animation.INDEFINITE);
        itemAnimation.setAutoReverse(true);
        itemAnimation.play();
      });
      setOnMouseExited(event -> {
        itemAnimation.stop();
        place.setFill(Color.BLACK);
      });
    }
  }

  private static class PlaceMenu extends VBox {
    public PlaceMenu(MenuItems... items) {
      // install button in buttons area
      setSpacing(10);
      setTranslateX(100);
      setTranslateY(50);
      for (MenuItems item : items) {
        getChildren().addAll(item);
      }
    }
  }

  private static class MenuBoxs extends Pane {
    // install buttons area on main screen
    static PlaceMenu placeMenu;

    public MenuBoxs(PlaceMenu placeMenu) {
      MenuBoxs.placeMenu = placeMenu;

      setVisible(false);
      Rectangle screenBox = new
          Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT, Color.ALICEBLUE);
      screenBox.setOpacity(0.04);
      getChildren().addAll(screenBox, placeMenu);
    }

    public void setMenuBoxs(PlaceMenu placeMenu) {
      getChildren().remove(MenuBoxs.placeMenu);
      MenuBoxs.placeMenu = placeMenu;
      getChildren().add(MenuBoxs.placeMenu);
    }
  }
}