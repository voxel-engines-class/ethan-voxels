package voxels;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import voxel.maps.BlockType;
import voxel.maps.Coord3;
import voxel.maps.Direction;
import voxels.generate.Chunk;
import voxels.generate.TerrainMap;
import voxels.meshconstruction.BlockMeshUtil;
import voxels.meshconstruction.MeshSet;
import voxels.player.Player;

import com.google.common.primitives.Ints;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.plugins.AWTLoader;
import com.jme3.util.BufferUtils;
import com.jme3.util.SkyFactory;

import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.plugins.AWTLoader;
import com.jme3.ui.Picture;
import com.jme3.util.BufferUtils;
import com.jme3.util.SkyFactory;



/* *
<<<<<<< HEAD
 * Created by didyouloseyourcat on 8/10/14.
 * =======
 * Created by didyouloseyourdog on 8/10/14.
 * this is a file change! see?
 */

public class VoxelWorld extends SimpleApplication
{
    public static MaterialLibrarian materialLibrarian;
    TerrainMap map;
    Player player;
//    Audio audio;
  
    @Override
    public void simpleUpdate(float secondsPerFrame) {}

    @Override
    public void simpleInitApp() {
//        player = new Player(map, cam, null, this, null, rootNode);
//        rootNode.attachChild(player.getPlayerNode());
        materialLibrarian = new MaterialLibrarian(assetManager);
        map = new TerrainMap();
        
        setUpTheCam();
        setupSkyTexture();
//        makeADemoMeshAndAdditToTheRootNode();
        makeSomeChunks();
        System.out.println(Chunk.ToChunkPosition(14, 122, 12).y);
        attachCoordinateAxes(Vector3f.ZERO);
    }
    
    /*
     * TEST METHOD. THIS CODE WILL MOVE!!
     */
    private static void ScreenSettings(VoxelWorld app, boolean fullScreen) {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        int SCREEN_MODE=0; // note: there are usually several, let's pick the first
        AppSettings settings = new AppSettings(true);
        float scale_screen = fullScreen ? 1f : .6f;
        Vector2f screenDims = new Vector2f((int)(modes[SCREEN_MODE].getWidth() * scale_screen ),(int)(modes[SCREEN_MODE].getHeight() * scale_screen ));
        settings.setResolution((int)screenDims.x,(int) screenDims.y);
        settings.setFrequency(modes[SCREEN_MODE].getRefreshRate());
        settings.setBitsPerPixel(modes[SCREEN_MODE].getBitDepth());
        if (fullScreen) {
            settings.setFullscreen(device.isFullScreenSupported());
        }
        app.setSettings(settings);
        app.setShowSettings(false);
    }
    
    private void addTestBlockFace() {
    	for(int i = 0; i < 6; i++){
        MeshSet mset = new MeshSet(); // 1
        Coord3 pos = new Coord3(0,0,0); // 2
        BlockMeshUtil.AddFaceMeshData(pos, mset, i, 0,BlockType.STONE, map); // 3
        Mesh testMesh = new Mesh(); // 4
        ApplyMeshSet(mset, testMesh); // 5
        Geometry someGeometry = new Geometry("test geom", testMesh); // 6
        someGeometry.setMaterial(materialLibrarian.getBlockMaterial()); // 7
        rootNode.attachChild(someGeometry); // 8
    	
      //new section. show same mesh with a texture material
        rootNode.attachChild(someGeometry); 
        Mesh texturedTestMesh = new Mesh(); 
        ApplyMeshSet(mset, texturedTestMesh);
        Geometry someTexturedGeometry = new Geometry("test textured geom", texturedTestMesh); 
        someTexturedGeometry.setMaterial(materialLibrarian.getTexturedBlockMaterial());
        rootNode.attachChild(someTexturedGeometry); 
    	}
    }
    
