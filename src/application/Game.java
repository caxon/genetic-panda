package application;

import java.util.ArrayList;
import java.util.Collections;

import pandas.Panda;
import pandas.Panda.Connection;
import world.World;

public class Game {

	Panda panda;
	World world;
	
	int generation = 0;
	int gennumber = 0;
	public int timewithoutscoring = 0;
	public int time;
	
	public Panda[] fittest;
	public int fitness;

	public static final int NUMBERPERGEN = 100;
	public static final int MAXTIME = 50;
	
	public Game(Panda panda){
		this.panda = panda;
		this.world = new World();
		panda.see(world);
	}
	public Game() {
		this.panda = new Panda();
		this.world = new World();
		panda.see(world);
	}
	
	public double getFitness() {
		return panda.fitness;
	}
	
	
	public void reset() {
		world = new World();
		this.panda = randomPanda(3);
	}
	
	public interface MainAction {
		void apply();
	}
	
	public boolean tickandcheck() {
		time ++;
		panda.tick(world);
		int pandatile = world.getTile(panda.x, panda.y);
		if (pandatile == -1) {
			panda.fitness -= 1.0/time;
			return true;
		}
		else if (pandatile == 1) {
			panda.fitness++;
			timewithoutscoring = 0;
			world.setTile(panda.x, panda.y, 0);
		}
		else {
			timewithoutscoring++;
		}
		if (timewithoutscoring > MAXTIME) {
			panda.fitness -= 1.0/time;
			return true;
		}
		return false;
	}
	
	public static Panda randomPanda(int complexity) {
		Panda panda = new Panda();
		for (int i =0;i<complexity; i++) {
			Connection cxn = Connection.rand();
			panda.addConnection(cxn);
		}
		return panda;
	}
	
	public static Panda mutate(Panda p1) {
		Panda p2 = p1.clone();
		double r = Math.random();
		if (r < 0.5) {
			p2.addConnection(Panda.Connection.rand());
		}
		else {
			p2.removeRandomConnection();
		}
		
		return p2;
	}
	
	public static Panda breed(Panda p1, Panda p2) {
		Panda p3 = new Panda();
		
		ArrayList<Connection> c3 = new ArrayList<>();
		c3 = Connection.deDupe(p1.connections, p2.connections);
		Collections.shuffle(c3);

		int size = (int)(c3.size() * (Math.random()/2 + 0.5)) ;
		
		c3.subList(0, size);
		p3.connections = c3;
		return p3;
	}
	
}
