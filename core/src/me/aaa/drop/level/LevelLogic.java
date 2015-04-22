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
		
		int x1 = (int)(new_x - data.char_r);
		int x2 = (int)(new_x + data.char_r);
		int y1 = (int)(new_y - data.char_r);
		int y2 = (int)(new_y + data.char_r);
		
		int cx = (int)new_x;
		int cy = (int)new_y;
		
		
		if (data.isWall(x1,cy)) {
			new_x = x1 + 1 + data.char_r;
		//	processed = true;
		} else if (data.isWall(x2,cy)) {
			new_x = x2 - data.char_r;
		//	processed = true;
		}
		
		if (data.isWall(cx,y1)) {
			new_y = y1 + 1 + data.char_r;
		//	processed = true;
		} else if (data.isWall(cx,y2)) {
			new_y = y2 - data.char_r;
		//	processed = true;
		}
		
		if (true) {//!processed) {
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
		
		
		/*
		if (x1 == x2) {
			if (y2 > y1) {
				if (data.isWall(x1,y1)) {
					new_y = y1 + 1 + data.char_r;
				} else if (data.isWall(x1,y2)) {
					new_y = y2 - data.char_r;
				}
			}
		} else {
			if (y1 == y2) {
				if (data.isWall(x1,y1)) {
					new_x = x1 + 1 + data.char_r;
				} else if (data.isWall(x2,y1)) {
					new_x = x2 - data.char_r;
				}
			} else {
				//TODO
			}
		}*/
		
		//if (x1 == x2)
		
		/*
		if (vx > 0) {
			if (data.grid[(int)(new_x + data.char_r)][(int)data.char_y] == 4) {
				new_x = (int)(new_x+data.char_r) - data.char_r;
				//new_y = data.char_y + vy * (new_x - data.char_x) / vx;
			}
		} else {
			if (data.grid[(int)(new_x - data.char_r)][(int)data.char_y] == 4) {
				new_x = (int)(new_x-data.char_r) + data.char_r + 1;
				//new_y = data.char_y + vy * (new_x - data.char_x) / vx;
			}
		}
		
		if (vy > 0) {
			if (data.grid[(int)data.char_x][(int)(new_y + data.char_r)] == 4) {
				new_y = (int)(new_y + data.char_r) - data.char_r;
				//new_x = data.char_x + vx * (new_y - data.char_y) / vy;
			}
		} else {
			if (data.grid[(int)data.char_x][(int)(new_y - data.char_r)] == 4) {
				new_y = (int)(new_y - data.char_r) + data.char_r + 1;
				//new_x = data.char_x + vx * (new_y - data.char_y) / vy;
			}
		}*/
		
		
				
		/*if (data.grid[(int)new_x][(int)data.char_y] == 4) {
			new_x = Math.round(new_x);
			if (new)
		}*/
		
		
		
		data.char_x = new_x;//Math.max(Math.min(data.char_x, data.width - data.char_r), data.char_r);
		data.char_y = new_y;//Math.max(Math.min(data.char_y, data.height - data.char_r), data.char_r);
	}
	
	public void update(float delta) {
		if (shouldUpdatePoisition) updateCharacterPosition(delta);
	}
}
