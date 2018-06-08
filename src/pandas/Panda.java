package pandas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import pandas.Panda.Connection;
import world.World;

public class Panda {

	public static final int MAXVISION = 9;
	public static final int MAXDIR = 5;
	
	public int x=5;
	public int y=5;
	public double fitness = 0;
	public ArrayList<Connection> connections = new ArrayList<Connection>();
	public int[] vision = new int[MAXVISION];
	public static final Directive[] dirs = {
			(p) -> {p.move(0, -1);},
			(p) -> {p.move(0, 1);},
			(p) -> {p.move(1, 0);},
			(p) -> {p.move(-1, 0);},
			(p) -> {
				int r = (int)(Math.random() * 4 );
				if (r == 0)
					p.move(-1,0);
				else if (r == 1)
					p.move(0,1);
				else if (r == 2)
					p.move(1,0);
				else if (r == 3)
					p.move(0,-1);
			}
	};

	
	public Panda() {
//		connections.add(new Connection(5, 2, 0));
//		connections.add(new Connection(5, 1, 1));
//		connections.add(new Connection(5, 1, -1));
	}
	
	public Panda clone() {
		Panda p = new Panda();
		p.connections = new ArrayList<>(this.connections);
		return p;
	}
	
	
	public void tick(World world) {
		think();

		see(world);
	}
	
	public void see(World world) {
		vision[0] = world.getTile(x-1, y-1);
		vision[1] = world.getTile(x, y-1);
		vision[2] = world.getTile(x+1, y-1);
		vision[3] = world.getTile(x-1, y);
		vision[4] = world.getTile(x, y);
		vision[5] = world.getTile(x+1, y);
		vision[6] = world.getTile(x-1, y+1);
		vision[7] = world.getTile(x, y+1);
		vision[8] = world.getTile(x+1, y+1);
	}
	
	public void move(int dx, int dy) {
		this.x +=dx;
		this.y +=dy;
	};
	
	public void think() {
		for (Connection c: connections) {
			if  (vision[c.in] == c.val) {
				dirs[c.out].movePanda(this);
			}
		}
	}
	
	public void addConnection(Connection c) {
		this.connections.add(c);
	}
	public void removeRandomConnection() {
		int size = connections.size();
		connections.remove((int) (Math.random() * size));
	}
	public static class Connection{
		public int in;
		public int out;
		public int val;
		public Connection(int input, int output, int value){
			in = input;
			out = output;
			val = value;
		}
		@Override 
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (!(obj instanceof Connection))
				return false;
			Connection cnx = (Connection) obj;
			return cnx.in == this.in && cnx.out==this.out && cnx.val== this.val;
		}
		@Override 
		public int hashCode() {
			return Objects.hash(in, out, val);
		}

		public static Connection rand() {
			int rvision = (int) (Math.random() * MAXVISION);
			int rdir = (int) (Math.random()* Panda.dirs.length );
			int rval = (int)(Math.random() * 3)-1;
			return new Connection(rvision, rdir, rval);
		}
		
		@Override 
		public Connection clone() {
			return new Connection(this.in, this.out, this.val);
		}
		// Removes all duplicates from two ArrayLists and returns the de-duped list
		public static ArrayList<Connection> deDupe(ArrayList<Connection> c1, ArrayList<Connection> c2) {
			ArrayList<Connection> c3 = c1.stream().
						distinct().
						collect(Collectors.toCollection(ArrayList::new));
			return c3;
		}
	}
	interface Directive{
		void movePanda(Panda p);
	}
	
}
