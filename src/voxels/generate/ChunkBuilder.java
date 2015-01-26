package voxels.generate;
//imports!
 
import static voxels.generate.Chunk.*;

import com.google.common.primitives.Ints;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
//import com.jme3.util.BufferUtils;

import com.jme3.util.BufferUtils;

import voxel.maps.BlockType;
import voxel.maps.Coord3;
import voxels.meshconstruction.BlockMeshUtil;
import voxels.meshconstruction.MeshSet;
import voxel.maps.Direction;
public class ChunkBuilder {
    
	public static void buildMesh(Chunk chunk, MeshSet mset) {
    	TerrainMap map = chunk.map;
    	int triIndexStart = 0;
        // TODO: WRITE THIS METHOD (DO THIS ON PAPER FIRST AND NOT BEFORE WE TALK ABOUT WHAT TO DO).
    	//TODO: Fourloops
    	Coord3 chGlobal;
    	chGlobal = Chunk.ToWorldPosition(chunk.position); //yep
    	for(int x = 0; x < Chunk.XLENGTH; x++){
    		for(int y = 0; y < Chunk.YLENGTH; y++){
    			for(int z = 0; z < Chunk.ZLENGTH; z++){
    				Coord3 blockGlobal = chGlobal.add(new Coord3(x,y,z));
    				int block = map.createOrLookupBlockAt(blockGlobal); // TODO: fix dat shiz
    				if(!BlockType.IsVisible(block)){ //TECHNICALLY, WE MEAN "IS VISIBLE" TODO: REFACTOR SWOAPY
    					continue;
    				}
    	    		for(int dir = 0; dir < Direction.DirectionCoords.length; dir++){
    	    				if (IsFaceVisible(map,blockGlobal,dir)) {
    	    					//TODO: fill in da geometry
    	    					BlockMeshUtil.AddFaceMeshData(blockGlobal, mset, dir, triIndexStart);
    	    					triIndexStart+=4;
    	    				}
    	    		}
    	    	}
        	}
    	}
    }
 
    // SAME METHOD AS THE ONE NOW IN VOXELWORLD.JAVA. NOW IN A BETTER HOME
    public static void ApplyMeshSet(MeshSet mset, Mesh bigMesh)
    {
        if (bigMesh == null) {
            System.out.println("trying to apply a mesh set to a null mesh");
            return;
        }
        
            bigMesh.clearBuffer(Type.Position);
             bigMesh.clearBuffer(Type.TexCoord);
            bigMesh.clearBuffer(Type.Index);
            
            
            bigMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(mset.vertices.toArray(new Vector3f[0])));
            bigMesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(mset.uvs.toArray(new Vector2f[0])));
               
            // google guava library helps with turning Lists into primitive arrays
            // "Ints" and "Floats" are guava classes. 
            
            bigMesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(Ints.toArray(mset.indices)));
            
        
        bigMesh.clearBuffer(Type.Color);
 
 
        bigMesh.setDynamic();
//        bigMesh.setMode(Mesh.Mode.Triangles);
        BoundingBox bbox = new BoundingBox(new Vector3f(0,0,0), new Vector3f(XLENGTH, YLENGTH, ZLENGTH));
        bigMesh.setBound(bbox);
        bigMesh.updateBound();
 
    }
 
        // YOU'LL NEED THIS METHOD IN BUILDMESH()
    private static boolean IsFaceVisible(TerrainMap terrainMap, Coord3 globalblockCo, int direction) {
    	int neighborBLOCK = terrainMap.createOrLookupBlockAt(globalblockCo.add(Direction.DirectionCoords[direction]));
    	return (!BlockType.IsVisible(neighborBLOCK)) ;
    		
        // DA LOGIC: 
                // THE FACE IS VISIBLE IF THE NEIGHBOR BLOCK IN 'DIRECTION' 
                // IS NOT SOLID
                // MAKE A NEW COORD3 THAT IS THE COORD FOR THIS NEIGHBOR: 
                // EXAMPLE: globalCo is (3, 4, 6) AND direction is Direction.ZNEG: neighborCo = (3, 4, 5)
                // ASK terrainMap FOR THE BLOCK AT COORD neighborCo. 
                // byte blockType = terrainMap.lookupOrCreateBlock(neighborCo);
                // RETURN TRUE IF blockType IS NOT A SOLID TYPE (i.e. it's air but you could make a method for this in BlockType non the less)
    	
    }
}