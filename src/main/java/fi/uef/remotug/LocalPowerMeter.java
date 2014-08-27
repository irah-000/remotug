package fi.uef.remotug;

import fi.conf.ae.gl.GLGraphicRoutines;
import fi.conf.ae.gl.texture.GLTextureManager;

public class LocalPowerMeter extends PowerMeter {

	private volatile float localForce = 0;
	
	public void glDraw(){
		
		GLTextureManager.getInstance().bindTexture("meter_face");
		GLGraphicRoutines.draw2DRect(-0.5f, -0.5f, 0.5f, 0.5f, 0);
		
		GLTextureManager.getInstance().bindTexture("meter_indicator");
		GLGraphicRoutines.draw2DRect(-0.1f, -0.1f, 0.9f, 0.1f, 0);
		
	}
	
	public void setLocalForce(float localForce){
		this.localForce = localForce;
	}

}
