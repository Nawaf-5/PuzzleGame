package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	private BorderPane border;
	private boolean clicked = true;
	private Button[] bt = new Button[16];
	private ArrayList<Image> images = new ArrayList<>();
	private ArrayList<ImageView> imageview = new ArrayList<>();
	private ArrayList<Integer> ID = new ArrayList<>();
	private ArrayList<Button> btFromArray;
	private Button btFirstClicked, btSecondClicked, btTryAgain;
	private String ID1, ID2, stringForTime;
	private Timeline animation;
	private Label lbl = new Label("00:00");
	private int second = 0, minute = 0, scores = 0, numClickedButtons = 0;
	private Text textForScore, textForHighScore;
	private int highScore = 0;

	private void timeLabel() {

		if (second >= 0) {
			if (second < 60) {
				second++;
			} else if (second >= 60) {
				second = 0;
				minute++;
			}
		}
		if (second < 10) {
			stringForTime = "0" + minute + ":0" + second;
			lbl.setText(stringForTime);
		} else {
			stringForTime = "0" + minute + ":" + second;
			lbl.setText(stringForTime);

		}

	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		// createDataStreams();
		highScore = changeHighScore();

		textForHighScore = new Text("" + highScore);
		textForHighScore.setFont(Font.font(20));
		// creating text for displaying scores
		textForScore = new Text("0");
		textForScore.setFont(Font.font(20));

		// creating try again button
		btTryAgain = new Button("Try Again");
		btTryAgain.setPrefSize(80, 20);
		btTryAgain.setAlignment(Pos.CENTER);
		btTryAgain.setTranslateX(-3);

		// creating a label for displaying the time
		lbl.setFont(Font.font(25));
		animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
			timeLabel();
		}));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

		// creating time for counting
		VBox vbForTime = new VBox();
		vbForTime.setAlignment(Pos.CENTER);
		vbForTime.setSpacing(20);
		vbForTime.setPrefSize(100, 300);
		vbForTime.setTranslateX(-15);
		vbForTime.getChildren().addAll(new Label("highest score: \n"), textForHighScore, new Label("\nScores"),
				textForScore, lbl, btTryAgain);

		// adding images to the arraylist
		int i = 1;
		while (i <= 8) {
			images.add(new Image("file:C:\\Users\\nilyo\\eclipse-workspace\\ICS201 project\\Images\\i" + i + ".jpg"));
			i++;
		}

		// adding images to imageview arraylist
		i = 0;
		int j = 0;
		for (int index = 0; index <= 15; i++, index++) {
			if (i < images.size()) {
				imageview.add(new ImageView(images.get(i)));
				imageview.get(index).setPreserveRatio(true);
				imageview.get(index).setFitHeight(80);
				imageview.get(index).setFitWidth(90);
				ID.add(index);

			} else if (j < images.size()) {
				imageview.add(new ImageView(images.get(j)));
				imageview.get(index).setPreserveRatio(true);
				imageview.get(index).setFitHeight(80);
				imageview.get(index).setFitWidth(90);

				j++;
			}

		}

		// creating buttons
		j = 0;
		i = 0;
		for (int index = 0; index < bt.length; index++, i++) {
			if (i < ID.size()) {
				bt[index] = new Button();
				bt[index].setPrefSize(100, 100);
				bt[index].setVisible(true);
				bt[index].setGraphic(imageview.get(i));
				bt[index].getGraphic().setVisible(false);
				bt[index].setId(ID.get(i).toString());

			} else if (j < ID.size()) {
				bt[index] = new Button();
				bt[index].setPrefSize(100, 100);
				bt[index].setVisible(true);
				bt[index].setGraphic(imageview.get(i));
				bt[index].getGraphic().setVisible(false);
				bt[index].setId(ID.get(j).toString());
				j++;
			}
		}

		btFromArray = new ArrayList<>(Arrays.asList(bt));

		// shuffling the arraylist of buttons
		java.util.Collections.shuffle(btFromArray);

		// creating grid pane for buttons
		GridPane gridForButtons = new GridPane();
		gridForButtons.setAlignment(Pos.CENTER);
		gridForButtons.setHgap(10);
		gridForButtons.setVgap(10);

		// adding buttons to the grid
		i = 0;
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 4; column++) {
				gridForButtons.add(btFromArray.get(i), column, row);

				i++;
			}
		}

		border = new BorderPane();
		border.setCenter(gridForButtons);
		border.setRight(vbForTime);
		BorderPane.setAlignment(vbForTime, Pos.CENTER_RIGHT);

		Scene scene = new Scene(border, 600, 500);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Game");
		primaryStage.show();

		for (i = 0; i < btFromArray.size(); i++) {
			btFromArray.get(i).setOnAction(new Handler());

		}

		btTryAgain.setOnAction(e -> {

			try {
				highScore = changeHighScore();
				textForHighScore.setText("" + highScore);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			lbl.setText("00:00");
			numClickedButtons = 0;
			second = 0;
			minute = 0;
			animation.play();
			scores = 0;
			textForScore.setText("" + scores);

			int k = 0, h = 0;

			for (int index = 0; index < bt.length; index++, k++) {
				if (k < ID.size()) {
					bt[index] = new Button();
					bt[index].setDisable(false);
					bt[index].setPrefSize(100, 100);
					bt[index].setVisible(true);
					bt[index].setGraphic(imageview.get(k));
					bt[index].getGraphic().setVisible(false);
					bt[index].setId(ID.get(k).toString());

				} else if (h < ID.size()) {
					bt[index] = new Button();
					bt[index].setDisable(false);
					bt[index].setPrefSize(100, 100);
					bt[index].setVisible(true);
					bt[index].setGraphic(imageview.get(k));
					bt[index].getGraphic().setVisible(false);
					bt[index].setId(ID.get(h).toString());
					h++;
				}
			}

			btFromArray = new ArrayList<>(Arrays.asList(bt));

			// shuffling the arraylist of buttons
			java.util.Collections.shuffle(btFromArray);

			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);

			// adding buttons to the grid
			k = 0;
			for (int row = 0; row < 4; row++) {
				for (int column = 0; column < 4; column++) {
					grid.add(btFromArray.get(k), column, row);

					k++;
				}
			}

			border.setCenter(grid);

			for (k = 0; k < btFromArray.size(); k++) {
				btFromArray.get(k).setOnAction(new Handler());

			}
		});

	}

	class Handler implements EventHandler<ActionEvent> {

		private PauseTransition pause;

		@Override
		public void handle(ActionEvent e) {
			for (int i = 0; i < btFromArray.size(); i++) {
				if (e.getSource() == btFromArray.get(i)) {
					if (clicked) {
						clicked = false;
						btFirstClicked = btFromArray.get(i);
						ID1 = btFromArray.get(i).getId();
						btFirstClicked.getGraphic().setVisible(true);
						btFirstClicked.setDisable(true);
					} else {
						btSecondClicked = btFromArray.get(i);
						ID2 = btFromArray.get(i).getId();
						btSecondClicked.getGraphic().setVisible(true);
						btSecondClicked.setDisable(true);

						pause = new PauseTransition(Duration.seconds(0.5));
						pause.setOnFinished(event -> {
							try {
								checkBothButtons();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						});
						pause.play();

						clicked = true;
					}
					break;
				}
			}
		}

	}

	private void checkBothButtons() throws IOException {

		if (ID1.equals(ID2)) {

			btFirstClicked.getGraphic().setVisible(true);
			btFirstClicked.setDisable(true);
			btSecondClicked.setDisable(true);
			btSecondClicked.getGraphic().setVisible(true);
			// correctSound(); it did not work with us
			scores++;
			numClickedButtons += 2;
			if (numClickedButtons == 16) {
				animation.pause();
				writeToTheFile(scores);

			}
			textForScore.setText("" + scores);
		} else {

			btFirstClicked.setDisable(false);
			btFirstClicked.getGraphic().setVisible(false);
			btSecondClicked.setDisable(false);
			btSecondClicked.getGraphic().setVisible(false);
			// wrongSound(); it did not work with us
			scores--;
			if (scores < 0) {
				scores = 0;
				textForScore.setText("" + scores);
			} else {
				textForScore.setText("" + scores);
			}

		}

	}

	/*
	 * public void createDataStreams() throws IOException { try (PrintWriter pw= new
	 * PrintWriter(new FileOutputStream("score.txt", true))) { pw.print(0+" "); }
	 * 
	 * }
	 */

	public int changeHighScore() throws IOException {
		try (Scanner input = new Scanner(new FileInputStream("score.txt"));) {
			ArrayList<Integer> list = new ArrayList<>();
			boolean check = false;
			while (input.hasNext()) {
				highScore = input.nextInt();
				list.add(highScore);
				check = true;
			}
			if (check) {
				highScore = java.util.Collections.max(list);
			} else {
				highScore = 0;
			}

		}

		return highScore;
	}

	void writeToTheFile(int score) throws FileNotFoundException {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream("score.txt", true))) {
			pw.print(score + " ");

		}
	}

	void correctSound() {
		String URL = "file: C:\\Users\\nilyo\\eclipse-workspace\\javafxtest\\correct.mp3";

		Media media = new Media(URL);
		MediaPlayer mediap = new MediaPlayer(media);

		mediap.setAutoPlay(true);
	}

	void wrongSound() {
		String URL = "file: C:\\Users\\nilyo\\eclipse-workspace\\javafxtest\\wrong.mp3";

		Media media = new Media(URL);
		MediaPlayer mediap = new MediaPlayer(media);

		mediap.setAutoPlay(true);

	}

	public static void main(String[] args) {
		launch(args);
	}

}
