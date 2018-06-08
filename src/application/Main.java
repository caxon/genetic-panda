package application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import pandas.Panda;
import pandas.Panda.Connection;
import world.World;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	
	Canvas canvas;
	GraphicsContext g2;
	Label fitvalue;
	Game game;
	AnimationTimer timer;
	
	public final static int GLAxoffset = 200;
	public final static int GLAyoffset = 5;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			canvas = new Canvas(400d, 400);
			HBox buttons = new HBox(12d);
			HBox infobar = new HBox(12d);
//			infobar.setPadding(new Insets(10, 10, 10,10));
			Button reset = new Button("Reset");
			reset.setOnAction( (event) -> {
				reset();
			});
			Button onestep = new Button(">|");
			onestep.setOnAction( (event) -> {
				game.tickandcheck();
				drawGame(game);
				
			});
			timer= new AnimationTimer() {
				@Override
				public void handle(long now) {
					if (game.tickandcheck() == true) {
						timer.stop();
					}
					resetScreen();
					drawGame(game);
					drawGLA(game.panda);
					
				}
			};
			
			Button start = new Button (">>");
			start.setOnAction((event) -> {
				timer.start();
			});
			Button stop = new Button("||");
			stop.setOnAction((event) -> {
				timer.stop();
			});
			Button random = new Button("Random");
			random.setOnAction((event) -> {
				randomPanda();
			});
			
			fitvalue = new Label();
			fitvalue.setText("5");
			
			buttons.getChildren().add(reset);
			buttons.getChildren().add(onestep);
			buttons.getChildren().add(start);
			buttons.getChildren().add(stop);
			buttons.getChildren().add(random);
			infobar.getChildren().add(fitvalue);
			root.setTop(buttons);
			root.setBottom(infobar);
			root.setCenter(canvas);
			
			g2 = canvas.getGraphicsContext2D();
			reset();
			
			Scene scene = new Scene(root,400,440);
			
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void reset() {
		game = new Game();
		resetScreen();
		drawGame(game);
		drawGLA(game.panda);
	}
	public void resetScreen() {
		g2.setFill(Color.WHITESMOKE);
		g2.fillRect(0, 0, 450, 450);
	}
	public void randomPanda() {
		game = new Game(Game.randomPanda(3));
		resetScreen();
		drawGame(game);
		drawGLA(game.panda);
	}
	public void drawGame(Game game) {
		drawGLA(game.panda);
		World world = game.world;
	
		g2.setFill(Color.LIGHTGREY);
		for (int x = 0; x < world.width; x++) {
			for (int y = 0; y< world.height; y++) {
				int id = world.tiles[y][x];
				if (id == 0) {
					g2.setFill(Color.LIGHTGREY);
				}
				if (id == 1) {
					g2.setFill(Color.GREENYELLOW);
				}
				g2.fillRect(x*10, y*10, 10, 10);
			}
		}

		g2.setFill(Color.BLUE);
		g2.fillOval(game.panda.x*10, game.panda.y*10, 10, 10);
	
		g2.setStroke(Color.DARKBLUE);
		for (int x = 0; x < world.width+1; x++) {
			g2.strokeLine(x*10, 0, x*10, (world.height)*10);
		}
		for (int y = 0; y < world.height+1; y++) {
			g2.strokeLine(0, y*10,(world.width )*10, y*10);
		}
		fitvalue.setText("TIME: " +game.time+ " | FIT: "+ String.format("%.3f", game.panda.fitness)+
				" | COMPLEX.: "+ game.panda.connections.size()+ " | GEN: " + 
				game.generation + " | GENNUMBER: " +game.gennumber);
	}
	
	public void drawGLA(Panda p) {
		
		g2.setStroke(Color.DARKBLUE);
		for (int i = 0 ; i < p.vision.length; i++) {
			int c = p.vision[i];
			if (c == -1)
				g2.setFill(Color.RED);
			else if (c == 0)
				g2.setFill(Color.LIGHTGREY);
			else if (c == 1)
				g2.setFill(Color.GREENYELLOW);
			else 
				g2.setFill(Color.MAGENTA);
			g2.strokeRect(GLAxoffset, i*10 + GLAyoffset, 10, 10);
			g2.fillRect(GLAxoffset, i*10+GLAyoffset, 10, 10);
		}
		String[] dirs = {
				"U", "D", "L", "R", "X"
		};
		g2.setFill(Color.ALICEBLUE);
		g2.setStroke(Color.BLUE);
		g2.setFont(new Font("consolas", 8));
		for (int i = 0; i < 5; i++) {
			g2.strokeRect(GLAxoffset+40, i*10 + GLAyoffset, 10, 10);
			g2.fillRect(GLAxoffset+40, i*10+GLAyoffset, 10, 10);
			g2.strokeText(dirs[i], GLAxoffset+40+2, i*10 + GLAyoffset + 7);
		}
		
		for (Connection c: p.connections) {
			int in = c.in;
			int out = c.out;
			int val = c.val;
			if (val == 0)
				g2.setStroke(Color.GREY);
			else if (val == -1)
				g2.setStroke(Color.RED);
			else if (val == 1)
				g2.setStroke(Color.LIMEGREEN);
			g2.strokeLine(GLAxoffset + 10, c.in*10 + GLAyoffset + 5, GLAxoffset+40, c.out*10 + GLAyoffset + 5);
		}
	}
	
	
}
