package voxel.maps;

import java.util.HashMap;

import com.jme3.math.Vector2f;

public enum BlockType {
	YOLOSWAG420(null,0),
	AIR(null,1),
	GRASS(new Vector2f[] { 
			new Vector2f(.0f,.75f),//x
			new Vector2f(.0f,.75f),
			new Vector2f(.0f,.25f),//y
			new Vector2f(.0f,.0f),
			new Vector2f(.0f,.75f),//z
			new Vector2f(.0f,.75f)} 
	, 2),
	DIRT(new Vector2f[] { new Vector2f(.0f,.25f) },3),
	STONE(new Vector2f[] { new Vector2f(.25f,.25f) },4 );
	
	public final Vector2f[] texCoords;
	public final int index;
	
	private static BlockType[] types = new BlockType[] {
		YOLOSWAG420, AIR, GRASS, DIRT, STONE
	};

	private BlockType(Vector2f[] _texCoords, int _index) {
		// if the length of array texCoords is one. 
		// just copy the one six times
		// else 
		// the array length should be six
		
		if(_texCoords != null &&  _texCoords.length == 1){
			texCoords = new Vector2f[6];
			for(int i = 0; i < 6; i++){
				texCoords[i] = _texCoords[0];
			}
		}else{
			texCoords = _texCoords;
		}
		index = _index;
	}
	
	public static BlockType getType(int bob){
		switch(bob){
			case 0:
				return BlockType.YOLOSWAG420;
			case 1:
				return BlockType.AIR;
			case 2:
				return BlockType.GRASS;
			case 3:
				return BlockType.DIRT;
			case 4:
				return BlockType.STONE;
		}
		return null;
	}
	
	public static boolean IsVisible(BlockType block) {
		return !(YOLOSWAG420 == block || AIR == block);
	}
	// duck tape :( TODO: fixerino my son
	public static BlockType getIntBlockType(int i) {
		for (BlockType b : types) {
			if (b.index == i) return b;
		}
		return null;
		
	}

}
