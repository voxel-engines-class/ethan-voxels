uniform mat4 g_WorldViewProjectionMatrix;

attribute vec3 inPosition;
attribute vec2 inTexCoord;
attribute vec3 inNormal;
attribute vec4 inColor;
attribute vec2 inTexCoord2; //TEX MAP OFFSET.

varying vec2 texCoord;
varying vec2 texMapOffset;
varying float light;
																																																		//				/
void main(){																																															//	`			  /
	light = (inColor.w); // 1.0; //MATTYYYY BOI 3e lerkg jwewlekf wefk jwefqwoeitujlqw4 t90iow4elg jeikglmawr; gmlwar., gawr.gmweroas m,fweifjklsdmç≈ ≤ehkjtbdfmn,vx cmx ,seilfcmvx,eighksjdvx,ncm egisdhjkvnxm,vbcnx vrsthkvmnbcx \/
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
    texCoord = inTexCoord;
    texMapOffset = inTexCoord2;
}