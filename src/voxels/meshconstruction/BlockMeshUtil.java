package voxels.meshconstruction;

import java.util.Arrays;
import java.util.List;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import voxel.maps.Coord3;
import voxel.maps.Direction;

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
        		//
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
      
            
      //Zneg
            new Vector3f[] {
                    new Vector3f(0.5f, -0.5f,  0.5f),
                    //1
                    new Vector3f(-0.5f, -0.5f,  0.5f),
                    //2
                    new Vector3f(-0.5f,  0.5f,  0.5f),
                    new Vector3f(0.5f,  0.5f,  0.5f),
                    //1 
                    
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
              
        
    };
	private static List<Vector2f> uvs = Arrays.asList(new Vector2f(0,0),new Vector2f(0,1),new Vector2f(1,1),new Vector2f(1,0));
	private static final int[] FaceIndices = new int[] {0,3,2, 0,2,1};
	
    public static void AddFaceMeshData(Coord3 pos, MeshSet mset, int direction, int triIndexStart)
    {
        FaceVertices(mset, pos, direction);
        UVsForDirection(mset, direction);
        IndicesForDirection(mset, triIndexStart);
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
    private static void UVsForDirection(MeshSet mset, int dir) {
      /*
       * TRY CHANGING THE X AND Y OF OFFSETSTART. X AND Y CAN EACH BE 0f, .25f, .5f, or .75f 
       * TRY ANY OF THE 16 COMBINATIONS: (.25f, .25f) for stone, (0f, .75f) for side grass
       * LOOK AT THE TEXTURE "dog_64d_.jpg" TO SEE THAT YOUR CHOICE CORRESPONDS TO THE 
       * TILE OFFSET INDICATED BY X AND Y. YOU MUST EDIT THE FRAGMENT SHADER, AS DESCRIBED HERE:
       * http://voxel.matthewpoindexter.com/class/block-faces-part-2-1-fixing-the-annoyingly-mis-aligned-texture/
       * FOR THIS TO WORK (WELL). 
       */
      Vector2f offsetStart = new Vector2f(.25f,.25f);
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
}
