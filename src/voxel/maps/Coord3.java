package voxel.maps;

import com.jme3.math.Vector3f;

public class Coord3 {
	public int x;
	public int y;
	public int z;
	public Coord3(int xx, int yy, int zz) {
		x = xx;
		y = yy;
		z = zz;
	}
	public Vector3f toVector3f() {
		return new Vector3f(x,y,z);
	}
	public static Coord3 FromVector3f(Vector3f vec){
		return new Coord3((int)vec.x,(int)vec.y,(int)vec.z);
		
	}
	
	public Coord3 add(Coord3 o){
		return new Coord3(x + o.x, y + o.y, z + o.z);
	}
	
	public Coord3 times(Coord3 o){
		return new Coord3(x * o.x, y * o.y, z * o.z);
	}
	
	public Coord3 divide(Coord3 o){
		return new Coord3(x / o.x, y / o.y, z / o.z);
	}
	
	public Coord3 sub(Coord3 o){
		return new Coord3(x - o.x, y - o.y, z - o.z);
	}
	 @Override
	    public boolean equals(Object other) {
	                //this is optional but a good idea: if the exactly same object return true
	        if (this == other) return true; 
	 
	        if (other instanceof Coord3) { // if other is a Coord3 type
	            return x == ((Coord3) other).x && y == ((Coord3) other).y && z == ((Coord3) other).z;
	        }
	        return false;
	    }
	 @Override
	    public int hashCode() {
	        return (z & Integer.MIN_VALUE) |  // z negative ?
	               ((y & Integer.MIN_VALUE) >>> 1 ) | // y negative ?
	               ((x & Integer.MIN_VALUE) >>> 2 ) | // x negative ?
	               ((z & 4095) << 17) | // first 12 binary digits
	               ((y & 31) << 12) | // first 5 binary digits
	               (x & 4095); // first 12 binary digits
	    }
}
