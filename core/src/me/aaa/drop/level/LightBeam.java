package me.aaa.drop.level;

public class LightBeam {
	public float src_x;
	public float src_y;
	
	public float left_x;
	public float left_y;
	
	public float right_x;
	public float right_y;
	
	public float[] intervals;
	
	
	public LightBeam(float _src_x, float _src_y, float _left_x, float _left_y, float _right_x, float _right_y) {
		src_x = _src_x;
		src_y = _src_y;
		left_x = _left_x;
		left_y = _left_y;
		right_x = _right_x;
		right_y = _right_y;
		
		intervals = new float[] {0f, 1f};
	}
	
	void removeFromLeft(float a) {
		//return;
		//if (a <= intervals[0]) return;
		
		//intervals = null;
	}
	
	void removeFromRight(float a) {
		//return;
		//if (a >= intervals[intervals.length - 1]) return;
		//intervals = null;
	}
	
	void removeFromMiddle(float a, float b) {
		
	}
	
	void remove(float a, float b) {
		if (intervals == null) return;
		
		if (a > b) {
			remove(b, a);
			return;
		}
		
		if (a <= intervals[0]) {
			removeFromLeft(b);
			return;
		}
		
		if (b >= intervals[intervals.length - 1]) {
			removeFromRight(a);
			return;
		}
		
		removeFromMiddle(a,b);
	}
	
	
	void splitByLine(float x1, float y1, float x2, float y2) {
		if (intervals == null) return;
		
		float d1 = (right_x - left_x) * (y1 - left_y) - (right_y - left_y) * (x1 - left_x);
		float d2 = (right_x - left_x) * (y2 - left_y) - (right_y - left_y) * (x2 - left_x);
		
		if (d1 > 0 && d2 > 0) return;
	}
	
	public void splitByRectangle(float x1, float y1, float x2, float y2) {
		splitByLine(x1, y1, x2, y1);
		splitByLine(x1, y2, x2, y2);
		splitByLine(x1, y1, x1, y2);
		splitByLine(x2, y1, x2, y2);
	}
}
