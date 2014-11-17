package voxels.generate;

import voxel.maps.Coord3;


/*
* 
*/
public class Chunk {
   public static final int XLENGTH = 16;
   public static final int YLENGTH = 16;
   public static final int ZLENGTH = 16;
   public final ChunkBrain chunkBrain;
   public final Coord3 position;
   
   public Chunk(Coord3 chunkCo, TerrainMap terrainMap) {
	// TODO Auto-generated constructor stub
	  chunkBrain = new ChunkBrain(null);
	  position = chunkCo;
	  
   }
/*
    * Chunk Position Methods
    * Chunk Positions are always the same as 
    * the position of the first block in the chunk (block at lower, xneg, zneg corner with local pos: (0,0,0))
    * divided by their lengths (16 in other words)
    * example: if ChunkPos is (1, 0, 0) SWAGBOIS IN DA HOOD
    *     * its "first" block is at world pos (16, 0, 0)
    * (16, 0, 0) would also be the chunk's "WorldPosition"
    * Conversely, a block with world pos (16, 0, 2)
    * would have chunk local pos (0, 0, 2)
    */
  
   public static Coord3 ToChunkPosition(int pointX, int pointY, int pointZ) {
       int chunkX = pointX/XLENGTH;// divide pointX by XLENGTH
       int chunkY = pointY/YLENGTH;// '' Y '' YLENGTH
       int chunkZ = pointZ/ZLENGTH;// '' Z '' ZLENGTH
       return new Coord3(chunkX, chunkY, chunkZ);
   }
   public static Coord3 ToChunkLocalCoord(Coord3 woco) {
       return ToChunkLocalCoord(woco.x, woco.y, woco.z);
   }
   public static Coord3 ToChunkLocalCoord(int x, int y, int z) {
       // TRICKY FOR NEGATIVE NUMBERS!
       // FOR NOW, USE MOD TO MAKE A SOLUTION THAT 
       // WORKS FOR POSITIVE NUMBERS
       int xlocal = x % XLENGTH;
       int ylocal = y % YLENGTH;
       int zlocal = z % ZLENGTH;
       return new Coord3(xlocal, ylocal, zlocal);
   }
   
   public int getBlock(Coord3 local){
	return 0;
	   
   }
   
   public void setBlock(Coord3 local){
	  
   }
   
   public static Coord3 ToWorldPosition(Coord3 chunkPosition) {
       return ToWorldPosition(chunkPosition, new Coord3(0,0,0));// HINT: use the method below to find the world position of a block at localPosition = (0,0,0)
   }
   public static Coord3 ToWorldPosition(Coord3 chunkPosition, Coord3 localPosition) {
       /*
        * Opposite of ToChunkPoschunkcuhunkposititititititition
        */
       int worldX = chunkPosition.x*16+localPosition.x;
       int worldY =chunkPosition.y*16+localPosition.y;
       int worldZ =chunkPosition.z*16+localPosition.z;
       return new Coord3(worldX, worldY, worldZ);
   }

public TerrainMap getMap() {
	// TODO Auto-generated method stub
	//return map;
	return null;         
}

public int blockAt(Coord3 local){
  return 0;  
}
}
