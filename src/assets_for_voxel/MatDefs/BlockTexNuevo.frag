 varying vec2 texCoord;
varying vec2 texMapOffset;

uniform sampler2D m_ColorMap;
uniform vec4 m_Color;

varying float light;

void main(){
  //  vec2 co = (texCoord * .25 + texMapOffset);
    vec4 texColor = texture2D(m_ColorMap, texCoord);
   // vec4 texColor = texture2D(m_ColorMap, texCoord * .25 + texMapOffset);
    gl_FragColor = vec4(mix(m_Color.rgb, texColor.rgb, texColor.a), 1.0) * light; 
}