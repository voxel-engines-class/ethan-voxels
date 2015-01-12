package voxel.maps;

public enum BlockType {
	YOLOSWAG420,
	AIR,
	GRASS,
	DIRT,
	STONE;

	
	public static boolean IsVisible(int block) {
		return !(YOLOSWAG420.ordinal() == block || AIR.ordinal() == block);
	}
	
}
