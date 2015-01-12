package voxels.generate;

import voxels.VoxelWorld;
import voxels.meshconstruction.MeshSet;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
//codes by didyouloseyourcat
public class ChunkBrain extends AbstractControl {
	public boolean meshDirty; //TODO swag
	public final Chunk chunk;
//	public Mesh mesh; // *****MEDDLER: DESPITE ANYTHING I MIGHT HAVE SAID BEFORE: HAVING THIS VAR HERE IS BAD. (MAY AS WELL USE getMesh()) IN PLACE OF IT.
	public ChunkBrain(Chunk CHUNK){
		chunk = CHUNK;
	}
	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method
	}

	@Override
	protected void controlUpdate(float tpf) {
		   
		System.out.println("matt");
		// TODO Auto-generated method stub
		if(meshDirty) {
            buildMesh();
            meshDirty = false;
        }
	}

	private void buildMesh() {
        MeshSet mset = new MeshSet();
        ChunkBuilder.buildMesh(chunk, mset);
        ChunkBuilder.ApplyMeshSet(mset, getMesh()); // MESH DOESN'T EXIST. REPLACE IT WITH "GETMESH()"
	}
	
	public Geometry getGeometry() {
        Geometry geom = (Geometry) getSpatial(); // an AbstractControl method
        if (geom == null) {
            Mesh mesh = new Mesh(); // placeholder mesh to be filled later
            mesh.setDynamic(); // hint to openGL that the mesh may change occasionally
            mesh.setMode(Mesh.Mode.Triangles); // GL draw mode 
            geom = new Geometry("chunk_geometry", mesh);
            geom.setMaterial(VoxelWorld.materialLibrarian.getTexturedBlockMaterial());
            geom.addControl(this); //NOTICE THIS LINE: MEANS THAT THIS IS NOW 'OUR' (CHUNKBRAIN'S) GEOM. (WHEN getSpatial() is called again) this "geom" will be returned.
        }
        return geom;
    }
	
	//MEDDLER ADDING GETMESH
	public Mesh getMesh() {
		return getGeometry().getMesh();
	}
	
}
