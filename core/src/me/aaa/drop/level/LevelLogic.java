package me.aaa.drop.level;

public class LevelLogic {
	
	LevelData data;
	
	public boolean shouldUpdatePoisition = false; 
	
	public LevelLogic(LevelData _data) {
		data = _data;
	}
	
	private void updateCharacterPosition(float delta) {
		float vx = data.target_x - data.char_x;
		float vy = data.target_y - data.char_y;
		
		float d = (float)Math.sqrt(vx * vx + vy * vy);
		
		float new_x = data.target_x;
		float new_y = data.target_y;
		
		if (d > data.char_speed * delta) {
			new_x = data.char_x + vx * data.char_speed * delta / d;
			new_y = data.char_y + vy * data.char_speed * delta / d;
			
	        
			data.char_a = (float)(Math.atan2(vy, vx) *180 / Math.PI);
		}
		
		
		for (int k=0;k<3;++k) {
			int x1 = (int)(new_x - data.char_r);
			int x2 = (int)(new_x + data.char_r);
			int y1 = (int)(new_y - data.char_r);
			int y2 = (int)(new_y + data.char_r);
			
			int cx = (int)new_x;
			int cy = (int)new_y;
			
		
		
			if (data.isWall(x1,cy))
				new_x = x1 + 1 + data.char_r;
			else if (data.isWall(x2,cy))
				new_x = x2 - data.char_r;
	
		
			if (data.isWall(cx,y1))
				new_y = y1 + 1 + data.char_r;
			else if (data.isWall(cx,y2))
				new_y = y2 - data.char_r;
	
			if (data.isWall(x2, y2)) {
				float dx = new_x - x2;
				float dy = new_y - y2;
				
				float d2 = (float)Math.sqrt(dx * dx + dy * dy);
				if (d2 < data.char_r) {
					new_x = x2 + dx * data.char_r / d2;
					new_y = y2 + dy * data.char_r / d2;
				}
			}
			if (data.isWall(x1, y2)) {
				float dx = new_x - x1 - 1;
				float dy = new_y - y2;
				
				float d2 = (float)Math.sqrt(dx * dx + dy * dy);
				if (d2 < data.char_r) {
					new_x = x1 + 1 + dx * data.char_r / d2;
					new_y = y2 + dy * data.char_r / d2;
				}
			}
			if (data.isWall(x2, y1)) {
				float dx = new_x - x2;
				float dy = new_y - y1 - 1;
				
				float d2 = (float)Math.sqrt(dx * dx + dy * dy);
				if (d2 < data.char_r) {
					new_x = x2 + dx * data.char_r / d2;
					new_y = y1 + 1 + dy * data.char_r / d2;
				}
			}
			if (data.isWall(x1, y1)) {
				float dx = new_x - x1 - 1;
				float dy = new_y - y1 - 1;
				
				float d2 = (float)Math.sqrt(dx * dx + dy * dy);
				if (d2 < data.char_r) {
					new_x = x1 + 1 + dx * data.char_r / d2;
					new_y = y1 + 1 + dy * data.char_r / d2;
				}
			}
		}

		data.char_x = new_x;
		data.char_y = new_y;
	}
	
	public void update(float delta) {
		if (shouldUpdatePoisition) updateCharacterPosition(delta);
		
		float lightSource_x = 5.5f;
        float lightSource_y = 5.5f;
        
        float vx = - (data.char_y - lightSource_y);
        float vy = (data.char_x - lightSource_x);
        
        float d = (float)Math.sqrt(vx*vx + vy*vy);
        vx/=d;
        vy/=d;
        
        float edge1_x = data.char_x + vx*data.char_r;
        float edge1_y = data.char_y + vy*data.char_r;
        
        float edge2_x = data.char_x - vx*data.char_r;
        float edge2_y = data.char_y - vy*data.char_r;
        
        data.lightBeams = new LightBeam[1];
        data.lightBeams[0] = new LightBeam(lightSource_x, lightSource_y, edge1_x, edge1_y, edge2_x, edge2_y);
        
        for (int i = 0; i < data.width; ++i)
        	for (int j = 0; j < data.height; ++j) 
	        	if (data.isWall(i, j)) {
	        		data.lightBeams[0].splitByRectangle(i, j, i + 1, j + 1);
	        	}
	}
}