    private void makeSomeChunks() {
    	for(int x = 0; x < 4; x++){
    		for(int y = 0; y < map.HEIGHTLIMITCHUNK; y++){
    		for(int z = 0; z < 4; z++){
    	 Coord3 chunkCoord = new Coord3(x,0,z); // arbitrary chunk coord
    	 Chunk chunk = map.createOrLookupChunkAt(chunkCoord);
         
         chunk.chunkBrain.meshDirty = true; // THIS IS STEP 4 OF 'PHASE 2 .1'
         //Asserter.assertTrue(chunk != null, "ahhhhhhh... why did we get a null chunk. ay aya ya ayayay");
         rootNode.attachChild(chunk.chunkBrain.getGeometry()); // THIS IS STEP 5 
         // TODO: implement chunk brain class and its getGeometry() method (see below in th
    		}
    		}
    	}
    	 // don't give coords that are outside of the world. OK? thanks.
    	 
    	 
    	 
    	 
         
    }
    /*
     * TEST METHOD. THIS CODE WILL MOVE!!
     */
    public static void ApplyMeshSet(MeshSet mset, Mesh bigMesh)
    {
        if (bigMesh == null) {
            System.out.println("Something is not right. This mesh is null. We should really be throwing an exception here.");
            return;
        }
		
		bigMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(mset.vertices.toArray(new Vector3f[0])));
		bigMesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(mset.uvs.toArray(new Vector2f[0])));

		/* google guava library helps with turning Lists into primitive arrays
		* "Ints" and "Floats" are guava classes.
		* */ 
		bigMesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(Ints.toArray(mset.indices)));
	}
    
    private void makeADemoMeshAndAdditToTheRootNode() {
        Mesh m = new Cylinder(4,24,5,11);
        Geometry g = new Geometry("demo geom", m);
        g.setMaterial(materialLibrarian.getBlockMaterial());
        rootNode.attachChild(g);
        attachCoordinateAxes(Vector3f.ZERO);
    }
    private void attachCoordinateAxes(Vector3f pos){
		  Arrow arrow = new Arrow(Vector3f.UNIT_X);
		  arrow.setLineWidth(4); // make arrow thicker
		  putShape(arrow, ColorRGBA.Red).setLocalTranslation(pos);
		 
		  arrow = new Arrow(Vector3f.UNIT_Y);
		  arrow.setLineWidth(4); // make arrow thicker
		  putShape(arrow, ColorRGBA.Green).setLocalTranslation(pos);
		 
		  arrow = new Arrow(Vector3f.UNIT_Z);
		  arrow.setLineWidth(4); // make arrow thicker
		  putShape(arrow, ColorRGBA.Blue).setLocalTranslation(pos);
    }
    	 
	private Geometry putShape(Mesh shape, ColorRGBA color){
	  Geometry g = new Geometry("coordinate axis", shape);
	  Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	  mat.getAdditionalRenderState().setWireframe(true);
	  mat.setColor("Color", color);
	  g.setMaterial(mat);
	  rootNode.attachChild(g);
	  return g;
	}
    
    private void setUpTheCam() {
        flyCam.setMoveSpeed(30);
    }
    
    private void setupSkyTexture() {
        Texture2D skyTex = TexFromBufferedImage(OnePixelBufferedImage(new Color(.6f,1f,1f,1f)));
        rootNode.attachChild(SkyFactory.createSky(assetManager, skyTex, true));
    }
    private static BufferedImage OnePixelBufferedImage(Color color) {
        BufferedImage image = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0 ; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight() ; ++y) {
                image.setRGB(x, y, color.getRGB() );
            }
        }
        return image;
    }
    private static Texture2D TexFromBufferedImage(BufferedImage bim) {
        AWTLoader awtl = new AWTLoader();
        Image im = awtl.load(bim, false);
        return new Texture2D(im);
    }

    /*******************************
     * Program starts here... ******
     *******************************/
    public static void main(String[] args) {
        VoxelWorld app = new VoxelWorld();
        ScreenSettings(app,false);
        app.start(); // start the game
    }
    
//    private void setupInputs() {
//        inputManager.addMapping("Break", new KeyTrigger(KeyInput.KEY_T), new MouseButtonTrigger(MouseInput.BUTTON_LEFT) );
//        inputManager.addMapping("Place", new KeyTrigger(KeyInput.KEY_G), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT) );
//        inputManager.addMapping("GoHome", new KeyTrigger(KeyInput.KEY_H));
//        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_I));
//        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_J));
//        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_L));
//        inputManager.addMapping("UpArrow", new KeyTrigger(keyInput.KEY_UP));
//        inputManager.addMapping("DownArrow", new KeyTrigger(keyInput.KEY_DOWN));
//        inputManager.addMapping("RightArrow", new KeyTrigger(keyInput.KEY_RIGHT));
//        inputManager.addMapping("LeftArrow", new KeyTrigger(keyInput.KEY_LEFT));
//        inputManager.addMapping("Inventory", new KeyTrigger(KeyInput.KEY_E));
//        inputManager.addMapping("DebugBlock", new KeyTrigger(KeyInput.KEY_B));
//        inputManager.addListener(player.getUserInputListener(), "Break", "Place", "GoHome", "Up", "Down", "Right", "Left",
//                "UpArrow", "DownArrow", "RightArrow", "LeftArrow", "Inventory", "DebugBlock");
//        
//        inputManager.addMapping("moveForward",  new KeyTrigger(keyInput.KEY_W));
//        inputManager.addMapping("moveBackward",  new KeyTrigger(keyInput.KEY_S));
//        inputManager.addMapping("moveRight",  new KeyTrigger(keyInput.KEY_D));
//        inputManager.addMapping("moveLeft",  new KeyTrigger(keyInput.KEY_A));
//        inputManager.addMapping("moveUp",  new KeyTrigger(keyInput.KEY_Q));
//        inputManager.addMapping("moveDown",  new KeyTrigger(keyInput.KEY_Z));
//        inputManager.addMapping("jump",  new KeyTrigger(keyInput.KEY_SPACE));
//        inputManager.addListener(player.getAnalogListener(),
//                "moveForward", "moveBackward", "moveRight", "moveLeft", "moveDown", "moveUp", "jump",
//                "lmb", "rmb");
//    }


    public class MaterialLibrarian
    {
        private Material blockMaterial;
        private AssetManager _assetManager;
        private Material texturedBlockMaterial;

        public MaterialLibrarian(AssetManager assetManager_) {
            _assetManager = assetManager_;
        }

        public Material getBlockMaterial() {
            if (blockMaterial == null) {
                Material wireMaterial = new Material(assetManager, "/Common/MatDefs/Misc/Unshaded.j3md");
                wireMaterial.setColor("Color", ColorRGBA.Green);
                wireMaterial.getAdditionalRenderState().setWireframe(true);
                wireMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
                blockMaterial = wireMaterial;
            }
            return blockMaterial;
        }
        
        public Material getTexturedBlockMaterial() {
            if (texturedBlockMaterial == null) {
                Material mat = new Material(assetManager, "BlockTex2.j3md");
                Texture blockTex = assetManager.loadTexture("dog_64d_.jpg");
                blockTex.setMagFilter(Texture.MagFilter.Nearest);
                blockTex.setWrap(Texture.WrapMode.Repeat);
                mat.setTexture("ColorMap", blockTex);
                texturedBlockMaterial = mat;
            }
            return texturedBlockMaterial;
        }

    }


}
