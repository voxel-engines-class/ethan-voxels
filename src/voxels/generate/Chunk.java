package voxels.generate;

import voxel.maps.BlockType;
import voxel.maps.Coord3;


/*
* 
*/
public class Chunk {
   public static final int XLENGTH = 16;
   public static final int YLENGTH = 16;
   public static final int ZLENGTH = 16;
private static final int SIZE_X_BITS = 4;
private static final int SIZE_Y_BITS = 4;
private static final int SIZE_Z_BITS = 4;
   public final ChunkBrain chunkBrain;
   public final Coord3 position;
   public final TerrainMap map;
   
   public Chunk(Coord3 chunkCo, TerrainMap terrainMap) {
	// TODO Auto-generated constructor stub
	  chunkBrain = new ChunkBrain(this);
	  position = chunkCo;
	  map = terrainMap;
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
  

   public static Coord3 ToChunkLocalCoord(Coord3 woco) {
      return ToChunkLocalCoord(woco.x, woco.y, woco.z);
   }


//33
    public static Coord3 ToChunkPosition(int pointX, int pointY, int pointZ) {
        /*
         * Bit-wise division: this is equivalent to pointX / (2 to the power of SIZE_X_BITS)  
         * in other words pointX divided by 16. (only works for powers of 2 divisors)  
         * This operation is much faster than the normal division operation ("/")
         */
        int chunkX = pointX >> SIZE_X_BITS;
        int chunkY = pointY >> SIZE_Y_BITS;
        int chunkZ = pointZ >> SIZE_Z_BITS;
        return new Coord3(chunkX, chunkY, chunkZ);
    }
 
       public static Coord3 ToChunkLocalCoord(int x, int y, int z) {
        /*
         * Bitwise mod (%) by X/Y/ZLENGTH. but better. since this is much faster than '%' and as a bonus will always return positive numbers.
         * the normal modulo operator ("%") will return negative for negative left-side numbers. (for example -17 % 16 becomes -1. <--bad.
         * since all local coords are positive we, want -17 mod 16 to be 15.)
         */
        int xlocal = x & (XLENGTH - 1);
        int ylocal = y & (YLENGTH - 1);
        int zlocal = z & (ZLENGTH - 1);
        return new Coord3(xlocal, ylocal, zlocal);
    }
    
    public static Coord3 ToWorldPosition(Coord3 chunkPosition, Coord3 localPosition) {
        /*
         * Opposite of ToChunkPosition
         */
        int worldX = (chunkPosition.x << SIZE_X_BITS) + localPosition.x;
        int worldY = (chunkPosition.y << SIZE_Y_BITS) + localPosition.y;
        int worldZ = (chunkPosition.z << SIZE_Z_BITS) + localPosition.z;
        return new Coord3(worldX, worldY, worldZ);
    }
   public BlockType getBlock(Coord3 local){
	   // ********MEDDLER: CHANGE ZERO TO BlockType.NONEXISTENT.ordinal();
	return BlockType.YOLOSWAG420;
	   
   }
   
   public void setBlock(Coord3 local){
	  //TODO: save ye blocke!!!!!
   }
   
   public static Coord3 ToWorldPosition(Coord3 chunkPosition) {
       return ToWorldPosition(chunkPosition, new Coord3(0,0,0));// HINT: use the method below to find the world position of a block at localPosition = (0,0,0)
   }

public int blockAt(Coord3 local){
	// ********MEDDLER: ERASE THIS WHOLE METHOD: DUPLICATES getBlock()
  return 0;  
}
}
