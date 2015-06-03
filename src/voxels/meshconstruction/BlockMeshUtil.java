package voxels.meshconstruction;

import java.util.Arrays;
import java.util.List;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import voxel.maps.BlockType;
import voxel.maps.Coord3;
import voxel.maps.Direction;
import voxels.generate.TerrainMap;

public class BlockMeshUtil {
        /*
     * Make four verts,
     * 6 indices and 4 UV vector2s
     * add them to mesh Set
     */
	public static Vector3f[][] faceVertices = new Vector3f[][] {
        //Xneg
        new Vector3f[] {
            new Vector3f(-0.5f, -0.5f, -0.5f),
            new Vector3f(-0.5f,  0.5f, -0.5f),
            new Vector3f(-0.5f,  0.5f,  0.5f),
            new Vector3f(-0.5f, -0.5f,  0.5f),    
        },
      //Xpos
        new Vector3f[] {
            new Vector3f(0.5f, -0.5f,  0.5f),
            new Vector3f(0.5f,  0.5f,  0.5f),
            new Vector3f(0.5f,  0.5f, -0.5f),
            new Vector3f(0.5f, -0.5f, -0.5f),
        },
      //Yneg
        new Vector3f[] {
     
                new Vector3f(0.5f,  -0.5f,  0.5f),
                //
                new Vector3f(0.5f,  -0.5f, -0.5f),
                //
                new Vector3f(-0.5f,  -0.5f, -0.5f),
                new Vector3f(-0.5f,  -0.5f,  0.5f),
                //
                
            },
          //Ypos
        new Vector3f[] {
        		//
                new Vector3f(0.5f,  0.5f,  0.5f),
                //
                new Vector3f(-0.5f,  0.5f,  0.5f),

                //
                new Vector3f(-0.5f,  0.5f, -0.5f),
                new Vector3f(0.5f,  0.5f, -0.5f),
                                //
               
            },
            //Zpos

            new Vector3f[] {
                    new Vector3f(0.5f, -0.5f,  -0.5f),
                    //1
                    new Vector3f(0.5f,  0.5f,  -0.5f),
                    //2
                    new Vector3f(-0.5f,  0.5f,  -0.5f),
                    new Vector3f(-0.5f, -0.5f,  -0.5f),
                    //1
                   
                },
            
      //Zneg
            new Vector3f[] {
        		
                    //1
                    new Vector3f(-0.5f, -0.5f,  0.5f),
                    //2
                    new Vector3f(-0.5f,  0.5f,  0.5f),
                    new Vector3f(0.5f,  0.5f,  0.5f),
                    new Vector3f(0.5f, -0.5f,  0.5f),
                    
                    //1 
                    
                },
           
              
        
    };
	private static List<Vector2f> uvs = Arrays.asList(new Vector2f(0,0),new Vector2f(0,1),new Vector2f(1,1),new Vector2f(1,0));
	private static final int[] FaceIndices = new int[] {0,3,2, 0,2,1};
	
    public static void AddFaceMeshData(Coord3 pos, MeshSet mset, int direction, int triIndexStart, BlockType blockType, TerrainMap map)
    {
        FaceVertices(mset, pos, direction);
        UVsForDirection(mset, direction,blockType);
        IndicesForDirection(mset, triIndexStart);
        AddFaceMeshLightData(pos,mset,direction,map);
    }
    
    /* ETHAN: THIS IS PERFECT! NOW CHANGE IT. 
     * AFTER POSTING THIS METHOD, I CHANGED
     * IT IN THE BLOG.  LOOK AT THE NEW METHOD 
     * BELOW.
     *  
    private static void UVsForDirection(MeshSet mset, int dir) {
        mset.uvs.addAll(uvs);
    }*/
   
