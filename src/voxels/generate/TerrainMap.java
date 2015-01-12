package voxels.generate;

import java.util.HashMap;

import voxel.maps.BlockType;
import voxel.maps.Coord3;

public class TerrainMap {
    
    //Chunk storage: 14*14*4 is a capacity hint for HashMap (a guess of the size of the visible world in chunks)
    private HashMap<Coord3, Chunk> chunks = new HashMap<Coord3, Chunk>(14 * 14 * 4);
    public static boolean withinWorldBlocks(Coord3 coord){
    	return (!(coord.y < 0 || coord.y > (HEIGHTLIMITCHUNK*Chunk.XLENGTH)));
    }
       // TerrainDataProvider     
    public static final int HEIGHTLIMITCHUNK = 6;
        public static final int WORLD_HEIGHT_CHUNKS = 4; // you can change this now or later.
 
        // TODO: add a method 'worldHeightBlocks()' // return world_height_chunks * Chunk.YLENGTH;
    
    /*			
     * Return null if chunkCo's y is outside of the world's vertical limits (less than 0 or greater than worldHeight)
     * Check if we already have a chunk at chunkCo.
     * if not, make a new chunk at chunkCo and put it in 'chunks'
     * return the chunk at chunkCo.
     */
    public Chunk createOrLookupChunkAt(Coord3 chunkCo) {
        if(chunkCo.y < 0 || chunkCo.y > HEIGHTLIMITCHUNK){
        	return null;
        }
        Chunk chunk = chunks.get(chunkCo);
        // if the chunk is null {
        // chunk = a new chunk with this chunk coord and this terrainMap)
        //}
        // return the chunk.
        if(chunk==null){
        	chunk = new Chunk(chunkCo,this);
        	chunks.put(chunkCo,chunk);
        }
        return chunk; // TODO: actually implement this.
    }
    public int createOrLookupBlockAt(Coord3 global) {
        /*
         * If this global is outside of the global boundaries: (make a public static method that checks for this)
         * return BlockType.NON_EXISTANT.ordinal();
         * Get the chunk pertaining to this global coord3. Use createOrLookupChunk()
         * if the chunk is null (this really should not happen, we should actually "assert" that it's not null 
         * but let's deal with that later), return NON_E.ordinal() 
         * declare an 'int blockType' and set it to chunk.getBlock( Chunk.toChunkLocalCoord(global) )
         * "oh by the way" Chunk needs a method "getBlock(Coord3 local)"
         * if blockType == BlockType.NON_EXISTANT.ordinal() we need to generate a value for it
         * so if so... set blockType to terrainDataProvider.getBlockDataAtPosition(global); and call: "chunk.setBlockAt(Chunk.toChunkLocalCoord(global));" ("oh, by the way" chunk.setBlockAt())
         * 
         * return blockType;
         * 
         */
    	if(!withinWorldBlocks(global)){
    		return BlockType.YOLOSWAG420.ordinal();
    	}
    	Chunk chunk = createOrLookupChunkAt(global);
    	//TODO: this method needs some extra logic. leaving it alone temporarily.
    	return chunk.getBlock(global);
    }

//    public byte createOrLookupBlockAt(Coord3 globalCo) {
//        return 0; // TODO: actually implement this 
//    }
    
}
