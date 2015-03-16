package voxels.generate;

import voxel.maps.BlockType;

import com.sudoplay.joise.module.*;
import com.sudoplay.joise.module.ModuleBasisFunction.BasisType;
import com.sudoplay.joise.module.ModuleBasisFunction.InterpolationType;
import com.sudoplay.joise.module.ModuleFractal.FractalType;
 
/*
 * This class's job is to tell
 * whoever wants to know
 * what block-type exists
 * at a given x, y and z
 */
 
public class TerrainDataProvider {
    private static long seed = 133742069;
    private Module module;
 
    public TerrainDataProvider() {
    	setupModule();
    }
 
    public BlockType getBlockDataAtPosition(int xin, int yin, int zin) {//change this to whatever i want to something else that something else is whatever just mess around with that thats part of the thing TODO:
    	// TODO: change the math to get a new pattern. you make up the pattern/math.
    	double scale = 1/(double)(TerrainMap.worldHeightBlocks());
     	return BlockType.getIntBlockType((int)module.get(xin*scale,yin*scale,zin*scale));
     	
   // 	if ((xin + yin + zin ) <  12 || yin < 2 ) return BlockType.GRASS; // #MEDDLERino
  //      return BlockType.AIR; // this method is pretty lazy right now.
    }
    
        private void setupModule() {
            /*
             * ground_gradient: the parameters of setGradient are (xlow, xhigh, ylow, yhigh, zlow, zhigh)
             * x/z low/high are all zero so they don't matter. return values will vary from 0 to 1
             * as y goes from 0 to 1 (they'll equal y actually)
         */
            ModuleGradient groundGradient = new ModuleGradient();
            groundGradient.setGradient(0, 0, 0, 1, 0, 0);
            /*
             * mountain: values vary to produce 'cloud-like' clumps
             */
            // mountain_shape_fractal
            ModuleFractal mountainShapeFractal = new ModuleFractal(FractalType.FBM, BasisType.GRADIENT, InterpolationType.QUINTIC);
            mountainShapeFractal.setNumOctaves(8);
            mountainShapeFractal.setFrequency(1);
            mountainShapeFractal.setSeed(seed);
     
            /*
             * this is a weird one: it turns out that Fractal modules (like the one above) don't reliably give answer
             * that stay within a given bound. When calculate is called, this module polls its source module (mountainShapeFractal in this case)
             * and establishes a high and a low value. It then scales the values from its source between the specified min and max (-1 and 1)
             */
            // mountain_autocorrect
            ModuleAutoCorrect mountainAutoCorrect = new ModuleAutoCorrect(-1, 1);
            mountainAutoCorrect.setSource(mountainShapeFractal);
            mountainAutoCorrect.calculate();
     
            /*
             * Notice how each of these modules takes the last as its source. This module is pretty simple
             * It takes the value given by its source (mountainAutoCorrect), multiplies by 0.45 and adds 0.15
             */
            // mountain_scale
            ModuleScaleOffset mountainScale = new ModuleScaleOffset();
            mountainScale.setScale(0.15);
            mountainScale.setOffset(0.15);
            mountainScale.setSource(mountainAutoCorrect);
     
            /*
             * This module looks similar to ModuleScaleOffset but its doing something totally different.
             * It is scaling the y value of the INPUT that it gets. See the blog's noise posts for an explanation
             * of why this is a good thing.
             */
            // mountain_y_scale
            ModuleScaleDomain mountainYScale = new ModuleScaleDomain();
            mountainYScale.setScaleY(0.1);
            mountainYScale.setSource(mountainScale);
     
            /*
             * If you understand this module, you'll understand our key trick for generating terrain.
             * If it weren't for this technique, we'd be better off using a height map. We are
             * 'perturbing the domain' here. Meaning before our x, y and z "get to" the groundGradient
             * the y will be nudged either up or down, either a little of a lot, by the value of mountainYScale.
             * See the blog for more details on this.
             */
            // mountain_terrain
            ModuleTranslateDomain mountainTerrain = new ModuleTranslateDomain();
            mountainTerrain.setAxisYSource(mountainYScale);
            mountainTerrain.setSource(groundGradient);
     
            /*
             * If the value from the source module (mountainTerrain) is greater than .5, return AIR; else, return GRASS
             */
            // ground_select
            ModuleSelect groundSelect = new ModuleSelect();
            groundSelect.setHighSource(BlockType.AIR.ordinal());
            groundSelect.setLowSource(BlockType.GRASS.ordinal());
            groundSelect.setThreshold(0.1);
            groundSelect.setControlSource(mountainTerrain);
     
            module= groundSelect;
        }
}