    /* NEW VERSION OF ABOVE. NOTICE THAT BOTH OLD AND NEW ADD FOUR VECTOR2Fs TO mset.uvs. JUST SLIGHTLY DIFFERENT VECTOR2Fs. */
    private static void UVsForDirection(MeshSet mset, int dir, BlockType blockType) {
      /*
       * TRY CHANGING THE X AND Y OF OFFSETSTART. X AND Y CAN EACH BE 0f, .25f, .5f, or .75f 
       * TRY ANY OF THE 16 COMBINATIONS: (.25f, .25f) for stone, (0f, .75f) for side grass
       * LOOK AT THE TEXTURE "dog_64d_.jpg" TO SEE THAT YOUR CHOICE CORRESPONDS TO THE 
       * TILE OFFSET INDICATED BY X AND Y. YOU MUST EDIT THE FRAGMENT SHADER, AS DESCRIBED HERE:
       * http://voxel.matthewpoindexter.com/class/block-faces-part-2-1-fixing-the-annoyingly-mis-aligned-texture/
       * FOR THIS TO WORK (WELL). 
       */
      Vector2f offsetStart = blockType.texCoords[ dir ];//lockCoordinates(blockType);
      mset.uvs.addAll(Arrays.asList(
              offsetStart ,
              new Vector2f(offsetStart.x, offsetStart.y +.25f),
              new Vector2f(offsetStart.x + .25f, offsetStart.y + .25f),
              new Vector2f(offsetStart.x + .25f, offsetStart.y)
              ));
    }

    private static void IndicesForDirection(MeshSet mset, int triIndexStart) {
    	for (int index = 0; index < FaceIndices.length; index++) {
    		int i = FaceIndices[index]; 
    		mset.indices.add(i + triIndexStart);
    	} 
    }
    
    
    private static void FaceVertices(MeshSet mset, Coord3 position, int dir ) {
    	//FILL IN THE LOGIC
    	//GET THE 1D VECTOR ARRAY AT INDEX 'DIR' IN 2D ARRAY faceVertices
            //FOR EACH OF THE FOUR CORNER VECTORS IN THIS ARRAY
            //MAKE A NEW VECTOR BY ADDING THE CORNER VECTOR
            //TO position (CONVERTED TO A VECTOR3F)
            //ADD THIS NEW VECTOR TO mset's vertices ARRAYLIST USING:
            // mset.vertices.add( the_new_vector );
    	for(int i = 0; i < faceVertices[dir].length; i++){
    		Vector3f corner = faceVertices[dir][i].add(position.toVector3f());
    		mset.vertices.add(corner);
    	}
    }
    
    public static void AddFaceMeshLightData(Coord3 pos, MeshSet mset, int dir, TerrainMap map){
    	for(Vector3f ver : faceVertices[dir]){
    		float[] color;
    		int dx = (int) Math.signum(ver.x);
    		int dy = (int) Math.signum(ver.y);
    		int dz = (int) Math.signum(ver.z);
    		 
    		Coord3 a, b, c, d;
    		// EITHER Z DIRECTION
    		if (dir == Direction.ZNEG || dir == Direction.ZPOS) {
	    		a = new Coord3(0, 0, dz).add(pos);
	    		b = new Coord3(dx, 0, dz).add(pos);
	    		c = new Coord3(0,dy,dz).add(pos);
	    		d = new Coord3(dx,dy,dz).add(pos);
    		}
    		else if (dir == Direction.YNEG || dir == Direction.YPOS) {
	    		a = new Coord3(0, dy, 0).add(pos);
	    		b = new Coord3(0, dy, dz).add(pos);
	    		c = new Coord3(dx,dy,0).add(pos);
	    		d = new Coord3(dx,dy,dz).add(pos);
    		}
    		else  {//X sorry
	    		a = new Coord3(dx, 0, 0).add(pos);
	    		b = new Coord3(dx, 0, dz).add(pos);
	    		c = new Coord3(dx,dy,0).add(pos);
	    		d = new Coord3(dx,dy,dz).add(pos);
    		}
    		
    		int steve = (map.lightlevel(a) + map.lightlevel(b) + map.lightlevel(c) + map.lightlevel(d))/4;
    		
    		color = new float[]{0f, 0f, 0f, (float) steve};
    		
    		
    		
//    		if(ver.y<0){
//    			color = new float[]{0,0,0,0.2f};
//    		}else{
//    			color = new float[]{0,0,0,1f};
//    		}
    		for(float cc : color){
    			mset.colors.add(cc);
    		}
    	}
    }
    
}
